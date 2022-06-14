package com.example.tasks.modules.task.ui.taskform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.modules._shared.models.HeaderModel
import com.example.tasks.modules._shared.models.PriorityModel
import com.example.tasks.modules._shared.repositories.PriorityRepository
import com.example.tasks.modules._shared.services.ApiCallbackService
import com.example.tasks.modules._shared.services.ValidationService
import com.example.tasks.modules.task.models.TaskModel
import com.example.tasks.modules.task.repositories.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {
    private val mPriorityRepository = PriorityRepository(application)
    private val mTaskRepository = TaskRepository(application)

    private val mPriorityList = MutableLiveData<List<PriorityModel>>()
    val priorityList: LiveData<List<PriorityModel>> = mPriorityList

    private val mValidation = MutableLiveData<ValidationService>()
    val validation: LiveData<ValidationService> = mValidation

    private val mTask = MutableLiveData<TaskModel>()
    val task: LiveData<TaskModel> = mTask

    private val onSaveCallback = object : ApiCallbackService<Boolean> {
        override fun onSuccess(data: Boolean) {
            mValidation.value = ValidationService()
        }

        override fun onFailure(message: String) {
            mValidation.value = ValidationService(message)
        }
    }

    fun listPriorities() {
        mPriorityList.value = mPriorityRepository.getAllLocal()
    }

    fun save(task: TaskModel) {
        if (task.id == 0) {
            mTaskRepository.create(task, onSaveCallback)
        } else {
            mTaskRepository.update(task, onSaveCallback)
        }
    }

    fun loadTask(taskId: Int) {
        mTaskRepository.getById(taskId, object : ApiCallbackService<TaskModel> {
            override fun onSuccess(data: TaskModel) {
                mTask.value = data
            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }
        })
    }
}