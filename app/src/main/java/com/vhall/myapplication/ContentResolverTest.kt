package com.vhall.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.content.ContentValues
import android.util.Log


/**
 * @author hkl
 *Date: 2022/3/14 4:33 下午
 */
class ContentResolverTest {
    val uri: Uri = Uri.parse("content://com.vhall.myapplication.provider/table1")

    @SuppressLint("Range")
    fun get(context: Context): String {
        var uriText = ""
        val contentResolver = context.contentResolver
        //插入
//        var values = ContentValues()
//        values.put("title", "teaaxt")
//        contentResolver.insert(uri, values)

        //查找
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            while (it.moveToNext()) {
                val columnIndex = it.getColumnIndex("title")
                val string = it.getString(columnIndex)
               string?.apply {
                   uriText=uriText.plus(this)
               }
            }
        }
        return uriText
    }
}
