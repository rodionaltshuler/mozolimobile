package com.ottamotta.mozoli

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.ottamotta.mozoli.dto.Rating
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.rating_list_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sample.R
import java.text.DecimalFormat

class RatingActivity : AppCompatActivity() {

    private lateinit var adapter: RatingAdapter

    companion object {
        val EXTRA_EVENT_ID = "event_id"
        val EXTRA_EVENT_NAME = "event_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val authModel = MozoliModel(applicationContext)
        authModel.authenticate(this)

        val api = authModel.apiWrapper()

        val eventId = intent.extras?.getString(EXTRA_EVENT_ID) ?: ""
        title = intent.extras?.getString(EXTRA_EVENT_NAME)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = RatingAdapter()
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RatingActivity.adapter
        }


        GlobalScope.launch(Dispatchers.Main) {
            adapter.ratings = api.getRatingByEventAsync(eventId)
                .sortedByDescending { it.score }
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

    class RatingAdapter() : RecyclerView.Adapter<RatingViewHolder>() {

        var ratings: List<Rating> = ArrayList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RatingViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rating_list_item, viewGroup, false);
            return RatingViewHolder(view);
        }

        override fun getItemCount(): Int {
            return ratings.size
        }

        override fun onBindViewHolder(viewHolder: RatingViewHolder, position: Int) {
            val rating = ratings[position]
            rating.rank = position + 1
            viewHolder.rating = ratings[position]
        }


    }

    class RatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var rating: Rating? = null
            set(newRating) {
                itemView.rank.text = newRating?.rank.toString()
                itemView.userName.text = newRating?.userName
                val nf = DecimalFormat("##.###")
                itemView.scores.text = nf.format(newRating?.score)
                field = newRating
            }

    }
}