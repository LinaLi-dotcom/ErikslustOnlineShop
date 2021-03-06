package com.example.erikslustonlineshop.activities

import android.app.Dialog
import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import com.example.erikslustonlineshop.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_progress.*

open class BaseActivity : AppCompatActivity() {

    //a progress dialog which will be initialized later on
    private lateinit var mProgressDialog:Dialog

    fun showErrorSnackBar(message: String, errorMessage:Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),
        message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if(errorMessage){
            snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                            this@BaseActivity,
                            R.color.colorSnackBarError
                    )
            )
        }else{
            snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                            this@BaseActivity,
                            R.color.colorSnackBarSuccess
                    )
            )
        }
        snackBar.show()
    }

    fun showProgressDialog(text:String){
        mProgressDialog = Dialog(this)
        //set the screen content from a layout resourcee
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.tv_progress_text.text = text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

}