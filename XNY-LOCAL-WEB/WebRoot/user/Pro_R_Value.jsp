<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>
<%@ page import="util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.math.*" %>
<%@ page import="java.text.*" %>
<%@ taglib uri="/WEB-INF/limitvalidatetag.tld" prefix="Limit"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中新能源LNG公司级信息化管理平台</title>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="../skin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../skin/js/util.js"></script>
<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
</head>
<%
	
	String Sid       = CommUtil.StrToGB2312(request.getParameter("Sid"));
	String Operator  = CommUtil.StrToGB2312(request.getParameter("Operator"));
	String Cpm_Id    = CommUtil.StrToGB2312(request.getParameter("Cpm_Id"));
	String Cpm_Name  = CommUtil.StrToGB2312(request.getParameter("Cpm_Name"));
	String Oil_CType = CommUtil.StrToGB2312(request.getParameter("Oil_CType"));
	String Value_Ware= CommUtil.StrToGB2312(request.getParameter("Value_Ware"));
	String Value     = CommUtil.StrToGB2312(request.getParameter("Value"));
	String WUnit      = CommUtil.StrToGB2312(request.getParameter("WUnit"));
	String VUnit      = CommUtil.StrToGB2312(request.getParameter("VUnit"));
	String Oil_CName = "";
	
	CurrStatus currStatus  = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	String Oil_Info = "";
	if(null != Corp_Info)
	{
		Oil_Info = Corp_Info.getOil_Info();
		if(null != Oil_Info && Oil_Info.trim().length() > 0)
		{
		  String[] List = Oil_Info.split(";");
		  for(int i=0; i<List.length && List[i].length()>0; i++)
		  {
		  	String[] subList = List[i].split(",");
		  	if(subList[0].equals(Oil_CType))
		  	{
		  		Oil_CName = subList[1];
		  		break;
		  	}
		  }
		}
	}
	
%>
<body style="background:#CADFFF">
<form name="Pro_R_Value" action="Pro_R.do" method="post" target="mFrame">
	<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='30'>
			<td width='25%' align='center'>站&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点</td>
			<td width='75%' align='left'>
				<%=Cpm_Name%>
				<input type='hidden' name='Cpm_Id' value='<%=Cpm_Id%>'>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>燃料类型</td>
			<td width='75%' align='left'>
				<%=Oil_CName%>
				<input type='hidden' name='Oil_CType' value='<%=Oil_CType%>'>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>当前库存</td>
			<td width='75%' align='left'>
				<input type='text' name='Value' value='<%=Value%>' style='width:120px;height:16px;' maxlength=10> <%=VUnit%>
			</td>
		</tr>
		<tr height='30'>
			<td width='25%' align='center'>预警阀值</td>
			<td width='75%' align='left'>
				<input type='text' name='Value_Ware' value='<%=Value_Ware%>' style='width:120px;height:16px;' maxlength=10> <%=WUnit%>
			</td>
		</tr>
		<tr height='40'>
			<td width='100%' align='center' colspan=2>
				<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doSave()'>
			</td>
		</tr>
	</table>
	<input type='hidden' name='Cmd' value='12'>
	<input type='hidden' name='Sid' value='<%=Sid%>'>
	<input type='hidden' name='Operator'     value='<%=Operator%>'>
	<input type='hidden' name='Func_Corp_Id' value='<%=currStatus.getFunc_Corp_Id()%>'>
	<input type='hidden' name='Func_Sub_Id'  value='<%=currStatus.getFunc_Sub_Id()%>'>
	<input type='hidden' name='Func_Sel_Id'  value='<%=currStatus.getFunc_Sel_Id()%>'>
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doSave()
{
	if(Pro_R_Value.Value.value.Trim().length < 1 || Pro_R_Value.Value.value < 0)
  {
  	alert("当前库存错误,可能的原因：\n\n  1.数量为空。\n\n  2.数量不是正值。");
		return;
  }
	for(var i=0; i<Pro_R_Value.Value.value.length; i++)
	{
		if(Pro_R_Value.Value.value.charAt(0) == '.' || Pro_R_Value.Value.value.charAt(Pro_R_Value.Value.value.length-1) == '.')
		{
			alert("输入当前库存有误，请重新输入!");
	    return;
		}
		if(Pro_R_Value.Value.value.charAt(i) != '.' && isNaN(Pro_R_Value.Value.value.charAt(i)))
	  {
	    alert("输入当前库存有误，请重新输入!");
	    return;
	  }
	}
	if(Pro_R_Value.Value.value.indexOf(".") != -1)
	{
		if(Pro_R_Value.Value.value.substring(Pro_R_Value.Value.value.indexOf(".")+1,Pro_R_Value.Value.value.length).length >2)
		{
			alert("小数点后最多只能输入两位!");
			return;
		}
	}
	if(Pro_R_Value.Value_Ware.value.Trim().length < 1 || Pro_R_Value.Value_Ware.value < 0)
  {
  	alert("当前库存错误,可能的原因：\n\n  1.数量为空。\n\n  2.数量不是正值。");
		return;
  }
	for(var i=0; i<Pro_R_Value.Value_Ware.value.length; i++)
	{
		if(Pro_R_Value.Value_Ware.value.charAt(0) == '.' || Pro_R_Value.Value_Ware.value.charAt(Pro_R_Value.Value_Ware.value.length-1) == '.')
		{
			alert("输入当前库存有误，请重新输入!");
	    return;
		}
		if(Pro_R_Value.Value_Ware.value.charAt(i) != '.' && isNaN(Pro_R_Value.Value_Ware.value.charAt(i)))
	  {
	    alert("输入当前库存有误，请重新输入!");
	    return;
	  }
	}
	if(Pro_R_Value.Value_Ware.value.indexOf(".") != -1)
	{
		if(Pro_R_Value.Value_Ware.value.substring(Pro_R_Value.Value_Ware.value.indexOf(".")+1,Pro_R_Value.Value_Ware.value.length).length >2)
		{
			alert("小数点后最多只能输入两位!");
			return;
		}
	}
	if(confirm('确定纠偏[当前站点][当前燃料]的库存数量或变更预警阀值?'))
	{
		Pro_R_Value.submit();
	}
}
</SCRIPT>
</html>