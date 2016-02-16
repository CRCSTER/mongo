<%@page import="crc.Check200"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta charset="UTF-8">
  <title>CRC的日常</title>
  <!-- 新 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="grafront/css/bootstrap.min.css">
  <link rel="stylesheet" href="grafront/css/model.css">
  <style>
    .file-btn {
      display: block;
      margin-top: 20px;
    }
    .haha button{
		float: left;
		margin-left :6px;    
    }
  </style>
</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <div class="nav-collapse collapse" style="font-size: 1.5em; color: #fff; margin-top: 10px;">
        	CRC的日常
        </div><!--/.nav-collapse -->
      </div>
    </div>
  </div>
  <!-- END nav -->

  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span2">
      </div><!--/span-->

      <div class="span10" >
        <form id="fileUpload" action="/graduation/upload" method="post" enctype="multipart/form-data">
          <input name="upload" type="file"  value="选择文件"/>
           <div class="haha">
          <button type="submit" class="file-btn btn btn-primary">提交</button>
         
          <button type="button" id="empty" class="file-btn btn btn-primary">清空</button>
          <button type="button" id="jump" class="file-btn btn btn-primary">详细数据</button>
          
          
          
          </div>
        </form>

        <table class="table table-striped table-hover">
          <thead>
            <tr>
              <td>需要分析的数据</td>
              <td>返回数据</td>
            </tr>
          </thead>

		<%
			Check200 c200 = new Check200();
		 %>

          <tbody>
            <tr>
              <td><a href="#">请求总数</a></td>
              <td class="clear"><a href="#"><%= c200.reqCount() %></a></td>
            </tr>
            <tr>
              <td><a href="#">常见IP</a></td>
              <td class="clear"><a href="#"><%= c200.maxIp() %></a></td>
            </tr>
            <tr>
              <td><a href="#">post数量</a></td>
              <td class="clear"><a href="#"><%= c200.postCount() %></a></td>
            </tr>
            <tr>
              <td><a href="#">200状态返回数</a></td>
              <td class="clear"><a href="#"><%= c200.get200Count() %></a></td>
            </tr>
			<!--	
            <tr>
              <td><a href="#"></a></td>
              <td><a href="#"></a></td>
            </tr>
			-->
          </tbody>
        </table> 
        <!-- END table -->
      </div>
      <!-- END span10 -->
    </div><!--/row-->
  </div> 
  </body>
  <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->

<script src="grafront/js/jquery.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="grafront/js/bootstrap.min.js"></script>
<!--<script src="grafront/js/underscore.min.js"></script>
<script src="grafront/js/backbone.min.js"></script>-->
<script type="text/javascript">
	$("#empty").click(function(){
		$(".clear a").empty();
	});
	$("#jump").click(function(){
		 window.location="index.html";
	});
</script>
  
</html>
