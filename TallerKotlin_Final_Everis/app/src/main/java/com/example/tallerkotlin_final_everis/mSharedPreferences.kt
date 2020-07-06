package com.example.tallerkotlin_final_everis

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class mSharedPreferences {
    var context : Context
    var sharedPreferences : SharedPreferences
    val editor : SharedPreferences.Editor

    constructor(context: Context) {
        this.context = context
        this.sharedPreferences = context.getSharedPreferences("CURSO_KOTLIN", Context.MODE_PRIVATE)
        this.editor = this.sharedPreferences.edit()
    }
    fun put(key:String, value : String) {
        Log.e("mysharedP$key",value)
        this.editor.putString(key, value)
    }
    fun save() {
        this.editor.apply()
    }

    fun getKey(key: String) : String? {
        return this.sharedPreferences.getString(key, "none")
    }
}