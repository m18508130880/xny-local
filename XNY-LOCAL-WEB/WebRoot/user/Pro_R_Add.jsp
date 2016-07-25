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
<script type="text/javascript" src="../skin/js/util.js"></script>
<script type="text/javascript" src="../skin/js/My97DatePicker/WdatePicker.js"></script>

<script language="javascript">
	var now=new Date();
	
</script>
</head>
<%
	
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
  UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
  String Operator = UserInfo.getId();
  String Operator_Name = UserInfo.getCName();
  String ManageId = UserInfo.getManage_Role();
  String BDate = CommUtil.getDate();
  
  ArrayList Pro_R = (ArrayList)session.getAttribute("Pro_R_" + Sid);
  CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
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
<form name="Pro_R_Add" action="Pro_R.do" method="post" target="mFrame">
<div id="down_bg_2">
	<div id="cap"><img src="../skin/images/pro_r_add_jh.gif"></div><br><br><br>
	<div id="right_table_center">
		<table width="50%" style='margin:auto;' border=0 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
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
							<td width='25%' align='center'>站&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点</td>
							<td width='75%' align='left'>							
								<select name="Cpm_Id" style="width:282px;height:20px" onchange="doChange(this.value)">
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
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>燃料类型</td>
							<td width='75%' align='left'>
								<select id="Oil_CType" name="Oil_CType" style="width:282px;height:20px" onchange="doChang()">
			<%						
						if(Oil_Info.trim().length() > 0)
						{
		  				String[] List = Oil_Info.split(";");
		 				 for(int i=0; i<List.length && List[i].length()>0; i++)
		  				{
		  					String[] subList = List[i].split(",");	
		  	%>				
		  			<option value='<%=subList[0]%>'><%=subList[0]%>|<%=subList[1]%></option>
		  	<%					
		  				}
		  			}		
			%>						
								</select>
							</td>
						</tr>
						
						<tr height='30'>
							<td width='25%' align='center'>储罐号</td>
							<td width='75%' align='left'>
								<select id="Tank_No" name="Tank_No" style="width:282px;height:20px" onchange="doChang()">
									<option value='1'>1号罐</option>
									<option value='2'>2号罐</option>
								</select>
							</td>
						</tr>
						
						<!--20150314修改-->	
						<tr height='30'>
							<td width='25%' align='center'>卸车计划</td>
							<td width='75%' align='left'>
								<input type='text' name='Value_Plan' style='width:200px;height:18px;' value='0.00' maxlength='10'> 
								<select name="PUnit" style='width:74px;height:20px;'>
										<option value='kg'>kg</option>
										<option value='L'>L</option>
										<option value='NM3'>NM3</option>
								</select>
							</td>
						</tr>
						
						<tr height='30'>
							<td width='25%' align='center'>录入时间</td>
							<td width='75%' align='left'>
								<input name='BDate' type='text' style='width:282px;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onchange="doChang()">
							<!--	<select name="Hour" style="width:85px;height:20px">
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
								<select name="Minute" style="width:85px;height:20px">
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
								</select>-->							
							</td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>当前库存</td>
							<td width='75%' align='left'>
								<input type='text' name='Value' style='width:200px;height:18px;' value='0.00' maxlength='10'> 
								<select name="VUnit" style='width:74px;height:20px;'>
										<option value='kg'>kg</option>
										<option value='L'>L</option>
										<option value='NM3'>NM3</option>
								</select>
							</td>
						</tr>
						
						<tr height='30'>
							<td width='25%' align='center'>预警阀值</td>
							<td width='75%' align='left'>
								<input type='text' name='Value_Ware' style='width:200px;height:18px;' value='0.00' maxlength='10'> 
								<select name="WUnit" style='width:74px;height:20px;'>
										<option value='kg'>kg</option>
										<option value='L'>L</option>
										<option value='NM3'>NM3</option>
								</select>
							</td>
						</tr>
						
					
						
		
						<tr height='30'>
							<td width='25%' align='center'>运营状态</td>
							<td width='75%' align='left'>
								<select name='Status' style='width:282px;height:20px;'>
									<option value='0'>在售</option>
									<option value='1'>停售</option>
								</select>
							</td>
						</tr>
						<tr height='30'>
							<td width='25%' align='center'>录入人员</td>
							<td width='75%' align='left'>
								<%=Operator%>
							</td>
						</tr>						
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<input type='hidden' name='Cmd' value='10'>
<input type='hidden' name='Sid' value='<%=Sid%>'>
<input type='hidden' name='Operator'     value='<%=Operator%>'>
<input type='hidden' name='Func_Corp_Id' value='<%=currStatus.getFunc_Corp_Id()%>'>
<input type='hidden' name='Func_Sub_Id'  value='<%=currStatus.getFunc_Sub_Id()%>'>
<input type='hidden' name='Func_Sel_Id'  value='<%=currStatus.getFunc_Sel_Id()%>'>
<input type='hidden' name='Func_Cpm_Id'  value='<%=currStatus.getFunc_Cpm_Id()%>'>
</form>
</body>
<SCRIPT LANGUAGE=javascript>
//初始化
var txt;
function doNO()
{
	location = "Pro_R.jsp?Sid=<%=Sid%>";
}
//选择站点 添加燃料类型&储罐编号
function doChange(pId)
{
/**--------------------------燃料类型--------------------------------
	//先删除
	var length = document.getElementById('Oil_CType').length;
	for(var i=0; i<length; i++)
	{
		document.getElementById('Oil_CType').remove(0);
	}
	
	//再添加
	if(pId.length > 0)
	{
		var str_Now = '';
		<%
		if(null != Pro_R)
		{
			Iterator riter = Pro_R.iterator();
			while(riter.hasNext())
			{
				ProRBean rBean = (ProRBean)riter.next();
		%>
				if('<%=rBean.getCpm_Id()%>' == pId)
				{
					str_Now += '<%=rBean.getOil_CType()%>' + ',';
				}
		<%
			}
		}
		if(Oil_Info.trim().length() > 0)
		{
		  String[] List = Oil_Info.split(";");
		  for(int i=0; i<List.length && List[i].length()>0; i++)
		  {
		  	String[] subList = List[i].split(",");
		%>
				if(str_Now.indexOf('<%=subList[0]%>') < 0)
				{
					var objOption = document.createElement('OPTION');
					objOption.value = '<%=subList[0]%>';
					objOption.text  = '<%=subList[0]%>|<%=subList[1]%>';
					document.getElementById('Oil_CType').add(objOption);
				}
		<%
		  }
		}
		%>
	}
	
------------------------储罐号---------------------------
	//先删除
	var length1 = document.getElementById('Tank_No').length;
	for(var i=0; i<length1; i++)
	{
		document.getElementById('Tank_No').remove(0);
	}
	//再添加
	if(pId.length > 0)
	{
		var str_Now_Tank = '';
		<%
		if(null != Pro_R)
		{
			Iterator riter = Pro_R.iterator();
			while(riter.hasNext())
			{
				ProRBean rBean = (ProRBean)riter.next();
		%>
				if('<%=rBean.getCpm_Id()%>' == pId)
				{
					str_Now_Tank += '<%=rBean.getTank_No()%>' + ',';
				}
		<%
			}
		}
		for(int j=1; j<3; j++)
		{
		%>
				if(str_Now_Tank.indexOf('<%=j%>') < 0)
				{
					var objOption = document.createElement('OPTION');
					objOption.value = '<%=j%>';
					objOption.text  = '<%=j%>号罐';
					document.getElementById('Tank_No').add(objOption);
				}
		<%
		 }
		%>
	}**/
	doChang();
}
doChange(Pro_R_Add.Cpm_Id.value);

