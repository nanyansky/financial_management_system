<%--
  Created by IntelliJ IDEA.
  User: 28264
  Date: 2023/5/18
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>资产管理</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="../statics/layui/lib/layui-v2.7.6/css/layui.css" media="all">
  <link rel="stylesheet" href="../statics/layui/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
  <div class="layuimini-main">


    <%--        头部工具栏区域--%>
    <script type="text/html" id="toolbarDemo">
      <div class="layui-btn-container">
        <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add"><i class="layui-icon layui-icon-add-1"></i> 添加 </button>
        <%--                <button class="layui-btn layui-btn-sm layui-btn-danger data-delete-btn" lay-event="delete"><i class="layui-icon layui-icon-delete"></i> 删除 </button>--%>
      </div>
    </script>
    <%--        表格区域--%>
    <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
    <%--        行工具栏区域--%>
    <script type="text/html" id="currentTableBar">
      <a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
      <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>
    </script>

    <%-- 添加和修改窗口 --%>
    <div style="display: none;padding: 5px" id="addOrUpdateWindow">
      <form class="layui-form" style="width:90%;" id="dataFrm" lay-filter="dataFrm">

        <div class="layui-form-item">
          <input type="hidden" name="assetsId">
          <label class="layui-form-label">资产名称</label>
          <div class="layui-input-block">
            <input type="text" name="assetsName" class="layui-input" lay-verify="required">
          </div>
        </div>

        <div class="layui-form-item">
          <label class="layui-form-label">资产位置</label>
          <div class="layui-input-block">
            <input type="text" name="assetsLocation" class="layui-input" lay-verify="required">
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">资产价值</label>
          <div class="layui-input-block">
            <input type="text" name="assetsPrice" class="layui-input" lay-verify="required">
          </div>
        </div>
        <div class="layui-form-item">
          <input type="hidden" name="assetsOwnerId">
          <label class="layui-form-label">资产拥有人</label>
          <div class="layui-input-block">
            <select name="assetsOwnerName" lay-verify="required" id="userfl-2">
              <option value="">请选择一个用户</option>
            </select>
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">资产备注</label>
          <div class="layui-input-block">
            <input type="text" name="assetsRemark" class="layui-input" lay-verify="required">
          </div>
        </div>


        <div class="layui-form-item layui-row layui-col-xs12">
          <div class="layui-input-block" style="text-align: center;">
            <button type="button" class="layui-btn" lay-submit lay-filter="doSubmit"><span
                    class="layui-icon layui-icon-add-1"></span>提交
            </button>
            <button type="reset" class="layui-btn layui-btn-warm"><span
                    class="layui-icon layui-icon-refresh-1"></span>重置
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script src="../statics/layui/lib/layui-v2.7.6/layui.js" charset="utf-8"></script>


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
        ,{title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: 'center'}
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


    //监听头部工具栏事件
    table.on('toolbar(currentTableFilter)', function (obj) {
      switch (obj.event){
        case "add":
          openAddWindows();
          break;
      }
    })


    //监听行工具栏事件
    table.on('tool(currentTableFilter)', function (obj) {
      console.log(obj)
      switch (obj.event){
        case "edit":
          openEditWindows(obj.data);
          break;
        case "delete":
          deleteById(obj.data);
      }
    })

    var url; //提交地址
    var mainIndex; //打开窗口的索引

    /**
     * 打开添加窗口
     */
    function openAddWindows() {
      mainIndex = layer.open({
        type: 1,
        title: "添加资产记录",
        area: ["800px","500px"],
        content: $("#addOrUpdateWindow"),
        success: function (){
          //清空数据表单
          $("#dataFrm")[0].reset();
          //添加提交的请求
          url = "/user/addAssets.action"
        }
      })
    }
    /**
     * 打开修改窗口
     */
    function openEditWindows(data) {
      mainIndex = layer.open({
        type: 1,
        title: "修改资产记录",
        area: ["800px","500px"],
        content: $("#addOrUpdateWindow"),
        success: function (){
          //表单数据回写
          form.val("dataFrm",data)
          //添加修改的请求
          url = "/user/updateAssets.actio"
        }
      })
    }

    //根据id删除用户
    function deleteById(data){
      layer.confirm("确定要删除该条记录吗？",{icon: 3,title: '提示'},function (index) {
        //发送ajax请求删除
        $.get("/user/updateAssetsDeleted.action",{"assetsId":data.assetsId},function (result){
          if (result.code === 1){
            layer.msg(result.message);
            //表格刷新
            tableIns.reload();
          }
        })
        layer.close(index);
      })
    }

    //监听表单提交事件
    form.on("submit(doSubmit)",function (data) {

        //发送ajax请求
      $.ajax({
        type: "get",
        async: false,
        url: "/user/findByUserName.action",
        data: {userName:data.field.assetsOwnerName},
        dataType: "JSON",
        success: function(result) {
          console.log(result);
          form.val("dataFrm",{
            "assetsOwnerId": result.data.id
          })
          data.field.assetsOwnerId = result.data.id;
        }
      });

      console.log(data.field)
      $.post(url,data.field,function (result) {
        if(result.code === 1){
          //表格刷新
          tableIns.reload();
          //关闭窗口
          layer.close(mainIndex);
        }
        //提示信息
        layer.msg(result.message);
      },"json");

      return false;
    })


    //执行一个laydate实例
    laydate.render({
      elem: '#incomeTime'//指定元素
      ,type: 'datetime'
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
      url: "/user/getUserList.action",
      dataType: "JSON",
      success: function(result) {
        // console.log(result);
        // name = result.name;
        $.each(result.data,function (index,value) {
          // console.log(value.name);
          $('#userfl-2').append(new Option(value.userName,value.userName));
        });
        layui.form.render("select");
      }
    })

  });
</script>

</body>
</html>

