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
    <title>收入账单管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../statics/layui/lib/layui-v2.7.6/css/layui.css" media="all">
    <link rel="stylesheet" href="../statics/layui/css/public.css" media="all">
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
                        <select name="userName" lay-verify="" id="userfl">
                            <option value="">请选择一个用户</option>
                        </select>
<%--                        <input type="hidden" name="id">--%>
<%--                        <input type="text" name="userName" lay-verify="required" autocomplete="off"--%>
<%--                               placeholder="请输入用户名" class="layui-input">--%>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">收入分类</label>
                    <div class="layui-input-block">
                        <select name="incomeTypeId" lay-verify="" id="fenlei-1">
                            <option value="">请选择一个分类</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label required">收入金额</label>
                    <div class="layui-input-block">
                        <input type="number" name="incomeAmount" lay-verify="required" lay-reqtext="金额为空" placeholder="请输入收入金额" value="" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">收入时间</label>
                    <div class="layui-input-block">
                        <input name="incomeTime" type="text" class="layui-input" id="incomeTime">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">收入说明</label>
                    <div class="layui-input-block">
                        <textarea name="incomeContent" required lay-verify="required" placeholder="请输入支出说明" class="layui-textarea"></textarea>
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
            url: '/income/getIncomeListByPage.action',
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports'],
            cols: [[
                { templet: function (d) {return parseInt(d.LAY_TABLE_INDEX) + 1;}, title: '序号', width: 80, fixed: 'left' }//序号列
                // {field: 'id', title: 'ID', width:80,align: 'center'}
                ,{field: 'userName', title: '用户名', minWidth:120, align: 'center'}
                ,{field: 'incomeTypeId',title: "收入分类",minWidth: 120, align: 'center',templet: function (d) {
                        let name = '';
                        $.ajax({
                            type: "GET",
                            url: "/getIncomeTypeNameById.action",
                            data: { id: d.incomeTypeId },
                            dataType: "JSON",
                            async: false, //不能发送异步请求，否则 name无法赋值
                            success: function(result) {
                                console.log(result);
                                name = result.name;
                            }
                        });
                        return name;
                    }}
                ,{field: 'incomeTime',title: "收入时间",minWidth: 120, align: 'center'}
                ,{field: 'createTime',title: "入账时间",minWidth: 120, align: 'center'}
                ,{field: 'incomeAmount',title: "收入金额",minWidth: 120, align: 'center'}
                ,{field: 'incomeContent',title: "收入备注",minWidth: 120, align: 'center'}
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
                title: "添加收入记录",
                area: ["800px","500px"],
                content: $("#addOrUpdateWindow"),
                success: function (){
                    //清空数据表单
                    $("#dataFrm")[0].reset();
                    //添加提交的请求
                    url = "/income/addIncome.action"
                }
            })
        }
        /**
         * 打开修改窗口
         */
        function openEditWindows(data) {
            mainIndex = layer.open({
                type: 1,
                title: "修改收入记录",
                area: ["800px","500px"],
                content: $("#addOrUpdateWindow"),
                success: function (){
                    //表单数据回写
                    form.val("dataFrm",data)
                    //添加修改的请求
                    url = "/income/editIncome.action"
                }
            })
        }

        //根据id删除用户
        function deleteById(data){
            layer.confirm("确定要删除该条记录吗？",{icon: 3,title: '提示'},function (index) {
                //发送ajax请求删除
                $.get("/income/deleteIncomeById.action",{"id":data.id},function (result){
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
            url: "/getIncomeTypeList.action",
            dataType: "JSON",
            success: function(result) {
                // console.log(result);
                // name = result.name;
                $.each(result.data,function (index,value) {
                    // console.log(value.name);
                    $('#fenlei-1').append(new Option(value.name,value.id));
                    $('#fenlei-2').append(new Option(value.name,value.id));
                });
                layui.form.render("select");
            }
        })

        $.ajax({
            type: "GET",
            url: "/user/getUserList.action",
            dataType: "JSON",
            success: function(result) {
                // console.log(result);
                // name = result.name;
                $.each(result.data,function (index,value) {
                    // console.log(value.name);
                    $('#userfl').append(new Option(value.userName,value.userName));
                });
                layui.form.render("select");
            }
        })

    });
</script>

</body>
</html>
