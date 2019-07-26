package com.example.simpleecommerce.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.simpleecommerce.R
import com.example.simpleecommerce.viewmodel.LoginViewModel
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val EXTRA_SING_IN = 101
    private lateinit var mGoogleClient: GoogleSignInClient
    private lateinit var callback: CallbackManager

    private val loginViewModel by lazy {
        ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        btn_login.setOnClickListener(this)

        callback = CallbackManager.Factory.create()
        btn_login_facebook.setReadPermissions(Arrays.asList("email","public_profile"))
        btn_login_facebook.registerCallback(callback, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                goToHome()
            }

            override fun onCancel() {}

            override fun onError(error: FacebookException?) {
                Log.d(LoginActivity::class.java.simpleName, "Terjadi Kesalah saat proses login facebook")
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleClient = GoogleSignIn.getClient(this, gso)
        btn_google_login.setOnClickListener {
            signIn()
        }
    }

    private fun signIn(){
        val signInIntent = mGoogleClient.signInIntent
        startActivityForResult(signInIntent, EXTRA_SING_IN)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login -> {
                goToHome()
            }
        }
    }

    private fun goToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callback.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EXTRA_SING_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSingInResult(task)
        }
    }

    private fun handleSingInResult(completeTask: Task<GoogleSignInAccount>){
        try {
            goToHome()
        }catch (e: ApiException){
            Log.d(LoginActivity::class.java.simpleName, "Terjadi Kesalah saat proses login")
        }
    }

    private val tokenTracer =  object : AccessTokenTracker(){
        override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
            if (currentAccessToken == null){
                Log.d(LoginActivity::class.java.simpleName, "User Log out ")
            }else{
                loginViewModel.loadUserProfile(currentAccessToken)
            }
        }
    }

    override fun onStart() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let { goToHome() }
        super.onStart()
    }

}
