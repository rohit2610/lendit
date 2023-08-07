package com.example.lendit

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.lendit.databinding.ActivityUserDetailsAcitivityBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserDetailsAcitivity : AppCompatActivity() {

    private var binding: ActivityUserDetailsAcitivityBinding?= null
    private var transaction: ArrayList<Transactions> = arrayListOf()
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsAcitivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbUserActivity)

        var bundle = intent?.getBundleExtra("userData")
        var name = bundle?.getString("name")

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = name

        binding?.tbUserActivity?.setNavigationOnClickListener {
            finish()
        }

        binding?.tvUdName?.text = name?.uppercase()
        binding?.tvUdAmount?.text = "Amount: ₹${bundle?.getString("amount")}"
        binding?.tvUdInterest?.text = "Interest: ${bundle?.getString("interest")}%"
        binding?.tvUdMobile?.text = "Mobile: ${bundle?.getString("contact")}"
        binding?.tvUdStartDate?.text = "Loan start date: ${bundle?.getString("startDate")}"
        transaction = bundle?.getSerializable("transactions",) as ArrayList<Transactions>

        val adapter = TransactionsCustomAdaptor(transaction)

        binding?.rcvTxnList?.adapter = adapter

        binding?.floatingActionButton?.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.activity_add_date_modal, null)
            dialog.setContentView(view)
            dialog.show()

            val date =  view?.findViewById<TextView>(R.id.tv_add_date)


            date?.setOnClickListener {
                val cal = Calendar.getInstance()
                DatePickerDialog(
                    this,
                    { datePicker, year, month, dayOfMonth ->

                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, month)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        var sdf = SimpleDateFormat("dd/MM/yy", Locale.US)
                        date.text = sdf.format(cal.time)
                    },
                    cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            view?.findViewById<Button>(R.id.btn_add_date)?.setOnClickListener {
                val amountTef = view.findViewById<TextInputLayout>(R.id.tf_add_amount)
                amountTef?.helperText = checkIfAmountValid(view)
                date?.error = checkIfDateValid(view)

                val isAmountOk = amountTef?.helperText == null
                val isDateOk = date?.error == null

                if(isAmountOk && isDateOk){

//                    var data = mapOf(
//                        Transactions(1000,
//                                        "02/04/2023",
//                            "debited"
//                            ) to FieldValue.arrayUnion("transactions")
//                    )

                    var data = Transactions(
                        view.findViewById<TextInputEditText>(R.id.tef_add_amount).text.toString().toInt(),
                        date?.text.toString(),
                        "credited"
                    )

                    db.collection("users")
                        .document("1aJaMoYm7c1dIRAgJKMc")
                        .update(
                            "transactions",
                            FieldValue.arrayUnion(data)
                        )
                        .addOnSuccessListener {
                            dialog.dismiss()
                        }
                        .addOnFailureListener { error ->
                        }



                }

            }
        }

    }

    private fun checkIfDateValid(view: View): String? {

        var text = view.findViewById<TextView>(R.id.tv_add_date).text.toString()

        if(text == "Select Date"){
            return "Required"
        }

        return null
    }

    private fun checkIfAmountValid(view: View): String?{
        val text = view.findViewById<TextInputEditText>(R.id.tef_add_amount).text.toString()

        if(text == ""){
            return "Amount is required"
        }

        return null

    }
}