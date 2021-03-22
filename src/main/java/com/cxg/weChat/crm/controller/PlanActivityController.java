package com.cxg.weChat.crm.controller;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.cxg.weChat.core.utils.ExcelUtil;
import com.cxg.weChat.core.utils.MultipleSheelPropety;
import com.cxg.weChat.core.utils.PageUtils;
import com.cxg.weChat.core.utils.Query;
import com.cxg.weChat.crm.pojo.PlanActivityDo;
import com.cxg.weChat.crm.pojo.TableHeaderPlanDO;
import com.cxg.weChat.crm.pojo.WxAdminInfoDo;
import com.cxg.weChat.crm.pojo.WxPlanPhotoDo;
import com.cxg.weChat.crm.service.PlanActivitySrevice;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Description 微信小程序领取数据报表
 * @Author xg.chen
 * @Date 14:43 2018/11/29
 */
@Controller
@RequestMapping("/data")
public class PlanActivityController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PlanActivityController.class);

    @Autowired
    PlanActivitySrevice planActivitySrevice;

    /**
     * @Description 领取数据页面跳转
     * @Author xg.chen
     * @Date 15:04 2018/11/29
     * @Param
     */
    @GetMapping("/data")
    public String index() {
        return "data/data";
    }

    /**
     * @Description 获取微信用户数据
     * @Date 13:52 2018/12/3
     * @Param
     */
    @GetMapping("/dataList")
    @ResponseBody
    public PageUtils dataList(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<PlanActivityDo> planList = planActivitySrevice.getPlanList(query);
        int count = planActivitySrevice.countPlanData(query);
        PageUtils pageUtils = new PageUtils(planList, count);
        return pageUtils;
    }


    /**
     * 导出用户参与明细数据
     * @param params
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadExcel")
    @ResponseBody
    public void downloadExcel(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception{
        PlanActivityDo planActivityDo = JSON.parseObject(JSON.toJSONString(params), PlanActivityDo.class);
        int count = planActivitySrevice.getPlanListCount(planActivityDo);
        if (count != 0){
            params.put("offset", 0);
            params.put("limit", count);
            Query query = new Query(params);
            List<PlanActivityDo> queryAll = planActivitySrevice.getPlanList(query);
            ArrayList<MultipleSheelPropety> multipleSheelList = new ArrayList<>();
            for(int j = 0; j < Math.round(count/20000); j++){
                ArrayList<TableHeaderPlanDO> tableHeaderPlanDOArrayList = new ArrayList<>();
                for (PlanActivityDo planActivity : queryAll) {
                    TableHeaderPlanDO tableHeaderPlanDO = new TableHeaderPlanDO();
                    tableHeaderPlanDO.setId(planActivity.getId());
                    tableHeaderPlanDO.setPlanId(planActivity.getPlanId());
                    tableHeaderPlanDO.setPlanTitle(planActivity.getPlanTitle());
                    tableHeaderPlanDO.setPlanAddress(planActivity.getPlanAddress());
                    tableHeaderPlanDO.setPlanStartDate(planActivity.getPlanStartDate());
                    tableHeaderPlanDO.setPlanEndDate(planActivity.getPlanEndDate());
                    tableHeaderPlanDO.setOpenId(planActivity.getOpenId());
                    tableHeaderPlanDO.setNickName(planActivity.getNickName());
                    tableHeaderPlanDO.setAvatarUrl(planActivity.getAvatarUrl());
                    tableHeaderPlanDO.setStatus(planActivity.getStatus());
                    tableHeaderPlanDO.setCreateTime(planActivity.getCreateTime());

                    tableHeaderPlanDOArrayList.add(tableHeaderPlanDO);

                }
                System.gc();

                Sheet sheet = new Sheet(j, 0);
                sheet.setSheetName("sheet"+j);

                MultipleSheelPropety multipleSheelPropety = new MultipleSheelPropety();
                multipleSheelPropety.setData(tableHeaderPlanDOArrayList);
                multipleSheelPropety.setSheet(sheet);

                multipleSheelList.add(multipleSheelPropety);
            }

            ExcelUtil.writeWithMultipleSheel(response, "推广活动数据", multipleSheelList);
        }
    }

    /**
     * @Description 现场执行活动照片
     * @Author xg.chen
     * @Date 12:12 2018/12/5
     **/
    @GetMapping("/photodata")
    public String photoData() {
        return "data/photodata";
    }

    /**
     * 获取照片列表
     * @param params
     * @return
     */
    @GetMapping("/photoDataList")
    @ResponseBody
    public PageUtils photoDataList(@RequestParam Map<String ,Object> params) {
        Query query = new Query(params);
        List<WxPlanPhotoDo> planList = planActivitySrevice.getPlanPhotoList(query);
        if (planList != null) {
            for (WxPlanPhotoDo wxPlanPhoto: planList) {
                wxPlanPhoto.setPhotoUrl("/images/"+wxPlanPhoto.getPhotoUrl().replace("/files/",""));
            }
        }
        int count = planActivitySrevice.countPlanPhotoData(query);
        PageUtils pageUtils = new PageUtils(planList, count);
        return pageUtils;
    }

    /**
     * @Description 现场执行人员信息
     * @Author xg.chen
     * @Date 10:03 2018/12/5
     */
    @GetMapping("/admindata")
    public String adminData() {
        return "data/admindata";
    }

    /**
     * @Description 获取管理员的信息列表
     * @Author xg.chen
     * @Date 10:12 2018/12/5
     **/
    @GetMapping("/adminDataList")
    @ResponseBody
    public PageUtils adminDataList(@RequestParam Map<String ,Object> params) {
        Query query = new Query(params);
        List<WxAdminInfoDo> planList = planActivitySrevice.getPlanDoAdminList(query);
        int count = planActivitySrevice.countPlanAdminData(query);
        PageUtils pageUtils = new PageUtils(planList, count);
        return pageUtils;
    }

    /**
     * @Description 导出管理员的信息
     * @Author xg.chen
     * @Date 11:33 2018/12/5
     **/
    @GetMapping("/downloadAdmin")
    @ResponseBody
    public void downloadAdmin(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        params.put("offset", "0");
        params.put("limit", "9999999");
        Query query = new Query(params);
        List<WxAdminInfoDo> queryAll = planActivitySrevice.getPlanDoAdminList(query);
        //工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        OutputStream out = null;
        HSSFSheet sheet = workbook.createSheet("数据");
        //设置导出的表的名字
        String fileName = "推广活动管理员者数据.xls";
        //设置头
        int rowNum = 1;
        String[] headers = {"活动详细ID", "活动Id", "活动标题", "活动地址",
                "活动开始时间", "活动结束时间", "openid",
                "微信昵称", "微信头像", "签到地点", "签到时间"};
        HSSFRow row = sheet.createRow(0);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);

        }

        for (WxAdminInfoDo planActivity : queryAll) {
            HSSFRow rown = sheet.createRow(rowNum);
            rown.createCell(0).setCellValue(planActivity.getId());
            rown.createCell(1).setCellValue(planActivity.getPlanId());
            rown.createCell(2).setCellValue(planActivity.getPlanTitle());
            rown.createCell(3).setCellValue(planActivity.getPlanAddress());
            rown.createCell(4).setCellValue(planActivity.getPlanStartDate());
            rown.createCell(5).setCellValue(planActivity.getPlanEndDate());
            rown.createCell(6).setCellValue(planActivity.getOpenId());
            rown.createCell(7).setCellValue(planActivity.getNickName());
            rown.createCell(8).setCellValue(planActivity.getAvatarUrl());
            rown.createCell(9).setCellValue(planActivity.getSignAddress());
            rown.createCell(10).setCellValue(planActivity.getSignDate());
            rowNum++;
        }
        try {
            response.setContentType("application/msexcel");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + new String(fileName.getBytes("GBK"), ("ISO8859-1"))
                    + ".xls\"");
            response.flushBuffer();
            out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
