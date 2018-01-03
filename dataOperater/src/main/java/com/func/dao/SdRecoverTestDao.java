package com.func.dao;

import com.func.entity.SdRecover;
import com.func.entity.SdRecoverTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created by chenwangming on 2017/8/18.
 */
@Mapper
public interface SdRecoverTestDao {

    /**
     * 查询所有的数据
     * @return
     */
    public List<SdRecoverTest> getSdRecoverTestList();

    /**
     *  通过分组查询io点
     * @return
     */
    public List<SdRecoverTest> getIOCodeList();


    /**
     * 通过Io来查询点数据
     * @param sd_io
     * @return
     */
    public List<SdRecoverTest> getSdRecoverTestListByIo(String sd_io);

    /**
     * 通过io 点来获取分组日期
     * @param sd_io
     * @return
     */
    public  List<Date> getIoDataTimeByIo(String sd_io);


    /**
     * 批量保存到数据库
     * @param sdlist
     * @return
     */
    public int saveBatchData(List<SdRecoverTest> sdlist);
}
