package com.future.quizgame

import android.annotation.SuppressLint
import android.view.View
import com.future.quizgame.databinding.PlayerRankBinding
import com.xwray.groupie.viewbinding.BindableItem

class PlayerRank (val player :Player):BindableItem<PlayerRankBinding>() {



    override fun bind(viewBinding: PlayerRankBinding, position: Int) {

        viewBinding.imageView2.setBackgroundResource(R.color.white)
        viewBinding.name.text= player.name
        if(player.level==1) {
            viewBinding.imageView2.setBackgroundResource(R.drawable.start)
        }
        else if (player.level==2)
        {
            viewBinding.imageView2.setBackgroundResource(R.drawable.second)
        }
        else if (player.level==3)
        {
            viewBinding.imageView2.setBackgroundResource(R.drawable.finalstep)
        }
        else{
            viewBinding.imageView2.visibility=(View.INVISIBLE)
        }
    }

    override fun getLayout(): Int = R.layout.player_rank

    override fun initializeViewBinding(view: View): PlayerRankBinding = PlayerRankBinding.bind(view)
}