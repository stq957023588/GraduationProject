package com.slz.demo.webscokt;



import com.alibaba.fastjson.JSON;
import com.slz.demo.VO.ChatMsg;
import com.slz.demo.VO.Message;
import com.slz.demo.pojo.Chat;
import com.slz.demo.pojo.City;
import com.slz.demo.pojo.User;
import com.slz.demo.service.ChatSerImp;
import com.slz.demo.service.CitySerImp;
import com.slz.demo.service.StrategySerImp;
import com.slz.demo.service.UserSerImp;
import com.slz.demo.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
//@ServerEndpoint(value="/websocket")
@ServerEndpoint(value="/websocket/{param}")
@Component
public class WebSocketTest {
    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext applicationContext){
        context=applicationContext;
    }


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();
    private static ConcurrentHashMap<String ,WebSocketTest> webSocketMap=new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //当前用户账户
    private String account;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "param")String param, Session session){
        this.session = session;
        this.account=param;
        webSocketMap.put(param,this);//加入map中
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println(param+"连接加入！当前在线人数为" + getOnlineCount());
        for (String str:webSocketMap.keySet()){
            System.out.println(str);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        webSocketMap.remove(account);
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    public void fansheTest(){
        System.out.println("fanshetestPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
    }
    public void fansheTest(String str){
        System.out.println("fanshetestPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
    }


    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的二进制消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        Map<String ,String > msgMap=(Map)JSON.parse(Tools.toString(message));
        String toAccount=msgMap.get("to");
        String content=msgMap.get("data");
        //将聊天记录存放到数据库中
        ChatSerImp chatSerImp=context.getBean(ChatSerImp.class);
        System.out.println(account+"\t"+toAccount+"\t");
        Chat chat=chatSerImp.save(account,toAccount,content);

        UserSerImp userSerImp=context.getBean(UserSerImp.class);
        ChatMsg chatMsg=new ChatMsg();
        User user=userSerImp.findByAccount(chat.getFromAccount());
        user.safe();
        chatMsg.setUser1(user);
        chatMsg.setContent(chat.getContent());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        chatMsg.setTime(sdf.format(chat.getTime()));

        //向制定用户发送信息
        try {
            webSocketMap.get(toAccount).session.getBasicRemote().sendText(JSON.toJSONString(chatMsg));
        } catch (NullPointerException e){
//            this.session.getBasicRemote().sendText("不在线");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }
}
