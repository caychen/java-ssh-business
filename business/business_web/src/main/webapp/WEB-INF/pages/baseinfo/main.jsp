<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../base.jsp" %>
<head>
    <title>模块介绍</title>
  	<link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/main.css" media="all"/>
</head>

<body>
<form>
<div class="textbox"></div>

	<div class="modelDiv">

        <table class="modelTable" cellspacing="1">
        	<tr>
        		<td colspan="2" class="modelTitle">基础信息管理模块介绍</td>
        	</tr>

        	<tr>
        		<td colspan="2" class="subModelTitle">基础代码管理</td>
        	</tr>        	
			<tr>
				<td class="model_intro_left" width="169">系统代码：</td>
				<td class="model_intro_right" width="81%">统一管理系统中的基础代码, 相比“基础代码”它结果将形成多级树型结构。</td>
			</tr>   	

			<tr>
        		<td colspan="2" class="subModelTitle">基础信息管理</td>
        	</tr>  
			<tr>
				<td class="model_intro_left">厂家信息：</td>
				<td class="model_intro_right">在购销合同中货物和附件中可选择对应的厂家。</td>
			</tr>   	
			
			<tfoot>
				<tr>
					<td colspan="2" class="tableFooter"></td>
				</tr>
			</tfoot>
        </table>
 
	</div>
</form>
</body>

</html>