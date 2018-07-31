package com.bjly.webservice.userManage;

import com.bjly.bjlymybatis.annotation.DataSourceAnnotation;
import com.bjly.webentity.userManage.LyUser;
import com.github.pagehelper.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


/**
 * 由MyBatis Generator工具自动生成
 */

@Service
public class LyUserService {

    @Autowired
    private com.bjly.webmapper.userManage.LyUserMapper lyUserMapper;

    /**
     * 新增
     */
    @DataSourceAnnotation
    public int insert(LyUser lyUser,String dsId) {
        return lyUserMapper.insert(lyUser);
    }

    /**
     * 修改
     */
    public int updateByPrimaryKey(LyUser lyUser) {
        return lyUserMapper.updateByPrimaryKey(lyUser);
    }

    /**
     * 删除
     */
    public int deleteByPrimaryKey(Object id) {
        return lyUserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过主键查询
     */
    public LyUser selectByPrimaryKey(Object id) {
        return lyUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     */
    public List<LyUser> selectAll() {
        Example example=new Example(LyUser.class);
        return lyUserMapper.selectByExample(example);
    }

    /**
     * 分页查询
     */
    public com.github.pagehelper.PageInfo<LyUser> select(LyUser lyUser, int pageNum, int pageSize) {
        Example example=new Example(LyUser.class);
        PageHelper.startPage(pageNum,pageSize);
        List<LyUser> pages = lyUserMapper.selectByExample(example);
        PageInfo<LyUser> list=new PageInfo<>(pages);
        return list;
    }
}