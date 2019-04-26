package com.cxg.weChat.crm.controller;

import com.cxg.weChat.core.utils.ShiroUtils;
import com.cxg.weChat.crm.pojo.AllUsersDo;
import org.springframework.stereotype.Controller;

/**
* @Description:    基础控制类
* @Author:         Cheney Master
* @CreateDate:     2018/11/14 10:16
* @Version:        1.0
*/
@Controller
public class BaseController {
    public AllUsersDo getUser() {
        return ShiroUtils.getUser();
    }

    public String getUserId() {
        return getUser().getUserId();
    }

    public String getUsername() {
        return getUser().getUserName();
    }
}
