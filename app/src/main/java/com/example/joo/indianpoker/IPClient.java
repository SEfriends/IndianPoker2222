package com.example.joo.indianpoker;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.INPUT_SERVICE;

public class IPClient extends AbstractClient {
    final int MAX_PERSON=10000;

    private String clientID;
    Handler handler = new Handler();
    static String communication;
    static String communication2;
    String[] rank= new String[MAX_PERSON];
    String[] rankPoint= new String[MAX_PERSON];
    String[] rankid= new String[MAX_PERSON];
    boolean isTurn=GameMain.isTurn;
    Login login = new Login();

    static String playerName;
    String turnName;
    static int turn;
    static int op_cardNum;
    static int my_cardNum;
    static int current_point;
    static int op_point;
    static int my_point;


    public IPClient(String host, int port, String id) throws IOException {
        super(host, port);
        clientID = id;
        openConnection();
        sendToServer(clientID);
        // TODO Auto-generated constructor stub
    }

    // Instance methods ************************************************

    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg
     *            The message from the server.
     */
    public void handleMessageFromServer(Object msg) {


        String s = msg.toString().substring(4);
        println(s);
        if (s.startsWith("play")) {                                     //게임시작을 하는 부분/**끝**/
            communication="play";

            s.substring(4);
            String info = s.substring(s.indexOf("@")+1);
            op_cardNum =Integer.parseInt(info.substring(0,info.indexOf("@")));
            info = info.substring(info.indexOf("@")+1);
            turn= Integer.parseInt(info.substring(0,info.indexOf("@")));
            info= info.substring(info.indexOf("@")+1);
            turnName= info.substring(0,info.indexOf("@"));
            info= info.substring(info.indexOf("@")+1);
            my_cardNum =Integer.parseInt(info);
            println(op_cardNum+" "+turn+turnName+my_cardNum);

            //상대카드,턴수,현재베팅할사람,내카드드
            println(turnName+playerName);
            if(turnName.equals(playerName))
                GameMain.isTurn=true;
            else
                GameMain.isTurn=false;
        } else if(s.startsWith("wait")){                /***끝***/
            communication= "wait";

        }else if (s.startsWith("rank")) {               /***끝***/

            println("Ranking 정보를 받아왔습니다.");
            String text = s.substring(4);
            rank= text.split("\n");
            println("rank.length"+rank.length);
            for(int i=0;i<rank.length;i++) {
                rankid[i]=rank[i].substring(3,rank[i].indexOf("!"));
                rankPoint[i]=rank[i].substring(rank[i].indexOf("!")+1);
                println(rankid[i]+rankPoint[i]);

            }
        }else if(s.startsWith("Login")){     /***끝***/

            println("로그인 정보 : "+communication);
            communication ="Login Success";


        } else if(s.startsWith("warn")){

            communication = "Need LowerPoint";

        } else if(s.startsWith("bet")){         /***끝***/

            s=s.substring(3);
            String betMsg = s;
            current_point = Integer.parseInt(betMsg.substring(0,betMsg.indexOf("@")));
            betMsg= betMsg.substring(betMsg.indexOf("@")+1);
            my_point = Integer.parseInt(betMsg.substring(0,betMsg.indexOf("@")));
            betMsg= betMsg.substring(betMsg.indexOf("@")+1);
            turnName = betMsg.substring(0,betMsg.indexOf("@"));
            betMsg= betMsg.substring(betMsg.indexOf("@")+1);
            op_point = Integer.parseInt(betMsg);


            if(turnName.equals(playerName))
                GameMain.isTurn=true;
            else
                GameMain.isTurn=false;

        }else if(s.startsWith("win")){          //이기거나 졌거나 비겼을 경우 알림 /***끝***/

            s=s.substring(3);
            if(s=="턴승리!")
                communication2="win";
            else if(s=="턴패배!")
                communication2="lose";
            else
                communication2 = "same card";

        }else if(s.startsWith("turn")){         //1턴이 종료되었을때     /***끝***/
            communication="turn";
            GameMain.ScoreChange(Integer.toString(my_cardNum));
            String result = s.substring(4);
            op_cardNum = Integer.parseInt(result.substring(0,result.indexOf("@")));
            result= result.substring(result.indexOf("@")+1);
            turn = Integer.parseInt(result.substring(0,result.indexOf("@")));
            result= result.substring(result.indexOf("@")+1);
            my_point= Integer.parseInt(result.substring(0,result.indexOf("@")));
            result= result.substring(result.indexOf("@")+1);
            op_point = Integer.parseInt(result.substring(0,result.indexOf("@")));
            result=result.substring(result.indexOf("@")+1);
            my_cardNum = Integer.parseInt(result);


        }else if(s.startsWith("finish")){                       /***끝***/
            communication2="finish";
            s= s.substring(6);
            if(s.startsWith("turn")){
                communication="turnover";
            }else if(s.startsWith("p1")) {
                communication = "p1over";
            }else if(s.startsWith("p2")){
                communication="p2over";
            }


        }else if(s.startsWith("quit")){
            //로그인에 실패하면 다시 게임 start부분으로 돌아가는데 이는 네트워크가 끊김으로 어쩔수 없다.
            communication="Login Fail";
        }
    }

    /**
     * This method handles all data coming from the UI
     *
     * @param message
     *            The message from the UI.
     */
    public void handleMessageFromClientUI(String message) {
        try {
            sendToServer(message);
        } catch (IOException e) {
            System.out.println("서버에 메세지를 보낼 수 없습니다. 클라이언트를 종료합니다..");
            quit();
        }
    }

    public void accept() {
        try {
            BufferedReader fromConsole = new BufferedReader(
                    new InputStreamReader(System.in));
            String message;

            while (true) {
                message = fromConsole.readLine();
                handleMessageFromClientUI(message);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }

    public void setID(String id) {
        clientID = id;
    }

    public String getID() {
        return clientID;
    }

    public void println(final String s){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(INPUT_SERVICE,"받은 메시지:"+s);
            }
        });
    }
}