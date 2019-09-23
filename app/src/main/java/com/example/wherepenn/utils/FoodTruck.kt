package com.example.wherepenn.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// FoodTruck Class Parcelled for future modification
// Parcelable classes(user-defined classes) can be directly sent as input to intent
@Parcelize
data class FoodTruck(
    var name: String = "",
    var rating: Double = 0.0,
    var avenue: Int = 0,
    var street: String = "",
    var xVal: Double = 0.0,
    var yVal: Double = 0.0,
    var description: String = "",
    var phoneNumber: String = "",
    var openHours: Map<Int, ArrayList<Int>> = mapOf(
        0 to arrayListOf(0),
        1 to arrayListOf(0),
        2 to arrayListOf(0),
        3 to arrayListOf(0),
        4 to arrayListOf(0),
        5 to arrayListOf(0),
        6 to arrayListOf(0)
    ),
    var distance : Double = 0.0
): Parcelable