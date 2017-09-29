<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>商务综合管理平台</title>
<script type="text/javascript">
	if(self.location != top.location){
		console.log(self.location);
		console.log(top.location);
		
		top.location = self.location;
	}
</script>
</head>
<frameset rows="125,*" name="topFrameset" border="0">
	<frame name="top_frame" scrolling="no"  target="middleFrameSet" src="homeAction_title.action">	
	<frameset cols="202,*" height="100%" name="middle" frameborder="no" border="0" framespacing="0">
		<frame name="leftFrame" class="leftFrame" target="main" scrolling="no" src="homeAction_toleft.action?moduleName=home" />
		<frame name="main" class="rightFrame" src="homeAction_tomain.action?moduleName=home" />
	</frameset>
</frameset>

<noframes>
<body>
    <p>此网页使用了框架，但您的浏览器不支持框架。</p>
</body>
</noframes>

</html>