<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<title>重置密码</title>
<script src="${ctx  }/common/js/jquery/jquery.validate.min.js"></script>
<style type="text/css">
	.error{
		float: left;display:none;
		color: #b94a48;
		background-color: #f2dede;
		border-color: #eed3d7;
		padding: 4px 35px 4px 14px;
		text-shadow: 0 1px 0 rgba(255,255,255,0.5);
		border: 1px solid #fbeed5;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
		border-radius: 4px;
		width: 260px;
	}
	.control-group{height: 40px;}
	.control-label{font-weight: bold;font-size: 13px;}
	input{width: 260px;height: 35px;margin-right:15px;}
</style>
</head>
<body>
<%@ include file="top.jsp"%>
<div class="container">
	<div class="view">
		<div class="page-header">
	    	<h1>重置密码</h1>
	    </div>
	    
	   <form class="form-horizontal" name="updateForm" id="updateForm" method="post">
		  <div class="control-group">
		    <label class="control-label" for="password">输入密码</label>
		    <div class="controls">
		      <input type="password" id="password" name="password" placeholder="输入密码">
		    </div>
		    <label class="error" for="password"></label>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="passwordAgain">再次输入密码</label>
		    <div class="controls">
		      <input type="password" id="passwordAgain" name="passwordAgain" placeholder="再次输入密码">
		    </div>
		    <label class="error" for="passwordAgain"></label>
		  </div>
		  <div class="control-group">
		    <div class="controls">
		    	<input type="hidden" name="userID" value="${user.id }"/>
		      <button type="submit" class="btn" id="submitBtn">确定</button>
		    </div>
		  </div>
		</form>
		
	</div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$("#updateForm").validate({
		rules: {
			password:
			{
				required: ["密码",true],
				rangelength: [6,16]
			},
			passwordAgain:{
				required: ["确认密码",true],
				equalTo: "#password"
			}
		},
    	submitHandler: function(form){
    		var btSubmit = document.getElementById("submitBtn");
			btSubmit.disabled="disabled";								
			document.getElementById("updateForm").action="${ctx}/login/editPassword.do";
			document.getElementById("updateForm").submit();
    	}
	});
});

jQuery.extend(jQuery.validator.messages, {
    required: jQuery.validator.format("{0}不能为空"),
    rangelength:jQuery.validator.format("请输入{0}-{1} 个字符长度"),
    equalTo:jQuery.validator.format("您输入的两次密码不一致")
});
</script>
</html>