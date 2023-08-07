package com.example.lendit

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lendit.databinding.ActivityAddFormBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFormActivity : AppCompatActivity() {

    private var binding: ActivityAddFormBinding ?= null

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFormBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbForm)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.tbForm?.setNavigationOnClickListener {
            finish()
        }


        binding?.btnSubmitForm?.setOnClickListener {
            onSubmit()
        }


        binding?.tvDate?.setOnClickListener { view ->
            openDatePicker()
        }

    }

    private fun openDatePicker() {

        var cal = Calendar.getInstance()

        DatePickerDialog(
            this,
            { datePicker, year, month, dayOfMonth ->

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                var sdf = SimpleDateFormat("dd/MM/yy", Locale.US)
                binding?.tvDate?.text = sdf.format(cal.time)

            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)

        ).show()


    }

    private fun onSubmit(){
        binding?.tfName?.helperText = validateName()
        binding?.tfPhone?.helperText = validateNumber()
        binding?.tfAmount?.helperText = validateAmount()
        binding?.tfInterest?.helperText = validateInterest()
        binding?.tvDate?.error = validateDate()

        val isNumberOk =  binding?.tfPhone?.helperText == null
        val isNameOk = binding?.tfName?.helperText == null
        val isAmountOk = binding?.tfAmount?.helperText == null
        val isInterestOk = binding?.tfInterest?.helperText == null
        val isDateOk = binding?.tvDate?.error == null



        if(isNumberOk && isNameOk && isAmountOk && isInterestOk && isDateOk){


            val userDetail = UserCardModel(
                binding?.tefName?.text.toString(),
                binding?.tefPhone?.text.toString(),
                binding?.tefAmount?.text.toString().toInt(),
                binding?.tefInterest?.text.toString().toInt(),
                binding?.tvDate?.text.toString(),
                arrayListOf<Transactions>(
                    Transactions(
                        binding?.tefAmount?.text.toString().toInt(),
                        binding?.tvDate?.text.toString(),
                        "debited"
                    )
                )
            )

            db.collection("users")
                .add(userDetail)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Added user data successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "User data not added. Please try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun validateDate(): String? {
        var text = binding?.tvDate?.text;

        if(text == "Select Date"){
            return "Date is required"
        }

        return null
    }

    private fun validateAmount(): String? {

        var amount = binding?.tefAmount?.text.toString()
        if(amount.isEmpty()){
            return "Enter valid amount"
        }

        return null
    }

    private fun validateInterest(): String? {
        var amount = binding?.tefInterest?.text.toString()
        if(amount.isEmpty()){
            return "Enter valid interest"
        }

        return null
    }

    private fun validateNumber(): String?{
        var phoneNumber = binding?.tefPhone?.text.toString()
        if(phoneNumber.length != 10){
            return "Enter a valid number"
        }
        return null
    }

    private fun validateName(): String? {

        var name = binding?.tefName?.text.toString()
        if(name.length == 0){
            return "Please enter name"
        }

        return null;
    }
}