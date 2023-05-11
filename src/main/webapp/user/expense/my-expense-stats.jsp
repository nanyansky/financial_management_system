<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/4/16
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>统计</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="../../statics/layui/lib/layui-v2.7.6/css/layui.css" media="all">
  <link rel="stylesheet" href="../../statics/layui/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
  <link rel="stylesheet" href="../../statics/layui/css/public.css" media="all">
  <style>
    .top-panel {
      border: 1px solid #eceff9;
      border-radius: 5px;
      text-align: center;
    }
    .top-panel > .layui-card-body{
      height: 60px;
    }
    .top-panel-number{
      line-height:60px;
      font-size: 30px;
      border-right:1px solid #eceff9;
    }
    .top-panel-tips{
      line-height:30px;
      font-size: 12px
    }
  </style>
</head>
<body>
<!--<div class="layuimini-container">-->
<div class="layuimini-main">

  <div class="layui-row">
    <div class="layui-col-md6">
      <div id="echarts-pies-3" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
    </div>
    <div class="layui-col-md6">
      <div id="echarts-pies-4" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
    </div>
  </div>

  <div id="echarts-records" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>

</div>

<script src="../../statics/layui/lib/layui-v2.7.6/layui.js" charset="utf-8"></script>
<script src="../../statics/layui/js/lay-config.js?v=1.0.4" charset="utf-8"></script>
<script>
  layui.use(['layer', 'echarts'], function () {
    var $ = layui.jquery,
            layer = layui.layer,
            echarts = layui.echarts;



    var time1=[];
    var expenseCount=[];
    var expenseMoney=[];

    //支出数据
    $.ajax({
      type: "GET",
      url: "/getExpenseData.action?userName=${sessionScope.user.userName}",
      async: false,
      datatype: "json",
      success: function (data) {
        $.each(data.data, function(i) {
          time1.push(data.data[i].date)
          expenseCount.push(data.data[i].count)
          expenseMoney.push(data.data[i].money)
        })
        // console.log(data)
        // console.log(time1)
        // console.log(incomeCount)
        // console.log(incomeMoney)
      }
    })


    //支出类型数据
    var expenseTypeName = [];
    var expenseTypeCountData = [];
    var expenseTypeMoneyData = [];

    //支出
    $.ajax({
      type: "GET",
      url: "/getExpenseTypeData.action?userName=${sessionScope.user.userName}",
      async: false,
      datatype: "json",
      success: function (data) {
        $.each(data.expenseMoney, function(i) {
          expenseTypeName.push(data.expenseMoney[i].name)
        })
        expenseTypeCountData = data.expenseCount
        expenseTypeMoneyData = data.expenseMoney
      }
    })



    /**
     * 报表功能
     */
    var echartsRecords = echarts.init(document.getElementById('echarts-records'), 'walden');

    var optionRecords = {
      title: {
        text: '支出报表'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        }
      },
      legend: {
        data: ['支出账单总数', '支出金额']
      },
      toolbox: {
        feature: {
          saveAsImage: {}
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: time1
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: '支出账单总数',
          type: 'line',
          areaStyle: {},
          data: expenseCount
        },
        {
          name: '支出金额',
          type: 'line',
          areaStyle: {},
          data: expenseMoney
        }
      ]
    };
    echartsRecords.setOption(optionRecords);


    /**
     * 玫瑰图表
     */

    var echartsPies = echarts.init(document.getElementById('echarts-pies-3'), 'walden');
    var optionPies = {
      title: {
        text: '支出分类（帐单数）',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: expenseTypeName
        // data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
      },
      series: [
        {
          name: '支出分类',
          type: 'pie',
          radius: '55%',
          center: ['50%', '60%'],
          roseType: 'radius',
          data: expenseTypeCountData,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    echartsPies.setOption(optionPies);

    var echartsPies = echarts.init(document.getElementById('echarts-pies-4'), 'walden');
    var optionPies = {
      title: {
        text: '支出分类（金额数）',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: expenseTypeName
        // data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
      },
      series: [
        {
          name: '支出分类',
          type: 'pie',
          radius: '55%',
          center: ['50%', '60%'],
          roseType: 'radius',
          data: expenseTypeMoneyData,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    echartsPies.setOption(optionPies);


    // echarts 窗口缩放自适应
    window.onresize = function () {
      echartsRecords.resize();
    }
  });
</script>
</body>
</html>
