package com.example.myandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroid.Bills
import com.example.myandroid.R

class BillAdapter(val context:Context,val billList:List<Bills>):RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val billAffair:TextView=view.findViewById(R.id.billAffair)
        val billExpense:TextView=view.findViewById(R.id.billExpense)
        val billTime:TextView=view.findViewById(R.id.billTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.bill_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill=billList[position]
        holder.billAffair.text=bill.affair
        holder.billExpense.text= bill.expense.toString()
        holder.billTime.text= bill.time.toString()
    }

    override fun getItemCount()=billList.size
}