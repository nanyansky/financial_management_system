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
    <title>资产列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../statics/layui/lib/layui-v2.7.6/css/layui.css" media="all">
    <link rel="stylesheet" href="../../statics/layui/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">


        <%--        表格区域--%>
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
            url: '/user/getAssetsListByPage.action',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports'],

            cols: [[
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 80, fixed: 'left' }//序号列
                // {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'assetsName', title: '资产名称', minWidth:120, align: 'center'}
                ,{field: 'assetsLocation',title: "资产位置",minWidth: 120, align: 'center'}
                ,{field: 'assetsCreateTime',title: "创建时间",minWidth: 120, align: 'center'}
                ,{field: 'assetsPrice',title: "资产价值",minWidth: 120, align: 'center'}
                ,{field: 'assetsOwnerName',title: "资产拥有者",minWidth: 120, align: 'center'}
                ,{field: 'assetsRemark',title: "资产备注",minWidth: 120, align: 'center'}
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
            tableIns.reload({
                url: '/income/searchIncome.action',
                method: "post",
                page: {
                    curr: 1
                }
                , where: data.field,
            }, 'json');
            return false;
        });


        //执行一个laydate实例
        laydate.render({
            elem: '#searchTime'//指定元素
            ,type: 'datetime'
            // ,range: true
            ,range: ['#startTime','#endTime']
        });

        $.ajax({
            type: "GET",
            url: "/getIncomeTypeList.action",
            dataType: "JSON",
            success: function(result) {
                // console.log(result);
                // name = result.name;
                $.each(result.data,function (index,value) {
                    // console.log(value.name);
                    $('#fenlei-2').append(new Option(value.name,value.id));
                });
                layui.form.render("select");
            }
        })

    });
</script>

</body>
</html>
