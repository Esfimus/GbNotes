package com.esfimus.gbnotes.data

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime

data class Note(private var title: String?, private var text: String?) : Parcelable {

    private var date: String

    init {
        date = currentDateAndTime()
    }

    private fun currentDateAndTime() = LocalDateTime.now().toString()

    fun getTitle() = title

    fun getText() = text

    fun getDate() = date

    fun setTitle(newTitle: String) {
        title = newTitle
        date = currentDateAndTime()
    }

    fun setText(newText: String) {
        text = newText
        date = currentDateAndTime()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(date)
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        date = parcel.readString().toString()
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}