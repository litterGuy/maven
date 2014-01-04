<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<title>litterGuy提示页面</title>
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
				<p>链接有效期已过，请重新发送激活邮件！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
			<c:if test="${type eq 'used'  }">
				<h2>链接失效</h2>
				<p>激活链接仅第一次点击有效，如您操作未成功、请重新发送邮件！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
			<c:if test="${type eq 'activated'  }">
				<h2>激活成功</h2>
				<p>恭喜您验证邮箱激活成功，现在就登录平台、体验一下吧！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
			<c:if test="${type eq 'error'  }">
				<h2>系统错误</h2>
				<p>服务器出错了，这可如何是好啊。您还是重新操作吧！</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
			<c:if test="${type eq 'passwordSuccess'  }">
				<h2>发送邮件成功</h2>
				<p>邮件已经发送到您的邮箱，你登录邮箱重置密码</p>
			</c:if>
			<c:if test="${type eq 'passwordError'  }">
				<h2>发送邮件失败</h2>
				<p>重置密码邮件发送失败，请尝试重新发送</p>
			</c:if>
			<c:if test="${type eq 'editError'  }">
				<h2>密码重置失败，请重新操作</h2>
				<p>系统繁忙，又跑去玩游戏了、重新来一次吧</p>
			</c:if>
			<c:if test="${type eq 'editSuccess'  }">
				<h2>密码重置成功</h2>
				<p>密码已经重置成功，登录一下吧</p>
				<p><a class="btn btn-primary btn-large" href="${ctx  }/login.jsp">登录»</a></p>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>