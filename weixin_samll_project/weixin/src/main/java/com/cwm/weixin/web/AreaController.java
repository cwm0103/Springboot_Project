package com.cwm.weixin.web;

import com.cwm.weixin.entity.Area;
import com.cwm.weixin.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cwm")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "listArea",method = RequestMethod.GET)
    private Map<String,Object> listArea()
    {
        Map<String,Object> map=new HashMap<String, Object>();
        List<Area> areas = areaService.queryArea();
        map.put("listArea",areas);
        return map;
    }
    @RequestMapping(value = "areaById",method = RequestMethod.GET)
    private Map<String,Object> areaById(Integer areaId)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        Area area=areaService.queryAreaById(areaId);
        map.put("area",area);
        return map;
    }
    @RequestMapping(value = "addArea",method = RequestMethod.POST)
    private Map<String,Object> addArea(@RequestBody Area area){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ok",areaService.insertArea(area));
        return map;
    }

    @RequestMapping(value = "updateArea",method = RequestMethod.POST)
    private Map<String,Object> updateArea(@RequestBody Area area){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ok",areaService.updateArea(area));
        return map;
    }

    @RequestMapping(value = "deleteArea",method = RequestMethod.GET)
    private Map<String,Object> deleteArea(Integer areaId){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ok",areaService.deleteArea(areaId));
        return map;
    }
}
