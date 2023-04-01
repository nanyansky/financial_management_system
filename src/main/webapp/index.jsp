<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/3/20
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                                <h1 class="no-margins"><i class="fa fa-user"></i> ${sessionScope.userNumber} 位</h1>
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
                                <h1 class="no-margins"><i class="fa fa-wallet"></i> 1234 笔</h1>
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
                                <h1 class="no-margins"><i class="fas fa-hand-holding-usd"></i> 1234 笔</h1>
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
                                <h1 class="no-margins"><i class="fa fa-pie-chart"></i> 1234 元</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%--    <div class="layui-box">--%>
<%--        <div class="layui-row layui-col-space10">--%>
<%--            <div class="layui-col-md12">--%>
<%--                <blockquote class="layui-elem-quote main_btn">--%>
<%--&lt;%&ndash;                    <p>测试</p>&ndash;%&gt;--%>
<%--                    <p>本模板基于layui2.5.4以及font-awesome-4.7.0进行实现。layui开发文档地址：<a class="layui-btn layui-btn-xs layui-btn-danger" target="_blank" href="http://www.layui.com/doc">layui文档</a></p>--%>
<%--                    <p>技术交流QQ群（561838086）：<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5JRGVfe"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="layuimini" title="layuimini"></a>（加群请备注来源：如gitee、github、官网等）</p>--%>
<%--                    <p>喜欢此后台模板的可以给我的GitHub和Gitee加个Star支持一下</p>--%>
<%--                    <p>GitHub地址：--%>
<%--                        <iframe src="https://ghbtns.com/github-btn.html?user=zhongshaofa&repo=layuimini&type=star&count=true" frameborder="0" scrolling="0" width="100px" height="20px"></iframe>--%>
<%--                        <iframe src="https://ghbtns.com/github-btn.html?user=zhongshaofa&repo=layuimini&type=fork&count=true" frameborder="0" scrolling="0" width="100px" height="20px"></iframe>--%>
<%--                    </p>--%>
<%--                    <p>Gitee地址：<a href="https://gitee.com/zhongshaofa/layuimini" target="_blank"><img src="https://gitee.com/zhongshaofa/layuimini/badge/star.svg?theme=dark" alt="star"></a> <a href="https://gitee.com/zhongshaofa/layuimini" target="_blank"><img src="https://gitee.com/zhongshaofa/layuimini/badge/fork.svg?theme=dark" alt="fork"></a></p>--%>
<%--                </blockquote>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

    <div class="layui-box">
        <div class="layui-row layui-col-space10">

            <div class="layui-col-md6">
                <h2 align="center">用户列表</h2>
                <table id="user" lay-filter="user"></table>
            </div>

            <div class="layui-col-md6">
                <h2 align="center">用户日志</h2>
                <ul class="layui-timeline">
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">8月18日</h3>
                            <p>
                                layui 2.0 的一切准备工作似乎都已到位。发布之弦，一触即发。
                                <br>不枉近百个日日夜夜与之为伴。因小而大，因弱而强。
                                <br>无论它能走多远，抑或如何支撑？至少我曾倾注全心，无怨无悔 <i class="layui-icon"></i>
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">8月16日</h3>
                            <p>杜甫的思想核心是儒家的仁政思想，他有“<em>致君尧舜上，再使风俗淳</em>”的宏伟抱负。个人最爱的名篇有：</p>
                            <ul>
                                <li>《登高》</li>
                                <li>《茅屋为秋风所破歌》</li>
                            </ul>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">8月15日</h3>
                            <p>
                                中国人民抗日战争胜利72周年
                                <br>常常在想，尽管对这个国家有这样那样的抱怨，但我们的确生在了最好的时代
                                <br>铭记、感恩
                                <br>所有为中华民族浴血奋战的英雄将士
                                <br>永垂不朽
                            </p>
                        </div>
                    </li>
                    <li class="layui-timeline-item">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text">
                            <div class="layui-timeline-title">过去</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script src="statics/layui/lib/layui-v2.6.3/layui.js" charset="utf-8"></script>
<script>

    layui.use('table', function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#user'
            // ,height: 420
            ,url: '/user/getUserListByPage.action' //数据接口
            ,page: false //开启分页
            ,cols: [[ //表头
                 {field: 'id', title: 'ID', minWidth:60, fixed: 'left',align: 'center'}
                ,{field: 'userName', title: '用户名', minWidth:80,align: 'center'}
                ,{field: 'sex', title: '性别', minWidth:80, align: 'center'}
                ,{field: 'registerTime',title: "加入时间",minWidth: 170, align: 'center'}
                ,{field: 'phoneNumber',title: "电话号码",minWidth: 130,align: 'center'}
                ,{field: 'isAdmin', title: '是否管理员', minWidth: 120, align: 'center'}
                ,{field: 'status', title: '账户状态', minwidth: 110, sort: true}
            ]]
        });
    });

</script>


</body>
</html>
