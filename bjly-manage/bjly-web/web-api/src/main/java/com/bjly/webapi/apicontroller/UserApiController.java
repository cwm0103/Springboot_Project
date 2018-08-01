package com.bjly.webapi.apicontroller;

import com.bjly.webentity.userManage.LyUser;
import com.bjly.webservice.financeManage.LyTestService;
import com.bjly.webservice.userManage.LyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserApiController {

    @Autowired
    @Qualifier("lyUserService")
    private LyUserService lyUserService;

//    @Autowired
//    private LyTestService lyTestService;
    /**
     * 用户添加
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public Object addUser()
    {
        LyUser lyUser=new LyUser();
        lyUser.setUAge(10);
        lyUser.setUCode("cwm");
        lyUser.setUEducation(1);
        lyUser.setUName("陈王明");
        lyUser.setUPhoto("//");
        lyUser.setUSex("1");
        lyUser.setUPwd("123456");
        lyUser.setUTel("18500328373");
        int insert = lyUserService.insert(lyUser,"db4");

        return insert;
    }

}