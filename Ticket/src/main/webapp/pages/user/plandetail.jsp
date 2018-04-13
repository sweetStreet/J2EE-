<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/22
  Time: 下午10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket——计划详情</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/plan.css">
    <link rel="stylesheet" type="text/css" href="css/sweetalert2.min.css" >
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>
    <script src="js/sweetalert2.min.js"></script>

</head>
<body>
<jsp:include page="islogin.jsp"></jsp:include>
<jsp:include page="header.jsp"></jsp:include>
<div>
    <ol class="breadcrumb">
        <%--<li><a href="#"></a></li>--%>
        <li><a href="planlist?type=${plan.type}">${plan.type}</a></li>
        <li class="active">${plan.name}</li>
    </ol>
</div>

<div>
    <div class="col-xs-6 col-md-3" id="poster">
        <a href="#" class="thumbnail">
            <img src="${plan.fileLocation}" alt="...">
        </a>
    </div>

    <div id="description">
        <h2>${plan.name}</h2>
        <h4>${plan.startTime}</h4>
        <h4>票价: ${plan.price}</h4>

        <button type="button" id="btn-order-in-advance" class="btn btn-primary" data-toggle="modal" data-target="#myModal">立即预订</button>
        <a href="seat?planId=${plan.id}" id="btn-order-by-select">在线选座</a>
    </div>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">提前预订，演出开始前两周自动配票</h4>
                </div>
                <div class="modal-body">
                    <div id="radio-price">请选择票价和要购买的数量</div>
                    数量:<input type="number" id="num">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="selectNow()">确定</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

</div>

<script>

    $(document).ready(function () {
        var arrprice = '${plan.price}'.split(",");
        if(arrprice.length==0) {
            var new_obj = $("<label class=\"radio-inline\"><input type=\"radio\" name=\"optradio\" value='${plan.price}'>${plan.price}</label>");
            $("div[id='radio-price']").after(new_obj);
        }else{
            for(var i=0;i<arrprice.length;i++){
                var type = ['a','b','c','d'];
                var new_obj = $("<label class=\"radio-inline\"><input type=\"radio\" name=\"optradio\" value="+arrprice[i]+" seattype="+type[i]+">"+arrprice[i]+"</label>");
                $("div[id='radio-price']").after(new_obj);
            }
        }

        if('${plan.getState()}'==0){
            $('#btn-order-in-advance').hide();
            $('#btn-order-by-select').hide();
        }else if('${plan.getState()}'==1){
            $('#btn-order-in-advance').hide();
        }else if('${plan.getState()}'==2){
            $('#btn-order-by-select').hide();
        }

//        $("input[type='radio']").change(function() {
//            alert($("input[type='radio']:checked").val());
//            alert($("input[type='radio']:checked").attr('seattype'));
//        });
    });

    function selectNow() {
        var num = $("#num").val();
        var unitprice = $("input[type='radio']:checked").val();
        var seattype = $("input[type='radio']:checked").attr('seattype');
        if(num==undefined || num==""){
            alert("请选择数量");
            return;
        }else if(unitprice==undefined){
            alert("请选择单价");
            return;
        }else if(num>20){
            alert("单次最多选择20张");
            return;
        }
        var payment = num*unitprice;
        swal({
            title: '输入你的密码',
            text: '实际支付'+payment,
            input: 'password',
            showCancelButton: true,
            inputAttributes: {
                'maxlength': 10,
                'autocapitalize': 'off',
                'autocorrect': 'off'
            }
        }).then(function (result) {
            if (result.value) {
                $.ajax({
                    type     : 'post',
                    url      : 'book',
                    data     : {
                        "planId" : '${plan.id}',
                        "houseName" : '${plan.name}',
                        "planTime" : '${plan.startTime}',
                        "password" : result.value,
                        "payment" : payment,
                        "num" : num,
                        "type" : seattype
                    },
                    success  : function(response) {
                        alert(response);
                    }
                });
            }
        });


    }

</script>
</body>
</html>
