package com.oper.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: jindb
 * @Date: 2018/8/17 10:10
 * @Description:
 */
@Data
public class DataParmerVO {
    /**
     * 图表ID
     */
    @ApiModelProperty(value = "图表ID")
    private Integer dashboardId;
    /**
     * 时间类型
     */
    @ApiModelProperty(value = "时间类型 YY:年;MM：月;DD:日,HH:小时")
    private String dateType;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Date beginDate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Date endDate;
}
