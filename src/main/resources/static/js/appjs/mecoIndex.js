/**
 * 提交确认
 * Created by Administrator on 2019/3/13.
 */
var prefix = "/api/userInfo/wx";
$().ready(function() {
    //getWxConfig();
});

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
