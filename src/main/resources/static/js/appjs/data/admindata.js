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
            url: prefix + "/adminDataList", // 服务器数据的加载地址
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
                    planId: $('#planId').val(),
                    id: $('#id').val(),
                    planTitle: $('#planTitle').val(),
                    planAddress: $('#planAddress').val(),
                    status: $('#status').val(),
                    planStartDate: $('#planStartDate').val(),
                    planEndDate: $('#planEndDate').val()
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
                    field: 'id',
                    title: '详细ID'
                },
                {
                    field: 'planId',
                    title: '活动ID'
                },
                {
                    field: 'planTitle',
                    title: '活动标题'
                },
                {
                    field: 'planAddress',
                    title: '活动地址'
                },
                {
                    field: 'planStartDate',
                    title: '活动开始时间'
                },
                {
                    field: 'planEndDate',
                    title: '活动结束时间'
                },
                {
                    field: 'openId',
                    title: 'openId'
                },
                {
                    field: 'nickName',
                    title: '微信昵称'
                },
                {
                    field: 'avatarUrl',
                    title: '微信头像',
                    formatter: function (value, row, index) {
                        var avatarUrl = row.avatarUrl;
                        if (avatarUrl != null) {
                            return '<a class = "view"  href="javascript:void(0)"><img style="width:30px;height:30px;"  src="' + avatarUrl + '" /></a>';
                        }
                        return '';
                    }
                },
                {
                    field: 'signAddress',
                    title: '签到地址'
                },
                {
                    field: 'signDate',
                    title: '签到时间'
                }]
        });
}
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
    $('#planId').val(''),
    $('#id').val(''),
    $('#planTitle').val(''),
    $('#planAddress').val(''),
    $('#planStartDate').val(''),
    $('#planEndDate').val('')
}
/**
 * 导出数据
 */
function downloadData() {
    window.open(prefix + '/downloadAdmin?planId=' + $('#planId').val() + '&id=' + $('#id').val()
    + '&planTitle=' + $('#planTitle').val() + '&planAddress=' + $('#planAddress').val()
    + '&planStartDate=' + $('#planStartDate').val() + '&planEndDate=' + $('#planEndDate').val());
}
