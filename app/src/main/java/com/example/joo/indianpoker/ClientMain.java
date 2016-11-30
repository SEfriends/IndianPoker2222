package com.example.joo.indianpoker;






//Client main start를 누르면 바로 로그인 화면으로 넘어감
// 네트워크를 이용하기위해서 무조건 스레드를 이용해야하고
//handler는 딜레이를 이용하여 수신받은내용을 나중에 업데이트하게
// or 스레드를 이용할때 ui에 출력하는경우 사용






import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class ClientMain extends AppCompatActivity {
    public static IPClient ipc;
    Handler handler = new Handler();
    private EditText editTextID, editTextPW;
    private Button loginBtr;

    public IPClient getIpc(){
        return ipc;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if(android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }

    }

    public void gameStartClicked(View v){                                       //start클릭부분
        Intent intent_login = new Intent(getApplicationContext(),Login.class);
        startActivity(intent_login);
        ConnectThread thread = new ConnectThread();
        thread.run();
    }
    class ConnectThread extends Thread{
        public void run(){
            println("hi");
            String host = "172.20.10.4";
            int port = 3000; // The port number
            String clientID = "클라이언트";

            try {
                println("서버로 연결 : "+host+" , "+port);

                ipc = new IPClient(host, port, clientID);

            } catch (ArrayIndexOutOfBoundsException e) {
                host="localhost";
            }catch(IOException e){
                e.printStackTrace();
            }

            //chat.accept(); // Wait for console data

        }
    }
    private void println(final String data){
        handler.post(new Runnable(){
            public void run(){
                Log.d(INPUT_SERVICE,data + "/n");
            }
        });

    }
}

