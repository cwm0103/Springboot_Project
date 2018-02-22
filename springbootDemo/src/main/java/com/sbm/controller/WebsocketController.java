package com.sbm.controller;

import com.sbm.websocket.Message;
import com.sbm.websocket.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenwangming on 2018/1/3.
 */
@Controller
@RequestMapping("/")
public class WebsocketController {

//    @RequestMapping("/ws")
//    public String ws()
//    {
//        return "ws";
//    }
//
//    @MessageMapping("/welcome")//浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
//    @SendTo("/topic/getResponse")//服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
//    public Response say(Message message) throws Exception {
//        Thread.sleep(1000);
//        return new Response("Welcome, " + message.getName() + "!");
//    }
}
