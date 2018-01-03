package com.func.dao;

import com.func.entity.SdRecover;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/16.
 */
@Mapper
public interface SdRecoverDao {
    /**
     * 查询所有的数据
     * @return
     */
    public List<SdRecover> getSdRecoverList();

    /**
     * 批量保存到数据库
     * @param sdlist
     * @return
     */
    public int saveData(List<SdRecover> sdlist);

}
