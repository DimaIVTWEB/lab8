package com.example.lab8

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter
    private val tasksList = mutableListOf<Task>()

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var btnAddTask: Button
    private lateinit var rvTasks: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // ← Сначала setContentView!

        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        spinnerPriority = findViewById(R.id.spinnerPriority)
        btnAddTask = findViewById(R.id.btnAddTask)
        rvTasks = findViewById(R.id.rvTasks)

        adapter = TaskAdapter { task ->
            deleteTask(task)
        }
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter

        setupPrioritySpinner()

        btnAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun setupPrioritySpinner() {
        val priorities = arrayOf("Низкий", "Средний", "Высокий")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter
    }

    private fun addTask() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val priorityIndex = spinnerPriority.selectedItemPosition

        if (title.isEmpty()) {
            Toast.makeText(this, "Введите название задачи", Toast.LENGTH_SHORT).show()
            return
        }

        val priority = when (priorityIndex) {
            0 -> Priority.LOW
            1 -> Priority.MEDIUM
            2 -> Priority.HIGH
            else -> Priority.MEDIUM
        }

        val task = Task(
            id = tasksList.size + 1,
            title = title,
            description = description,
            priority = priority
        )

        tasksList.add(task)
        adapter.setTasks(tasksList)
        
        etTitle.text.clear()
        etDescription.text.clear()
        spinnerPriority.setSelection(0)

        Toast.makeText(this, "Задача добавлена", Toast.LENGTH_SHORT).show()
    }

    private fun deleteTask(task: Task) {
        tasksList.remove(task)
        adapter.setTasks(tasksList)
        Toast.makeText(this, "Задача удалена", Toast.LENGTH_SHORT).show()
    }
}