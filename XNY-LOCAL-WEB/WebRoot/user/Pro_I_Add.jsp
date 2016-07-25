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
	CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	String BDate = CommUtil.getDate();
	
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
  UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
  String Operator = UserInfo.getId();
  String Operator_Name = UserInfo.getCName();
  String ManageId = UserInfo.getManage_Role();
  
  ArrayList Pro_R_Buss = (ArrayList)session.getAttribute("Pro_R_Buss_" + Sid);
  ArrayList Pro_Tank_NO = (ArrayList)session.getAttribute("Pro_R_Tank_No_" + Sid);
  CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
  ArrayList Pro_I_All = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);
	String Oil_Info = "";
	if(null != Corp_Info)
	{
		Oil_Info = Corp_Info.getOil_Info();
		if(null == Oil_Info){Oil_Info = "";}
	}
	
	String Role_List = "";
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
											Role_List += R_Point;
										}
									}
								}
								String Dept_Id = UserInfo.getDept_Id();
							if(Dept_Id.length()>3){Role_List = Dept_Id; }
%>
<body style="background:#CADFFF">
<form name="Pro_I_Add" action="Pro_I.do" method="post" target="mFrame">
<div id="down_bg_2">
	<div id="cap"><img src="../skin/images/pro_i_add.gif"></div><br><br>
	<div id="right_table_center">
		<table width="60%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
			<tr height='30'>
				<td width='100%' align='right'>
					<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doAdd()'>
					<img src="../skin/images/button10.gif"           style='cursor:hand;' onclick='doNO()'>
				</td>
			</tr>
			<tr height='30'>
				<td width='100%' align='center'>
					<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">		
						<tr height='30'>
							<td width='15%' align='center'>卸车站点</td>
							<td width='35%' align='center'>							
								<select name="Cpm_Id" style="width:80%;height:20px" onchange="doChange(this.value)" onkeydown="changeEnter()">
								<%								
								if(Role_List.length() > 0 && null != User_Device_Detail)
								{
									Iterator iterator = User_Device_Detail.iterator();
									while(iterator.hasNext())
									{
										DeviceDetailBean statBean = (DeviceDetailBean)iterator.next();
										if(Role_List.contains(statBean.getId()))
										{
								%>
											<option value='<%=statBean.getId()%>'><%=statBean.getBrief()%></option>
								<%
										}
									}
								}
								%>
								</select>								
							</td>										
							<td width='15%' align='center'>卸入罐号</td>
							<td width='35%' align='center'>
								<select name="Tank_No" style="width:80%;height:18px" onkeydown="changeEnter()">
			<%
					for(int j=1;j<3;j++)
					{
			%>
								<option value='<%=j%>'><%=j%></option>
			<%				
						
					}										
			%>											
							</td>
						</tr>
					  <tr height='30'>
					  	<td width='15%' align='center'>卸车单号</td>
							<td width='35%' align='center'>
								<input type='text' name='Order_Id' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;&nbsp;
							</td>
							<td width='15%' align='center'>开始卸车时间</td>
							<td width='35%' align='center'>
								<input name='BDate_C' type='text' style='width:40%;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onkeydown="changeEnter()">
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
						</tr>
							<tr>									
							<td width='15%' align='center'>卸车前检查</td>
							<td width='35%' align='center'>							
								<input type='text' name='Pre_Check' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">	&nbsp;&nbsp;&nbsp;						
							</td>
							<td width='15%' align='center'>装车毛重</td>
              <td colspan="2" width='35%' align='center'>
							<input type='text' name='Gross_Weight' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;Kg								   
               </td>
                    	
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车过程检查</td>
							<td width='35%' align='center'>							
								<input type='text' name='Pro_Check' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;&nbsp;&nbsp;
									
							</td>
							<td width='15%' align='center'>装车皮重</td>
							<td width='35%' align='center'>
								<input type='text' name='Tear_Weight' style='width:80%;height:18px;' value='' maxlength='20' onblur="Sum1(this.value)" onkeydown="changeEnter()">&nbsp;&nbsp;Kg
								
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后检查</td>
							<td width='35%' align='center'>							
								<input type='text' name='Lat_Check' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;&nbsp;&nbsp;
									
							</td>
							<td width='15%' align='center'>装车净重</td>
							<td width='35%' align='center'>
								<input type='text' name='Ture_Weight' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" readonly> &nbsp;&nbsp;Kg
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前罐容</td>
							<td width='35%' align='center'>							
								<input type='text' name='Pre_Tank_V' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;&nbsp;L								
							</td>
							<td width='15%' align='center'>运输车牌</td>
							<td width='35%' align='center'>							
								<select name="Car_Id" style="width:46%;height:20px" onkeydown="changeEnter()">
									<option value='-1'>=========广东省(粤)=========</option>
									<option value='粤A-'>粤A-</option>
									<option value='粤B-'>粤B-</option>
									<option value='粤C-' selected>粤C-</option>
									<option value='粤D-'>粤D-</option>
									<option value='粤E-'>粤E-</option>
									<option value='粤F-'>粤F-</option>
									<option value='粤G-'>粤G-</option>
									<option value='粤H-'>粤H-</option>
									<option value='粤J-'>粤J-</option>
									<option value='粤K-'>粤K-</option>
									<option value='粤L-'>粤L-</option>
									<option value='粤M-'>粤M-</option>
									<option value='粤N-'>粤N-</option>
									<option value='粤P-'>粤P-</option>
									<option value='粤Q-'>粤Q-</option>
									<option value='粤R-'>粤R-</option>
									<option value='粤S-'>粤S-</option>
									<option value='粤T-'>粤T-</option>
									<option value='粤U-'>粤U-</option>
									<option value='粤V-'>粤V-</option>
									<option value='-1'>=========河北省(冀)=========</option>
									<option value='冀A-'>冀A-</option>
									<option value='冀B-'>冀B-</option>
									<option value='冀C-'>冀C-</option>
									<option value='冀D-'>冀D-</option>
									<option value='冀E-'>冀E-</option>
									<option value='冀F-'>冀F-</option>
									<option value='冀G-'>冀G-</option>
									<option value='冀H-'>冀H-</option>
									<option value='冀J-'>冀J-</option>
									<option value='冀K-'>冀K-</option>
									<option value='冀L-'>冀L-</option>
									<option value='冀N-'>冀N-</option>
									<option value='冀P-'>冀P-</option>
									<option value='冀Q-'>冀Q-</option>
									<option value='冀R-'>冀R-</option>
									<option value='冀S-'>冀S-</option>
									<option value='冀T-'>冀T-</option>
									<option value='-1'>=========河南省(豫)=========</option>
									<option value='豫A-'>豫A-</option>
									<option value='豫B-'>豫B-</option>
									<option value='豫C-'>豫C-</option>
									<option value='豫D-'>豫D-</option>
									<option value='豫E-'>豫E-</option>
									<option value='豫F-'>豫F-</option>
									<option value='豫G-'>豫G-</option>
									<option value='豫H-'>豫H-</option>
									<option value='豫J-'>豫J-</option>
									<option value='豫K-'>豫K-</option>
									<option value='豫L-'>豫L-</option>
									<option value='豫M-'>豫M-</option>
									<option value='豫N-'>豫N-</option>
									<option value='豫P-'>豫P-</option>
									<option value='豫Q-'>豫Q-</option>
									<option value='豫R-'>豫R-</option>
									<option value='豫S-'>豫S-</option>
									<option value='-1'>=========云南省(云)=========</option>
									<option value='云A-'>云A-</option>
									<option value='云B-'>云B-</option>
									<option value='云C-'>云C-</option>
									<option value='云D-'>云D-</option>
									<option value='云E-'>云E-</option>
									<option value='云F-'>云F-</option>
									<option value='云G-'>云G-</option>
									<option value='云H-'>云H-</option>
									<option value='云J-'>云J-</option>
									<option value='云K-'>云K-</option>
									<option value='云L-'>云L-</option>
									<option value='云M-'>云M-</option>
									<option value='云N-'>云N-</option>
									<option value='云P-'>云P-</option>
									<option value='云Q-'>云Q-</option>
									<option value='云R-'>云R-</option>
									<option value='云S-'>云S-</option>								
									<option value='-1'>=========辽宁省(辽)=========</option>
									<option value='辽A-'>辽A-</option>
									<option value='辽B-'>辽B-</option>
									<option value='辽C-'>辽C-</option>
									<option value='辽D-'>辽D-</option>
									<option value='辽E-'>辽E-</option>
									<option value='辽F-'>辽F-</option>
									<option value='辽G-'>辽G-</option>
									<option value='辽H-'>辽H-</option>
									<option value='辽J-'>辽J-</option>
									<option value='辽K-'>辽K-</option>
									<option value='辽L-'>辽L-</option>
									<option value='辽M-'>辽M-</option>
									<option value='辽N-'>辽N-</option>
									<option value='辽P-'>辽P-</option>
									<option value='-1'>=========黑龙江省(黑)=========</option>
									<option value='黑A-'>黑A-</option>
									<option value='黑B-'>黑B-</option>
									<option value='黑C-'>黑C-</option>
									<option value='黑D-'>黑D-</option>
									<option value='黑E-'>黑E-</option>
									<option value='黑F-'>黑F-</option>
									<option value='黑G-'>黑G-</option>
									<option value='黑H-'>黑H-</option>
									<option value='黑J-'>黑J-</option>
									<option value='黑K-'>黑K-</option>
									<option value='黑L-'>黑L-</option>
									<option value='黑M-'>黑M-</option>
									<option value='黑N-'>黑N-</option>
									<option value='黑P-'>黑P-</option>
									<option value='-1'>=========湖南省(湘)=========</option>
									<option value='湘A-'>湘A-</option>
									<option value='湘B-'>湘B-</option>
									<option value='湘C-'>湘C-</option>
									<option value='湘D-'>湘D-</option>
									<option value='湘E-'>湘E-</option>
									<option value='湘F-'>湘F-</option>
									<option value='湘G-'>湘G-</option>
									<option value='湘H-'>湘H-</option>
									<option value='湘J-'>湘J-</option>
									<option value='湘K-'>湘K-</option>
									<option value='湘L-'>湘L-</option>
									<option value='湘M-'>湘M-</option>
									<option value='湘N-'>湘N-</option>
									<option value='湘P-'>湘P-</option>
									<option value='-1'>=========安徽省(皖)=========</option>
									<option value='皖A-'>皖A-</option>
									<option value='皖B-'>皖B-</option>
									<option value='皖C-'>皖C-</option>
									<option value='皖D-'>皖D-</option>
									<option value='皖E-'>皖E-</option>
									<option value='皖F-'>皖F-</option>
									<option value='皖G-'>皖G-</option>
									<option value='皖H-'>皖H-</option>
									<option value='皖J-'>皖J-</option>
									<option value='皖K-'>皖K-</option>
									<option value='皖L-'>皖L-</option>
									<option value='皖M-'>皖M-</option>
									<option value='皖N-'>皖N-</option>
									<option value='皖P-'>皖P-</option>
									<option value='皖Q-'>皖Q-</option>
									<option value='皖R-'>皖R-</option>
									<option value='-1'>=========山东省(鲁)=========</option>
									<option value='鲁A-'>鲁A-</option>
									<option value='鲁B-'>鲁B-</option>
									<option value='鲁C-'>鲁C-</option>
									<option value='鲁D-'>鲁D-</option>
									<option value='鲁E-'>鲁E-</option>
									<option value='鲁F-'>鲁F-</option>
									<option value='鲁G-'>鲁G-</option>
									<option value='鲁H-'>鲁H-</option>
									<option value='鲁J-'>鲁J-</option>
									<option value='鲁K-'>鲁K-</option>
									<option value='鲁L-'>鲁L-</option>
									<option value='鲁M-'>鲁M-</option>
									<option value='鲁N-'>鲁N-</option>
									<option value='鲁P-'>鲁P-</option>
									<option value='鲁Q-'>鲁Q-</option>
									<option value='鲁R-'>鲁R-</option>
									<option value='鲁S-'>鲁S-</option>
									<option value='-1'>=========新疆维吾尔(新)=========</option>
									<option value='新A-'>新A-</option>
									<option value='新B-'>新B-</option>
									<option value='新C-'>新C-</option>
									<option value='新D-'>新D-</option>
									<option value='新E-'>新E-</option>
									<option value='新F-'>新F-</option>
									<option value='新G-'>新G-</option>
									<option value='新H-'>新H-</option>
									<option value='新J-'>新J-</option>
									<option value='新K-'>新K-</option>
									<option value='新L-'>新L-</option>
									<option value='新M-'>新M-</option>
									<option value='新N-'>新N-</option>
									<option value='新P-'>新P-</option>
									<option value='新Q-'>新Q-</option>
									<option value='新R-'>新R-</option>
									<option value='-1'>=========江苏省(苏)=========</option>
									<option value='苏A-'>苏A-</option>
									<option value='苏B-'>苏B-</option>
									<option value='苏C-'>苏C-</option>
									<option value='苏D-'>苏D-</option>
									<option value='苏E-'>苏E-</option>
									<option value='苏F-'>苏F-</option>
									<option value='苏G-'>苏G-</option>
									<option value='苏H-'>苏H-</option>
									<option value='苏J-'>苏J-</option>
									<option value='苏K-'>苏K-</option>
									<option value='苏L-'>苏L-</option>
									<option value='-1'>=========浙江省(浙)=========</option>
									<option value='浙A-'>浙A-</option>
									<option value='浙B-'>浙B-</option>
									<option value='浙C-'>浙C-</option>
									<option value='浙D-'>浙D-</option>
									<option value='浙E-'>浙E-</option>
									<option value='浙F-'>浙F-</option>
									<option value='浙G-'>浙G-</option>
									<option value='浙H-'>浙H-</option>
									<option value='浙J-'>浙J-</option>
									<option value='浙K-'>浙K-</option>
									<option value='浙L-'>浙L-</option>
									<option value='-1'>=========江西省(赣)=========</option>
									<option value='赣A-'>赣A-</option>
									<option value='赣B-'>赣B-</option>
									<option value='赣C-'>赣C-</option>
									<option value='赣D-'>赣D-</option>
									<option value='赣E-'>赣E-</option>
									<option value='赣F-'>赣F-</option>
									<option value='赣G-'>赣G-</option>
									<option value='赣H-'>赣H-</option>
									<option value='赣J-'>赣J-</option>
									<option value='赣K-'>赣K-</option>
									<option value='赣L-'>赣L-</option>
									<option value='-1'>=========湖北省(鄂)=========</option>
									<option value='鄂A-'>鄂A-</option>
									<option value='鄂B-'>鄂B-</option>
									<option value='鄂C-'>鄂C-</option>
									<option value='鄂D-'>鄂D-</option>
									<option value='鄂E-'>鄂E-</option>
									<option value='鄂F-'>鄂F-</option>
									<option value='鄂G-'>鄂G-</option>
									<option value='鄂H-'>鄂H-</option>
									<option value='鄂J-'>鄂J-</option>
									<option value='鄂K-'>鄂K-</option>
									<option value='鄂L-'>鄂L-</option>
									<option value='鄂M-'>鄂M-</option>
									<option value='鄂N-'>鄂N-</option>
									<option value='鄂P-'>鄂P-</option>
									<option value='鄂Q-'>鄂Q-</option>
									<option value='-1'>=========广西壮族(桂)=========</option>
									<option value='桂A-'>桂A-</option>
									<option value='桂B-'>桂B-</option>
									<option value='桂C-'>桂C-</option>
									<option value='桂D-'>桂D-</option>
									<option value='桂E-'>桂E-</option>
									<option value='桂F-'>桂F-</option>
									<option value='桂G-'>桂G-</option>
									<option value='桂H-'>桂H-</option>
									<option value='桂J-'>桂J-</option>
									<option value='桂K-'>桂K-</option>
									<option value='桂L-'>桂L-</option>
									<option value='桂M-'>桂M-</option>
									<option value='桂N-'>桂N-</option>
									<option value='桂P-'>桂P-</option>
									<option value='-1'>=========甘肃省(甘)=========</option>
									<option value='甘A-'>甘A-</option>
									<option value='甘B-'>甘B-</option>
									<option value='甘C-'>甘C-</option>
									<option value='甘D-'>甘D-</option>
									<option value='甘E-'>甘E-</option>
									<option value='甘F-'>甘F-</option>
									<option value='甘G-'>甘G-</option>
									<option value='甘H-'>甘H-</option>
									<option value='甘J-'>甘J-</option>
									<option value='甘K-'>甘K-</option>
									<option value='甘L-'>甘L-</option>
									<option value='甘M-'>甘M-</option>
									<option value='甘N-'>甘N-</option>
									<option value='甘P-'>甘P-</option>
									<option value='-1'>=========山西省(晋)=========</option>
									<option value='晋A-'>晋A-</option>
									<option value='晋B-'>晋B-</option>
									<option value='晋C-'>晋C-</option>
									<option value='晋D-'>晋D-</option>
									<option value='晋E-'>晋E-</option>
									<option value='晋F-'>晋F-</option>
									<option value='晋G-'>晋G-</option>
									<option value='晋H-'>晋H-</option>
									<option value='晋J-'>晋J-</option>
									<option value='晋K-'>晋K-</option>
									<option value='晋L-'>晋L-</option>
									<option value='晋M-'>晋M-</option>
									<option value='-1'>=========内蒙古(蒙)=========</option>
									<option value='蒙A-'>蒙A-</option>
									<option value='蒙B-'>蒙B-</option>
									<option value='蒙C-'>蒙C-</option>
									<option value='蒙D-'>蒙D-</option>
									<option value='蒙E-'>蒙E-</option>
									<option value='蒙F-'>蒙F-</option>
									<option value='蒙G-'>蒙G-</option>
									<option value='蒙H-'>蒙H-</option>
									<option value='蒙J-'>蒙J-</option>
									<option value='蒙K-'>蒙K-</option>
									<option value='蒙L-'>蒙L-</option>
									<option value='-1'>=========陕西省(陕)=========</option>
									<option value='陕A-'>陕A-</option>
									<option value='陕B-'>陕B-</option>
									<option value='陕C-'>陕C-</option>
									<option value='陕D-'>陕D-</option>
									<option value='陕E-'>陕E-</option>
									<option value='陕F-'>陕F-</option>
									<option value='陕G-'>陕G-</option>
									<option value='陕H-'>陕H-</option>
									<option value='陕J-'>陕J-</option>
									<option value='陕K-'>陕K-</option>
									<option value='-1'>=========吉林省(吉)=========</option>
									<option value='吉A-'>吉A-</option>
									<option value='吉B-'>吉B-</option>
									<option value='吉C-'>吉C-</option>
									<option value='吉D-'>吉D-</option>
									<option value='吉E-'>吉E-</option>
									<option value='吉F-'>吉F-</option>
									<option value='吉G-'>吉G-</option>
									<option value='吉H-'>吉H-</option>
									<option value='-1'>=========福建省(闽)=========</option>
									<option value='闽A-'>闽A-</option>
									<option value='闽B-'>闽B-</option>
									<option value='闽C-'>闽C-</option>
									<option value='闽D-'>闽D-</option>
									<option value='闽E-'>闽E-</option>
									<option value='闽F-'>闽F-</option>
									<option value='闽G-'>闽G-</option>
									<option value='闽H-'>闽H-</option>
									<option value='闽J-'>闽J-</option>
									<option value='-1'>=========贵州省(贵)=========</option>
									<option value='贵A-'>贵A-</option>
									<option value='贵B-'>贵B-</option>
									<option value='贵C-'>贵C-</option>
									<option value='贵D-'>贵D-</option>
									<option value='贵E-'>贵E-</option>
									<option value='贵F-'>贵F-</option>
									<option value='贵G-'>贵G-</option>
									<option value='贵H-'>贵H-</option>
									<option value='贵J-'>贵J-</option>
									<option value='-1'>=========四川省(川)=========</option>
									<option value='川A-'>川A-</option>
									<option value='川C-'>川C-</option>
									<option value='川D-'>川D-</option>
									<option value='川E-'>川E-</option>
									<option value='川F-'>川F-</option>
									<option value='川G-'>川G-</option>
									<option value='川H-'>川H-</option>
									<option value='川J-'>川J-</option>
									<option value='川K-'>川K-</option>
									<option value='川L-'>川L-</option>
									<option value='川M-'>川M-</option>
									<option value='川N-'>川N-</option>
									<option value='川P-'>川P-</option>
									<option value='川Q-'>川Q-</option>
									<option value='川R-'>川R-</option>
									<option value='川S-'>川S-</option>
									<option value='川T-'>川T-</option>
									<option value='川U-'>川U-</option>
									<option value='川V-'>川V-</option>
									<option value='川W-'>川W-</option>
									<option value='-1'>=========青海省(青)=========</option>
									<option value='青A-'>青A-</option>
									<option value='青B-'>青B-</option>
									<option value='青C-'>青C-</option>
									<option value='青D-'>青D-</option>
									<option value='青E-'>青E-</option>
									<option value='青F-'>青F-</option>
									<option value='青G-'>青G-</option>
									<option value='青H-'>青H-</option>
									<option value='-1'>=========西藏(藏)=========</option>
									<option value='藏A-'>藏A-</option>
									<option value='藏B-'>藏B-</option>
									<option value='藏C-'>藏C-</option>
									<option value='藏D-'>藏D-</option>
									<option value='藏E-'>藏E-</option>
									<option value='藏F-'>藏F-</option>
									<option value='藏G-'>藏G-</option>
									<option value='-1'>=========海南省(琼)=========</option>
									<option value='琼A-'>琼A-</option>
									<option value='琼B-'>琼B-</option>
									<option value='琼C-'>琼C-</option>
									<option value='-1'>=========宁夏回族(宁)=========</option>
									<option value='宁A-'>宁A-</option>
									<option value='宁B-'>宁B-</option>
									<option value='宁C-'>宁C-</option>
									<option value='宁D-'>宁D-</option>
									<option value='-1'>=========重庆市(渝)=========</option>
									<option value='渝A-'>渝A-</option>
									<option value='渝B-'>渝B-</option>
									<option value='渝C-'>渝C-</option>
									<option value='渝F-'>渝F-</option>
									<option value='渝G-'>渝G-</option>
									<option value='渝H-'>渝H-</option>
									<option value='-1'>=========北京市(京)=========</option>
									<option value='京A-'>京A-</option>
									<option value='京B-'>京B-</option>
									<option value='京C-'>京C-</option>
									<option value='京D-'>京D-</option>
									<option value='-1'>=========天津市(津)=========</option>
									<option value='津A-'>津A-</option>
									<option value='津C-'>津C-</option>
									<option value='津D-'>津D-</option>
									<option value='津E-'>津E-</option>
									<option value='-1'>=========上海市(沪)=========</option>
									<option value='沪A-'>沪A-</option>
									<option value='沪B-'>沪B-</option>
									<option value='沪C-'>沪C-</option>
									<option value='沪D-'>沪D-</option>				
								</select>
								<input type='text' name='Car_Id_Num' style='width:46%;height:16px;' value='' maxlength='5' onkeydown="changeEnter()">
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后罐容</td>
							<td width='35%' align='center'>							
								<input type='text' name='Lat_Tank_V' style='width:80%;height:18px;' value='' maxlength='20' onblur="Sum2(this.value)" onkeydown="changeEnter()">&nbsp;&nbsp;&nbsp;L
									
							</td>
							<td width='15%' align='center'>挂车车号</td>
							<td width='35%' align='center'>
								<select name="Trailer_No" style="width:46%;height:20px" onkeydown="changeEnter()">
									<option value='-1'>=========广东省(粤)=========</option>
									<option value='粤A-'>粤A-</option>
									<option value='粤B-'>粤B-</option>
									<option value='粤C-' selected>粤C-</option>
									<option value='粤D-'>粤D-</option>
									<option value='粤E-'>粤E-</option>
									<option value='粤F-'>粤F-</option>
									<option value='粤G-'>粤G-</option>
									<option value='粤H-'>粤H-</option>
									<option value='粤J-'>粤J-</option>
									<option value='粤K-'>粤K-</option>
									<option value='粤L-'>粤L-</option>
									<option value='粤M-'>粤M-</option>
									<option value='粤N-'>粤N-</option>
									<option value='粤P-'>粤P-</option>
									<option value='粤Q-'>粤Q-</option>
									<option value='粤R-'>粤R-</option>
									<option value='粤S-'>粤S-</option>
									<option value='粤T-'>粤T-</option>
									<option value='粤U-'>粤U-</option>
									<option value='粤V-'>粤V-</option>
									<option value='-1'>=========河北省(冀)=========</option>
									<option value='冀A-'>冀A-</option>
									<option value='冀B-'>冀B-</option>
									<option value='冀C-'>冀C-</option>
									<option value='冀D-'>冀D-</option>
									<option value='冀E-'>冀E-</option>
									<option value='冀F-'>冀F-</option>
									<option value='冀G-'>冀G-</option>
									<option value='冀H-'>冀H-</option>
									<option value='冀J-'>冀J-</option>
									<option value='冀K-'>冀K-</option>
									<option value='冀L-'>冀L-</option>
									<option value='冀N-'>冀N-</option>
									<option value='冀P-'>冀P-</option>
									<option value='冀Q-'>冀Q-</option>
									<option value='冀R-'>冀R-</option>
									<option value='冀S-'>冀S-</option>
									<option value='冀T-'>冀T-</option>
									<option value='-1'>=========河南省(豫)=========</option>
									<option value='豫A-'>豫A-</option>
									<option value='豫B-'>豫B-</option>
									<option value='豫C-'>豫C-</option>
									<option value='豫D-'>豫D-</option>
									<option value='豫E-'>豫E-</option>
									<option value='豫F-'>豫F-</option>
									<option value='豫G-'>豫G-</option>
									<option value='豫H-'>豫H-</option>
									<option value='豫J-'>豫J-</option>
									<option value='豫K-'>豫K-</option>
									<option value='豫L-'>豫L-</option>
									<option value='豫M-'>豫M-</option>
									<option value='豫N-'>豫N-</option>
									<option value='豫P-'>豫P-</option>
									<option value='豫Q-'>豫Q-</option>
									<option value='豫R-'>豫R-</option>
									<option value='豫S-'>豫S-</option>
									<option value='-1'>=========云南省(云)=========</option>
									<option value='云A-'>云A-</option>
									<option value='云B-'>云B-</option>
									<option value='云C-'>云C-</option>
									<option value='云D-'>云D-</option>
									<option value='云E-'>云E-</option>
									<option value='云F-'>云F-</option>
									<option value='云G-'>云G-</option>
									<option value='云H-'>云H-</option>
									<option value='云J-'>云J-</option>
									<option value='云K-'>云K-</option>
									<option value='云L-'>云L-</option>
									<option value='云M-'>云M-</option>
									<option value='云N-'>云N-</option>
									<option value='云P-'>云P-</option>
									<option value='云Q-'>云Q-</option>
									<option value='云R-'>云R-</option>
									<option value='云S-'>云S-</option>								
									<option value='-1'>=========辽宁省(辽)=========</option>
									<option value='辽A-'>辽A-</option>
									<option value='辽B-'>辽B-</option>
									<option value='辽C-'>辽C-</option>
									<option value='辽D-'>辽D-</option>
									<option value='辽E-'>辽E-</option>
									<option value='辽F-'>辽F-</option>
									<option value='辽G-'>辽G-</option>
									<option value='辽H-'>辽H-</option>
									<option value='辽J-'>辽J-</option>
									<option value='辽K-'>辽K-</option>
									<option value='辽L-'>辽L-</option>
									<option value='辽M-'>辽M-</option>
									<option value='辽N-'>辽N-</option>
									<option value='辽P-'>辽P-</option>
									<option value='-1'>=========黑龙江省(黑)=========</option>
									<option value='黑A-'>黑A-</option>
									<option value='黑B-'>黑B-</option>
									<option value='黑C-'>黑C-</option>
									<option value='黑D-'>黑D-</option>
									<option value='黑E-'>黑E-</option>
									<option value='黑F-'>黑F-</option>
									<option value='黑G-'>黑G-</option>
									<option value='黑H-'>黑H-</option>
									<option value='黑J-'>黑J-</option>
									<option value='黑K-'>黑K-</option>
									<option value='黑L-'>黑L-</option>
									<option value='黑M-'>黑M-</option>
									<option value='黑N-'>黑N-</option>
									<option value='黑P-'>黑P-</option>
									<option value='-1'>=========湖南省(湘)=========</option>
									<option value='湘A-'>湘A-</option>
									<option value='湘B-'>湘B-</option>
									<option value='湘C-'>湘C-</option>
									<option value='湘D-'>湘D-</option>
									<option value='湘E-'>湘E-</option>
									<option value='湘F-'>湘F-</option>
									<option value='湘G-'>湘G-</option>
									<option value='湘H-'>湘H-</option>
									<option value='湘J-'>湘J-</option>
									<option value='湘K-'>湘K-</option>
									<option value='湘L-'>湘L-</option>
									<option value='湘M-'>湘M-</option>
									<option value='湘N-'>湘N-</option>
									<option value='湘P-'>湘P-</option>
									<option value='-1'>=========安徽省(皖)=========</option>
									<option value='皖A-'>皖A-</option>
									<option value='皖B-'>皖B-</option>
									<option value='皖C-'>皖C-</option>
									<option value='皖D-'>皖D-</option>
									<option value='皖E-'>皖E-</option>
									<option value='皖F-'>皖F-</option>
									<option value='皖G-'>皖G-</option>
									<option value='皖H-'>皖H-</option>
									<option value='皖J-'>皖J-</option>
									<option value='皖K-'>皖K-</option>
									<option value='皖L-'>皖L-</option>
									<option value='皖M-'>皖M-</option>
									<option value='皖N-'>皖N-</option>
									<option value='皖P-'>皖P-</option>
									<option value='皖Q-'>皖Q-</option>
									<option value='皖R-'>皖R-</option>
									<option value='-1'>=========山东省(鲁)=========</option>
									<option value='鲁A-'>鲁A-</option>
									<option value='鲁B-'>鲁B-</option>
									<option value='鲁C-'>鲁C-</option>
									<option value='鲁D-'>鲁D-</option>
									<option value='鲁E-'>鲁E-</option>
									<option value='鲁F-'>鲁F-</option>
									<option value='鲁G-'>鲁G-</option>
									<option value='鲁H-'>鲁H-</option>
									<option value='鲁J-'>鲁J-</option>
									<option value='鲁K-'>鲁K-</option>
									<option value='鲁L-'>鲁L-</option>
									<option value='鲁M-'>鲁M-</option>
									<option value='鲁N-'>鲁N-</option>
									<option value='鲁P-'>鲁P-</option>
									<option value='鲁Q-'>鲁Q-</option>
									<option value='鲁R-'>鲁R-</option>
									<option value='鲁S-'>鲁S-</option>
									<option value='-1'>=========新疆维吾尔(新)=========</option>
									<option value='新A-'>新A-</option>
									<option value='新B-'>新B-</option>
									<option value='新C-'>新C-</option>
									<option value='新D-'>新D-</option>
									<option value='新E-'>新E-</option>
									<option value='新F-'>新F-</option>
									<option value='新G-'>新G-</option>
									<option value='新H-'>新H-</option>
									<option value='新J-'>新J-</option>
									<option value='新K-'>新K-</option>
									<option value='新L-'>新L-</option>
									<option value='新M-'>新M-</option>
									<option value='新N-'>新N-</option>
									<option value='新P-'>新P-</option>
									<option value='新Q-'>新Q-</option>
									<option value='新R-'>新R-</option>
									<option value='-1'>=========江苏省(苏)=========</option>
									<option value='苏A-'>苏A-</option>
									<option value='苏B-'>苏B-</option>
									<option value='苏C-'>苏C-</option>
									<option value='苏D-'>苏D-</option>
									<option value='苏E-'>苏E-</option>
									<option value='苏F-'>苏F-</option>
									<option value='苏G-'>苏G-</option>
									<option value='苏H-'>苏H-</option>
									<option value='苏J-'>苏J-</option>
									<option value='苏K-'>苏K-</option>
									<option value='苏L-'>苏L-</option>
									<option value='-1'>=========浙江省(浙)=========</option>
									<option value='浙A-'>浙A-</option>
									<option value='浙B-'>浙B-</option>
									<option value='浙C-'>浙C-</option>
									<option value='浙D-'>浙D-</option>
									<option value='浙E-'>浙E-</option>
									<option value='浙F-'>浙F-</option>
									<option value='浙G-'>浙G-</option>
									<option value='浙H-'>浙H-</option>
									<option value='浙J-'>浙J-</option>
									<option value='浙K-'>浙K-</option>
									<option value='浙L-'>浙L-</option>
									<option value='-1'>=========江西省(赣)=========</option>
									<option value='赣A-'>赣A-</option>
									<option value='赣B-'>赣B-</option>
									<option value='赣C-'>赣C-</option>
									<option value='赣D-'>赣D-</option>
									<option value='赣E-'>赣E-</option>
									<option value='赣F-'>赣F-</option>
									<option value='赣G-'>赣G-</option>
									<option value='赣H-'>赣H-</option>
									<option value='赣J-'>赣J-</option>
									<option value='赣K-'>赣K-</option>
									<option value='赣L-'>赣L-</option>
									<option value='-1'>=========湖北省(鄂)=========</option>
									<option value='鄂A-'>鄂A-</option>
									<option value='鄂B-'>鄂B-</option>
									<option value='鄂C-'>鄂C-</option>
									<option value='鄂D-'>鄂D-</option>
									<option value='鄂E-'>鄂E-</option>
									<option value='鄂F-'>鄂F-</option>
									<option value='鄂G-'>鄂G-</option>
									<option value='鄂H-'>鄂H-</option>
									<option value='鄂J-'>鄂J-</option>
									<option value='鄂K-'>鄂K-</option>
									<option value='鄂L-'>鄂L-</option>
									<option value='鄂M-'>鄂M-</option>
									<option value='鄂N-'>鄂N-</option>
									<option value='鄂P-'>鄂P-</option>
									<option value='鄂Q-'>鄂Q-</option>
									<option value='-1'>=========广西壮族(桂)=========</option>
									<option value='桂A-'>桂A-</option>
									<option value='桂B-'>桂B-</option>
									<option value='桂C-'>桂C-</option>
									<option value='桂D-'>桂D-</option>
									<option value='桂E-'>桂E-</option>
									<option value='桂F-'>桂F-</option>
									<option value='桂G-'>桂G-</option>
									<option value='桂H-'>桂H-</option>
									<option value='桂J-'>桂J-</option>
									<option value='桂K-'>桂K-</option>
									<option value='桂L-'>桂L-</option>
									<option value='桂M-'>桂M-</option>
									<option value='桂N-'>桂N-</option>
									<option value='桂P-'>桂P-</option>
									<option value='-1'>=========甘肃省(甘)=========</option>
									<option value='甘A-'>甘A-</option>
									<option value='甘B-'>甘B-</option>
									<option value='甘C-'>甘C-</option>
									<option value='甘D-'>甘D-</option>
									<option value='甘E-'>甘E-</option>
									<option value='甘F-'>甘F-</option>
									<option value='甘G-'>甘G-</option>
									<option value='甘H-'>甘H-</option>
									<option value='甘J-'>甘J-</option>
									<option value='甘K-'>甘K-</option>
									<option value='甘L-'>甘L-</option>
									<option value='甘M-'>甘M-</option>
									<option value='甘N-'>甘N-</option>
									<option value='甘P-'>甘P-</option>
									<option value='-1'>=========山西省(晋)=========</option>
									<option value='晋A-'>晋A-</option>
									<option value='晋B-'>晋B-</option>
									<option value='晋C-'>晋C-</option>
									<option value='晋D-'>晋D-</option>
									<option value='晋E-'>晋E-</option>
									<option value='晋F-'>晋F-</option>
									<option value='晋G-'>晋G-</option>
									<option value='晋H-'>晋H-</option>
									<option value='晋J-'>晋J-</option>
									<option value='晋K-'>晋K-</option>
									<option value='晋L-'>晋L-</option>
									<option value='晋M-'>晋M-</option>
									<option value='-1'>=========内蒙古(蒙)=========</option>
									<option value='蒙A-'>蒙A-</option>
									<option value='蒙B-'>蒙B-</option>
									<option value='蒙C-'>蒙C-</option>
									<option value='蒙D-'>蒙D-</option>
									<option value='蒙E-'>蒙E-</option>
									<option value='蒙F-'>蒙F-</option>
									<option value='蒙G-'>蒙G-</option>
									<option value='蒙H-'>蒙H-</option>
									<option value='蒙J-'>蒙J-</option>
									<option value='蒙K-'>蒙K-</option>
									<option value='蒙L-'>蒙L-</option>
									<option value='-1'>=========陕西省(陕)=========</option>
									<option value='陕A-'>陕A-</option>
									<option value='陕B-'>陕B-</option>
									<option value='陕C-'>陕C-</option>
									<option value='陕D-'>陕D-</option>
									<option value='陕E-'>陕E-</option>
									<option value='陕F-'>陕F-</option>
									<option value='陕G-'>陕G-</option>
									<option value='陕H-'>陕H-</option>
									<option value='陕J-'>陕J-</option>
									<option value='陕K-'>陕K-</option>
									<option value='-1'>=========吉林省(吉)=========</option>
									<option value='吉A-'>吉A-</option>
									<option value='吉B-'>吉B-</option>
									<option value='吉C-'>吉C-</option>
									<option value='吉D-'>吉D-</option>
									<option value='吉E-'>吉E-</option>
									<option value='吉F-'>吉F-</option>
									<option value='吉G-'>吉G-</option>
									<option value='吉H-'>吉H-</option>
									<option value='-1'>=========福建省(闽)=========</option>
									<option value='闽A-'>闽A-</option>
									<option value='闽B-'>闽B-</option>
									<option value='闽C-'>闽C-</option>
									<option value='闽D-'>闽D-</option>
									<option value='闽E-'>闽E-</option>
									<option value='闽F-'>闽F-</option>
									<option value='闽G-'>闽G-</option>
									<option value='闽H-'>闽H-</option>
									<option value='闽J-'>闽J-</option>
									<option value='-1'>=========贵州省(贵)=========</option>
									<option value='贵A-'>贵A-</option>
									<option value='贵B-'>贵B-</option>
									<option value='贵C-'>贵C-</option>
									<option value='贵D-'>贵D-</option>
									<option value='贵E-'>贵E-</option>
									<option value='贵F-'>贵F-</option>
									<option value='贵G-'>贵G-</option>
									<option value='贵H-'>贵H-</option>
									<option value='贵J-'>贵J-</option>
									<option value='-1'>=========四川省(川)=========</option>
									<option value='川A-'>川A-</option>
									<option value='川C-'>川C-</option>
									<option value='川D-'>川D-</option>
									<option value='川E-'>川E-</option>
									<option value='川F-'>川F-</option>
									<option value='川G-'>川G-</option>
									<option value='川H-'>川H-</option>
									<option value='川J-'>川J-</option>
									<option value='川K-'>川K-</option>
									<option value='川L-'>川L-</option>
									<option value='川M-'>川M-</option>
									<option value='川N-'>川N-</option>
									<option value='川P-'>川P-</option>
									<option value='川Q-'>川Q-</option>
									<option value='川R-'>川R-</option>
									<option value='川S-'>川S-</option>
									<option value='川T-'>川T-</option>
									<option value='川U-'>川U-</option>
									<option value='川V-'>川V-</option>
									<option value='川W-'>川W-</option>
									<option value='-1'>=========青海省(青)=========</option>
									<option value='青A-'>青A-</option>
									<option value='青B-'>青B-</option>
									<option value='青C-'>青C-</option>
									<option value='青D-'>青D-</option>
									<option value='青E-'>青E-</option>
									<option value='青F-'>青F-</option>
									<option value='青G-'>青G-</option>
									<option value='青H-'>青H-</option>
									<option value='-1'>=========西藏(藏)=========</option>
									<option value='藏A-'>藏A-</option>
									<option value='藏B-'>藏B-</option>
									<option value='藏C-'>藏C-</option>
									<option value='藏D-'>藏D-</option>
									<option value='藏E-'>藏E-</option>
									<option value='藏F-'>藏F-</option>
									<option value='藏G-'>藏G-</option>
									<option value='-1'>=========海南省(琼)=========</option>
									<option value='琼A-'>琼A-</option>
									<option value='琼B-'>琼B-</option>
									<option value='琼C-'>琼C-</option>
									<option value='-1'>=========宁夏回族(宁)=========</option>
									<option value='宁A-'>宁A-</option>
									<option value='宁B-'>宁B-</option>
									<option value='宁C-'>宁C-</option>
									<option value='宁D-'>宁D-</option>
									<option value='-1'>=========重庆市(渝)=========</option>
									<option value='渝A-'>渝A-</option>
									<option value='渝B-'>渝B-</option>
									<option value='渝C-'>渝C-</option>
									<option value='渝F-'>渝F-</option>
									<option value='渝G-'>渝G-</option>
									<option value='渝H-'>渝H-</option>
									<option value='-1'>=========北京市(京)=========</option>
									<option value='京A-'>京A-</option>
									<option value='京B-'>京B-</option>
									<option value='京C-'>京C-</option>
									<option value='京D-'>京D-</option>
									<option value='-1'>=========天津市(津)=========</option>
									<option value='津A-'>津A-</option>
									<option value='津C-'>津C-</option>
									<option value='津D-'>津D-</option>
									<option value='津E-'>津E-</option>
									<option value='-1'>=========上海市(沪)=========</option>
									<option value='沪A-'>沪A-</option>
									<option value='沪B-'>沪B-</option>
									<option value='沪C-'>沪C-</option>
									<option value='沪D-'>沪D-</option>						
								</select>
								<input type='text' name='Trailer_No_Num' style='width:46%;height:16px;' value='' maxlength='5' onkeydown="changeEnter()">
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸入容积</td>
							<td width='35%' align='center'>							
								<input type='text' name='Unload' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" readonly>&nbsp;&nbsp;&nbsp;L
									
							</td>
							<td width='15%' align='center'>承运单位</td>
							<td width='35%' align='center'>
								<input type='text' name='Car_Corp' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前温度</td>
							<td width='35%' align='center'>							
								<input type='text' name='Pre_Temper' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;℃
									
							</td>
							<td width='15%' align='center'>槽车司机</td>
							<td width='35%' align='center'>
								<input type='text' name='Car_Owner' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后温度</td>
							<td width='35%' align='center'>							
								<input type='text' name='Lat_Temper' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;℃
									
							</td>
							<td width='15%' align='center'>发货单位</td>
							<td width='35%' align='center'>
								<input type='text' name='Forward_Unit' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前压力</td>
							<td width='35%' align='center'>							
								<input type='text' name='Pre_Press' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">Mpa
									
							</td>
							<td width='15%' align='center'>燃料类型</td>
							<td width='35%' align='center'>
								<select id="Oil_CType" name="Oil_CType" style="width:80%;height:20px" onkeydown="changeEnter()">
								</select>
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后压力</td>
							<td width='35%' align='center'>							
								<input type='text' name='Lat_Press' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">MPa
									
							</td>
							<td width='15%' align='center'>气质报告单号</td>
							<td width='35%' align='center'>
								<input type='text' name='Temper_Report' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前重量</td>
							<td width='35%' align='center'>							
								<input type='text' name='Pre_Weight' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">&nbsp;&nbsp;Kg
									
							</td>
							<td width='15%' align='center'>到站时间</td>
							<td width='35%' align='center'>
								<input name='BDate_A' type='text' style='width:40%;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onkeydown="changeEnter()">
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
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后重量</td>
							<td width='35%' align='center'>							
								<input type='text' name='Lat_Weight' style='width:80%;height:18px;' value='' maxlength='20' onblur="Sum3(this.value)" onkeydown="changeEnter()">&nbsp;&nbsp;Kg
									
							</td>
							<td width='15%' align='center'>离站时间</td>
							<td width='35%' align='center'>
								<input name='BDate_D' type='text' style='width:40%;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onkeydown="changeEnter()">
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
							<td width='15%' align='center'>卸车重量</td>
							<td width='35%' align='center'>							
								<input type='text' name='Value' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" readonly>&nbsp;&nbsp;Kg
							</td>
							<td width='15%' align='center'>作业人员</td>
							<td width='35%' align='center'>							
								<input type='text' name='Worker' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()">
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
							<td width='85%' align='left' colspan='4'>
								<textarea name='Memo' rows='5' cols='80' maxlength=128></textarea>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<input name="Order_Value" type="hidden" value="1" />
