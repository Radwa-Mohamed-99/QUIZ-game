package com.future.quizgame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.future.quizgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var player: MediaPlayer? = null
    var player2: MediaPlayer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.playerName.addTextChangedListener {
            binding.play.isEnabled = binding.playerName.text.isNotEmpty()
        }
        player= MediaPlayer.create(this,R.raw.game)
        player?.start()
        player?.isLooping = true

    }

    override fun onDestroy() {
        player?.stop()
        player2?.stop()
        super.onDestroy()
    }

    fun play(view: View) {

        player2= MediaPlayer.create(this,R.raw.gamestart)
        player2?.start()

        val name = binding.playerName.text.toString().lowercase()
        player?.stop()
        val a = Intent(this,Category::class.java)
        a.putExtra("name", name)
        startActivity(a)

    }
}