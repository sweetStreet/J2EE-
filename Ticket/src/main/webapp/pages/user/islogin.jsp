<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/20
  Time: 下午1:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("user") ==null)
    {
%>
<jsp:forward page="login.jsp"></jsp:forward>
<%
    }
%>
