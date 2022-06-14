package com.example.tasks.modules._shared.repositories

import android.content.Context
import com.example.tasks.R
import com.example.tasks.core.Constants
import com.example.tasks.infra.DatabaseHelper
import com.example.tasks.infra.InternetClientHelper
import com.example.tasks.modules._shared.models.HeaderModel
import com.example.tasks.modules._shared.models.PriorityModel
import com.example.tasks.modules._shared.services.ApiCallbackService
import com.example.tasks.modules._shared.services.PriorityService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(context: Context) : BaseRepository(context) {
    private val mRemote = InternetClientHelper.createService(PriorityService::class.java)
    private val mDatabase = DatabaseHelper.getDatabase(context).priorityDAO()

    fun getAllRomete(listener: ApiCallbackService<List<PriorityModel>>) {
        if (!isConnectionAvailable()) {
            return
        }

        mRemote.getAll().enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
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

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                listener.onFailure(
                    context.getString(
                        R.string.ERROR_UNEXPECTED
                    )
                )
            }
        })
    }


    fun getAllLocal() = mDatabase.getAll()

    fun getDescriptionById(id: Int) = mDatabase.getDescriptionById(id)

    fun save(priorities: List<PriorityModel>) = mDatabase.save(priorities)

    fun clear() = mDatabase.clear()
}