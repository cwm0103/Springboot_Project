package com.bjly.webservice.userManage;

import com.bjly.webentity.userManage.LyRole;
import com.github.pagehelper.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class LyRoleService {
    @Autowired
    private com.bjly.webmapper.userManage.LyRoleMapper lyRoleMapper;

    /**
     * 新增
     */
    public int insert(LyRole lyRole) {
        return lyRoleMapper.insert(lyRole);
    }

    /**
     * 修改
     */
    public int updateByPrimaryKey(LyRole lyRole) {
        return lyRoleMapper.updateByPrimaryKey(lyRole);
    }

    /**
     * 删除
     */
    public int deleteByPrimaryKey(Object id) {
        return lyRoleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过主键查询
     */
    public LyRole selectByPrimaryKey(Object id) {
        return lyRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     */
    public List<LyRole> selectAll() {
        Example example=new Example(LyRole.class);
        return lyRoleMapper.selectByExample(example);
    }

    /**
     * 分页查询
     */
    public com.github.pagehelper.PageInfo<LyRole> select(LyRole lyRole, int pageNum, int pageSize) {
        Example example=new Example(LyRole.class);
         PageHelper .startPage(pageNum,pageSize);
        List<LyRole> pages = lyRoleMapper.selectByExample(example);
         PageInfo<LyRole> list=new PageInfo<>(pages);
        return list;
    }
}