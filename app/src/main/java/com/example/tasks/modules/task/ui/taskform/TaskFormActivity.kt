package com.example.tasks.modules.task.ui.taskform

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.R
import androidx.lifecycle.Observer
import com.example.tasks.core.Constants
import com.example.tasks.modules._shared.ui.main.MainActivity
import com.example.tasks.modules.task.models.TaskModel
import kotlinx.android.synthetic.main.activity_register.button_save
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private val mCalendar = Calendar.getInstance()

    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    private val mPriorityIdList: MutableList<Int> = arrayListOf()

    private lateinit var mViewModel: TaskFormViewModel

    private var mTaskId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mViewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)

        // Inicializa eventos
        listeners()
        loadExtras()
        observe()

        mViewModel.listPriorities()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_save -> {
                handleSave()
            }
            R.id.button_date -> {
                handleShowDatePickerDialog()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = mCalendar
        date.set(year, month, dayOfMonth)
        button_date.text = mDateFormat.format(date.time)
    }

    private fun makeToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun listeners() {
        button_save.setOnClickListener(this)
        button_date.setOnClickListener(this)
    }

    private fun loadExtras() {
        val bundle = intent.extras
        if (bundle != null) {
            mTaskId = bundle.getInt(Constants.BUNDLE.TASKID)
            mViewModel.loadTask(mTaskId)
            button_save.text = getString(R.string.update_task)
        }
    }

    private fun observe() {
        mViewModel.priorityList.observe(this, Observer {
            val list: MutableList<String> = arrayListOf()
            for (item in it) {
                list.add(item.description)
                mPriorityIdList.add(item.id)
            }

            spinner_priority.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                list
            )
        })
        mViewModel.validation.observe(this, Observer {
            if (it.isSuccess()) {
                if (mTaskId == 0) {
                    makeToast(getString(R.string.task_created))
                } else {
                    makeToast(getString(R.string.task_updated))
                }
                finish()
            } else {
                makeToast(it.getMessage())
            }
        })
        mViewModel.task.observe(this, Observer {
            edit_description.setText(it.description)
            check_complete.isChecked = it.complete
            spinner_priority.setSelection(getPriorityIndex(it.priorityId))
            button_date.text = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .parse(it.dueDate)
                ?.let { dateParsed -> mDateFormat.format(dateParsed) }
        })
    }

    private fun handleShowDatePickerDialog() {
        DatePickerDialog(
            this,
            this,
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun handleSave() {
        val task = TaskModel().apply {
            this.id = mTaskId
            this.description = edit_description.text.toString()
            this.complete = check_complete.isChecked
            this.dueDate = button_date.text.toString()
            this.priorityId = mPriorityIdList[spinner_priority.selectedItemPosition]
        }

        mViewModel.save(task)
    }

    private fun getPriorityIndex(priorityId: Int): Int {
        var index = 0
        for (i in 0 until mPriorityIdList.count()) {
            if (mPriorityIdList[i] == priorityId) {
                index = i
                break
            }
        }

        return index
    }
}
