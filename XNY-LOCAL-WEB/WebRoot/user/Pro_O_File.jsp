<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ page import="bean.*"%>
<%@ page import="util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中新能源LNG公司级信息化管理平台</title>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="../skin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../skin/js/util.js"></script>
</head>
<%
	
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	String Cpm_Id = CommUtil.StrToGB2312(request.getParameter("Cpm_Id"));
  CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
  String BDate = currStatus.getVecDate().get(0).toString().substring(0,10);
	String EDate = currStatus.getVecDate().get(1).toString().substring(0,10);		
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	ArrayList Pro_R_Type = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);
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
	String ManageId = UserInfo.getManage_Role();
	
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
		String Pro_Rate = "";
		ArrayList Pro_R_Info   = (ArrayList)session.getAttribute("Pro_R_Info_" + Sid);
		if(null != Pro_R_Info)
		{
			Iterator inf = Pro_R_Info.iterator();
			while(inf.hasNext())
			{
				CorpInfoBean ifBean  = (CorpInfoBean)inf.next();
				Pro_Rate = ifBean.getRate(); 
				
			}
		}
	
%>
<body  style=" background:#CADFFF">
<form name="Pro_O_File" action="Pro_O_File.do" method="post" target="mFrame" >
  <table width="100%" style='margin:auto;' border=1 cellPadding=0 cellSpacing=0 bordercolor="#3491D6" borderColorDark="#ffffff">
		<tr height='30'>			
			<td colspan='4' align='center'>
				<strong>场站日报数据</strong>
			</td>
		</tr>
		<tr height='30'>
			<td width='20%' align='center'  >站点</td>	
				<td width='30%' align='center'  >				
					<select  name='Func_Cpm_Id' style='width:85%;height:20px' onChange="doSelect()" >			
						<option value='' > --站点选择-- </option>															
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
				</td>
				<td width='20%' align='center'>燃料类型</td>	
				<td width='30%' align='center'>				
					<select name='Func_Corp_Id' style='width:150px;height:20px' >				
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
				</td>
		</tr>
			<tr height='30'>
				
				<td width='20%' align='center'>日期</td>
				<td width='30%' align='center'>
					<input  name='BDate' type='text'  value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10' onChange="doSelect()">													
				</td>
				<td width='20%' align='center'>库存气量(kg)</td>	
				<td width='30%' align='center'>
					<input type='text' name='Value' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
			
			<tr height='30'>
				<td width='20%' align='center'>场站员工(人)</td>	
				<td width='30%' align='center'>
					<input type='text' name='Z_Person' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>承包商(人)</td>
				<td width='30%' align='center'>
					<input type='text' name='C_Person' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
			<tr height='30'>
				<td width='20%' align='center'>事故、险情</td>	
				<td width='30%' align='center'>
					<input type='text' name='Danger' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>违章情况</td>
				<td width='30%' align='center'>
					<input type='text' name='Peccancy' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
			<tr height='30'>
				<td width='20%' align='center'>消防系统状态</td>	
				<td width='30%' align='center'>
					<input type='text' name='XiaoFang' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>探测报警系统状态</td>
				<td width='30%' align='center'>
					<input type='text' name='BaoJing' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
			<tr height='30'>
				<td width='20%' align='center'>通讯系统状态</td>	
				<td width='30%' align='center'>
					<input type='text' name='TongXun' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
				<td width='20%' align='center'>医疗急救设施</td>
				<td width='30%' align='center'>
					<input type='text' name='JiJiu' style='width:80%;height:18px;' value='' maxlength='20' onkeydown="changeEnter()" >
				</td>
			</tr>
	</table>
			<div align='center' >
				<img src="../skin/images/mini_button_submit.gif" style='cursor:hand;' onClick='doAdd()'>					
			</div>
			
	</table> 
	<input type="hidden" name="Sid"             value="<%=Sid%>">
	<input type="hidden" name="CTime"             value="">
	<input type="hidden" name="Value_Gas"             value="">
	<input type="hidden" name="Cpm_Id"             value="<%=Cpm_Id%>">
	<input type="hidden" name="Func_Corp_Id"             value="">
	<input type="hidden" name="Func_Sub_Id"             value="9">
	<input type="hidden" name="Func_Type_Id"             value="">	
	<input name="BTime" 				type="hidden"  value= "" >
	<input name="ETime" 				type="hidden"  value= "" >
	<input name="Status" 				type="hidden"  value= "" >
</form>
</body>
<script LANGUAGE="javascript">
function doAdd()
{		
	if(Pro_O_File.Func_Cpm_Id.value.length < 1)
  {
  	alert('请选择站点!');
  	return;
  }  
	if(Pro_O_File.Value.value.length < 1)
  {
  	alert('请输入当天库存!');
  	return;
  }  	
  if(Pro_O_File.Status.value == "999")
  {
  	alert("报表已存在");
  	return;
  	}
		Pro_O_File.Cpm_Id.value = Pro_O_File.Func_Cpm_Id.value;
		Pro_O_File.CTime.value = Pro_O_File.BDate.value;
		Pro_O_File.BTime.value = Pro_O_File.BDate.value+ " 00:00:00";
		Pro_O_File.ETime.value = Pro_O_File.BDate.value+ " 23:59:59";
		Pro_O_File.Value_Gas.value = Pro_O_File.Value.value*<%=Pro_Rate%>;
		Pro_O_File.submit();
}

function changeEnter()
{    
	if(event.keyCode==13)
	{
		event.keyCode=9;
	} 	
} 
function doSelect()
{ 
	Pro_O_File.Status.value = "";
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
							if(txt.length > 5)
							{
								Pro_O_File.Status.value = "999"	;						
							}
						}else
						{
						alert("发生错误");
						}
					}
	};
	var turl ="Pro_O_Date.do?&Sid=<%=Sid%>&Cmd=0&Cpm_Id="+Pro_O_File.Func_Cpm_Id.value+"&BTime="+Pro_O_File.BDate.value+"&ETime="+Pro_O_File.BDate.value;
	reqAdd.open("post",turl,true);
	reqAdd.send(null);
	return true;

}

</script>
</html>