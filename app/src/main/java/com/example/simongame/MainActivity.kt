package com.example.simongame

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.example.simongame.databinding.ActivityMainBinding
import com.example.simongame.persistence.Application
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.MainActivityViewModelFactory((application as Application).repository)
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