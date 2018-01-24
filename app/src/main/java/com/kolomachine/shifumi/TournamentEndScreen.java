package com.kolomachine.shifumi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asus pc on 08/11/2017.
 */

public class TournamentEndScreen extends Activity{

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_end_tournament);

        i = new Intent(this, MenuScreen.class);

        ArrayList<Player> players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        TextView tv = (TextView)findViewById(R.id.text_end_score_players);
        TextView tvBravo = (TextView)findViewById(R.id.text_end_score);
        Button bMenu = (Button)findViewById(R.id.btn_end_back_to_menu);
        bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });

        String s ="";
        int total = 0, max=0, idMax=0;
        for(int i=0; i<players.size(); i++) {
            s += players.get(i).getName();
            for(int j=0; j<players.get(i).getScores().size(); j++)
                total +=  players.get(i).getScores().get(j).getValeur();
            if(total>max)
                idMax = i;
            s += " : " + total + "\n";
            total = 0;
        }

        tv.setText(s);
        tvBravo.setText(tvBravo.getText()+players.get(idMax).getName());
    }
}
