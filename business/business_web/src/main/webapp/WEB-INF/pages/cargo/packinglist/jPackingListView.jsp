<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../../base.jsp"%>
<head>
<title></title>
</head>

<body>
	<form name="icform" method="post">

		<div id="menubar">
			<div id="middleMenubar">
				<div id="innerMenubar">
					<div id="navMenubar">
						<ul>
							<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox-title">
			<img src="${ctx }/skin/default/images/icon/currency_yen.png" /> 浏览装箱单
		</div>
		<div>
			<table class="commonTable" cellspacing="1">
				<tr>
					<td class="columnTitle">卖方：</td>
					<td class="tableContent">${seller}</td>

					<td class="columnTitle">买方：</td>
					<td class="tableContent">${buyer}</td>
				</tr>
				<tr>
					<td class="columnTitle">发票号：</td>
					<td class="tableContent">${invoiceNo}</td>

					<td class="columnTitle">发票日期：</td>
					<td class="tableContent">${invoiceDate}</td>
				</tr>
				<tr>
					<td class="columnTitle">唛头：</td>
					<td class="tableContent">${marks}</td>

					<td class="columnTitle">描述：</td>
					<td class="tableContent">${descriptions}</td>
				</tr>

				<tr>
					<td class="columnTitle">状态：</td>
					<td class="tableContent">
						<c:if test="${state==0}">草稿</c:if>
						<c:if test="${state==1}">已上报</c:if>
						<c:if test="${state==2}">已装箱</c:if>
					</td>
				</tr>

			</table>
		</div>


	</form>
</body>
</html>

