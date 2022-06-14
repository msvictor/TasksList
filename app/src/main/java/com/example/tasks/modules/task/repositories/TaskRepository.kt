package com.example.tasks.modules.task.repositories

import android.content.Context
import com.example.tasks.R
import com.example.tasks.core.Constants
import com.example.tasks.infra.InternetClientHelper
import com.example.tasks.modules._shared.repositories.BaseRepository
import com.example.tasks.modules._shared.services.ApiCallbackService
import com.example.tasks.modules.task.models.TaskModel
import com.example.tasks.modules.task.services.TaskService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(context: Context) : BaseRepository(context) {
    private val mRemote = InternetClientHelper.createService(TaskService::class.java)

    fun getAll(listener: ApiCallbackService<List<TaskModel>>) {
        get(mRemote.getAll(), listener)
    }

    fun getNextWeek(listener: ApiCallbackService<List<TaskModel>>) {
        get(mRemote.getNextWeek(), listener)
    }

    fun getOverDue(listener: ApiCallbackService<List<TaskModel>>) {
        get(mRemote.getOverdue(), listener)
    }

    private fun get(call: Call<List<TaskModel>>, listener: ApiCallbackService<List<TaskModel>>) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
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

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure(
                    context.getString(
                        R.string.ERROR_UNEXPECTED
                    )
                )
            }

        })
    }

    fun getById(id: Int, listener: ApiCallbackService<TaskModel>) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        mRemote
            .getById(id)
            .enqueue(object : Callback<TaskModel> {
                override fun onResponse(call: Call<TaskModel>, response: Response<TaskModel>) {
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

                override fun onFailure(call: Call<TaskModel>, t: Throwable) {
                    listener.onFailure(
                        context.getString(
                            R.string.ERROR_UNEXPECTED
                        )
                    )
                }
            })
    }

    fun create(task: TaskModel, listener: ApiCallbackService<Boolean>) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        mRemote
            .create(
                dueDate = task.dueDate,
                complete = task.complete,
                priorityId = task.priorityId,
                description = task.description
            )
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    listener.onFailure(
                        context.getString(
                            R.string.ERROR_UNEXPECTED
                        )
                    )
                }
            })
    }

    fun update(task: TaskModel, listener: ApiCallbackService<Boolean>) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        mRemote
            .update(
                id = task.id,
                dueDate = task.dueDate,
                complete = task.complete,
                priorityId = task.priorityId,
                description = task.description
            )
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    listener.onFailure(
                        context.getString(
                            R.string.ERROR_UNEXPECTED
                        )
                    )
                }
            })
    }

    fun alterStatus(id: Int, isComplete: Boolean, listener: ApiCallbackService<Boolean>) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        val call: Call<Boolean> = if (isComplete) {
            mRemote.complete(id)
        } else {
            mRemote.undo(id)
        }

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(
                    context.getString(
                        R.string.ERROR_UNEXPECTED
                    )
                )
            }
        })
    }

    fun delete(id: Int, listener: ApiCallbackService<Boolean>) {
        if (!isConnectionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        mRemote
            .delete(id)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
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

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    listener.onFailure(
                        context.getString(
                            R.string.ERROR_UNEXPECTED
                        )
                    )
                }
            })
    }

}