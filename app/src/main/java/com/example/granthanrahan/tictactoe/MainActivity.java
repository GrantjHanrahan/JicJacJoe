package com.example.granthanrahan.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    // public, private & protected are access modifiers
    //protected is slightly stricter than public
    //private variables only visible to the classes that they belong to
    // void is a return type and means that the method does not have a return value
    // java methods expect a return type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1); //associating the variable with the TV created in activity_main
        textViewPlayer2 = findViewById(R.id.text_view_p2);


        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){// iterate through the buttons of the two dimensional array
                String buttonID = "button_" + i + j; //loop through all of the buttons ids
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resourceID);// references to all of the buttons
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() { //onClick listener for the reset button
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        //checks if the button that was clicked contains an empty string
        if(!((Button) view).getText().toString().equals("")) {
            // if the button does not equal to an empty string
            return;
        }

        if(player1Turn){
            ((Button)view).setText("X");
        } else {
            ((Button)view).setText("O");
        }

        roundCount++;

        if (checkForWin()){
            if (player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }
        } else if(roundCount == 9){ //drawn game
            draw();
        } else { // no winner and no draw
            player1Turn = !player1Turn;
        }


    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString(); //iterate through all of the buttons and save them to the string array
            }
        }
        // [0][0] [0][1] [0][2]
        // [1][0] [1][1] [1][2]
        // [2][0] [2][1] [2][2]

        for (int i = 0; i < 3; i++) { //use the string array to go through all the rows and columns
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true; //compares the 3 fields next to each other, !field makes sure that it's not 3 empty fields
            }
        }// checks the top 3 rows

        for (int i = 0; i < 3; i++) { //use the string array to go through all the rows and columns
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true; //compares the 3 fields next to each other, !field makes sure that it's not 3 empty fields
            }
        }// checks the first 3 columns L - R

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true; //compares the 3 fields next to each other, !field makes sure that it's not 3 empty fields
        }// checks diag

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true; //compares the 3 fields next to each other, !field makes sure that it's not 3 empty fields
        }//checks diag

        return false; // no winner - checkForWin = false
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText(){
    textViewPlayer1.setText("Player 1: " + player1Points);
    textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard(){
        for(int i = 0; i< 3; i++){
            for(int j = 0; j < 3; j++){
                buttons[i][j].setText(""); //loop through all buttons and set text to an empty string
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {//handles device orientation change
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {//restores game after orientation change
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");


    }
}







