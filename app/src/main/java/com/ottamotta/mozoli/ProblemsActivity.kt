package com.ottamotta.mozoli

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.problem_list_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sample.R


class ProblemsActivity : AppCompatActivity() {

    private val TAG = ProblemsActivity::class.java.simpleName

    private lateinit var authModel: AuthModel
    private lateinit var eventId : String

    companion object {
        val EXTRA_EVENT_ID = "event_id"
        val EXTRA_EVENT_NAME = "event_name"
    }

    private lateinit var adapter: ProblemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        eventId = intent.extras?.getString(EXTRA_EVENT_ID) ?: ""
        title = intent.extras?.getString(EXTRA_EVENT_NAME)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        adapter = ProblemsAdapter{ loadData() }

        with(recyclerView) {
            layoutManager = GridLayoutManager(context, this.resources.getInteger(R.integer.problem_grid_columns))
            adapter = this@ProblemsActivity.adapter
        }

        authModel = AuthModel(applicationContext)

        loadData()
    }

    private fun loadData() {
        authModel.authenticate(this) {
            GlobalScope.launch(Dispatchers.Main) {
                Log.d(TAG, "Authenticated - launching post-auth code")
                val api = authModel.apiWrapper()
                adapter.items = ArrayList()
                adapter.items = api.getProblemsForEventWithSolutions(eventId).await()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class ProblemsAdapter(private val refreshAction: () -> Unit) : RecyclerView.Adapter<ProblemViewHolder>() {

        var items: List<Problem> = ArrayList()
            set(value) {
                field = value.sortedBy { it.name?.toInt() }
                notifyDataSetChanged()
            }


        override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ProblemViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.problem_list_item, viewGroup, false);
            return ProblemViewHolder(view, refreshAction);
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(viewHolder: ProblemViewHolder, position: Int) {
            viewHolder.problem = items[position]
        }

        override fun getItemId(position: Int): Long {
            return items[position].name!!.toLong()
        }

    }

    class ProblemViewHolder(itemView: View, private val refreshAction: () -> Unit) : RecyclerView.ViewHolder(itemView) {

        var problem: Problem = Problem()
            set(newProblem) {
                field = newProblem
                itemView.routeNumber.text = newProblem.name
                itemView.gradeImage.setColorFilter(Colors.colorByName(newProblem.stickerColor))

                val result = newProblem.requestingUserSolving?.points
                val solved = result != null && result > 0
                if (solved) {
                    itemView.solvedImage.visibility = View.VISIBLE
                } else {
                    itemView.solvedImage.visibility = View.GONE
                }
                itemView onClick {
                    GlobalScope.launch (Dispatchers.Main) {
                        try {
                            val requestBody = newProblem.solutionToSubmit()
                            Log.i("ProblemViewHolder", "Request body: $requestBody")
                            val solution = AuthModel(itemView.context).apiWrapper().solve(
                                requestBody
                            ).await()
                            Log.i("ProblemViewHolder", solution.toString())
                            refreshAction()
                        } catch (e : Exception) {
                            Log.e("ProblemViewHolder", e.message)
                        }
                    }
                }
            }

    }

}