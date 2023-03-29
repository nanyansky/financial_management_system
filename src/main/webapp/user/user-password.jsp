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
    <title>修改密码</title>
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

        <div class="layui-form layuimini-form">
            <div class="layui-form-item">
                <label class="layui-form-label required">旧的密码</label>
                <div class="layui-input-block">
                    <input type="password" name="old_password" lay-verify="required" lay-reqtext="旧的密码不能为空" placeholder="请输入旧的密码"  value="" class="layui-input">
                    <tip>填写自己账号的旧的密码。</tip>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label required">新的密码</label>
                <div class="layui-input-block">
                    <input type="password" name="new_password" id="pwd1" lay-verify="required" lay-reqtext="新的密码不能为空" placeholder="请输入新的密码"  value="" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label required">确认密码</label>
                <div class="layui-input-block">
                    <input type="password" name="again_password" id="pwd2" onblur="confirmPwd()" lay-verify="required" lay-reqtext="密码不能为空" placeholder="请确认新的密码"  value="" class="layui-input">
                    <span id="pwd2Span" style="color: red;font-family: 楷体"></span></p>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" id="saveBtn" lay-submit lay-filter="saveBtn">确认保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="../statics/layui/lib/layui-v2.6.3/layui.js" charset="utf-8"></script>
<script src="../statics/layui/js/lay-config.js?v=1.0.4" charset="utf-8"></script>
<script>
    layui.use(['form','miniTab'], function () {
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer,
            miniTab = layui.miniTab;


        //监听提交
        form.on('submit(saveBtn)', function (data) {
            // console.log(data);
            $.ajax({
                url: '/user/changePwdByUsername.action',
                type: 'post',
                data: data.field,
                success: function (res) {
                    if(res.code === 1){
                        $.get("/user/logout.action",function (result){
                            if(result.code === 1){
                                location.href = "/login.jsp";
                            }
                        },"json");
                    }
                    layer.msg(res.message);
                },
                dataType: 'json'
            });
            return false;
        });
    });

    function confirmPwd() {
        var text1 = document.getElementById("pwd1").value;
        var text2 = document.getElementById("pwd2").value;
        if (text1 !== text2){
            document.getElementById("pwd2Span").innerHTML = "两次输入的密码不一致";
            document.getElementById("saveBtn").disabled = true;
            return false;
        }
        else {
            document.getElementById("pwd2Span").innerHTML = "";
            document.getElementById("saveBtn").disabled = false;
            return true;
        }
    }
</script>
</body>
</html>
</body>
</html>
