/**
 * 提交确认
 * Created by Administrator on 2019/3/13.
 */
var prefix = "/api/userInfo/admin";
$().ready(function() {
    validateRule();
});

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
        url : prefix + '/submit1',
        success : function(r) {
            if (r.code == 0) {
                layer.msg("亲！您已领取成功！");
                window.location.href = prefix+"/success";
            } else {
                layer.msg("亲！您已经领取了，不能重复领取了！");
            }
        }
    });
}