<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<title>忘记密码</title>
<script src="${ctx  }/common/js/jquery/jquery.validate.min.js"></script>
<style type="text/css">
	.email-div{margin: 60px auto;text-align: center;}
	.email-tip{margin: 20px 0;font-size: 15px;color: #000;font: 15px "Hiragino Sans GB", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;}
	.email-input{}
	.email-input  input{width: 400px;height: 35px;font-size: 20px;padding-left:15px;}
	.email-btn{margin-top: 20px;}
	.email-btn button{width: 240px;height: 40px;}
	.error{
		float: left;display:none;
		color: #b94a48;
		background-color: #f2dede;
		border-color: #eed3d7;
		padding: 10px 35px 10px 14px;
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
	<div class="view">
		<div class="page-header">
	    	<h1>忘记密码</h1>
	    </div>
	    <form action="" name="pwForm" id="pwForm" method="post">
		    <div class="email-div">
		    	<div class="email-tip">请输入您要找回密码帐号绑定的邮箱</div>
		    	<div class="email-input">
		    		<div class="pull-left" style="margin-left: 260px;"><input type="text" class="form-control" name="email" id="email" placeholder="请输入邮箱"></div>
					<div class="pull-right"><label class="error" for="email"></label></div>
		    		<div class="clearfix"></div>
		    	</div>
		    	<div class="email-btn"><button type="submit" class="btn btn-default" id="submitBtn">找回密码</button></div>
		    </div>
	    </form>
	</div>
</div>
</body>
<script type="text/javascript">
$(function(){
	$("#pwForm").validate({
		rules: {
			email:{
				required:["邮箱" ,true],
				maxlength: 30,
				email: true,
				remote: "${ctx}/login/existEmail.do"
			}
		},
		messages: {
         email:{remote: "该邮箱尚未注册，用户不存在" }
    	},
    	submitHandler: function(form){
    		var btSubmit = document.getElementById("submitBtn");
			btSubmit.disabled="disabled";								
			document.getElementById("pwForm").action="${ctx}/login/emailPassword.do";
			document.getElementById("pwForm").submit();
    	}
	});
});

jQuery.extend(jQuery.validator.messages, {
    required: jQuery.validator.format("{0}不能为空"),
    rangelength:jQuery.validator.format("请输入{0}-{1} 个字符长度"),
    email:jQuery.validator.format("邮箱格式填写不正确"),
    maxlength:jQuery.validator.format("你输入的字符长度不能超过{0}个")
});
</script>
</html>