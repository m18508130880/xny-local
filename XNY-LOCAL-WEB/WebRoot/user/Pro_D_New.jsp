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

</head>
<%

	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	String Cpm_Id = CommUtil.StrToGB2312(request.getParameter("Cpm_Id"));
	String Func_Corp_Id = CommUtil.StrToGB2312(request.getParameter("Func_Corp_Id"));
	String BTime = CommUtil.StrToGB2312(request.getParameter("BTime")); 
	
	CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
			
	ArrayList User_FP_Role = (ArrayList)session.getAttribute("User_FP_Role_" + Sid);
	UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);

	
	
	
	CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	String Rate_t = "1.412";
	if(null != Corp_Info)
	{
		 Rate_t = Corp_Info.getRate(); 
	}
	
%>
<body style="background:#CADFFF">
<form name="Pro_O_Dat" action="Pro_L.do" method="post" target="mFrame">
		<table width="100%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
			<tr height='30'>
				<td width='100%' align='center'>
				<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">	
					<tr height='30'><td colspan='2' align='center'>盈亏修改</td></tr>							       
						<tr height='25'>
							<td width='35%' align='center'>盈亏&nbsp;&nbsp;数量</td>
							<td width='65%' align='left'>
								<input type="text"  name='Value_PAL' style='width:160px;height:20px;' value='' maxlength='10' >kg								
							</td>
						</tr>
						
						<tr><td colspan='2' align='center'>
							<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doEdit()'>	
					</td></tr>
					</table>	
				</td>
			</tr>
		</table>
<input name="Sid" type="hidden" value="<%=Sid%>" />
<input name="Cmd" type="hidden" value="11" />
<input name="Cpm_Id" type="hidden" value="<%=Cpm_Id%>" />
<input name="Func_Sub_Id" type="hidden" value="3" />
<input name="Func_Corp_Id" type="hidden" value="<%=Func_Corp_Id%>" />
<input name="CTime" type="hidden" value="<%=BTime%>" />
<input name="BTime" type="hidden" value="<%=BTime%>" />
<input name="ETime" type="hidden" value="<%=BTime%>" />
<input name='Value_PAL_Gas' type="hidden" value=''  >
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doEdit()
{
	var a = Pro_O_Dat.Value_PAL.value;
	var b = <%=Rate_t%>	
	Pro_O_Dat.Value_PAL_Gas.value = Math.round((a*b)*100)/100;		
	Pro_O_Dat.submit();
}
</SCRIPT>
</html>