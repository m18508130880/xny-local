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
	
	ArrayList Pro_R_Buss = (ArrayList)session.getAttribute("Pro_R_Buss_" + Sid);
	CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	String Oil_Info = "";
	String Car_Info = "";
	if(null != Corp_Info)
	{
		Oil_Info = Corp_Info.getOil_Info();
		Car_Info = Corp_Info.getCar_Info();
		if(null == Oil_Info){Oil_Info = "";}
		if(null == Car_Info){Car_Info = "";}
	}
	
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
  UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
  String Operator = UserInfo.getId();
  String Operator_Name = UserInfo.getCName();
  String ManageId = UserInfo.getManage_Role();
  
  
  ArrayList Pro_O = (ArrayList)session.getAttribute("Pro_O_" + Sid);
  ArrayList Pro_O_Corp = (ArrayList)session.getAttribute("Pro_O_Corp_" + Sid);
  String SN = request.getParameter("SN");
	String Cpm_Id  = "";
	String Cpm_Name = "";
	String Oil_CType = "";	
	String Oil_CName = "无";
	String CTime = "";
	String Memo = "";
	
	//加注信息
	String Value = "";
	String Value_Gas = "";
	String Price = "";
  String Amt = "";
	String Worker = "";
	
	//客户信息
	String Unq_Flag = "";
	String Unq_Str = "";
	String Car_More = "";
	String Car_CType = "";
	String Car_Owner = "";
	String Car_BH = "";
	String Car_DW = "";
	String DW_ID  = "";  
	
	//审核信息
	String Status = "";
	String Checker_Name = "";
	
	//单号
	String Fill_Number = "";
	
	
  if(Pro_O != null)
	{
		Iterator iterator = Pro_O.iterator();
		while(iterator.hasNext())
		{
			ProOBean Bean = (ProOBean)iterator.next();
			if(Bean.getSN().equals(SN))			
			{
			
			
				Cpm_Id    = Bean.getCpm_Id();
				
				if( null != User_Device_Detail)
						{
							Iterator Uiterator = User_Device_Detail.iterator();
							while(Uiterator.hasNext())
							{
								DeviceDetailBean sBean = (DeviceDetailBean)Uiterator.next();
								if(sBean.getId().equals(Cpm_Id))
								{
									Cpm_Name = sBean.getBrief();
								}
							}
						}

				
				Oil_CType = Bean.getOil_CType();
				
				if(Oil_Info.trim().length() > 0)
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
				
				CTime     = Bean.getCTime();
				Memo      = Bean.getMemo();
				
				
				Value     = Bean.getValue();
				Value_Gas = Bean.getValue_Gas();
				Price     = Bean.getPrice();
			  Amt       = Bean.getAmt();
				Worker    = Bean.getWorker();
				Cpm_Id    = Bean.getCpm_Id();
				
				Unq_Flag  = Bean.getUnq_Flag();
				Unq_Str   = Bean.getUnq_Str();
				Car_More  = Bean.getCar_More();
				Car_CType = Bean.getCar_CType();
				Car_Owner = Bean.getCar_Owner();
				Car_BH 	  = Bean.getCar_BH();
				Car_DW 	  = Bean.getCar_DW();
				DW_ID     = Bean.getDW_ID();
				
				Status        = Bean.getStatus();
				Checker_Name  = Bean.getChecker_Name();
				
				
				Fill_Number   = Bean.getFill_Number();
			}
		}
 	}
  
