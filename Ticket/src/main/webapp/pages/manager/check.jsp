<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/10
  Time: 下午8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Tickets——支付结算</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
</head>
<body>
<jsp:include page="../user/islogin.jsp"></jsp:include>
<jsp:include page="../user/header.jsp"></jsp:include>
<table class="table">
    <thead>
    <tr>
        <th>计划名称</th>
        <th>场馆名称</th>
        <th>结束时间</th>
        <th>票务总收入</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${planList}" var="node">
    <tr>
        <td>${node.name}</td>
        <td>${node.houseName}</td>
        <td>${node.endTime}</td>
        <td>${node.checkMoney}</td>
        <td class="state" state="${node.money}"></td>
        <td>
            <button class="btn-check" state="${node.money}" onclick="check(${node.id},${node.houseID},${node.checkMoney})">结算</button>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
    <script>
        $(document).ready(function() {
            $('.state').each(function () {
                if($(this).attr('state') == 0){
                    $(this).html("未结算");
                }else{
                    $(this).html("已结算"+$(this).attr('state'))

                }
            });
            $('.btn-check').each(function () {
                if($(this).attr('state') != 0){
                    $(this).hide();
                }
            });
        });

        function check(id, houseID, money) {
            swal({
                title: '输入计入网站收入结算比率',
                text: "当前计划收入¥"+money,
                input: 'number',
                showCancelButton: true,
                closeOnConfirm: false,
                inputAttributes: {
                    'autocapitalize': 'off',
                    'autocorrect': 'off'
                }
            }).then(function (result) {
                //将钱计入网站收入
                if(result.value){
                    if(result.value>1){
                        alert("请输入一个比1小的正数")
                        return;
                    }
                    $.ajax({
                        url:"websiteincome",
                        type: 'POST',
                        data: {
                            "planId":id,
                            "houseId":houseID,
                            "money":money*result.value,
                            "checkmoney":money*(1-result.value)
                        },
                        success: function (data) {
                            location.reload();
                        }
                    });
                }
            });
        }
    </script>
</body>
</html>
