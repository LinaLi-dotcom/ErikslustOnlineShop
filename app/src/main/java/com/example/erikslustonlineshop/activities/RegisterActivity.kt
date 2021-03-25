 package com.example.erikslustonlineshop.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.*
import com.example.erikslustonlineshop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.widget.Toolbar
import com.example.erikslustonlineshop.firestore.FirestoreClass
import com.example.erikslustonlineshop.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*


 class RegisterActivity : BaseActivity() {

     private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //launch the register screen when the user clicks on the text.

        findViewById<TextView>(R.id.tv_login_registerActivity).setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        setupActinBar()

        btn_register.setOnClickListener {
            RegisterUser()
        }

        val tv_login_register = findViewById<TextView>(R.id.tv_login_registerActivity)
        tv_login_register.setOnClickListener {
            // call OnBack funtion when 'login' text clicked, which go back to login activity
            onBackPressed()
        }

    }

     private fun setupActinBar(){

         val toolbar_register_activity=findViewById<Toolbar>(R.id.toolbar_register_activity)

         setSupportActionBar(toolbar_register_activity)

         val actionBar = supportActionBar
         if(actionBar != null) {
             actionBar.setDisplayHomeAsUpEnabled(true)
             actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
         }
         toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }

     }

     private fun validateRegisterDetails(): Boolean {

         val et_register_email = findViewById<EditText>(R.id.et_email_login)
         val et_register_password = findViewById<EditText>(R.id.et_password_login)
         val et_repeatpassword = findViewById<EditText>(R.id.et_repeatpassword)
         val et_firstname = findViewById<EditText>(R.id.et_firstname)
         val et_familyname = findViewById<EditText>(R.id.et_familyname)

         return when {
             TextUtils.isEmpty(et_firstname.text.toString().trim { it <= ' ' }) -> {
                 showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                 false
             }

             TextUtils.isEmpty(et_familyname.text.toString().trim{it <= ' '}) -> {
                 showErrorSnackBar(resources.getString(R.string.err_msg_enter_family_name),true)
                 false
             }

             TextUtils.isEmpty(et_register_email.text.toString().trim{it <= ' '}) -> {
                 showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                 false
             }

             TextUtils.isEmpty(et_register_password.text.toString().trim{it <= ' '}) -> {
                 showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                 false
             }

             TextUtils.isEmpty(et_repeatpassword.text.toString().trim{it <= ' '}) -> {
                 showErrorSnackBar(resources.getString(R.string.err_msg_enter_password_again),true)
                 false
             }

             et_register_password.text.toString().trim{it<=' '} != et_repeatpassword.text.toString().trim{it<=' '} ->{
                 showErrorSnackBar(resources.getString(R.string.err_msg_password_not_match),true)
                 false
             }
             //check if terms and condition ischecked
              //!cb_terms_and_condition_isChecked -> { }

             else ->{
                 showErrorSnackBar("Your details are valid.",false)
                 true
             }

         }
     }

     fun RegisterUser() {
         if (validateRegisterDetails()) {

             showProgressDialog("Please Wait...")

             val et_register_email = findViewById<EditText>(R.id.et_email_login)
             val et_register_password = findViewById<EditText>(R.id.et_password_login)

             val email: String = et_register_email.text.toString().trim { it <= ' ' }
             val password: String = et_register_password.text.toString().trim { it <= ' ' }
             val et_firstname = findViewById<EditText>(R.id.et_firstname)
             val firstName: String = et_firstname.text.toString()

             //Create an instance and create a register a user with email and password
             auth = Firebase.auth
             auth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this) { task ->

                     if (task.isSuccessful) {
                         Log.d("ErikslustShop", "signInWithEmai:success")

                         val firebaseUser : FirebaseUser = task.result!!.user!!
                         val user = User(
                             firebaseUser.uid,
                             email,
                             firstName
                         )

                         FirestoreClass().registerUser(this@RegisterActivity, user)

                         val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                         showErrorSnackBar("You are registered successfully. Your user id is ${firebaseUser.uid}", false)
                         //signout after registration
                         auth.signOut()
                         finish()
                         //finish register sidan
                     } else {
                         hideProgressDialog()
                         showErrorSnackBar(task.exception!!.message.toString(),true)
                         Log.d("firebasedebug",task.exception!!.message.toString() )
                     }
                 }
         }
     }
     fun userRegisterSuccess(){
         hideProgressDialog()
         Toast.makeText(this@RegisterActivity, "You are registered successfully", Toast.LENGTH_SHORT).show()
         auth.signOut()
         finish()
     }
 }
