<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/22
  Time: 下午10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Ticket——计划列表</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/planlist.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="islogin.jsp"></jsp:include>
<jsp:include page="header.jsp"></jsp:include>

<div id="sort-nav">
    <ul class="nav nav-pills" >
        <li role="presentation"><a href="index" class="sort-link">首页</a></li>
        <li role="presentation"><a href="planlist?type=演唱会" class="sort-link">演唱会</a></li>
        <li role="presentation"><a href="planlist?type=音乐会" class="sort-link">音乐会</a></li>
        <li role="presentation"><a href="planlist?type=曲苑杂坛" class="sort-link">曲苑杂谈</a></li>
        <li role="presentation"><a href="planlist?type=话剧歌剧" class="sort-link">话剧歌剧</a></li>
        <li role="presentation"><a href="planlist?type=体育比赛" class="sort-link">体育比赛</a></li>
        <li role="presentation"><a href="planlist?type=舞蹈芭蕾" class="sort-link">舞蹈芭蕾</a></li>
        <li role="presentation"><a href="planlist?type=度假休闲" class="sort-link">度假休闲</a></li>
        <li role="presentation"><a href="planlist?type=儿童亲子" class="sort-link">儿童亲子</a></li>
        <li role="presentation"><a href="planlist?type=动漫" class="sort-link">动漫</a></li>
        <li role="presentation"><a href="planlist?type=其他" class="sort-link">其他</a></li>
    </ul>
</div>

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
        window.location.href = "http://127.0.0.1:8080/plandetail?id="+id;
        return false
    }

</script>

</body>
</html>
