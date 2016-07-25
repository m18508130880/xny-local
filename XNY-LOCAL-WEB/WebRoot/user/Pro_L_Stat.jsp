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
	String cpm_name = "";
	Double 		Total_Value_All = 0.00;																									//总合计值
	ArrayList Crm_Info = (ArrayList)session.getAttribute("Crm_Info_" + Sid);
  ArrayList Pro_R_Type = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);			//所有燃料类型
  ArrayList Pro_L_Stat   = (ArrayList)session.getAttribute("Pro_L_Stat_" + Sid);						//所有数据
  ArrayList Pro_L_Stat_Day   = (ArrayList)session.getAttribute("Pro_L_Stat_Day_" + Sid);		//所有有数据的天数（X天）
  ArrayList Pro_L_Stat_Car   = (ArrayList)session.getAttribute("Pro_L_Stat_Car_" + Sid);		//所有有数据的天数（X天）
  List<String> Pro_L_Day = new ArrayList<String>();				//用来储存所有日期
	List<Double> Pro_Value_Day = new ArrayList<Double>();		//用来储存每个日期的累计值
  int Cnt_Car = 0;
  int sn      = 0;
  String Car_DW = "";
  String Car_More = "";
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
<form name="Pro_L_Stat"  action="Pro_L_Stat.do" method="post" target="mFrame">
<div id="down_bg_2">
	<table width="100%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">			
		<tr height='25px' class='sjtop'>
			<td td width='80%' align='left'>
				客户名称：
				<select name='Func_Type_Id' style='width:100px;height:20px' onChange="doSelect()">
					<%
						if(null != Crm_Info)
						{
							Iterator iterator = Crm_Info.iterator();
							while(iterator.hasNext())
							{
								CrmInfoBean statBean = (CrmInfoBean)iterator.next();
					%>
								<option name='DW_ID' value='<%=statBean.getId()%>' <%=currStatus.getFunc_Type_Id().equals(statBean.getId())?"selected":""%>><%=statBean.getBrief()%></option>
					<%
							}
						}
					%>
				</select>
				加气站点:
				<select  name='Func_Cpm_Id' style='width:150px;height:20px' onChange="doSelect()" >								
								<!--<option value="<%=Manage_List%>" <%=currStatus.getFunc_Cpm_Id().equals(Manage_List)?"selected":""%>>全部站点</option>	-->			
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
			  燃料类型:
				<select name='Func_Corp_Id' style='width:150px;height:20px' onChange="doSelect()">
					<option value='9999' <%=currStatus.getFunc_Corp_Id().equals("9999")?"selected":""%>>全部</option>
					<%
					
					String Oil_List = "";
					if(null != Pro_R_Type)
					{
						Iterator riter = Pro_R_Type.iterator();
						while(riter.hasNext())
						{
							ProRBean rBean = (ProRBean)riter.next();
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
				<input id='EDate' name='EDate' type='text' style='width:90px;height:18px;' value='<%=EDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>			
			</td>						
			<td width='20%' align='right'>
				<img id="img1" src="../skin/images/mini_button_search.gif" onClick='doSelect()' style='cursor:hand;'>
				<img id="img2" src="../skin/images/excel.gif"              onClick='doExport()' >
				
			</td>
		</tr>
		<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
			<tr height='50'>
				<td width='100%' align=center colspan=7><font size=4><B>中海油珠海新能源有限公司对账表</B></font></td>
			</tr>
			<tr height='30'>
				<td width='100%' align=center colspan=7>
					<table width="100%" border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
							<tr height='30'>
		<%
					if(null != Pro_L_Stat)
					{
						Iterator staIter = Pro_L_Stat.iterator();
						ProOBean 	OBean = (ProOBean)staIter.next();
					 
					 	Car_DW        =  OBean.getCar_DW();
					 	String Cpm_Id = OBean.getCpm_Id();
					 	if( null != User_Device_Detail)
								{
									Iterator Uiterator = User_Device_Detail.iterator();
									while(Uiterator.hasNext())
									{
										DeviceDetailBean sBean = (DeviceDetailBean)Uiterator.next();
										if(sBean.getId().equals(Cpm_Id))
										{
											cpm_name = sBean.getBrief();
										}
									}
								}
					
					}
		%>						
								<td width='10%' align=center>&nbsp;</td>
								<td width='65%' align=left><strong>站点名称:   <%=cpm_name%><%=Car_DW%></strong></td>
								<td width='25%' align=left><strong>数据日期: <%=BDate%>至<%=EDate%></strong></td>
							</tr>
						</table>
					</td>
				</tr>
	 </table>		
	<table width="100%" border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='30'>
				<td width='100%' align=center colspan=9>
						<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
							 <tr height='30'>
										<td width='100px' align=center ><strong>车牌号</strong></td>		
										<%		
/**********************************先把日期遍历出来。表格table的台头为车牌号，日期 1 2 3 4等，合计**********************************/																			
											if(null != Pro_L_Stat_Day)
											{
													Iterator riter = Pro_L_Stat_Day.iterator();
													while(riter.hasNext())
													{
													sn++;
														ProOBean dayBean = (ProOBean)riter.next();
														Pro_L_Day.add(dayBean.getCTime().substring(5,10));
														Pro_Value_Day.add(0.00);
										%>
											<td width='100px' align=center  ><strong><%=dayBean.getCTime().substring(8,10)%>号</strong></td>
										<%
													}
											}
										%>
										<td width='100px' align=center><strong>合计</strong></td>
					     </tr>	
								<%		
/**************************************遍历所有车辆********************************************************************************/													
									if(null != Pro_L_Stat_Car)
									{
										Iterator riter = Pro_L_Stat_Car.iterator();
										while(riter.hasNext())
										{
											ProOBean carBean 				= (ProOBean)riter.next();
											String carName 					= carBean.getUnq_Str();
											Double Total_Value_Car 	= 0.00;
											Cnt_Car ++;
											
							%>
									<tr height='30'>
										<td width='100px'><%=carName%></td>
							<%
/****************遍历日期，从每个Pro_L_Stat中取出对应的（日期，车牌）放入当前列****************************************************/							
											if(null != Pro_L_Day)
											{
												Iterator dayIter = Pro_L_Day.iterator();
												int i = 0;												//不论是否有数据，日期LIST计数必须++
												while(dayIter.hasNext())
												{
													boolean flagHaveData = false;		//判断该车该日是否有数据，有则加数据td，无则加空白td
													String tmpDay = (String)dayIter.next();	
													if(null != Pro_L_Stat)
													{
														Iterator valueIter = Pro_L_Stat.iterator();
														while(valueIter.hasNext())
														{
															ProOBean valueBean 	= (ProOBean)valueIter.next();
															String 		CValue 		= valueBean.getValue();										//当前ProOBean的Value
															String 		CDay 			= valueBean.getCTime().substring(5,10);		//当前ProOBean的日期
															String 		CUnqStr		= valueBean.getUnq_Str();									//当前ProOBean的车牌号
															if(tmpDay.equals(CDay) && carName.equals(CUnqStr))
															{
																flagHaveData = true;
																Total_Value_Car += Double.parseDouble(CValue);
																Total_Value_All += Double.parseDouble(CValue);
																Pro_Value_Day.set(i, Pro_Value_Day.get(i) + Double.parseDouble(CValue));
									%>										
										<td width='100px' align=center>
											<%=new BigDecimal(Double.parseDouble(CValue)).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%>				
										</td>
									<%	
															}																																		
														}		
													}
													if(!flagHaveData)
													{//无数据，加入空白td
									%>										
										<td width='100px' align=center>0.00</td>
									<%					
													}
													i++;							//不论是否有数据，日期LIST计数必须++
												}							
											}
							%>
										<td width='100px' align=center><strong><%=new BigDecimal(Total_Value_Car).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></strong></td>
									</tr>
							<%
									
										}
									}
								%>					     
					     				     								
										<tr height='30'>
											<td width='100px' align=center><strong><%=Cnt_Car%>辆</strong></td>
										<%
/********************每日合计**************************************************************************************************/
											if(null != Pro_Value_Day)
											{
												Iterator dayIter = Pro_Value_Day.iterator();
												while(dayIter.hasNext())
												{
													Double Value_Day = (Double)dayIter.next();
											%>
												<td width='100px' align=center><strong><%=new BigDecimal(Value_Day).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></strong></td>
											<%
												}
											}
										%>
										<td width='100px' align=center><strong><%=new BigDecimal(Total_Value_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></strong></td>
										</tr>									
									</table>
								</td>
					</tr>					
		</table>			
	</table>
</div>
<input name="Cmd"      type="hidden" value="1">
<input name="Sid"      type="hidden" value="<%=Sid%>">
<input name="Cpm_Id"   type="hidden" value=""/>
<input name="DW_ID"   type="hidden" value=""/>
<input name="BTime"    type="hidden" value="">
<input name="ETime"    type="hidden" value="">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
function doSelect()
	{		
		Pro_L_Stat.DW_ID.value = Pro_L_Stat.Func_Type_Id.value;	
		Pro_L_Stat.Cpm_Id.value = Pro_L_Stat.Func_Cpm_Id.value;	
		Pro_L_Stat.BTime.value = Pro_L_Stat.BDate.value + " 00:00:00";
		Pro_L_Stat.ETime.value = Pro_L_Stat.EDate.value + " 23:59:59";
		Pro_L_Stat.submit();
	}
	
	var req = null;
function doExport()
{	
	var days = new Date(Pro_L_Stat.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_L_Stat.BDate.value.replace(/-/g, "/")).getTime();
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
		var url = "Pro_Stat_Export.do?Sid=<%=Sid%>&Cpm_Name=<%=cpm_name%>&Car_DW=<%=Car_DW%>&Cpm_Id="+Pro_L_Stat.Func_Cpm_Id.value+"&DW_ID="+Pro_L_Stat.Func_Type_Id.value+"&Func_Corp_Id="+Pro_L_Stat.Func_Corp_Id.value+"&BTime="+Pro_L_Stat.BDate.value + ' 00:00:00'+"&ETime="+Pro_L_Stat.EDate.value + ' 23:59:59' ;
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