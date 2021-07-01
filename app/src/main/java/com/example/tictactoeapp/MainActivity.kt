package com.example.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

//implementing interface View.OnClickListener
class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startgame.setOnClickListener {
            var player1: String? = player1name.text.toString().toUpperCase()
            var player2: String? = player2name.text.toString().toUpperCase()

            if(player1.equals("")){
                player1= "Player-1"
            }
            if(player2.equals("")){
                player2= "Player-2"
            }

            val intent1 = Intent(this, GameActivity::class.java)
            intent1.putExtra("player1", player1)
            intent1.putExtra("player2", player2)
            startActivity(intent1)
        }

    }
}