function doAdd()
{
  if(Pro_R_Add.Cpm_Id.value.length < 1)
  {
  	alert('请选择站点!');
  	return;
  }
  if(Pro_R_Add.Oil_CType.value.length < 1)
  {
  	alert('请选择燃料类型!');
  	return;
  }

  if(Pro_R_Add.Value.value.Trim().length < 1 || Pro_R_Add.Value.value < 0)
  {
  	alert("当前库存错误,可能的原因：\n\n  1.数量为空。\n\n  2.数量不是正值。");
		return;
  }
	for(var i=0; i<Pro_R_Add.Value.value.length; i++)
	{
		if(Pro_R_Add.Value.value.charAt(0) == '.' || Pro_R_Add.Value.value.charAt(Pro_R_Add.Value.value.length-1) == '.')
		{
			alert("输入当前库存有误，请重新输入!");
	    return;
		}
		if(Pro_R_Add.Value.value.charAt(i) != '.' && isNaN(Pro_R_Add.Value.value.charAt(i)))
	  {
	    alert("输入当前库存有误，请重新输入!");
	    return;
	  }
	}
	if(Pro_R_Add.Value.value.indexOf(".") != -1)
	{
		if(Pro_R_Add.Value.value.substring(Pro_R_Add.Value.value.indexOf(".")+1,Pro_R_Add.Value.value.length).length >2)
		{
			alert("小数点后最多只能输入两位!");
			return;
		}
	}
	if(Pro_R_Add.Value_Ware.value.Trim().length < 1 || Pro_R_Add.Value_Ware.value < 0)
  {
  	alert("预警阀值错误,可能的原因：\n\n  1.数量为空。\n\n  2.数量不是正值。");
		return;
  }
	for(var i=0; i<Pro_R_Add.Value_Ware.value.length; i++)
	{
		if(Pro_R_Add.Value_Ware.value.charAt(0) == '.' || Pro_R_Add.Value_Ware.value.charAt(Pro_R_Add.Value_Ware.value.length-1) == '.')
		{
			alert("输入预警阀值有误，请重新输入!");
	    return;
		}
		if(Pro_R_Add.Value_Ware.value.charAt(i) != '.' && isNaN(Pro_R_Add.Value_Ware.value.charAt(i)))
	  {
	    alert("输入预警阀值有误，请重新输入!");
	    return;
	  }
	}
	if(Pro_R_Add.Value_Ware.value.indexOf(".") != -1)
	{
		if(Pro_R_Add.Value_Ware.value.substring(Pro_R_Add.Value_Ware.value.indexOf(".")+1,Pro_R_Add.Value_Ware.value.length).length >2)
		{
			alert("小数点后最多只能输入两位!");
			return;
		}
	}
	
	
	 if(Pro_R_Add.Value_Plan.value.Trim().length < 1 || Pro_R_Add.Value_Plan.value < 0)
  {
  	alert("当前卸车计划错误,可能的原因：\n\n  1.数量为空。\n\n  2.数量不是正值。");
		return;
  }
	for(var i=0; i<Pro_R_Add.Value_Plan.value.length; i++)
	{
		if(Pro_R_Add.Value_Plan.value.charAt(0) == '.' || Pro_R_Add.Value_Plan.value.charAt(Pro_R_Add.Value_Plan.value.length-1) == '.')
		{
			alert("输入当前卸车计划有误，请重新输入!");
	    return;
		}
		if(Pro_R_Add.Value_Plan.value.charAt(i) != '.' && isNaN(Pro_R_Add.Value_Plan.value.charAt(i)))
	  {
	    alert("输入当前卸车计划有误，请重新输入!");
	    return;
	  }
	}
	if(Pro_R_Add.Value_Plan.value.indexOf(".") != -1)
	{
		if(Pro_R_Add.Value_Plan.value.substring(Pro_R_Add.Value_Plan.value.indexOf(".")+1,Pro_R_Add.Value_Plan.value.length).length >2)
		{
			alert("小数点后最多只能输入两位!");
			return;
		}
	}
	if(txt == 1)
	{
		alert('该站本日计划已上报!');
		return;
		}
	if(confirm("信息无误,确定提交?"))
  {
  	Pro_R_Add.Func_Cpm_Id.value = Pro_R_Add.Cpm_Id.value;
  	Pro_R_Add.submit();
  }
 
}


//查询当前时间数据是否已录入
function doChang()
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
							txt = reqAdd.responseText;														
						}
					}
	};
	var turl = 'Pro_R_Date.do?Sid=<%=Sid%>&Cpm_Id='+Pro_R_Add.Cpm_Id.value+'&Oil_CType='+Pro_R_Add.Oil_CType.value+'&CTime='+Pro_R_Add.BDate.value+'&Tank_No='+Pro_R_Add.Tank_No.value;
	reqAdd.open("post",turl,true);
	reqAdd.send(null);	
}
</SCRIPT>
</html>