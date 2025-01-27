package com.future.quizgame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.future.quizgame.databinding.ActivityCategoryBinding
import com.future.quizgame.databinding.ActivityMainBinding

class Category : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    val dataPassed= mutableListOf<String>()
    var mediaPlayer:MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = intent.getStringExtra("name")
        if(name != null)
        {
            dataPassed.add(name)
        }
    }

    fun scienceClick(view: View) {
        startGame("17")
    }
    fun geographyClick(view: View) {
        startGame("22")
    }
    fun booksClick(view: View) {
       startGame("10")
    }
    fun animalsClick(view: View) {
        startGame("27")
    }
    fun filmClick(view: View) {
        startGame("11")
    }
    fun sportsClick(view: View) {
        startGame("21")
    }

    private fun startGame(categories:String) {
        mediaPlayer=MediaPlayer.create(this,R.raw.clicknote)
        mediaPlayer?.start()
        dataPassed.add(categories)
        val b = Intent(this, GameWindow::class.java)
        b.putExtra("player", dataPassed.toTypedArray())
        startActivity(b)
    }
}