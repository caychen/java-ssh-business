<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
-8"%>
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
							<li id="save"><a href="#"
								onclick="formSubmit('deptAction_update','_self');this.blur();">保存</a></li>
							<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox-title">
			<img src="${ctx }/skin/default/images/icon/currency_yen.png" /> 修改部门
		</div>

		<div>
			<table class="commonTable" cellspacing="1">
				<tr>
					<td class="columnTitle">上级部门：</td>
					<td class="tableContent">
						<s:select name="parent.id"
								list="deptList" listKey="id" listValue="deptName" headerKey=""
								headerValue="--请选择--">
						</s:select>
					</td>
				</tr>
				<tr>
					<td class="columnTitle">部门名称：</td>
					<td class="tableContent">
						<input type="text" name="deptName" value="${deptName }" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>