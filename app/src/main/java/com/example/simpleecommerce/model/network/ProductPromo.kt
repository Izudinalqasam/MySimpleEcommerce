package com.example.simpleecommerce.model.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductPromo(
    val description: String,
    val id: String,
    val imageUrl: String,
    val loved: Int,
    val price: String,
    val title: String
) : Parcelable