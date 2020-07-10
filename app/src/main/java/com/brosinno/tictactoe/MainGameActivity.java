package com.brosinno.tictactoe;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class MainGameActivity extends AppCompatActivity {

    boolean easy;
    boolean medium;
    boolean hard;
    boolean extremelyHard;
    Random random = new Random();

    int flag = 0;
    int ax = 10;
    int zero = 1;
    int win = 0;
    int i;
    int game = 1;
    int gameSum = 0;
    int ctrFlag = 0;
    int resetChecker = 1;
    int currentGameDoneChecker = 0;
    int player1Score = 0, player2Score = 0, drawchecker = 0;
    static int[][] tracker = new int[3][3];
    int[] boardSum = new int[8];
    static int[][] buttonPressed = new int[3][3];

    boolean player1ax;
    boolean selectedSinglePlayer;

    TextView player1TV;
    TextView player2TV;
    CharSequence player1 = "Player 1";
    CharSequence player2 = "Player 2";

    private InterstitialAd interstitialAd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_afterstart);

        CharSequence[] players = getIntent().getCharSequenceArrayExtra("playersnames");
        player1ax = getIntent().getBooleanExtra("player1ax", true);
        selectedSinglePlayer = getIntent().getBooleanExtra("selectedsingleplayer", true);

        easy = getIntent().getBooleanExtra("easy", false);
        medium = getIntent().getBooleanExtra("medium", false);
        hard = getIntent().getBooleanExtra("hard", false);
        extremelyHard = getIntent().getBooleanExtra("extremely_hard", false);


        if (player1ax) {
            ax = 1;
            zero = 10;
        }


        player1 = players[0];
        player2 = players[1];
        player1TV = findViewById(R.id.playerone);
        player2TV = findViewById(R.id.playertwo);

        player1TV.setText(player1);
        player2TV.setText(player2);

        Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_LONG).show();

        // INTER AD
        MobileAds.initialize(this);
        interstitialAd1 = new InterstitialAd(this);
        interstitialAd1.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd1.loadAd(new AdRequest.Builder().build());


    }


    public void upperLeft(View view) {


        if (win == 0 && buttonPressed[0][0] == 0) {
            if (flag % 2 == 0)
                tracker[0][0] = ax;
            else
                tracker[0][0] = zero;

            printBoard();
            winCheck();
            checkCPUPlay();
            flag++;
            buttonPressed[0][0]++;
        }
    }


    public void upperCenter(View view) {

        if (win == 0 && buttonPressed[0][1] == 0) {
            if (flag % 2 == 0) tracker[0][1] = ax;
            else tracker[0][1] = zero;

            printBoard();
            winCheck();
            checkCPUPlay();
            buttonPressed[0][1]++;
            flag++;
        }
    }

    public void upperRight(View view) {
        if (win == 0 && buttonPressed[0][2] == 0) {
            if (flag % 2 == 0) tracker[0][2] = ax;
            else tracker[0][2] = zero;

            printBoard();
            winCheck();
            checkCPUPlay();
            buttonPressed[0][2]++;
            flag++;
        }
    }

    public void middleLeft(View v) {
        if (win == 0 && buttonPressed[1][0] == 0) {
            if (flag % 2 == 0) tracker[1][0] = ax;
            else tracker[1][0] = zero;

            printBoard();
            winCheck();
            checkCPUPlay();

            ++buttonPressed[1][0];
            flag++;
        }
    }

    public void middleCenter(View v) {
        if (win == 0 && buttonPressed[1][1] == 0) {
            if (flag % 2 == 0) tracker[1][1] = ax;
            else tracker[1][1] = zero;
            printBoard();
            winCheck();
            checkCPUPlay();
            ++buttonPressed[1][1];
            flag++;
        }
    }

    public void middleRight(View v) {
        if (win == 0 && buttonPressed[1][2] == 0) {
            if (flag % 2 == 0) tracker[1][2] = ax;
            else tracker[1][2] = zero;

            printBoard();
            winCheck();
            checkCPUPlay();
            ++buttonPressed[1][2];
            flag++;
        }
    }

    public void bottomLeft(View v) {
        if (win == 0 && buttonPressed[2][0] == 0) {
            if (flag % 2 == 0) tracker[2][0] = ax;
            else tracker[2][0] = zero;

            printBoard();
            winCheck();
            checkCPUPlay();
            ++buttonPressed[2][0];
            flag++;
        }
    }

    public void bottomCenter(View v) {
        if (win == 0 && buttonPressed[2][1] == 0) {
            if (flag % 2 == 0) tracker[2][1] = ax;
            else tracker[2][1] = zero;
            printBoard();
            winCheck();
            checkCPUPlay();
            ++buttonPressed[2][1];
            flag++;
        }
    }

    public void bottomRight(View v) {
        if (win == 0 && buttonPressed[2][2] == 0) {
            if (flag % 2 == 0) tracker[2][2] = ax;
            else tracker[2][2] = zero;

            printBoard();
            winCheck();
            checkCPUPlay();
            ++buttonPressed[2][2];
            flag++;
        }
    }

    public void checkCPUPlay() {
        if ((selectedSinglePlayer) && (win == 0)) {


            if (checkIfCPUWins()) ;
            else if (ifOpponentWins()) ;
            else if (isEmptyCenter()) ;
            else if (isEmptyCorner()) ;
            else isEmptyAny();

            printBoard();
            winCheck();

            flag++;
            return;
        }
    }

    public boolean checkIfCPUWins() {
        if (!easy) {
            for (i = 0; i < 8; i++) {
                if (boardSum[i] == 2 * zero) {
                    if (i == 0) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[0][x] == 0)
                                tracker[0][x] = zero;
                    }

                    if (i == 1) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[1][x] == 0)
                                tracker[1][x] = zero;
                    }
                    if (i == 2) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[2][x] == 0)
                                tracker[2][x] = zero;
                    }

                    if (i == 3) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[x][0] == 0)
                                tracker[x][0] = zero;
                    }

                    if (i == 4) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][1] == 0)
                                tracker[x][1] = zero;
                    }

                    if (i == 5) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][2] == 0)
                                tracker[x][2] = zero;
                    }
                    if (i == 6) {

                        for (int y = 0; y < 3; y++)
                            for (int x = 0; x < 3; x++)
                                if (x == y)
                                    if (tracker[x][y] == 0)
                                        tracker[x][y] = zero;
                    }
                    if (i == 7) {
                        if (tracker[0][2] == 0)
                            tracker[0][2] = zero;
                        else if (tracker[1][1] == 0)
                            tracker[1][1] = zero;
                        else tracker[2][0] = zero;

                    }
                    return true;
                }
            }
        }
        return false;
    }


    public boolean ifOpponentWins() {
        if ((!easy) || (!medium)) {

            for (i = 0; i < 8; i++) {
                if (boardSum[i] == 2 * ax) {
                    if (i == 0) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[0][x] == 0) {
                                tracker[0][x] = zero;
                                buttonPressed[0][x]++;
                            }
                    }

                    if (i == 1) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[1][x] == 0) {
                                tracker[1][x] = zero;
                                buttonPressed[1][x]++;
                            }
                    }
                    if (i == 2) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[2][x] == 0) {
                                tracker[2][x] = zero;
                                buttonPressed[2][x]++;
                            }
                    }

                    if (i == 3) {
                        for (int x = 0; x < 3; x++)
                            if (tracker[x][0] == 0) {
                                tracker[x][0] = zero;
                                buttonPressed[x][0]++;
                            }
                    }

                    if (i == 4) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][1] == 0) {
                                tracker[x][1] = zero;
                                buttonPressed[x][1]++;
                            }
                    }

                    if (i == 5) {

                        for (int x = 0; x < 3; x++)
                            if (tracker[x][2] == 0) {
                                tracker[x][2] = zero;
                                buttonPressed[x][2]++;
                            }
                    }
                    if (i == 6) {

                        for (int y = 0; y < 3; y++)
                            for (int x = 0; x < 3; x++)
                                if (x == y)
                                    if (tracker[x][y] == 0) {
                                        tracker[x][y] = zero;
                                        buttonPressed[x][y]++;
                                    }


                    }
                    if (i == 7) {
                        if (tracker[0][2] == 0) {
                            tracker[0][2] = zero;
                            buttonPressed[0][2]++;
                        } else if (tracker[1][1] == 0) {
                            tracker[1][1] = zero;
                            buttonPressed[1][1]++;
                        } else {
                            tracker[2][0] = zero;
                            buttonPressed[2][0]++;
                        }


                    }
                    return true;
                }
            }

        }
        return false;
    }

    public boolean isEmptyCenter() {
        if (extremelyHard || hard) {
            if (tracker[1][1] == 0) {
                tracker[1][1] = zero;
                buttonPressed[1][1]++;
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyCorner() {


        if (hard || extremelyHard)
            if (((tracker[0][0] + tracker[2][2]) == 2 * ax) || ((tracker[0][2] + tracker[2][0]) == 2 * ax)) {
                for (int k = 0; k < 3; k++)
                    for (int j = 0; j < 3; j++)
                        if ((k + j) % 2 == 1) {
                            if (tracker[k][j] == 0)
                                tracker[k][j] = zero;
                            buttonPressed[k][j]++;
                            return true;
                        }
            }


        if (extremelyHard)
            if (boardSum[6] == zero || boardSum[7] == zero) {
                if (boardSum[6] == zero) {
                    if ((boardSum[0] + boardSum[3]) > (boardSum[2] + boardSum[5])) {
                        tracker[0][0] = zero;
                        buttonPressed[0][0]++;
                    } else {
                        tracker[2][2] = zero;
                        buttonPressed[2][2]++;
                    }
                    return true;
                }

                if (boardSum[7] == zero) {
                    if ((boardSum[0] + boardSum[5]) > (boardSum[3] + boardSum[2])) {
                        tracker[0][2] = zero;
                        buttonPressed[0][2]++;
                    } else {
                        tracker[2][0] = zero;
                        buttonPressed[2][0]++;
                    }
                    return true;
                }

            }


        for (int i = 0; i < 3; i++) {
            if (tracker[0][i] == ax) {
                if (tracker[0][0] == 0) {
                    tracker[0][0] = zero;
                    buttonPressed[0][0]++;
                    return true;
                }
                if (tracker[0][2] == 0) {
                    tracker[0][2] = zero;
                    buttonPressed[0][2]++;
                    return true;
                }
            }
        }

        for (int i = 0; i < 3; i++) {

            if (tracker[2][i] == ax) {
                if (tracker[2][0] == 0) {
                    tracker[2][0] = zero;
                    buttonPressed[2][0]++;
                    return true;
                }
                if (tracker[2][2] == 0) {
                    tracker[2][2] = zero;
                    buttonPressed[2][2]++;
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (tracker[i][0] == ax) {
                if (tracker[0][0] == 0) {
                    tracker[0][0] = zero;
                    buttonPressed[0][0]++;
                    return true;
                }
                if (tracker[2][0] == 0) {
                    tracker[2][0] = zero;
                    buttonPressed[2][0]++;
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (tracker[i][2] == ax) {
                if (tracker[0][2] == 0) {
                    tracker[0][2] = zero;
                    buttonPressed[0][2]++;
                    return true;
                }
                if (tracker[2][2] == 0) {
                    tracker[2][2] = zero;
                    buttonPressed[2][2]++;
                    return true;
                }
            }
        }
        return false;

    }

    public void isEmptyAny() {

        if (ctrFlag == 0)
            while (true) {
                int x = random();
                int y = random();

                if (tracker[x][y] == 0) {
                    tracker[x][y] = zero;
                    buttonPressed[x][y]++;
                    return;

                }
            }

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                if (tracker[x][y] == 0) {
                    tracker[x][y] = zero;
                    buttonPressed[x][y]++;
                    return;
                }


    }

    public int random() {
        return random.nextInt(3);
    }

    public void printBoard() {
        ImageView qube1, qube2, qube3, qube4, qube5, qube6, qube7, qube8, qube9;

        qube1 = findViewById(R.id.tzz);
        qube2 = findViewById(R.id.tzo);
        qube3 = findViewById(R.id.tzt);
        qube4 = findViewById(R.id.toz);
        qube5 = findViewById(R.id.too);
        qube6 = findViewById(R.id.tot);
        qube7 = findViewById(R.id.ttz);
        qube8 = findViewById(R.id.tto);
        qube9 = findViewById(R.id.ttt);


        if (tracker[0][0] == 1) qube1.setImageResource(R.drawable.letter_x);
        if (tracker[0][0] == 10) qube1.setImageResource(R.drawable.zero);


        if (tracker[0][1] == 1) qube2.setImageResource(R.drawable.letter_x);
        if (tracker[0][1] == 10) qube2.setImageResource(R.drawable.zero);


        if (tracker[0][2] == 1) qube3.setImageResource(R.drawable.letter_x);
        if (tracker[0][2] == 10) qube3.setImageResource(R.drawable.zero);


        if (tracker[1][0] == 1) qube4.setImageResource(R.drawable.letter_x);
        if (tracker[1][0] == 10) qube4.setImageResource(R.drawable.zero);

        if (tracker[1][1] == 1) qube5.setImageResource(R.drawable.letter_x);
        if (tracker[1][1] == 10) qube5.setImageResource(R.drawable.zero);


        if (tracker[1][2] == 1) qube6.setImageResource(R.drawable.letter_x);
        if (tracker[1][2] == 10) qube6.setImageResource(R.drawable.zero);

        if (tracker[2][0] == 1) qube7.setImageResource(R.drawable.letter_x);
        if (tracker[2][0] == 10) qube7.setImageResource(R.drawable.zero);


        if (tracker[2][1] == 1) qube8.setImageResource(R.drawable.letter_x);
        if (tracker[2][1] == 10) qube8.setImageResource(R.drawable.zero);

        if (tracker[2][2] == 1) qube9.setImageResource(R.drawable.letter_x);
        if (tracker[2][2] == 10) qube9.setImageResource(R.drawable.zero);

        resetChecker++;
    }


    public void showDialog(String whoWon, String scoreWon, String whoLose, String scoreLose) {

        final Dialog dialog = new Dialog(MainGameActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        TextView titleText = dialog.findViewById(R.id.title_text);
        dialog.setCancelable(false);
        dialog.show();

        titleText.setText(whoWon);

        Button resetButton = dialog.findViewById(R.id.reset_button);
        Button playAgainButton = dialog.findViewById(R.id.play_again_button);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                reset();
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                playMoreGame();
            }
        });
    }

    public void winCheck() {
        ctrFlag++;
        boardSum[0] = tracker[0][0] + tracker[0][1] + tracker[0][2];
        boardSum[1] = tracker[1][0] + tracker[1][1] + tracker[1][2];
        boardSum[2] = tracker[2][0] + tracker[2][1] + tracker[2][2];
        boardSum[3] = tracker[0][0] + tracker[1][0] + tracker[2][0];
        boardSum[4] = tracker[0][1] + tracker[1][1] + tracker[2][1];
        boardSum[5] = tracker[0][2] + tracker[1][2] + tracker[2][2];
        boardSum[6] = tracker[0][0] + tracker[1][1] + tracker[2][2];
        boardSum[7] = tracker[0][2] + tracker[1][1] + tracker[2][0];


        currentGameDoneChecker++;
        resetChecker++;

        for (int i = 0; i < 8; i++)
            if (boardSum[i] == 3 || boardSum[i] == 30) {
                win++;
                if ((boardSum[i] == 3) && (ax == 1)) {
                    player1Score++;
                    TextView q1 = findViewById(R.id.p1score);
                    q1.setText("" + player1Score);
                    showDialog("" + player1 + " won!", "" + player1Score, "" + player2, "" + player2Score);

                }
                if ((boardSum[i] == 3) && (zero == 1)) {
                    player2Score++;
                    TextView q1 = findViewById(R.id.p2score);
                    q1.setText("" + player2Score);
                    showDialog("" + player2 + " won!", "" + player2Score, "" + player1, "" + player1Score);

                }
                if ((boardSum[i] == 30) && (ax == 10)) {
                    player1Score++;
                    TextView q1 = findViewById(R.id.p1score);
                    q1.setText("" + player1Score);
                    showDialog("" + player1 + " won!", "" + player1Score, "" + player2, "" + player2Score);

                }
                if ((boardSum[i] == 30) && (zero == 10)) {
                    player2Score++;
                    TextView q1 = findViewById(R.id.p2score);
                    q1.setText("" + player2Score);
                    showDialog("" + player2 + " won!", "" + player2Score, "" + player1, "" + player1Score);

                }

            }

        if ((ctrFlag == 9) && (win == 0)) {
            showDialog("This is a draw !", "" + player1Score, "" + player2, "" + player2Score);

            drawchecker++;
        }


    }

    private void playMoreGame() {
        if ((drawchecker > 0) || (win > 0)) {
            game++;
            TextView qq = findViewById(R.id.gamenumber);
            qq.setText("" + game);

            for (int i = 0; i < 8; i++)
                boardSum[i] = 0;

            drawchecker = 0;


            ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;
            q1 = findViewById(R.id.tzz);
            q2 = findViewById(R.id.tzo);
            q3 = findViewById(R.id.tzt);
            q4 = findViewById(R.id.toz);
            q5 = findViewById(R.id.too);
            q6 = findViewById(R.id.tot);
            q7 = findViewById(R.id.ttz);
            q8 = findViewById(R.id.tto);
            q9 = findViewById(R.id.ttt);
            q1.setImageDrawable(null);
            q2.setImageDrawable(null);
            q3.setImageDrawable(null);
            q4.setImageDrawable(null);
            q5.setImageDrawable(null);
            q6.setImageDrawable(null);
            q7.setImageDrawable(null);
            q8.setImageDrawable(null);
            q9.setImageDrawable(null);

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    buttonPressed[i][j] = 0;

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    tracker[i][j] = 0;


            if ((game + 1) % 2 == 0)
                Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "" + player2 + "\'s turn", Toast.LENGTH_LONG).show();
            win = 0;
            gameSum = 0;
            ctrFlag = 0;
            flag = (game + 1) % 2;
            currentGameDoneChecker = 0;

            if (selectedSinglePlayer && (game % 2 == 0))
                checkCPUPlay();
        }
    }


    public void reset() {

        TextView qq = findViewById(R.id.gamenumber);
        qq.setText("" + 1);


        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                tracker[i][j] = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttonPressed[i][j] = 0;

        ImageView q1, q2, q3, q4, q5, q6, q7, q8, q9;

        q1 = findViewById(R.id.tzz);
        q2 = findViewById(R.id.tzo);
        q3 = findViewById(R.id.tzt);
        q4 = findViewById(R.id.toz);
        q5 = findViewById(R.id.too);
        q6 = findViewById(R.id.tot);
        q7 = findViewById(R.id.ttz);
        q8 = findViewById(R.id.tto);
        q9 = findViewById(R.id.ttt);
        q1.setImageDrawable(null);
        q2.setImageDrawable(null);
        q3.setImageDrawable(null);
        q4.setImageDrawable(null);
        q5.setImageDrawable(null);
        q6.setImageDrawable(null);
        q7.setImageDrawable(null);
        q8.setImageDrawable(null);
        q9.setImageDrawable(null);


        win = 0;
        drawchecker = 0;
        gameSum = 0;
        resetChecker = 0;
        ctrFlag = 0;
        player1Score = 0;
        player2Score = 0;
        game = 1;
        flag = 0;
        currentGameDoneChecker = 0;
        TextView player1Score = findViewById(R.id.p1score);
        player1Score.setText("" + this.player1Score);
        TextView player2Score = findViewById(R.id.p2score);
        player2Score.setText("" + this.player2Score);

        Toast.makeText(this, "" + player1 + "\'s turn", Toast.LENGTH_LONG).show();


    }

    private void showExitDialog() {
        final Dialog dialog = new Dialog(MainGameActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout_exit);
        dialog.setCancelable(false);

        dialog.show();

        Button exit = dialog.findViewById(R.id.yes_button);
        final Button dismiss = dialog.findViewById(R.id.no_button);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd1.isLoaded()) {
                    interstitialAd1.show();
                    interstitialAd1.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            reset();
                            finish();
                        }
                    });
                }
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}


