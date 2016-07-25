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
<script type='text/javascript' src='../skin/js/browser.js' charset='gb2312'></script>
<script type="text/javascript" src="../skin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../skin/js/util.js"></script>
<script type="text/javascript" src="../skin/js/day.js"></script>

</head>
<%
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
	ArrayList User_FP_Role = (ArrayList)session.getAttribute("User_FP_Role_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	String ManageId = UserInfo.getManage_Role();
	String Operator = UserInfo.getId();
	String FpId = UserInfo.getFp_Role();
	String FpList = "";
	if(null != FpId && FpId.length() > 0 && null != User_FP_Role)
	{
		Iterator roleiter = User_FP_Role.iterator();
		while(roleiter.hasNext())
		{
			UserRoleBean roleBean = (UserRoleBean)roleiter.next();
			if(roleBean.getId().equals(FpId) && null != roleBean.getPoint())
			{
				FpList = roleBean.getPoint();
			}
		}
	}
	
	ArrayList Pro_R_Type = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);
	CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	String Oil_Info = "";
	if(null != Corp_Info)
	{
		Oil_Info = Corp_Info.getOil_Info();
		if(null == Oil_Info){Oil_Info = "";}
	}
	
  CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
  String BDate = currStatus.getVecDate().get(0).toString().substring(0,10);
	String EDate = currStatus.getVecDate().get(1).toString().substring(0,10);
	
	ArrayList Crm_Info = (ArrayList)session.getAttribute("Crm_Info_" + Sid);
  ArrayList Pro_L_Crm_All = (ArrayList)session.getAttribute("Pro_L_Crm_All_" + Sid);
  ArrayList Pro_L_Crm   = (ArrayList)session.getAttribute("Pro_L_Crm_" + Sid);
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
								
	String Cpm_Name = "";
	String T_Cpm_Id = "";
	String Crm_Name = "";
	String T_Crm_Id = "";	
	String CrmList = "";
	int sn = 0;				
%>	
<body style="background:#CADFFF">
<form name="Pro_L_Crm"  action="Pro_L_Crm.do" method="post" target="mFrame">
<div id="down_bg_2">
	<table width="100%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">			
		<tr height='25px' class='sjtop'>
			<td td width='80%' align='left'>
				加气站点:
				<select  name='Func_Cpm_Id' style='width:150px;height:20px' onChange="doSelect()" >			
					<option value="<%=Manage_List%>" <%=currStatus.getFunc_Cpm_Id().equals(Manage_List)?"selected":""%>>全部站点</option>				
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
				客户名称：
				<select name='Func_Type_Id' style='width:100px;height:20px' onChange="doSelect()">
					<option value="" <%=currStatus.getFunc_Cpm_Id().equals("")?"selected":""%>>全部客户</option>		
					<%
						if(null != Crm_Info)
						{
							Iterator iterator = Crm_Info.iterator();
							while(iterator.hasNext())
							{
								CrmInfoBean statBean = (CrmInfoBean)iterator.next();
					%>
								<option value='<%=statBean.getId()%>' <%=currStatus.getFunc_Type_Id().equals(statBean.getId())?"selected":""%>><%=statBean.getBrief()%></option>
					<%
							}
						}
					%>
				</select>				
			燃料类型:
				<select name='Func_Corp_Id' style='width:150px;height:20px' onChange="doSelect()">
					<%
					
					String Oil_List = "";
					if(null != Pro_L_Crm_All)
					{
						Iterator riter = Pro_L_Crm_All.iterator();
						while(riter.hasNext())
						{
							ProLCrmBean rBean = (ProLCrmBean)riter.next();
							if(currStatus.getFunc_Cpm_Id().contains(rBean.getCpm_Id()))
							{
								Oil_List += rBean.getOil_CType() + ",";
							}
						}
					}
					
					if(Oil_Info.trim().length() > 0)
					{
					  String[] List = Oil_Info.split(";");
					  for(int i=0; i<List.length && List[i].length()>0; i++)
					  {
					  	String[] subList = List[i].split(",");
					  	if(Oil_List.contains(subList[0]))
					  	{
					%>
					  	<option value='<%=subList[0]%>' <%=currStatus.getFunc_Corp_Id().equals(subList[0])?"selected":""%>><%=subList[0]%>|<%=subList[1]%></option>
					<%
							}
					  }
					}
					%>
				</select>
				时间日期：
				<input id='BDate' name='BDate' type='text' style='width:90px;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
