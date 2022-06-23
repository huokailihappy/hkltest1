package com.vhall.myapplication.myTEST.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.database.sqlite.SQLiteDatabase


/**
 * @author hkl
 *Date: 2022/3/14 5:01 下午
 */
class MyContentProvider: ContentProvider() {

    private  var db: SQLiteDatabase?=null

    override fun onCreate(): Boolean {
        var mySqlite: MySqLite = MySqLite(context!!)
        db = mySqlite.writableDatabase

        return db != null
    }
    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        return db?.query("student",projection,selection,selectionArgs,null,null,sortOrder)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        db?.insert("student",null,values)
        return uri
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    private val AUTHORITY = "com.vhall.myapplication.myTEST.provider"
    private val PATH = "table1"

    //匹配成功后的匹配码
    private val MATCH_CODE = 100

    //匹配不成功返回NO_MATCH(-1)
    private var uriMatcher: UriMatcher=UriMatcher(UriMatcher.NO_MATCH)

    //数据改变后指定通知的Uri
    private val NOTIFY_URI = Uri.parse("content://$AUTHORITY/$PATH")

    init {
        uriMatcher.addURI(AUTHORITY,"PATH", MATCH_CODE);
    }
}