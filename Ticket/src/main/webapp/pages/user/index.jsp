<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ticket</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/index.css">
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="islogin.jsp"></jsp:include>
<jsp:include page="header.jsp"></jsp:include>
<div>
    <div id="location" class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            地点
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li><a href="#">北京</a></li>
            <li><a href="#">上海</a></li>
            <li><a href="#">南京</a></li>
        </ul>
    </div>

    <div id="search">
        <form class="bs-example bs-example-form" role="form">
            <div class="col-lg-6">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="请输入演出、艺人、场馆名称...">
                    <span class="input-group-btn">
              <button class="btn btn-default" type="button">
                搜索
              </button>
            </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
        </form>
    </div>
</div>

<div>
    <div id="sort-nav">
        <ul class="nav nav-pills nav-stacked" >
            <li role="presentation"><a href="planlist?type=演唱会" class="sort-link">演唱会</a></li>
            <li role="presentation"><a href="planlist?type=音乐会" class="sort-link">音乐会</a></li>
            <li role="presentation"><a href="planlist?type=曲苑杂坛" class="sort-link">曲苑杂谈</a></li>
            <li role="presentation"><a href="planlist?type=话剧歌剧" class="sort-link">话剧歌剧</a></li>
            <li role="presentation"><a href="planlist?type=体育比赛" class="sort-link">体育比赛</a></li>
            <li role="presentation"><a href="planlist?type=舞蹈芭蕾" class="sort-link">舞蹈芭蕾</a></li>
            <li role="presentation"><a href="planlist?type=度假休闲" class="sort-link">度假休闲</a></li>
            <li role="presentation"><a href="planlist?type=儿童亲子" class="sort-link">儿童亲子</a></li>
            <li role="presentation"><a href="planlist?type=动漫" class="sort-link">动漫</a></li>
        </ul>
    </div>

    <div id="myCarousel" class="carousel slide">
        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner">
            <div class="item active">
                <img src="img/1.png" alt="First slide">
                <div class="carousel-caption">至乐汇全国巡演</div>
            </div>
            <div class="item">
                <img src="img/2.png" alt="Second slide">
                <div class="carousel-caption">Tomorrowland音乐节</div>
            </div>
            <div class="item">
                <img src="img/3.png" alt="Third slide">
                <div class="carousel-caption">快乐农场大冒险</div>
            </div>
        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="carousel-control left" href="#myCarousel"
           data-slide="prev">&lsaquo;
        </a>
        <a class="carousel-control right" href="#myCarousel"
           data-slide="next">&rsaquo;
        </a>
    </div>
</div>

</body>
</html>
