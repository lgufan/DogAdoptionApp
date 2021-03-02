package com.example.androiddevchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dog(
    val head: Int = 0,
    val name: String = "",
    val kind: String = "",
    val age: String = ""
) : Parcelable
