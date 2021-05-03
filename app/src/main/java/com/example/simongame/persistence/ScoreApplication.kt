package com.example.simongame.persistence

//IMPORTANT NOTE BELOW

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#15
//this was used as a resource for the database in case anything looks really similar to that page
//its because i had to use it for a lot of help


import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ScoreApplication: Application() {
    //i got this layout template from the resource above
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ScoreDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { SimonRepo(database.simonDao()) }
}