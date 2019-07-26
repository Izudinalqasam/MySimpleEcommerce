package com.example.simpleecommerce.repository

import android.content.Context
import com.example.simpleecommerce.model.network.ListStuffResult
import com.example.simpleecommerce.network.RetrofitLib
import com.example.simpleecommerce.network.VolleyLib
import io.reactivex.Single

class RemoteApiStuff {

    fun getAllListStuffs(): Single<String> {
        return RetrofitLib.instance.getListStuff()
    }

    fun getAllListStuffVolley(context: Context): VolleyLib = VolleyLib.getInstance(context)
}