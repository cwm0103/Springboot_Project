package com.rick.controller;

import com.rick.entities.User;
import com.rick.entities.Weibo;
import com.rick.mappers.UserRepository;
import com.rick.mappers.WeiboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("test")
@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeiboRepository weiboRepository;

    @RequestMapping("/searchUser/{username}")
    public @ResponseBody
    List<User> searchUser(@PathVariable("username") String username) {
        List<User> result = this.userRepository.findByUsernameContaining(username);
        return result;
    }
    @RequestMapping("/useradd")
    @ResponseBody
    @Transactional(value = "transactionManager")
    public   String AddUser()
    {
        for (int i=0;i<100;i++)
        {
            User user=new User();
            user.setUsername("2测试"+i);
            user.setUserpwd("123456");
            User save = this.userRepository.save(user);
        }


        return "成功！" ;
    }

    @RequestMapping("/getUser")
    public @ResponseBody User getUser(String username, String userpwd)
    {
        User user=this.userRepository.getByUsernameIsAndUserpwdIs(username,userpwd);
        return user;
    }

    @RequestMapping("/username")
    @ResponseBody
    public List<Weibo> getUserWeibo(String username) {
        //Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"weiboId"));
        return this.weiboRepository.searchUserWeibo(username);
    }

}
