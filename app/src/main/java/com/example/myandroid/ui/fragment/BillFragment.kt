package com.example.myandroid.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroid.Bills
import com.example.myandroid.MyDatabaseHelper
import com.example.myandroid.R
import kotlinx.android.synthetic.main.fragment_bill.*
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService

class BillFragment : Fragment() {

    val billList=ArrayList<Bills>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bill, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SQLiteStudioService.instance().start(activity);
        initBills()
        val layoutManager = GridLayoutManager(activity,1)
        main_recycler.layoutManager=layoutManager
        val adapter = BillAdapter(billList)
        main_recycler.adapter=adapter
    }

    // 内部适配器
    inner class BillAdapter(val billsList: List<Bills>) : RecyclerView.Adapter<BillAdapter.ViewHolder>(){
        inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
            val billAffair: TextView =view.findViewById(R.id.billAffair)
            val billExpense: TextView =view.findViewById(R.id.billExpense)
            val billTime: TextView =view.findViewById(R.id.billTime)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view=LayoutInflater.from(parent.context).inflate(R.layout.bill_layout,parent,false)
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

    private fun initBills(){
        billList.clear()
        val dbHelper= this.activity?.let { MyDatabaseHelper(it,"Bill.db",1) }
        val db = dbHelper?.writableDatabase
        if (dbHelper != null) {
            db?.let { dbHelper.getAll(it) }?.let { billList.addAll(it) }
        }
    }

    fun refresh(){
        initBills()
        val layoutManager = GridLayoutManager(activity,1)
        main_recycler.layoutManager=layoutManager
        val adapter = BillAdapter(billList)
        main_recycler.adapter=adapter
    }
}