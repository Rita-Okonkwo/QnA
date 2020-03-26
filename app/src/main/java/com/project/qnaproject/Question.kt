package com.project.qnaproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val __v: Int,
    val _id: String,
    val answers: List<Answer>,
    val question: String
):Parcelable