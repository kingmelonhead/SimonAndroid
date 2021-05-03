package com.example.simongame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simongame.persistence.ScoreContainer

class RecyclerAdapter(private val scoreList: List<ScoreContainer>) :
        RecyclerView.Adapter<RecyclerAdapter.ScoresViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ScoresViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.template,
                parent, false)
        return ScoresViewHolder(itemView)
    }
    override fun getItemCount() = scoreList.size

    override fun onBindViewHolder(holder: ScoresViewHolder, i: Int) {
        val currentItem = scoreList[i]
        holder.score.text = currentItem.score.toString()
        holder.difficulty.text = currentItem.difficulty
        holder.date.text = currentItem.date

    }
    class ScoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var score: TextView = itemView.findViewById(R.id.roundField)
        var difficulty: TextView = itemView.findViewById(R.id.difficultyField)
        var date: TextView = itemView.findViewById(R.id.dateField)
    }
}