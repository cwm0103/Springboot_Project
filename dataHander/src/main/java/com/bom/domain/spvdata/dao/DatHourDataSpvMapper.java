package com.bom.domain.spvdata.dao;

import com.bom.domain.Mapper;
import com.bom.domain.spvdata.model.DatHourDataSpv;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface DatHourDataSpvMapper extends com.bom.domain.Mapper<DatHourDataSpv> {

    /**
     * 查询时间表最后一条数据
     * @param station_id
     * @param code
     * @return
     */
    Object selectHourdataLast(@Param("station_id") String station_id,@Param("code") String code);

    /**
     * 插入一个实体 datHourDataSpv
     * @param datHourDataSpv
     * @return
     */
    Integer insertHourdata(DatHourDataSpv datHourDataSpv);

    /**
     * 修改一个实体 datHourDataSpv
     * @param datHourDataSpv
     * @return
     */
    Integer updateHourdata(DatHourDataSpv datHourDataSpv);


}