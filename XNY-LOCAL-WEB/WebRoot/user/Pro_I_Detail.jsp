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
  String Operator = UserInfo.getId();
  String Operator_Name = UserInfo.getCName();
  String ManageId = UserInfo.getManage_Role();
  
  ArrayList Pro_R_Buss = (ArrayList)session.getAttribute("Pro_R_Buss_" + Sid);
  CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	String Oil_Info = "";
	if(null != Corp_Info)
	{
		Oil_Info = Corp_Info.getOil_Info();
		if(null == Oil_Info){Oil_Info = "";}
	}
	 
  
  ArrayList Pro_I = (ArrayList)session.getAttribute("Pro_I_" + Sid);
  String SN = request.getParameter("SN");

	 String Cpm_Id= "";
	 String Cpm_Name= "";
	 String CTime= "";
	 String Order_Id= "";
	 String Order_Value= "";
	 String Value= "";
	 String Value_Gas= "";
	 String Car_Id= "";
	 String Car_Owner= "";
	 String Memo= "";
	 String Status= "";
	 String Oil_CType= "";
	 String Oil_CName = "无";
	 String Worker= "";
	 String Checker= "";
	 String Checker_Name= "";
	 String Car_Corp= "";

	 String Tank_No= "";         //储罐罐号
	 String Pre_Check= "";   		//卸车前检查
	 String Pro_Check= "";   		//卸车过程检查
	 String Lat_Check= ""; 			//卸车后检查
	 String Pre_Tank_V= "";			//卸车前罐容
	 String Lat_Tank_V= "";			//卸车后罐容
	 String Pre_Temper= "";			//卸车前温度
	 String Lat_Temper= "";			//卸车后温度
	 String Pre_Press= ""; 			//卸车前压力
	 String Lat_Press= ""; 			//卸车后压力
	 String Pre_Weight= "";			//卸车前重量
	 String Lat_Weight= "";			//卸车后重量
	 String Unload= "";      		//卸车重量
	 String Gross_Weight= "";		//装车毛重
	 String Tear_Weight= ""; 		//装车皮重
	 String Ture_Weight= ""; 		//装车净重
	 String Trailer_No= "";  		//挂车车号
	 String Forward_Unit= "";		//发货单位
	 String Temper_Report= "";   //气质报告单号
	 String Arrive_Time= "";     //到站时间
	 String Depart_Time= "";     //离站时间
	 
	 if(Pro_I != null)
	{
		Iterator iterator = Pro_I.iterator();
		while(iterator.hasNext())
		{
			ProIBean Bean = (ProIBean)iterator.next();
			if(Bean.getSN().equals(SN))
			{
				Cpm_Name    = Bean.getCpm_Name();
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
	  		Cpm_Id					=	Bean.getCpm_Id();
	  		Cpm_Name				=	Bean.getCpm_Name();
	  		CTime						=	Bean.getCTime();
	  		Order_Id				=	Bean.getOrder_Id();
	  		Order_Value			=	Bean.getOrder_Value();
	  		Value						=	Bean.getValue();
	  		Value_Gas				= Bean.getValue_Gas();
	  		Car_Id					= Bean.getCar_Id();
	  		Car_Owner				= Bean.getCar_Owner();
	  		Operator				= Bean.getOperator();
	  		Operator_Name		= Bean.getOperator_Name();
	  		Memo						= Bean.getMemo();
	  		Status					= Bean.getStatus();
	  		Oil_CType				= Bean.getOil_CType();
	  		Worker					= Bean.getWorker();
	  		Checker					= Bean.getChecker();
	  		Checker_Name		= Bean.getChecker_Name();
	  		Car_Corp				= Bean.getCar_Corp();

	  		Tank_No					= Bean.getTank_No();     		//储罐罐号
	  		Pre_Check				= Bean.getPre_Check();   		//卸车前检查
	  		Pro_Check				= Bean.getPro_Check();   		//卸车过程检查
	 			Lat_Check				= Bean.getLat_Check(); 			//卸车后检查
	 			Pre_Tank_V			= Bean.getPre_Tank_V();			//卸车前罐容
	 			Lat_Tank_V			= Bean.getLat_Tank_V();			//卸车后罐容
	 			Pre_Temper			= Bean.getPre_Temper();			//卸车前温度
	 			Lat_Temper			= Bean.getLat_Temper();			//卸车后温度
	 			Pre_Press				= Bean.getPre_Press(); 			//卸车前压力
	 			Lat_Press				= Bean.getLat_Press(); 			//卸车后压力
	 			Pre_Weight			= Bean.getPre_Weight();			//卸车前重量
	 			Lat_Weight			= Bean.getLat_Weight();			//卸车后重量
	 			Unload					= Bean.getUnload();     		//卸车重量
	 			Gross_Weight		= Bean.getGross_Weight();		//装车毛重
	 			Tear_Weight			= Bean.getTear_Weight(); 		//装车皮重
	 			Ture_Weight			= Bean.getTure_Weight(); 		//装车净重
	 			Trailer_No			= Bean.getTrailer_No();  		//挂车车号
	 			Forward_Unit		= Bean.getForward_Unit();		//发货单位
	 			Temper_Report		= Bean.getTemper_Report();  //气质报告单号
	 			Arrive_Time			= Bean.getArrive_Time();    //到站时间
	 			Depart_Time			= Bean.getDepart_Time();    //离站时间
			
				
			}
		}
 	}  	
