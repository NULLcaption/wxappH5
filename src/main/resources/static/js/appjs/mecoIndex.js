/**
 * 提交确认
 * Created by Administrator on 2019/3/13.
 */
var prefix = "/api/userInfo/admin";
$().ready(function() {
    save(1);
});
/**
 * 获取微信配置信息
 */
function getWxConfig() {
    var url = location.href.split('#')[0];//当前的url
    $.ajax({
        type: "POST",
        url: prefix + "/config",
        dataType: 'json',
        data : {
            "appId" : "wxe9bec4afeb0d2985",
            "url" : url
        },
        success: function (data) {
            wxConfig(data);
        },
        error: function() {
            alert("授权错误")
        }
    });
}
/**
 * 微信js授权
 * @param _config
 */
function wxConfig(_config) {
    //获取微信的配置信息
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wxe9bec4afeb0d2985', // 必填，公众号的唯一标识
        timestamp: _config.timestamp, // 必填，生成签名的时间戳
        nonceStr: _config.nonceStr, // 必填，生成签名的随机串
        signature: _config.signature,// 必填，签名
        jsApiList: [
            'updateAppMessageShareData',//自定义“分享给朋友”及“分享到QQ”按钮的分享内容
            'updateTimelineShareData', //自定义“分享到朋友圈”及“分享到QQ空间”按钮的分享内容
            'onMenuShareTimeline',
            'onMenuShareAppMessage'
        ] // 必填，需要使用的JS接口列表
    });

    wx.checkJsApi({
        jsApiList: [
            'onMenuShareTimeline','onMenuShareAppMessage',
            'onMenuShareTimeline', 'onMenuShareAppMessage'
        ]
    });
    //开始转发
    wx.ready(function () {
        //分享到朋友圈
        wx.updateTimelineShareData({
            title: '推广分享', // 分享标题
            link: location.href.split('#')[0],
            imgUrl: '', // 分享图标
            success: function () {
                //save(1);
            }
        });
    });
}

$.validator.setDefaults({
    submitHandler : function() {
        save(1);
    }
});
//确认领取
function save(status) {
    $.ajax({
        type : 'POST',
        data : {
            "id" : $("#id").val(),
            "planId" : $("#planId").val(),
            "openId" : $("#openId").val()
        },
        url : prefix + '/submit',
        success : function(r) {
            if (r.code == 0) {
                window.location.href = prefix+"/mecoIndex";
            } else {
                layer.msg("亲！您已经领取了，不能重复领取了！");
            }
        }
    });
}
