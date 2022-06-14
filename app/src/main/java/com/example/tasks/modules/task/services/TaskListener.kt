package com.example.tasks.modules.task.services

interface TaskListener {
    /**
     * click to edit
     */
    fun onListClick(id: Int)

    /**
     * click to remove
     */
    fun onDeleteClick(id: Int)

    /**
     * click to comnplte
     */
    fun onCompleteClick(id: Int)

    /**
     * click to undo
     */
    fun onUndoClick(id: Int)

}