<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/24
  Time: 上午9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets——场馆申请</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/apply.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
</head>

<script>
    function isValid(form)
    {
        if (form.name.value=="") {
            swal({
                title: "名称不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }else if (form.location.value=="") {
            swal({
                title: "地址不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }else if (form.rows.value==""||form.lines.value == "") {
            swal({
                title: "座位情况不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }else if (form.email.value=="") {
            swal({
                title: "管理员邮箱不能为空",
                timer: 2000,
                showConfirmButton: false
            });
            return false;
        }else {
            alert("发送成功，请等待管理员审核");
            swal({
                title: "发送成功，请等待管理员审核",
                timer: 2000,
                showConfirmButton: false
            });
            return true;
        }
    }
</script>

<body>
<div class="demo form-bg" style="padding: 20px 0;">
    <div class="container">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <form class="form-horizontal" action="apply" method="post" onsubmit="return isValid(this)">
                    <span class="heading">场馆申请</span>
                    <div class="form-group">
                        <input type="text" class="form-control" name="name" placeholder="名 称">
                        <i class="fa fa-tag"></i>
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" name="location" placeholder="地 址">
                        <i class="fa fa-map-marker"></i>
                    </div>

                    <div class="form-group">
                        <input class="form-control" rows="5" name="rows" placeholder="排"></input>
                        <i class="fa fa-th"></i>
                    </div>

                    <div class="form-group">
                        <input class="form-control" rows="5" name="lines" placeholder="列"></input>
                        <i class="fa fa-th"></i>
                    </div>

                    <div class="form-group">
                        <input type="email" class="form-control" name="email" placeholder="管理员邮箱">
                        <i class="fa fa-user"></i>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-default">确认</button>
                        <div>如果您已有账号，请单击<a href="house">这里</a>登录！</div>
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
