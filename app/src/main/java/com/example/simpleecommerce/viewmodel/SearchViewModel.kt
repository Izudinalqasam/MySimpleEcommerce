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

class SearchViewModel : ViewModel() {

    private val remoteApiStuff = RemoteApiStuff()
    private val compositeDisposable = CompositeDisposable()
    val listStuffSearch = MutableLiveData<List<ProductPromo>>()

    private var keyword = ""
    private val gson by lazy {
        Gson()
    }

    fun searchProduct(item: String, context: Context){
        remoteApiStuff.getAllListStuffVolley(context).addToRequestQueue(makeSimpleRequest())
        keyword = item
    }

    private fun makeSimpleRequest(): JsonArrayRequest {
        return JsonArrayRequest(
            Request.Method.GET, BuildConfig.BASE_URL + ConstantEndPoint.GET_STUFF, null,
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
            val productPromoResult = dataResult.getJSONArray("productPromo")

            val listProductPromo = mutableListOf<ProductPromo>()

            for (i in 0 until productPromoResult.length()){
                val productPromoRaw = productPromoResult.getJSONObject(i)
                listProductPromo.add(gson.fromJson(productPromoRaw.toString(), ProductPromo::class.java))
            }

            filterProductByKeyword(listProductPromo, keyword)

        }catch (e: Exception){
            Log.d("MainViewModel", "Error parsing data")
        }

    }

    private fun filterProductByKeyword(items: List<ProductPromo>, keyword: String){
        val listItemProduct = mutableListOf<ProductPromo>()

        try {
            for (i in items.indices) {
                if (items[i].title.contains(keyword)){
                    listItemProduct.add(items[i])
                }
            }

            listStuffSearch.postValue(listItemProduct)
        }catch (e: Exception){
            Log.d("SearchViewModel", "Error find item")
        }

    }

    fun dispatchAll(){
        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}