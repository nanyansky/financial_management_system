<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/3/22
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户管理</title>
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
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                        <input type="hidden" name="id">
                        <input type="text" name="userName" lay-verify="required" autocomplete="off"
                               placeholder="请输入用户名" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input type="text" name="password" lay-verify="required" autocomplete="off" placeholder="请输入用户密码"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">手机</label>
                    <div class="layui-input-block">
                        <input type="number" name="phoneNumber" lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机" value="" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">性别</label>
                    <div class="layui-input-block">
                        <input type="radio" name="sex" value="男" title="男" checked="">
                        <input type="radio" name="sex" value="女" title="女">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">是否为管理员</label>
                    <div class="layui-input-block">
                        <input type="radio" name="isAdmin" value="1" title="是">
                        <input type="radio" name="isAdmin" value="0" title="否" checked="">
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
            url: '/user/getUserListByPage.action',
            toolbar: '#toolbarDemo',
            cols: [[
                {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'userName', title: '用户名', minWidth:120, align: 'center'}
                ,{field: 'sex', title: '性别', minWidth:120, sort: true, align: 'center'}
                ,{field: 'registerTime',title: "加入时间",minWidth: 120, align: 'center'}
                ,{field: 'phoneNumber',title: "电话号码",minWidth: 120, align: 'center'}
                ,{field: 'isAdmin', title: '是否管理员', minWidth: 120, align: 'center'}
                // ,{field: 'isDeleted', title: '账户状态', minWidth: 120, align: 'center'}
                ,{field: 'status', title: '账户状态', minWidth: 120, align: 'center', templet: function (res) {
                        if (res.status === 1){
                            return '<input type="checkbox" checked="checked" name="status" lay-filter="switchTest" lay-skin="switch" lay-text="启用|禁用" userId=' + res.id + '>';
                        } else if (res.status === 0){
                            return '<input type="checkbox" name="is_del" lay-filter="switchTest" lay-skin="switch" lay-text="启用|禁用" userId=' + res.id + '>'
                        }
                    }}
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
                url: '/user/findListByUserName.action',
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

        // 监听user Switch
        form.on('switch(switchTest)',function (data) {
            var status = this.checked ? "1" : "0";
            // layer.msg(status);
            console.log(data);
            console.log(data.elem.attributes['userId'].nodeValue);
            $.ajax({
                url: '/user/changeUserStatus.action',
                type: 'post',
                data: {
                    'id': data.elem.attributes['userId'].nodeValue,
                    'status': status
                },
                success: function (res) {
                    layer.msg(res.message);
                },
                dataType: 'json'
            });
            form.render();
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
                title: "添加用户",
                area: ["800px","500px"],
                content: $("#addOrUpdateWindow"),
                success: function (){
                    //清空数据表单
                    $("#dataFrm")[0].reset();
                    //添加提交的请求
                    url = "/user/addUser.action"
                }
            })
        }
        /**
         * 打开修改窗口
         */
        function openEditWindows(data) {
            mainIndex = layer.open({
                type: 1,
                title: "修改用户",
                area: ["800px","500px"],
                content: $("#addOrUpdateWindow"),
                success: function (){
                    //表单数据回写
                    form.val("dataFrm",data)
                    //添加修改的请求
                    url = "/user/editUser.action"
                }
            })
        }

        //根据id删除用户
        function deleteById(data){
            layer.confirm("确定要删除用户 <b><font color='red' weight:bold>"+data.userName+"</font></b> 吗？",{icon: 3,title: '提示'},function (index) {
                //发送ajax请求删除
                $.get("/user/deleteById.action",{"id":data.id},function (result){
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