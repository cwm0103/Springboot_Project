package com.rick.mappers;

import com.rick.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository  extends JpaRepository<User,Long>{
    //查询用户名称包含username字符串的用户对象
    List<User> findByUsernameContaining(String username);

    //获得单个用户对象，根据username和pwd的字段匹配
    User getByUsernameIsAndUserpwdIs(String username,String pwd);

    //精确匹配username的用户对象
    User getByUsernameIs(String username);

    //保存用户
    User save(User user);
}
