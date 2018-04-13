<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/20
  Time: 下午1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Tickets——我的订单</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/order.css">
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
</head>


<body>
    <jsp:include page="islogin.jsp"></jsp:include>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">我的订单</div>
        <div class="panel-body">
        </div>

        <div class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                请选择订单类型
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                <li><a href="order?state=0">待支付</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="order?state=1">已完成</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="order?state=2">已取消</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="order?state=-1">已退款</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="order">所有</a></li>
            </ul>
        </div>

        <!-- Table -->
        <table class="table">
            <tr>
                <th>项目名称</th>
                <th>座位</th>
                <th>优惠</th>
                <th>订单金额</th>
                <th>交易状态</th>
                <th>操作</th>
            </tr>
        </table>

    <c:forEach items="${orderList}" var="node">
        <table width="100%" border="0" class="table">
            <thead>
            <tr>
                <th colspan="7">
                    <span>订单号：${node.id}</span>
                    <span>成交时间：${node.createTime}</span>
                </th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td>
                    <div>
                        <a href="plandetail?id=${node.planID}" target="_blank">${node.planName}</a></div>
                </td>
                <td >${node.seats}</td>
                <td ><p>${node.activity}</p></td>
                <%--<td>1</td>--%>
                <td>
                    ${node.payment}
                </td>
                <td>
                    <span class="state-to-string" state=${node.state} overdueTime=${node.getOverdue()}>
                    </span>
                </td>
                <td>
                    <button class="btn btn-default btn-pay" state=${node.state} onclick="payOrder(${node.payment},${node.id})">支付</button>
                    <button class="btn btn-default btn-cancle" state=${node.state} onclick="cancleOrder(${node.id})">退票</button>
                    <button class="btn btn-danger btn-delete" state=${node.state} onclick="deleteOrder()">删除</button>
                </td>
            </tr>
            </tbody>
        </table>
    </c:forEach>
    </div>

    <script>
        $(document).ready(function() {
            $('.btn-pay').each(function () {
                if($(this).attr('state') != 0){
                    $(this).hide();
                }
            })
            $('.btn-cancle').each(function () {
                if($(this).attr('state') != 1 ){
                    $(this).hide();
                }
            })
            $('.btn-delete').each(function () {
                if($(this).attr('state') != 2 && $(this).attr('state') != 3){
                    $(this).hide();
                }
            })
            $('.state-to-string').each(function () {
                if($(this).attr('state') == 0){
                    $(this).html("未支付");
                }else if($(this).attr('state') == 1){
                    $(this).html("已支付");
                }else if($(this).attr('state') == 2){
                    $(this).html("已关闭");
                }else if($(this).attr('state') == -1){
                    $(this).html("已退款");
                }else if($(this).attr('state') == 3){
                    $(this).html("已检票");
                }
            })

            setInterval("timer()",1000);
        });

        function timer(){
            $('.state-to-string').each(function () {
                var overdueTime = $(this).attr('overdueTime');
                var leftTime = overdueTime - new Date().getTime();
                var minute = parseInt(leftTime / 1000 / 60 % 60, 10);
                var second = parseInt(leftTime / 1000 % 60, 10);
                var min = checkTime(minute);
                var sec = checkTime(second);
                if(leftTime>0) {
                    $(this).html(min + ":" + sec+" 后关闭");
                }else if(leftTime == 0){
                    $(this).html("已关闭");
                }
            });
        };


        function checkTime(i){
            if(i < 10 ){
                i = "0" + i;
            }
            return i;
        }

        function payOrder(payment,orderId) {
            <%--//弹出账号输入框，让用户输入密码。 订单用异步的方式进入队列--%>
            swal({
                title: '输入你的密码',
                text: "需支付¥"+payment,
                input: 'password',
                showCancelButton: true,
                closeOnConfirm: false,
                inputAttributes: {
                    'maxlength': 10,
                    'autocapitalize': 'off',
                    'autocorrect': 'off'
                }
            }).then(function (result) {
                if (result.value) {
                    $.ajax({
                        type: 'POST',
                        url: "validatepay",
                        data: {
                            "password":result.value,
                            "actualPay": payment,
                            "orderId": orderId
                        },
                        success: function(response){
                            if(response == "Success"){
//                                alert("支付成功");
                                location.reload();
                            }else{
                                alert(response);
                            }
                        }
                    });
                }
            })
        }
        
        function cancleOrder(orderId) {
            swal({
                title: '你确定退票吗',
                text: '退款规则：演出当天不能退票，演出前一周内退50%，演出前2周内退85%',
                type: 'warning',
                showCancelButton: true,
                confirmButtonText: '是的!',
                cancelButtonText: '取消',
            }).then(function(isConfirm) {
                if (isConfirm.value === true) {

                        $.ajax({
                            type: 'POST',
                            url: "cancleOrder",
                            data: {
                                "orderId": orderId
                            },
                            success: function(response){
                                if(response == "Success"){
                                    alert("退款成功");
                                    location.reload();
                                }else{
                                    if(response == "TimeConflict") {
                                        alert("离演出开始不到一天无法退票");
                                    }else{
                                        alert(response);
                                    }
                                }
                            }
                        });
                }
            });

        }

        function deleteOrder() {

        }
    </script>
</body>
</html>
