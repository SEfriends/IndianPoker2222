package com.example.joo.indianpoker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static com.example.joo.indianpoker.R.id.RoundText;
import static com.example.joo.indianpoker.R.id.imageView;
import static com.example.joo.indianpoker.R.id.imageView2;
import static com.example.joo.indianpoker.R.id.my_Betting;
import static com.example.joo.indianpoker.R.id.op_Betting;
import static com.example.joo.indianpoker.R.id.text_my_point;
import static com.example.joo.indianpoker.R.id.text_op_point;

public class GameMain extends AppCompatActivity{

    private int arr[] = new int[10];
    public Button PointBtn;
    Button GiveupBtn;
    Button Point10Btn;
    Button Point20Btn;
    Button Point30Btn;                  //게임진행 버튼들
    Button ExitBtn;

    TextView MyPoint;
    TextView OpPoint;                   //기본점수판
    TextView myBetting;
    TextView ckBetting;                 //배팅점수판
    TextView RoundView;
    ImageView op_img;
    ImageView my_img;                     //카드이미지
    Toast toast_start;
    Toast toast_continue;
    Toast toast_retouch;
    Toast toast_result;
    Toast toast_turn;
    static boolean isTurn;

    ClientMain ipc = new ClientMain();
    Handler handler1 = new Handler();
    static ScoreHandler scorehandler;
    UIHandler handler;
    Login login = new Login();

    int my_cardNum;
    int op_cardNum;
    int turn;
    String turnName;
    int current_point=0;
    int op_point=200;
    int my_point=200;
    int total_point =0;
    int bet_point;

    private void initCard() {
        arr[0] = R.drawable.card1;
        arr[1] = R.drawable.card2;
        arr[2] = R.drawable.card3;
        arr[3] = R.drawable.card4;
        arr[4] = R.drawable.card5;
        arr[5] = R.drawable.card6;
        arr[6] = R.drawable.card7;
        arr[7] = R.drawable.card8;
        arr[8] = R.drawable.card9;
        arr[9] = R.drawable.card10;

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemain);

        handler = new UIHandler();
        scorehandler = new ScoreHandler();
        PointBtn = (Button) findViewById(R.id.RankingButton);    //점수 올리기 버튼
        GiveupBtn = (Button) findViewById(R.id.giveup_button);   //점수 배팅 포기 버튼
        Point10Btn = (Button) findViewById( R.id.point10);
        Point20Btn = (Button) findViewById( R.id.point20);
        ckBetting= (TextView)findViewById(op_Betting);
        Point30Btn = (Button) findViewById( R.id.point30);
        ExitBtn = (Button) findViewById(R.id.HelpButton);           //종료 버튼
        initCard();                                                     //카드화면배열에 넣기

        MyPoint= (TextView)findViewById(text_my_point);
        OpPoint= (TextView)findViewById(text_op_point);
        myBetting= (TextView)findViewById(my_Betting);
        RoundView= (TextView)findViewById(RoundText);

        toast_start = Toast.makeText(getApplicationContext(),"게임을 시작합니다.",Toast.LENGTH_LONG); //게임시작을 알림
        toast_continue= Toast.makeText(getApplicationContext(),"게임을 진행합니다.",Toast.LENGTH_LONG);
        toast_retouch= Toast.makeText(getApplicationContext(),"높은 점수를 터치해주세요",Toast.LENGTH_SHORT);

        toast_turn = Toast.makeText(getApplicationContext(),"당신의 턴입니다.",Toast.LENGTH_SHORT);
        toast_start.show();


        my_img = (ImageView) findViewById(imageView2);               //상대편이미지 아이디가져오기
        op_img = (ImageView) findViewById(imageView);

        op_cardNum= ClientMain.ipc.op_cardNum;             //msg: play로 받은 상대카드,turn,현재배팅순서이름
        turn = ClientMain.ipc.turn;
        turnName = ClientMain.ipc.turnName;
        my_cardNum=ClientMain.ipc.my_cardNum;
        current_point = ClientMain.ipc.current_point;

        PointBtn.setEnabled(false);
        GiveupBtn.setEnabled(false);
        op_img.setImageResource(arr[op_cardNum-1]);
    /*
        if(turnName == login.playerName) {                    // 내차례이면 버튼열어주고, 내차례아니면 버튼 닫아주고
            buttonStart();
        }else if(turnName!= login.playerName){
            buttonStop();
        }
    */

        //처음엔 모두 클릭 불가


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        //나의 턴임을 알리는 메세지를 받으면 모두 true
        //코드

        //메세지에 상대방 카드번호가 오면 카드화면을 해당 숫자화면으로 바꿔준다.
        //


