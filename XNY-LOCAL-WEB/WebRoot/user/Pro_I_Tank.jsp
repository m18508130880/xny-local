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
	CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	String BDate = CommUtil.getDate();
	
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
  UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
  
  ArrayList Pro_I_MX = (ArrayList)session.getAttribute("Pro_I_MX_" + Sid);
  ArrayList Pro_I_Tank = (ArrayList)session.getAttribute("Pro_I_Tank_" + Sid);
  String Memo = "";
 	String SN   = "";
 	String SNO  = "";
 	String SNT  = "";
 	String Cpm_Id = "";
 	String Order_Id ="";
 	String CTime    ="";
%>
<body style="background:#CADFFF">
<form name="Pro_I_Detail" action="Pro_I.do" method="post" target="mFrame">
<div id="down_bg_2">
	<div id="cap"><img src="../skin/images/pro_i_Detail.gif"></div><br><br>
	<div id="right_table_center">
		<table width="80%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">			
			<tr>
				<td align='right'>
					<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doEdit()'>
					<img id="img2" src="../skin/images/excel.gif"   onClick='doExport()'  >
					</td>
				</tr>
			<tr height='30'>
				<td width='100%' align='center'>
					<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">		
			<%
				if(null != Pro_I_MX)
					 {
						Iterator iterator = Pro_I_MX.iterator();
						while(iterator.hasNext())
						{
							ProIBean Bean = (ProIBean)iterator.next();
							 SN         = Bean.getSN();
						 Cpm_Id     = Bean.getCpm_Id();
							String Cpm_Name   = Bean.getCpm_Name();
							String Oil_CType  = Bean.getOil_CType();
							String Tank_No    = Bean.getTank_No();
							String Arrive_Time = Bean.getArrive_Time();
						  CTime       = Bean.getCTime();
							String Depart_Time = Bean.getDepart_Time();
							Order_Id    = Bean.getOrder_Id();
							String Temper_Report = Bean.getTemper_Report();
							String Worker        = Bean.getWorker();
							String Car_Id        = Bean.getCar_Id();
							String Pre_Check     = Bean.getPre_Check();
							String Trailer_No    = Bean.getTrailer_No();
							String Pro_Check     = Bean.getPro_Check();
							String Car_Owner     = Bean.getCar_Owner();
							String Lat_Check     = Bean.getLat_Check();
							String Pre_Weight    = Bean.getPre_Weight();
							String Gross_Weight  = Bean.getGross_Weight();
							String Lat_Weight    = Bean.getLat_Weight();
							String Tear_Weight   = Bean.getTear_Weight();
							String Value         = Bean.getValue();
							String Ture_Weight   = Bean.getTure_Weight();
							String Forward_Unit  = Bean.getForward_Unit();
							String Car_Corp      = Bean.getCar_Corp();
							Memo                 = Bean.getMemo();
			%>				
				<tr height='30'>
							<td width='11%'>卸车站点</td>
							<td width='22%'>
								<%=Cpm_Name%>
							</td>	
							<td width='11%'>燃料类型</td>
							<td width='22%'>
							<%=Oil_CType%>
							</td>	
							<td>卸入罐号</td>
							<td>
								<%=Tank_No%>
							</td>							
						</tr>						
						<tr height='30'>
							<td>到站时间</td>
							<td>
								<input name='BDate_A' type='text' style='width:40%;height:18px;' value='<%=Arrive_Time.substring(0,10)%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onkeydown="changeEnter()">
								<select name="Hour_A" style="width:25%;height:20px" onkeydown="changeEnter()">
									<option value='00'>0点</option>
									<option value='01'>1点</option>
									<option value='02'>2点</option>
									<option value='03'>3点</option>
									<option value='04'>4点</option>
									<option value='05'>5点</option>
									<option value='06'>6点</option>
									<option value='07'>7点</option>
									<option value='08'>8点</option>
									<option value='09' selected>9点</option>
									<option value='10'>10点</option>
									<option value='11'>11点</option>
									<option value='12'>12点</option>
									<option value='13'>13点</option>
									<option value='14'>14点</option>
									<option value='15'>15点</option>
									<option value='16'>16点</option>
									<option value='17'>17点</option>
									<option value='18'>18点</option>
									<option value='19'>19点</option>
									<option value='20'>20点</option>
									<option value='21'>21点</option>
									<option value='22'>22点</option>
									<option value='23'>23点</option>
								</select>
								<select name="Minute_A" style="width:25%;height:20px" onkeydown="changeEnter()">
									<option value='05'>5分</option>
									<option value='10'>10分</option>
									<option value='15'>15分</option>
									<option value='20'>20分</option>
									<option value='25'>25分</option>
									<option value='30' selected>30分</option>
									<option value='35'>35分</option>
									<option value='40'>40分</option>
									<option value='45'>45分</option>
									<option value='50'>50分</option>
									<option value='55'>55分</option>
								</select>								
							</td>	
							<td>开始卸车时间</td>
							<td>
								<input name='BDate_C' type='text' style='width:40%;height:18px;' value='<%=CTime.substring(0,10)%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onkeydown="changeEnter()">
								<select name="Hour_C" style="width:25%;height:20px" onkeydown="changeEnter()">
									<option value='00'>0点</option>
									<option value='01'>1点</option>
									<option value='02'>2点</option>
									<option value='03'>3点</option>
									<option value='04'>4点</option>
									<option value='05'>5点</option>
									<option value='06'>6点</option>
									<option value='07'>7点</option>
									<option value='08'>8点</option>
									<option value='09' selected>9点</option>
									<option value='10'>10点</option>
									<option value='11'>11点</option>
									<option value='12'>12点</option>
									<option value='13'>13点</option>
									<option value='14'>14点</option>
									<option value='15'>15点</option>
									<option value='16'>16点</option>
									<option value='17'>17点</option>
									<option value='18'>18点</option>
									<option value='19'>19点</option>
									<option value='20'>20点</option>
									<option value='21'>21点</option>
									<option value='22'>22点</option>
									<option value='23'>23点</option>
								</select>
								<select name="Minute_C" style="width:25%;height:20px" onkeydown="changeEnter()">
									<option value='05'>5分</option>
									<option value='10'>10分</option>
									<option value='15'>15分</option>
									<option value='20'>20分</option>
									<option value='25'>25分</option>
									<option value='30' selected>30分</option>
									<option value='35'>35分</option>
									<option value='40'>40分</option>
									<option value='45'>45分</option>
									<option value='50'>50分</option>
									<option value='55'>55分</option>
								</select>							
							</td>	
							<td>离开时间</td>
							<td>
								<input name='BDate_D' type='text' style='width:40%;height:18px;' value='<%=Depart_Time.substring(0,10)%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onkeydown="changeEnter()">
								<select name="Hour_D" style="width:25%;height:20px" onkeydown="changeEnter()">
									<option value='00'>0点</option>
									<option value='01'>1点</option>
									<option value='02'>2点</option>
									<option value='03'>3点</option>
									<option value='04'>4点</option>
									<option value='05'>5点</option>
									<option value='06'>6点</option>
									<option value='07'>7点</option>
									<option value='08'>8点</option>
									<option value='09' selected>9点</option>
									<option value='10'>10点</option>
									<option value='11'>11点</option>
									<option value='12'>12点</option>
									<option value='13'>13点</option>
									<option value='14'>14点</option>
									<option value='15'>15点</option>
									<option value='16'>16点</option>
									<option value='17'>17点</option>
									<option value='18'>18点</option>
									<option value='19'>19点</option>
									<option value='20'>20点</option>
									<option value='21'>21点</option>
									<option value='22'>22点</option>
									<option value='23'>23点</option>
								</select>
								<select name="Minute_D" style="width:25%;height:20px" onkeydown="changeEnter()">
									<option value='05'>5分</option>
									<option value='10'>10分</option>
									<option value='15'>15分</option>
									<option value='20'>20分</option>
									<option value='25'>25分</option>
									<option value='30' selected>30分</option>
									<option value='35'>35分</option>
									<option value='40'>40分</option>
									<option value='45'>45分</option>
									<option value='50'>50分</option>
									<option value='55'>55分</option>
								</select>								
							</td>	
						</tr>
						<tr height='30'>
							<td>卸车单号</td>
							<td>
								<%=Order_Id%>	
							</td>	
							<td>气质报告单</td>
							<td>
								<%=Temper_Report%>
							</td>	
							<td width='11%'>作业人员</td>
							<td width='22%'>
								<%=Worker%>
							</td>	
						</tr>
					</table>
					<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">	
						<tr>
							<td rowspan='8' > LNG槽车 </td>
						</tr>
						<tr>	
							<td width="14%">运输车牌</td>
							<td width="30%">
								<input type='text' name='Car_Id' style='width:85%;height:18px;' value='<%=Car_Id%>	' maxlength='20' >&nbsp;&nbsp;																
							</td>
							<td width="15%">卸车前检查</td>
							<td width="30%">
								<input type='text' name='Pre_Check' style='width:85%;height:18px;' value='<%=Pre_Check%>' maxlength='20' >&nbsp;&nbsp;								
							</td>
						</tr>
						<tr>							
							<td>挂车车号</td>
							<td>
								<input type='text' name='Trailer_No' style='width:85%;height:18px;' value='<%=Trailer_No%>' maxlength='20' >&nbsp;&nbsp;	
							</td>
							<td>卸车过程检查</td>
							<td>
								<input type='text' name='Pro_Check' style='width:85%;height:18px;' value='<%=Pro_Check%>' maxlength='20' >&nbsp;&nbsp;
							</td>
						</tr>
						<tr>							
							<td>槽车司机</td>
							<td>
								<input type='text' name='Car_Owner' style='width:85%;height:18px;' value='<%=Car_Owner%>' maxlength='20' >&nbsp;&nbsp;
							</td>
							<td>卸车后检查</td>
							<td>
								<input type='text' name='Lat_Check' style='width:85%;height:18px;' value='<%=Lat_Check%>' maxlength='20' >&nbsp;&nbsp;
							</td>
						</tr>
						<tr>							
							<td>卸车前重量</td>
							<td>
								<input type='text' name='Pre_Weight' style='width:85%;height:18px;' value='<%=Pre_Weight%>' maxlength='20' >kg
							</td>
							<td>装车毛重</td>
							<td>
								<input type='text' name='Gross_Weight' style='width:85%;height:18px;' value='<%=Gross_Weight%>' maxlength='20' >kg
							</td>
						</tr>
						<tr>							
							<td>卸车后重量</td>
							<td>
								<input type='text' name='Lat_Weight' style='width:85%;height:18px;' value='<%=Lat_Weight%>' maxlength='20' >kg
							</td>
							<td>装车皮重</td>
							<td>
								<input type='text' name='Tear_Weight' style='width:85%;height:18px;' value='<%=Tear_Weight%>' maxlength='20' >kg
							</td>
						</tr>
						<tr>							
							<td>卸入量</td>
							<td>
								<input type='text' name='Value' style='width:85%;height:18px;' value='<%=Value%>' maxlength='20' >kg
							</td>
							<td>装车净重</td>
							<td>
								<input type='text' name='Ture_Weight' style='width:85%;height:18px;' value='<%=Ture_Weight%>' maxlength='20' >kg
							</td>
						</tr>
						<tr>							
							<td>发货单位</td>
							<td>
									<input type='text' name='Forward_Unit' style='width:85%;height:18px;' value='<%=Forward_Unit%>' maxlength='20' >&nbsp;&nbsp;
							</td>
							<td>承运单位</td>
							<td>
								<input type='text' name='Car_Corp' style='width:85%;height:18px;' value='<%=Car_Corp%>' maxlength='20' >&nbsp;&nbsp;
							</td>
						</tr>			
			<%
						}
					}			
			%>
				</table>					
	<%						
			if(null != Pro_I_Tank)
					 {
					 	int i = 0;
						Iterator it = Pro_I_Tank.iterator();
						while(it.hasNext())
						{				
							i++;
							ProITankBean tBean = (ProITankBean)it.next();							
							String Tank_No       = tBean.getTank_No();
							String Pre_Tank_V    = tBean.getPre_Tank_V();
							String Lat_Tank_V    = tBean.getLat_Tank_V();
							String Unload        = tBean.getUnload();
							String Pre_Temper    = tBean.getPre_Temper();
							String Lat_Temper    = tBean.getLat_Temper();
							String Pre_Press     = tBean.getPre_Press();
							String Lat_Press     = tBean.getLat_Press();
							if(Tank_No.equals("1") && i == 1)
							{
								SNO  = tBean.getSN();
	%>	
				<table id='table_One' width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff" style="display:''">				
					<tr>
							<td rowspan='4'><%=Tank_No%>号罐</td>								
						</tr>
						<tr>
							<td width="11%">卸车前罐容</td>
							<td width="18%">
								<input type='text' name='Pre_Tank_V' style='width:85%;height:18px;' value='<%=Pre_Tank_V%>' maxlength='20' >L	
							</td>
							<td width="11%">卸车后罐容</td>
							<td width="19%">
								<input type='text' name='Lat_Tank_V' style='width:85%;height:18px;' value='<%=Lat_Tank_V%>' maxlength='20' >L
							</td>
							<td width="11%">卸入容积</td>
							<td width="19%">
								<input type='text' name='Unload' style='width:85%;height:18px;' value='<%=Unload%>' maxlength='20' >L
							</td>
						</tr> 
						<tr>
							<td>卸车前温度</td>
							<td colspan='2'>
								<input type='text' name='Pre_Temper' style='width:85%;height:18px;' value='<%=Pre_Temper%>' maxlength='20' >&nbsp;&nbsp;&nbsp;℃
							</td>
							<td>卸车后温度</td>
							<td colspan='2'>
								<input type='text' name='Lat_Temper' style='width:85%;height:18px;' value='<%=Lat_Temper%>' maxlength='20' >&nbsp;&nbsp;&nbsp;℃
							</td>	
						</tr>
						<tr>
							<td>卸车前压力</td>
							<td colspan='2'>
								<input type='text' name='Pre_Press' style='width:85%;height:18px;' value='<%=Pre_Press%>' maxlength='20' >Mpa
							</td>
							<td>卸车后压力</td>
							<td colspan='2'>
								<input type='text' name='Lat_Press' style='width:85%;height:18px;' value='<%=Lat_Press%>' maxlength='20' >Mpa
							</td>							
						</tr>
			<%			
				if(Pro_I_Tank.size() == 1)		
					{
			%>		
				<tr>
							<td>备注:</td>	
							<td colspan='6'>
								<textarea name='Memo'  rows='2' cols='95' maxlength=128> <%=Memo%></textarea>
							</td>	
						</tr>		
			<%		
					}
			%>			
				</table>													
			<%									
							}		
					if(Tank_No.equals("2") )
						{
							 SNT = tBean.getSN();
			%>			
				<table id='table_Two' width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff" style="display:''">				
					<tr>
							<td rowspan='4'><%=Tank_No%>号罐</td>								
						</tr>
						<tr>
							<td width="11%">卸车前罐容</td>
							<td width="18%">
								<input type='text' name='Pre_Tank_VT' style='width:85%;height:18px;' value='<%=Pre_Tank_V%>' maxlength='20' >L
							</td>
							<td width="11%">卸车后罐容</td>
							<td width="19%">
								<input type='text' name='Lat_Tank_VT' style='width:85%;height:18px;' value='<%=Lat_Tank_V%>' maxlength='20' >L
							</td>
							<td width="11%">卸入容积</td>
							<td width="19%">
								<input type='text' name='UnloadT' style='width:85%;height:18px;' value='<%=Unload%>' maxlength='20' >L
							</td>
						</tr> 
						<tr>
							<td>卸车前温度</td>
							<td colspan='2'>
								<input type='text' name='Pre_TemperT' style='width:85%;height:18px;' value='<%=Pre_Temper%>' maxlength='20' >&nbsp;&nbsp;&nbsp;℃
							</td>
							<td>卸车后温度</td>
							<td colspan='2'>
								<input type='text' name='Lat_TemperT' style='width:85%;height:18px;' value='<%=Lat_Temper%>' maxlength='20' >&nbsp;&nbsp;&nbsp;℃
							</td>	
						</tr>
						<tr>
							<td>卸车前压力</td>
							<td colspan='2'>
								<input type='text' name='Pre_PressT' style='width:85%;height:18px;' value='<%=Pre_Press%>' maxlength='20' >Mpa
							</td>
							<td>卸车后压力</td>
							<td colspan='2'>
								<input type='text' name='Lat_PressT' style='width:85%;height:18px;' value='<%=Lat_Press%>' maxlength='20' >Mpa
							</td>							
						</tr>
						<tr>
							<td>备注:</td>	
							<td colspan='6'>
								<textarea name='Memo'  rows='2' cols='95' maxlength=128> <%=Memo%></textarea>
							</td>	
						</tr>
				</table>																		
			<%				
							}
						}	
					}									
		%>													
				</td>
			</tr>			
		</table>
	</div>