</form>
</body>
<SCRIPT LANGUAGE=javascript>

function doNO()
{
	location = "Pro_I.jsp?Sid=<%=Sid%>";
}

function doChange(pId)
{
	//先删除
	var length = document.getElementById('Oil_CType').length;
	for(var i=0; i<length; i++)
	{
		document.getElementById('Oil_CType').remove(0);
	}
	
	//再添加
	if(pId.length > 0)
	{
		<%
		if(null != Pro_I_All)
		{
			Iterator bussiter = Pro_I_All.iterator();
			while(bussiter.hasNext())
			{
				ProRBean bussBean = (ProRBean)bussiter.next();
				String buss_cpmid = bussBean.getCpm_Id();
				String buss_oilid = bussBean.getOil_CType();
				String buss_oilname = "无";
				if(Oil_Info.trim().length() > 0)
				{
				  String[] List = Oil_Info.split(";");
				  for(int i=0; i<List.length && List[i].length()>0; i++)
				  {
				  	String[] subList = List[i].split(",");
				  	if(buss_oilid.equals(subList[0]))
				  	{
				  		buss_oilname = subList[1];
				  		break;
				  	}
				  }
				}
		%>
				
					var objOption = document.createElement('OPTION');
					objOption.value = '<%=buss_oilid%>';
					objOption.text  = '<%=buss_oilid%>' + '|' + '<%=buss_oilname%>';
					document.getElementById('Oil_CType').add(objOption);
				
		<%
			}
		}
		%>
	}
}
doChange(Pro_I_Add.Cpm_Id.value);

