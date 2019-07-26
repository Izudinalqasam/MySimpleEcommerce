package com.example.simpleecommerce.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyLib(context: Context) {

    companion object {
        @Volatile
        private var instance: VolleyLib? = null

        fun getInstance(context: Context) =
                instance ?: synchronized(this){
                    instance ?: VolleyLib(context).also {
                        instance = it
                    }
                }
    }

    private val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>){
        requestQueue.add(request)
    }

}