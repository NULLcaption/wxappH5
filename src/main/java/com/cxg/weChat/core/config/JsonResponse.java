package com.cxg.weChat.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * @author: omybug
 * @date: 18/9/4 15:18
 */
public class JsonResponse implements Serializable {

    public enum Code{
        /**
         * 操作成功
         */
        SUCCESS("操作成功", 1),

        /**
         * 操作失败
         */
        FAIL("操作失败",-1),

        CREATE_FAIL("创建失败, 请重试!",-1),
        UPDATE_FAIL("修改失败, 请重试!",-1),
        DELETE_FAIL("删除失败, 请重试!",-1),

        /**
         * 未关注公众号无法访问
         */
        UNSUBSCRIBE("未关注公众号",-7),

        /**
         * 权限不足，禁止访问
         */
        FORBIDDEN("禁止访问",-8),

        /**
         * 未登录
         */
        NOT_LOGIN_IN("未登录", -9),

        /**
         * 系统错误
         */
        RUNTIME_ERROR("系统错误", -10),

        /**
         * 页面或接口不存在
         */
        NOT_FOUND("接口不存在",-11),

        ACTIVITY_NOT_START("活动尚未开始",-12),

        ACTIVITY_FINISHED("活动已结束",-12),
        ;

        private String msg;

        private int ret;

        Code(String msg, int ret)
        {
            this.setMsg(msg);
            this.setRet(ret);
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getRet() {
            return ret;
        }

        public void setRet(int ret) {
            this.ret = ret;
        }
    }

    private Integer ret;

    private Object data;

    private String st;

    private String msg;

    public JsonResponse() {
        this.st = String.valueOf(System.currentTimeMillis() / 1000);
        this.msg = "";
        this.ret = 0;
        this.data = "";
    }

    public static JsonResponse success() {
        JsonResponse jr = new JsonResponse();
        jr.setRet(Code.SUCCESS);
        return jr;
    }

    public static JsonResponse success(Object data) {
        JsonResponse jr = new JsonResponse();
        jr.setRet(Code.SUCCESS);
        jr.setData(data);
        return jr;
    }

    public static JsonResponse fail(String msg) {
        JsonResponse jr = new JsonResponse();
        jr.setRet(Code.FAIL);
        jr.setMsg(msg);
        return jr;
    }


    /**
     * 其他错误
     * @param retEnum
     * @return
     */
    public static JsonResponse error(Code retEnum) {
        JsonResponse jr = new JsonResponse();
        jr.setRet(retEnum);
        jr.setMsg(retEnum.getMsg());
        return jr;
    }

    /**
     * 其他错误
     * @param retEnum
     * @param msg
     * @return
     */
    public static JsonResponse error(Code retEnum, String msg) {
        JsonResponse jr = new JsonResponse();
        jr.setRet(retEnum);
        jr.setMsg(msg);
        return jr;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public void setRet(Code retEnum) {
        this.ret = retEnum.getRet();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.toString();
    }
}