        Point10Btn.setOnClickListener(new View.OnClickListener() {                     // 나의 턴에 10점추가
            @Override
            public void onClick(View v) {
                // 점수올리기기 활성화
                if(IPClient.communication2=="finish")
                    Finish();
                myBetting.setText("10");
                UIchange();
                // 메세지에 10점 입력
                if(isTurn){
                    toast_turn.show();
                    PointBtn.setEnabled(true);
                    GiveupBtn.setEnabled(true);


                }

            }

        });
        Point20Btn.setOnClickListener(new View.OnClickListener() {                     // 나의 턴에 20점추가
            @Override
            public void onClick(View v) {
                if(IPClient.communication2=="finish")
                    Finish();
                // 점수올리기기 활성화
                myBetting.setText("20");
                UIchange();
                // 메세지에 10점 입력
                if(isTurn){
                    toast_turn.show();
                    PointBtn.setEnabled(true);
                    GiveupBtn.setEnabled(true);

                }
            }

        });
        Point30Btn.setOnClickListener(new View.OnClickListener() {                     // 나의 턴에 30점추가
            @Override
            public void onClick(View v) {
                // 점수올리기기 활성화
                if(IPClient.communication2=="finish")
                    Finish();
                myBetting.setText("30");
                UIchange();
                // 메세지에 10점 입력
                if(isTurn){
                    toast_turn.show();
                    PointBtn.setEnabled(true);
                    GiveupBtn.setEnabled(true);

                }
            }

        });
        GiveupBtn.setOnClickListener(new View.OnClickListener() {                       // 나의 턴에 포기를 누르는 경우
            @Override
            public void onClick(View view) {
                //배팅점수 다 상대방에게 전달
                if(isTurn) {
                    GiveupBtn.setClickable(false);
                    if (my_cardNum == 10) {
                        my_point = my_point - 100;
                        if (my_point < 0)
                            my_point = 0;
                    }
                    GiveupThread giveupThread = new GiveupThread();
                    giveupThread.run();
                }
            }
        });

