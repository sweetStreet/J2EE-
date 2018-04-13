<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/10
  Time: 下午8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Ticket</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/sweetalert2.min.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
</head>

<body>
<jsp:include page="../user/islogin.jsp"></jsp:include>
<jsp:include page="../user/header.jsp"></jsp:include>
<%--<div id="search">--%>
    <%--<form class="bs-example bs-example-form" role="form">--%>
        <%--<div class="col-lg-6">--%>
            <%--<div class="input-group">--%>
                <%--<input type="text" class="form-control" placeholder="请输入场馆名称...">--%>
                <%--<span class="input-group-btn">--%>
                  <%--<button class="btn btn-default" type="button">--%>
                    <%--搜索--%>
                  <%--</button>--%>
                <%--</span>--%>
            <%--</div><!-- /input-group -->--%>
        <%--</div><!-- /.col-lg-6 -->--%>
    <%--</form>--%>
<%--</div>--%>
    <div class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            请选择场馆状态
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li><a href="houseinfo?state=0">待审核</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="houseinfo?state=1">已审核</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="houseinfo?state=-1">已拒绝</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="houseinfo">所有</a></li>
        </ul>
    </div>


    <table class = "table">
        <caption>剧场信息</caption>
        <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>地址</th>
                <th>座位</th>
                <th>状态</th>
                <th>审核</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${houseList}" var="node">
            <tr>
                <td>${node.getNo()}</td>
                <td>${node.name}</td>
                <td>${node.location}</td>
                <td>${node.row}排${node.line}列</td>
                <td class="state-to-string" state=${node.isValid}></td>
                <td><button type="button" class="btn btn-success btn-pass" state=${node.isValid} onclick="pass(${node.id},${node.isValid})">通过</button>
                    <button type="button" class="btn btn-danger btn-reject"  state=${node.isValid} onclick="reject(${node.id})">拒绝</button>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>

<script>
    $(document).ready(function() {
        $('.btn-pass').each(function () {
            if($(this).attr('state') == -1 || $(this).attr('state') == 1){
                $(this).hide();
            }
        })

        $('.btn-reject').each(function () {
            if($(this).attr('state') == -1 || $(this).attr('state') == 1){
                $(this).hide();
            }
        })

        $('.state-to-string').each(function () {
            if($(this).attr('state') == 0 || $(this).attr('state') == 2){
                $(this).html("未审核");
            }else if($(this).attr('state') == 1){
                $(this).html("已通过");
            }else if($(this).attr('state') == -1){
                $(this).html("已拒绝");
            }
        })
    });

    function pass(id,state) {
            $.ajax({
                url:"passhouse",
                type: 'POST',
                data: {
                    id:id,
                    state:state
                },
                success: function (data) {
                    location.reload();
                }
            });
    }

    function reject(id) {
            swal({
                title: 'title',
                text: "输入拒绝理由",
                input: "text",
                showCancelButton: true,
                inputPlaceholder: "输入..."
            }).then(function(result){
                if (result.value === "") {
                    swal("输入不能为空");
                }else{
                    $.ajax({
                        url:"rejecthouse",
                        type: 'POST',
                        data: {
                            "id": id,
                            "reason": result.value
                        },
                        success: function (data) {
                            location.reload();
                        }
                    });
                }

            });
    }

</script>

</body>
</html>