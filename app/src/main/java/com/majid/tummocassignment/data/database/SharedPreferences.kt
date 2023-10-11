package com.majid.tummocassignment.data.database

import android.content.Context
import com.majid.tummocassignment.app.App


class SharedPreferences {

    companion object {
        var instance: SharedPreferences? = null
        var sp: android.content.SharedPreferences? = null


        @JvmName("getInstance1")
        fun getInstance(): SharedPreferences {
            if (instance == null) {
                instance = SharedPreferences()


            }
            sp = App.context.getSharedPreferences(DBDEFINITIONS.KEY_PREF_NAME, Context.MODE_PRIVATE)
            return instance!!
        }


    }


    /**
     *  This Section Can be used to store Boolean type  KEY-VALUE pair
     * **/
    fun setBoolean(key: String, value: Boolean) {
        val editor = sp?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
        editor?.commit()
    }

    fun getBoolean(key: String): Boolean {
        return sp!!.getBoolean(key, false)
    }


}