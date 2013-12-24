<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<title>首页</title>
</head>
<body>
<%@ include file="top.jsp"%>
<div class="container">
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
	  <!-- Indicators -->
	  <ol class="carousel-indicators">
	    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
	    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
	    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
	  </ol>
	  <!-- Wrapper for slides -->
	  <div class="carousel-inner">
	    <div class="item active">
	      <img src="${ctx  }/common/image/1.jpg">
	      <div class="carousel-caption">
		    <h3>井儿</h3>
		    <p>瓶子下的画笔</p>
		  </div>
	    </div>
		<div class="item">
	      <img src="${ctx  }/common/image/2.jpg">
	    </div>
	    <div class="item">
	      <img src="${ctx  }/common/image/3.jpg">
	    </div>
	  </div>
	  <!-- Controls -->
	  <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
	    <span class="glyphicon glyphicon-chevron-left"></span>
	  </a>
	  <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
	    <span class="glyphicon glyphicon-chevron-right"></span>
	  </a>
	</div>
</div>
</body>
<script type="text/javascript">   
	$('#carousel-example-generic').carousel('next');  
</script>
</html>