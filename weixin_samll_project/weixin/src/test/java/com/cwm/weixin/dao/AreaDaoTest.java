package com.cwm.weixin.dao;

import com.cwm.weixin.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaDaoTest {

    @Autowired
    private AreaDao areaDao;
    @Test
    @Ignore
    public void queryArea() {
        List<Area> areas = areaDao.queryArea();
        assertEquals(2,areas.size());
    }

    @Test
    @Ignore
    public void queryAreaById() {
        Area area = areaDao.queryAreaById(1);
        assertEquals("北苑",area.getAreaName());
    }

    @Test
    @Ignore
    public void insertArea() {
        Area area=new Area();
        area.setAreaName("南苑");
        area.setPriority(1);
        int i = areaDao.insertArea(area);
        assertEquals(1,i);
    }

    @Test
    @Ignore
    public void updateArea() {
    }

    @Test
    @Ignore
    public void deleteArea() {
    }
}