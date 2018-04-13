<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/20
  Time: 下午1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tickets</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <%--<link rel="stylesheet" href="css/profile.css">--%>
    <%--<link rel="stylesheet" href="css/sweetalert.css">--%>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
    <script src="js/profile.js"></script>


</head>


<body>
    <jsp:include page="islogin.jsp"></jsp:include>
    <jsp:include page="header.jsp"></jsp:include>
    <form action="profile" method="post" onsubmit="return isValid(this);" style="margin-left: 20px; margin-right: 70%">
    <dl>
        <dt><label>邮箱:</label></dt>
        <dd><input  class="form-control" type="text" name="email" size="32" maxlength="128" readonly="readonly" value="${user.email}"/></dd>
    </dl>
    <dl>
        <dt><label>密码:</label></dt>
        <dd><input class="form-control" type="password" name="password" size="32" maxlength="32"  onclick="showRePassword()"/></dd>
    </dl>
    <dl id="rePassword">
        <dt><label>再次输入确认:</label></dt>
        <dd><input class="form-control" type="password" name="rePassword" size="32" maxlength="32"/></dd>
    </dl>
    <dl>
        <dt><label>用户名: </label></dt>
        <dd><input class="form-control" type="username" name="username" size="32" maxlength="32" value="${user.username}"/></dd>

    </dl>

        <dl>
            <dt><label>会员等级:</label></dt>
            <dd><input class="form-control" type="level" name="level"  size="32" maxlength="32" disabled="disabled" value="${user.getVIP().level}"/></dd>
        </dl>

        <dl>
            <dt><label>会员积分:</label></dt>
            <dd><input class="form-control" type="level" name="level"  size="32" maxlength="32" disabled="disabled" value="${user.integration}"/></dd>
        </dl>

    <input type="submit" class="btn btn-success" value="保存" />

</form>
    <button type="button" class="btn btn-danger" onclick="stopAccount()" style="margin-left: 20px;">注销会员</button>
</body>
</html>
