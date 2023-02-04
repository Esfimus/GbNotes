package com.esfimus.gbnotes.domain.clicks

import android.view.View

interface OnListItemLongClick {
    fun onLongClick(position: Int, itemView: View)
}