        PointBtn.setOnClickListener(new View.OnClickListener() { //게임시작 후 점수보내기를 누르는 경우
            @Override
            public void onClick(View v) {
                if(isTurn) {
                    bet_point = Integer.parseInt(myBetting.getText().toString());

                    BettingThread thread1 = new BettingThread();
                    thread1.start();
                }
            }
        });
    }

    public void buttonStop(){              // 게임시작하면 게임중으로 바뀜
        PointBtn.setEnabled(false);
        GiveupBtn.setEnabled(false);

    }

    public void println(final String s){                        //안스에 출력하는 메소드
        handler1.post(new Runnable(){
            public void run(){
                Log.d(INPUT_SERVICE,s + "/n");
            }
        });

    }

    /************************배팅하는 터치후 생기는일*********************/

    class BettingThread extends Thread{                             //베팅 서버로 보내는 클래스
        public void run(){
            try {
                ClientMain.ipc.sendToServer("betting" + bet_point);
                bettingResult();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public void bettingResult(){
        handler1.postDelayed(new Runnable(){
            public void run(){                                  //betting 업데이트하여 삽입
                String communication = ClientMain.ipc.communication;
                String communication2 = ClientMain.ipc.communication2;


                if(communication=="Need LowerPoint"){               //warn: 점수가 얼마 없어 낮은 점수를 배팅해야될 때
                    toast_retouch.show();
                    return;
                }

                current_point= ClientMain.ipc.current_point;
                my_point=ClientMain.ipc.my_point;
                turnName= ClientMain.ipc.turnName;
                op_point= ClientMain.ipc.op_point;

                UIchange();
                /*
                ckBetting.setText(Integer.toString(current_point));
                MyPoint.setText(Integer.toString(my_point));
                OpPoint.setText(Integer.toString(op_point));
                */

                if(communication =="turn"&&communication2=="same card"){ //무승부일 경우
                    //toast_result= Toast.makeText(getApplicationContext(),"당신의 카드숫자는 "+my_cardNum+"입니다.",Toast.LENGTH_LONG);
                    //toast_result.show();
                    op_cardNum=ClientMain.ipc.op_cardNum;
                    my_cardNum=ClientMain.ipc.my_cardNum;
                    turn = ClientMain.ipc.turn;
                    my_point = ClientMain.ipc.my_point;
                    op_point = ClientMain.ipc.op_point;

                    UIchange();
                    /*
                    MyPoint.setText(Integer.toString(my_point));
                    OpPoint.setText(Integer.toString(op_point));
                    RoundView.setText("Round"+Integer.toString(turn));
                    op_img.setImageResource(arr[op_cardNum-1]);
                    */

                }else if(communication =="turn"){                       //승이거나 패일경우
                    //toast_result= Toast.makeText(getApplicationContext(),"당신의 카드숫자는 "+my_cardNum+"입니다.",Toast.LENGTH_LONG);
                    //toast_result.show();
                    op_cardNum=ClientMain.ipc.op_cardNum;
                    my_cardNum=ClientMain.ipc.my_cardNum;
                    turn = ClientMain.ipc.turn;
                    my_point = ClientMain.ipc.my_point;
                    op_point = ClientMain.ipc.op_point;
                    IPClient.current_point=0;

                    UIchange();
                    /*
                    MyPoint.setText(Integer.toString(my_point));
                    OpPoint.setText(Integer.toString(op_point));
                    RoundView.setText("Round"+Integer.toString(turn));
                    ckBetting.setText("0");
                    */

                }

                //버튼을 자신의 턴전까지 이용x
                Finish();
                buttonStop();


            }
        },2000);

    }

    // UI를 바꾸기위해 부르는 메소드
    public void UIchange(){
        Message message= handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putInt("current_point",IPClient.current_point);
        bundle.putInt("my_point",IPClient.my_point);
        bundle.putInt("op_point",IPClient.op_point);
        bundle.putInt("Round",IPClient.turn);
        bundle.putInt("op_cardNum",IPClient.op_cardNum);
        bundle.putString("communication",IPClient.communication);

        message.setData(bundle);
        handler.sendMessage(message);
    }
    public static void ScoreChange(String s){
        Message message= scorehandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("finishScore",s);
        message.setData(bundle);
        scorehandler.sendMessage(message);
    }
    /*********************************포기 누르면 생기는 일 **********************************/

    class GiveupThread extends Thread{

        public void run(){
            try{
                ClientMain.ipc.sendToServer("giveUp");
                giveupResult();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void giveupResult() {
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                String communication = ClientMain.ipc.communication;
                //toast_result= Toast.makeText(getApplicationContext(),"당신의 카드숫자는 "+my_cardNum+"입니다.",Toast.LENGTH_LONG);
                //toast_result.show();
                op_cardNum = ClientMain.ipc.op_cardNum;
                my_cardNum = ClientMain.ipc.my_cardNum;
                turn = ClientMain.ipc.turn;
                my_point = ClientMain.ipc.my_point;
                op_point = ClientMain.ipc.op_point;
                IPClient.current_point = 0;

                GiveupBtn.setClickable(true);
                UIchange();
                /*MyPoint.setText(Integer.toString(my_point));
                OpPoint.setText(Integer.toString(op_point));
                RoundView.setText("Round" + Integer.toString(turn));
                op_img.setImageResource(arr[op_cardNum-1]);
                ckBetting.setText("0");*/


                //버튼을 자신의 턴전까지 이용x
                Finish();

            }
        }, 2000);
    }

    //포커가 자신의 순서가 아닐때 순서를 기다리는 listen

    //  종료 하는 부분
    public void Finish(){
        String communication = ClientMain.ipc.communication;

        if (communication == "turnover") {                         //10번턴 마치면 종료
            AlertDialog.Builder finishbuilder1 = new AlertDialog.Builder(GameMain.this);
            finishbuilder1.setTitle("안내");
            finishbuilder1.setMessage("총 10번의 턴을 마치셨습니다.");
            finishbuilder1.setIcon(android.R.drawable.ic_dialog_alert);
            finishbuilder1.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = finishbuilder1.create();
            dialog.show();

        } else if (communication == "p1over") {                  // 플레이어1 점수가 0점이 되면
            AlertDialog.Builder finishbuilder2 = new AlertDialog.Builder(GameMain.this);
            finishbuilder2.setTitle("안내");
            finishbuilder2.setMessage("Player1의 point가 0이 되어 종료되었습니다.");
            finishbuilder2.setIcon(android.R.drawable.ic_dialog_alert);
            finishbuilder2.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = finishbuilder2.create();
            dialog.show();
        } else if (communication == "p2over") {                 // 플레이어2 점수가 0점이 되면
            AlertDialog.Builder finishbuilder3 = new AlertDialog.Builder(GameMain.this);
            finishbuilder3.setTitle("안내");
            finishbuilder3.setMessage("Player1의 point가 0이 되어 종료되었습니다.");
            finishbuilder3.setIcon(android.R.drawable.ic_dialog_alert);
            finishbuilder3.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = finishbuilder3.create();
            dialog.show();
        }
    }

    /**************UI는 handler로 업데이트해줘야 한답니다.*************/
    class UIHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int op_cardNum= -1;
            int current_point =-1;
            int  my_point =-1;
            int op_point =-1;
            int turn =-1;
            String communication = "NoRef";
            current_point = bundle.getInt("current_point");
            my_point= bundle.getInt("my_point");
            op_point= bundle.getInt("op_point");
            op_cardNum = bundle.getInt("op_cardNum");
            turn = bundle.getInt("Round");
            communication= bundle.getString("communication");

            if(current_point != -1)
                ckBetting.setText(Integer.toString(current_point));
            if(my_point != -1)
                MyPoint.setText(Integer.toString(my_point));
            if(op_point != -1)
                OpPoint.setText(Integer.toString(op_point));
            if(op_cardNum != -1)
                op_img.setImageResource(arr[op_cardNum-1]);
            if(turn != -1)
                RoundView.setText("Round"+Integer.toString(turn));
            if(communication=="finish")
                Finish();
        }
    }
    class ScoreHandler extends Handler{
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String update_toast = bundle.getString("finishScore");
            Toast toast_result= Toast.makeText(getApplicationContext(),"당신의 카드숫자는 "+update_toast+"입니다.",Toast.LENGTH_LONG);
            toast_result.show();
        }
    }
}