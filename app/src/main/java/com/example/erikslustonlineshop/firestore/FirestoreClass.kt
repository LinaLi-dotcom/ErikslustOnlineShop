package com.example.erikslustonlineshop.firestore

import android.util.Log
import com.example.erikslustonlineshop.activities.RegisterActivity
import com.example.erikslustonlineshop.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FirestoreClass {

    //private val mFireStore = FirebaseFirestore.getInstance()
    private val dbFireStore = Firebase.firestore

    fun registerUser(activity: RegisterActivity, userInfo: User){

        dbFireStore.collection("users")
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot added with ID: ${userInfo.id}")
            }
            .addOnFailureListener { e->
                Log.w("Firestore", "Eroor adding document",e)
            }




    }

}