<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/11
  Time: 上午8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets——场馆信息</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <link rel="stylesheet" type="text/css" href="css/jquery.seat-charts.css" >
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script>
</head>
<body>
<jsp:include page="househeader.jsp"></jsp:include>
<div style="margin-left: 30px">
<div class="page-header">
    <h1>${house.name}</h1>
</div>

<address>
    <strong>地址</strong><br>
    ${house.location}<br>
</address>

<address>
    <strong>管理员邮箱</strong><br>
    <a href="#">${house.email}</a>
</address>

<address>
    <strong>座位图</strong>
    <div id="seat-map">
    </div>
</address>

    <address>
    <button type="button" id="btn-order-in-advance" class="btn btn-primary" data-toggle="modal" data-target="#myModal">修改信息</button>
        <button type="button" class="btn btn-primary" onclick="reviseHousePassword()">修改密码</button>
    </address>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    <div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">场馆信息修改</h4>
    </div>
    <div class="modal-body">
        <div class="form-group">
            名称<input type="text" class="form-control" id="name" name="name" placeholder=${house.name}>
        </div>

        <div class="form-group">
           地点<input type="text" class="form-control" id="location" name="location" placeholder="${house.location}">
        </div>

        <div class="form-group">
            排<input class="form-control" rows="5" id="rows" name="rows" placeholder=${house.row}>
        </div>

        <div class="form-group">
            列<input class="form-control" rows="5" id="lines" name="lines" placeholder=${house.line}>
        </div>

        <div class="form-group">
            管理员邮箱<input type="email" class="form-control" id="email" name="email" placeholder=${house.email}>
        </div>

    </div>
    <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="revise()">确定</button>
    </div>
    </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    </div>


</div>

<script type="text/javascript">

    $(document).ready(function() {
        var sc = $('#seat-map').seatCharts({
            map: "${house.seats}".split(",")
        });
    });


    function reviseHousePassword() {
        swal({
            title: '输入密码',
            input: 'password',
            showCancelButton: true,
            closeOnConfirm: false,
        }).then(function (result) {
            if (result.value) {
                $.ajax({
                    type: 'POST',
                    url: "revisehousepassword",
                    data: {
                        "houseId" : ${house.id},
                        "password":result.value
                    },
                    success: function(response){
                            alert(response);
                    }
                });
            }
        });
    }

    function revise() {
        var name = $("#name").val();
        var location = $("#location").val();
        var row = $("#rows").val();
        var line = $("#lines").val();
        var email = $("#email").val();
        if(name == ""){
            name = $("#name").attr("placeholder");
        }
        if(location == ""){
            location = $("#location").attr("placeholder");
        }
        if(row == ""){
            row = $("#rows").attr("placeholder");
        }
        if(line == ""){
            line = $("#lines").attr("placeholder");
        }
        if(email == ""){
            email = $("#email").attr("placeholder");
        }

        $.ajax({
            type: 'POST',
            url: "revisehouse",
            data:{
                "houseId" : ${house.id},
                "name" : name,
                "location" : location,
                "row" : row,
                "line" : line,
                "email" : email
            },
            success: function(response){
                if(response == "Success"){
                    alert("修改成功，等待管理员审批");
                }else{
                    alert(response);
                }
            }
        });
    }
</script>
</body>
</html>
