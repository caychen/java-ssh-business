<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>

<script type="text/javascript">
	//必须从此转向，否则路径错误会导致读取配置xml和数据xml文件错误。
	var _date = new Date();
	window.location.href = "${pageContext.request.contextPath}/stat/chart/<%=request.getParameter("forward")%>/index.html?d="+_date;
</script>

<body>


</body>
</html>
