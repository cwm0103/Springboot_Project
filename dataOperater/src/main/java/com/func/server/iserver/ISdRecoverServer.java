package com.func.server.iserver;

import com.func.entity.SdRecover;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/16.
 */
public interface ISdRecoverServer {
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
