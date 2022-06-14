package com.example.tasks.modules.person.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.core.Constants
import com.example.tasks.infra.SharedPreferencesHelper
import com.example.tasks.modules._shared.models.HeaderModel
import com.example.tasks.modules.person.repositories.PersonRepository
import com.example.tasks.modules._shared.services.ApiCallbackService
import com.example.tasks.modules._shared.services.ValidationService

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val mPersonRepository = PersonRepository(application)
    private val mPreferences = SharedPreferencesHelper(application)

    private val mCreate = MutableLiveData<ValidationService>()
    val create: LiveData<ValidationService> = mCreate

    fun create(name: String, email: String, password: String) {
        mPersonRepository.create(name, email, password, object : ApiCallbackService<HeaderModel> {
            override fun onSuccess(data: HeaderModel) {
                mPreferences.let {
                    it.store(Constants.SHAREDPREFERENCES.KEYS.TOKEN_KEY, data.token)
                    it.store(Constants.SHAREDPREFERENCES.KEYS.PERSON_KEY, data.personKey)
                    it.store(Constants.SHAREDPREFERENCES.KEYS.PERSON_NAME, data.name)
                }

                mCreate.value = ValidationService()
            }

            override fun onFailure(message: String) {
                mCreate.value = ValidationService(message)
            }
        })
    }

}