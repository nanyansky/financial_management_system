<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/5/10
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>股票列表</title>
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
                            <label class="layui-form-label" style="width: 100px">股票类型</label>
                            <div class="layui-input-inline" style="width: 100px">
                                <select name="node" lay-verify="node" id="nodeId">
                                    <option value="a" selected>沪深A股</option>
                                    <option value="ash">沪市A股</option>
                                    <option value="asz">深市A股</option>
                                </select>
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label" style="width: 100px">排序方法</label>
                            <div class="layui-input-inline" style="width: 100px">
                                <select name="asc" lay-verify="sortMethod" id="sortMethodId">
                                    <option value=0 selected>降序</option>
                                    <option value=1>升序</option>
                                    <option value=-1>默认序列</option>
                                </select>
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label" style="width: 100px">排序依据</label>
                            <div class="layui-input-inline" style="width: 100px">
                                <select name="sort" lay-verify="sort" id="sortId">
                                    <option value="turnover" selected>成交额</option>
                                    <option value="price">交易价格</option>
                                    <option value="open">开盘价格</option>
                                    <option value="close">收盘价格</option>
                                    <option value="volume">成交量</option>
                                    <option value="high">最高价</option>
                                    <option value="low">最高价</option>
                                </select>
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label" style="width: 100px">行业板块</label>
                            <div class="layui-input-inline" style="width: 140px">
                                <select name="industryCode" lay-verify="" id="fenlei-1">
                                    <option value="sw_jsj">计算机</option>
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
        <%--        行工具栏区域--%>
        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs layui-bg-red layuimini-content-href" lay-event="info" layuimini-content-href="/user/stock/stock-detail.jsp" data-title="股票详情">详情</a>
            <a class="layui-btn layui-btn-xs data-count-delete" lay-event="buy">购买</a>
        </script>


        <%--购买股票表单--%>
        <div style="display: none;padding: 5px" id="buyStock">
            <form class="layui-form" style="width:90%;" id="dataFrm" lay-filter="dataFrm">

                <div class="layui-form-item">
                    <label class="layui-form-label">股票代码</label>
                    <div class="layui-input-block">
                        <input type="text" name="code" class="layui-input" value="" disabled="disabled">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">股票名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" class="layui-input" value="" disabled="disabled">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">股票价格（元/股）</label>
                    <div class="layui-input-block">
                        <input type="text" name="price" class="layui-input" value="" disabled="disabled">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">股票数量（手）</label>
                    <div class="layui-input-block">
                        <input type="number" name="stockNum" class="layui-input" value="" placeholder="1手=100股" lay-verify="numberVerify">
                    </div>
                </div>


                <div class="layui-form-item layui-row layui-col-xs12">
                    <div class="layui-input-block" style="text-align: center;">
                        <button type="button" class="layui-btn" lay-submit lay-filter="doSubmit"><span
                                class="layui-icon layui-icon-add-1"></span>购买
                        </button>
                        <button type="button" class="layui-btn layui-btn-warm" lay-on="b1"  lay-filter="doCancel" "><span
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
                if(value == null || value == ''){
                    return '请先输入购买数量';
                }
            }
        })

        miniTab.listen();

        var tableIns = table.render({
            elem: '#currentTableId',
            url: '/stock/getStockList.action',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports'],
            cols: [[
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 60, fixed: 'left' }//序号列
                // {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'code', title: '股票代码',  align: 'center'}
                ,{field: 'name',title: "股票名称", align: 'center'}
                ,{field: 'open',title: "今日开盘价  ", align: 'center'}
                ,{field: 'close',title: "昨日收盘价", align: 'center'}
                ,{field: 'price',title: "实时价格", align: 'center'}
                ,{field: 'high',title: "最高价", align: 'center'}
                ,{field: 'low',title: "最低价", align: 'center'}
                ,{field: 'volume',title: "成交量/手", align: 'center'}
                ,{field: 'turnover',title: "成交额/万",align: 'center'}
                ,{field: 'totalWorth',title: "总市值/亿",align: 'center'}
                // ,{field: 'circulationWorth',title: "流通市值/亿", align: 'center'}
                // ,{field: 'pe',title: "市盈率", align: 'center'}
                // ,{field: 'spe',title: "静态市盈率", align: 'center'}
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
                url: '/stock/getStockList.action',
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
                    sessionStorage.setItem("stockCode",obj.data.code);
                    // 打开新的窗口
                    miniTab.openNewTabByIframe({
                        href:"/user/stock/stock-detail.jsp",
                        // href:"/user/stock/stock-detail.jsp?code="+obj.data.code,
                        title:"股票详情",
                    });
                    break;
                case "buy":
                    openBuyWindows(obj.data);
                    // buy(obj.data);
            }
        })


        $.ajax({
            type: "GET",
            url: "/stock/getStockIndustry.action",
            dataType: "JSON",
            success: function(result) {
                $.each(result.data,function (index,value) {
                    $('#fenlei-1').append(new Option(value.name,value.industryCode));
                });
                layui.form.render("select");
            }
        })

        var url; //提交地址
        var mainIndex; //打开窗口的索引

        /**
         * 打开购买窗口
         */
        function openBuyWindows(data) {
            mainIndex = layer.open({
                type: 1,
                title: "购买股票",
                area: ["800px","500px"],
                content: $("#buyStock"),
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
            $.post(url,data.field,function (result) {
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

    });
</script>

</body>
</html>