%>
<body style="background:#CADFFF">
<form name="Pro_O_Detail" action="Pro_O.do" method="post" target="mFrame">
<div id="down_bg_2">
	<div id="cap"><img src="../skin/images/pro_o_Detail.gif"></div><br><br>
	<div id="right_table_center">
		<table width="50%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
			<tr height='30'>
				<td width='100%' align='right'>
				
					<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doEdit()'>
					
					<img src="../skin/images/button10.gif"           style='cursor:hand;' onclick='doNO()'>
				</td>
			</tr>
			<tr height='30'>
				<td width='100%' align='center'>
					<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">		
	          <tr height='30'>
							<td width='25%' align='center'>加注站点</td>
							<td width='75%' align='left'><%=Cpm_Name%></td>
						<tr height='30'>
							<td width='25%' align='center'>燃料类型</td>
							<td width='75%' align='left'><%= Oil_CName%></td>
						<tr height='30'>
						 	<td width='25%' align='center'>加注时间</td>
						 	<td width='75%' align='left'>
						 		<input name='BDate' type='text' style='width:50%;height:20px;' value='<%=CTime.substring(0,10)%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
								<select name="Hour" style="width:20%;height:20px">
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
								<select name="Minute" style="width:25%;height:20px">
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
		       <!-- <tr height='30'>
							<td width='25%' align='center'>加注单价</td>
							<td width='75%' align='left'><%= Price%>元/L 或 元/kg</td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>加注金额</td>
							<td width='75%' align='left'><%= Amt%>元</td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>加注人员</td>
							<td width='75%' align='left'><%= Worker%></td>
						</tr>
						-->
						<tr height='30'>
							<td width='25%' align='center'>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
							<td width='75%' align='left'>
								<input type="text"  name='Fill_Number' style='width:280px;height:18px;' value='<%= Fill_Number%>' maxlength='10' >							
								</td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>加注数量</td>
							<td width='75%' align='left'>
								<input type="text"  name='Value' style='width:280px;height:18px;' value='<%= Value%>' maxlength='10' >kg						
								</td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'><%=Unq_Flag.equals("0")?"IC卡号":"车牌号"%></td>
							<td width='75%' align='left'>
								<input type="text"  name='Unq_Str' style='width:280px;height:18px;' value='<%= Unq_Str%>' maxlength='10' onblur='doDown(this.value)'  >									
							</td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>车载类型</td>
							<td id='T_Car' width='75%' align='left'>			
	<%
							String str1 = "";
							String str_ctype = "";
							if(null != Pro_O_Corp)
							{
								 Iterator iter = Pro_O_Corp.iterator();
								while(iter.hasNext())
								{
									CorpInfoBean corpBean = (CorpInfoBean)iter.next();	
									String[] strs = corpBean.getCar_Info().split(";");
									for(int j =0;j<strs.length&&strs[j].length()>0;j++)
									{
										String[] substr = strs[j].split(",");
										if(Car_CType.equals(substr[0]))
										{
											str1 = substr[1];
											str_ctype = substr[0];
											break;
										}
									}
								}
							}	
	%>																																										
								&nbsp;<%= str1%></td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>车辆司机</td>
							<td id='T_Owner' width='75%' align='left'>&nbsp;<%= Car_Owner%></td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>车载瓶号</td>
							<td id='T_BH' width='75%' align='left'>&nbsp;<%= Car_BH%></td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>所属单位</td>
							<td  id='T_DW' width='75%' align='left'>&nbsp;<%= Car_DW%></td>
						</tr>		
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<input name="SN" type="hidden" value="<%=SN%>" />
<input name="Cmd"      type="hidden" value="12">
<input name="Sid"      type="hidden" value="<%=Sid%>">
<input name="Cpm_Id"      type="hidden" value="<%=Cpm_Id%>">

<input name="CTime"      type="hidden" value="">
<input name="Car_CType" 			type="hidden" value="<%=str_ctype%>">
<input name="Car_CType_Name" 	type="hidden" value="">
<input name="Car_Owner" 			type="hidden" value="<%= Car_Owner%>">
<input name="Car_BH" 					type="hidden" value="<%= Car_BH%>">
<input name="Car_DW" 					type="hidden" value="<%= Car_DW%>">
<input name="DW_ID" 					type="hidden" value="<%=DW_ID%>">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doNO()
{
	location = "Pro_O.jsp?Sid=<%=Sid%>";
	
}
function doEdit()
{		
	Pro_O_Detail.CTime.value = Pro_O_Detail.BDate.value+' '+Pro_O_Detail.Hour.value+':'+Pro_O_Detail.Minute.value+':00';
		Pro_O_Detail.submit();
}

function doDown(cId)
{ 
	if(window.XMLHttpRequest)
	  {
			reqAdd = new XMLHttpRequest();
		}
		else if(window.ActiveXObject)
		{
			reqAdd = new ActiveXObject("Microsoft.XMLHTTP");
		}		
	reqAdd.onreadystatechange=function()
	{
					if(reqAdd.readyState == 4)
					{ 
						if(reqAdd.status == 200)
						{
							var txt = reqAdd.responseText;
							if(txt.length >5)
							{
								var str = txt.split(',');		
								Pro_O_Detail.DW_ID.value=str[0];																					
					  		Pro_O_Detail.Car_Owner.value=str[3];
								Pro_O_Detail.Car_BH.value=str[4];
								Pro_O_Detail.Car_DW.value=str[6];								
								Pro_O_Detail.Car_CType.value=str[8];
								Pro_O_Detail.Car_CType_Name.value=str[9];
								document.getElementById('T_Owner').innerHTML=str[3];
								document.getElementById('T_BH').innerHTML=str[4];
								document.getElementById('T_DW').innerHTML=str[6];
								document.getElementById('T_Car').innerHTML=str[9];
							}	else
							{
								Pro_O_Detail.DW_ID.value='无数据';																					
					  		Pro_O_Detail.Car_Owner.value='无数据';
								Pro_O_Detail.Car_BH.value='无数据';
								Pro_O_Detail.Car_DW.value='无数据';								
								Pro_O_Detail.Car_CType.value='无数据';
								Pro_O_Detail.Car_CType_Name.value='无数据';
								}		
						}else
						{
						alert("发生错误");
						}
					}
	};
	var turl ="Pro_Id_Car.do?Id="+Pro_O_Detail.Unq_Str.value+"&Sid=<%=Sid%>&Func_Corp_Id=<%=currStatus.getFunc_Corp_Id()%>";
	reqAdd.open("post",turl,true);
	reqAdd.send(null);
	return true;
	Pro_O_Add.Value.focus(); 
}
</SCRIPT>
</html>