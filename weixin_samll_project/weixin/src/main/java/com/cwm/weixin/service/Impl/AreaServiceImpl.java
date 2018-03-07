package com.cwm.weixin.service.Impl;

import com.cwm.weixin.dao.AreaDao;
import com.cwm.weixin.entity.Area;
import com.cwm.weixin.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> queryArea() {
        return areaDao.queryArea();
    }

    @Override
    public Area queryAreaById(int areaId) {
       // int i=1/0;
        return areaDao.queryAreaById(areaId);
    }

    @Transactional
    @Override
    public boolean insertArea(Area area) {
        if(area.getAreaName()!=null&&!"".equals(area.getAreaName()))
        {
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());
            try
            {
                int i = areaDao.insertArea(area);
                if(i>0)
                {
                    return true;
                }else
                {
                    throw  new RuntimeException("插入区域信息错误！");
                }
            }catch (Exception e)
            {
                throw  new RuntimeException("插入区域信息错误："+e.getMessage());
            }
        }else
        {
            throw  new RuntimeException("插入区域信息不能为空！");
        }
    }

    @Override
    public boolean updateArea(Area area) {
        if(area.getAreaName()!=null&&!"".equals(area.getAreaName()))
        {
            area.setLastEditTime(new Date());
            try
            {
                int i = areaDao.updateArea(area);
                if(i>0)
                {
                    return true;
                }else
                {
                    throw  new RuntimeException("更新区域信息错误！");
                }
            }catch (Exception e)
            {
                throw  new RuntimeException("更新区域信息错误："+e.getMessage());
            }
        }else
        {
            throw  new RuntimeException("更新区域信息不能为空！");
        }
    }

    @Override
    public boolean deleteArea(int areaId) {
        if(areaId>0)
        {

            try
            {
                int i = areaDao.deleteArea(areaId);
                if(i>0)
                {
                    return true;
                }else
                {
                    throw  new RuntimeException("删除区域信息错误！");
                }
            }catch (Exception e)
            {
                throw  new RuntimeException("删除区域信息错误："+e.toString());
            }
        }else
        {
            throw  new RuntimeException("删除区域信息不能为空！");
        }
    }
}
