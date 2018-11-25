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
import java.util.*


class ProblemsActivity : AppCompatActivity() {

    private val TAG = ProblemsActivity::class.java.simpleName

    private lateinit var mozoliModel: MozoliModel
    private lateinit var eventId: String

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


        adapter = ProblemsAdapter()

        with(recyclerView) {
            layoutManager = GridLayoutManager(context, this.resources.getInteger(R.integer.problem_grid_columns))
            adapter = this@ProblemsActivity.adapter
        }

        mozoliModel = MozoliModel(applicationContext)

        loadData()
    }

    private fun loadData() {
        mozoliModel.authenticate(this) {
            GlobalScope.launch(Dispatchers.Main) {
                Log.d(TAG, "Authenticated - launching post-auth code")
                val api = mozoliModel.apiWrapper()
                adapter.setItems(api.getProblemsForEventWithSolutions(eventId).await())
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

    class ProblemsAdapter : RecyclerView.Adapter<ProblemViewHolder>() {

        private val items: MutableList<Problem> = ArrayList()

        private val updateItemAction = { p: Problem -> updateItem(p) }

        fun setItems(items: Problems) {
            with(this.items) {
                clear()
                notifyItemRangeRemoved(0, size)
                addAll(items.sortedBy{it.name.toInt()})
            }
            notifyItemRangeInserted(0, items.size)
        }

        fun updateItem(item: Problem, position: Int = -1) {
            val positionToUpdate = when (position < 0) {
                true -> items.indexOfFirst { p -> p.name == item.name }
                else -> position
            }
            items[positionToUpdate] = item
            notifyItemChanged(positionToUpdate)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ProblemViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.problem_list_item, viewGroup, false);
            return ProblemViewHolder(view, updateItemAction)
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

    class ProblemViewHolder(itemView: View, private val updateItemAction: (Problem) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        var problem: Problem = Problem()
            set(newProblem) {
                field = newProblem
                itemView.routeNumber.text = newProblem.name
                itemView.gradeImage.setColorFilter(Colors.colorByName(newProblem.stickerColor))

                val result = newProblem.requestingUserSolving?.points
                val solved = result != null && result > 0
                itemView.solvedImage visibleIf solved
                itemView onClick {
                    GlobalScope.launch(Dispatchers.Main) {
                        try {
                            val requestBody = when (newProblem.solved()) {
                                true -> newProblem.solutionToCancelSubmission()
                                else -> newProblem.solutionToSubmit(isFlash = true)
                            }
                            itemView.solvedImage visibleIf requestBody.solved()
                            problem.requestingUserSolving =
                                    MozoliModel(itemView.context).apiWrapper().solve(requestBody).await()
                            updateItemAction(problem)
                        } catch (e: Exception) {
                            Log.e("ProblemViewHolder", e.message)
                        }
                    }
                }
            }

    }

}