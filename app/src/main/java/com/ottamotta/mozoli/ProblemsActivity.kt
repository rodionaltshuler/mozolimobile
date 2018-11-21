package com.ottamotta.mozoli

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.rating_list_item.view.*
import kotlinx.coroutines.*
import sample.R


class ProblemsActivity : AppCompatActivity() {

    private val TAG = ProblemsActivity::class.java.simpleName

    companion object {
        val EXTRA_EVENT_ID = "event_id"
        val EXTRA_EVENT_NAME = "event_name"
    }

    private lateinit var adapter: ProblemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val eventId = intent.extras?.getString(EXTRA_EVENT_ID) ?: ""
        title = intent.extras?.getString(EXTRA_EVENT_NAME)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val authModel = AuthModel(applicationContext)
        authModel.authenticate(this) {
            GlobalScope.launch(Dispatchers.Main) {
                Log.d(TAG, "Authenticated - launching post-auth code")
                val api = authModel.apiWrapper()

                adapter = ProblemsAdapter()
                with(recyclerView) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = this@ProblemsActivity.adapter
                }

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

    class ProblemsAdapter() : RecyclerView.Adapter<ProblemViewHolder>() {

        var items: List<Problem> = ArrayList()
            set(value) {
                field = value.sortedBy { it.name?.toInt() }
                notifyDataSetChanged()
            }


        override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ProblemViewHolder {
            //TODO specify layout resource
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rating_list_item, viewGroup, false);
            return ProblemViewHolder(view);
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(viewHolder: ProblemViewHolder, position: Int) {
            viewHolder.problem = items[position]
        }


    }

    class ProblemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var problem: Problem? = null
            set(newProblem) {
                //TODO initialize viewholder
                itemView.rank.text = newProblem?.name
                itemView.userName.text = newProblem?.grade?.font
                itemView.scores.text = newProblem?.requestingUserSolving?.points?.toString()
                field = newProblem
            }

    }

}