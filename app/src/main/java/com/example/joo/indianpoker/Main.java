package com.example.joo.indianpoker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import static com.example.joo.indianpoker.R.id.playStartGame;


public class Main extends AppCompatActivity {
    public static ClientMain ipc;
    Handler handler = new Handler();
    Button startBtn;
    Button helpBtr;
    Button rankingBtr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ipc = new ClientMain();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemenu);
        final Button startBtr= (Button)findViewById(playStartGame);
        final Button helpBtr= (Button)findViewById(R.id.HelpButton);
        final Button rankingBtr= (Button)findViewById(R.id.RankingButton);

        startBtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBtr.setClickable(false);
                startBtr.setText("wait..");
                helpBtr.setClickable(false);
                rankingBtr.setClickable(false);
                PlayThread playThread = new PlayThread();
                playThread.start();
            }
        });
    }

    public void ranking_click(View v){
        RankingThread rankThread = new RankingThread();//메뉴에서 ranking버튼
        rankThread.start();
    }




    public void help_click(View v){                             //메뉴에서 Help버튼
        Intent intent_help = new Intent(getApplicationContext(), Help1.class);
        startActivity(intent_help);
    }

    class PlayThread extends Thread{
        public void run(){
            println("기다림");
            String s = "Play";
            Button startBtr = (Button)findViewById(playStartGame);
            try{
                ipc.getIpc().sendToServer(s);
            }catch(IOException e){
                e.printStackTrace();
            }
            while(true) {
                try {

                    if(ipc.getIpc().communication=="play"){

                        startBtr.setClickable(true);

                        Intent intent_play = new Intent(getApplicationContext(),GameMain.class);
                        startActivity(intent_play);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class RankingThread extends Thread{
        public void run(){
            try {
                ipc.getIpc().sendToServer("Rank");
                Intent intent_ranking = new Intent(getApplicationContext(), Ranking.class);
                startActivity(intent_ranking);


            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void println(String s){
        Log.d(INPUT_SERVICE,s+"/n");
    }

    public void insertRank(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        },2000);
    }

}