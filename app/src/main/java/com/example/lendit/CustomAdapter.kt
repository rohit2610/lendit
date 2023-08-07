package com.example.lendit

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lendit.databinding.UserCardBinding

class CustomAdapter(private val userList: ArrayList<UserCardModel>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    inner class ViewHolder(val itemBinding: UserCardBinding):
        RecyclerView.ViewHolder(itemBinding.root)  {
            fun bindItem(userCardModel: UserCardModel){
                itemBinding?.tvCardUserName?.text = userCardModel?.userName
                itemBinding?.tvCardUserAmount?.text = "Amount: ${userCardModel?.amount.toString()}"
                itemBinding?.tvCardUserPhone?.text = "Contact: ${userCardModel?.contact}"


                itemBinding?.llUserCard?.setOnClickListener { view ->

                    var intent = Intent(view.context, UserDetailsAcitivity::class.java)
                    var bundle = Bundle()
                    bundle.putString("name", "${userCardModel.userName}")
                    bundle.putString("amount", "${userCardModel?.amount.toString()}" )
                    bundle.putString("contact", "${userCardModel?.contact}")
                    bundle.putString("interest", "${userCardModel?.interest}")
                    bundle.putString("startDate", "${userCardModel?.startDate}")
                    bundle.putSerializable("transactions", userCardModel.transactions)
                    intent.putExtra("userData", bundle)
                    ContextCompat.startActivity(view.context, intent, null)

                }
            }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UserCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userCard = userList[position]
        holder.bindItem(userCard)
    }

}