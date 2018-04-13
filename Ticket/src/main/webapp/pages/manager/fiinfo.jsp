<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/15
  Time: 下午10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets——财务信息</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/planlist.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/echarts/4.0.4/echarts-en.common.js"></script>
</head>
<body>
<jsp:include page="../user/islogin.jsp"></jsp:include>
<jsp:include page="../user/header.jsp"></jsp:include>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="house" style="width: 600px;height:400px;"></div>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="user" style="width: 600px;height:400px;"></div>

<div></div>

<script type="text/javascript">
    $(document).ready(function() {
        // 基于准备好的dom，初始化echarts实例
        var houseChart = echarts.init(document.getElementById('house'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '场馆统计信息'
            },
            tooltip: {},
            legend: {
                data:['收益']
            },
            xAxis: {
                data: ${statistic}.houseXAxis.split(",")
            },
            yAxis: {},
            series: [{
                name: '收益',
                type: 'bar',
                data: ${statistic}.houseYAxis.split(",")
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        houseChart.setOption(option);

        // 基于准备好的dom，初始化echarts实例
        var userChart = echarts.init(document.getElementById('user'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '用户统计信息'
            },
            tooltip: {},
            legend: {
                data:['消费']
            },
            xAxis: {
                data: ${statistic}.userXAxis.split(",")
            },
            yAxis: {},
            series: [{
                name: '消费',
                type: 'bar',
                data: ${statistic}.userYAxis.split(",")
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        userChart.setOption(option);
    });
</script>


</body>
</html>
