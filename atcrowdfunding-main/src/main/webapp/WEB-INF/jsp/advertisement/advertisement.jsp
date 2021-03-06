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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 广告管理</a></div>
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
                <input type="text" class="form-control" placeholder="Search...">
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="selectConditionText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="selectConditionBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" id="batchDeleteBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" onclick="window.location.href='${APP_PATH}/advertisement/toAddPage.htm'" class="btn btn-primary" style="float:right;" onclick="window.location.href='form.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="allCheckbox" type="checkbox"></th>
                                <th>广告描述</th>
                                <th>状态</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%--<tr>
                                <td>1</td>
                                <td>XXXXXXXXXXXX商品广告</td>
                                <td>未审核</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>XXXXXXXXXXXX商品广告</td>
                                <td>已发布</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-eye-open"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>XXXXXXXXXXXX商品广告</td>
                                <td>审核中</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-eye-open"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>--%>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="5" align="center">
                                    <ul class="pagination">
                                       <%----%>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
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

    $("tbody .btn-success").click(function(){
        window.location.href = "assignPermission.html";
    });

    var jsonDataObj = {
        pn:null,
        selectCondition:null
    }
    //首次加载角色信息
    $(loadUser(1,""));
    //异步加载用户信息
    function loadUser(pn,selectCondition) {
        var listLoading;
        jsonDataObj.pn=pn
        jsonDataObj.selectCondition=selectCondition;
        $.ajax({
            url:"${APP_PATH}/advertisements.do",
            data:jsonDataObj,
            type:"get",
            beforeSend:function(){
                listLoading = layer.load(2, {time: 10*1000});
            },
            success:function (result) {
                layer.close(listLoading);
                $("#allCheckbox").prop("checked",false);
                if (result.code==100){
                    parseJsonToRoleList(result);
                    parseJsonToPage(result,selectCondition);
                } else{
                    layer.msg(result.message,{icon:0,shift:6});
                }
            },
            error:function () {
                layer.msg("请求错误！",{icon:0,shift:6});
            }
        });
    }
    //将服务器返回的json数据中的user信息转换为html格式的数据进行显示
    function ststus(ststus) {
        switch (ststus) {
            case '0':
                return "草稿";
            case '1':
                return "未审核";
            case '2':
                return "审核完成";
            case '3':
                return "发布";
            default:
                return null;
        }
    }
    function parseJsonToRoleList(result) {
        var roleListHtml = "";
        var roleList = result.info.pageInfo.list;
        $.each(roleList,function (i,advertisement) {
            roleListHtml+='<tr>';
            roleListHtml+='<td>'+(i+1)+'</td>';
            roleListHtml+='<td><input deleteId='+advertisement.id+' class="aCheckbox" type="checkbox"></td>';
            roleListHtml+='<td>'+advertisement.name+'</td>';
            roleListHtml+='<td>'+ststus(advertisement.status)+'</td>';
            roleListHtml+='<td>';
            roleListHtml+=' <button type="button" onclick="window.location.href=\''+'${APP_PATH}/role/toAssignPermissionPage.htm?id='+advertisement.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            roleListHtml+=' <button type="button" onclick="window.location.href=\''+'${APP_PATH}/advertisement/toUpdatePage.htm?id='+advertisement.id+'\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
            roleListHtml+=' <button type="button" onclick="doDelete('+advertisement.id+',\''+advertisement.name+'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            roleListHtml+='</td>';
            roleListHtml+='</tr>';
        });
        $("tbody").html(roleListHtml);
    }
    //将服务器返回的json数据中的分页信息转换为html格式的数据进行显示
    function parseJsonToPage(result,selectCondition) {
        var pageHtml = "";
        var pageInfo = result.info.pageInfo;
        if(pageInfo.hasPreviousPage){
            pageHtml+='<li><a href="javascript:loadUser(1,\''+selectCondition+'\')">首页</a></li>';
            pageHtml+='<li><a href="javascript:loadUser('+(pageInfo.pageNum-1)+',\''+selectCondition+'\')">上一页</a></li>';
        }else{
            pageHtml+='<li class="disabled"><a href="#">首页</a></li>';
            pageHtml+='<li class="disabled"><a href="#">上一页</a></li>';
        }
        $.each(pageInfo.navigatepageNums,function (i,pn) {
            if(pageInfo.pageNum==pn){
                pageHtml+='<li class="active"><a href="javascript:loadUser('+pn+',\''+selectCondition+'\')">'+pn+'</a></li>';
            }else{
                pageHtml+='<li><a href="javascript:loadUser('+pn+',\''+selectCondition+'\')">'+pn+'</a></li>';
            }
        });
        if(pageInfo.hasNextPage){
            pageHtml+='<li><a href="javascript:loadUser('+(pageInfo.pageNum+1)+',\''+selectCondition+'\')">下一页</a></li>';
            pageHtml+='<li><a href="javascript:loadUser('+(pageInfo.pages)+',\''+selectCondition+'\')">末页</a></li>';
        } else{
            pageHtml+='<li class="disabled" ><a href="#">下一页</a></li>';
            pageHtml+='<li class="disabled" ><a href="#">末页</a></li>';
        }
        $(".pagination").html(pageHtml);
    }

    //点击查询按钮，发送查询请求，局部刷新列表（模糊查询）
    $("#selectConditionBtn").click(function () {
        var selectCondition = $("#selectConditionText").val();
        loadUser(1,selectCondition);
    });

    //删除角色请求
    function doDelete(id,name) {
        var listLoading;
        layer.confirm("确认删除广告【"+name+"】吗？",  {icon: 3, title:'提示'}, function(cindex){
            layer.close(cindex);
            $.ajax({
                url:"${APP_PATH}/advertisement/"+id+".do",
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
                        window.location.href="${APP_PATH}/advertisement/toAdvertisementPage.htm";
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
    //为复选框添加全选全不选事件
    $("#allCheckbox").click(function () {
        $("tbody tr td input[type=checkbox]").prop("checked",$(this).prop("checked"));
    });
    //全部选中时最上边的也应该被选中
    $(document).on("click",".aCheckbox",function () {
        var flag = $(".aCheckbox:checked").length==$(".aCheckbox").length;
        $("#allCheckbox").prop("checked",flag);
    });
    //点击删除按钮进行批量删除
    $("#batchDeleteBtn").click(function () {
        if ($(".aCheckbox:checked").length==0){
            layer.msg("请选择要删除的广告！", {time:1500, icon:0, shift:6});
            return false;
        }

        var listLoading;
        var jsonData = {
            "_method":"delete"
        }
        $.each($(".aCheckbox:checked"),function (i,advertisement) {
            jsonData["ids["+i+"]"]=$(advertisement).attr("deleteId")
        });
        layer.confirm("确认删除这些广告吗？",  {icon: 3, title:'提示'}, function(cindex){
            layer.close(cindex);
            $.ajax({
                url:"${APP_PATH}/advertisements.do",
                data:jsonData,
                type:"post",
                beforeSend:function(){
                    listLoading = layer.load(2, {time: 10*1000});
                    return true;
                },
                success:function (result) {
                    layer.close(listLoading);
                    if (result.code==100){
                        layer.msg(result.message, {time:1500, icon:1, shift:6});
                        window.location.href="${APP_PATH}/advertisement/toAdvertisementPage.htm";
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
    });
</script>
</body>
</html>

