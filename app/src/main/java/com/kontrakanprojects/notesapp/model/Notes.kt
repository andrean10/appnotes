package com.kontrakanprojects.notesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notes(
    val title: String,
    val description: String
) : Parcelable
