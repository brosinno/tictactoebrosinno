package com.brosinno.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainHomeActivity extends AppCompatActivity {

    public EditText player1EditText;
    public EditText player2EditText;

    public Spinner difficulty;
    public CharSequence player1 = "Player 1";
    public CharSequence player2 = "Player 2";

    public CharSequence player2Clone;
    boolean player1ax = false;
    boolean selectedSinglePlayer;
    boolean easy = true;
    boolean medium = false;
    boolean hard = false;
    boolean extremelyHard = false;
    public CheckBox singlePlayer, doublePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        addItemToDifficultySpinner();

        player1EditText = findViewById(R.id.playerone);
        player2EditText = findViewById(R.id.playertwo);


        singlePlayer = findViewById(R.id.splayer);
        doublePlayer = findViewById(R.id.tplayer);

        setZeroAndX();

        singlePlayer.setOnClickListener(checkboxClickListener);
        doublePlayer.setOnClickListener(checkboxClickListener);

        difficulty.setEnabled(false);

        doublePlayer.setChecked(true);


        player1EditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        player2EditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player2 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AdView homePageBannerAd = findViewById(R.id.home_page_banner_ad);
        MobileAds.initialize(this, initializationStatus -> {
        });
        AdRequest homePageBannerAdRequest = new AdRequest.Builder().build();
        homePageBannerAd.loadAd(homePageBannerAdRequest);
    }


    private void setZeroAndX() {
        Random random = new Random();
        boolean player1x = random.nextBoolean();
        if (player1x) {
            player1ax = true;
        }
    }


    public void addItemToDifficultySpinner() {
        difficulty = findViewById(R.id.difficulty);

        List<String> list = new ArrayList<>();
        list.add("Easy");
        list.add("Medium");
        list.add("Hard");
        list.add("Extremely Hard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(dataAdapter);


        difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = parent.getItemAtPosition(position).toString();

                switch (temp) {
                    case "Easy":
                        easy = true;
                        medium = false;
                        hard = false;
                        extremelyHard = false;
                        break;
                    case "Medium":
                        easy = false;
                        medium = true;
                        hard = false;
                        extremelyHard = false;
                        break;
                    case "Hard":
                        easy = false;
                        medium = false;
                        hard = true;
                        extremelyHard = false;
                        break;
                    case "Extremely Hard":
                        easy = false;
                        medium = false;
                        hard = false;
                        extremelyHard = true;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                medium = true;
                easy = false;
                hard = false;
                extremelyHard = false;
            }
        });
    }


    View.OnClickListener checkboxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            if (checked) {
                switch (view.getId()) {
                    case R.id.splayer:
                        doublePlayer.setChecked(false);
                        selectedSinglePlayer = true;
                        player2Clone = player2;
                        player2EditText.setText("CPU");
                        player1EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        player1EditText.setImeActionLabel("DONE", EditorInfo.IME_ACTION_DONE);
                        difficulty.setEnabled(true);
                        break;

                    case R.id.tplayer:
                        singlePlayer.setChecked(false);
                        selectedSinglePlayer = false;
                        player2EditText.setText(player2Clone);
                        player1EditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                        player1EditText.setImeActionLabel("NEXT", EditorInfo.IME_ACTION_NEXT);
                        difficulty.setEnabled(false);
                        break;
                }

            } else {
                switch (view.getId()) {
                    case R.id.splayer:
                        doublePlayer.setChecked(true);
                        selectedSinglePlayer = false;
                        player2EditText.setText(player2Clone);
                        difficulty.setEnabled(false);
                        player1EditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                        player1EditText.setImeActionLabel("NEXT", EditorInfo.IME_ACTION_NEXT);
                        break;

                    case R.id.tplayer:
                        singlePlayer.setChecked(true);
                        selectedSinglePlayer = true;
                        player2EditText.setText("CPU");
                        player1EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        player1EditText.setImeActionLabel("DONE", EditorInfo.IME_ACTION_DONE);
                        difficulty.setEnabled(true);
                        break;
                }

            }

        }
    };


    public void startGame(View view) {

        if (!selectedSinglePlayer)
            if (player2.length() == 0)
                player2 = "player 2";
        if (player1.length() == 0)
            player1 = "player 1";

        CharSequence[] players = {player1, player2};
        Intent i = new Intent(this, MainGameActivity.class);
        i.putExtra("easy", easy);
        i.putExtra("medium", medium);
        i.putExtra("hard", hard);
        i.putExtra("extremely_hard", extremelyHard);
        i.putExtra("playersnames", players);
        i.putExtra("player1ax", player1ax);
        i.putExtra("selectedsingleplayer", selectedSinglePlayer);
        startActivity(i);
    }

}