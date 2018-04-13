<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/14
  Time: 下午7:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Tickets——现场购票</title>
    <link rel="stylesheet" type="text/css" href="css/jquery.seat-charts.css" >
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
</head>
<body>
<jsp:include page="househeader.jsp"></jsp:include>
<div class="row" id="planlist" >
    <c:forEach items="${planList}" var="node">
        <div class="col-sm-4 col-md-4">
            <div class="thumbnail" onclick="showDetail(${node.id})">
                <img src="${node.fileLocation}" alt="...">
                <div class="caption">
                    <h3>${node.name}</h3>
                    <p>票价：${node.price}</p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<script>
    function showDetail(id) {
        window.location.href = "http://127.0.0.1:8080/houseplandetail?id="+id;
        return false
    }
</script>
</body>
</html>
