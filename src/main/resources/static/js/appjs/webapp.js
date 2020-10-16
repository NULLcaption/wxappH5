/**
 * 提交确认
 * Created by Administrator on 2019/3/13.
 */
var prefix = "/api/userInfo/admin";
$().ready(function() {
    //validateRule();
});

$.validator.setDefaults({
    submitHandler : function() {
        save(1);
    }
});
/**
 * 确认领取
 * @param status
 */
function save(status) {
    window.location.href = prefix+"/success?id="+$("#id").val()+'&planId='+$("#planId").val()+'&openId='+$("#openId").val();
    //$.ajax({
    //    type : 'POST',
    //    data : {
    //        "id" : $("#id").val(),
    //        "planId" : $("#planId").val(),
    //        "openId" : $("#openId").val()
    //    },
    //    url : prefix + '/submit1',
    //    success : function(r) {
    //        if (r.code == 0) {
    //            layer.msg("亲！您已领取成功！");
    //            //分享海报
    //            window.location.href = prefix+"/success?id="+$("#id").val()+'&planId='+$("#planId").val()+'&openId='+$("#openId").val();
    //        } else {
    //            layer.msg("亲！您已经领取了，不能重复领取了！");
    //        }
    //    }
    //});
}

/**
 * 上传图片
 */
function btnUploadFile(e) {
    var imgFile = e.target.files[0]; //获取图片文件
    var formData = new FormData();  // 创建form对象
    var id = $("#id").val();
    var openId = $("#openId").val();
    formData.append('id',id);
    formData.append('openId',openId);
    formData.append('file', imgFile);  // 通过append向form对象添加数据
    formData.append('other', 'other')  // 如果还需要传替他参数的话
    $.ajax({
        url: prefix +'/uploadFile', //请求的接口地址
        type: 'POST',
        cache: false, //上传文件不需要缓存
        data: formData,
        processData: false, // 不要去处理发送的数据
        contentType: false, // 不要去设置Content-Type请求头
        success: function (r) {
            if (r.code == 0) {
                layer.msg("亲！您已领取成功！");
                window.location.href = prefix+"/success";
            } else if(r.code == 1) {
                layer.msg(r.msg);
            } else if (r.code == 2){
                layer.msg(r.msg);
            }else {
                layer.msg("亲！您已经领取了，不能重复领取了！");
            }
        },
        error: function (data) {
            layer.msg(data.msg)
        }
    })
}