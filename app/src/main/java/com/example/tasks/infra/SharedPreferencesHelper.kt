package com.example.tasks.infra

import android.content.Context
import android.content.SharedPreferences
import com.example.tasks.core.Constants

/**
 * Acesso a dados rápidos do projeto - SharedPreferences
 */
class SharedPreferencesHelper(context: Context) {

    private val mPreferences: SharedPreferences = context.getSharedPreferences(
        Constants.SHAREDPREFERENCES.NAME,
        Context.MODE_PRIVATE
    )

    fun store(key: String, value: String) {
        mPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun remove(key: String) {
        mPreferences
            .edit()
            .remove(key)
            .apply()
    }

    fun get(key: String): String {
        return mPreferences.getString(key, "") ?: ""
    }

}
