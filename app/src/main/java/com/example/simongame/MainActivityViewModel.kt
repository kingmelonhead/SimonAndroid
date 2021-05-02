package com.example.simongame

//IMPORTANT NOTE BELOW

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#15
//this was used as a resource for the database in case anything looks really similar to that page
//its because i had to use it for a lot of help

//Imports
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import com.example.simongame.persistence.GameObject
import com.example.simongame.persistence.Repo
import kotlinx.coroutines.launch

//the possible states the game may be in
//easier than remembering what value corresponds to each state
private const val START = 0
private const val RUNNING = 1
private const val PAUSED = 2

//difficulty macros
private const val EASY = 0
private const val MED = 1
private const val HARD = 2

class MainActivityViewModel (val repository: Repo): ViewModel() {

    //this can be private. It is only used by the view model
    private var array = MutableLiveData<MutableList<String>>()

    //various private variables
    private var timerThread : Timer? = null
    private var tempList = ArrayList<String>()
    private var clickTimer : Long = 0
    private var emphasizeTime : Long = 0
    private var counter : Int = 0  //functions as a replacement to a pointer
    private var depth : Int = 0 // used as iterator through the array list

    //used for the button timing
    private val timeHandler : Handler =  Handler(Looper.getMainLooper())

    //used as a pool for the random color generation
    private val colors = listOf("Red", "Blue", "Yellow", "Green")

    //these need to be observable so they were left public
    var difficultly = MutableLiveData<Int>()
    var state = MutableLiveData<Int>()
    var color = MutableLiveData<String>()
    var correct = MutableLiveData<Boolean>()
    var score = MutableLiveData<Int>()

    //used for the database
    val scores : LiveData<List<GameObject>> = repository.scores.asLiveData()

    init {
        difficultly.value = 0
        state.value = START
        score.value = 0
        correct.value = true
        array.value = arrayListOf()
        tempList = arrayListOf()
    }

    fun insert(score: GameObject) = viewModelScope.launch {
        repository.insert(score)
    }

    fun fetch() = viewModelScope.launch {
        repository.fetchScores()
    }


    fun initGame(){
        //generates the random array of colors used for the game
            when (difficultly.value) {
                EASY -> {
                    tempList.add(colors.random())
                    //player is given a long time between clicks
                    clickTimer = 3500
                    //the button is highlighted for longer before the round starts
                    emphasizeTime = 3000
                    array.value = tempList
                    Log.e("tag", "array : ${array.value}")
                }
                MED -> {
                    for (i in 1..3) {
                        tempList.add(colors.random())
                    }
                    //player is given slightly less time between button clicks
                    clickTimer = 1700
                    //button is highlighted for slightly less tiem now
                    emphasizeTime = 2700
                    array.value = tempList
                    Log.e("tag", "${array.value}")
                }
                HARD -> {
                    for (i in 1..5) {
                        tempList.add(colors.random())
                    }
                    //player is given minimal time between button clicks
                    clickTimer = 1000
                    //button is highlighted for minimal amount of time
                    emphasizeTime = 1000
                    array.value = tempList
                    Log.e("tag", "${array.value}")
                }
            }
    }

    private fun emphasizeButton(){
        //this "emphasizes" or highlights the current button that is being looked at
        //this is used when the array list is being shown before the player plays the game
        //is also used to flash the correct color in the case that the player hits the wrong button
        Log.e("tag", tempList[counter])
        timeHandler.post {
            color.value = tempList[counter]
        }
        timeHandler.postDelayed({

        }, 1200)
    }

    private fun startTimer(){
        //sets up the timer that monitors the button presses
        timerThread = Timer("name", false)
        timerThread?.schedule(clickTimer) {
            emphasizeButton()
        }

    }

    private fun showList(){
        //this is the function that emphasizes the correct sequence to the user prior to them playing

        //stops the timer
        timerThread?.cancel()
        timerThread = null
        if (state.value == RUNNING) {
            timeHandler.postDelayed( {
                tempList = array.value as ArrayList<String>
                if (depth < tempList.size) {
                    color.value = tempList[depth]
                    depth += 1
                    //recursively calls the function until the depth = the array size
                    showList()
                }
                else {
                    color.value = "flag"
                    state.value = PAUSED
                    startTimer()
                }
            }, 1700)
        }
        timeHandler.postDelayed( {
            color.value = "flag"
        }, emphasizeTime)
    }

    fun swapState(){
        //toggles the game between running and paused
        //paused is just time where the timer isnt counting against the player
        if (state.value == START){
            state.value = RUNNING
            depth = 0
            showList()
        }
        else if (state.value == PAUSED){
            state.value = RUNNING
            depth = 0
            showList()
        }
    }

    fun validateButton(btnColor : String){
        //this is the function that the game activity uses to communicate what button the user presses

        //stops the timer
        timerThread?.cancel()
        timerThread = null
        Log.e("tag", "Checking to see if $btnColor == ${tempList[counter]}")
        //checks to see if the button pressed matches the next color
        if (btnColor == tempList[counter]) {
            //if so increment score
            score.value = score.value?.plus(1)
            //and increment the value "pointing" to the next spot in the array
            counter += 1
            //call the timer to begin again
            startTimer()
        }
        else {
            //if not a match

            //then flash the correct color
            emphasizeButton()
            //set the condition variable to false so the observer will know that the game is to end
            correct.value = false
        }
        if (counter == tempList.size) {
            //the code will reach this point if the player completes the game

            //add a new element to the array
            tempList.add(colors.random())
            Log.e("tag", "appended array : $tempList")
            //point the live data to the modified temp list
            array.value = tempList
            //reset the "pointer"
            counter = 0
            swapState()
        }
    }

    class MainActivityViewModelFactory(val repository: Repo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
