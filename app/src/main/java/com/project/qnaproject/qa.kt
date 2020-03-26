package com.project.qnaproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class qa(
    val message: String,
    val questions: List<Question>
):Parcelable