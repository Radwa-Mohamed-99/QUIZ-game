package com.future.quizgame

import android.content.ClipData
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import com.future.quizgame.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var player: MediaPlayer? = null
    var player2: MediaPlayer? = null
    var players = mutableListOf<Player>()
    lateinit var playerData:Player
    lateinit var file: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val retrievedData = getDataList()
        if (retrievedData.isNotEmpty()) {
            for (player in retrievedData) {
                players.add(player)
            }

        }
        binding.playerName.addTextChangedListener {
            binding.play.isEnabled = binding.playerName.text.isNotEmpty()
        }
        player = MediaPlayer.create(this, R.raw.game)
        player?.start()
        player?.isLooping = true

        var data = intent.getSerializableExtra("player")
        if(data != null)
        {
            playerData = data as Player
            saveDataList()

        }
      if (players.isNotEmpty()) {
          binding.ranking.isInvisible = false
      }

    }

    override fun onDestroy() {
        player?.stop()
        player2?.stop()
        super.onDestroy()
        saveDataList()

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
    fun ranked(view: View) {
        val d = Intent(this, RankedPlayers::class.java)
        d.putExtra("ranking", players.toTypedArray())
        startActivity(d)

    }

    fun saveDataList() {
        file = getSharedPreferences("allPlayers", MODE_PRIVATE)
        if (playerData.level >= 1 && ! players.any { playerData.name == it.name  }) {
            players.add(playerData)
        }
        else if (playerData.level >= 1 && players.any { playerData.name == it.name && playerData.level > it.level })
        {
            val dataPlayer =players.first{playerData.name == it.name && playerData.level > it.level }
            val index = players.indexOf(dataPlayer)
            players.removeAt(index)
            players.add(playerData)
        }
        val gson = Gson()
        val json = gson.toJson(players.toTypedArray())
        file.edit {
            putString("allPlayersData", json)
        }
    }

    fun getDataList(): Array<Player> {
        file = getSharedPreferences("allPlayers", MODE_PRIVATE)
        val gson = Gson()
        val json = file.getString("allPlayersData", null)
        val list= gson.fromJson(json, Array<Player>::class.java)
        if(!list.isNullOrEmpty())
        {
            return list
        }
        return emptyArray()
    }


}