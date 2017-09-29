<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../../base.jsp"%>
<head>
<title></title>
<script type="text/javascript"
	src="${ctx }/js/datepicker/WdatePicker.js"></script>
</head>

<body>
	<form name="icform" method="post">

		<div id="menubar">
			<div id="middleMenubar">
				<div id="innerMenubar">
					<div id="navMenubar">
						<ul>
							<li id="save">
								<a href="#"	onclick="formSubmit('outProductAction_print?useTemplate=false','_self');this.blur();">非模版打印</a>
							</li>
							<li id="save1">
								<a href="#"	onclick="formSubmit('outProductAction_print?useTemplate=true','_self');this.blur();">模版打印</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox-title">
			<img src="${ctx }/skin/default/images/icon/folder_edit.png" />
			购销合同月统计（出货表）
		</div>


		<div>
			<table class="commonTable" cellspacing="1">
				<tr>
					<td class="columnTitle">船期：</td>
					<td class="tableContent"><input type="text"
						style="width: 90px;" name="inputDate" value="2015-07"
						onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM'});" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>

