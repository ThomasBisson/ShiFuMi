package com.kolomachine.shifumi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by asus pc on 27/09/2017.
 */

public class GameScreen extends Activity {

    //GamePreparation variables
    TextView tvNom, tvManche;

    ImageView imgEmpty;

    //Game variables
    ImageView img1, img2, imgRock, imgCisor, imgPaper, imgAgain;
    TextView tvRes, tvTimer;
    Button bBack;

    int cptTimer = 1, alea, choice = 1;

    Tournament tournament;

    Intent i, i2;

    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            tvTimer.setText(cptTimer +"");
            cptTimer++;
            if(cptTimer < 4)
                handler.postDelayed(runnable, interval);
            else {
                tvTimer.setText("!");
                switch (alea) {
                    case 1 :
                        img2.setImageResource(R.drawable.pierre);
                        break;
                    case 2 :
                        img2.setImageResource(R.drawable.ciseau);
                        break;
                    case 3 :
                        img2.setImageResource(R.drawable.feuille);
                        break;
                }

                switch (choice) {
                    case 1 :
                        img1.setImageResource(R.drawable.pierre);
                        break;
                    case 2 :
                        img1.setImageResource(R.drawable.ciseau);
                        break;
                    case 3 :
                        img1.setImageResource(R.drawable.feuille);
                        break;
                }

                if(alea == choice) {
                    tvRes.setText("TIE");
                    if(tournament != null)
                        tournament.getPlayers().get(tournament.getIdActualPlayer()).addScore(Player.Result.TIE);
                } else if ((choice == 1 && alea == 2) || (choice == 2 && alea == 3) || (choice == 3 && alea == 1)) {
                    tvRes.setText("WIN");
                    if(tournament != null)
                        tournament.getPlayers().get(tournament.getIdActualPlayer()).addScore(Player.Result.WIN);
                } else {
                    tvRes.setText("FAIL");
                    if(tournament != null)
                        tournament.getPlayers().get(tournament.getIdActualPlayer()).addScore(Player.Result.LOOSE);
                }
                if(tournament != null) {
                    if (tournament.getPlayers().get(tournament.getIdActualPlayer()).getScores().size() == tournament.getNumberOfRounds()) {
                        if (tournament.getIdActualPlayer() == tournament.getPlayers().size() - 1) {
                            i2.putExtra("players", tournament.getPlayers());
                            startActivity(i2);
                        } else {
                            tournament.increaseIdActualPlayer();
                        }
                    }
                } else {
                    bBack.setVisibility(View.VISIBLE);
                    bBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(i);
                        }
                    });
                }

                imgAgain.setVisibility(View.VISIBLE);
                imgAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choice = 1;
                        cptTimer = 1;
                        showGamePreparation();
                    }
                });

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i = new Intent(this, MenuScreen.class);
        i2 = new Intent(this, TournamentEndScreen.class);


        if(getIntent().getBooleanExtra("Tournament",false))
            tournament = new Tournament(getIntent().getIntExtra("NbManches",1),getIntent().getStringArrayExtra("Names"));

        showGamePreparation();

    }

    public void showGamePreparation() {
        setContentView(R.layout.layout_ready);

        tvNom = (TextView)findViewById(R.id.text_actual_name_player);
        tvManche = (TextView)findViewById(R.id.text_actual_manche_player);

        if(tournament != null) {
            tvNom.setVisibility(View.VISIBLE);
            tvManche.setVisibility(View.VISIBLE);
            tvNom.setText(tournament.getPlayers().get(tournament.getIdActualPlayer()).getName());
            tvManche.setText(String.valueOf(tournament.getPlayers().get(tournament.getIdActualPlayer()).getScores().size()+1));
        }

        //i = new Intent(this, GameScreen.class);

        imgEmpty = (ImageView)findViewById(R.id.image_empty);
        imgEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGame();
            }
        });
    }

    public void showGame() {
        setContentView(R.layout.layout_game);

        tvRes = (TextView)findViewById(R.id.text_res);
        tvTimer = (TextView)findViewById(R.id.text_timer);
        img1 = (ImageView)findViewById(R.id.image_1);
        img2 = (ImageView)findViewById(R.id.image_2);
        imgAgain = (ImageView)findViewById(R.id.image_again);
        bBack = (Button)findViewById(R.id.btn_backToMenu);

        imgRock = (ImageView)findViewById(R.id.image_pierre);
        imgRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRock.setBackgroundResource(R.drawable.border_imageview);
                imgCisor.setBackground(null);
                imgPaper.setBackground(null);
                choice = 1;
            }
        });

        imgCisor = (ImageView)findViewById(R.id.image_cisceau);
        imgCisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRock.setBackground(null);
                imgCisor.setBackgroundResource(R.drawable.border_imageview);
                imgPaper.setBackground(null);
                choice = 2;
            }
        });

        imgPaper = (ImageView)findViewById(R.id.image_feuille);
        imgPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRock.setBackground(null);
                imgPaper.setBackgroundResource(R.drawable.border_imageview);
                imgCisor.setBackground(null);
                choice = 3;
            }
        });


        Random r = new Random();
        alea = r.nextInt(4 - 1) + 1;

        handler.postDelayed(runnable, interval);
    }
}
