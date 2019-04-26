package com.cxg.weChat.crm.mapper;

import com.cxg.weChat.crm.pojo.AllUsersDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @Description:    用户登录相关
* @Author:         Cheney Master
* @CreateDate:     2018/11/13 16:21
* @Version:        1.0
*/
@Mapper
public interface AllUserDao {

    AllUsersDo get(Long userId);

    List<AllUsersDo> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int save(AllUsersDo user);

    int update(AllUsersDo user);

    int remove(Long userId);

    int batchRemove(Long[] userIds);

    Long[] listAllDept();
}
