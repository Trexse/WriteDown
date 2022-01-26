package com.example.myandroid.ui

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myandroid.MyDatabaseHelper
import com.example.myandroid.R
import com.example.myandroid.ui.fragment.BillFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                put("affair","买东西")
            }
            db.insert("Bill",null,values1)
            val values2=ContentValues().apply {
                put("time",1.17)
                put("expense",300)
                put("affair","刮彩票")
            }
            db.insert("Bill",null,values2)
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show()
        }

        //下拉刷新
        val mMainNavFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)
        val fragment = mMainNavFragment?.childFragmentManager?.primaryNavigationFragment
        swipeRefresh.setColorSchemeResources(R.color.cardview_dark_background)
        swipeRefresh.setOnRefreshListener {
            if(fragment is BillFragment) refresh(fragment)
            else {
                thread {
                    Thread.sleep(1000)
                    runOnUiThread{
                        swipeRefresh.isRefreshing=false
                    }
                }
            }
        }
    }

    private fun refresh(fragment: BillFragment){
        thread {
            Thread.sleep(1000)
            runOnUiThread{
                fragment.refresh()
                swipeRefresh.isRefreshing=false
            }
        }
    }
}