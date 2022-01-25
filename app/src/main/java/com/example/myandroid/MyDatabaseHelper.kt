package com.example.myandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context:Context,name:String,version:Int):SQLiteOpenHelper(context,name,null,version) {
    private val createBill="create table Bill ("+
            "id integer primary key autoincrement,"+
            "time real,"+
            "expense real,"+
            "affair text)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBill)
        Toast.makeText(context,"Create succeed",Toast.LENGTH_LONG).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun getAll(db: SQLiteDatabase):ArrayList<Bills>{
        var arrayList=ArrayList<Bills>()
        val cursor=db.query("Bill",null,null,null,null,null,null)
        if (cursor.moveToFirst()){
            do {
                val expense=cursor.getLong(cursor.getColumnIndex("expense"))
                val affair=cursor.getString(cursor.getColumnIndex("affair"))
                val time=cursor.getLong(cursor.getColumnIndex("time"))
                val bill=Bills(time, expense, affair)
                arrayList.add(bill)
            }while (cursor.moveToNext())
        }
        return arrayList
    }

    fun recordBill(dbHelper: MyDatabaseHelper):Boolean{
        return true
    }
}