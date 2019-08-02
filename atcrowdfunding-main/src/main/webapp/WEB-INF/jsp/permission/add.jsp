<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 许可维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <jsp:include page="/WEB-INF/jsp/common/userInfoHead.jsp"></jsp:include>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="查询">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="${APP_PATH}/main.htm">首页</a></li>
                <li><a href="${APP_PATH}/permission/toPermissionPage.htm">许可树</a></li>
                <li class="active">添加</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form id="addForm" role="form">
                        <div class="form-group">
                            <label for="fname">许可名称</label>
                            <input type="text" class="form-control" id="fname" placeholder="请输入许可名称">
                            <p id="loginacctTip" class="help-block label label-warning"></p>
                        </div>
                        <div class="form-group">
                            <label for="furl">许可URL</label>
                            <input type="text" class="form-control" id="furl" placeholder="请输入许可URL">
                            <p id="emailTip" class="help-block label label-warning"></p>
                        </div>
                        <div class="form-group">
                            <label for="ficon">许可图标</label>
                            <div class="alert alert-danger" role="alert" style="width: 250px; font-size: 18px">
                                <span id="showicon" class=" glyphicon glyphicon-arrow-down " aria-hidden="true">  请选择许可图标</span>
                            </div>
                            <select style="width: 350px" type="radio" class="form-control" id="ficon" placeholder="请选择许可图标">
                                <option>glyphicon glyphicon-th-list</option>
                                <option>glyphicon glyphicon-dashboard</option>
                                <option>glyphicon glyphicon glyphicon-tasks</option>
                                <option>glyphicon glyphicon-user</option>
                                <option>glyphicon glyphicon-king</option>
                                <option>glyphicon glyphicon-lock</option>
                                <option>glyphicon glyphicon-ok</option>
                                <option>glyphicon glyphicon-check</option>
                                <option>glyphicon glyphicon-check</option>
                                <option>glyphicon glyphicon-check</option>
                                <option>glyphicon glyphicon-th-large</option>
                                <option>glyphicon glyphicon-picture</option>
                                <option>glyphicon glyphicon-equalizer</option>
                                <option>glyphicon glyphicon-random</option>
                                <option>glyphicon glyphicon-hdd</option>
                                <option>glyphicon glyphicon-comment</option>
                                <option>glyphicon glyphicon-list</option>
                                <option>glyphicon glyphicon-tags</option>
                                <option>glyphicon glyphicon-list-alt</option>
                            </select>
                            <p id="usernameTip" class="help-block label label-warning"></p>
                        </div>
                        <button type="button" id="addBtn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="button" id="resetBtn" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
    //点击按钮，发送添加请求
    $("#addBtn").click(function () {
        var fname = $("#fname");
        var ficon = $("#ficon");
        var furl = $("#furl");
        var listLoading;
        $.ajax({
            url:"${APP_PATH}/permission.do",
            data:{
                "pid":"${param.id}",
                "name":fname.val(),
                "icon":ficon.val(),
                "url":furl.val()
            },
            type:"post",
            beforeSend:function(){
                listLoading = layer.load(2, {time: 10*1000});
                return true;
            },
            success:function (result) {
                layer.close(listLoading);
                if (result.code==100){
                    layer.msg(result.message, {time:1500, icon:1, shift:6});
                    window.location.href="${APP_PATH}/permission/toPermissionPage.htm";
                } else{
                    layer.msg(result.message,{icon:0,shift:6});
                }
            },
            error:function () {
                layer.msg("请求错误！",{icon:0,shift:6});
            }
        });
    });
    //重置表单
    $("#resetBtn").click(function () {
        $("#addForm")[0].reset();
    });

    $("#ficon").change(function () {
        $("#showicon").removeClass();
        $("#showicon").addClass($(this).val());
    });
</script>
</body>
</html>

