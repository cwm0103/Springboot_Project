package com.rick.mappers;

import com.rick.entities.User;
import com.rick.entities.Weibo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface WeiboRepository extends JpaRepository<Weibo,Long>,JpaSpecificationExecutor<Weibo> {

    @Query("select w from Weibo w where w.user.username = ?1")
    List<Weibo> searchUserWeibo(@Param("username") String username);

    @Query(value = "select w from Weibo w where w.user.username = ?1")
    List<Weibo> searchUserWeibo(@Param("username") String username, Sort sort);

//    @Modifying
//    @Transactional(readOnly = false)
//    @Query(value = "update Weibo w set w.weiboText = :text where w.user = :user")
//    int setUserWeiboContent(@Param("text")String weiboText,@Param("user")User user);
//
//    Page<Weibo> findByUserIsAndWeiboTextContaining(User user, String weiboText, Pageable pageable);
//
//    @Transactional(readOnly = false)
//    int deleteByUser(User user);

}
