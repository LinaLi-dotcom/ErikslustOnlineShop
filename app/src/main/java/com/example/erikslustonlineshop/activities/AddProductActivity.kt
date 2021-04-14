package com.example.erikslustonlineshop.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.erikslustonlineshop.databinding.ActivityAddProductBinding
import com.example.erikslustonlineshop.models.Products
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*

class AddProductActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    val REQUEST_GALLERY = 2

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

        binding.ivAddUpdateProduct.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }

        }
        binding.ivAddUpdateProductGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_GALLERY)

        }

        binding.btnSubmit.setOnClickListener {

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

    }

    fun saveToDatabase(){

        val productTitle = binding.etProductTitle.toString()
        val productDescription = binding. etProductDescription.toString()

        val productRef = database.getReference("products").child("addProduct").child(Firebase.auth.currentUser!!.uid).push()
        var newProduct = Products(productTitle, productDescription,imageFilename )

        productRef.setValue(newProduct)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val theImage = binding.IVProduct

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            selectedBitmap = data!!.extras!!.get("data") as Bitmap
            theImage.setImageBitmap(selectedBitmap)
            theImage.visibility = View.VISIBLE


        }

        if (requestCode == REQUEST_GALLERY && resultCode == AppCompatActivity.RESULT_OK){
            theImage.setImageURI(data?.data)
            theImage.visibility = View.VISIBLE


        }
    }
}
