package com.example.tasks.modules.person.repositories

import android.content.Context
import com.example.tasks.R
import com.example.tasks.core.Constants
import com.example.tasks.infra.InternetClientHelper
import com.example.tasks.modules._shared.models.HeaderModel
import com.example.tasks.modules._shared.repositories.BaseRepository
import com.example.tasks.modules._shared.services.ApiCallbackService
import com.example.tasks.modules.person.services.PersonService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(context: Context) : BaseRepository(context) {
    private val mRemote = InternetClientHelper.createService(PersonService::class.java)

    fun login(email: String, password: String, listener: ApiCallbackService<HeaderModel>) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        mRemote
            .login(email, password)
            .enqueue(object : Callback<HeaderModel> {
                override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                    if (response.code() != Constants.API.HTTP.SUCCESS) {
                        listener.onFailure(
                            Gson().fromJson(
                                response.errorBody()!!.string(),
                                String::class.java
                            )
                        )
                    } else {
                        response.body()?.let {
                            listener.onSuccess(it)
                        }
                    }
                }

                override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                    listener.onFailure(
                        context.getString(
                            R.string.ERROR_UNEXPECTED
                        )
                    )
                }
            })
    }

    fun create(
        name: String,
        email: String,
        password: String,
        listener: ApiCallbackService<HeaderModel>
    ) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        mRemote
            .create(name, email, password, true)
            .enqueue(object : Callback<HeaderModel> {
                override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                    if (response.code() != Constants.API.HTTP.SUCCESS) {
                        listener.onFailure(
                            Gson().fromJson(
                                response.errorBody()!!.string(),
                                String::class.java
                            )
                        )
                    } else {
                        response.body()?.let {
                            listener.onSuccess(it)
                        }
                    }
                }

                override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                    listener.onFailure(
                        context.getString(
                            R.string.ERROR_UNEXPECTED
                        )
                    )
                }
            })
    }
}