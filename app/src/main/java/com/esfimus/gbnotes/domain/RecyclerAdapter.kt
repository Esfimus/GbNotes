package com.esfimus.gbnotes.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbnotes.R

class RecyclerAdapter(private val itemsList: MutableList<Note>) : RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.card_title_field)
        val textView: TextView = itemView.findViewById(R.id.card_text_field)
        val timeView: TextView = itemView.findViewById(R.id.card_time_field)
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
        holder.textView.text = itemsList[position].getText()
        holder.timeView.text = itemsList[position].getDateAndTime()
    }

}