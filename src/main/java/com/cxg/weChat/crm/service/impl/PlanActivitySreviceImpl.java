package com.cxg.weChat.crm.service.impl;

import com.cxg.weChat.core.utils.Query;
import com.cxg.weChat.crm.mapper.PlanActivityMapper;
import com.cxg.weChat.crm.pojo.PlanActivityDo;
import com.cxg.weChat.crm.pojo.WxAdminInfoDo;
import com.cxg.weChat.crm.service.PlanActivitySrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 获取微信活动数据之实现类
 * @Author xg.chen
 * @Date 15:47 2018/11/29
**/
@Transactional
@Service
public class PlanActivitySreviceImpl implements PlanActivitySrevice {

    @Autowired
    PlanActivityMapper planActivityMapper;
    @Override
    public List<PlanActivityDo> getPlanList(Query query) {
        try {
            return planActivityMapper.getPlanList(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countPlanData(Query query) {
        try {
            return planActivityMapper.countPlanData(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<WxAdminInfoDo> getPlanDoAdminList(Query query) {
        try{
            return planActivityMapper.getPlanDoAdminList(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countPlanAdminData(Query query) {
        try {
            return planActivityMapper.countPlanAdminData(query);
        } catch (Exception e) {

        }
        return 0;
    }

    @Override
    public int countPlanPhotoData(Query query) {
        try{
            return planActivityMapper.countPlanPhotoData(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public List<String> getDetailIdList(String detailId) {
        try {
            return planActivityMapper.getDetailIdList(detailId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
