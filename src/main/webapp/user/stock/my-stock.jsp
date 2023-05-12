<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/5/12
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>收入账单</title>
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
                            <label class="layui-form-label" style="width: 80px">用户名</label>
                            <div class="layui-input-inline" style="width: 90px">
                                <input type="text" name="userName" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label" style="width: 80px">分类</label>
                            <div class="layui-input-inline" style="width: 120px">
<%--                                <input type="text" name="userName" autocomplete="off" class="layui-input">--%>
                                <select name="incomeTypeId" lay-verify="" id="fenlei-2">
                                    <option value="-1">请选择一个分类</option>
                                </select>
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label" style="width: 140px">收入日期范围</label>
                            <div class="layui-inline" id="searchTime">
                                <div class="layui-input-inline" style="width: 160px">
                                    <input type="text" id="startTime" name="startTime" class="layui-input" placeholder="开始日期">
                                </div>
                                <div class="layui-form-mid">-</div>
                                <div class="layui-input-inline" style="width: 160px">
                                    <input type="text" id="endTime" name="endTime" class="layui-input" placeholder="结束日期">
                                </div>
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

        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs layui-bg-red layuimini-content-href" lay-event="info" layuimini-content-href="/user/stock/stock-detail.jsp" data-title="股票详情">详情</a>
            <a class="layui-btn layui-btn-xs data-count-delete" lay-event="sell">出售</a>
        </script>

        <%--出售股票表单--%>
        <div style="display: none;padding: 5px" id="sellStock">
            <form class="layui-form" style="width:90%;" id="dataFrm" lay-filter="dataFrm">

                <div class="layui-form-item">
                    <label class="layui-form-label">股票代码</label>
                    <div class="layui-input-block">
                        <input type="text" name="stockCode" class="layui-input" value="" disabled="disabled">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">股票名</label>
                    <div class="layui-input-block">
                        <input type="text" name="stockName" class="layui-input" value="" disabled="disabled">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">股票价格（元/股）</label>
                    <div class="layui-input-block">
                        <input type="text" name="stockPrice" class="layui-input" value="" disabled="disabled">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">持有数量（手）</label>
                    <div class="layui-input-block">
                        <input type="number" name="stockNum" id="originNum" class="layui-input" value="" disabled="disabled">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">卖出数量（手）</label>
                    <div class="layui-input-block">
                        <input type="number" name="sellStockNum" class="layui-input" value="" placeholder="1手=100股" lay-verify="numberVerify">
                    </div>
                </div>


                <div class="layui-form-item layui-row layui-col-xs12">
                    <div class="layui-input-block" style="text-align: center;">
                        <button type="button" class="layui-btn" lay-submit lay-filter="doSubmit"><span
                                class="layui-icon layui-icon-add-1"></span>出售
                        </button>
                        <button type="button" class="layui-btn layui-btn-warm" lay-on="b1"  lay-filter="doCancel"><span
                                class="layui-icon layui-icon-refresh-1 layui-icon-close"></span>取消
                        </button>
                    </div>
                </div>
            </form>
        </div>



    </div>
</div>
<script src="../../statics/layui/lib/layui-v2.7.6/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="../../statics/layui/js/lay-module/layuimini/miniTab.js"></script>


<script>
    layui.use(['form', 'table', 'layer','laydate','miniTab','util'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            layer = layui.layer,
            laydate = layui.laydate,
            miniTab = layui.miniTab,
            util = layui.util

        form.verify({
            numberVerify:function (value) {
                var tmp =  $('#originNum').val();
                if(value > tmp){
                    return '出售数量不能大于' + tmp;
                }
            }
        })

        var tableIns = table.render({
            elem: '#currentTableId',
            url: '/stock/getUserStockListByPage',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports'],

            cols: [[
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 80, fixed: 'left' }//序号列
                // {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'stockCode', title: '股票代码', align: 'center'}
                ,{field: 'stockName',title: "股票名称", align: 'center'}
                ,{field: 'stockPrice',title: "购买时价格（元/股）", align: 'center'}
                ,{field: 'stockNum',title: "持有数量（手）", align: 'center'}
                ,{field: 'stockTime',title: "购买时间", align: 'center'}
                ,{title: '操作', minWidth: 120, toolbar: '#currentTableBar', align: 'center'}
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

        //监听行工具栏事件
        table.on('tool(currentTableFilter)', function (obj) {
            console.log(obj)
            switch (obj.event){
                case "info":
                    //将股票代码存在sessionStorage中
                    sessionStorage.setItem("stockCode",obj.data.stockCode);
                    // 打开新的窗口
                    miniTab.openNewTabByIframe({
                        href:"/user/stock/stock-detail.jsp",
                        // href:"/user/stock/stock-detail.jsp?code="+obj.data.code,
                        title:"股票详情",
                    });
                    break;
                case "sell":
                    openSellWindows(obj.data);
                // buy(obj.data);
            }
        })


        /**
         * 打开出售窗口
         */
        function openSellWindows(data) {
            mainIndex = layer.open({
                type: 1,
                title: "出售股票",
                area: ["800px","500px"],
                content: $("#sellStock"),
                success: function (){
                    //表单数据回写
                    form.val("dataFrm",data)
                    //添加修改的请求
                    url = "/stock/buyStock.action"
                }
            })
        }

        //监听表单提交事件
        form.on("submit(doSubmit)",function (data) {
            // url
            $.post("url",data.field,function (result) {
                if(result.code === 1){
                    //关闭窗口
                    layer.close(mainIndex);
                }
                //提示信息
                layer.msg(result.message);
            },"json");

            return false;
        })
        //监听表单取消事件
        util.on("lay-on", {
            b1: function () {
                //关闭窗口
                layer.close(mainIndex);
                return false;
            }
        })

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
