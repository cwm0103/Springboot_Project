package com.bom.dataservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-14
 * Time: 15:20
 */
@Service
public class DataServiceFactory {


    public static ABSDataService getInstance(String type,ApplicationContext context)
    {
        ABSDataService dataService = (ABSDataService) context.getBean(type);
        return dataService;
    }
}
