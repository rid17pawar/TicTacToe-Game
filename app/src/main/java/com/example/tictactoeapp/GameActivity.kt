package com.example.tictactoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.btn00
import kotlinx.android.synthetic.main.activity_game.btn01
import kotlinx.android.synthetic.main.activity_game.btn02
import kotlinx.android.synthetic.main.activity_game.btn10
import kotlinx.android.synthetic.main.activity_game.btn11
import kotlinx.android.synthetic.main.activity_game.btn12
import kotlinx.android.synthetic.main.activity_game.btn20
import kotlinx.android.synthetic.main.activity_game.btn21
import kotlinx.android.synthetic.main.activity_game.btn22
import kotlinx.android.synthetic.main.activity_game.displaytxt
import kotlinx.android.synthetic.main.activity_game.resetbtn
import kotlinx.android.synthetic.main.activity_main.*

//implementing interface View.OnClickListener
class GameActivity : AppCompatActivity(), View.OnClickListener {

    var player1: String? = ""
    var player2: String? = ""

    var isPlayer1: Boolean = true  //store which player is playing. true=player1, false=player2
    var turnCount: Int = 0  //how many turns are done. max turns=9
    var boardButtonsValues= Array(3){ IntArray(3) }  //declare 2D array to hold values of tic tac toe board [backend]
    lateinit var boardButtonsRef: Array<Array<Button>>  //declare 2D array to hold reference of buttons in view of tic tac toe board [frontend]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //player names got from main activity
        player1= intent.getStringExtra("player1")
        player2= intent.getStringExtra("player2")
        Log.d("players names: ", "1. ${player1} 2. ${player2}")

        //define 2D array containing reference to buttons on view
        boardButtonsRef= arrayOf(
            arrayOf(btn00, btn01, btn02),
            arrayOf(btn10, btn11, btn12),
            arrayOf(btn20, btn21, btn22)
        )

        //registering event listener on all btn
        for(buttons in boardButtonsRef){
            for(button in buttons){
                button.setOnClickListener(this)
            }
        }

        initializeBoardStatus() //initialze board btn and values

        //registering event listener on reset button
        resetbtn.setOnClickListener {
            isPlayer1= true
            turnCount=0
            initializeBoardStatus()
        }

    }

    private fun initializeBoardStatus() {
        displaytxt.text="${player1} Turn"
        //To reset board status, data and text
        for(i in 0..2){
            for(j in 0..2){
                boardButtonsValues[i][j]= -1    //values array reset
                boardButtonsRef[i][j].isEnabled= true //make btn clickable
                boardButtonsRef[i][j].text= ""  //remove text on btn
            }
        }
    }

    override fun onClick(v: View) {
        //defining onClick method on all 9 btn
        //perform diff. action on diff. button click using when in kotlin == switch in java
        when(v.id){
            R.id.btn00 ->{  updateValue(0,0, isPlayer1)   }
            R.id.btn01 ->{  updateValue(0,1, isPlayer1)   }
            R.id.btn02 ->{  updateValue(0,2, isPlayer1)   }
            R.id.btn10 ->{  updateValue(1,0, isPlayer1)   }
            R.id.btn11 ->{  updateValue(1,1, isPlayer1)   }
            R.id.btn12 ->{  updateValue(1,2, isPlayer1)   }
            R.id.btn20 ->{  updateValue(2,0, isPlayer1)   }
            R.id.btn21 ->{  updateValue(2,1, isPlayer1)   }
            R.id.btn22 ->{  updateValue(2,2, isPlayer1)   }
        }

        Log.d("current: ", "turncount= ${turnCount}, isPlayer1= ${isPlayer1}")
        turnCount++
        //switch player, another player will play now
        isPlayer1= !isPlayer1

        //display current player name above board
        if(isPlayer1){
            displayMessage("${player1} Turn")
        }
        else{
            displayMessage("${player2} Turn")
        }

        if(turnCount==9){
            displayMessage("Game Draw !")
        }

        //check winner player
        checkWinnerPlayer()
    }


    private fun updateValue( row: Int, column: Int, player: Boolean){
        var symbol: String= if(player){ "X" }else{ "O" }  //symbol to setText on btn
        var boardVal: Int= if(player){ 1 }else{ 0 }  //value to store in values array

        //setting values to frontend btn properties
        boardButtonsRef[row][column].apply{
            isEnabled= false
            text= symbol
        }

        //storing value in backend array
        boardButtonsValues[row][column]= boardVal

    }

    private fun checkWinnerPlayer() {
        //for debugging winning condition
        var tempVals: String =""
        for (i in 0..2){
            tempVals= "${i} -> "
            for (j in 0..2){
                tempVals= tempVals+ " ${ boardButtonsValues[i][j] }, "
            }
            Log.d("board row: ", tempVals)
        }

        //iterate through rows
        var wonFlag: Boolean= false
        var wonPlayer: Int= -1

        for (i in 0..2){
            wonFlag= false

            //check horizontally i.e. all rows for winner
            if(boardButtonsValues[i][0] == boardButtonsValues[i][1] && boardButtonsValues[i][0] == boardButtonsValues[i][2]){
                wonFlag= true
                wonPlayer= boardButtonsValues[i][0]
                break
            }

            //check vertically i.e. all columns for winner
            if(boardButtonsValues[0][i] == boardButtonsValues[1][i] && boardButtonsValues[0][i] == boardButtonsValues[2][i]){
                wonFlag= true
                wonPlayer= boardButtonsValues[0][i]
                break
            }

        }

        //check both diagonals
        if(boardButtonsValues[0][0] == boardButtonsValues[1][1] && boardButtonsValues[0][0] == boardButtonsValues[2][2]) {
            wonFlag = true
            wonPlayer = boardButtonsValues[0][0]
        }

        if(boardButtonsValues[0][2] == boardButtonsValues[1][1] && boardButtonsValues[0][2] == boardButtonsValues[2][0]) {
            wonFlag = true
            wonPlayer = boardButtonsValues[0][2]
        }

        //display winner and end game
        if(wonFlag && wonPlayer ==1){
            displayMessage("${player1} Won !")
            disableBoardBtns()
        }
        else if(wonFlag && wonPlayer ==0){
            displayMessage("${player2} Won !")
            disableBoardBtns()
        }

    }

    private fun displayMessage( msg: String){
        displaytxt.text= msg
    }

    private fun disableBoardBtns(){
        for(i in 0..2) {
            for (j in 0..2) {
                boardButtonsRef[i][j].isEnabled = false
            }
        }
    }


}