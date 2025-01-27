package com.future.quizgame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.future.quizgame.databinding.ActivityGameWindowBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import kotlin.math.log


class GameWindow : AppCompatActivity() {
    lateinit var binding: ActivityGameWindowBinding
    var data :List<Result> = emptyList()
    lateinit var queue:RequestQueue
    var index = 1
    var score = 0
    var answer = ""
    var name =""
    private val player= Player()
    var media: MediaPlayer? = null

    val lanucher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )
    {
        if (it.resultCode == RESULT_OK) {
            val level = it.data?.getIntExtra("level", -1)
            if (level != null) {
               player.level= level
            }
            score=0
            reset()
            index++
            binding.countQuestions.text = " ${index} / 10"
            getData()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityGameWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val categoryData = intent.getStringArrayExtra("player")
        if (!categoryData.isNullOrEmpty()) {
            name = categoryData[0]
            binding.welcome.text = "Hello $name"
        }
        val doubleBounce: Sprite = DoubleBounce()
        binding.spinkit.setIndeterminateDrawable(doubleBounce)
        queue = Volley.newRequestQueue(this)
        val url = "https://opentdb.com/api.php?amount=20&category=${categoryData?.get(1)}&type=multiple"
        val request = JsonObjectRequest(url,{
            binding.spinkit.visibility=View.INVISIBLE
            val gson = Gson()
            val result = gson.fromJson(it.toString(),Quiz::class.java)
            if(result != null && result.results.isNotEmpty()) {
                data = result.results
                getData()
            }

        },{
            Toast.makeText(this, "error while loading", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)
        binding.countQuestions.text="${index} / 10"

        object : CountDownTimer(300000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                binding.timer.text = "${(millisUntilFinished / 60000)%60} : ${(millisUntilFinished / 1000)%60} "

            }

            override fun onFinish() {
                binding.timer.text="done!"
            }
        }.start()
        binding.timer.doAfterTextChanged {
            if(it.toString() == "done!")
            {
                player.name = name
                player.score = score
                val c = Intent(this,Achievement::class.java)
                c.putExtra("playerData",player)
                lanucher.launch(c)
            }

        }


        binding.chipGroup.setOnCheckedStateChangeListener {
          group  , checkedIds ->
            if (checkedIds.isNotEmpty()) {
                binding.next.isEnabled= true
                val selectedChipId = checkedIds[0]
                val selectedChip = group.findViewById<Chip>(selectedChipId)
                val selectedChipText = selectedChip.text
                answer = selectedChipText.toString()
                media= MediaPlayer.create(this,R.raw.clicknote)
                media?.start()
            } else {
                media?.stop()
                binding.next.isEnabled= false
            }
        }

    }

    override fun onDestroy() {
        queue.stop()
        media?.stop()
        super.onDestroy()
    }

    fun next(view: View) {
        if(index > 3 && player.level==0)
        {
            player.name=name
            player.score=score
            val c = Intent(this,Achievement::class.java)
            c.putExtra("playerData",player)
            lanucher.launch(c)
        }
        else if (index > 6 && player.level==1)
        {
            player.name=name
            player.score=score
            val c = Intent(this,Achievement::class.java)
            c.putExtra("playerData",player)
            lanucher.launch(c)
        }
        else if (index > 9  && player.level==2)
        {
            player.name=name
            player.score=score
            val c = Intent(this,Achievement::class.java)
            c.putExtra("playerData",player)
            lanucher.launch(c)
        }
        else {
            reset()
            if (answer == data[index].correctAnswer) {
                score++
            }
            index++
            binding.countQuestions.text = " ${index} / 10"
            getData()
        }
    }


    private fun reset() {
        binding.answer1.isChecked = false
        binding.answer2.isChecked = false
        binding.answer3.isChecked = false
        binding.answer4.isChecked = false
    }

    private fun getData() {
        val str = Html.fromHtml(data[index].question, Html.FROM_HTML_MODE_LEGACY).toString()
        binding.questions.text = str
        val answers = (data[index].incorrectAnswers + data[index].correctAnswer)
        val modifiedAnswers = answers.map { Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString() }.shuffled()
        binding.answer1.text = modifiedAnswers[0]
        binding.answer2.text = modifiedAnswers[1]
        binding.answer3.text = modifiedAnswers[2]
        binding.answer4.text = modifiedAnswers[3]
//        Log.d("answer",data[index].correctAnswer)
        Toast.makeText(this, data[index].correctAnswer, Toast.LENGTH_SHORT).show()
    }
}

