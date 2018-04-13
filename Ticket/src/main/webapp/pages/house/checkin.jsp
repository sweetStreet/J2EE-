<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/19
  Time: 上午11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets——检票</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
</head>
<body>
<jsp:include page="househeader.jsp"></jsp:include>

    <input type="text" class="form-control" id="orderId" name="orderId" placeholder="请输入订单号">

    <button onclick="checkin()" style="margin-top: 50px">确定</button>

</body>

<script>
    function checkin() {
        var orderId = $("#orderId").val();
        if (orderId == "") {
            alert("不能为空");
        } else {

            $.ajax({
                type: 'post',
                url: 'checkin',
                data: {
                    "orderId": orderId
                },
                success: function (response) {
                    if(response == "Invalid") {
                        alert("无效订单号");
                    }else if(response == "HaveChecked"){
                        alert("已检票");
                    }else{
                        alert("检票成功\n"+response);
                    }
                }
            });

        }
    }
</script>
</html>
