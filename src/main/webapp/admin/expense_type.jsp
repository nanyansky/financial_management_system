<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/4/2
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>分类管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../statics/layui/lib/layui-v2.6.3/css/layui.css" media="all">
    <link rel="stylesheet" href="../statics/layui/css/public.css" media="all">
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
                            <label class="layui-form-label">支出分类名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="name" autocomplete="off" class="layui-input">
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
                    <label class="layui-form-label">支出分类名</label>
                    <div class="layui-input-block">
                        <input type="hidden" name="id">
                        <input type="text" name="name" lay-verify="required" autocomplete="off"
                               placeholder="请输入标签名" class="layui-input">
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
<script src="../statics/layui/lib/layui-v2.6.3/layui.js" charset="utf-8"></script>

<script>
    layui.use(['form', 'table', 'layer'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            layer = layui.layer

        var tableIns = table.render({
            elem: '#currentTableId',
            url: '/getExpenseTypeListByPage.action',
            toolbar: '#toolbarDemo',
            cols: [[
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 80, fixed: 'left' }//序号列
                // {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'name', title: '支出分类名', minWidth:120, align: 'center'}
                ,{field: 'createTime',title: "创建时间",minWidth: 120, align: 'center'}
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
                url: '/getExpenseTypeListByName.action',
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
                title: "添加支出分类",
                area: ["400px","200px"],
                content: $("#addOrUpdateWindow"),
                success: function (){
                    //清空数据表单
                    $("#dataFrm")[0].reset();
                    //添加提交的请求
                    url = "/addExpenseType.action"
                }
            })
        }
        /**
         * 打开修改窗口
         */
        function openEditWindows(data) {
            mainIndex = layer.open({
                type: 1,
                title: "修改支出分类",
                area: ["400px","200px"],
                content: $("#addOrUpdateWindow"),
                success: function (){
                    //表单数据回写
                    form.val("dataFrm",data)
                    //添加修改的请求
                    url = "/editExpenseType.action"
                }
            })
        }

        //根据id删除用户
        function deleteById(data){
            layer.confirm("确定要删除该条记录吗？",{icon: 3,title: '提示'},function (index) {
                //发送ajax请求删除
                $.get("/deleteExpenseByTypeById.action",{"id":data.id},function (result){
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

    });
</script>

</body>
</html>
