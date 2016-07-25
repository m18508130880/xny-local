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
<script type="text/javascript" src="../skin/js/util.js"></script>
<script type="text/javascript" src="../skin/js/day.js"></script>

</head>
<%
	
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	CurrStatus currStatus  = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	ArrayList User_FP_Role = (ArrayList)session.getAttribute("User_FP_Role_" + Sid);
	UserInfoBean UserInfo  = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
	String ManageId = UserInfo.getManage_Role();
	String Operator_Name = UserInfo.getCName();
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
	
	ArrayList Pro_R_Type   = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);
	CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	String Corp_Name = "";
	String Oil_Info = "";
	String Oil_Name = "";
	String Station_Info = "";
	String Car_Info = "";
	if(null != Corp_Info)
	{
		Corp_Name = Corp_Info.getCName();
		Oil_Info = Corp_Info.getOil_Info();
		Station_Info = Corp_Info.getStation_Info();
		Car_Info = Corp_Info.getCar_Info();
		if(null == Corp_Name){Corp_Name = "";}
		if(null == Oil_Info){Oil_Info = "";}
		if(null == Station_Info){Station_Info = "";}
		if(null == Car_Info){Car_Info = "";}
		if(null != currStatus.getFunc_Corp_Id() && Oil_Info.length() > 0)
		{
			String[] List = Oil_Info.split(";");
		  for(int i=0; i<List.length && List[i].length()>0; i++)
		  {
		  	String[] subList = List[i].split(",");
		  	if(currStatus.getFunc_Corp_Id().equals(subList[0]))
		  	{
		  		Oil_Name = subList[1];
		  		break;
		  	}
  		}
		}
	}
	
	int Year  = Integer.parseInt(CommUtil.getDate().substring(0,4));
  if(null != (String)session.getAttribute("Year_" + Sid)  && ((String)session.getAttribute("Year_" + Sid)).trim().length() > 0) {Year = CommUtil.StrToInt((String)session.getAttribute("Year_" + Sid));}
  
  ArrayList Pro_L_Crp_Y_C = (ArrayList)session.getAttribute("Pro_L_Crp_Y_C_" + Sid);
  ArrayList Pro_L_Crp_Y   = (ArrayList)session.getAttribute("Pro_L_Crp_Y_" + Sid);
  
	//站点
	String T_Cpm_Id = "";
	int cnt = 0;
	
	//合计
	double Value_O_01_All = 0.0;
	double Value_O_02_All = 0.0;
	double Value_O_03_All = 0.0;
	double Value_O_04_All = 0.0;
	double Value_O_05_All = 0.0;
	double Value_O_06_All = 0.0;
	double Value_O_07_All = 0.0;
	double Value_O_08_All = 0.0;
	double Value_O_09_All = 0.0;
	double Value_O_10_All = 0.0;
	double Value_O_11_All = 0.0;
	double Value_O_12_All = 0.0;
	double Value_O_Y_All  = 0.0;
	int Car_Cnt_All       = 0;
	int Car_Num       = 0;
	String Car_Name   = "";
	List<Double> Car_List = new ArrayList<Double>();
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
<form name="Pro_L_Crp_Y"  action="Pro_L_Crp.do" method="post" target="mFrame">
<div id="down_bg_2">
	<table width="100%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='25px' class='sjtop'>
			<td width='70%' align='left'>
				加气站点:
				<select  name='Func_Cpm_Id' style='width:100px;height:20px' onChange="doSelect()" >										
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
				燃料类型:
				<select name='Func_Corp_Id' style='width:150px;height:20px' onChange="doSelect()">				
				<%
				if(null != Pro_R_Type)
				{
					Iterator typeiter = Pro_R_Type.iterator();
					while(typeiter.hasNext())
					{
						ProRBean typeBean = (ProRBean)typeiter.next();
						String type_Id = typeBean.getOil_CType();
						String type_Name = "无";
												
						if(Oil_Info.trim().length() > 0)
						{
						  String[] List = Oil_Info.split(";");
						  for(int i=0; i<List.length && List[i].length()>0; i++)
						  {
						  	String[] subList = List[i].split(",");
						  	if(type_Id.equals(subList[0]))
						  	{
						  		type_Name = subList[1];
						  		break;
						  	}
				  		}
				  	}
				%>
				  	<option value='<%=type_Id%>' <%=currStatus.getFunc_Corp_Id().equals(type_Id)?"selected":""%>><%=type_Id%>|<%=type_Name%></option>
				<%				  	
					}
				}
				%>
				</select>
				<select name="Func_Sub_Id"  style="width:90px;height:20px" onChange="doChangeSelect(this.value)">
					<option value="0" <%=(currStatus.getFunc_Sub_Id() == 0 ?"SELECTED":"")%>>年报表</option>
	        <option value="1" <%=(currStatus.getFunc_Sub_Id() == 1 ?"SELECTED":"")%>>月报表</option>
	        <option value="2" <%=(currStatus.getFunc_Sub_Id() == 2 ?"SELECTED":"")%>>周报表</option>
	        <option value="3" <%=(currStatus.getFunc_Sub_Id() == 3 ?"SELECTED":"")%>>日报表</option>
	      </select>
	      <select name="Year" style="width:70px;height:20px;">
        <%
        for(int j=2012; j<=2030; j++)
        {
        %>
          <option value="<%=j%>" <%=(Year == j?"selected":"")%>><%=j%>年</option>
        <%
        }
        %>
        </select>       
			</td>
			<td width='30%' align='right'>		
				<img id="img1" src="../skin/images/mini_button_search.gif" onClick='doSelect()' style='cursor:hand;'>
				<img id="img2" src="../skin/images/excel.gif"              onClick='doExport()' >
		<!--		<img id="img3" src="../skin/images/tubiaofenxi.gif"        onClick='doGraph()'  style="cursor:hand;display:<Limit:limitValidate userrole='<%=FpList%>' fpid='020503' ctype='1'/>">-->
			</td>
		</tr>
		<tr height='30' valign='middle'>
			<td width='100%' align='center' colspan=2>
				<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
				<%
				if(null != Pro_L_Crp_Y)
				{
				%>
					<tr height='50'>
						<td width='100%' align=center colspan=18>
							<font size=4><B><%=Corp_Name%>生产运营年报汇总表[<%=Oil_Name%>]</B></font>
						</td>
					</tr>
					<tr height='30'>
						<td width='100%' align=right  colspan=18>
							<strong>数据日期: <%=Year%>年1月—<%=Year%>年12月</strong>
						</td>
					</tr>
				  <tr height='30'>
				    <td width='12%' align=center rowspan='2' ><strong>加气站</strong></td>
				    <td width='8%'  align=center rowspan='2' ><strong>投运时间</strong></td>
				    <td width='52%' align=center colspan='13'><strong>加注数量(L或kg)</strong></td>
				    <td width='20%' align=center colspan='2' ><strong>在运营车辆</strong></td>
				    <td width='8%'  align=center rowspan='2' ><strong>备注</strong></td>
				  </tr>
				  <tr height='30'>
				    <td width='4%'  align=center><strong>1月</strong></td>
				    <td width='4%'  align=center><strong>2月</strong></td>
				    <td width='4%'  align=center><strong>3月</strong></td>
				    <td width='4%'  align=center><strong>4月</strong></td>
				    <td width='4%'  align=center><strong>5月</strong></td>
				    <td width='4%'  align=center><strong>6月</strong></td>
				    <td width='4%'  align=center><strong>7月</strong></td>
				    <td width='4%'  align=center><strong>8月</strong></td>
				    <td width='4%'  align=center><strong>9月</strong></td>
				    <td width='4%'  align=center><strong>10月</strong></td>
				    <td width='4%'  align=center><strong>11月</strong></td>
				    <td width='4%'  align=center><strong>12月</strong></td>
				    <td width='4%'  align=center><strong>本年累计</strong></td>
				    <td width='5%'  align=center><strong>数量</strong></td>
				    <td width='15%' align=center><strong>车辆类型</strong></td>
				  </tr>
				<%
					Iterator iterator = Pro_L_Crp_Y.iterator();
					while(iterator.hasNext())
					{
						ProLCrpBean Bean = (ProLCrpBean)iterator.next();
						String T_Cpm_Name  = Bean.getCpm_Name();
						String T_Cpm_CTime = Bean.getCpm_CTime();
						if(!T_Cpm_Id.equals(Bean.getCpm_Id()))
						{
							//加注数量
							String Value_O_01 = "0.00";
							String Value_O_02 = "0.00";
							String Value_O_03 = "0.00";
							String Value_O_04 = "0.00";
							String Value_O_05 = "0.00";
							String Value_O_06 = "0.00";
							String Value_O_07 = "0.00";
							String Value_O_08 = "0.00";
							String Value_O_09 = "0.00";
							String Value_O_10 = "0.00";
							String Value_O_11 = "0.00";
							String Value_O_12 = "0.00";
							double Value_O_Y  = 0.0;
							
							Iterator subiter = Pro_L_Crp_Y.iterator();
							while(subiter.hasNext())
							{
								ProLCrpBean subBean = (ProLCrpBean)subiter.next();
								if(subBean.getCpm_Id().equals(Bean.getCpm_Id()))
								{
									if(subBean.getCTime().equals(Year+"01"))
									{
										Value_O_01 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"02"))
									{
										Value_O_02 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"03"))
									{
										Value_O_03 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"04"))
									{
										Value_O_04 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"05"))
									{
										Value_O_05 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"06"))
									{
										Value_O_06 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"07"))
									{
										Value_O_07 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"08"))
									{
										Value_O_08 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"09"))
									{
										Value_O_09 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"10"))
									{
										Value_O_10 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"11"))
									{
										Value_O_11 = subBean.getValue_O();
									}
									if(subBean.getCTime().equals(Year+"12"))
									{
										Value_O_12 = subBean.getValue_O();
									}
								}
							}
							Value_O_Y = Double.parseDouble(Value_O_01)
							 		      + Double.parseDouble(Value_O_02)
							 		      + Double.parseDouble(Value_O_03)
							 		      + Double.parseDouble(Value_O_04)
							 		      + Double.parseDouble(Value_O_05)
							 		      + Double.parseDouble(Value_O_06)
							 		      + Double.parseDouble(Value_O_07)
							 		      + Double.parseDouble(Value_O_08)
							 		      + Double.parseDouble(Value_O_09)
							 		      + Double.parseDouble(Value_O_10)
							 		      + Double.parseDouble(Value_O_11)
							 		      + Double.parseDouble(Value_O_12);
							
							//车辆累计
							int Car_Cnt = 0;
							String Car_CType1    = "";
							String Car_CType2    = "";
							int    Car_CType_cnt = 0;
							String Car_CType_str = "";
							if(null != Pro_L_Crp_Y_C)
							{
								Iterator cariter = Pro_L_Crp_Y_C.iterator();
								while(cariter.hasNext())
								{
									ProOBean carBean = (ProOBean)cariter.next();
									if(carBean.getCpm_Id().equals(Bean.getCpm_Id()))
									{
										Car_Cnt++;
										Car_CType1 += carBean.getCar_CType() + ",";
										if(!Car_CType2.contains(carBean.getCar_CType()))
										{
											Car_CType2 += carBean.getCar_CType() + ",";
										}
									}
								}
								if(Car_CType1.length() > 0 && Car_CType2.length() > 0)
								{
									String[] List = Car_CType2.split(",");
									for(int i=0; i<List.length; i++)
									{
										//车辆类型
										String str_Car_Name = "";
										if(Car_Info.length() > 0)
										{
											String[] CarList = Car_Info.split(";");
											for(int k=0; k<CarList.length; k++)
											{
												String[] subCarList = CarList[k].split(",");
												if(List[i].equals(subCarList[0]))
												{
													str_Car_Name = subCarList[1];												
												}
											}
										}
								
										Car_CType_cnt = 0;
										String[] sub_List = Car_CType1.split(",");
										for(int j=0; j<sub_List.length; j++)
										{
											if(List[i].equals(sub_List[j]))
											{
												Car_CType_cnt++;
											}
										}
										if(0 == i)
										{
											Car_CType_str += str_Car_Name + " " + Car_CType_cnt + " 辆;";

										}
										else
										{
											Car_CType_str += "<br>" + str_Car_Name + " " + Car_CType_cnt + " 辆;";

										}								
									}
								}
							}
							
							//合计
							Value_O_01_All = Value_O_01_All + Double.parseDouble(Value_O_01);
							Value_O_02_All = Value_O_02_All + Double.parseDouble(Value_O_02);
							Value_O_03_All = Value_O_03_All + Double.parseDouble(Value_O_03);
							Value_O_04_All = Value_O_04_All + Double.parseDouble(Value_O_04);
							Value_O_05_All = Value_O_05_All + Double.parseDouble(Value_O_05);
							Value_O_06_All = Value_O_06_All + Double.parseDouble(Value_O_06);
							Value_O_07_All = Value_O_07_All + Double.parseDouble(Value_O_07);
							Value_O_08_All = Value_O_08_All + Double.parseDouble(Value_O_08);
							Value_O_09_All = Value_O_09_All + Double.parseDouble(Value_O_09);
							Value_O_10_All = Value_O_10_All + Double.parseDouble(Value_O_10);
							Value_O_11_All = Value_O_11_All + Double.parseDouble(Value_O_11);
							Value_O_12_All = Value_O_12_All + Double.parseDouble(Value_O_12);
							Value_O_Y_All  = Value_O_Y_All + Value_O_Y;
							Car_Cnt_All    = Car_Cnt_All + Car_Cnt;
							
							//记录数
							cnt++;
				%>
							<tr height='30'>
								<td width='12%' align=center><%=T_Cpm_Name%></td>
								<td width='8%'  align=center><%=T_Cpm_CTime%></td>								
						    <td width='4%'  align=center><%=Value_O_01%></td>
						    <td width='4%'  align=center><%=Value_O_02%></td>
						    <td width='4%'  align=center><%=Value_O_03%></td>
						    <td width='4%'  align=center><%=Value_O_04%></td>
						    <td width='4%'  align=center><%=Value_O_05%></td>
						    <td width='4%'  align=center><%=Value_O_06%></td>
						    <td width='4%'  align=center><%=Value_O_07%></td>
						    <td width='4%'  align=center><%=Value_O_08%></td>
						    <td width='4%'  align=center><%=Value_O_09%></td>
						    <td width='4%'  align=center><%=Value_O_10%></td>
						    <td width='4%'  align=center><%=Value_O_11%></td>
						    <td width='4%'  align=center><%=Value_O_12%></td>
						    <td width='4%'  align=center><%=new BigDecimal(Value_O_Y).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></td>
						    <td width='5%'  align=center><%=Car_Cnt%></td>
						    <td width='15%' align=left><%=Car_CType_str%>&nbsp;</td>
						    <td width='8%'  align=center>&nbsp;</td>
						  </tr>
				<%
						}
						T_Cpm_Id = Bean.getCpm_Id();
					}
				%>
					<tr height='30'>
						<td width='20%' align=center colspan=2><B>合计</B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_01_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_02_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_03_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_04_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_05_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_06_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_07_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_08_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_09_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_10_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_11_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_12_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='4%'  align=center><B><%=new BigDecimal(Value_O_Y_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></B></td>
				    <td width='5%'  align=center><B><%=Car_Cnt_All%></B></td>
			
				    <td width='15%' align=left>&nbsp;</td>
				    <td width='8%'  align=center>&nbsp;</td>
				  </tr>
				  <tr height='30'>
				    <td width='36%' align=center colspan=6><strong>制表: </strong>系统</td>
				    <td width='24%' align=center colspan=6><strong>审核: </strong><%=Operator_Name%></td>
				    <td width='40%' align=center colspan=6><strong>上报日期: </strong><%=CommUtil.getDate()%></td>
				  </tr>
				<%
				}
				else
				{
				%>
					<tr height='30'>
						<td width='100%' align=center colspan=18>无!</td>
					</tr>
				<%
				}
				%>
				</table>
			</td>
		</tr>
	</table>
