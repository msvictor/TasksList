package com.example.tasks.modules.task.ui.tasksfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.core.Constants
import com.example.tasks.modules.task.services.TaskListener
import com.example.tasks.modules.task.services.TaskAdapter
import com.example.tasks.modules.task.ui.taskform.TaskFormActivity

class TasksFragment : Fragment() {
    private val mAdapter = TaskAdapter()

    private lateinit var mViewModel: TasksFragmentViewModel
    private lateinit var mListener: TaskListener
    private var mFilter = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        mViewModel = ViewModelProvider(this).get(TasksFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)

        mFilter = requireArguments().getInt(Constants.BUNDLE.TASKFILTER, Constants.FILTER.ALL)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_tasks)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        // Eventos disparados ao clicar nas linhas da RecyclerView
        mListener = object : TaskListener {
            override fun onListClick(id: Int) {
                val intent = Intent(context, TaskFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(Constants.BUNDLE.TASKID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onCompleteClick(id: Int) {
                mViewModel.onComplete(id, mFilter)
            }

            override fun onUndoClick(id: Int) {
                mViewModel.onUndo(id, mFilter)
            }

            override fun onDeleteClick(id: Int) {
                mViewModel.onDelete(id, mFilter)
            }

        }

        // Cria os observadores
        observe()

        // Retorna view
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.load(mFilter)
    }

    private fun observe() {
        mViewModel.taskList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                mAdapter.updateList(it)
            }
        })
        mViewModel.validation.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess()) {
                Toast
                    .makeText(
                        context,
                        getString(R.string.task_removed),
                        Toast.LENGTH_SHORT
                    )
                    .show()
            } else {
                Toast
                    .makeText(
                        context,
                        it.getMessage(),
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        })
    }
}
