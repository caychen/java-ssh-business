<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../base.jsp" %>
<head>
    <title>模块介绍</title>
  	<link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/staticfile/skin/default/css/main.css" media="all"/>
</head>

<body>
<form>
<div class="textbox"></div>

	<div class="modelDiv">

        <table class="modelTable" cellspacing="1">
        	<tr>
        		<td colspan="2" class="modelTitle">统计分析介绍</td>
        	</tr>
        	<tr>
        		<td class="subModelTitle">生产厂家销售情况</td>
        		<td class="model_intro_right">和公司合作的生产厂家销售情况饼形图<br>
				</td>
        	</tr>
        	<tr>
        		<td class="subModelTitle">产品销售排行</td>
        		<td class="model_intro_right">统计公司最畅销的产品是哪些？<br>统计公司最滞销的产品是哪些？</td>
        	</tr>
        	<tr>
        		<td class="subModelTitle">系统访问</td>
        		<td class="model_intro_right">统计系统一天用户访问系统的情况，何时访问频繁，何时访问较少，方便开发人员对系统进行优化。</td>
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