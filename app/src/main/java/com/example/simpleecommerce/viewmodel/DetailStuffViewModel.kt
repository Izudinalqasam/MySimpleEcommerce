package com.example.simpleecommerce.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.simpleecommerce.model.network.ProductPromo
import com.example.simpleecommerce.repository.LocalDbStuff

class DetailStuffViewModel : ViewModel() {

    private val localRepo = LocalDbStuff.instance

    fun insertToHistory(item: ProductPromo){
        localRepo.insertItemToHistory(item)
    }
}