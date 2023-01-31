package com.esfimus.gbnotes.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbnotes.R

class RecyclerAdapter(private val itemsList: MutableList<Note>) : RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    private var itemClickListener: OnListItemClick? = null

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.note_card)
        val titleView: TextView = itemView.findViewById(R.id.card_title_field)
        val timeView: TextView = itemView.findViewById(R.id.card_time_field)

        init {
            cardView.setOnClickListener {
                itemClickListener?.onCLick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.titleView.text = itemsList[position].getTitle()
        holder.timeView.text = itemsList[position].getDateAndTime()
    }

    fun setClickListener(listener: OnListItemClick) {
        itemClickListener = listener
    }

}