<%--
  Created by IntelliJ IDEA.
  User: nanyan
  Date: 2023/3/29
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改资料</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../statics/layui/lib/layui-v2.6.3/css/layui.css" media="all">
    <link rel="stylesheet" href="../statics/layui/css/public.css" media="all">
    <style>
        .layui-form-item .layui-input-company {width: auto;padding-right: 10px;line-height: 38px;}
    </style>
</head>
<body>


<div class="layuimini-container">
    <div class="layuimini-main">

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
<script src="../statics/layui/lib/layui-v2.6.3/layui.js" charset="utf-8"></script>
<script src="../statics/layui/js/lay-config.js?v=1.0.4" charset="utf-8"></script>
<script>
    layui.use(['form','miniTab'], function () {
        var form = layui.form,
            layer = layui.layer,
            miniTab = layui.miniTab;
        var tmpUserName = ${sessionScope.user.userName};

        $.get("/user/findByUserName.action",tmpUserName,function (result){
            console.log(result);
            form.val("dataFrm",result.data)
        },"json");

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            var index = layer.alert(JSON.stringify(data.field), {
                title: '最终的提交信息'
            }, function () {
                layer.close(index);
                miniTab.deleteCurrentByIframe();
            });
            return false;
        });

    });
</script>
</body>
</html>

</body>
</html>
