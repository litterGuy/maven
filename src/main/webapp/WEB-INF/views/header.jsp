<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<style>
	.navbar .container{
		width:980px;
	}
</style>
<div class="navbar">
       <div class="navbar-inner">
         <div class="container">
           <a data-target=".navbar-responsive-collapse" data-toggle="collapse" class="btn btn-navbar">
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
             <span class="icon-bar"></span>
           </a>
           <a href="#" class="brand">litterGuy</a>
           <div class="nav-collapse collapse navbar-responsive-collapse">
             <ul class="nav">
               <li class="active"><a href="${ctx  }/user/index.do">主页</a></li>
               <li><a href="#">Link</a></li>
               <li><a href="#">Link</a></li>
             </ul>
             <ul class="nav pull-right">
               <li><a href="${ctx  }/user/account.do">帐号管理</a></li>
               <li class="divider-vertical"><a href="${ctx  }/j_spring_security_logout">退出</a></li>
               <li class="dropdown">
                 <a data-toggle="dropdown" class="dropdown-toggle" href="#">Dropdown <b class="caret"></b></a>
                 <ul class="dropdown-menu">
                   <li><a href="#">Action</a></li>
                   <li><a href="#">Another action</a></li>
                   <li><a href="#">Something else here</a></li>
                   <li class="divider"></li>
                   <li><a href="#">Separated link</a></li>
                 </ul>
               </li>
             </ul>
           </div>
       </div>
     </div>
   </div>