</div>
<input name="Cmd"    type="hidden" value="0">
<input name="Sid"    type="hidden" value="<%=Sid%>">
<input name="Cpm_Id" type="hidden" value=""/>
<input name="BTime"  type="hidden" value="">
<input name="ETime"  type="hidden" value="">
<input type="button" id="CurrButton" onClick="doSelect()" style="display:none">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
//ipad禁掉导出
if(1 == fBrowserRedirect() || 2 == fBrowserRedirect())
{
	document.getElementById('img2').style.display = 'none';
}

//window.parent.frames.lFrame.document.getElementById('CurrJsp').innerText = 'Pro_L_Crp_Y.jsp';

function doChangeSelect(pFunc_Sub_Id)
{
	switch(parseInt(pFunc_Sub_Id))
	{
		case 0://今年
				var Year  = new Date().format("yyyy-MM-dd").substring(0,4);
				var BTime = Year + '-01-01';
				var ETime = Year + '-12-31';
				window.parent.frames.mFrame.location = "Pro_L_Crp.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id="+Pro_L_Crp_Y.Func_Cpm_Id.value+"&Func_Sub_Id="+pFunc_Sub_Id+"&Func_Corp_Id="+Pro_L_Crp_Y.Func_Corp_Id.value+"&BTime="+BTime+"&ETime="+ETime+"&Year="+Year;
			break;
		case 1://上月
  			var BTime = showPreviousFirstDay().format("yyyy-MM-dd");
			  var ETime = showPreviousLastDay().format("yyyy-MM-dd");
			  var Year  = BTime.substring(0,4);
			  var Month = BTime.substring(5,7);
				window.parent.frames.mFrame.location = "Pro_L_Crp.do?Cmd=1&Sid=<%=Sid%>&Cpm_Id="+Pro_L_Crp_Y.Func_Cpm_Id.value+"&Func_Sub_Id="+pFunc_Sub_Id+"&Func_Corp_Id="+Pro_L_Crp_Y.Func_Corp_Id.value+"&BTime="+BTime+"&ETime="+ETime+"&Year="+Year+"&Month="+Month;
			break;
		case 2://本周
				var TDay  = new Date().format("yyyy-MM-dd");
				var Year  = TDay.substring(0,4);
				var Month = TDay.substring(5,7);
				var Week  = "1";
				var BTime = TDay;
				var ETime = TDay;
				window.parent.frames.mFrame.location = "Pro_L_Crp.do?Cmd=2&Sid=<%=Sid%>&Cpm_Id="+Pro_L_Crp_Y.Func_Cpm_Id.value+"&Func_Sub_Id="+pFunc_Sub_Id+"&Func_Corp_Id="+Pro_L_Crp_Y.Func_Corp_Id.value+"&BTime="+BTime+"&ETime="+ETime+"&Year="+Year+"&Month="+Month+"&Week="+Week;
			break;
		case 3://昨天
				var BTime = showPreviousDay().format("yyyy-MM-dd");
				window.parent.frames.mFrame.location = "Pro_L_Crp.do?Cmd=3&Sid=<%=Sid%>&Cpm_Id="+Pro_L_Crp_Y.Func_Cpm_Id.value+"&Func_Sub_Id="+pFunc_Sub_Id+"&Func_Corp_Id="+Pro_L_Crp_Y.Func_Corp_Id.value+"&BTime="+BTime;
			break;
	}
}

