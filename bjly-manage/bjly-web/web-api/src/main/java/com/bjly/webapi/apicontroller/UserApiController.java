package com.bjly.webapi.apicontroller;

import com.bjly.webapi.utils.AjaxResult;
import com.bjly.webentity.userManage.LyUser;
import com.bjly.webservice.financeManage.LyTestService;
import com.bjly.webservice.userManage.LyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@CrossOrigin
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
        try
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
            if(insert>0)
            {
                return AjaxResult.success();
            }
        }catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public Object login(String userName,String password)
    {
        try{
            LyUser lyUser = lyUserService.selectLogin(userName, password,"db4");
            if(lyUser!=null)
            {
                return AjaxResult.success(lyUser);
            }
            return AjaxResult.error("500");
        }catch (Exception ex)
        {
            return  AjaxResult.error(ex.getMessage());
        }

    }

}