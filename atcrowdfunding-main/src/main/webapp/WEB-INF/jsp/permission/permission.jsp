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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 许可树</h3>
                </div>
                <div class="panel-body">
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
        view: {
            selectedMulti: false,
            addDiyDom: function (treeId, treeNode) {
                var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                if (treeNode.icon) {
                    icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background", "");
                }
            },
            addHoverDom: function(treeId, treeNode){
                var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
                aObj.attr("href", "javascript:;");
                if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
                var s = '<span id="btnGroup'+treeNode.tId+'">';
                if ( treeNode.level == 0 ) {
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="window.location.href=\'${APP_PATH}/permission/toAddPage.htm?id='+treeNode.id+'\'" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if ( treeNode.level == 1 ) {
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="window.location.href=\'${APP_PATH}/permission/toUpdatePage.htm?id='+treeNode.id+'\'"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    if (treeNode.children.length == 0) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="doDelete ('+treeNode.id+',\''+treeNode.name+'\');" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="window.location.href=\'${APP_PATH}/permission/toAddPage.htm?id='+treeNode.id+'\'" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                } else if ( treeNode.level == 2 ) {
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="window.location.href=\'${APP_PATH}/permission/toUpdatePage.htm?id='+treeNode.id+'\'"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="doDelete ('+treeNode.id+',\''+treeNode.name+'\');" href="#">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                }

                s += '</span>';
                aObj.after(s);
            },
            removeHoverDom: function(treeId, treeNode){
                $("#btnGroup"+treeNode.tId).remove();
            }

        }
    };

    loadTree();
    function loadTree(){
        var listLoading;
        $.ajax({
            url:"${APP_PATH}/permission/getNodeData.do",
            type:"get",
            beforeSend:function(){
                listLoading = layer.load(2, {time: 10*1000});
            },
            success:function (result) {
                layer.close(listLoading);
                if (result.code==100){
                    $.fn.zTree.init($("#treeDemo"), setting, result.info.zNodes)
                } else {
                    layer.msg(result.message,{icon:0,shift:6});
                }
            },
            error:function () {
                layer.msg("请求错误！",{icon:0,shift:6});
            }
        });
    }

    //删除许可请求
    function doDelete(id,name) {
        var listLoading;
        layer.confirm("确认删除许可【"+name+"】吗？",  {icon: 3, title:'提示'}, function(cindex){
            layer.close(cindex);
            $.ajax({
                url:"${APP_PATH}/permission/"+id+".do",
                data:{
                    "_method":"delete"
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
                        loadTree();
                    } else{
                        layer.msg(result.message,{icon:0,shift:6});
                    }
                },
                error:function () {
                    layer.msg("请求错误！",{icon:0,shift:6});
                }
            });
        }, function(cindex){
            layer.close(cindex);
        });
    }
</script>
</body>
</html>
