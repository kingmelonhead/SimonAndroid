package com.example.simongame

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simongame.persistence.ScoreApplication
import com.example.simongame.persistence.ScoreContainer
import kotlinx.android.synthetic.main.activity_loss_screen.*
import java.time.LocalDate

class LossScreen : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.MainActivityViewModelFactory((application as ScoreApplication).repository)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loss_screen)
        backBtn.setOnClickListener(back)
        val data = intent.extras
        val difficulty = data?.getString("Difficulty")
        val score = data?.getInt("Score")
        Log.e("some stuff", "Difficulty: $difficulty")
        Log.e("some stuff", "score: ${score.toString()}")
        val databaseEntry = score?.let { ScoreContainer(score, difficulty.toString(), LocalDate.now().toString(), 0)  }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (viewModel.needsToUpdateDatabase){
            if (databaseEntry != null) {
                postUpdate(databaseEntry)
            }
            viewModel.needsToUpdateDatabase = false
        }
        viewModel.scoreList.observe(this){
            recyclerView.apply {
                adapter?.notifyDataSetChanged()
                adapter = RecyclerAdapter(it)}}
    }
    private fun postUpdate(container : ScoreContainer){
        viewModel.insert(container)
    }
    private val back = View.OnClickListener {
        //click listener for difficulty down listener
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}