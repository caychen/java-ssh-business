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
		<input type="hidden" name="id" value="${id}" />

		<div id="menubar">
			<div id="middleMenubar">
				<div id="innerMenubar">
					<div id="navMenubar">
						<ul>
							<li id="save">
								<a href="#"	onclick="formSubmit('roleAction_update','_self');this.blur();">保存</a>
							</li>
							<li id="back">
								<a href="#" onclick="history.go(-1);">返回</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox" id="centerTextbox">
			<div class="textbox-header">
				<div class="textbox-inner-header">
					<div class="textbox-title">修改角色</div>
				</div>
			</div>

			<div>
				<table class="commonTable" cellspacing="1">
					<tr>
						<td class="columnTitle">名称：</td>
						<td class="tableContent">
							<input type="text" name="name" value="${name}" />
						</td>
					</tr>
					<tr>
						<td class="columnTitle">说明：</td>
						<td class="tableContent">
							<input type="text" name="remark" value="${remark}" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>

