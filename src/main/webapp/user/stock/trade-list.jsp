<%--
  Created by IntelliJ IDEA.
  User: 28264
  Date: 2023/5/17
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>股票交易记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../statics/layui/lib/layui-v2.7.6/css/layui.css" media="all">
    <link rel="stylesheet" href="../../statics/layui/css/public.css" media="all">
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
                                <%--                                <input type="text" name="userName" autocomplete="off" class="layui-input">--%>
                                <select name="tradeType" lay-verify="" id="fenlei-2">
                                    <option value = -1>全部</option>
                                    <option value = 1>买入</option>
                                    <option value = 0>售出</option>
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
            url: '/stock/getTradeListByPage.action',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports'],

            cols: [[
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 80, fixed: 'left' }//序号列
                // {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'stockCode', title: '股票代码', minWidth:120, align: 'center'}
                ,{field: 'stockName',title: "股票名称",minWidth: 120, align: 'center'}
                ,{field: 'stockPrice',title: "交易时股票价格",minWidth: 120, align: 'center'}
                ,{field: 'stockNum',title: "交易股票数量(手)",minWidth: 120, align: 'center'}
                ,{field: 'tradePrice',title: "交易总金额",minWidth: 120, align: 'center'}
                ,{field: 'tradeTime',title: "交易时间",minWidth: 120, align: 'center'}
                ,{field: 'tradeType',title: "交易类型",minWidth: 120, align: 'center',templet: function (d) {
                        return d.tradeType === 1 ? "买入" : "售出";
                    }}
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
            if(data.field.tradeType === "-1"){
                tableIns.reload({
                    url: '/stock/getTradeListByPage.action',
                    method: "post",
                    page: {
                        curr: 1
                    }
                    , where: data.field,
                }, 'json');
            }
            else {
                tableIns.reload({
                    url: '/stock/searchTradList.action',
                    method: "post",
                    page: {
                        curr: 1
                    }
                    , where: data.field,
                }, 'json');
            }

            return false;
        });

    });
</script>

</body>
</html>
