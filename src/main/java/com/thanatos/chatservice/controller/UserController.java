package com.thanatos.chatservice.controller;

import com.thanatos.chatservice.socket.ChatMessageHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * des: 用户控制器
 * author: thanatos
 * data: 2018/7/16
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @RequestMapping("index")
    public Map<String, Object> index(){
        return Collections.singletonMap("tag","hello!");
    }

    @RequestMapping("sendAll")
    public Map<String, Object> sendMessage(){
        ChatMessageHandler.sendAll("哈哈哈");
        return Collections.singletonMap("msg","success");
    }

    @RequestMapping("sendMessage")
    public Map<String, Object> sendMessage(HttpServletRequest request){
        String userId = request.getParameter("userId");
        ChatMessageHandler.sendMessage("我是单独发的消息",userId);
        return Collections.singletonMap("msg","success");
    }

    @RequestMapping("getOnlineNum")
    public Map<String, Object> getOnlineNum(){
        return Collections.singletonMap("data",ChatMessageHandler.getOnlineNum());
    }

    @RequestMapping("getOnlineUser")
    public Map<String, Object> getOnlineUser(){
        return Collections.singletonMap("data",ChatMessageHandler.getOnlineUsers());
    }
}
