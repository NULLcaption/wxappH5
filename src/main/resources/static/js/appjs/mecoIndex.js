/**
 * 提交确认
 * Created by Administrator on 2019/3/13.
 */
var prefix = "/api/userInfo/wx";
//微信JS授权
$().ready(function() {
    getWxConfig();
});

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

function wxConfig(_config) {
    //获取微信的配置信息
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: _config.appId, // 必填，公众号的唯一标识
        timestamp: _config.timestamp, // 必填，生成签名的时间戳
        nonceStr: _config.nonceStr, // 必填，生成签名的随机串
        signature: _config.signature,// 必填，签名
        jsApiList: [
            'updateAppMessageShareData',//自定义“分享给朋友”及“分享到QQ”按钮的分享内容
            'updateTimelineShareData', //自定义“分享到朋友圈”及“分享到QQ空间”按钮的分享内容
            'onMenuShareTimeline',
            'onMenuShareAppMessage'
        ], // 必填，需要使用的JS接口列表
        openTagList: ['wx-open-launch-app'] // 可选，需要使用的开放标签列表，例如['wx-open-launch-app']
    });

    wx.ready(function () {
        alert('微信授权成功');
        var dom = document.getElementById('wxOpenInAPP') //
        dom.innerHTML = '<wx-open-launch-app style="width:100%; display:block;" id="launch-btn"     extinfo="" appid=""><template><style>  .wx-btn {   color: #666; width: 100%; height:100%; display: block; text-aligin:center; }</style><button class="wx-btn">我想接这单</button></template></wx-open-launch-app>'
        var launchBtn = document.getElementById('launch-btn')
        if (!launchBtn) {
        }
        launchBtn.setAttribute('appid','gh_583b84db012a')
        launchBtn.setAttribute('extinfo', '{"url":"pages/redEnvelope/index"}')
        launchBtn.addEventListener('launch', function (e) {
            console.log('success')
        })
        launchBtn.addEventListener('error', function (e) {
            console.log('fail', e.detail)
        })
    });
}




$.validator.setDefaults({
    submitHandler : function() {
        save(1);
    }
});
//确认领取
function save(status) {
    if(status==1) {
        var date = dateFormat("YYYY-mm-dd",new Date());//当前时间
        //活动开始时间
        var startDate = dateFormat("YYYY-mm-dd",new Date("2020-05-17"));
        //活动结束时间
        var endDate = dateFormat("YYYY-mm-dd",new Date("2020-05-23"));
        if (date>=startDate || date<endDate) {
            window.location.href = 'https://www.wjx.top/jq/77483125.aspx';
        } else if (date>endDate) {
            alert("活动已经结束")
        } else if (date<startDate){
            alert("活动未开始")
        }
    }
    if(status==2) {
        var date = dateFormat("YYYY-mm-dd",new Date());//当前时间
        //活动开始时间
        var startDate = dateFormat("YYYY-mm-dd",new Date("2020-05-22"));
        //活动结束时间
        var endDate = dateFormat("YYYY-mm-dd",new Date("2020-06-30"));
        if (date>=startDate && date<endDate) {
            window.location.href = 'https://chinaxpp.wjx.cn/jq/76076854.aspx';
        } else if (date>endDate) {
            alert("活动已经结束")
        } else if (date<startDate){
            alert("活动未开始")
        }
    }
}

function dateFormat(fmt, date) {
    var ret;
    const opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString()            // 日
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (var k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        };
    };
    return fmt;
}
function getDate(strDate) {
    var st = strDate;
    var a = st.split(" "); //这个根据你的字符串决定，如果中间为T则改T
    var b = a[0].split("-");
    var date = new Date(b[0], b[1], b[2]);
    return date;
}
