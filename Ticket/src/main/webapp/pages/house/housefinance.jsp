<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/16
  Time: 下午11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets——场馆财务</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/planlist.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/echarts/4.0.4/echarts-en.common.js"></script>
</head>
<body>
<jsp:include page="househeader.jsp"></jsp:include>

<%--<div class="dropdown">--%>
    <%--<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">--%>
        <%--请选择类型--%>
        <%--<span class="caret"></span>--%>
    <%--</button>--%>
    <%--<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">--%>
        <%--<li><a href="housefinance?state=0">预订</a></li>--%>
        <%--<li role="separator" class="divider"></li>--%>
        <%--<li><a href="housefinance?state=1">退订</a></li>--%>
        <%--<li role="separator" class="divider"></li>--%>
        <%--<li><a href="housefinance?state=2">财务</a></li>--%>
        <%--<li role="separator" class="divider"></li>--%>
    <%--</ul>--%>
<%--</div>--%>
<%--</body>--%>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="house" style="width: 600px;height:400px;"></div>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="check" style="width: 600px;height:400px;"></div>

<div id="echartsPie" style="width: 600px;height:400px;"></div>

</body>
<script type="text/javascript">
    $(document).ready(function() {
        // 基于准备好的dom，初始化echarts实例
        var houseChart = echarts.init(document.getElementById('house'));

        // 指定图表的配置项和数据
        var option = {
                title: {
                    text: '各计划收益'
                },
                tooltip: {},
                legend: {
                    data:['收益']
                },
                xAxis: {
                    data: ${statistic}.planXAxis.split(",")
            },
            yAxis: {},
        series: [{
            name: '收益',
            type: 'bar',
            data: ${statistic}.planYAxis.split(",")
        }]
        };

        // 使用刚指定的配置项和数据显示图表。
        houseChart.setOption(option);

        // 基于准备好的dom，初始化echarts实例
        var userChart = echarts.init(document.getElementById('check'));

        // 指定图表的配置项和数据
        var option = {
                title: {
                    text: '网站结算后获得收益'
                },
                tooltip: {},
                legend: {
                    data:['收益']
                },
                xAxis: {
                    data: ${statistic}.checkXAxis.split(",")
            },
            yAxis: {},
        series: [{
            name: '消费',
            type: 'bar',
            data: ${statistic}.checkYAxis.split(",")
    }]
    };

        // 使用刚指定的配置项和数据显示图表。
        userChart.setOption(option);

        option = {
            backgroundColor: '#2c343c',

            title: {
                text: '各类型收入',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#ccc'
                }
            },

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 80,
                max: 600,
                inRange: {
                    colorLightness: [0, 1]
                }
            },
            series : [
                {
                    name:'各收入类型占比',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:(${statistic}).pie.sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#c23531',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };
        var echartsPie = echarts.init(document.getElementById('echartsPie'));
        echartsPie.setOption(option);



    });
</script>

</html>
