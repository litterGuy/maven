<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<style>
	.navbar .container{
		width:980px;
	}
</style>
<div class="navbar">
  <div class="navbar-inner">
    <div class="container">
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <a class="brand" href="${ctx }/index.jsp">litterGuy</a>
      <div class="nav-collapse">
        <ul class="nav">
			  <li class="active">
			    <a href="${ctx }/index.jsp">首页</a>
			  </li>
			  <li><a href="#">链接</a></li>
			  <li><a href="#">链接</a></li>
			   <li class="dropdown">
				    <a href="#"
				          class="dropdown-toggle"
				          data-toggle="dropdown">
				          帐户
				          <b class="caret"></b>
				    </a>
				    <ul class="dropdown-menu">
				   		<li><a href="#">12</a></li>
				   		<li><a href="#">34</a></li>
				   		<li><a href="#">56</a></li>
				    </ul>
				  </li>
			</ul>
			<ul class="nav pull-right">
	            <li><a href="${ctx  }/login.jsp">登陆</a></li>
	            <li><a href="${ctx  }/register.jsp">注册</a></li>
	         </ul>
      </div>
    </div>
  </div>
</div>