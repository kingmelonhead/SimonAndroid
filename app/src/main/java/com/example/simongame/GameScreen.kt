package com.example.simongame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.simongame.persistence.Application
import kotlinx.android.synthetic.main.activity_game_screen.*

private const val START = 0
private const val RUNNING = 1
private const val PAUSED = 2

private const val EASY = 0
private const val MED = 1
private const val HARD = 2


class GameScreen : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModel.MainActivityViewModelFactory((application as Application).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        val data = intent.extras
        if (data != null){
            viewModel.difficultly.value = data.getInt("Difficulty")
            Log.e("Tag","Difficulty is being set :  ${ viewModel.difficultly.value}")
        }


        viewModel.initGame()
        initObservers()
        initButtons()
    }

    private fun initObservers(){
        viewModel.state.observe(this) { refreshButtons() }
        viewModel.color.observe(this) { updateColor() }
        viewModel.correct.observe(this) { loss()}
        viewModel.score.observe(this) { refreshScore() }
    }

    private fun initButtons(){
        redButt.setOnClickListener(){
            viewModel.validateButton("Red")
        }
        blueButt.setOnClickListener(){
            viewModel.validateButton("Blue")
        }
        greenButt.setOnClickListener(){
            viewModel.validateButton("Green")
        }
        yellowButt.setOnClickListener(){
            viewModel.validateButton("Yellow")
        }
        startButt.setOnClickListener(){
            viewModel.swapState()
        }
    }

    private fun refreshButtons() {
        when (viewModel.state.value){
            START->{
                greenButt.isEnabled = false
                blueButt.isEnabled = false
                yellowButt.isEnabled = false
                redButt.isEnabled = false
                startButt.isEnabled = true
            }
            RUNNING->{
                greenButt.isEnabled = false
                blueButt.isEnabled = false
                yellowButt.isEnabled = false
                redButt.isEnabled = false
                startButt.isEnabled = false
            }
            PAUSED->{
                greenButt.isEnabled = true
                blueButt.isEnabled = true
                yellowButt.isEnabled = true
                redButt.isEnabled = true
                startButt.isEnabled = false
            }
        }
    }
    private fun updateColor(){
        when(viewModel.color.value){
            "Red"->{
                redButt.setBackgroundResource(R.drawable.red_outline)
            }
            "Blue"->{
                blueButt.setBackgroundResource(R.drawable.blue_outline)
            }
            "Green"->{
                greenButt.setBackgroundResource(R.drawable.green_outline)
            }
            "Yellow"->{
                yellowButt.setBackgroundResource(R.drawable.yellow_outline)
            }
            "flag"->{
                blueButt.setBackgroundResource(R.drawable.blue)
                redButt.setBackgroundResource(R.drawable.red)
                greenButt.setBackgroundResource(R.drawable.green)
                yellowButt.setBackgroundResource(R.drawable.yellow)
            }
        }
    }
    private fun loss() {
        if (viewModel.correct.value == false) {
            val temp = viewModel.score.value
            if (temp != null) {

            }
            var difficulty : String = ""
            when (viewModel.difficultly.value){
                EASY->{
                    difficulty = "Easy"
                }
                MED->{
                    difficulty = "Medium"
                }
                HARD->{
                    difficulty = "Hard"
                }
            }
            val gamedata = Bundle()
            gamedata.putString("Difficulty", difficulty)
            intent = Intent(this, LossScreen::class.java)
            intent.putExtras(gamedata)
            startActivity(intent)
        }
    }
    private fun refreshScore(){
        scoreBox.text = viewModel.score.value.toString()
    }
}