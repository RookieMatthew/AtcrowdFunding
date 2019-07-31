<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <form action="${APP_PATH}/doLogin.do" method="post" id="loginForm" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input name="loginacct" type="text" class="form-control" id="floginacct" value="zhangshouren" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input name="userpswd" type="password" class="form-control" value="123" id="fuserpswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select name="usertype" id="fusertype" class="form-control" >
                <option value="member">会员</option>
                <option value="user" selected>管理</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="${APP_PATH}/reg.htm">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
        <h4 id="formTip"></h4>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script>
    <%--同步请求--%>
    /*function dologin() {
        $("#loginForm").submit();
    }*/

    //异步请求
    function dologin() {
        var loginacct = $("#floginacct");
        var userpswd = $("#fuserpswd");
        var usertype = $("#fusertype");

        if ($.trim(loginacct.val())==""){
            // $("#formTip").text("用户名不能为空，请重新输入！");
            layer.msg("用户名不能为空，请重新输入！",{icon:2,shift:6},function () {
                loginacct.val("");
                loginacct.focus();
            });
            return false;
        }

        if ($.trim(userpswd.val())==""){
            // $("#formTip").text("密码不能为空，请重新输入！");
            layer.msg("密码不能为空，请重新输入！",{icon:2,shift:6},function () {
                userpswd.val("");
                userpswd.focus();
            });
            return false;
        }
        var loadingIndex;
        $.ajax({
            url:"${APP_PATH}/doLogin.do",
            data:{
                "loginacct":loginacct.val(),
                "userpswd":userpswd.val(),
                "usertype":usertype.val()
            },
            type:"post",
            beforeSend:function () {
                loadingIndex = layer.msg('登录中', {icon: 16});
              //一般用于表单验证
              return true;
            },
            success:function (result) {
                layer.close(loadingIndex);
                if (result.code==100) {
                    window.location.href="${APP_PATH}/main.htm";
                }else {
                    // $("#formTip").text("用户名或密码错误！");
                    layer.msg("用户名或密码错误！",{icon:5,shift:6});
                }
            },
            error:function () {
              // alert("error");
                layer.msg("请求错误！",{icon:0,shift:6});
            }
        });
    }
</script>
</body>
</html>