package com.oper.repository;

import com.oper.entity.PubView;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 李书根 on 2018/8/6.
 */
public interface PubViewRepository extends JpaRepository<PubView,Integer> {

    /**
     *根据id删除
     * @param id
     */
    Object  deletePubViewByViewId(Integer id);

    /**
     * 根据Id去查找
     * @param id
     * @return
     */
    PubView findPubViewByViewId(Integer id);

    /**
     * 根据did去查找所有的视图
     * @param did
     * @return
     */
    List<PubView> findAllByDirectoryId(Integer did);

    /**
     * 获取所有根据Id
     * @param did
     * @return
     */
    List<PubView> findAllByDirectoryIdIs(Integer did);

    boolean existsByViewName(String viewname);

    boolean existsByViewNameAndViewIdNot(String viewname,Integer viewId);

    @Query(value ="select pv.view_id,pv.view_name,pv.time_type,pv.directory_id,pdc.time_type as view_desc\n" +
            "from pub_dashboard_config pdc JOIN pub_view pv on pdc.view_id = pv.view_id \n" +
            "where pdc.dashboard_id = ?1", nativeQuery = true)
    PubView findTimeTypesByDid(Integer did) ;
}
