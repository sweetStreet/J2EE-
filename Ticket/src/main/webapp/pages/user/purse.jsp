<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/12
  Time: 下午8:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Tickets——我的钱包</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
</head>
<body>
<jsp:include page="islogin.jsp"></jsp:include>
<jsp:include page="header.jsp"></jsp:include>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion"
               href="#collapseOne">
                ${purse.name}
                点击显示余额
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse">
        <div class="panel-body">
            ${purse.remain}
        </div>
    </div>
</div>


<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion"
               href="#collapseTwo">
                预订 ¥${reserve}
                点击显示详情
            </a>
        </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse">
        <div class="panel-body">

            <table class="table">
                <thead>
                <tr>
                    <th>流水号</th>
                    <th>时间</th>
                    <th>金额</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${reserveDetail}" var="node">
                    <tr>
                        <td>${node.id}</td>
                        <td>${node.date} </td>
                        <td>${-node.money}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>


<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion"
               href="#collapseThree">
                消费 ¥${consume}
                点击显示详情
            </a>
        </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse">
        <div class="panel-body">

            <table class="table">
                <thead>
                <tr>
                    <th>流水号</th>
                    <th>时间</th>
                    <th>金额</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${consumeDetail}" var="node">
                    <tr>
                        <td>${node.id}</td>
                        <td>${node.date} </td>
                        <td>${-node.money}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>


<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion"
               href="#collapseFour">
                退订 ¥${back}
                点击显示详情
            </a>
        </h4>
    </div>
    <div id="collapseFour" class="panel-collapse collapse">
        <div class="panel-body">

            <table class="table">
                <thead>
                <tr>
                    <th>流水号</th>
                    <th>时间</th>
                    <th>金额</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${backDetail}" var="node">
                    <tr>
                        <td>${node.id}</td>
                        <td>${node.date} </td>
                        <td>${node.money}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>
