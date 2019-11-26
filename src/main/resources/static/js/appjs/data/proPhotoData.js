/**
 * Created by xg.chen on 2018/11/29.
 */
var prefix = "/data";
$(function () {
    $('#datetimepicker1').datetimepicker({
        format: 'YYYY年MM月DD日',
        locale: moment.locale('zh-cn')
    });
    $('#datetimepicker2').datetimepicker({
        format: 'YYYY年MM月DD日',
        locale: moment.locale('zh-cn')
    });
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
        {
            method: 'get', // 服务器数据的请求方式 get or post
            url: prefix + "/proPhotoDataList", // 服务器数据的加载地址
            // showRefresh : true,
            // showToggle : true,
            showColumns: true,
            iconSize: 'outline',
            toolbar: '#exampleToolbar',
            striped: true, // 设置为true会有隔行变色效果
            dataType: "json", // 服务器返回的数据类型
            pagination: true, // 设置为true会在底部显示分页条
            // queryParamsType : "limit",
            // //设置为limit则会发送符合RESTFull格式的参数
            singleSelect: false, // 设置为true将禁止多选
            // contentType : "application/x-www-form-urlencoded",
            // //发送到服务器的数据编码类型
            pageNumber: 1, // 如果设置了分布，首页页码
            pageSize: 10, // 如果设置了分页，每页数据条数
            pageList: [10, 20, 30, 40], //可供选择的每页的行数（*）
            // search : true, // 是否显示搜索框
            //showColumns : false, // 是否显示内容下拉框（选择显示的列）
            sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
            // "server"

            queryParams: function (params) {
                return {
                    // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    limit: params.limit,//页面大小
                    offset: params.offset,//页码
                    planAddress: $('#planAddress').val(),
                    planStartDate: $('#planStartDate').val(),
                    planEndDate: $('#planEndDate').val(),
                    userName: $('#userName').val()
                };
            },
            // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
            // queryParamsType = 'limit' ,返回参数必须包含
            // limit, offset, search, sort, order 否则, 需要包含:
            // pageSize, pageNumber, searchText, sortName,
            // sortOrder.
            // 返回false将会终止请求
            columns: [
                {
                    field: 'planAddress',
                    title: '活动地址'
                },
                {
                    field: 'loginId',
                    title: '用户ID'
                },
                {
                    field: 'userName',
                    title: '用户姓名'
                },
                {
                    field: 'userRole',
                    title: '人员角色',
                    formatter: function (value) {
                        if (value == "G") {
                            return "导购";
                        } else if (value == "L") {
                            return "理货员";
                        } else if (value == "D") {
                            return "督导";
                        }
                    }
                },
                {
                    field: 'photoUrl',
                    title: '现场照片',
                    formatter : function(value, row, index) {
                        var photoUrl = row.photoUrl;
                        if (photoUrl != null ) {
                            return '<a class = "view"  href="javascript:void(0)"><img style="width:50px;height:50px;"  src="'+photoUrl+'" /></a>';
                        }
                        return '';
                    },
                    events: 'operateEvents'//定义点击之后放大图片的事件
                },
                {
                    field: 'creartDate',
                    title: '上传时间'
                }
            ]
        });
}

/**
 * 图片放大器
 * @type {{click .view: Function}}
 */
window.operateEvents = {
    'click .view': function (e, value, row, index) {
        var img ='<img style="width:100%; height:100%;" src="'+row.photoUrl+'"/>';
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            area: [375 + 'px', 667 +'px'],
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content: img
        });
    }
};

/**
 * 查询
 */
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}
/**
 * 重置
 */
function clearData() {
    $('#planAddress').val(''),
        $('#planStartDate').val(''),
        $('#planEndDate').val(''),
        $('#userName').val()
}
/**
 * 导出数据
 */
function downloadData() {
    window.open(prefix + '/downloadSiginData?planAddress=' + $('#planAddress').val()
    + '&planStartDate=' + $('#planStartDate').val()
    + '&planEndDate=' + $('#planEndDate').val()
    + '&userName=' + $('#userName').val());
}