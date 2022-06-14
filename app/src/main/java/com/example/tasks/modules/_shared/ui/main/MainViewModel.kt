package com.example.tasks.modules._shared.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.core.Constants
import com.example.tasks.infra.SharedPreferencesHelper

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mPreferences = SharedPreferencesHelper(application)

    private val mUserName = MutableLiveData<String>()
    val userName: LiveData<String> = mUserName

    private val mLogout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = mLogout

    fun loadUserName() {
        mUserName.value = mPreferences.get(Constants.SHAREDPREFERENCES.KEYS.PERSON_NAME)
    }

    fun logout() {
        mPreferences.remove(Constants.SHAREDPREFERENCES.KEYS.PERSON_NAME)
        mPreferences.remove(Constants.SHAREDPREFERENCES.KEYS.PERSON_KEY)
        mPreferences.remove(Constants.SHAREDPREFERENCES.KEYS.TOKEN_KEY)

        mLogout.value = true
    }
}