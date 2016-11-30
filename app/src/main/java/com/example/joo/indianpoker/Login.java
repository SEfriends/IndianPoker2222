package com.example.joo.indianpoker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import static com.example.joo.indianpoker.R.id.editID;
import static com.example.joo.indianpoker.R.id.editPW;

public class Login extends AppCompatActivity {
    private EditText editTextID, editTextPW;
    String playerName;

    Handler handler = new Handler();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login_click(View v) {
        ConnectThread thread = new ConnectThread();
        thread.run();
    }

    public void insert_click(View v){
        ConnectThread2 thread2 = new ConnectThread2();
        thread2.run();
    }

    class ConnectThread extends Thread{
        public void run(){
            editTextID  = (EditText) findViewById(editID);
            editTextPW = (EditText) findViewById(editPW);

            String s;
            try{
                IPClient.playerName= editTextID.getText().toString();
                s = "Login" +editTextID.getText().toString() + "!" + editTextPW.getText().toString();
                println("보낸메시지: "+s);

                ClientMain.ipc.sendToServer(s);
                result();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    class ConnectThread2 extends Thread{
        public void run(){
            editTextID  = (EditText) findViewById(editID);
            editTextPW = (EditText) findViewById(editPW);
            String s;
            try {
                s = "Insert" + editTextID.getText().toString() + "!" + editTextPW.getText().toString();
                println(s);
                ClientMain.ipc.sendToServer(s);
                result();
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }
    public void println(final String s){
        handler.post(new Runnable(){
            public void run(){
                Log.d(INPUT_SERVICE,s + "/n");
            }
        });

    }
    public void result(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                println("받은메시지: "+ClientMain.ipc.communication);

                if(ClientMain.ipc.communication=="Login Success") {
                    Intent intent_login = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent_login);
                }else {

                    Intent intent = new Intent(getApplicationContext(), ClientMain.class);
                    startActivity(intent);
                }
            }
        },10000);
    }


}
