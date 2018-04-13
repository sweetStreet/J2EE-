<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/3/14
  Time: 下午7:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets——现场购票</title>
    <link rel="stylesheet" type="text/css" href="css/jquery.seat-charts.css" >
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/seat.css" >
    <link rel='stylesheet' href='css/nprogress.css'/>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="js/sweetalert2.min.js"></script>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>--%>
    <script src='js/nprogress.js'></script>
</head>
<body>
<jsp:include page="househeader.jsp"></jsp:include>
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
        <button class="checkout-button" onclick="inputemail()">会员购买</button>
        <button class="checkout-button" onclick="makeorder(0,'',1,0)">非会员购买</button>
        <div id="legend"></div>
    </div>
</div>

<script>
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

    //会员购买
    function inputemail(){
        swal({
            title: '输入邮箱号',
            input: 'text',
            showCancelButton: true,
            closeOnConfirm: false,
            inputAttributes: {
                'autocapitalize': 'off',
                'autocorrect': 'off'
            }
        }).then(function (result) {
            if(result.value){
                $.ajax({
                    type: 'POST',
                    url: 'validateemail',
                    data:{
                        "email":result.value
                    },success: function (response) {
                        if(response.result == 'Success'){
                            makeorder(response.id,response.username,response.discount,response.level);
                        }else{
                            alert('该邮箱还没有注册')
                        }
                    }
                })
            }
        });
    }

    //点击确认按钮
    function makeorder(userId, username, discount, level) {
        var lis = $("#selected-seats li");
        var seatID = new Array();
        for(var i=0;i<lis.length;i++){
            seatID[i] = $(lis[i]).data("seatId");
        }
        var counter = $("#counter").html();
        var total = $("#total").html();
        var actualPay = parseFloat(total);

        if(lis.length<=0){
            alert("您尚未选座");
            return;
        }

        var activity="";
        if(userId!=0){
            activity = "会员"+level+"级，享受"+parseInt(discount*100)+"折优惠"
            alert(activity+"。 最终支付¥"+parseFloat(total)*discount);
        }

        $.ajax({
            type: 'POST',
            url: "orderonsite",
            data: {
                //这里total是没有优惠打过折的
                "totalPrice":total,
                "payment":parseFloat(total)*discount,
                "seats":seatID.toString(),
                "planId":'${plan.id}',
                "planName":'${plan.name}',
                "actualPay":actualPay,
                "userId": userId,
                "username": username,
                "activity": activity
            },
            success: function(response){
                if(response == "Success"){
                    alert("购票成功");
                    location.reload();
                }else{
                    alert(response);
                }
            }
        });
    }

</script>
</body>
</html>
