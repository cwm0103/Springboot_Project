package com.oper.repository;
import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cwm on 2018/8/6.
 */
public interface PubDashboardConfigRepository extends JpaRepository<PubDashboardConfig,Integer> {

    //删除方法
    void deletePubDashboardConfigByDashboardId(Integer id);
    //根据id查找方法
    PubDashboardConfig findPubDashboardConfigByDashboardId(Integer id);
    //根据视图viewid来查询所有的配置实体
    List<PubDashboardConfig> findAllByViewIdIs(Integer vid);
    //根据XY轴排序查询
    @Query(value = "select * from   pub_dashboard_config  where view_id=?1 order BY dasy,dasx", nativeQuery = true)
    @Transactional
    List<PubDashboardConfig> getAllByVidSort(Integer view_id);
    //根据标题，描述，类别来查询一个报表数据
    PubDashboardConfig findPubDashboardConfigByDashboardTitleEqualsAndChartTypeAndDashboardDesc(String title,String type,String desc);

}
