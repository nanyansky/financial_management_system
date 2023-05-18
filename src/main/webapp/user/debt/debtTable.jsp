<%--
  Created by IntelliJ IDEA.
  User: 28264
  Date: 2023/5/18
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>债务列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../statics/layui/lib/layui-v2.7.6/css/layui.css" media="all">
    <link rel="stylesheet" href="../../statics/layui/css/public.css" media="all">

    <style>
        .debtStatusCSS1 { border-radius:5px 5px 5px 5px;background-color:rgba(139, 245, 148, 1);background-clip:content-box;width:50px !important;text-align:center;font-weight:bold; }
        .debtStatusCSS2 { border-radius:5px 5px 5px 5px;background-color:rgba(255, 119, 0, 1);background-clip:content-box;width:50px !important;text-align:center;font-weight:bold; }
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">

        <%--搜索条件区域--%>
        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" action="">
                    <div class="layui-form-item">

                        <div class="layui-inline">
                            <label class="layui-form-label" style="width: 80px">分类</label>
                            <div class="layui-input-inline" style="width: 120px">
                                <select name="debtType" lay-verify="" id="fenlei-2">
                                    <option value = -1>全部</option>
                                    <option value = 1>借出</option>
                                    <option value = 0>借入</option>
                                </select>
                            </div>
                        </div>

                        <div class="layui-inline">
                            <button type="submit" class="layui-btn" lay-submit lay-filter="data-search-btn"><i class="layui-icon"></i> 搜 索</button>
                            <button type="reset" class="layui-btn layui-btn-warm"><i class="layui-icon layui-icon-refresh"></i>重 置</button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>

        <%--表格区域--%>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>


    </div>
</div>
<script src="../../statics/layui/lib/layui-v2.7.6/layui.js" charset="utf-8"></script>

<script>
    layui.use(['form', 'table', 'layer','laydate'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            layer = layui.layer,
            laydate = layui.laydate

        var tableIns = table.render({
            elem: '#currentTableId',
            url: '/user/getDebtListByPage.action',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports'],

            cols: [[
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 80, fixed: 'left' }//序号列
                // {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'debtName', title: '债务名称', minWidth:120, align: 'center'}
                ,{field: 'debtType',title: "债务类型",minWidth: 120, align: 'center',templet:function (d) {
                        if(d.debtType===1){
                            return "借出"
                        }else{
                            return "借入"
                        }
                    }}
                ,{field: 'debtCreateTime',title: "创建时间",minWidth: 120, align: 'center'}
                ,{field: 'debtPrice',title: "债务金额",minWidth: 120, align: 'center'}
                ,{field: 'debtOwnerName',title: "债务拥有者",minWidth: 120, align: 'center'}
                ,{field: 'debtRemark',title: "债务备注",minWidth: 120, align: 'center'}
                ,{field: 'debtStatus',title: "债务状态",minWidth: 120, align: 'center',templet:function (d) {
                        if(d.debtStatus===1){
                            return '<div class="debtStatusCSS2">未还清</div>'
                        }else{
                            return '<div class="debtStatusCSS1">已还清</div>'
                        }}
                }
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 15,
            page: true,
            skin: 'line'
        });

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            console.log(data.field);
            //执行搜索重载
            if(data.field.debtType === "-1"){
                tableIns.reload({
                    url: '/user/getDebtListByPage.action',
                    method: "post",
                    page: {
                        curr: 1
                    }
                    , where: data.field,
                }, 'json');
            }
            else {
                tableIns.reload({
                    url: '/user/searchDebtList.action',
                    method: "post",
                    page: {
                        curr: 1
                    }
                    , where: data.field,
                }, 'json');
            }
            return false;
        });


        //执行一个laydate实例
        laydate.render({
            elem: '#searchTime'//指定元素
            ,type: 'datetime'
            // ,range: true
            ,range: ['#startTime','#endTime']
        });


    });
</script>

</body>
</html>
