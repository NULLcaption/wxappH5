/**
 * 提交确认
 * Created by Administrator on 2019/3/13.
 */
var prefix = "/api/userInfo/admin";
/**
 * 初始化页面
 */
$().ready(function() {
    var id = $("#id").val();
    var plan = $("#planId").val();
    var openid = $("#openId").val();
    var qrcode = id+","+plan+","+openid;
    CurrentTime();
    createQRcode(qrcode);
    save();
});
/**
 * 页面时间确认
 * @constructor
 */
function CurrentTime() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    month = month < 10 ? ("0" + month) : month;
    day = day < 10 ? ("0" + day) : day;
    hour = hour < 10 ? ("0" + hour) : hour;
    minute = minute < 10 ? ("0" + minute) : minute;
    second = second < 10 ? ("0" + second) : second;
    var Timer = year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
    //在页面上插入日期
    $("#clock").html(Timer);
    setTimeout(function () {
        CurrentTime();
    }, 1000);
}
/**
 * 生成二维码
 * @param qrcode
 */
function createQRcode(qrcode) {
    layer.msg("请到现场管理管理员处进行合核销确认");
    //生成二维码
    $('.poster-qrcode').qrcode({
        mode: 4,
        mSize: 20,
        //label: '文字Logo',//使用文字logo必须去掉mode,mSize参数
        image: $(".poster-qrcode-logo")[0],
        text: qrcode,
        size: 1080 / 3,
        render: 'canvas',
        width: 150,
        height: 150
    });
}
/**
 * 确认领取
 * 动态二维码长连接请求；
 * 请求60S没有响应，则显示动态二维码过期，需要返回重新扫码进入；
 * @param status
 */
function save() {
    var num = 60;
    var timer = setInterval(function () {
        num --;
        if (num <= 0) {
            clearTimeout(timer);
            window.location.href = prefix+"/timeout";
        } else {
            $.ajax({
                type : 'POST',
                data : {
                    "id" : $("#id").val(),
                    "planId" : $("#planId").val(),
                    "openId" : $("#openId").val()
                },
                url : prefix + '/submit1',
                success : function(r) {
                    if (r.code == 0) {
                        window.location.href = prefix+"/success?id="+$("#id").val()+'&planId='+$("#planId").val()+'&openId='+$("#openId").val();
                    }
                }
            });
        }
    },5000);
}
