<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/11
  Time: 上午9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="houseindex">
                <img alt="Brand" src="img/logo.png"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Hi，${house.name}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="planmake?seatmap='${house.seats}'">发布计划</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="houseorder">订单管理</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="houseplanlist">现场购票</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="housecheckin">检票</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="housefinance">财务统计</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="houseindex">场馆信息</a></li>

                    </ul>
                </li>

                <li><a href="logout">退出</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
