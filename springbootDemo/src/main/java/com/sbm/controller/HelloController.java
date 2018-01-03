package com.sbm.controller;

import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sbm
 * Created by yadong.zhang on com.sbm.controller
 * User：chenwangming
 * Date：2016/10/20
 * Time：18:26
 */
@Controller
@RequestMapping("/")
public class HelloController {

    private static final Logger LOGGER = LogManager.getLogger(HelloController.class);



    @RequestMapping("/json")
    @ResponseBody
    public Map<String,Object> json(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name","Flyat");
        map.put("age","25");
        map.put("sex","man");
        return map;
    }
    @RequestMapping("/index")
    public String index()
    {
        return "index";
    }
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age","25");
        model.addAttribute("sex","man");
        model.addAttribute("birth",new Date());
        return "hello";
    }


}