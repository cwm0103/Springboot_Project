package com.bjly.webservice.financeManage;

import com.bjly.webentity.financeManage.LyTest;
import com.github.pagehelper.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class LyTestService {
    @Autowired
    private com.bjly.webmapper.financeManage.LyTestMapper lyTestMapper;

    /**
     * 新增
     */
    public int insert(LyTest lyTest) {
        return lyTestMapper.insert(lyTest);
    }

    /**
     * 修改
     */
    public int updateByPrimaryKey(LyTest lyTest) {
        return lyTestMapper.updateByPrimaryKey(lyTest);
    }

    /**
     * 删除
     */
    public int deleteByPrimaryKey(Object id) {
        return lyTestMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过主键查询
     */
    public LyTest selectByPrimaryKey(Object id) {
        return lyTestMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     */
    public List<LyTest> selectAll() {
        Example example=new Example(LyTest.class);
        return lyTestMapper.selectByExample(example);
    }

    /**
     * 分页查询
     */
    public com.github.pagehelper.PageInfo<LyTest> select(LyTest lyTest, int pageNum, int pageSize) {
        Example example=new Example(LyTest.class);
         PageHelper .startPage(pageNum,pageSize);
        List<LyTest> pages = lyTestMapper.selectByExample(example);
         PageInfo<LyTest> list=new PageInfo<>(pages);
        return list;
    }
}