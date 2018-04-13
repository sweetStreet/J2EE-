<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/11
  Time: 上午8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
</head>

<script>
    function isValid(form)
    {
        if (form.account.value=="")
        {
            swal({
                title: "邮箱不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }
        else if (form.password.value == "")
        {
            swal({
                title: "密码不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }
        else return true;
    }
</script>

<body>
<div class="demo form-bg" style="padding: 20px 0;">
    <div class="container">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <form class="form-horizontal" action="hlogin" method="post" onsubmit="return isValid(this);">
                    <span class="heading">场馆登录</span>
                    <div class="form-group">
                        <input type="number" class="form-control" name="account" placeholder="7位账号">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group help">
                        <input type="password" class="form-control" name="password" placeholder="密　码">
                        <i class="fa fa-lock"></i>
                        <a href="#" class="fa fa-question-circle"></a>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-default">登录</button>
                        <div>如果您没有账号，请单击<a href="apply">这里</a>申请！</div>
                        <div>用户登录，请单击<a href="login">这里</a>登录！</div>
                        <div>用户注册，请单击<a href="register">这里</a>注册！</div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
