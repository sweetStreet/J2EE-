<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/sweetalert.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="js/sweetalert.min.js"></script>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Tickets</title>
</head>

<script>
    function isValid(form)
    {
        if (form.email.value=="")
        {
            swal({
                title: "邮箱不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }
        if (form.password.value!=form.repassword.value)
        {
            swal({
                title: "两次输入的密码不一致",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }
        else  if (form.password.value=="")
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
    <%--<form action="register?action=register" method="post" onsubmit="return isValid(this);">--%>
        <%--<table>--%>
            <%--<tr><td>邮箱: <input type="text" name="email" size="20"/></td></tr>--%>
            <%--<tr><td>密码: <input type="password" name="password" size="20"/></td></tr>--%>
            <%--<tr><td>密码确认: <input type="password" name="repassword" size="20"/></td></tr>--%>
            <%--<tr><td><input type="submit" value="注册" /><input type="reset" value="取消"/></td></tr>--%>
        <%--</table>--%>
    <%--</form>--%>

    <div class="demo form-bg" style="padding: 20px 0;">
        <div class="container">
            <div class="row">
                <div class="col-md-offset-3 col-md-6">
                    <form class="form-horizontal" action="register?action=register" method="post" onsubmit="return isValid(this);">
                        <span class="heading">用户注册</span>
                        <div class="form-group">
                            <input type="email" class="form-control" name="email" placeholder="电子邮件">
                            <i class="fa fa-user"></i>
                        </div>
                        <div class="form-group help">
                            <input type="password" class="form-control" name="password" placeholder="密　码">
                            <i class="fa fa-lock"></i>
                        </div>
                        <div class="form-group help">
                            <input type="password" class="form-control" name="repassword" placeholder="密码确认">
                            <i class="fa fa-lock"></i>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-default">注册</button>
                            <div>如果您已有账号，请单击<a href="login">这里</a>登录！</div>
                            <div>场馆管理，请单击<a href="house">这里</a>登录！</div>
                            <div>场馆申请，请单击<a href="apply">这里</a>注册！</div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div>
        ${text}
    </div>
</body>
</html>