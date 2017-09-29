<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../baselist.jsp" %>
<head>
    <title></title>
    <link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/left.css" media="all"/>
</head>
 
<body id="left_frame">
<div class="PositionFrame_black" id="PositionFrame"></div>
 
 
<!-- begin1  -->
<div id="sidebar" class="sidebar">
	<div class="sidebar_t">
		<div class="sidebar_t_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_t_r"></div>
	</div>
        <div class="panel">
        <div class="panel_icon"><img src="${ctx}/skin/default/images/icon/components.png"/></div>
        <div class="panel-header">
        <div class="panel-title">货运管理</div>
        <div class="panel-content">
			<%-- <ul>
				<li><a href="${ctx}/cargo/contractAction_list" onclick="linkHighlighted(this)" target="main" id="aa_1">购销合同</a></li>
				<li><a href="${ctx}/cargo/outProductAction_toedit" onclick="linkHighlighted(this)" target="main" id="aa_1">出货表</a></li>
				<li><a href="${ctx}/cargo/export/exportAction_contractList" onclick="linkHighlighted(this)" target="main" id="aa_1">合同查询</a></li>
				<li><a href="${ctx}/cargo/export/exportAction_list.action" onclick="linkHighlighted(this)" target="main" id="aa_1">出口报运</a></li>
			</ul> --%>
			 <%@include file="../leftmenu.jsp" %> 
        </div>
        </div>
    </div>
    <div class="sidebar_t">
		<div class="sidebar_b_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_b_r"></div>
	</div>  
</div>	


</body>
</html>
