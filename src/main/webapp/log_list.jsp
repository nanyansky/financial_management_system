<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/4/1
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>用户操作日志</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="./statics/layui/lib/layui-v2.6.3/css/layui.css" media="all">
  <link rel="stylesheet" href="./statics/layui/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
  <div class="layuimini-main">

    <%--        搜索条件区域--%>
    <fieldset class="table-search-fieldset">
      <legend>搜索信息</legend>
      <div style="margin: 10px 10px 10px 10px">
        <form class="layui-form layui-form-pane" action="">
          <div class="layui-form-item">
            <div class="layui-inline">
              <label class="layui-form-label">用户名</label>
              <div class="layui-input-inline">
                <input type="text" name="userName" autocomplete="off" class="layui-input">
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
<script src="./statics/layui/lib/layui-v2.6.3/layui.js" charset="utf-8"></script>

<script>
  layui.use(['form', 'table', 'layer'], function () {
    var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            layer = layui.layer

    var tableIns = table.render({
      elem: '#currentTableId',
      url: '/log/getLogList.action',
      toolbar: '#toolbarDemo',
      cols: [[
        {field: 'id', title: 'ID', width:80, align: 'center'}
        ,{field: 'userName', title: '用户名', minWidth:120, align: 'center'}
        ,{field: 'operationTime', title: '操作时间', minWidth:120, align: 'center'}
        ,{field: 'operationType',title: "操作类别",minWidth: 120, align: 'center'}
        ,{field: 'operationContent',title: "操作内容",minWidth: 120, align: 'center'}
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
        url: '/log/getLogListByUserName.action',
        method: "post",
        page: {
          curr: 1
        }
        , where: data.field,
      }, 'json');
      return false;
    });
  });
</script>

</body>
</html>