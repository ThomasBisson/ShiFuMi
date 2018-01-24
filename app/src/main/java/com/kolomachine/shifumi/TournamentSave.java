package com.kolomachine.shifumi;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by asus pc on 02/10/2017.
 */

public class TournamentSave {

    //Design in file :
    /*
    tournament_mode : true;name_players : ...;nb_manches : ...;actual_player : ...;actual_manche : ...;actual_res_for all : ...; //exemple : 1-0-0 1-1-0 0-1-1 (en bit 1=win 0=fail)
     */

    public enum PatternInFile {
        TOURNAMENT_MODE,
        NAME_PLAYERS,
        NB_MANCHES,
        ACTUAL_PLAYER,
        ACTUAL_MANCHE,
        ACTUAL_RES_FOR_ALL;
    }

    public static String FILE_NAME = "myData";


    public static void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File file = new File(mcoContext.getFilesDir(), sFileName);
        try {
        if(!file.exists()){
            file.createNewFile();
        }

            FileOutputStream writer = new FileOutputStream(file);
            writer.write(sBody.getBytes());
            writer.flush();
            writer.close();

        }catch (Exception e){}
    }

    public static String readFileOnInternalStorage(Context mcoContext, String sFileName) {
        FileInputStream fis = null;
        String res = "";
        try {
            fis = mcoContext.openFileInput(sFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            res = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return res;
    }

    public static String assembleDataIntoFormeInit(String[] allNames, int nbManches) {
        String s = "tournament_mode : true;name_players : ";
        for(int i=0; i<allNames.length; i++)
            if(i== allNames.length-1)
                s += allNames[i];
            else
                s += allNames[i] + ",";

        s+=";nb_manches : " + nbManches + ";nactual_player : ...;actual_manche : ...;actual_res_for_all : ...;";
        return s;
    }

    public static String findPatternInFile(Context mcoContext, PatternInFile pattern){
        String toResearch = "";
        if(pattern == PatternInFile.TOURNAMENT_MODE)
            toResearch = "tournament_mode";
        else if(pattern == PatternInFile.NAME_PLAYERS)
            toResearch = "name_players";
        else if(pattern == PatternInFile.NB_MANCHES)
            toResearch = "nb_manches";
        else if(pattern == PatternInFile.ACTUAL_PLAYER)
            toResearch = "actual_player";
        else if(pattern == PatternInFile.ACTUAL_MANCHE)
            toResearch = "actual_manche";
        else if(pattern == PatternInFile.ACTUAL_RES_FOR_ALL)
            toResearch = "actual_res_for_all";
        else {
            return "";
        }

        String entireFile = readFileOnInternalStorage(mcoContext, FILE_NAME);
        String res = "";
        for(int indice = entireFile.indexOf(toResearch)+toResearch.length()+3; indice!=-1; indice++) {
            if (entireFile.charAt(indice) != ';')
                res += entireFile.charAt(indice);
            else
                indice = -2;
        }

        return res;
    }

    public static void writePatternInFile(Context mcoContext, PatternInFile pattern, String sBody){
        int i = 0;
        if(pattern == PatternInFile.TOURNAMENT_MODE)
            i = 0;
        else if(pattern == PatternInFile.NAME_PLAYERS)
            i = 1;
        else if(pattern == PatternInFile.NB_MANCHES)
            i = 2;
        else if(pattern == PatternInFile.ACTUAL_PLAYER)
            i = 3;
        else if(pattern == PatternInFile.ACTUAL_MANCHE)
            i = 4;
        else if(pattern == PatternInFile.ACTUAL_RES_FOR_ALL)
            i = 5;

        String[] sBodySplit = readFileOnInternalStorage(mcoContext, FILE_NAME).split(";");
        sBodySplit[i] = pattern.toString().toLowerCase() + " : " + sBody;
        String res = "";

        for(int j=0; j<sBodySplit.length; j++) {
            res += sBodySplit[j] + ";";
        }
        writeFileOnInternalStorage(mcoContext, FILE_NAME, res);
    }

    public static String[] transformeAllNamesIntoArray(String allNames) {
        return allNames.split("-");
    }

    public static String searchNextName(Context mcoContext) {
        String actualPlayer = findPatternInFile(mcoContext,PatternInFile.ACTUAL_PLAYER);
        String[] allNames = transformeAllNamesIntoArray(findPatternInFile(mcoContext, PatternInFile.NAME_PLAYERS));
        for(int i=0; i<allNames.length; i++) {
            if(allNames[i].equals(actualPlayer)) {
                if(allNames[i+1] != null)
                    return allNames[i+1];
                else
                    return null;
            }
        }
        return null;
    }

    //TODO fonction qui marque le score


}
