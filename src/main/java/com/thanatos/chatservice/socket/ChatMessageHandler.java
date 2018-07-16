package com.thanatos.chatservice.socket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * des:
 * author: thanatos
 * data: 2018/7/16
 */
@ServerEndpoint(value = "/socket/chat/{userId}")
@Component
public class ChatMessageHandler {

    private Session session;
    private static volatile ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();
    private static volatile ConcurrentHashMap<String, String> sessionIds = new ConcurrentHashMap<>();


    /**
     * socket连接成功后
     * @param session
     * @param userId
     */
    @OnOpen
    public synchronized void open(Session session, @PathParam(value = "userId") String userId){
        this.session = session;
        sessionPool.put(userId,session);
        sessionIds.put(session.getId(),userId);
        sendMessage("我是来自服务端的消息",userId);
    }

    /**
     * 连接当前用户后走此方法
     * @param message
     */
    @OnMessage
    public synchronized void onMessage(String message){
//        System.out.println("当前发送内容为："+message+"    \n 用户id:"+sessionIds.get(session.getId()));
//        sendAll("我是来自服务端的消息");
    }

    /**
     * 断开连接
     */
    @OnClose
    public synchronized void onClose(){
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
    }

    /**
     * 连接异常中断
     * @param session
     * @param e
     */
    @OnError
    public synchronized void onError(Session session, Throwable e){
        e.printStackTrace();
    }

    /**
     * 发送消息
     * @param message
     * @param userId
     */
    public synchronized static void sendMessage(String message, String userId){
        Session session = sessionPool.get(userId);
        if (session != null){
            try {
                session.getBasicRemote().sendText(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前在线人数
     * @return
     */
    public synchronized static int getOnlineNum(){
        return sessionPool.size();
    }

    /**
     * 获取当前在线用户
     * @return
     */
    public synchronized static List<String> getOnlineUsers(){
        List<String> ids = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : sessionIds.entrySet()) {
            ids.add(stringStringEntry.getValue());
        }
        return ids;
    }

    /**
     * 给所有用户发送消息
     * @param message
     */
    public synchronized static void sendAll(String message){
        for (String s : sessionIds.keySet()) {
            sendMessage(message, sessionIds.get(s));
        }
    }













}
