<%--
  Created by IntelliJ IDEA.
  User: liuyu
  Date: 2018/2/24
  Time: 下午4:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tickets——发布计划</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="css/jquery.seat-charts.css">
    <link rel="stylesheet" type="text/css" href="css/minitoggle.css">

    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="js/jquery.tags.min.js"></script>
    <script type="text/javascript" src="js/sweetalert.min.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script>
    <script type="text/javascript" src="js/minitoggle.js"></script>
    <script type="text/javascript" src="js/jquery.seat-charts.min.js"></script>
</head>

    <script>
        $(document).ready(function() {

            $('.toggle').minitoggle();
            $('#toggle1').on("toggle", function(e){
                if (e.isActive)
                    $("#toggle1_price").show();
                else
                    $("#toggle1_price").hide();
            });

            $('#toggle2').on("toggle", function(e){
                if (e.isActive)
                    $("#toggle2_price").show();
                else
                    $("#toggle2_price").hide();
            });

            $('#toggle3').on("toggle", function(e){
                if (e.isActive)
                    $("#toggle3_price").show();
                else
                    $("#toggle3_price").hide();
            });

            $('#toggle4').on("toggle", function(e){
                if (e.isActive)
                    $("#toggle4_price").show();
                else
                    $("#toggle4_price").hide();
            });
        });

        function save() {
            var form = new FormData(document.getElementById("plan-form"));
            $.ajax({
                url:"planmake",
                type:"post",
                data:form,
                processData: false,
                contentType: false,
                success:function(data){
                   alert("成功");
                   location.reload();
                },
                error:function(e){
                    alert("错误！！");
                }
            });
        }
    </script>


<body>
<jsp:include page="househeader.jsp"></jsp:include>
<form id="plan-form" action="planmake" method="post" enctype="multipart/form-data" style="margin-left: 5%; margin-right: 5%;">
    <div class="form-group">
        <input type="text" name="name" id="name" placeholder="名称" class="form-control">
    </div>

    <div class="form-group">
        <input size="16" type="text" placeholder="开始时间" name="startTime" id="startTime" readonly class="form_datetime form-control">
        <input size="16" type="text" placeholder="结束时间" name="endTime" id="endTime" readonly class="form_datetime form-control">
        <script type="text/javascript">
            $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
        </script>
    </div>

    <div>
        <label>类型</label>
        <select class="form-control" name="type" id="type">
            <option>演唱会</option>
            <option>音乐会</option>
            <option>曲苑杂坛</option>
            <option>话剧歌剧</option>
            <option>体育比赛</option>
            <option>舞蹈芭蕾</option>
            <option>度假休闲</option>
            <option>儿童亲子</option>
            <option>动漫</option>
            <option>其他</option>
        </select>
    </div>

    <div class="form-group">
        <label>描述</label>
        <textarea class="form-control" rows="3" id="description" name="description"></textarea>
    </div>

    <div style="display: inline-block; float: left;">
        <div id="seat-map"><label>场馆座位图</label></div>
    </div>

    <div style="display: inline-block; float: left;">
        <label>票价</label>
        <h4><span class="label label-danger">1等座</span></h4><div class="toggle" id="toggle1"></div>
        <div id="toggle1_price" hidden>
            <input type="number" placeholder="价格" name="price1">
            <input type="number" placeholder="数字" name="row1">排
        </div>
        <h4><span class="label label-warning">2等座</span></h4><div class="toggle" id="toggle2"></div>
        <div id="toggle2_price" hidden>
            <input type="number" placeholder="价格" name="price2">
            <input type="number" placeholder="数字" name="row2">排
        </div>
        <h4><span class="label label-primary">3等座</span></h4><div class="toggle" id="toggle3"></div>
        <div id="toggle3_price" hidden>
            <input type="number" placeholder="价格" name="price3">
            <input type="number" placeholder="数字" name="row3">排
        </div>
        <h4><span class="label label-info">4等座</span></h4><div class="toggle" id="toggle4"></div>
        <div id="toggle4_price" hidden>
            <input type="number" placeholder="价格" name="price4">
            <input type="number" placeholder="数字" name="row4">排
        </div>
    <%--<input type="text" data-toggle="tags" name="price" id="price" placeholder="确认输入后按回车" />--%>
    </div>


    <div class="col-sm-6">
    <input type="file" class="file" name="file" id="imgfile" placeholder="请选择文件">
    <img src="" id="showImg" >
    <script>
        $(document).ready(function() {
            var sc = $('#seat-map').seatCharts({
                map: "${seatmap}".split(",")
            });
        })

        var input = document.getElementById("imgfile");
        //检测浏览器是否支持FileReader
        if (typeof (FileReader) === 'undefined') {
            result.innerHTML = "抱歉，你的浏览器不支持 FileReader，请使用现代浏览器操作！";
            input.setAttribute('disabled', 'disabled');
        } else {
            //开启监听
            input.addEventListener('change', readFile, false);
        }
        function readFile() {
            var file = this.files[0];

            //限定上传文件的类型，判断是否是图片类型
            if (!/image\/\w+/.test(file.type)) {
                alert("只能选择图片");
                return false;
            }
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                base64Code=this.result;
                //把得到的base64赋值到img标签显示
                $("#showImg").attr("src",base64Code);
            }
        }
    </script>


    <!--   <input type="text" class="form-control" id="id" />
            <p>value: <span id="value"></span></p>
      <script type="text/javascript">
      $(function(){
        $('#id').tags('initial', 'value');
        $('#value').text($('#id').val());
        setInterval(function () {
          $('#value').text($('#id').val());
        }, 500);
        });
    </script> -->
    <input type="button" class="btn btn-success" value="保存" onclick="save()" />
    </div>
</form>
</body>



