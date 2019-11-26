package com.cxg.weChat.crm.mapper;

import com.cxg.weChat.core.utils.Query;
import com.cxg.weChat.crm.pojo.PlanActivityDo;
import com.cxg.weChat.crm.pojo.WxAdminInfoDo;
import com.cxg.weChat.crm.pojo.WxPlanPhotoDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 获取获取数据
 * @Author xg.chen
 * @Date 15:50 2018/11/29
**/
@Mapper
public interface PlanActivityMapper {
    List<PlanActivityDo> getPlanList(Query query);

    int countPlanData(Query query);

    List<WxAdminInfoDo> getPlanDoAdminList(Query query);

    int countPlanAdminData(Query query);

    int countPlanPhotoData(Query query);

    List<String> getDetailIdList(String detailId);

    List<WxPlanPhotoDo> getProPhotoDataList(Query query);

    int countProPhotoData(Query query);

    List<WxPlanPhotoDo> getPlanPhotoList(Query query);
}
