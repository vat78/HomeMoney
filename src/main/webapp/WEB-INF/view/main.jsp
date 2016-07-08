
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home money</title>

    <link href="/resources/lumino/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/lumino/css/datepicker3.css" rel="stylesheet">
    <link href="/resources/lumino/css/styles.css" rel="stylesheet">
    <link href="/resources/css/hm.css" rel="stylesheet">

    <!--Icons-->
    <script src="/resources/lumino/js/lumino.glyphs.js"></script>

    <!--[if lt IE 9]>
    <script src="/resources/lumino/js/html5shiv.js"></script>
    <script src="/resources/lumino/js/respond.min.js"></script>
    <![endif]-->
    <script src="/resources/lumino/js/jquery-1.11.1.min.js"></script>

</head>

<body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/"><span>Home</span>Money</a>
                <ul class="user-menu">
                    <li class="dropdown pull-right">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg>
                            <sec:authorize access="!isAuthenticated()">User</sec:authorize>
                            <sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username" /></sec:authorize>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#"><svg class="glyph stroked gear"><use xlink:href="#stroked-gear"></use></svg> Settings</a></li>
                            <li><a href="/logout"><svg class="glyph stroked cancel"><use xlink:href="#stroked-cancel"></use></svg> Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div><!-- /.container-fluid -->
    </nav>

    <div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
        <form role="search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search">
            </div>
        </form>
        <tiles:insertAttribute name="sidebar" />

        <div class="panel-body">
            <p align="center">All rights reserved by <a href="http://vat78.ru">vat78</a></p>
            <p align="center">Graphical template: <a href="http://medialoot.com/item/lumino-admin-bootstrap-template/">Lumino</a></p>
        </div>
    </div><!--/.sidebar-->

    <div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
        <div class="row">
            <tiles:insertAttribute name="buttons" />
        </div>
        <tiles:insertAttribute name="content" />
    </div>	<!--/.main-->

    <script src="/resources/lumino/js/bootstrap.min.js"></script>
    <script src="/resources/lumino/js/chart.min.js"></script>
    <script src="/resources/lumino/js/chart-data.js"></script>
    <script src="/resources/lumino/js/easypiechart.js"></script>
    <script src="/resources/lumino/js/easypiechart-data.js"></script>
    <script src="/resources/lumino/js/bootstrap-datepicker.js"></script>
    <script src="/resources/lumino/js/bootstrap-table.js"></script>
    <script>
        $('#calendar').datepicker({
        });

        !function ($) {
            $(document).on("click","ul.nav li.parent > a > span.icon", function(){
                $(this).find('em:first').toggleClass("glyphicon-minus");
            });
            $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
        }(window.jQuery);

        $(window).on('resize', function () {
            if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
        })
        $(window).on('resize', function () {
            if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
        })
    </script>
</body>
</html>
