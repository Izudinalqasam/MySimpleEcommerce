package com.example.simpleecommerce.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface StuffAPI {

    @GET(ConstantEndPoint.GET_STUFF)
    @Headers(ConstantEndPoint.HEADER_CONTENT_TYPE_NAME + ConstantEndPoint.HEADER_CONTENT_TYPE_VALUE)
    fun getListStuff(): Single<String>
}