-
				<input id='EDate' name='EDate' type='text' style='width:90px;height:18px;' value='<%=EDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
			
			</td>			
			<td width='20%' align='right'>
				<img id="img1" src="../skin/images/mini_button_search.gif" onClick='doSelect()' style='cursor:hand;'>
				<img id="img2" src="../skin/images/excel.gif"              onClick='doExport()' >
			</td>
		</tr>
	</table>			 	 	 		
	<%
		if(null != Pro_L_Crm)
				{										
					Iterator ri = Pro_L_Crm.iterator();
					while(ri.hasNext())
					{				
						ProLCrmBean pBean = (ProLCrmBean)ri.next();	
						Cpm_Name        = pBean.getCpm_Name();
						CrmList += pBean.getCrm_Id()+",";
					}
				}	
		if(null != Crm_Info)
		{
			Iterator init = Crm_Info.iterator();
			while(init.hasNext())
			{
				CrmInfoBean infoBean = (CrmInfoBean)init.next();
				String Id  = infoBean.getId();
				Crm_Name   = infoBean.getBrief();
				Double Total_Value = 0.00;
				Double Total_Value_Gas = 0.00;		
				if(CrmList.contains(infoBean.getId()+","))
				{
	%>
		<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
			<tr height='50'>
				<td width='100%' align=center ><font size=4><B>中海油珠海新能源有限公司销量确认表</B></font></td>
			</tr>			
	 	</table>			
		<table width="100%" border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
				<tr height='30'>		
				<td width='3%' align=center>&nbsp;</td>
				<td width='72%' align=left><strong>客户: <%=Crm_Name%> </strong></td>
				<td width='25%' align=left><strong>数据日期: <%=BDate%>至<%=EDate%></strong></td>
			</tr>
		</table>
		<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='30'>
			<td width='25%' align=center rowspan ='2'><strong>时间</strong></td>
			<td width='50%' align=center colspan ='2'><strong>销售量</strong></td>
			<td width='25%' align=center rowspan ='2'><strong>备注</strong></td>
		</tr>
		<tr height='30'>
			<td width='25%' align=center><strong>液态(kg) </strong></td>
			<td width='25%' align=center><strong>折算气态(Nm3)</strong></td>
		</tr>						
	<%		
				if(null != Pro_L_Crm)
				{										
					Iterator riter = Pro_L_Crm.iterator();
					while(riter.hasNext())
					{								
						ProLCrmBean plcBean = (ProLCrmBean)riter.next();										
						if(Id.equals(plcBean.getCrm_Id()))
						{			
							Total_Value += Double.parseDouble(plcBean.getValue_I());
							Total_Value_Gas += Double.parseDouble(plcBean.getValue_I_Gas());
							sn++;
	%>							
		<tr height='30'>
			<td width='25%' align=center><%=plcBean.getCTime()%></td>
			<td width='25%' align=center><%=new BigDecimal(Double.parseDouble(plcBean.getValue_I())).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></td>
		  <td width='25%' align=center><%=new BigDecimal(Double.parseDouble(plcBean.getValue_I_Gas())).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></td>
		  <td width='25%' align=center>&nbsp;</td>
		</tr>							
								
	<%							
					}			
				}
	%>
		<tr height='30'>
			<td width='25%' align=center><strong>合计: </strong></td>
			<td width='25%' align=center><strong><%=new BigDecimal(Total_Value).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></strong></td>
		  <td width='25%' align=center><strong><%=new BigDecimal(Total_Value_Gas).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></strong></td>
		  <td width='25%' align=center>&nbsp;</td>
		</tr>		
	</table>				
	<%										
			}
			}
		}
		}
	%>
																			
</div>
<input name="Cmd"      type="hidden" value="0">
<input name="Sid"      type="hidden" value="<%=Sid%>">
<input name="Cpm_Id"   type="hidden" value=""/>
<input name="Crm_Id"   type="hidden" value=""/>
<input name="BTime"    type="hidden" value="">
<input name="ETime"    type="hidden" value="">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doSelect()
{
	Pro_L_Crm.Crm_Id.value = Pro_L_Crm.Func_Type_Id.value;	
	Pro_L_Crm.Cpm_Id.value = Pro_L_Crm.Func_Cpm_Id.value;	            
	Pro_L_Crm.BTime.value = Pro_L_Crm.BDate.value + " 00:00:00";
	Pro_L_Crm.ETime.value = Pro_L_Crm.EDate.value + " 23:59:59";
	Pro_L_Crm.submit();
}


var req = null;
function doExport()
{	
	var days = new Date(Pro_L_Crm.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_L_Crm.BDate.value.replace(/-/g, "/")).getTime();
	var dcnt = parseInt(days/(1000*60*60*24));
	if(dcnt < 0)
	{
		alert('截止日期需大于开始日期');
		return;
	}
	if((dcnt + 1) > 31)
	{
		alert('日期跨越不超过31天');
		return;
	}
	
	if(0 == <%=sn%>)
	{
		alert('当前无记录!');
		return;
	}
	
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
		var url = "Pro_Crm_XL_Export.do?Sid=<%=Sid%>&Cpm_Name=<%=Cpm_Name%>&Cpm_Id="+Pro_L_Crm.Func_Cpm_Id.value+"&Crm_Id="+Pro_L_Crm.Func_Type_Id.value+"&Func_Corp_Id="+Pro_L_Crm.Func_Corp_Id.value+"&BTime="+Pro_L_Crm.BDate.value + ' 00:00:00'+"&ETime="+Pro_L_Crm.EDate.value + ' 23:59:59' ;;
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