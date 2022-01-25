package com.example.myandroid.ui

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myandroid.adapter.BillAdapter
import com.example.myandroid.Bills
import com.example.myandroid.MyDatabaseHelper
import com.example.myandroid.R
import kotlinx.android.synthetic.main.activity_main.*
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val billList=ArrayList<Bills>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SQLiteStudioService.instance().start(this);
        setSupportActionBar(toolbar)

        //主页面
        initBills()
        val layoutManager=GridLayoutManager(this,1)
        main_recycler.layoutManager=layoutManager
        val adapter= BillAdapter(this,billList)
        main_recycler.adapter=adapter


        fab.setOnClickListener {
            val dbHelper= MyDatabaseHelper(this,"Bill.db",1)
            dbHelper.writableDatabase
        }

        bar_history.setOnClickListener {
            val dbHelper= MyDatabaseHelper(this,"Bill.db",1)
            val db =dbHelper.writableDatabase
            val values1=ContentValues().apply {
                put("time",1.16)
                put("expense",30)
                put("affair","吃饭")
            }
            db.insert("Bill",null,values1)
            val values2=ContentValues().apply {
                put("time",1.17)
                put("expense",150)
                put("affair","购物")
            }
            db.insert("Bill",null,values2)
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show()
        }

        //下拉刷新
        swipeRefresh.setColorSchemeResources(R.color.cardview_dark_background)
        swipeRefresh.setOnRefreshListener {
            refresh(adapter)
        }
    }

    private fun initBills(){
        billList.clear()
        val dbHelper= MyDatabaseHelper(this,"Bill.db",1)
        val db =dbHelper.writableDatabase
        billList.addAll(dbHelper.getAll(db))
    }

    private fun refresh(adapter: BillAdapter){
        thread {
            Thread.sleep(1000)
            runOnUiThread{
                initBills()
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing=false
            }
        }
    }
}