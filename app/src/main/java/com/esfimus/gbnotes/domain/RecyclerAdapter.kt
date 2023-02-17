package com.esfimus.gbnotes.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.esfimus.gbnotes.R
import com.esfimus.gbnotes.data.Note
import com.esfimus.gbnotes.domain.clicks.OnListItemClick
import com.esfimus.gbnotes.domain.clicks.OnListItemLongClick
import com.esfimus.gbnotes.domain.clicks.OnOptionsClick

class RecyclerAdapter(private val itemsList: MutableList<Note>) :
    RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    private var itemClickListener: OnListItemClick? = null
    private var itemLongClickListener: OnListItemLongClick? = null
    private var optionsClickListener: OnOptionsClick? = null

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.note_card)
        val titleView: TextView = itemView.findViewById(R.id.card_title_field)
        val textView: TextView = itemView.findViewById(R.id.card_text_field)
        val timeView: TextView = itemView.findViewById(R.id.card_time_field)
        private val optionsView: TextView = itemView.findViewById(R.id.card_options)

        // reaction on single and long click on item in list, provided by interface
        init {
            cardView.setOnClickListener {
                itemClickListener?.onCLick(adapterPosition)
            }
            cardView.setOnLongClickListener {
                itemLongClickListener?.onLongClick(adapterPosition, cardView)
                true
            }
            optionsView.setOnClickListener {
                optionsClickListener?.onOptionsClick(adapterPosition, optionsView)
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
        holder.textView.text = itemsList[position].getText()
        holder.timeView.text = itemsList[position].getDate()
    }

    /**
     * Interface method is used for item clicks registration in ListNotesFragment
     */
    fun setClickListener(clickListener: OnListItemClick) {
        itemClickListener = clickListener
    }

    /**
     * Interface method is used for long item clicks registration in ListNotesFragment
     */
    fun setLongClickListener(longClickListener: OnListItemLongClick) {
        itemLongClickListener = longClickListener
    }

    /**
     * Interface method is used for options clicks registration in ListNotesFragment
     */
    fun setOptionsClickListener(optionsListener: OnOptionsClick) {
        optionsClickListener = optionsListener
    }

}