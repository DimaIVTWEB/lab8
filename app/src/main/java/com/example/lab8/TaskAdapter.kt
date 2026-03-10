package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<Task>()

    fun setTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvPriority: TextView = itemView.findViewById(R.id.tvPriority)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        private val cardView: CardView = itemView as CardView

        fun bind(task: Task) {
            tvTitle.text = task.title
            tvDescription.text = task.description
            tvPriority.text = when (task.priority) {
                Priority.HIGH -> "🔴 Высокий"
                Priority.MEDIUM -> "🟡 Средний"
                Priority.LOW -> "🟢 Низкий"
            }

            // Цвет карточки в зависимости от приоритета
            cardView.setCardBackgroundColor(
                when (task.priority) {
                    Priority.HIGH -> 0xFFFFEBEE.toInt()
                    Priority.MEDIUM -> 0xFFFFF8E1.toInt()
                    Priority.LOW -> 0xFFE8F5E9.toInt()
                }
            )

            btnDelete.setOnClickListener {
                onDeleteClick(task)
            }
        }
    }
}