package com.example.erikslustonlineshop.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.erikslustonlineshop.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(), View.OnClickListener{

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        findViewById<TextView>(R.id.tv_forgotPassword).setOnClickListener { this }
        findViewById<Button>(R.id.btn_login).setOnClickListener { this }
        findViewById<TextView>(R.id.tv_register).setOnClickListener { this }
    }

    override fun onClick(view: View?) {

        if(view != null){
            when (view.id){
                R.id.tv_forgotPassword -> {
                    //launch the forgot password screen
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_login -> {
                    //login via firebase
                    logInRegisteredUser()
                    val intent = Intent(this@LoginActivity, AddProductActivity::class.java)
                    startActivity(intent)

                }
                R.id.tv_register -> {
                    //launch register screen
                    val intent= Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    Log.d("register?", "text clicked")
                }
            }
        }

    }

    fun validateLoginDetails():Boolean {
        val et_email_login= findViewById<EditText>(R.id.et_email_login)
        val email= et_email_login.text.toString().trim { it <= ' ' }

        val et_password_login = findViewById<EditText>(R.id.et_password_login)
        val password = et_password_login.text.toString().trim { it <= ' ' }

        if(email != null){
            showErrorSnackBar("", false)
        } else {
            showErrorSnackBar("Please enter your email address", true)
        }

        if(password != null){
            showErrorSnackBar("", false)
        } else {
            showErrorSnackBar("Please enter your password", true)
        }

        return true

    }

    fun logInRegisteredUser(){

        val et_email_login= findViewById<EditText>(R.id.et_email_login)
        val email= et_email_login.text.toString().trim { it <= ' ' }

        val et_password_login = findViewById<EditText>(R.id.et_password_login)
        val password = et_password_login.text.toString().trim { it <= ' ' }

        if(validateLoginDetails()){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        Log.d("firebaseSignin", "signin success")
                        val user = auth.currentUser
                    }else{
                        Log.d("firebaseSignin", task.exception.toString())
                        showErrorSnackBar("Login Failed: task.exception.toString()", true)
                    }
                }
        }
    }

}