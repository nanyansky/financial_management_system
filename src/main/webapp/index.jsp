<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/3/20
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--导入格式化金额标签--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>主页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="statics/layui/lib/layui-v2.6.3/css/layui.css" media="all">
    <link rel="stylesheet" href="statics/layui/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="statics/layui/css/public.css" media="all">
    <link rel="stylesheet" href="statics/layui/lib/layui-v2.6.3/css/layui.css" media="all">
</head>
<style>
    .layui-top-box {padding:40px 20px 20px 20px;color:#fff}
    .panel {margin-bottom:17px;background-color:#fff;border:1px solid transparent;border-radius:3px;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.05);box-shadow:0 1px 1px rgba(0,0,0,.05)}
    .panel-body {padding:15px}
    .panel-title {margin-top:0;margin-bottom:0;font-size:14px;color:inherit}
    .label {display:inline;padding:.2em .6em .3em;font-size:75%;font-weight:700;line-height:1;color:#fff;text-align:center;white-space:nowrap;vertical-align:baseline;border-radius:.25em;margin-top: .3em;}
    .layui-red {color:red}
    .main_btn > p {height:40px;}
    .layui-card {border:1px solid #f2f2f2;border-radius:5px;}
    .icon {margin-right:10px;color:#1aa094;}
    .icon-cray {color:#ffb800!important;}
    .icon-blue {color:#1e9fff!important;}
    .icon-tip {color:#ff5722!important;}
    .layuimini-qiuck-module {text-align:center;margin-top: 10px}
    .layuimini-qiuck-module a i {display:inline-block;width:100%;height:60px;line-height:60px;text-align:center;border-radius:2px;font-size:30px;background-color:#F8F8F8;color:#333;transition:all .3s;-webkit-transition:all .3s;}
    .layuimini-qiuck-module a cite {position:relative;top:2px;display:block;color:#666;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;font-size:14px;}
    .welcome-module {width:100%;height:210px;}
    .panel {background-color:#fff;border:1px solid transparent;border-radius:3px;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.05);box-shadow:0 1px 1px rgba(0,0,0,.05)}
    .panel-body {padding:10px}
    .panel-title {margin-top:0;margin-bottom:0;font-size:12px;color:inherit}
    .label {display:inline;padding:.2em .6em .3em;font-size:75%;font-weight:700;line-height:1;color:#fff;text-align:center;white-space:nowrap;vertical-align:baseline;border-radius:.25em;margin-top: .3em;}
    .layui-red {color:red}
    .main_btn > p {height:40px;}
    .layui-bg-number {background-color:#F8F8F8;}
    .layuimini-notice:hover {background:#f6f6f6;}
    .layuimini-notice {padding:7px 16px;clear:both;font-size:12px !important;cursor:pointer;position:relative;transition:background 0.2s ease-in-out;}
    .layuimini-notice-title,.layuimini-notice-label {
        padding-right: 70px !important;text-overflow:ellipsis!important;overflow:hidden!important;white-space:nowrap!important;}
    .layuimini-notice-title {line-height:28px;font-size:14px;}
    .layuimini-notice-extra {position:absolute;top:50%;margin-top:-8px;right:16px;display:inline-block;height:16px;color:#999;}
</style>
<body>
<link href="https://cdn.bootcdn.net/ajax/libs/font-awesome/6.3.0/css/all.min.css" rel="stylesheet">
<div class="layuimini-container">
    <div class="layuimini-main layui-top-box">
        <div class="layui-row layui-col-space10">

            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-cyan">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-blue">实时</span>
                                <h5>用户统计</h5>
                            </div>
                            <div class="panel-content">
                                <h2 class="no-margins"><i class="fa fa-user"></i> ${sessionScope.userNumber} 位</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-blue">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-cyan">实时</span>
                                <h5>收入统计</h5>
                            </div>
                            <div class="panel-content">
                                <h2 class="no-margins" style="display: inline"><i class="fa fa-wallet"></i> ${sessionScope.incomeNumber} 笔</h2>
                                <h3 style="display: inline">(共 <fmt:formatNumber type="number" value="${sessionScope.incomeCount}" maxFractionDigits="2" pattern="0.00"/> 元)</h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-green">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-orange">实时</span>
                                <h5>支出统计</h5>
                            </div>
                            <div class="panel-content">
                                <h2 class="no-margins" style="display: inline"><i class="fas fa-hand-holding-usd"></i> ${sessionScope.expenseNumber} 笔</h2>
                                <h3 style="display: inline">(共 <fmt:formatNumber type="number" value="${sessionScope.expenseCount}" maxFractionDigits="2" pattern="0.00"/> 元)</h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md3">
                <div class="col-xs-6 col-md-3">
                    <div class="panel layui-bg-orange">
                        <div class="panel-body">
                            <div class="panel-title">
                                <span class="label pull-right layui-bg-green">实时</span>
                                <h5>剩余总额</h5>
                            </div>
                            <div class="panel-content">
                                <h2 class="no-margins"><i class="fa fa-pie-chart"> </i> <fmt:formatNumber type="number" value=" ${sessionScope.incomeCount - sessionScope.expenseCount}" maxFractionDigits="2" pattern="0.00"/> 元</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-box">
        <div class="layui-row layui-col-space10">

            <div class="layui-col-md8">
                <h2 align="center">用户列表</h2>
                <table id="user" lay-filter="user"></table>
            </div>

            <div class="layui-col-md4">
                <h2 align="center">用户动态</h2>
                <div class="layui-card">
                    <div class="layui-card-header"><i class="fa fa-bullhorn icon icon-tip"></i>用户动态</div>
                    <div class="layui-card-body layui-text">

                        <c:forEach items="${sessionScope.top10OperationLog}" var="log">

                            <div class="layuimini-notice">
                                <div class="layuimini-notice-title">${log.userName} ${log.operationContent}</div>
                                <div class="layuimini-notice-extra">${log.operationTime}</div>
                            </div>

                        </c:forEach>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="statics/layui/lib/layui-v2.6.3/layui.js" charset="utf-8"></script>
<script>

    layui.use('table', function(){
        var table = layui.table,
            $ = layui.jquery

        //第一个实例
        table.render({
            elem: '#user'
            // ,height: 420
            ,url: '/user/getUserListByPage.action' //数据接口
            ,page: false //开启分页
            ,cols: [[ //表头
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 80, fixed: 'left' }//序号列                ,{field: 'id', title: 'ID', minWidth:60, fixed: 'left',align: 'center'}
                ,{field: 'userName', title: '用户名', minWidth:80,align: 'center'}
                ,{field: 'sex', title: '性别', minWidth:80, align: 'center'}
                ,{field: 'registerTime',title: "加入时间",minWidth: 170, align: 'center'}
                ,{field: 'phoneNumber',title: "电话号码",minWidth: 130,align: 'center'}
                ,{field: 'isAdmin', title: '是否管理员', minWidth: 120, align: 'center',templet: function (d) {
                        return d.isAdmin === 1 ? "是" : "否";
                    }}
            ]]
        });

        $.ajax({
            url: "/log/getTop10OperationLog.action",
            type: "post",
            async: false,
            dataType: "json",
            success: function (data) {
                console.log("日志请求成功！")
            }
        });

    });


</script>

<%--<script type="text/html" id="xuhao">--%>
<%--    {{d.LAY_TABLE_INDEX+1}}--%>
<%--</script>--%>

</body>
</html>
