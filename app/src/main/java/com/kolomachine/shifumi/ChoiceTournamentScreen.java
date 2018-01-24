package com.kolomachine.shifumi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by asus pc on 02/10/2017.
 */

public class ChoiceTournamentScreen extends Activity {

    Context context;

    EditText edNameEntered, edNbManche;
    Button bGo, bCancel, bNext;
    TextView tvAllName;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choice_tournament);

        edNameEntered = (EditText)findViewById(R.id.edit_nom_personnes);
        edNbManche = (EditText)findViewById(R.id.edit_nb_manche);
        bGo = (Button)findViewById(R.id.button_send_name);
        bGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAllName.setText(tvAllName.getText() + "-" + edNameEntered.getText());
            }
        });
        bCancel = (Button)findViewById(R.id.button_cancel_name);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = (String) tvAllName.getText();
                int i = s.lastIndexOf("-");
                tvAllName.setText(s.substring(0,i));
            }
        });
        tvAllName = (TextView)findViewById(R.id.text_noms);
        bNext = (Button)findViewById(R.id.button_next_tournament);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] allNames = transformeAllNamesIntoStringArray(tvAllName.getText().toString());
                //TournamentSave.writeFileOnInternalStorage(context, TournamentSave.FILE_NAME, TournamentSave.assembleDataIntoFormeInit(allNames,Integer.parseInt(edNbManche.getText().toString())));
                //TournamentSave.writePatternInFile(context, TournamentSave.PatternInFile.ACTUAL_PLAYER,TournamentSave.findPatternInFile(context,));
                i.putExtra("Tournament", true);
                i.putExtra("Names",allNames);
                i.putExtra("NbManches", Integer.parseInt(edNbManche.getText().toString()));
                for(int j=0; j<allNames.length;j++)
                    Log.d("ChoiceTournament556",allNames[j]);
                Log.d("ChoiceTournament556","int : " + Integer.parseInt(edNbManche.getText().toString()) + ", noms : " + tvAllName.getText().toString() );
                startActivity(i);
            }
        });
        i=new Intent(this, GameScreen.class);
        context = this;

    }

    private String[] transformeAllNamesIntoStringArray(String names) {
        names = names.replaceFirst("Noms :-","");
        return names.split("-");
    }
}
