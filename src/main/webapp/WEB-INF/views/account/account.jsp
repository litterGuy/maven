<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<title>帐号信息</title>
<script src="${ctx  }/common/js/jquery/jquery.validate.min.js"></script>
<style type="text/css">
	table{
		margin-left: 40px;
	}
	.table th, .table td {
		border-top: 1px solid #ffffff;
	}
	.font-red{color: red;font-weight: bold;margin-right: 5px;}
	.error{
		display:none;
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
		width: 206px;
	}
</style>
</head>
<body>
<%@ include file="../header.jsp"%>
<div class="container">
	<div class="view">
		<ul class="nav nav-pills">
		  <li class="active"><a href="#">帐号信息</a></li>
		  <li><a href="${ctx }/user/setPhoto.do">修改头像</a></li>
		</ul>
	</div>
	<form name="account" id="account"  action="#"  method="post">
		<table class="table table-hover">
			<tbody>
				<tr>
					<td width="60px" style="text-align: right;">用户名</td>
					<td width="245px">
						${user.loginName  }
						<input type="text"  name="loginName" value="${user.loginName  }"  style="display: none;"/>
					</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right;"><font class="font-red">*</font>昵称</td>
					<td>
						<input class="form-control"  type="text"  value="${user.name }"  id="name"  name="name">
					</td>
					<td><label class="error" for="name"></label></td>
				</tr>
				<tr>
					<td style="text-align: right;">邮箱</td>
					<td>
						${user.email }
						<input type="text"  name="email" value="${user.email  }"  style="display: none;"/>
					</td>
					<td>
						<c:if test="${user.verify == 1}">邮箱未激活</c:if>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">手机号码</td>
					<td><input class="form-control"  type="text"   value="${user.cellPhone }"  id="cellPhone"  name="cellPhone"></td>
					<td><label class="error" for="cellPhone"></label></td>
				</tr>
				<tr>
					<td style="text-align: right;">地址</td>
					<td><input class="form-control"   type="text"  value="${user.address }"  id="address"  name="address"></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="text" value="${user.id }" name="id" style="display: none;"/>
						<input type="submit" class="btn btn-primary" id="subBtn" value="确定"/>
						<span style="margin-left: 20px;"><button type="button" class="btn btn-default"  id="canBtn">取消</button></span>
					</td>
					<td></td>
				</tr>
				<c:if test="${flag ne null }">
					<tr>
						<td colspan="3">
						      <div class="alert alert-success">✔ 修改成功</div>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</form>
</div>
</body>
<script type="text/javascript">
$(function(){
	$("#account").validate({
		rules: {
			name:{
				required:["姓名",true],
				rangelength: [2,12]
			},
			cellPhone:{
				isMobile:true
			}
		},
		messages: {
         loginName:{remote: "该用户名已经被使用" },
         mail:{remote: "该邮箱已经被使用" }
    	},
    	submitHandler: function(form){
    		// 使用 jQuery 异步提交表单
   			$("#subBtn").attr("disabled","disabled");
   			$("#canBtn").attr("disabled","disabled");
   			$.ajax({
				type: 'POST',
				url: "${ctx }/user/modify.do",
				data:$('#account').serialize(),
				async:false,
				cache:false,
				success: function(msg){
					if(msg == 0){
						window.location.href="${ctx }/user/account.do?flag=0";
					}else{
						return false;
					}
				}
			});
    	}
	});
});

jQuery.extend(jQuery.validator.messages, {
	required: jQuery.validator.format("{0}不能为空"),
	rangelength:jQuery.validator.format("请输入{0}-{1} 个字符长度"),
	maxlength:jQuery.validator.format("你输入的字符长度不能超过{0}个")
});
jQuery.validator.addMethod("isMobile", function(value, element) {
   	var length = value.length;
   	return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
	}, "请正确填写您的手机号码");
</script>
</html>