package com.example.tasks.modules.task.ui.tasksfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.core.Constants
import com.example.tasks.modules._shared.services.ApiCallbackService
import com.example.tasks.modules._shared.services.ValidationService
import com.example.tasks.modules.task.models.TaskModel
import com.example.tasks.modules.task.repositories.TaskRepository

class TasksFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val mTaskRepository = TaskRepository(application)

    private val mTaskList = MutableLiveData<List<TaskModel>>()
    val taskList: LiveData<List<TaskModel>> = mTaskList

    private val mValidation = MutableLiveData<ValidationService>()
    val validation: LiveData<ValidationService> = mValidation


    fun load(filter: Int) {
        val listener = object : ApiCallbackService<List<TaskModel>> {
            override fun onSuccess(data: List<TaskModel>) {
                mTaskList.value = data
            }

            override fun onFailure(message: String) {
                mTaskList.value = arrayListOf()
                mValidation.value = ValidationService(message)

            }
        }

        when (filter) {
            Constants.FILTER.EXPIRED -> mTaskRepository.getOverDue(listener)
            Constants.FILTER.NEXT -> mTaskRepository.getNextWeek(listener)
            Constants.FILTER.ALL -> mTaskRepository.getAll(listener)
            else -> mTaskRepository.getAll(listener)
        }
    }

    private fun onAlterStatus(taskId: Int, isComplete: Boolean, filter: Int) {
        mTaskRepository.alterStatus(taskId, isComplete, object : ApiCallbackService<Boolean> {
            override fun onSuccess(data: Boolean) {
                mValidation.value = ValidationService()
                load(filter)
            }

            override fun onFailure(message: String) {
                mValidation.value = ValidationService(message)
            }
        })
    }

    fun onComplete(taskId: Int, filter: Int) {
        onAlterStatus(taskId, true, filter)

    }

    fun onUndo(taskId: Int, filter: Int) {
        onAlterStatus(taskId, false, filter)
    }

    fun onDelete(taskId: Int, filter: Int) {
        mTaskRepository.delete(taskId, object : ApiCallbackService<Boolean> {
            override fun onSuccess(data: Boolean) {
                mValidation.value = ValidationService()
                load(filter)
            }

            override fun onFailure(message: String) {
                mValidation.value = ValidationService(message)
            }
        })
    }
}