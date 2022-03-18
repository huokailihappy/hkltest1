package com.vhall.myapplication.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author hkl
 *Date: 2022/3/14 5:12 下午
 */
class MySqLite(context: Context) : SQLiteOpenHelper(context,"student",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table  student(id integer primary key autoincrement  ,title varchar(50) ,collect_num varchar(50) ,food_title varchar(50) ,type varchar(20) , pic varchar(100) , phone varchar(20))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}