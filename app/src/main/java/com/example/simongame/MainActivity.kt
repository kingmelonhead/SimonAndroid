package com.example.simongame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.simongame.persistence.ScoreApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.MainActivityViewModelFactory((application as ScoreApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diffUp.setOnClickListener(btnUp);
        diffDown.setOnClickListener(btnDown);
        btnStart.setOnClickListener(start)

        viewModel.difficultly.value == 0

        if (viewModel.difficultly.value == 0){
            diffText.text = "Easy"
        }
        else if (viewModel.difficultly.value  == 1){
            diffText.text = "Normal"
        }
        else {
            diffText.text = "Hard"
        }
    }
    private val btnDown = View.OnClickListener {
        //click listener for difficulty down listener
        decreaseDifficulty()
    }
    private val btnUp = View.OnClickListener {
        //click listener for difficulty up listener
        increaseDifficulty()
    }
    private val start = View.OnClickListener {
        //click listener for start game button
        startGame()
    }
    private fun increaseDifficulty(){
        //increases the difficulty
        if (viewModel.difficultly.value  == 0){
            viewModel.difficultly.value =1
            diffText.text = "Normal"
        }
        else if (viewModel.difficultly.value  == 1){
            viewModel.difficultly.value = 2
            diffText.text = "Hard"
        }
    }
    private fun decreaseDifficulty(){
        //decreases the difficulty
        if (viewModel.difficultly.value  == 1){
            viewModel.difficultly.value = 0
            diffText.text = "Easy"
        }
        else if (viewModel.difficultly.value  == 2){
            viewModel.difficultly.value = 1
            diffText.text = "Normal"
        }
    }
    private fun startGame(){
        //passes the difficulty to the game activity
        val data = Bundle()
        data.putInt("Difficulty", viewModel.difficultly.value!!)
        intent = Intent(this, GameScreen::class.java)
        intent.putExtras(data)
        startActivity(intent)
    }
}