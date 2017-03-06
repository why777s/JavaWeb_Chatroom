package why777s.WebSocket;

/**
 * Created by wangzhaojun on 2017/2/25.
 */

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;


@ServerEndpoint("/echo")//注解使得此Java类声明成WebSocket的端点
public class EchoServer {
    private boolean first = true;
    private String name;//用户昵称
    //connect key为session的ID，value为此对象this
    private static final HashMap<String,Object> connect = new HashMap<String,Object>();
    //userMap key为session的ID，value为用户名
    private static final HashMap<String,String> userMap = new HashMap<String,String>();
    private Session session;
    @OnOpen
    public void start(Session session){
        this.session = session; //获取Seession,存入SashMap
        connect.put(session.getId(),this);
    }

    @OnMessage
    public void echo( String incomingMessage,Session session){
        EchoServer client = null ;
        //first 判断是否第一次传值，第一次的值是昵称，由web端的OnOpen传入
        if(first){
            this.name = incomingMessage;
            String message ="系统：欢迎"+name;
            //昵称和session的Id一一对应存储在HashMap
            userMap.put(session.getId(), name);
            //将message广播给所有用户
            for (String key : connect.keySet()) {
                try {
                    client = (EchoServer) connect.get(key);
                    synchronized (client) {
                        //给对应的Web端发送一个文本消息
                        client.session.getBasicRemote().sendText(message);
                    }
                } catch (IOException e) {
                    connect.remove(client);
                    try {
                        client.session.close();
                    } catch (IOException e1) {
                    }
                }
            }
            //输入昵称后，往后的交互传值都不是第一次
            first = false;
        }else{
            /**
             * incomingMessage的值为xxx@xxxxx的形式xxx为要发给的用户昵称，all则表示发给所有人
             * incomingMessage.split("@",2);以@为分隔符把字符串分为xxx和xxxxx两部分
             */
            String [] list = incomingMessage.split("@",2);
            if(list[0].equalsIgnoreCase("all")){ //all广播全部人
                sendAll(list[1],session);
            }else{
                boolean you = false;//标记是否找到发送的用户
                for(String key : userMap.keySet()){
                    if(list[0].equalsIgnoreCase(userMap.get(key))){
                        client = (EchoServer) connect.get(key);
                        synchronized (client) {
                            try {
                                //发送信息给指定的用户
                                client.session.getBasicRemote().sendText(userMap.get(session.getId())+"对你说:"+list[1]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        you = true;//找到指定用户标记为true
                        break;
                    }

                }
                //you为true则在自己页面显示自己对xxx说xxxxx,否则显示系统：无此用户
                if(you){
                    try {
                        session.getBasicRemote().sendText(getName()+"对"+ list[0]+"说:"+list[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        session.getBasicRemote().sendText("系统：无此用户");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

    @OnClose
    public void close(Session session){
        //当一用户退出时，对其他用户进行广播
        String message ="系统："+userMap.get(session.getId()) +"退出群聊";
        userMap.remove(session.getId());
        connect.remove(session.getId());
        for (String key : connect.keySet()) {
            EchoServer client = null ;
            try {
                client = (EchoServer) connect.get(key);
                synchronized (client) {
                    client.session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                connect.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    //对信息进行全体广播
    public static void sendAll(String mess,Session session){
        String who = null;
        for (String key : connect.keySet()) {
            EchoServer client = null ;
            try {
                client = (EchoServer) connect.get(key);
                if(key.equalsIgnoreCase(session.getId())){
                    who = "我: ";
                }else{
                    who = userMap.get(session.getId())+"对大家说 : ";
                }
                synchronized (client) {
                    KeywordFilter ky =new KeywordFilter();
                    String mess2 = ky.addKeyWordFilter(mess);
                    client.session.getBasicRemote().sendText(who+mess2);
                }
            } catch (IOException e) {
                connect.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public String getName(){
        return this.name;
    }
}