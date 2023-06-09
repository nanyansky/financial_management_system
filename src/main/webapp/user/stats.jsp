<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/4/6
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
  <link rel="stylesheet" href="../statics/layui/lib/layui-v2.7.6/css/layui.css" media="all">
  <link rel="stylesheet" href="../statics/layui/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
  <link rel="stylesheet" href="../statics/layui/css/public.css" media="all">
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

<%--  报表图--%>
  <div id="echarts-records" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
<%--  饼状图--%>
  <div class="layui-row">
    <div class="layui-col-md6">
      <div id="echarts-pies-1" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
    </div>
    <div class="layui-col-md6">
      <div id="echarts-pies-2" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
    </div>
  </div>

  <div class="layui-row">
    <div class="layui-col-md6">
      <div id="echarts-pies-3" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
    </div>
    <div class="layui-col-md6">
      <div id="echarts-pies-4" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
    </div>
  </div>



</div>
<!--</div>-->
<script src="../statics/layui/lib/layui-v2.7.6/layui.js" charset="utf-8"></script>
<script src="../statics/layui/js/lay-config.js?v=1.0.4" charset="utf-8"></script>
<script>
  layui.use(['layer', 'echarts'], function () {
    var $ = layui.jquery,
            layer = layui.layer,
            echarts = layui.echarts;



    var time1=[];
    var incomeCount=[];
    var incomeMoney=[];
    var expenseCount=[];
    var expenseMoney=[];

    //收入数据
    $.ajax({
      type: "GET",
      url: "/getIncomeData.action?userName=",
      async: false,
      datatype: "json",
      success: function (data) {
        $.each(data.data, function(i) {
          time1.push(data.data[i].date)
          incomeCount.push(data.data[i].count)
          incomeMoney.push(data.data[i].money)
        })
        // console.log(data)
        // console.log(time1)
        // console.log(incomeCount)
        // console.log(incomeMoney)
      }
    })

    //支出数据
    $.ajax({
      type: "GTE",
      url: "/getExpenseData.action?userName=",
      async: false,
      datatype: "json",
      success: function (data) {
        $.each(data.data, function(i) {
          expenseCount.push(data.data[i].count)
          expenseMoney.push(data.data[i].money)
        })
        // console.log(data)
        // console.log(time1)
        // console.log(incomeCount)
        // console.log(incomeMoney)
      }
    })


    //收入类型数据
    var incomeTypeName = [];
    var incomeTypeCountData = [];
    var incomeTypeMoneyData = [];
    //支出类型数据
    var expenseTypeName = [];
    var expenseTypeCountData = [];
    var expenseTypeMoneyData = [];

    //收入
    $.ajax({
      type: "GET",
      url: "/getIncomeTypeData.action?userName=",
      async: false,
      datatype: "JSON",
      success: function (data) {
        $.each(data.incomeMoney, function(i) {
          incomeTypeName.push(data.incomeMoney[i].name)
        })
        incomeTypeCountData = data.incomeCount
        incomeTypeMoneyData = data.incomeMoney
        console.log(incomeTypeName)
      }
    })

    //支出
    $.ajax({
      type: "GET",
      url: "/getExpenseTypeData.action?userName=",
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
        text: '7日收入支出报表'
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
        data: ['收入账单总数', '支出账单总数', '收入金额', '支出金额']
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
          name: '收入账单总数',
          type: 'line',
          areaStyle: {},
          data: incomeCount
        },
        {
          name: '支出账单总数',
          type: 'line',
          areaStyle: {},
          data: expenseCount
        },
        {
          name: '收入金额',
          type: 'line',
          areaStyle: {},
          data: incomeMoney
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

    var echartsPies = echarts.init(document.getElementById('echarts-pies-1'), 'walden');
    var optionPies = {
      title: {
        text: '收入分类（帐单数）',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: incomeTypeName
        // data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
      },
      series: [
        {
          name: '收入分类',
          type: 'pie',
          radius: '55%',
          center: ['50%', '60%'],
          roseType: 'radius',
          data: incomeTypeCountData,
          // data: [
          //   {value: 335, name: '直接访问'},
          //   {value: 310, name: '邮件营销'},
          //   {value: 234, name: '联盟广告'},
          //   {value: 135, name: '视频广告'},
          //   {value: 368, name: '搜索引擎'}
          // ],
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

    var echartsPies = echarts.init(document.getElementById('echarts-pies-2'), 'walden');
    var optionPies = {
      title: {
        text: '收入分类（金额数）',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: incomeTypeName
        // data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
      },
      series: [
        {
          name: '收入分类',
          type: 'pie',
          radius: '55%',
          center: ['50%', '60%'],
          roseType: 'radius',
          data: incomeTypeMoneyData,
          // data: [
          //   {value: 335, name: '直接访问'},
          //   {value: 310, name: '邮件营销'},
          //   {value: 234, name: '联盟广告'},
          //   {value: 135, name: '视频广告'},
          //   {value: 368, name: '搜索引擎'}
          // ],
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



    /**
     * 柱状图
     */
    var echartsDataset = echarts.init(document.getElementById('echarts-dataset'), 'walden');

    var optionDataset = {
      legend: {},
      tooltip: {},
      dataset: {
        dimensions: ['product', '2015', '2016', '2017'],
        source: [
          {product: 'Matcha Latte', '2015': 43.3, '2016': 85.8, '2017': 93.7},
          {product: 'Milk Tea', '2015': 83.1, '2016': 73.4, '2017': 55.1},
          {product: 'Cheese Cocoa', '2015': 86.4, '2016': 65.2, '2017': 82.5},
          {product: 'Walnut Brownie', '2015': 72.4, '2016': 53.9, '2017': 39.1}
        ]
      },
      xAxis: {type: 'category'},
      yAxis: {},
      // Declare several bar series, each will be mapped
      // to a column of dataset.source by default.
      series: [
        {type: 'bar'},
        {type: 'bar'},
        {type: 'bar'}
      ]
    };

    echartsDataset.setOption(optionDataset);


    /**
     * 中国地图
     */
    var echartsMap = echarts.init(document.getElementById('echarts-map'), 'walden');


    var optionMap = {
      legend: {},
      tooltip: {
        trigger: 'axis',
        showContent: false
      },
      dataset: {
        source: [
          ['product', '2012', '2013', '2014', '2015', '2016', '2017'],
          ['Matcha Latte', 41.1, 30.4, 65.1, 53.3, 83.8, 98.7],
          ['Milk Tea', 86.5, 92.1, 85.7, 83.1, 73.4, 55.1],
          ['Cheese Cocoa', 24.1, 67.2, 79.5, 86.4, 65.2, 82.5],
          ['Walnut Brownie', 55.2, 67.1, 69.2, 72.4, 53.9, 39.1]
        ]
      },
      xAxis: {type: 'category'},
      yAxis: {gridIndex: 0},
      grid: {top: '55%'},
      series: [
        {type: 'line', smooth: true, seriesLayoutBy: 'row'},
        {type: 'line', smooth: true, seriesLayoutBy: 'row'},
        {type: 'line', smooth: true, seriesLayoutBy: 'row'},
        {type: 'line', smooth: true, seriesLayoutBy: 'row'},
        {
          type: 'pie',
          id: 'pie',
          radius: '30%',
          center: ['50%', '25%'],
          label: {
            formatter: '{b}: {@2012} ({d}%)'
          },
          encode: {
            itemName: 'product',
            value: '2012',
            tooltip: '2012'
          }
        }
      ]
    };

    echartsMap.setOption(optionMap);


    // echarts 窗口缩放自适应
    window.onresize = function () {
      echartsRecords.resize();
    }

  });
</script>
</body>
</html>
