package com.example.simpleecommerce.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.simpleecommerce.BuildConfig
import com.example.simpleecommerce.model.database.PurchaseHistory
import com.example.simpleecommerce.model.network.Category
import com.example.simpleecommerce.model.network.ProductPromo
import com.example.simpleecommerce.network.ConstantEndPoint
import com.example.simpleecommerce.repository.LocalDbStuff
import com.example.simpleecommerce.repository.RemoteApiStuff
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val remoteApiStuff = RemoteApiStuff()
    private val localDbStuf = LocalDbStuff.instance
    private val cDisposable = CompositeDisposable()

    private val gson by lazy {
        Gson()
    }

    val allListStuff = MutableLiveData<List<ProductPromo>>()
    val allCategoryStuff = MutableLiveData<List<Category>>()
    val purchaseHistoryStuff = MutableLiveData<List<PurchaseHistory>>()

    fun getAllListStuff(context: Context){
        remoteApiStuff.getAllListStuffVolley(context).addToRequestQueue(makeSimpleRequest())
    }

    private fun makeSimpleRequest(): JsonArrayRequest {
        return JsonArrayRequest(Request.Method.GET, BuildConfig.BASE_URL + ConstantEndPoint.GET_STUFF, null,
            Response.Listener {
                convertResponseToProduct(it)
            },
            Response.ErrorListener {
                Log.d("MainViewModel", "Error get data with Volley")
            })
    }

    private fun convertResponseToProduct(jsonArray: JSONArray){
        try {
            val result = jsonArray.getJSONObject(0)
            val dataResult = result.getJSONObject("data")
            val categoryResult = dataResult.getJSONArray("category")
            val productPromoResult = dataResult.getJSONArray("productPromo")

            val listCategory = mutableListOf<Category>()
            val listProductPromo = mutableListOf<ProductPromo>()

            for (i in 0 until categoryResult.length()) {
                val categoryRaw = categoryResult.getJSONObject(i)
                listCategory.add(gson.fromJson(categoryRaw.toString(), Category::class.java))
            }

            for (i in 0 until productPromoResult.length()){
                val productPromoRaw = productPromoResult.getJSONObject(i)
                listProductPromo.add(gson.fromJson(productPromoRaw.toString(), ProductPromo::class.java))
            }

            allListStuff.postValue(listProductPromo)
            allCategoryStuff.postValue(listCategory)

        }catch (e: Exception){
            Log.d("MainViewModel", "Error parsing data")
        }

    }

    fun getPurchaseHistory(){
        cDisposable.add(localDbStuf.publistResultHistory
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                purchaseHistoryStuff.postValue(it)
            })

        localDbStuf.getPurchasedProduct()
    }

    fun convertHistoryToProduct(items: List<PurchaseHistory>): List<ProductPromo>{
        val listResult = mutableListOf<ProductPromo>()

        for (i in items.indices){
            listResult.add(
                ProductPromo(
                items[i].description ?: "",
                items[i].id ?: "",
                items[i].imageUrl ?: "",
                items[i].loved ?: 0,
                items[i].price ?: "",
                items[i].title ?: ""
            )
            )
        }

        return listResult
    }

    fun dispatchAll(){
        if (!cDisposable.isDisposed) {
            cDisposable.clear()
        }
    }
}