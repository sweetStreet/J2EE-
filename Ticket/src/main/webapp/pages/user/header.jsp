<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/22
  Time: 下午10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="index">
                <img alt="Brand" src="img/logo.png"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Hi，${user.username}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="profile">个人信息</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="order">订单管理</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="purse">我的钱包</a></li>
                    </ul>
                </li>

                <li class="dropdown" id="manage">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">网站管理<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="houseinfo">场馆信息</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="check">支付结算</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="fiinfo">财务信息</a></li>
                    </ul>
                </li>

                <li><a href="logout">退出</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<script>
    window.onload = function(){
        var isManager = "${user.isManager}";
        if(isManager == 0){
            document.getElementById("manage").style.display= "none"
        }
    }
</script>

