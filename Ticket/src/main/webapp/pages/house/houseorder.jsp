<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/14
  Time: 下午6:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Tickets——订单信息</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
</head>
<body>
    <jsp:include page="househeader.jsp"></jsp:include>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">我的订单</div>
        <div class="panel-body">
        </div>

        <div class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                请选择订单类型
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                <li><a href="houseorder?state=0">待支付</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="houseorder?state=1">已完成</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="houseorder?state=2">已取消</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="houseorder?state=-1">已退款</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="houseorder">所有</a></li>
            </ul>
        </div>

        <!-- Table -->
        <table class="table">
            <tr>
                <th>项目名称</th>
                <th>订单金额</th>
                <th>交易状态</th>
            </tr>
        </table>

        <c:forEach items="${orderList}" var="node">
            <table width="100%" border="0" class="table">
                <thead>
                <tr>
                    <th colspan="7">
                        <span>订单号：${node.id}</span>
                        <span>成交时间：${node.createTime}</span>
                    </th>
                </tr>
                </thead>

                <tbody>
                <tr>
                    <td>
                        <div>
                            <a href="#">${node.planName}</a>
                                <%--<a href="plandetail?id=${node.planID}" target="_blank">${node.houseName}</a>--%>
                        </div>

                    </td>
                    <td>
                            ${node.payment}
                    </td>
                    <td>
                        <span class="state-to-string" state=${node.state}></span>
                    </td>
                </tr>
                </tbody>
            </table>
        </c:forEach>
    </div>

    <script>
        $(document).ready(function() {
            $('.state-to-string').each(function () {
                if($(this).attr('state') == 0){
                    $(this).html("未支付");
                }else if($(this).attr('state') == 1){
                    $(this).html("已支付");
                }else if($(this).attr('state') == 2){
                    $(this).html("已关闭");
                }else if($(this).attr('state') == -1){
                    $(this).html("已退款");
                }else if($(this).attr('state') == 3){
                    $(this).html("已检票");
                }
            })
        });
    </script>
</body>
</html>
