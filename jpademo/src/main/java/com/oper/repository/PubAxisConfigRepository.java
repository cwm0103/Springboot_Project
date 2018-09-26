package com.oper.repository;

import com.oper.entity.PubAxisConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.oper.entity.CimIoCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface PubAxisConfigRepository extends JpaRepository<PubAxisConfig,Integer>  {
    /**
     * 根据图表ID获取作标轴信息
     * @param dashboardId
     * @return
     */
    public List<PubAxisConfig>  findByDashboardId(Integer dashboardId);

    /**
     * 根据did来获取所有的
     * @param did
     * @return
     */
    List<PubAxisConfig>  findPubAxisConfigsByDashboardId(Integer did);

    /**
     * 根据主键Id去删除
     * @param id
     * @return
     */
    Object deletePubAxisConfigByAxisId(Integer id);

    /**
     * 根据主键id来查询一条数据
     * @param id
     * @return
     */
    PubAxisConfig findPubAxisConfigByAxisId(Integer id);
}