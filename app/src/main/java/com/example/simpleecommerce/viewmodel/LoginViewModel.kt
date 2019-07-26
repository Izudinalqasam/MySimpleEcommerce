package com.example.simpleecommerce.viewmodel

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest

class LoginViewModel : ViewModel() {

    fun loadUserProfile(accessToken: AccessToken){
        val request = GraphRequest.newMeRequest(accessToken) { myObject, response ->
            // get object here
        }

        val bundle = Bundle()
        bundle.putString("field","first_name,last_name,email,id")

        request.parameters = bundle
        request.executeAsync()
    }
}