package com.cxg.weChat.core.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 设置微信三方平台的配置常量
 * @Author xg.chen
 * @Date 8:58 2019/3/15
**/
@Component
@ConfigurationProperties(prefix = "wechat.open")
public class WechatOpenProperties {
    /**
     * 设置微信三方平台的appid
     */
    private String componentAppId;

    /**
     * 设置微信三方平台的app secret
     */
    private String componentSecret;

    /**
     * 设置微信三方平台的token
     */
    private String componentToken;

    /**
     * 设置微信三方平台的EncodingAESKey
     */
    private String componentAesKey;

    public String getComponentAppId() {
        return componentAppId;
    }

    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
    }

    public String getComponentSecret() {
        return componentSecret;
    }

    public void setComponentSecret(String componentSecret) {
        this.componentSecret = componentSecret;
    }

    public String getComponentToken() {
        return componentToken;
    }

    public void setComponentToken(String componentToken) {
        this.componentToken = componentToken;
    }

    public String getComponentAesKey() {
        return componentAesKey;
    }

    public void setComponentAesKey(String componentAesKey) {
        this.componentAesKey = componentAesKey;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);


    }
}
