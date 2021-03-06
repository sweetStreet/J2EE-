<%@ page import="cn.yuki.entity.User" %><%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/3
  Time: 下午10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets——在线选座</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="css/jquery.seat-charts.css" >
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/seat.css" >
    <link rel='stylesheet' href='css/nprogress.css'/>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
    <script src='js/nprogress.js'></script>
</head>

<body>
<jsp:include page="islogin.jsp"></jsp:include>
<jsp:include page="header.jsp"></jsp:include>

<div class="demo">
    <div id="seat-map">
        <div class="front">舞台</div>
    </div>

    <div class="booking-details">
        <p><span>${plan.name}</span></p>
        <p>时间：<span>${plan.startTime}</span></p>
        <p>座位：</p>
        <ul id="selected-seats"></ul>
        <p>票数：<span id="counter">0</span></p>
        <p>总计：<b>￥<span id="total">0</span></b></p>
        <p>优惠<input type="radio"/>${user.integration}</p>
        <%--<p>优惠<input type="radio"/><%=((User)request.getSession().getAttribute("user")).getIntegration()%></p>--%>
        <button class="checkout-button" onclick="makeorder()">确定购买</button>
        <div id="legend"></div>
    </div>

</div>
<script type="text/javascript">

    $(document).ready(function() {
        NProgress.start();
        NProgress.inc(0.1);
        NProgress.configure({ trickleSpeed: 2000 });
        NProgress.configure({ easing: 'ease', speed: 500 });
        NProgress.done();

        var $cart = $('#selected-seats'), //座位区
            $counter = $('#counter'), //票数
            $total = $('#total'); //总计金额

        var sc = $('#seat-map').seatCharts({

            map: '${plan.seatmap}'.split(","),

            seats: ${plan.getPriceList()},
            legend : { //定义图例
                node : $('#legend'),
                items : ${plan.getLegned()},
            },

            click: function() { //点击事件
                if (this.status() == 'available') { //可选座
                    if(sc.find('selected').length<6) {
                        $('<li>' + (this.settings.row + 1) + '排' + this.settings.label + '座</li>')
                            .attr('id', 'cart-item-' + this.settings.id)
                            .data('seatId', this.settings.id)
                            .appendTo($cart);
                        $counter.text(sc.find('selected').length + 1);
                        $total.text((recalculateTotal(sc) + parseFloat(this.data().price)).toFixed(2));
                        return 'selected';
                    }else{
                        alert("本场次最多选择6个座位");
                        return 'available';
                    }
                } else if (this.status() == 'selected') { //已选中
                                                          //更新数量
                    $counter.text(sc.find('selected').length-1);
                    //更新总计
                    $total.text((recalculateTotal(sc)-parseFloat(this.data().price)).toFixed(2));
                    //删除已预订座位
                    $('#cart-item-'+this.settings.id).remove();
                    //可选座
                    return 'available';
                } else if (this.status() == 'unavailable') { //已售出
                    return 'unavailable';
                } else {
                    return this.style();
                }
            }
        });

//        //已售出的座位
//        sc.get(['1_2']).status('unavailable');

        $('input:radio').click(function(){
            var domName = $(this).attr('name');

            var $radio = $(this);
            // if this was previously checked
            if ($radio.data('waschecked') == true){
                $total.text(recalculateTotal(sc).toFixed(2));
                $radio.prop('checked', false);
                $("input:radio[name='" + domName + "']").data('waschecked',false);
                $radio.data('waschecked', false);
            } else {
                $radio.prop('checked', true);
                $("input:radio[name='" + domName + "']").data('waschecked',false);
                $radio.data('waschecked', true);
//                $total.text((recalculateTotal(sc)-$('input:radio').val()).toFixed(2));
                $total.text((recalculateTotal(sc)-(parseFloat(${user.integration})/1000).toFixed(2)).toFixed(2));
            }
        });
        //定时刷新
        setInterval(function() {
        $.ajax({
            type     : 'get',
            url      : 'unavailable',
            data     : {
               "planId" : ${plan.id}
            },
            success  : function(response) {
                if(response!=null && response!="") {
                    sc.get(response.split(",")).status('unavailable');
                }
            }
        });
        }, 2000); //every 2 seconds


    });

    //计算总金额
    function recalculateTotal(sc) {
        var total = 0;
        sc.find('selected').each(function () {
            total += parseFloat(this.data().price) ;
        });
        return total;
    }

        //点击确认按钮
        function makeorder() {
            var lis = $("#selected-seats li");
            var seatID = new Array();
            for(var i=0;i<lis.length;i++){
                seatID[i] = $(lis[i]).data("seatId");
            }
            var counter = $("#counter").html();
            var total = $("#total").html();
            var actualPay = parseFloat(total)*${user.getVIP().getDiscount()};

            if(lis.length<=0){
                alert("您尚未选座");
                return;
            }

            var integration = 0;
            if ($('input:radio').prop('checked') == true){
                integration = (parseFloat(${user.integration})/1000.0).toFixed(2);
            }
            $.ajax({
                type: 'POST',
                url: "makeorder",
                data: {
                    //这里total是没有优惠打过折的
                    "total":parseFloat(total)+parseFloat(integration),
                    "counter":counter,
                    "seatID":seatID.toString(),
                    "planId":'${plan.id}',
                    "planName":'${plan.name}',
                    "integration":integration,
                    "actualPay":actualPay.toFixed(2)
                },
                success: function(response){
                    if(response>0){
                        var orderId = response;
                        <%--//弹出账号输入框，让用户输入密码。 订单用异步的方式进入队列--%>
                        swal({
                        title: '输入你的密码',
                        text: '会员折扣'+parseInt('${user.getVIP().getDiscount()}'*100)+'折,实际支付'+ actualPay.toFixed(2),
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
                                    "actualPay": actualPay.toFixed(2),
                                    "orderId": orderId
                                },
                                success: function(response){
                                    if(response == "Success"){
                                        alert("支付成功");
                                        location.reload();
                                    }else{
                                        alert(response);
                                        location.reload();
                                    }
                                }
                            });
                        }else{
                            alert("订单已经创建，3分钟后取消，可去我的订单支付");
                            location.reload();
                        }
                        })
                    }else{
                        alert("座位已经被抢，请重新选座");
                        location.reload();
                    }
                }
            });

        }



</script>
</body>
</html>
