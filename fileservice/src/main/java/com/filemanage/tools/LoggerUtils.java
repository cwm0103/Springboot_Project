package com.filemanage.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jindb on 2017/7/20.
 */
public class LoggerUtils {
    private static final Logger logger = LoggerFactory.getLogger("hma");

    /**
     *
     * @param msg
     */
    public  static  void  Info(String msg){
        logger.info(msg);
    }

    /**
     *
     * @param msg
     */
    public  static  void  Error(String msg){
        logger.error(msg);
    }

    /**
     *
     * @param msg
     * @param ex
     */
    public  static  void  Error(String msg ,Object ex){
        logger.error(msg,ex);
    }

    /**
     *
     * @param msg
     */
    public  static  void  Trace(String msg)
    {
        logger.trace(msg);
    }
}
