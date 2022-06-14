package com.example.tasks.modules.task.services

import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.modules._shared.repositories.PriorityRepository
import com.example.tasks.modules.task.models.TaskModel
import java.text.SimpleDateFormat
import java.util.Locale

class TaskViewHolder(itemView: View, private val listener: TaskListener) :
    RecyclerView.ViewHolder(itemView) {

    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    private val mPriorityRepository = PriorityRepository(itemView.context)

    private var mTextDescription: TextView = itemView.findViewById(R.id.text_description)
    private var mTextPriority: TextView = itemView.findViewById(R.id.text_priority)
    private var mTextDueDate: TextView = itemView.findViewById(R.id.text_due_date)
    private var mImageTask: ImageView = itemView.findViewById(R.id.image_task)

    /**
     * assing values to interfaces and events elements
     */
    fun bindData(task: TaskModel) {
        this.mTextDescription.text = task.description
        this.mTextPriority.text = mPriorityRepository.getDescriptionById(task.priorityId)
        this.mTextDueDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .parse(task.dueDate)
            ?.let { mDateFormat.format(it) }
        if (task.complete) {
            mTextDescription.setTextColor(Color.GRAY)
            mTextPriority.setTextColor(Color.GRAY)
            mImageTask.setImageResource(R.drawable.ic_done)
        } else {
            mTextDescription.setTextColor(Color.BLACK)
            mTextPriority.setTextColor(Color.BLACK)
            mImageTask.setImageResource(R.drawable.ic_todo)
        }

        // -> Events
        mTextDescription.setOnClickListener { listener.onListClick(task.id) }
        mImageTask.setOnClickListener {
            if (task.complete) {
                listener.onUndoClick(task.id)
            } else {
                listener.onCompleteClick(task.id)
            }
        }

        mTextDescription.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_de_tarefa)
                .setMessage(R.string.remover_tarefa)
                .setPositiveButton(R.string.sim) { _, _ ->
                    listener.onDeleteClick(task.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()
            true
        }

    }

}