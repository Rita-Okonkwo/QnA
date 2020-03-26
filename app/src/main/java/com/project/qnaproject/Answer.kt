package com.project.qnaproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(
    val option: String,
    val value: Boolean
): Parcelable