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
							<li id="save"><a href="#"
								onclick="formSubmit('userAction_update','_self');this.blur();">保存</a></li>
							<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox-title">
			<img src="${ctx }/skin/default/images/icon/currency_yen.png" /> 查看用户
		</div>



		<div>
			<table class="commonTable" cellspacing="1">
				<tr>
					<td class="columnTitle">所在部门：</td>
					<td class="tableContent"><s:select name="dept.id"
							list="deptList" listKey="id" listValue="deptName" headerKey=""
							headerValue="--请选择--"></s:select></td>
				</tr>
				<tr>
					<td class="columnTitle">用户名：</td>
					<td class="tableContent"><input type="text" name="userName"
						value="${userName }" /></td>
				</tr>
				<tr>
					<td class="columnTitle">状态：</td>
					<td class="tableContentAuto"><input type="radio" name="state"
						class="input" ${state==0?'checked':'' } value="0">停用 <input
						type="radio" name="state" class="input" ${state==1?'checked':'' }
						value="1">启用</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>