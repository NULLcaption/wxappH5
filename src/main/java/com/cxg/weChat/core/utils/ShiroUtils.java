package com.cxg.weChat.core.utils;

import com.cxg.weChat.crm.pojo.AllUsersDo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
* @Description:    shiro工具类
* @Author:         Cheney Master
* @CreateDate:     2018/8/15 15:30
* @Version:        1.0
*/

public class ShiroUtils {

    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static AllUsersDo getUser() {
        Object object = getSubjct().getPrincipal();
        return (AllUsersDo)object;
    }
    public static Long getUserId() {
        return Long.valueOf(getUser().getUserId());
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }
}
