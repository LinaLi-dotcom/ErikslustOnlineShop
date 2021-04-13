package com.example.erikslustonlineshop.activities

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.erikslustonlineshop.databinding.ActivityAddProductBinding
import com.example.erikslustonlineshop.models.Products
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    var selectedBitmap : Bitmap? = null
    var imageFilename: String? = null
    val storage = Firebase.storage
    val storageRef = storage.reference
    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.flProductImage.setOnClickListener {

           if(selectedBitmap != null) {
               val baos = ByteArrayOutputStream()
               selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
               val data = baos.toByteArray()

               imageFilename = UUID.randomUUID().toString() + ".jpg"

               val testimageRef = storageRef.child("productImages").child(imageFilename!!)
               Log.d("addProduct", "let's upload")
               var uploadTask = testimageRef.putBytes(data)
               uploadTask.addOnFailureListener {
                   Log.d("addProduct", "Upload fail")
               }.addOnSuccessListener {
                   Log.d("addProduct", "Upload ok")
                   saveToDatabase()
               }
               uploadTask.resume()
           } else {
               saveToDatabase()
           }

        }

        binding.btnSubmit.setOnClickListener {

        }

    }

    fun saveToDatabase(){

        val productTitle = binding.etProductTitle.toString()
        val productDescription = binding. etProductDescription.toString()

        val productRef = database.getReference("products").child("addProduct").child(Firebase.auth.currentUser!!.uid).push()
        var newProduct = Products(productTitle, productDescription,imageFilename )

        productRef.setValue(newProduct)

    }
}
