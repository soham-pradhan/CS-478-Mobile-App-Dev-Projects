package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    public int counter = 0;
    boolean exitfpr,exitspr=false;
    public static char[][] positions = new char[3][3];
    private final Handler mhandler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){}

            int what = msg.what;


            //Receiving notifications regarding the next move and Displaying a toast everytime UI gets updated
            //Used Toastit to provide workflow of the app, if uncommented, it will display toasts of every move made by player
            //Toastit(msg,what,msg.arg1,msg.arg2);
            hasWon();
            isDraw();

            //Displaying current tic tac toe move and updating after every move
            switch(what){
                case PLAYER_1:
                    setBoardX(msg.arg1,msg.arg2);
                    if(!hasFWon() &&  !hasSWon() && !isDraw()){


                        //Informing player the move of its opponent
                        Thread SecondPlayerThread = new Thread(new SecondPlayerRunnable(msg.arg1,msg.arg2));
                        SecondPlayerThread.start();
                    }

                    break;
                case PLAYER_2:
                    setBoardO(msg.arg1,msg.arg2);
                    if(!hasSWon() &&!hasFWon() && !isDraw()){

                        //Informing player the move of its opponent
                        Thread FirstPlayerThread = new Thread(new FirstPlayerRunnable(msg.arg1,msg.arg2));
                        FirstPlayerThread.start();
                    }
                    break;
            }
        }
    };


    //Updating the User by posting a toast of players move
    private void Toastit(Message x, int msg, int arg1, int arg2) {
        if(msg == 2){
            try{Thread.sleep(1000);}
            catch(InterruptedException e){};
            Toast.makeText(MainActivity.this,"First player X has played "+x.arg1+x.arg2,Toast.LENGTH_SHORT).show();
        }
        if(msg == 1){
            try{Thread.sleep(1000);}
            catch(InterruptedException e){};
            Toast.makeText(MainActivity.this,"Second player O has played "+x.arg1+x.arg2,Toast.LENGTH_SHORT).show();

        }
    }

    //Checking the status of the game

    private void hasWon() {
        if(isDraw() && counter == 0){
            Toast.makeText(MainActivity.this,"It's a draw ",Toast.LENGTH_SHORT).show();
            counter =1;
            exitfpr = true;
            exitspr = true;
        }
        if(hasFWon()){
            Toast.makeText(MainActivity.this,"First Player won!", Toast.LENGTH_SHORT).show();
            exitfpr = true;
            exitspr = true;
        }
        if(hasSWon()){
            Toast.makeText(MainActivity.this,"Second Player won!", Toast.LENGTH_SHORT).show();
            exitfpr = true;
            exitspr = true;
        }

    }

    public static final int PLAYER_1 = 2 ;
    public static final int PLAYER_2 = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv1);
        tv.setTypeface(null, Typeface.BOLD);
        Button button1  = findViewById(R.id.new_game);
        reset();

        //Button to start, reset the game


        button1.setOnClickListener(this::startThread);
    }

    //Function which resets the UI and necessary things for game to start again when button is pressed

    public void reset(){

        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3 ; j++) {
                positions[i][j] = '*';
            }
        }
        Button button;
        button = findViewById(R.id.button_00);
        button.setText("");
        button = findViewById(R.id.button_01);
        button.setText("");
        button = findViewById(R.id.button_02);
        button.setText("");
        button = findViewById(R.id.button_10);
        button.setText("");
        button = findViewById(R.id.button_11);
        button.setText("");
        button = findViewById(R.id.button_12);
        button.setText("");
        button = findViewById(R.id.button_20);
        button.setText("");
        button = findViewById(R.id.button_21);
        button.setText("");
        button = findViewById(R.id.button_22);
        button.setText("");
        counter = 0;
        exitfpr = false;
        exitspr = false;
        try{Thread.sleep(1000);}
        catch (InterruptedException e){}
    }

    //UI Thread
    public void startThread(View view){
        reset();

        //Removing all the messages and callbacks placed by the previous game
        mhandler.removeCallbacksAndMessages(null);

        //Starting the game
        Thread FirstPlayerThread = new Thread(new FirstPlayerRunnable(-1,-1));
        FirstPlayerThread.start();
    }

    public class FirstPlayerRunnable implements Runnable {

        private int a;
        private int b;

        //Updating the positions array which stores the opponents move
        public FirstPlayerRunnable(int x,int y){
            a = x;
            b = y;

            //Updating the player move of its opponent
            if(a!=-1 && b!=-1){
                positions[a][b] = 'o';
            }


        }

        @Override
        public void run(){
            while(!exitfpr){
                Random random = new Random();
                while (true) {
                    int x = random.nextInt(3);
                    int y = random.nextInt(3);
                    if (positions[x][y] =='*') {
                        positions[x][y] = 'x';

                        //Sending message to handleMessage about the move computed by stratergy
                        Message msg = mhandler.obtainMessage(MainActivity.PLAYER_1) ;
                        msg.arg1 = x ;
                        msg.arg2 = y;
                        mhandler.sendMessage(msg) ;
                        break;
                    }
                }
                break;
            }

        }
    }

    public class SecondPlayerRunnable implements Runnable{

        private int a;
        private int b;
        public SecondPlayerRunnable(int x,int y){
            a = x;
            b = y;

            //Updating player move of its opponent
            if(a!=-1 && b!=-1){
                positions[a][b] = 'x';
            }
        }
        @Override
        public void run() {
            while(!exitspr){
                Move bestMove = findBestMove(positions);
                int x = bestMove.row;
                int y = bestMove.col;
                positions[x][y] = 'o';

                //Sending message to handleMessage about the move computed by stratergy
                Message msg = mhandler.obtainMessage(MainActivity.PLAYER_2) ;
                msg.arg1 = x ;
                msg.arg2 = y;
                mhandler.sendMessage(msg);
                break;
            }

        }

        class Move {
            int row, col;
        };

        public Boolean isMovesLeft(char board[][])
        {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '*')
                        return true;
            return false;
        }

        public char player = 'o', opponent = 'x';

        //Evaluation function
        public int evaluate(char b[][])
        {
            // Checking for Rows for X or O victory.
            for (int row = 0; row < 3; row++)
            {
                if (b[row][0] == b[row][1] &&
                        b[row][1] == b[row][2])
                {
                    if (b[row][0] == player)
                        return +10;
                    else if (b[row][0] == opponent)
                        return -10;
                }
            }

            // Checking for Columns for X or O victory.
            for (int col = 0; col < 3; col++)
            {
                if (b[0][col] == b[1][col] &&
                        b[1][col] == b[2][col])
                {
                    if (b[0][col] == player)
                        return +10;

                    else if (b[0][col] == opponent)
                        return -10;
                }
            }

            // Checking for Diagonals for X or O victory.
            if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
            {
                if (b[0][0] == player)
                    return +10;
                else if (b[0][0] == opponent)
                    return -10;
            }

            if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
            {
                if (b[0][2] == player)
                    return +10;
                else if (b[0][2] == opponent)
                    return -10;
            }

            // Else if none of them have won then return 0
            return 0;
        }

        public int minimax(char board[][],
                           int depth, Boolean isMax)
        {
            int score = evaluate(board);

            // If Maximizer has won the game
            // return his/her evaluated score
            if (score == 10)
                return score;

            // If Minimizer has won the game
            // return his/her evaluated score
            if (score == -10)
                return score;

            // If there are no more moves and
            // no winner then it is a tie
            if (isMovesLeft(board) == false)
                return 0;

            // If this maximizer's move
            if (isMax)
            {
                int best = -1000;

                // Traverse all cells
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        // Check if cell is empty
                        if (board[i][j]=='*') {
                            // Make the move
                            board[i][j] = player;

                            // Call minimax recursively and choose
                            // the maximum value
                            best = Math.max(best, minimax(board,
                                    depth + 1, !isMax));

                            // Undo the move
                            board[i][j] = '*';
                        }
                    }
                }
                return best;
            }

            // If this minimizer's move
            else
            {
                int best = 1000;
                // Traverse all cells
                for (int i = 0; i < 3; i++){
                    for (int j = 0; j < 3; j++) {
                        // Check if cell is empty
                        if (board[i][j] == '*') {
                            // Make the move
                            board[i][j] = opponent;

                            // Call minimax recursively and choose
                            // the minimum value
                            best = Math.min(best, minimax(board,
                                    depth + 1, !isMax));

                            // Undo the move
                            board[i][j] = '*';
                        }
                    }
                }
                return best;
            }
        }

        public Move findBestMove(char board[][])
        {
            int bestVal = -1000;
            Move bestMove = new Move();
            bestMove.row = -1;
            bestMove.col = -1;

            // Traverse all cells, evaluate minimax function
            // for all empty cells. And return the cell
            // with optimal value.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (board[i][j] == '*') {
                        // Make the move
                        board[i][j] = player;

                        // compute evaluation function for this
                        // move.
                        int moveVal = minimax(board, 0, false);
                        // Undo the move
                        board[i][j] = '*';

                        // If the value of the current move is
                        // more than the best value, then update
                        // best/
                        if (moveVal > bestVal) {
                            bestMove.row = i;
                            bestMove.col = j;
                            bestVal = moveVal;
                        }
                    }
                }
            }

            return bestMove;
        }
    }

    //Updating the UI of X
    public void setBoardX(int i, int j){
        Button button;
        String x="";
        if(i ==0 && j==0){
            button = findViewById(R.id.button_00);
            button.setText("X");
        }
        if(i ==0 && j==1){
            button = findViewById(R.id.button_01);
            button.setText("X");
        }
        if(i ==0 && j==2){
            button = findViewById(R.id.button_02);
            button.setText("X");
        }
        if(i ==1 && j==0){
            button = findViewById(R.id.button_10);
            button.setText("X");
        }
        if(i ==1 && j==1){
            button = findViewById(R.id.button_11);
            button.setText("X");
        }
        if(i == 1 && j==2){
            button = findViewById(R.id.button_12);
            button.setText("X");
        }
        if(i == 2 && j==0){
            button = findViewById(R.id.button_20);
            button.setText("X");
        }
        if(i == 2 && j==1){
            button = findViewById(R.id.button_21);
            button.setText("X");
        }
        if(i == 2 && j==2){
            button = findViewById(R.id.button_22);
            button.setText("X");
        }

    }

    //Updating UI of O
    public void setBoardO(int i, int j){
        Button button;
        String x="";
        if(i ==0 && j==0){
            button = findViewById(R.id.button_00);
            button.setText("O");
        }
        if(i ==0 && j==1){
            button = findViewById(R.id.button_01);
            button.setText("O");
        }
        if(i ==0 && j==2){
            button = findViewById(R.id.button_02);
            button.setText("O");
        }
        if(i ==1 && j==0){
            button = findViewById(R.id.button_10);
            button.setText("O");
        }
        if(i ==1 && j==1){
            button = findViewById(R.id.button_11);
            button.setText("O");
        }
        if(i == 1 && j==2){
            button = findViewById(R.id.button_12);
            button.setText("O");
        }
        if(i == 2 && j==0){
            button = findViewById(R.id.button_20);
            button.setText("O");
        }
        if(i == 2 && j==1){
            button = findViewById(R.id.button_21);
            button.setText("O");
        }
        if(i == 2 && j==2){
            button = findViewById(R.id.button_22);
            button.setText("O");
        }
    }

    //Checking if the game is a draw
    public boolean isDraw(){
        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ;j < 3 ; j++)
                if(positions[i][j] == '*')
                    return false;
        }
        exitfpr = true;
        exitspr = true;
        return true;
    }

    //Checking if second player has won the game
    public boolean hasSWon(){
        if(positions[0][0] == 'o' && positions[0][1]=='o' && positions[0][2]=='o'){
            return true;
        }

        if(positions[0][0] == 'o' && positions[1][0]=='o' && positions[2][0]=='o'){
            return true;
        }

        if(positions[0][2] == 'o' && positions[1][2]=='o' && positions[2][2]=='o'){
            return true;
        }

        if(positions[2][0] == 'o' && positions[2][1]=='o' && positions[2][2]=='o'){
            return true;
        }
        if(positions[0][0] == 'o' && positions[1][1]=='o' && positions[2][2]=='o'){
            return true;
        }

        if(positions[0][2] == 'o' && positions[1][1]=='o' && positions[2][0]=='o'){
            return true;
        }

        if(positions[0][1] == 'o' && positions[1][1]=='o' && positions[2][1]=='o'){
            return true;
        }

        if(positions[1][0] == 'o' && positions[1][1]=='o' && positions[1][2]=='o'){
            return true;
        }
        return false;
    }

    //Checking if first player has won the game
    public boolean hasFWon(){
        if(positions[0][0] == 'x' && positions[0][1]=='x' && positions[0][2]=='x'){
            return true;
        }

        if(positions[0][0] == 'x' && positions[1][0]=='x' && positions[2][0]=='x'){
            return true;
        }

        if(positions[0][2] == 'x' && positions[1][2]=='x' && positions[2][2]=='x'){
            return true;
        }

        if(positions[2][0] == 'x' && positions[2][1]=='x' && positions[2][2]=='x'){
            return true;
        }

        if(positions[0][0] == 'x' && positions[1][1]=='x' && positions[2][2]=='x'){
            return true;
        }

        if(positions[0][2] == 'x' && positions[1][1]=='x' && positions[2][0]=='x'){
            return true;
        }

        if(positions[0][1] == 'x' && positions[1][1]=='x' && positions[2][1]=='x'){
            return true;
        }

        if(positions[1][0] == 'x' && positions[1][1]=='x' && positions[1][2]=='x') {
            return true;
        }
        return false;
    }

}