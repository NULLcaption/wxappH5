package com.cxg.weChat.core.config;

import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author: omybug
 * @date: 2019/1/15 18:07
 */
@Configuration
public class WechatConfig {

    @Autowired
    private WechatOpenProperties wechatOpenProperties;

    @Autowired
    private JedisPool jedisPool;

    @Bean
    public WxOpenService wxOpenService(){
        WxOpenService wxOpenService = new WxOpenServiceImpl();
        WxOpenInRedisConfigStorage inRedisConfigStorage = new WxOpenInRedisConfigStorage(jedisPool);
        inRedisConfigStorage.setComponentAppId(wechatOpenProperties.getComponentAppId());
        inRedisConfigStorage.setComponentAppSecret(wechatOpenProperties.getComponentSecret());
        inRedisConfigStorage.setComponentToken(wechatOpenProperties.getComponentToken());
        inRedisConfigStorage.setComponentAesKey(wechatOpenProperties.getComponentAesKey());
        wxOpenService.setWxOpenConfigStorage(inRedisConfigStorage);
        return wxOpenService;
    }
}
