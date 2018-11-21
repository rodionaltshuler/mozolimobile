package com.ottamotta.mozoli

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.event_list_item.view.*
import kotlinx.coroutines.*
import sample.R


class EventsActivity : AppCompatActivity() {


    private lateinit var adapter : EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val authModel = AuthModel(applicationContext)
        val api = authModel.apiWrapper()

        adapter = EventsAdapter()
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@EventsActivity.adapter
        }

        GlobalScope.launch(Dispatchers.Main) {
            adapter.events = api.getEventsByCity("1").await()
        }

    }

    class EventsAdapter() : RecyclerView.Adapter<EventViewHolder>() {

        var events: List<Event> = ArrayList()
            set(value) {
                field = value.filter { !it.name.equals("Event 1") }
                notifyDataSetChanged()
            }


        override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): EventViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.event_list_item, viewGroup, false);
            return EventViewHolder(view);
        }

        override fun getItemCount(): Int {
            return events.size
        }

        override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {
            viewHolder.event = events[position]
        }


    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var event: Event? = null
            set(newEvent) {
                val imageUrl = "https://res.cloudinary.com/mozoli/image/upload/w_750/v1536925700/" + newEvent?.coverId + ".jpg"
                Picasso.get().load(imageUrl).into(itemView.cover)
                itemView.description.setText(newEvent?.description)
                itemView.title.setText(newEvent?.name)
                field = newEvent
                itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val i = Intent(v?.context, ProblemsActivity::class.java)
                        i.putExtra(ProblemsActivity.EXTRA_EVENT_ID, newEvent?.id)
                        i.putExtra(ProblemsActivity.EXTRA_EVENT_NAME, newEvent?.name)
                        v?.context?.startActivity(i)
                    }
                })
            }

    }

}