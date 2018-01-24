package com.kolomachine.shifumi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static java.lang.System.exit;

/**
 * Created by asus pc on 27/09/2017.
 */

public class MenuScreen extends Activity{

    public Button bOnePlayer, bTwoPlayer, bExit;
    public Intent i;

    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu);

        bOnePlayer = (Button) findViewById(R.id.btn_oneplayer);
        bOnePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(activity, GameScreen.class);
                i.putExtra("Tournament",false);
                startActivity(i);
            }
        });

        bTwoPlayer = (Button) findViewById(R.id.btn_twoplayer);
        bTwoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(activity, ChoiceTournamentScreen.class);
                startActivity(i);
            }
        });

        bExit = (Button) findViewById(R.id.btn_exit);
        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit(0);
            }
        });

        activity = this;
    }
}
