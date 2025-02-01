package com.future.quizgame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.future.quizgame.databinding.ActivityRankedPlayersBinding
import com.xwray.groupie.GroupieAdapter

class RankedPlayers : AppCompatActivity() {
    lateinit var binding: ActivityRankedPlayersBinding
    lateinit var adapter: GroupieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityRankedPlayersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapter = GroupieAdapter()
        val data = intent.getSerializableExtra("ranking") as Array<Player>

        if(data.isNotEmpty())
        {
            val  items = data.filter { it.level > 0 }.sortedByDescending { it.level }.map { PlayerRank(it) }
            adapter.addAll(items)
            binding.ranking.adapter=adapter

        }





    }


}