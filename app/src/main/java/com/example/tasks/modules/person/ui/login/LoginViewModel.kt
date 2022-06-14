package com.example.tasks.modules.person.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.core.Constants
import com.example.tasks.infra.BiometricHelper
import com.example.tasks.infra.InternetClientHelper
import com.example.tasks.infra.SharedPreferencesHelper
import com.example.tasks.modules._shared.models.HeaderModel
import com.example.tasks.modules._shared.models.PriorityModel
import com.example.tasks.modules._shared.repositories.PriorityRepository
import com.example.tasks.modules._shared.services.ApiCallbackService
import com.example.tasks.modules.person.repositories.PersonRepository
import com.example.tasks.modules._shared.services.ValidationService

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val mPreferences = SharedPreferencesHelper(application)
    private val mPersonRepository = PersonRepository(application)
    private val mPriorityRepository = PriorityRepository(application)

    private val mLogin = MutableLiveData<ValidationService>()
    val login: LiveData<ValidationService> = mLogin

    private val mBiometyCheck = MutableLiveData<Boolean>()
    val biometyCheck: LiveData<Boolean> = mBiometyCheck

    fun doLogin(email: String, password: String) {
        mPersonRepository.login(email, password, object : ApiCallbackService<HeaderModel> {
            override fun onSuccess(data: HeaderModel) {
                mPreferences.let {
                    it.store(Constants.SHAREDPREFERENCES.KEYS.TOKEN_KEY, data.token)
                    it.store(Constants.SHAREDPREFERENCES.KEYS.PERSON_KEY, data.personKey)
                    it.store(Constants.SHAREDPREFERENCES.KEYS.PERSON_NAME, data.name)
                }
                InternetClientHelper.addHeaders(
                    data.token,
                    data.personKey
                )
                mLogin.value = ValidationService()
            }

            override fun onFailure(message: String) {
                mLogin.value = ValidationService(message)
            }
        })
    }


    fun isAuthenticationAvailable() {
        var token: String
        var personKey: String

        mPreferences.let {
            token = it.get(Constants.SHAREDPREFERENCES.KEYS.TOKEN_KEY)
            personKey = it.get(Constants.SHAREDPREFERENCES.KEYS.PERSON_KEY)
        }

        InternetClientHelper.addHeaders(
            token,
            personKey
        )

        val hasLoggedAlredy = (token != "" && personKey != "")
        if (!hasLoggedAlredy) {
            mPriorityRepository.getAllRomete(object : ApiCallbackService<List<PriorityModel>> {
                override fun onSuccess(data: List<PriorityModel>) {
                    mPriorityRepository.clear()
                    mPriorityRepository.save(data)
                }

                override fun onFailure(message: String) {}
            })
        }

        if (BiometricHelper.isBiometryAvailable(getApplication())) {
            mBiometyCheck.value = hasLoggedAlredy
        }
    }


}