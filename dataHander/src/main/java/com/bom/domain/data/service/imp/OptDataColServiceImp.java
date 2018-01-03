package com.bom.domain.data.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.model.OptDataCol;
import com.bom.domain.data.service.IOptDataColService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class OptDataColServiceImp extends BaseService<OptDataCol> implements IOptDataColService {
    @Autowired
    private com.bom.domain.data.dao.OptDataColMapper optDataColMapper;

    @Override
    @TargetDataSource(name="datadb")
    public void findAll() {
         List<OptDataCol> optDataCols = optDataColMapper.selectAll() ;
         for(OptDataCol optDataCol : optDataCols){
            // System.out.println("数据："+optDataCol.getCode()+"  "+optDataCol.getValue()) ;
         }
    }

    @TargetDataSource(name="datadb")
     public void  insertDataColBatch(List<OptDataCol> dataColList)
    {
         optDataColMapper.insertDataColBatch(dataColList);
    }

    @TargetDataSource(name="datadb")
    public List<OptDataCol> selectDataColTop300()
    {
        return  optDataColMapper.selectDataColTop300();
    }

    @TargetDataSource(name="datadb")
    public double GetEnergyDataSummary(String code,String project_id)
    {
    return  optDataColMapper.GetEnergyDataSummary(code, project_id)  ;
    }
    @TargetDataSource(name="datadb")
    public double GetDayDiffValueFromDataCol(String siteId,String code,String stime ,String etime)
    {
        return  optDataColMapper.GetDayDiffValueFromDataCol( siteId,code, stime , etime);
    }
    @TargetDataSource(name="datadb")
    public List<OptDataCol> getModelByProjectIdAndTime(String projectId,String sysTime)
    {
        return  optDataColMapper.getModelByProjectIdAndTime(projectId, sysTime);
    }
}