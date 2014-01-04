<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登陆</title>
<style>
	*{margin:0;padding: 0;}
      .loginBox{width:420px;height:230px;padding:0 20px;border:1px solid #fff; color:#000; margin-top:40px; border-radius:8px;background: white;box-shadow:0 0 15px #222; background: -moz-linear-gradient(top, #fff, #efefef 8%);background: -webkit-gradient(linear, 0 0, 0 100%, from(#f6f6f6), to(#f4f4f4));font:11px/1.5em 'Microsoft YaHei' ;position: absolute;left:50%;top:240px;margin-left:-210px;margin-top:-115px;}
      .loginBox h2{height:30px;font-size:20px;font-weight:normal;}
      .loginBox .left{border-right:1px solid #ccc;height:100%;padding-right: 20px; }
      ul, ol, li {list-style: none;}
.oauth-icon-list{padding: 0 0 0 10px;}
.oauth-icon-list li a {
	display: block;
	width: 42px;
	height: 42px;
	background: url(common/image/reg_outsude_icon.jpg) no-repeat;
}
.oauth-icon-list li {
	float: left;
	margin: 0 10px 10px 0;
}
.oauth-icon-list li.qq a {background-position: 0 0;}
.oauth-icon-list li.qq a:hover {background-position: 0 -60px;}
.oauth-icon-list li.weibo a {background-position: -60px 0;}
.oauth-icon-list li.weibo a:hover {background-position: -60px -60px;}
.oauth-icon-list li.baidu a {background-position: -180px 0;}
.oauth-icon-list li.baidu a:hover {background-position: -180px -60px;}
.oauth-icon-list li.douban a {background-position: -540px 0;}
.oauth-icon-list li.douban a:hover {background-position: -540px -60px;}
</style>
</head>
<body>
<%@ include file="top.jsp"%>
<div class="container">
	<c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message  ne null}">
		<div style="position: absolute;width:450px;top:80px;left:35%;">
			<div class="alert alert-error">
			 	${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }
			</div>
		</div>
	</c:if>
	<form action="${ctx }/j_spring_security_check" name="loginForm" method="post">
        <section class="loginBox row-fluid">
          <section class="span7 left">
            <h2>登录</h2>
            <p><input type="text" name="j_username"  placeholder="请输入用户名"/></p>
            <p><input type="password" name="j_password" placeholder="请输入密码"/></p>
            <input type="hidden" name="spring-security-redirect" value="/login/login.do"/>
            <section class="row-fluid">
              <section class="span8 lh30">
              		<label class="checkbox">
						<input type="checkbox" contenteditable="true">下次自动登录
					</label>
              </section>
          	  <section class="span1"><input type="submit" value=" 登录 " class="btn btn-primary"></section>
            </section>
            <section>
            	<a href="${ctx  }/forgotPassword.jsp">忘记密码</a>
            </section>
          </section>
          <section class="span5 right">
            <h2>没有帐户？</h2>
            <section>
              <div class="oauth-icon-list">
					<ul>
						<li class="qq"><a target="_blank" href="${ctx }/oauth2/authorize.do?type=qq"></a></li>
						<li class="weibo"><a target="_blank" href="${ctx }/oauth2/authorize.do?type=weibo"></a></li>
						<li class="baidu"><a target="_blank" href="${ctx }/oauth2/authorize.do?type=baidu"></a></li>
						<li class="douban"><a target="_blank" href="${ctx }/oauth2/authorize.do?type=douban"></a></li>
					</ul>
				</div>
              <p><input type="button" value=" 注册 " class="btn" onclick="window.location.href='${ctx  }/register.jsp'"></p>
            </section>
          </section>
        </section>
	</form>        
</div>
</body>
<script type="text/javascript">
	$(".pull-right").css("visibility","hidden");
</script>
</html>