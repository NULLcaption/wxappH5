package com.cxg.weChat.crm.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 微信登录网页授权
 * @Author xg.chen
 * @Date 12:19 2019/3/14
 **/

@RestController("wechat")
@RequestMapping("/")
public class WechatController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${server.domain}")
    private static String appDomain;

    @Autowired
    WxOpenService wxOpenService;

    private static String URL = "http://ssl.zjxpp.com";


    @GetMapping("/mp/auth_show")
    public String authShow(){
        return "<html><body><h1><a href='/mp/auth' target='_blank'>授权</a></h1></body></html>";
    }

    /**
     * 打开授权扫码地址，本地址必须手动从页面点开，不可直接浏览输入地址打开，微信会判断访问来源。
     * @param response
     */
    @GetMapping("/mp/auth")
    public void gotoPreAuthUrl(HttpServletResponse response){
        //页面的回调地址
        String url = URL + "/wechat/auth";
        try {
            url = wxOpenService.getWxOpenComponentService().getPreAuthUrl(url);
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公众号管理员授权认证后回调
     *
     * @param authCode
     * @param expiresIn
     * @return
     */
    @GetMapping("/wechat/auth")
    public Object auth(@RequestParam("auth_code") String authCode,
                       @RequestParam("expires_in") String expiresIn) {
        logger.debug("授权认证后回调：[auth_code=[{}], expires_in=[{}]] ", authCode, expiresIn);
        try {
            WxOpenQueryAuthResult result = wxOpenService.getWxOpenComponentService().getQueryAuth(authCode);
            // 保存 appid accessToken
            WxOpenAuthorizationInfo authorizationInfo = result.getAuthorizationInfo();
            logger.info("公众号授权信息：" + authorizationInfo.toString());
            return "公众号授权信息：" + authorizationInfo.toString();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return "授权成功";
    }

    /**
     * 开放平台回调，在开放平台后台设置
     *
     * @param requestBody
     * @param timestamp
     * @param nonce
     * @param signature
     * @param encType
     * @param msgSignature
     */
    @PostMapping("/auth")
    public String receiveTicket(@RequestBody(required = false) String requestBody,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce,
                                @RequestParam("signature") String signature,
                                @RequestParam(name = "encrypt_type", required = false) String encType,
                                @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        logger.debug(
                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();
        if (!StringUtils.equalsIgnoreCase("aes", encType)
                || !wxOpenComponentService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody,
                wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);

        logger.debug("\n开放平台回调：{} ", inMessage.toString());

        try {
            String out = wxOpenComponentService.route(inMessage);
            this.logger.debug("\n开放平台回调回复：{}", out);
            return out;
        } catch (WxErrorException e) {
            this.logger.error("receive_ticket", e);
        }

        return "fail";
    }

    /**
     * 微信事件回调，在开放平台后台设置
     *
     * @param requestBody
     * @param appId
     * @param signature
     * @param timestamp
     * @param nonce
     * @param openid
     * @param encType
     * @param msgSignature
     * @return
     */
    @PostMapping("/callback/{appId}")
    public String callback(@RequestBody(required = false) String requestBody,
                           @PathVariable("appId") String appId,
                           @RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("openid") String openid,
                           @RequestParam("encrypt_type") String encType,
                           @RequestParam("msg_signature") String msgSignature,
                           HttpServletResponse response) throws IOException {
        logger.debug(
                "\n微信回调：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
        WxOpenComponentService wxOpenComponentService = wxOpenService.getWxOpenComponentService();

        if (!StringUtils.equalsIgnoreCase("aes", encType)
                || !wxOpenComponentService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = "";
        // aes加密的消息
        WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody,
                wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        // 全网发布测试用例，请勿变动
        if (StringUtils.equalsAnyIgnoreCase(appId, "wxd101a85aa106f53e", "wx570bc396a51b8ff8")) {
            try {
                if (StringUtils.equals(inMessage.getMsgType(), "text")) {
                    if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                        out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                                WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                        .fromUser(inMessage.getToUser())
                                        .toUser(inMessage.getFromUser())
                                        .build(),
                                wxOpenService.getWxOpenConfigStorage()
                        );

                    } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
                        String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
                        WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser()).build();
                        wxOpenComponentService.getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                    }

                } else if (StringUtils.equals(inMessage.getMsgType(), "event")) {
                    WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback").toUser(inMessage.getFromUser()).build();
                    wxOpenComponentService.getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                }
            } catch (WxErrorException e) {
                logger.error("callback", e);
            }
        } else {
            // 关注事件

        }
        return out;
    }
}
