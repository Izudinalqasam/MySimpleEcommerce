package com.example.simpleecommerce.model.database

import io.realm.RealmObject

open class PurchaseHistory (
    var description: String? = null,
    var id: String? = null,
    var imageUrl: String? = null,
    var loved: Int? = 0,
    var price: String? = null,
    var title: String? = null
) : RealmObject()