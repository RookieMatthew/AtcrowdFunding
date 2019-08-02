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
    <link rel="stylesheet" href="${APP_PATH}/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 许可维护</a></div>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 权限分配列表</h3>
                </div>
                <div class="panel-body">
                    <button onclick="assignPermissionForRole(${param.id});" class="btn btn-success">分配许可</button>
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script src="${APP_PATH}/ztree/jquery.ztree.all-3.5.min.js"></script>
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

    var setting = {
        check : {
            enable : true
        },
        view: {
            selectedMulti: false,
            addDiyDom: function(treeId, treeNode){
                var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                if ( treeNode.icon ) {
                    icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background","");
                }
            },
        },
        async: {
            enable: true,
            url:"${APP_PATH}/permission/loadDataAsync.do?roleid=${param.id}",
            autoParam:["id", "name=n", "level=lv"],
            type:"get"
        },
        callback: {
            onClick : function(event, treeId, json) {

            }
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting); //异步访问数据

    function assignPermissionForRole(roleid){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var checkedNodes = treeObj.getCheckedNodes(true);
        if (checkedNodes.length==0){
            layer.msg("请选择要分配给该角色的许可！", {time:1500, icon:0, shift:6});
            return false;
        }
        var ajaxObj = {
            "roleid":roleid
        }
        $.each(checkedNodes,function (i,n) {
            ajaxObj["ids["+i+"]"]=n.id;
        });
        var loadingIndex;
        $.ajax({
            url:"${APP_PATH}/role/assignPermissionForRole.do",
            data:ajaxObj,
            type:"post",
            beforeSend:function(){
                layer.msg('处理中', {icon: 16});
                return true;
            },
            success:function (result) {
                layer.close(loadingIndex);
                $.fn.zTree.init($("#treeDemo"), setting); //异步访问数据
                layer.msg(result.message, {time:1000, icon:6, shift:6});
            },
            error:function (result) {
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        });
    }
</script>
</body>
</html>
