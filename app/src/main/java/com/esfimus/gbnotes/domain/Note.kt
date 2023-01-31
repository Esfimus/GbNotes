package com.esfimus.gbnotes.domain

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime

class Note(private var title: String?, private var text: String?) : Parcelable {

    private var dateAndTime: String

    init {
        dateAndTime = currentDateAndTime()
    }

    private fun currentDateAndTime() = LocalDateTime.now().toString()

    fun getTitle() = title

    fun getText() = text

    fun getDateAndTime() = dateAndTime

    fun setTitle(newTitle: String) {
        title = newTitle
        dateAndTime = currentDateAndTime()
    }

    fun setText(newText: String) {
        text = newText
        dateAndTime = currentDateAndTime()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(dateAndTime)
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        dateAndTime = parcel.readString().toString()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}