%>
<body style="background:#CADFFF">
<form name="Pro_I_Detail" action="Pro_I.do" method="post" target="mFrame">
<div id="down_bg_2">
	<div id="cap"><img src="../skin/images/pro_i_Detail.gif"></div><br><br>
	<div id="right_table_center">
		<table width="60%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">			
			<tr>
				<td align='right'>
					<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doEdit()'>
					<img id="img2" src="../skin/images/excel.gif"   onClick='doExport()'  >
					</td>
				</tr>
			<tr height='30'>
				<td width='100%' align='center'>
					<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">		
						<tr height='30'>
							  <td width='15%' align='center'>卸车站点</td>
								<td width='35%' align='left' ><%=Cpm_Name%></td>
								<td width='15%' align='center'>卸入罐号</td>
							  <td width='35%' align='left'><%= Tank_No%></td>				
						</tr>
					  <tr height='30'>
					  	  <td width='15%' align='center'>卸车单号</td>
								<td width='35%' align='left'><%= Order_Id%></td>
								<td width='15%' align='center'>开始卸车时间</td>
								<td width='35%' align='left'><%= CTime%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前检查</td>						
							<td width='35%' align='left'>
								<input type='text' name='Pre_Check' style='width:80%;height:18px;' value='<%= Pre_Check%>' maxlength='20'>
							</td>
							<td width='15%' align='center'>装车毛重</td>
                <td width='35%' align='left'>
                	<input type='text' name='Gross_Weight' style='width:80%;height:18px;' value='<%= Gross_Weight%>' maxlength='20'>
                	Kg</td>
							</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车过程检查</td>
							<td width='35%' align='left'>
								<input type='text' name='Pro_Check' style='width:80%;height:18px;' value='<%= Pro_Check%>' maxlength='20'>
								</td>
							<td width='15%' align='center'>装车皮重</td>
							<td width='35%' align='left'>
								<input type='text' name='Tear_Weight' style='width:80%;height:18px;' value='<%= Tear_Weight%>' maxlength='20'>
								Kg</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后检查</td>
							<td width='35%' align='left'>
								<input type='text' name='Lat_Check' style='width:80%;height:18px;' value='<%= Lat_Check%>' maxlength='20'>
							</td>
							<td width='15%' align='center'>装车净重</td>
							<td width='35%' align='left'>
								<input type='text' name='Ture_Weight' style='width:80%;height:18px;' value='<%= Ture_Weight%>' maxlength='20'>
								Kg</td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前罐容</td>
							<td width='35%' align='left'>
								<input type='text' name='Pre_Tank_V' style='width:80%;height:18px;' value='<%= Pre_Tank_V%>' maxlength='20'>
								L</td>
							<td width='15%' align='center'>运输车牌</td>
							<td width='35%' align='left'><%= Car_Id%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后罐容</td>
							<td width='35%' align='left'>
								<input type='text' name='Lat_Tank_V' style='width:80%;height:18px;' value='<%= Lat_Tank_V%>' maxlength='20'>
								L</td>
							<td width='15%' align='center'>挂车车号</td>
							<td width='35%' align='left'><%= Trailer_No%></td>
						<tr height='30'>
							<td width='15%' align='center'>卸入容积</td>
							<td width='35%' align='left'>
								<input type='text' name='Unload' style='width:80%;height:18px;' value='<%= Unload%>' maxlength='20'>
								L</td>
							<td width='15%' align='center'>承运单位</td>
							<td width='35%' align='left'><%= Car_Corp%></td>	
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前温度</td>
							<td width='35%' align='left'>
								<input type='text' name='Pre_Temper' style='width:80%;height:18px;' value='<%= Pre_Temper%>' maxlength='20'>
								℃</td>
							<td width='15%' align='center'>槽车司机</td>
							<td width='35%' align='left'><%= Car_Owner%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后温度</td>
							<td width='35%' align='left'>
								<input type='text' name='Lat_Temper' style='width:80%;height:18px;' value='<%= Lat_Temper%>' maxlength='20'>
								℃</td>
							<td width='15%' align='center'>发货单位</td>
							<td width='35%' align='left'><%= Forward_Unit%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前压力</td>
							<td width='35%' align='left'>
								<input type='text' name='Pre_Press' style='width:80%;height:18px;' value='<%= Pre_Press%>' maxlength='20'>
								MPa</td>
							<td width='15%' align='center'>燃料类型</td>
							<td width='35%' align='left'><%= Oil_CName%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后压力</td>
							<td width='35%' align='left'>
								<input type='text' name='Lat_Press' style='width:80%;height:18px;' value='<%= Lat_Press%>' maxlength='20'>
								MPa</td>
							<td width='15%' align='center'>气质报告单号</td>
							<td width='35%' align='left'><%= Temper_Report%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车前重量</td>
							<td width='35%' align='left'>
								<input type='text' name='Pre_Weight' style='width:80%;height:18px;' value='<%= Pre_Weight%>' maxlength='20'>
								Kg</td>
							<td width='15%' align='center'>到站时间</td>
							<td width='35%' align='left'><%= Arrive_Time%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车后重量</td>
							<td width='35%' align='left'>
								<input type='text' name='Lat_Weight' style='width:80%;height:18px;' value='<%= Lat_Weight%>' maxlength='20'>
								Kg</td>
							<td width='15%' align='center'>离站时间</td>
							<td width='35%' align='left'><%= Depart_Time%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>卸车重量</td>
							<td width='35%' align='left'>
								<input type='text' name='Value' style='width:80%;height:18px;' value='<%= Value%>' maxlength='20'>
								Kg</td>
							<td width='15%' align='center'>作业人员</td>
							<td width='35%' align='left'><%= Worker%></td>
						</tr>
						<tr height='30'>
							<td width='15%' align='center'>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
							<td width='35%' align='left' colspan='3'>&nbsp;<%= Memo%></td>
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
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doEdit()
{
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
		var url = "Pro_I_Detail_Export.do?Cmd=5&Sid=<%=Sid%>&SN="+Pro_I_Detail.SN.value;
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