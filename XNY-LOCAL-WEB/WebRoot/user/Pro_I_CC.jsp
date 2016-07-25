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
<script type='text/javascript' src='../skin/js/zDrag.js'   charset='gb2312'></script>
<script type='text/javascript' src='../skin/js/zDialog.js' charset='gb2312'></script>
<!--<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
-->
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
	
  ArrayList Pro_I_CC = (ArrayList)session.getAttribute("Pro_I_CC_" + Sid);
  ArrayList Pro_I_All = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);
  ArrayList Pro_CC_FU = (ArrayList)session.getAttribute("Pro_CC_FU_" + Sid);
  ArrayList Pro_CC_CA = (ArrayList)session.getAttribute("Pro_CC_CA_" + Sid);
  int sn = 0;
  double Value_All       = 0.0;	
  String Unit = "";
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
<body style=" background:#CADFFF">
<form name="Pro_I_CC"  action="Pro_I_CC.do" method="post" target="mFrame">
<div id="down_bg_2">
	<table width="100%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='25px' class='sjtop'>
			<td width='80%' align='left'>
				装气点:
				<select  name='Func_Cpm_Id' style='width:100px;height:20px' onChange="doSelect()" >											
						<option value="6666" <%=currStatus.getFunc_Cpm_Id().equals("6666")?"selected":""%>>全部</option>				
				<%
								if(null != Pro_CC_FU)
								{
									Iterator fuit = Pro_CC_FU.iterator();
									while(fuit.hasNext())
									{
										ProIBean fuBean = (ProIBean)fuit.next();									
				%>
								<option value='<%=fuBean.getForward_Unit()%>' <%=currStatus.getFunc_Cpm_Id().equals(fuBean.getForward_Unit())?"selected":""%>><%=fuBean.getForward_Unit()%></option>
				<%
									}
								}
				%>		
				</select>
				卸气点:
				<select  name='Func_Name_Id' style='width:100px;height:20px' onChange="doSelect()" >											
								<option value="<%=Manage_List%>" >全部站点</option>				
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
											<option value='<%=statBean.getId()%>' <%=currStatus.getFunc_Name_Id().equals(statBean.getId())?"selected":""%>><%=statBean.getBrief()%></option>
								<%
										}
									}
								}
								%>
				</select>
				
				
				
				承运商:
				<select  name='Func_Type_Id' style='width:100px;height:20px' onChange="doSelect()" >											
				<option value="8888" <%=currStatus.getFunc_Type_Id().equals("8888")?"selected":""%>>全部</option>				
				<%
								if(null != Pro_CC_CA)
								{
									Iterator cait = Pro_CC_CA.iterator();
									while(cait.hasNext())
									{
										ProIBean caBean = (ProIBean)cait.next();									
				%>
								<option value='<%=caBean.getCar_Corp()%>'<%=currStatus.getFunc_Type_Id().equals(caBean.getCar_Corp())?"selected":""%>><%=caBean.getCar_Corp()%></option>
				<%
									}
								}
				%>				
				</select>
				
			燃料类型:
				<select name='Func_Corp_Id' style='width:100px;height:20px' onChange="doSelect()">
					<option value='9999' <%=currStatus.getFunc_Corp_Id().equals("9999")?"selected":""%>>全部</option>
					<%
					
					String Oil_List = "";
					if(null != Pro_I_All)
					{
						Iterator riter = Pro_I_All.iterator();
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
			  日期时间:
				<input id='BDate' name='BDate' type='text' style='width:90px;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>

				<input id='EDate' name='EDate' type='text' style='width:90px;height:18px;' value='<%=EDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
			</td>
			<td width='20%' align='right'>		
				<img id="img1" src="../skin/images/mini_button_search.gif" onClick='doSelect()' style='cursor:hand;'>
				<img id="img2" src="../skin/images/excel.gif"              onClick='doExport()' >
			</td>
		</tr>
		
		<tr height='10' valign='middle'>
			<td width='100%' align='center' colspan=2>
				<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">	
				
							<tr height='50'>
								<td width='100%' align=center colspan=9><font size=4><B>中海油珠海新能源槽车统计表</B></font></td>
							</tr>
							
						</table>
				</td>
		</tr>
		
		
		<tr height='30' valign='middle'>
			<td width='100%' align='center' colspan=2>
				<table width="100%" border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
					<tr height='30' valign='middle'>
						<td width='8%'  align='center' ><strong>序号</strong></td>
						<td width='8%' align='center' ><strong>编号</strong></td>
						<td width='8%'  align='center' ><strong>装气点</strong></td>
						<td width='8%'  align='center' ><strong>卸气点</strong></td>
						<td width='8%'  align='center' ><strong>司机</strong></td>
						<td width='10%'  align='center' ><strong>挂车号码</strong></td>
						<td width='10%'  align='center' ><strong>车牌号码</strong></td>
						<td width='8%'  align='center' ><strong>承运商</strong></td>
						<td width='8%'  align='center' ><strong>日期</strong></td>
						<td width='8%'  align='center' ><strong>毛重</strong></td>
						<td width='8%'  align='center' ><strong>皮重</strong></td>
						<td width='8%'  align='center' ><strong>净重</strong></td>
					</tr>
					<%
					 if(Pro_I_CC != null)
					 {
						Iterator iterator = Pro_I_CC.iterator();
						while(iterator.hasNext())
						{
							ProIBean Bean = (ProIBean)iterator.next();
							String SN = Bean.getSN();
							String Cpm_Name = Bean.getCpm_Name();
							String SQLtime = Bean.getCTime();
							String CTime = SQLtime.substring(0,10);	
							String Order_Id = Bean.getOrder_Id();											
							String Memo = Bean.getMemo();
							String Status = Bean.getStatus();
							String Car_Id = Bean.getCar_Id();
							String Car_Owner = Bean.getCar_Owner();
							String Car_Corp  = Bean.getCar_Corp();
							String Oil_CType = Bean.getOil_CType();
							String Checker_Name = Bean.getChecker_Name();
							String Operator_Name = Bean.getOperator_Name();
							String Trailer_No    = Bean.getTrailer_No();
							String Forward_Unit  = Bean.getForward_Unit();
							String Gross_Weight  = Bean.getGross_Weight();
							String Tear_Weight   = Bean.getTear_Weight();
							String Ture_Weight   = Bean.getTure_Weight();
							Value_All     = Value_All + Double.parseDouble(Ture_Weight);				
							switch(Integer.parseInt(Oil_CType))
							{
								default:
								case 1000://汽油
								case 1010://90#汽油
								case 1011://90#无铅汽油
								case 1012://90#清洁汽油
								case 1020://92#汽油
								case 1021://92#无铅汽油
								case 1022://92#清洁汽油
								case 1030://93#汽油
								case 1031://93＃无铅汽油
								case 1032://93#清洁汽油
								case 1040://95#汽油
								case 1041://95#无铅汽油
								case 1042://95#清洁汽油
								case 1050://97#汽油
								case 1051://97#无铅汽油
								case 1052://97#清洁汽油
								case 1060://120＃汽油
								case 1080://其他车用汽油
								case 1090://98#汽油
								case 1091://98#无铅汽油
								case 1092://98＃清洁汽油
								case 1100://车用汽油
								case 1200://航空汽油
								case 1201://75#航空汽油
								case 1202://95#航空汽油
								case 1203://100#航空汽油
								case 1204://其他航空汽油
								case 1300://其他汽油
								case 2000://柴油
								case 2001://0#柴油
								case 2002://+5#柴油
								case 2003://+10#柴油
								case 2004://+15#柴油
								case 2005://+20#柴油
								case 2006://-5#柴油
								case 2007://-10#柴油
								case 2008://-15#柴油
								case 2009://-20#柴油
								case 2010://-30#柴油
								case 2011://-35#柴油
								case 2015://-50#柴油
								case 2100://轻柴油
								case 2016://其他轻柴油
								case 2200://重柴油
								case 2012://10#重柴油
								case 2013://20#重柴油
								case 2014://其他重柴油
								case 2300://军用柴油
								case 2301://-10#军用柴油
								case 2900://其他柴油
										Unit = "L";
									break;
								case 3001://CNG
								case 3002://LNG
										Unit = "kg";
									break;
							}
							
							if(null == Order_Id){Order_Id = "";}							
							if(null == Car_Id){Car_Id = "";}
							if(null == Car_Owner){Car_Owner = "";}
							if(null == Oil_CType || Oil_CType.trim().length() < 1){Oil_CType = "1000";}
							if(null == Memo){Memo = "";}
							if(null == Checker_Name){Checker_Name = "";}
							
							String Oil_CName = "无";
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
							
							
							sn++;
					%>
				  <tr height='30' valign='middle' title='<%=Memo%>'>				
							<td align=center><%=sn%></td>
							<td align=center><%=Order_Id%></td>
							<td align=center><%=Forward_Unit%></td>
							<td align=center><%=Cpm_Name%></td>
							<td align=center><%=Car_Owner%></td>
							<td align=center><%=Trailer_No%></td>
							<td align=center><%=Car_Id%></td>
							<td align=center><%=Car_Corp%></td>
							<td align=center><%=CTime%></td>
							<td align=center><%=Gross_Weight%><%=Unit%></td>
							<td align=center><%=Tear_Weight%><%=Unit%></td>
							<td align=center><%=Ture_Weight%><%=Unit%></td>						
					</tr>
					<%
						}
					}
			%>
			<tr height='25'><td colspan='12'align='center'><strong>本页合计：共卸车净重为：
							<font color='red'><%=new BigDecimal(Value_All).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP)%></font></strong><%= Unit%></td></tr>
			<%
					for(int i=0;i<(MsgBean.CONST_PAGE_SIZE - sn);i++)
					{
						if(sn % 2 != 0)
					  {
					%>				  
				      <tr height='30' valign='middle'>
				      	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
				      </tr>
					<%
						}
					  else
					  {
					%>				
	          <tr height='30' valign='middle'>
		          <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
		        </tr>	        
					<%
       			}
     			}
					%> 
     		 	<tr>
						<td colspan="12" class="table_deep_blue" >
				 			<table width="100%" height="20"  border="0" cellpadding="0" cellspacing="0" >
			    			<tr valign="bottom">
			      			<td nowrap><%=currStatus.GetPageHtml("Pro_I_CC")%></td>
			    			</tr>			    		
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<input name="Cmd"      type="hidden" value="1">
<input name="Sid"      type="hidden" value="<%=Sid%>">
<input name="SN"       type="hidden" value=""/>
<input name="Cpm_Id"       type="hidden" value=""/>
<input name="Status"   type="hidden" value=""/>
<input name="BTime"    type="hidden" value="">
<input name="ETime"    type="hidden" value="">
<input name="Func_Sub_Id"    type="hidden" value="0">
<input name="CurrPage" type="hidden" value="<%=currStatus.getCurrPage()%>">
</form>
</body>
<SCRIPT LANGUAGE=javascript>
if(<%=currStatus.getResult().length()%> > 0)
   alert("<%=currStatus.getResult()%>");
