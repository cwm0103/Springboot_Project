package com.oper.repository;

import com.oper.dto.DashboardDataDTO;
import com.oper.entity.PubDashboardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * cwm
 * add
 * 2018-8-8
 * 数据仓库
 */
public interface PubDashboardDataRepository extends JpaRepository<PubDashboardData,Integer> {

    /**
     *   //根据id来查找
     * @param id
     * @return
     */

    PubDashboardData findPubDashboardDataByDataId(Integer id);

    /**
     *   //根据id删除数据
     * @param id
     * @return
     */

    Object deletePubDashboardDataByDataId(Integer id);

    /**
     *   //根据did来查询所有的数据
     * @param did
     * @return
     */
    List<PubDashboardData> findPubDashboardDataByDashboardIdIs(Integer did);

    /**
     *   //根据did来查询所有的数据关联出对象名
     * @param did
     * @return
     */
    @Query(value = "SELECT new com.oper.dto.DashboardDataDTO(pd.dataId,pi.instanceName,pd.dataName,pd.unit,pd.dataType) FROM PubDashboardData  pd JOIN  PubCimInstance pi on pd.instanceCode=pi.instanceCode WHERE pd.dashboardId = ?1")
    List<DashboardDataDTO> getPubDashboardDataDtoByDashboardIdIs(Integer did);

    /**
     * //根据dashboard_id来删除数据
     * @param did
     * @return
     */
    Object deletePubDashboardDataByDashboardId(Integer did);

}
