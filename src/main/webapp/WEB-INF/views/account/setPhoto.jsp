<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<title>修改头像</title>
<script type="text/javascript" src="${ctx }/common/js/jquery/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${ctx }/common/js/jquery/jquery.form.min.js"></script>
<link rel="stylesheet" href="${ctx  }/common/css/jquery.Jcrop.min.css">
<style type="text/css">
#preview-pane {
  display: block;
  position: absolute;
  z-index: 2000;
  padding: 6px;
  border: 1px rgba(0,0,0,.4) solid;
  background-color: white;
  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
  -webkit-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
}
#preview-pane .preview-container {
  width: 170px;
  height: 170px;
  overflow: hidden;
}
.target-container{
	width: 300px;height: 300px;border: 1px rgba(0,0,0,.4) solid;
	-webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;
  -webkit-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  vertical-align:middle;display:table-cell;
  text-align:center;
}
#hideFile{display: none;}
.prew-add{top:130px;right:560px;}
.prew-orign{top: 10px;right: -280px;}
</style>
</head>
<body>
<%@ include file="../header.jsp"%>
<div class="container">
	<div class="view">
		<ul class="nav nav-pills">
		  <li><a href="${ctx  }/user/account.do">帐号信息</a></li>
		  <li class="active"><a href="#">修改头像</a></li>
		</ul>
	</div>
	<!-- 图片裁剪区 -->
	<div>
		<div class="target-container">
			<img src="" id="target" alt="请上传图片"/>
		</div>
		<div id="preview-pane">
		  <div class="preview-container">
		  		<c:choose>
		  			<c:when test="${user.picture ne null }"><img src="${ctx  }/${user.picture }" class="jcrop-preview" alt="头像预览" /></c:when>
		  			<c:otherwise><img src="" class="jcrop-preview" alt="头像预览" /></c:otherwise>
		  		</c:choose>
		  </div>
		</div>
	</div>
	<div style="margin-top: 20px;">
		<button type="button" id="uploadBtn" class="btn btn-success">本地上传</button>
		<span style="margin-left: 300px;">
			<input type="text" id="xPoint" value="" style="display: none;"/>
			<input type="text" id="yPoint" value="" style="display: none;"/>
			<input type="text" id="destWidth" value="" style="display: none;"/>
			<input type="text" id="destHeight" value="" style="display: none;"/>
			<input type="text" id="finalWidth" value="" style="display: none;"/>
			<input type="text" id="finalHeight" value="" style="display: none;"/>
			<input type="text" id="saveFlag" value="" style="display: none;"/>
			<button type="button" id="saveBtn" class="btn btn-default">保存</button>
		</span>
		<form action="" id="picForm" method="post" enctype="multipart/form-data">
			<input type="file"  id="hideFile" name="hideFile" onchange="changeSrc();" value="">
			<input type="text" name="fileName" id="fileName" style="display: none;"/>
			<input type="text" name="tempURL" id="tempURL"style="display: none;"/>
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
//关闭页面时删除缓存在服务器的图片
$(window).unload(function(){
	var temp = $("#tempURL").val();
	if($("#saveFlag").val()=="" || $("#saveFlag").val() == "undefined" ){
		$.get("${ctx }/user/delTemp.do?tempURL="+temp);
	}
});
$(function(){
	if($("#target").attr("src")=="" || $("#target").attr("src") == "undefined"){
		$("#preview-pane").addClass("prew-add");
	}
	//绑定点击事件
	$("#uploadBtn").bind("click",function(){
		$("#hideFile").click();
	});
    // Grab some information about the preview pane
    $preview = $('#preview-pane'),
    $pcnt = $('#preview-pane .preview-container'),
    $pimg = $('#preview-pane .preview-container img'),
    xsize = $pcnt.width(),
    ysize = $pcnt.height();
    //保存事件
    $("#saveBtn").bind("click",function(){
    	$("#saveFlag").val(1);
    	
    	var x = $("#xPoint").val();
    	var y = $("#yPoint").val();
    	var tempURL = $("#tempURL").val();
    	var destWidth = $("#destWidth").val();
    	var destHeight = $("#destHeight").val();
    	var finalWidth = $("#finalWidth").val();
    	var finalHeight = $("#finalHeight").val();
    	var paramer = "x="+x+"&y="+y+"&tempURL="+tempURL+"&destWidth="+destWidth
    		+"&destHeight="+destHeight+"&finalWidth="+finalWidth+"&finalHeight="+finalHeight;
    	window.location.href="${ctx }/user/savePhoto.do?"+paramer;
    });
});
	var target_x=0 , target_y=0;
	// Create variables (in this scope) to hold the API and image size
	var boundx,boundy;
	//图片更换事件
	function changeSrc(){
		$("#fileName").val($("#hideFile").val());
		var options = {  
                url :"${ctx }/user/preUpload.do",//跳转到相应的Action  
                type : "POST",//提交方式  
                async: false,//同步 async设置为false就可以（默认是true）
                success : function(msg) {//调用Action后返回过来的数据
                	//将url放进隐藏域，删除使用
                	$("#tempURL").val(msg);
                	$(".prew-add").addClass("prew-orign").removeClass("prew-add");
                	//清除上次的图片值
                	$(".jcrop-preview").attr("src","${ctx }/"+msg);
                	$('.target-container').empty();  
                	$('.target-container').append("<img src='' id='target' alt='请上传图片'/>");  
                	$("#target").attr("src","${ctx }/"+msg);
                	//获取图片的原始大小
                	var img = new Image();
                	img.src = $("#target").attr("src");
                	if(img.complete){
                		target_x = img.width;
                		target_y = img.height;
                		img = null;
                		//初始化图片的大小
                		initPic();
                		initCrop();
                	}else{
                		img.onload = function(){
                			target_x = img.width;
                    		target_y = img.height;
                			img = null;
                			//初始化图片的大小
                    		initPic();
                    		initCrop();
                		};
                	}
                }
            };  
        $("#picForm").ajaxSubmit(options);//绑定页面中form表单的id  
	}
	//初始化图片大小
	function initPic(){
		var width_basic = $(".target-container").width();
		if(parseInt(target_x)>=parseInt(target_y)){
			$("#target").css({"width":width_basic,"height":target_y/(target_x/width_basic)});
		}else{
			$("#target").css({"width":target_x/(target_y/width_basic),"height":width_basic});
		}
	}
	function initCrop(){
		var jcrop_api;
		$('#target').Jcrop({
		      onChange: updatePreview,
		      onSelect: updatePreview,
		      aspectRatio: xsize / ysize
		    },function(){
		      // Use the API to get the real image size
		      var bounds = this.getBounds();
		      boundx = bounds[0];
		      boundy = bounds[1];
		      // Store the API in the jcrop_api variable
		      jcrop_api = this;
		      // Move the preview into the jcrop container for css positioning
		      $preview.appendTo(jcrop_api.ui.holder);
		    });
		//调整预览图片的位置
		var fixedNum_right =parseInt($(".target-container").width()) - parseInt($(".jcrop-holder").width());
		var result_right = parseInt(-280)-fixedNum_right/2;
		$("#preview-pane").css("right",result_right);
		var fixedNum_top =parseInt($(".target-container").height()) - parseInt($(".jcrop-holder").height());
		var result_top = parseInt(10)-fixedNum_top/2;
		$("#preview-pane").css("top",result_top);
	}
    function updatePreview(c)
    {
      if (parseInt(c.w) > 0)
      {
        var rx = xsize / c.w;
        var ry = ysize / c.h;
      //标记x、y值
    	$("#xPoint").val(Math.round(rx * c.x));
    	$("#yPoint").val(Math.round(ry * c.y));
    	$("#destWidth").val(170);
    	$("#destHeight").val(170);
    	$("#finalWidth").val(Math.round(rx * boundx));
    	$("#finalHeight").val(Math.round(ry * boundy));
    	
        $pimg.css({
          width: Math.round(rx * boundx) + 'px',
          height: Math.round(ry * boundy) + 'px',
          marginLeft: '-' + Math.round(rx * c.x) + 'px',
          marginTop: '-' + Math.round(ry * c.y) + 'px'
        });
      }
    }
</script>
</html>