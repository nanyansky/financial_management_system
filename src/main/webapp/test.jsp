<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>滑块</title>
    <link rel="stylesheet" href="statics/layui/lib/layui-v2.6.3/css/layui.css">
</head>
<body>
<div id="slideTest1"></div>
<script src="statics/layui/lib/layui-v2.6.3/layui.js"></script>
<script>
    layui.use('slider', function(){
        var slider = layui.slider;

        //渲染
        slider.render({
            elem: '#slideTest1'  //绑定元素
        });
    });
</script>
</body>
</html>
      