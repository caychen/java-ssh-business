<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../../base.jsp" %>
<head>
<title>商务综合管理平台</title>
	<link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/login.css" media="all" />
	<script src="${ctx}/components/pngfix/DD_belatedPNG.js"></script>
	<script> DD_belatedPNG.fix('*'); </script>
</head>

<body>
<form id="login_main" method="post">
<div id="png">
	<div class="box">
			<div class="inputstyle">
				<div class="inputlable">用户名：
					<input type="text" value="${userName}" name="username" id="userName" onFocus="this.select();" title="请您输入用户名"/>
					<div id="ts" style="z-index:1;">
					</div>
				</div>

			    <div class="inputlable">密　码：
					<input type="password" value="${password}" name="password" id="password" onfocus="$('#ts').css('display','none');this.select();"
						onKeyDown="javascript:if(event.keyCode==13){ submitFind(); }" title="请您输入密码"/>
				</div>
			</div>
			<div class="btnstyle">
				<input  class="loginImgOut" value="" type="button" onclick="formSubmit('${ctx}/login.action','_self');"
				  onmouseover="this.className='loginImgOver'" 
				  onmouseout="this.className='loginImgOut'"
				/>
				<input class="resetImgOut" value="" type="button"   
				  onmouseover="this.className='resetImgOver'" 
				  onmouseout="this.className='resetImgOut'"
				/>
			</div>
		  	<div class="msgtip">
				<c:if test="${!empty errorInfo}">
					${errorInfo}
				</c:if>
			</div>
	</div>
</div>
</form>

<script type="text/JavaScript">
	document.getElementById('login_main').userName.focus();
</script>

</body>
</html>


