package com.example.lendit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lendit.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.reflect.typeOf


//var list = listOf<UserCardModel>(
//    UserCardModel(
//        userName = "Rohit",
//        contact = "9898483843",
//        amount = 100,
//        interest = 10),
//    UserCardModel(
//        userName = "Munjal",
//        contact = "9898483",
//        amount = 10043433,
//        interest = 12
//    )
//)

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getFirebaseData()


        binding?.flAddButton?.setOnClickListener {
            val intent = Intent(this, AddFormActivity::class.java)
            startActivity(intent)
        }

        binding?.tvSummary?.setOnClickListener {
            val intent = Intent(this, Summary::class.java)
            startActivity(intent)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    private fun getFirebaseData(){
        db.collection("users")
            .addSnapshotListener { snapshots, error ->

                var dataList = arrayListOf<UserCardModel>()
                for(docs in snapshots!!.documents){
                    var user = docs.toObject(UserCardModel::class.java)

                    if (user != null) {
                       dataList.add(user)
                    }
                }
                val adapter = CustomAdapter(dataList)
                binding?.rcvList?.adapter = adapter

            }
    }
}