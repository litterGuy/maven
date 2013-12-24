<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<%@ taglib uri="/struts-tags" prefix="struts" %>
<html>
<head>
<title>注册</title>
<script src="${ctx  }/common/js/jquery/jquery.validate.min.js"></script>
<style type="text/css">
	.control-group{
		clear: both;
	}
	.control-label{
		float: left;
		width: 100px;
		padding-top: 5px;
		text-align: right;
	}
	.controls {
	margin-left: 20px;
	width: 240px;float: left;
	}
	.controls input{
		width: 220px;
	}
	.error{
		float: left;display:none;
		color: #b94a48;
		background-color: #f2dede;
		border-color: #eed3d7;
		padding: 5px 35px 5px 14px;
		margin-bottom: 5px;
		text-shadow: 0 1px 0 rgba(255,255,255,0.5);
		border: 1px solid #fbeed5;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
		border-radius: 4px;
		width: 200px;
	}
</style>
</head>
<body>
<%@ include file="top.jsp"%>
<div class="container">
	<div class="container-fluid" id="container">
		<h2>注册</h2>
		<form action=""  name="registerForm"  id="registerForm" method="post">
			<div class="control-group">
				<label class="control-label" for="loginName">登录名</label>
				<div class="controls">
					<input type="text" name="loginName" id="loginName">			
				</div>
				<label class="error" for="loginName"></label>
			</div>
			<div class="control-group">
				<label class="control-label" for="name">昵称</label>
				<div class="controls">
					<input type="text" name="name" id="name">			
				</div>
				<label class="error" for="name"></label>
			</div>
			<div class="control-group">
				<label class="control-label" for="email">邮箱</label>
				<div class="controls">
					<input type="text" name="email" id="email">			
				</div>
				<label class="error" for="email"></label>
			</div>
			<div class="control-group">
				<label class="control-label" for="password">密码</label>
				<div class="controls">
					<input type="password" name="password" id="password">			
				</div>
				<label class="error" for="password"></label>
			</div>
			<div class="control-group">
				<label class="control-label" for="repassword">再次输入密码</label>
				<div class="controls">
					<input type="password" name="repassword" id="repassword">			
				</div>
				<label class="error" for="repassword"></label>
			</div>
		  	<div class="control-group">
				<div class="controls" style="margin-left: 120px;">
					<struts:token/>
				  <button type="submit" class="btn" id="submitBtn">注册</button>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
	$(".pull-right").css("visibility","hidden");
	$(function(){
		$("#registerForm").validate({
			rules: {
				loginName:{
					required: ["登录名",true],
					rangelength: [6,16],
					isLoginName:true,
					remote: "${ctx}/login/validLoginName.do"
				},
				email:{
					required:["邮箱" ,true],
					maxlength: 30,
					email: true,
					remote: "${ctx}/login/validEmail.do"
				},
				name:{
					required:["姓名",true],
					rangelength: [2,12]
				},
				password:
				{
					required: ["密码",true],
					rangelength: [6,16]
				},
				repassword:{
					required: ["确认密码",true],
					equalTo: "#password"
				}
			},
			messages: {
	         loginName:{remote: "该用户名已经被使用" },
	         mail:{remote: "该邮箱已经被使用" }
	    	},
	    	submitHandler: function(form){
	    		var btSubmit = document.getElementById("submitBtn");
				btSubmit.disabled="disabled";								
				document.getElementById("registerForm").action="${ctx}/login/register.do";
				document.getElementById("registerForm").submit();
	    	}
		});
});

jQuery.extend(jQuery.validator.messages, {
    required: jQuery.validator.format("{0}不能为空"),
    rangelength:jQuery.validator.format("请输入{0}-{1} 个字符长度"),
    email:jQuery.validator.format("邮箱格式填写不正确"),
  	minlength:jQuery.validator.format("密码长度不能少于6位数"),
    equalTo:jQuery.validator.format("您输入的两次密码不一致"),
    maxlength:jQuery.validator.format("你输入的字符长度不能超过{0}个")
});
jQuery.validator.addMethod("isLoginName", function(value, element) {
	return this.optional(element) || (/^[A-Za-z0-9_]+$/.test(value));
}, "登录名只能包含数字、字母、_");
</script>
</html>