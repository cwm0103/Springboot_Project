package com.sbm.controller;

import com.github.pagehelper.PageHelper;
import com.sbm.domain.MessageDemo.model.Message;
import com.sbm.domain.MessageDemo.service.IMessageService;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by chenwangming on 2018/1/3.
 */
@Controller
public class MessageController {
    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(HelloController.class);

    @Resource
    private IMessageService messageService;

    @RequestMapping("/message/{currentPage}")
    public String message(@PathVariable("currentPage") Integer currentPage, Model model){
        if(currentPage!= null){
            PageHelper.startPage(currentPage, 11);
        }
        LOGGER.debug("程序执行的时候输出Log日志...");
        List<Message> messages = messageService.list();
        model.addAttribute("messages", messages);
        return "message";
    }
}
