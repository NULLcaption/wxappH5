/**
 * 提交确认
 * Created by Administrator on 2019/3/13.
 */
var prefix = "/api/userInfo/admin";
$().ready(function() {
    var id = $("#id").val();
    var plan = $("#planId").val();
    var openid = $("#openId").val();
    var qrcode = id+","+plan+","+openid;
    createQRcode(qrcode);
    save();
});

function createQRcode(qrcode) {
    layer.msg("请到现场管理管理员处扫码合核销");
    //生成二维码
    $('.poster-qrcode').qrcode({
        mode: 4,
        mSize: 20,
        //label: '文字Logo',//使用文字logo必须去掉mode,mSize参数
        image: $(".poster-qrcode-logo")[0],
        text: qrcode,
        size: 1080 / 3,
        render: 'canvas',
        width: 100,
        height: 100
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
