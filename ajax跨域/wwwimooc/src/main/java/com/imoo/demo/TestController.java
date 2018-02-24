package com.imoo.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
//@CrossOrigin
public class TestController {

    @GetMapping("get1")
    public RusultBean get1()
    {
        System.out.println("TestController.get1");
        return new RusultBean("get1 ok");
    }

    @PostMapping("postjson")
    public RusultBean postjson(@RequestBody User user)
    {
        System.out.println("TestController.postjson");

        return new RusultBean("postjson "+user.getName());
    }

    @GetMapping("getcookie")
    public RusultBean getCookie(@CookieValue(value = "cookie1")String cookie1)
    {
        System.out.println("TestController.getcookie");
        return new RusultBean("getcookie "+cookie1);
    }
    @GetMapping("getHeader")
    public RusultBean getHeader(@RequestHeader("x-header1")String header1,@RequestHeader("x-header2")String header2){
        System.out.println("TestController.getHeader");
        return new RusultBean("getHeader "+header1+" "+header2);
    }
}
