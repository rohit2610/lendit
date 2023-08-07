package com.example.lendit

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lendit.databinding.TransactionCardBinding

class TransactionsCustomAdaptor(private val transactions: ArrayList<Transactions>): RecyclerView.Adapter<TransactionsCustomAdaptor.ViewHolder>() {


    inner class ViewHolder(private val binding: TransactionCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(txn: Transactions){

            binding.tvTxnAmount.text = "₹${txn.amount}"
            binding.tvTxnDate.text = txn.date
            val colorString = if (txn.type == "debited") "red" else "green"
            binding?.tvTxnAmount?.setTextColor( Color.parseColor(colorString))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionsCustomAdaptor.ViewHolder {
        return ViewHolder(
            TransactionCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionsCustomAdaptor.ViewHolder, position: Int) {
        val txn = transactions[position]
        holder.bindItem(txn)
    }

    override fun getItemCount(): Int {
        return  transactions.size
    }
}