function doSelect()
{
	Pro_L_Crp_Y.Cpm_Id.value = Pro_L_Crp_Y.Func_Cpm_Id.value;
	Pro_L_Crp_Y.BTime.value = Pro_L_Crp_Y.Year.value + '-01-01';
  Pro_L_Crp_Y.ETime.value = Pro_L_Crp_Y.Year.value + '-12-31';
	Pro_L_Crp_Y.submit();
}

var req = null;
function doExport()
{
	if(0 == <%=cnt%>)
	{
		alert('当前无报表!');
		return;
	}
	var BTime = Pro_L_Crp_Y.Year.value + '-01-01';
	var ETime = Pro_L_Crp_Y.Year.value + '-12-31';
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
		var url = "Pro_L_Crp_Y_Export.do?Sid=<%=Sid%>&Operator_Name=<%=Operator_Name%>&Cpm_Id="+Pro_L_Crp_Y.Func_Cpm_Id.value+"&BTime="+BTime+"&ETime="+ETime+"&Func_Sub_Id="+Pro_L_Crp_Y.Func_Sub_Id.value+"&Func_Corp_Id="+Pro_L_Crp_Y.Func_Corp_Id.value+"&Year="+Pro_L_Crp_Y.Year.value;
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

function doGraph()
{
	//按月分析
	var BTime = showPreviousFirstDay().format("yyyy-MM-dd");
  var BYear  = BTime.substring(0,4);
  var BMonth = BTime.substring(5,7);
  window.parent.frames.mFrame.location = "Pro_Crp_G.do?Cmd=20&Sid=<%=Sid%>&Cpm_Id="+Pro_L_Crp_Y.Func_Cpm_Id.value+"&Func_Corp_Id=3001&Func_Sub_Id=1&Func_Sel_Id=1&BYear="+BYear+"&BMonth="+BMonth;
}
</SCRIPT>
</html>