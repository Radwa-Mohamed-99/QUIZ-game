package com.future.quizgame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import com.future.quizgame.databinding.ActivityAchievementBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Achievement : AppCompatActivity() {
    lateinit var binding: ActivityAchievementBinding
    lateinit var playerData: Player
    private var mediaPlayer: MediaPlayer? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val data = intent.getSerializableExtra("playerData")
        if (data != null) {
            playerData = data as Player
            Log.d("Achievement", "Level: ${playerData.level}, Score: ${playerData.score}")
                if (playerData.level == 0 && playerData.score > 2) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.win)
                    mediaPlayer?.start()
                    binding.badge.isInvisible = false
                    binding.badge.setBackgroundResource(R.drawable.start)
                    binding.badgeName.text = "ROCKET \n "
                    binding.level.text = "You got ROCKET badge\n LEVEL 1\n"
                    binding.poet.text =
                        "You’ve conquered the launch\n the first flight’s won\n Level One is done \noh\n what fun! \n But the cosmos calls\nthere’s more to see\nOnward, pilot, to victory!"

                } else if (playerData.level == 1 && playerData.score > 1) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.win)
                    mediaPlayer?.start()
                    binding.badge.isInvisible = false
                    binding.badge.setBackgroundResource(R.drawable.second)
                    binding.badgeName.text = "PICASSO \n "
                    binding.level.text = "You got PICASSO badge\n LEVEL 2\n"
                    binding.poet.text =
                        "Precision mastered\n design refined \n Level Two is now behind\n The stars applaud \n your path is clear \n Onward \nbuilder \nto new frontiers! \n"
                } else if (playerData.level == 2 && playerData.score > 1) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.win)
                    mediaPlayer?.start()
                    binding.badge.isInvisible = false
                    binding.badge.setBackgroundResource(R.drawable.finalstep)
                    binding.badgeName.text = "SWORD IN HILL\n"
                    binding.level.text = "you got SWORD IN HILL badge\n LEVEL 3\n"
                    binding.poet.text =
                        "The hill is conquered\n  the sword held high\n Your journey ends beneath the sky.\n Through every trial\n you rose\nyou fought\n A legend forged\n a story wrought.\n"
                    binding.countine.text = "start again"
                    playerData.level=3

                }
                else {
                mediaPlayer = MediaPlayer.create(this, R.raw.fail)
                mediaPlayer?.start()
                binding.badge.visibility = (View.INVISIBLE)
                binding.gif.setBackgroundResource(R.drawable.cat)
                binding.badgeName.text = ""
                binding.level.text = ""
                binding.poet.textSize = 20.0F
                binding.poet.text =
                    "The battle was fierce\n the fight was steep\n But even heroes need their sleep.\n Rest now\nbrave soul\n and dream anew\n Tomorrow’s dawn will call on you."
                binding.countine.text = "start again"

            }
        }

    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        super.onDestroy()
    }


    fun continueButton(view: View) {
        mediaPlayer = MediaPlayer.create(this, R.raw.clicknote)
        mediaPlayer?.start()

        if (binding.countine.text == "start again") {
            val a = Intent(this, MainActivity::class.java)
            a.putExtra("player",playerData)
            startActivity(a)
        } else {
            val a = Intent(this, GameWindow::class.java)
            a.putExtra("level", playerData.level + 1)
            Log.d("Achievement", "Level: ${playerData.level + 1}")
            setResult(RESULT_OK, a)
            finish()
        }

    }










}