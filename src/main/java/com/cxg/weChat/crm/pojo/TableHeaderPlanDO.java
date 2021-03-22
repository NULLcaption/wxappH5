package com.cxg.weChat.crm.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TableHeaderPlanDO extends BaseRowModel {
    /**
     * value: 表头名称
     * index: 列的号, 0表示第一列
     */
    @ExcelProperty(value = "活动详细ID", index = 0)
    private String id;
    @ExcelProperty(value = "活动Id", index = 1)
    private String planId;
    @ExcelProperty(value = "活动标题", index = 2)
    private String planTitle;
    @ExcelProperty(value = "活动地址", index = 3)
    private String planAddress;
    @ExcelProperty(value = "活动开始时间", index = 4)
    private String planStartDate;
    @ExcelProperty(value = "活动结束时间", index = 5)
    private String planEndDate;
    @ExcelProperty(value = "openid", index = 6)
    private String openId;
    @ExcelProperty(value = "微信昵称", index = 7)
    private String nickName;
    @ExcelProperty(value = "微信头像", index = 8)
    private String avatarUrl;
    @ExcelProperty(value = "领取状态", index = 9)
    private String status;
    @ExcelProperty(value = "领取时间", index = 10)
    private String createTime;
}
