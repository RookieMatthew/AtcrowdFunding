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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
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
                        <button id="selectConditionBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>

                    <button type="button" id="uploadBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-upload"></i> 上传流程定义文件</button>
                    <form method="post" id="uploadForm" enctype="multipart/form-data">
                        <input style="display: none;" id="uploadFile" name="uploadFile" type="file">
                    </form>

                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>流程定义名称</th>
                                <th>流程定义版本</th>
                                <th>流程定义Key</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                 <%----%>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
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
<script src="${APP_PATH }/jquery/jquery-form/jquery-form.min.js"></script>
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

    var jsonDataObj = {
        pn:null,
        selectCondition:null
    }
    //首次加载流程信息
    $(loadUser(1,""));
    //异步加载流程信息
    function loadUser(pn,selectCondition) {
        var listLoading;
        jsonDataObj.pn=pn
        jsonDataObj.selectCondition=selectCondition;
        $.ajax({
            url:"${APP_PATH}/processs.do",
            data:jsonDataObj,
            type:"get",
            beforeSend:function(){
                listLoading = layer.load(2, {time: 10*1000});
            },
            success:function (result) {
                layer.close(listLoading);
                if (result.code==100){
                    parseJsonToUserList(result);
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
    function parseJsonToUserList(result) {
        var ListHtml = "";
        var List = result.info.pageInfo.list;
        $.each(List,function (i,n) {
            ListHtml+='<tr>';
            ListHtml+='<td>'+(i+1)+'</td>';
            ListHtml+='<td>'+n.name+'</td>';
            ListHtml+='<td>'+n.version+'</td>';
            ListHtml+='<td>'+n.key+'</td>';
            ListHtml+='<td>';
            ListHtml+='<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-eye-open"></i></button>';
            ListHtml+=' <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            ListHtml+='</td>';
            ListHtml+='</tr>';
        });
        $("tbody").html(ListHtml);
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

    //点击查询按钮，发送查询请求，局部刷新列表
    $("#selectConditionBtn").click(function () {
        var selectCondition = $("#selectConditionText").val();
        loadUser(1,selectCondition);
    });

    //点击上传按钮，弹出文件选择页
    $("#uploadBtn").click(function () {
        $("#uploadFile").click();
    });
    //当文件上传表单改变时，进行异步提交
    $("#uploadFile").change(function () {
        var options = {
            url:"${APP_PATH}/process.do",
            beforeSubmit : function(){
                loadingIndex = layer.msg('流程定义正在部署中！', {icon: 16});
                return true ; //必须返回true,否则,请求终止.
            },
            success : function(result){
                layer.close(loadingIndex);
                if(result.code==100){
                    layer.msg(result.message, {time:1000, icon:6});
                    $(loadUser(1,""));
                }else{
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            }
        };

        $("#uploadForm").ajaxSubmit(options); //异步提交
        return ;
    });

</script>
</body>
</html>
