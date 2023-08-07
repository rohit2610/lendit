package com.example.lendit

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lendit.databinding.ActivityAddDateModalBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddDateModal : AppCompatActivity() {

    private var binding: ActivityAddDateModalBinding?= null
    private val dialog = BottomSheetDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDateModalBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnAddDate?.setOnClickListener { view ->
            submitData()
        }

    }

    private fun submitData(){
        Toast.makeText(
            this,
            "PResses",
            Toast.LENGTH_SHORT
        ).show()
        dialog.dismiss()

    }
}