</div>
<input name="Cmd"      type="hidden" value="12">
<input name="SN" type="hidden" value="<%=SN%>"/>
<input name="SNO" type="hidden" value="<%=SNO%>"/>
<input name="SNT" type="hidden" value="<%=SNT%>"/>
<input name="Sid"      type="hidden" value="<%=Sid%>">
<input name="Cpm_Id"      type="hidden" value="<%=Cpm_Id%>">
<input name="CTime"      type="hidden" value="">
<input name="Arrive_Time"      type="hidden" value="">
<input name="Depart_Time"      type="hidden" value="">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doEdit()
{
		Pro_I_Detail.CTime.value=Pro_I_Detail.BDate_C.value+' '+Pro_I_Detail.Hour_C.value+':'+Pro_I_Detail.Minute_C.value+':00';
		Pro_I_Detail.Arrive_Time.value = Pro_I_Detail.BDate_A.value+' '+Pro_I_Detail.Hour_A.value+':'+Pro_I_Detail.Minute_A.value+':00';
		Pro_I_Detail.Depart_Time.value = Pro_I_Detail.BDate_D.value+' '+Pro_I_Detail.Hour_D.value+':'+Pro_I_Detail.Minute_D.value+':00';
		Pro_I_Detail.submit();
}

function doExport()
{	
	if(confirm("确定导出?"))
  {
		if(window.XMLHttpRequest)
	  {
			req = new XMLHttpRequest();
		}
		else if(window.ActiveXObject)
		{
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}		
		//设置回调函数
		req.onreadystatechange = callbackForName;
		var url = "Pro_I_Detail_Export.do?&Sid=<%=Sid%>&SN=<%=SN%>&Cpm_Id=<%=Cpm_Id%>&Func_Type_Id=<%=Order_Id%>&CTime=<%=CTime%>";
		req.open("post",url,true);
		req.send(null);
		return true;
	}
}
function callbackForName()
{
	var state = req.readyState;
	if(state==4)
	{
		var resp = req.responseText;			
		var str = "";
		if(resp != null)
		{
			location.href = "../files/excel/" + resp + ".xls";
		}
	}
}
</SCRIPT>
</html>