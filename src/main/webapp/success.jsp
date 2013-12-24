<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<title>
	<c:choose>
		<c:when test="${type eq null }">注册成功</c:when>
		<c:otherwise>激活</c:otherwise>
	</c:choose>
</title>
</head>
<body>
<%@ include file="top.jsp"%>
<div class="container">
	<div class="view">
		<div class="hero-unit" >
			<c:if test="${type eq null }">
				<h2>注册成功</h2>
				<p>激活邮件已经发送到您的邮箱，请登录邮箱激活注册帐号！</p>
				<p><a class="btn btn-primary btn-large" href="#">登录邮箱 »</a></p>
			</c:if>
			<c:if test="${type eq 'expires'  }">
				<h2>激活过期</h2>
				<p>链接有效期已过，请登录账号重新发送激活邮件！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
			<c:if test="${type eq 'used'  }">
				<h2>链接失效</h2>
				<p>激活链接仅第一次点击有效，如您帐号未激活、请重新登录发送！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
			<c:if test="${type eq 'activated'  }">
				<h2>激活成功</h2>
				<p>恭喜您验证邮箱激活成功，现在就登录平台、体验一下吧！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
			<c:if test="${type eq 'error'  }">
				<h2>系统错误</h2>
				<p>服务器出错了，这可如何是好啊。您还是重新登录帐号再次验证邮箱吧！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>