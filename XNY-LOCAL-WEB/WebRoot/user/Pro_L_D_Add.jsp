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
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	String CTime = CommUtil.StrToGB2312(request.getParameter("CTime"));
	String Func_Corp_Id = CommUtil.StrToGB2312(request.getParameter("Func_Corp_Id"));
	CurrStatus currStatus  = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	String ManageId = UserInfo.getManage_Role();
	
	 String Manage_List = "";
								if(ManageId.length() > 0 && null != User_Manage_Role)
								{
									Iterator iterator = User_Manage_Role.iterator();
									while(iterator.hasNext())
									{
										UserRoleBean statBean = (UserRoleBean)iterator.next();
										if(statBean.getId().substring(0,4).equals(ManageId) && statBean.getId().length() == 8)
										{
											String R_Point = statBean.getPoint();
											if(null == R_Point){R_Point = "";}
											Manage_List += R_Point;
										}
									}
								}
								String Dept_Id = UserInfo.getDept_Id();
								if(Dept_Id.length()>3){Manage_List = Dept_Id; }
	
%>
<body style="background:#CADFFF">
<form name="Pro_L_D_Add" action="Pro_L_D_Add.do" method="post" target="mFrame">
	<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='30'><td colspan='4' align='center'><strong>场站日报其他信息录入</strong></td></tr>
			<tr height='30'>
				<td width='20%' align='center'>站点</td>	
				<td width='30%' align='center'>				
					<select  name='Func_Cpm_Id' style='width:150px;height:20px' onChange="doSelect()" >																		
								<%
								if(Manage_List.length() > 0 && null != User_Device_Detail)
								{
									Iterator iterator = User_Device_Detail.iterator();
									while(iterator.hasNext())
									{
										DeviceDetailBean statBean = (DeviceDetailBean)iterator.next();
										if(Manage_List.contains(statBean.getId()))
										{
								%>
											<option value='<%=statBean.getId()%>' <%=currStatus.getFunc_Cpm_Id().equals(statBean.getId())?"selected":""%>><%=statBean.getBrief()%></option>
								<%
										}
									}
								}
								%>
				</select>
				</td>
				<td width='20%' align='center'>日期</td>
				<td width='30%' align='center'>
					<input id='BDate' name='BDate' type='text'  value='<%=CTime%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
				</td>
			</tr>
			
			<tr height='30'>
				<td width='20%' align='center'>场站员工(人)</td>	
				<td width='30%' align='center'>
					<input type='text' name='Z_Person' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>承包商(人)</td>
				<td width='30%' align='center'>
					<input type='text' name='C_Person' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
			<tr height='30'>
				<td width='20%' align='center'>事故、险情</td>	
				<td width='30%' align='center'>
					<input type='text' name='Danger' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>违章情况</td>
				<td width='30%' align='center'>
					<input type='text' name='Peccancy' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
			<tr height='30'>
				<td width='20%' align='center'>消防系统状态</td>	
				<td width='30%' align='center'>
					<input type='text' name='XiaoFang' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>探测报警系统状态</td>
				<td width='30%' align='center'>
					<input type='text' name='BaoJing' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
			<tr height='30'>
				<td width='20%' align='center'>通讯系统状态</td>	
				<td width='30%' align='center'>
					<input type='text' name='TongXun' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>医疗急救设施</td>
				<td width='30%' align='center'>
					<input type='text' name='JiJiu' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
	</table>
			<div align='center' >
				<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doAdd()'>					
			</div>
<input name="Cmd" 					type="hidden"  value= "10" >
<input name="Sid" 					type="hidden"  value= "<%=Sid%>">
<input name="Cpm_Id" 				type="hidden"  value= "" >
<input name="CTime" 				type="hidden"  value= "" >
<input name="BTime" 				type="hidden"  value= "" >
<input name="ETime" 				type="hidden"  value= "" >
<input name="Func_Corp_Id" 				type="hidden"  value= "<%=Func_Corp_Id%>" >
</form>
</body>
<SCRIPT LANGUAGE=javascript>

function doAdd()
{

	Pro_L_D_Add.Cpm_Id.value = Pro_L_D_Add.Func_Cpm_Id.value;
	Pro_L_D_Add.CTime.value = Pro_L_D_Add.BDate.value;
	Pro_L_D_Add.BTime.value = Pro_L_D_Add.BDate.value;
	Pro_L_D_Add.ETime.value = Pro_L_D_Add.BDate.value;
	Pro_L_D_Add.submit();

}
function changeEnter()
{    
	if(event.keyCode==13)
	{
		event.keyCode=9;
	} 	
} 
</SCRIPT>
</html>