var reqAdd = null;
function doAdd() 
{
  if(Pro_I_Add.Cpm_Id.value.length < 1)
  {
  	alert('请选择卸车站点!');
  	return;
  }
  if(Pro_I_Add.Oil_CType.value.length < 1)
  {
  	alert('请选择燃料类型!');
  	return;
  }
  if(Pro_I_Add.Tank_No.value.length < 1)
  {
  	alert('请选择气罐编号');
  	return;
  }
  if(Pro_I_Add.Order_Id.value.Trim().length < 1)
  {
  	alert('请填写磅单编号!');
  	return;
  }

  if(Pro_I_Add.BDate_C.value.length < 1)
  {
  	alert('请选择卸车时间!');
  	return;
  }
  var TDay = new Date().format("yyyy-MM-dd");
	if(Pro_I_Add.BDate_C.value != TDay)
	{
		alert('只可记账当天流水!');
		return;
	}
 			
	if(Pro_I_Add.Car_Id.value.length < 1 || Pro_I_Add.Car_Id.value == '-1')
  {
  	alert('请选择运输车牌所在地!');
  	return;
  }
  if(Pro_I_Add.Car_Id_Num.value.Trim().length != 5)
  {
  	alert('车牌尾数填写有误!');
  	return;
  }
  if(Pro_I_Add.Trailer_No.value.length < 1 || Pro_I_Add.Trailer_No.value == '-1')
  {
  	alert('请选择运输车牌所在地!');
  	return;
  }
  
  if(Pro_I_Add.Trailer_No_Num.value.Trim().length != 5)
  {
  	alert('挂车车牌尾数填写有误!');
  	return;
  }
  if(Pro_I_Add.Car_Owner.value.Trim().length < 1)
  {
  	alert('请填写押运人员姓名!');
  	return;
  }
  if(Pro_I_Add.Car_Corp.value.Trim().length < 1)
  {
  	alert('请填写承运单位名称!');
  	return;
  }
  if(Pro_I_Add.Worker.value.Trim().length < 1)
  {
  	alert('请填写作业人员姓名!');
  	return;
  }

  if(Pro_I_Add.Tear_Weight.value.Trim().length < 1)
  {
  	alert('请填写皮重数量!');
  	return;
  }
  if(Pro_I_Add.Gross_Weight.value.Trim().length < 1)
  {
  	alert('请填写毛重数量!');
  	return;
  }
  if(Pro_I_Add.Ture_Weight.value.Trim().length < 1)
  {
  	alert('请填写净重数量!');
  	return;
  }

  if(confirm("信息无误,确定提交?"))
  {
  	if(window.XMLHttpRequest)
	  {
			reqAdd = new XMLHttpRequest();
		}
		else if(window.ActiveXObject)
		{
			reqAdd = new ActiveXObject("Microsoft.XMLHTTP");
		}		
		//设置回调函数
		reqAdd.onreadystatechange = function()
		{
			var state = reqAdd.readyState;
			if(state == 4)
			{
				if(reqAdd.status == 200)
				{
					var resp = reqAdd.responseText;			
					if(resp != null && resp.substring(0,4) == '0000')
					{
						alert('成功');
	
						Pro_I_Add.Order_Value.value = '1';
						Pro_I_Add.Value.value = '';
						Pro_I_Add.Gross_Weight.value = '';
						Pro_I_Add.Tear_Weight.value = '';
						Pro_I_Add.Ture_Weight.value = '';
						Pro_I_Add.Pre_Tank_V.value = '';
						Pro_I_Add.Lat_Tank_V.value = '';
						Pro_I_Add.Pre_Temper.value = '';
						Pro_I_Add.Lat_Temper.value = '';
						Pro_I_Add.Pre_Press.value = '';
						Pro_I_Add.Lat_Press.value = '';
						Pro_I_Add.Pre_Weight.value = '';
						Pro_I_Add.Lat_Weight.value = '';
						Pro_I_Add.Unload.value = '';
						
						return;
					}
					else
					{
						alert('失败，请重新操作');
						return;
					}
				}
				else
				{
					alert("失败，请重新操作");
					return;
				}
			}
		};
		var url = 'Pro_I_Add.do?Cmd=10&Sid=<%=Sid%>&Cpm_Id='+Pro_I_Add.Cpm_Id.value+'&Oil_CType='+Pro_I_Add.Oil_CType.value+'&Car_Id='+Pro_I_Add.Car_Id.value+Pro_I_Add.Car_Id_Num.value+'&Car_Owner='+Pro_I_Add.Car_Owner.value+'&Car_Corp='+Pro_I_Add.Car_Corp.value+'&CTime='+Pro_I_Add.BDate_C.value+' '+Pro_I_Add.Hour_C.value+':'+Pro_I_Add.Minute_C.value+':00'+'&Value='+Pro_I_Add.Value.value.Trim()+'&Memo='+Pro_I_Add.Memo.value+'&Order_Id='+Pro_I_Add.Order_Id.value+'&Order_Value=1&Worker='+Pro_I_Add.Worker.value+'&Pre_Check='+Pro_I_Add.Pre_Check.value+'&Pro_Check='+Pro_I_Add.Pro_Check.value+'&Lat_Check='+Pro_I_Add.Lat_Check.value+'&Pre_Tank_V='+Pro_I_Add.Pre_Tank_V.value+'&Lat_Tank_V='+Pro_I_Add.Lat_Tank_V.value+'&Pre_Temper='+Pro_I_Add.Pre_Temper.value+'&Lat_Temper='+Pro_I_Add.Lat_Temper.value+'&Pre_Press='+Pro_I_Add.Pre_Press.value+'&Lat_Press='+Pro_I_Add.Lat_Press.value+'&Pre_Weight='+Pro_I_Add.Pre_Weight.value+'&Lat_Weight='+Pro_I_Add.Lat_Weight.value+'&Gross_Weight='+Pro_I_Add.Gross_Weight.value+'&Tear_Weight='+Pro_I_Add.Tear_Weight.value+'&Ture_Weight='+Pro_I_Add.Ture_Weight.value+'&Trailer_No='+Pro_I_Add.Trailer_No.value+Pro_I_Add.Trailer_No_Num.value+'&Forward_Unit='+Pro_I_Add.Forward_Unit.value+'&Temper_Report='+Pro_I_Add.Temper_Report.value+'&Tank_No='+Pro_I_Add.Tank_No.value+'&Unload='+Pro_I_Add.Unload.value+'&Arrive_Time='+Pro_I_Add.BDate_A.value+' '+Pro_I_Add.Hour_A.value+':'+Pro_I_Add.Minute_A.value+':00'+'&Depart_Time='+Pro_I_Add.BDate_D.value+' '+Pro_I_Add.Hour_D.value+':'+Pro_I_Add.Minute_D.value+':00'+'&Operator=<%=Operator%>&Func_Corp_Id=<%=currStatus.getFunc_Corp_Id()%>&Func_Sub_Id=<%=currStatus.getFunc_Sub_Id()%>&Func_Type_Id=<%=currStatus.getFunc_Type_Id()%>&CurrPage=<%=currStatus.getCurrPage()%>&BTime=<%=currStatus.getVecDate().get(0).toString().substring(0,10)+" 00:00:00"%>&ETime=<%=currStatus.getVecDate().get(1).toString().substring(0,10)+" 23:59:59"%>&currtime='+new Date();
		reqAdd.open("post",url,true);
		reqAdd.send(null);
		return true;
  }
}

function Sum3()
{ 
	if(Pro_I_Add.Pre_Weight.value.length>0 && Pro_I_Add.Lat_Weight.value.length>0)
	{		
		var va = Pro_I_Add.Pre_Weight.value;
		var vb = Pro_I_Add.Lat_Weight.value;		
		Pro_I_Add.Value.value = parseInt(va)-parseInt(vb);
		
	}
}
	function Sum2()
{ 
	if(Pro_I_Add.Pre_Tank_V.value.length>0 && Pro_I_Add.Lat_Tank_V.value.length>0)
	{		
		var va = Pro_I_Add.Lat_Tank_V.value;
		var vb = Pro_I_Add.Pre_Tank_V.value;		
		Pro_I_Add.Unload.value = parseInt(va)-parseInt(vb);
		
	}
}
	function Sum1()
{ 
	if(Pro_I_Add.Gross_Weight.value.length>0 && Pro_I_Add.Tear_Weight.value.length>0)
	{		
		var va = Pro_I_Add.Gross_Weight.value;
		var vb = Pro_I_Add.Tear_Weight.value;		
		Pro_I_Add.Ture_Weight.value = parseInt(va)-parseInt(vb);
		
	}
	
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