<%
currStatus.setResult("");
session.setAttribute("CurrStatus_" + Sid, currStatus);
%>

//ipad禁掉导出
if(1 == fBrowserRedirect() || 2 == fBrowserRedirect())
{
	document.getElementById('img2').style.display = 'none';
}


function doSelect()
{
	/**var days = new Date(Pro_I_CC.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_I_CC.BDate.value.replace(/-/g, "/")).getTime();
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
	}**/
	Pro_I_CC.Cpm_Id.value = Pro_I_CC.Func_Name_Id.value;
	Pro_I_CC.BTime.value = Pro_I_CC.BDate.value + " 00:00:00";
	Pro_I_CC.ETime.value = Pro_I_CC.EDate.value + " 23:59:59";
	Pro_I_CC.submit();
}

function GoPage(pPage)
{
	/**var days = new Date(Pro_I_CC.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_I_CC.BDate.value.replace(/-/g, "/")).getTime();
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
	**/
	if(pPage == "")
	{
		 alert("请输入目标页面的数值!");
		 return;
	}
	if(pPage < 1)
	{
	   	alert("请输入页数大于1");
		  return;	
	}
	if(pPage > <%=currStatus.getTotalPages()%>)
	{
		pPage = <%=currStatus.getTotalPages()%>;
	}
	Pro_I_CC.Cpm_Id.value = Pro_I_CC.Func_Name_Id.value;
	Pro_I_CC.BTime.value = Pro_I_CC.BDate.value + " 00:00:00";
	Pro_I_CC.ETime.value = Pro_I_CC.EDate.value + " 23:59:59";
	Pro_I_CC.CurrPage.value = pPage;
	Pro_I_CC.submit();
}



var req = null;
function doExport()
{	
/**	var days = new Date(Pro_I_CC.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_I_CC.BDate.value.replace(/-/g, "/")).getTime();
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
	**/
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
		var url = "Pro_I_CC_Export.do?Sid=<%=Sid%>&Cpm_Id="+Pro_I_CC.Func_Name_Id.value+"&Func_Sub_Id=0&Func_Corp_Id="+Pro_I_CC.Func_Corp_Id.value+"&BTime="+Pro_I_CC.BDate.value + ' 00:00:00'+"&ETime="+Pro_I_CC.EDate.value + ' 23:59:59' ;
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