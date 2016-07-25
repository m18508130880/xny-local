<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>
<%@ page import="util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.math.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中新能源LNG公司级信息化管理平台</title>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="../skin/js/jquery.min.js"></script>
<script type="text/javascript" src="../skin/js/highcharts.js"></script>
<script type="text/javascript" src="../skin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../skin/js/util.js"></script>

</head>
<%
	
	String Sid   = CommUtil.StrToGB2312(request.getParameter("Sid"));
  CurrStatus currStatus = (CurrStatus)session.getAttribute("CurrStatus_" + Sid);
  ArrayList Pro_R_Type = (ArrayList)session.getAttribute("Pro_R_Type_" + Sid);
  UserInfoBean UserInfo = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	CorpInfoBean Corp_Info = (CorpInfoBean)session.getAttribute("User_Corp_Info_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	ArrayList User_Device_Detail = (ArrayList)session.getAttribute("User_Device_Detail_" + Sid);
	String ManageId = UserInfo.getManage_Role();
	String Oil_Info = "";
	if(null != Corp_Info)
	{
		Oil_Info = Corp_Info.getOil_Info();
		if(null == Oil_Info){Oil_Info = "";}
	}
	
	int BYear = Integer.parseInt(CommUtil.getDate().substring(0,4));
  int BMonth = Integer.parseInt(CommUtil.getDate().substring(5,7));
  int EYear = Integer.parseInt(CommUtil.getDate().substring(0,4));
  int EMonth = Integer.parseInt(CommUtil.getDate().substring(5,7));
  int BWeek = 1;
  int EWeek = 1;
  String BDate = CommUtil.getDate();
  String EDate = CommUtil.getDate();
  
  if(null != (String)session.getAttribute("BYear_" + Sid) && ((String)session.getAttribute("BYear_" + Sid)).trim().length() > 0){BYear = CommUtil.StrToInt((String)session.getAttribute("BYear_" + Sid));}
  if(null != (String)session.getAttribute("BMonth_" + Sid) && ((String)session.getAttribute("BMonth_" + Sid)).trim().length() > 0){BMonth = CommUtil.StrToInt((String)session.getAttribute("BMonth_" + Sid));}
  if(null != (String)session.getAttribute("EYear_" + Sid) && ((String)session.getAttribute("EYear_" + Sid)).trim().length() > 0){EYear = CommUtil.StrToInt((String)session.getAttribute("EYear_" + Sid));}
  if(null != (String)session.getAttribute("EMonth_" + Sid) && ((String)session.getAttribute("EMonth_" + Sid)).trim().length() > 0){EMonth = CommUtil.StrToInt((String)session.getAttribute("EMonth_" + Sid));}
  if(null != (String)session.getAttribute("BWeek_" + Sid)  && ((String)session.getAttribute("BWeek_" + Sid)).trim().length() > 0) {BWeek = CommUtil.StrToInt((String)session.getAttribute("BWeek_" + Sid));}
  if(null != (String)session.getAttribute("EWeek_" + Sid)  && ((String)session.getAttribute("EWeek_" + Sid)).trim().length() > 0) {EWeek = CommUtil.StrToInt((String)session.getAttribute("EWeek_" + Sid));}
  if(null != (String)session.getAttribute("BDate_" + Sid) && ((String)session.getAttribute("BDate_" + Sid)).trim().length() > 0){BDate = (String)session.getAttribute("BDate_" + Sid);}
  if(null != (String)session.getAttribute("EDate_" + Sid) && ((String)session.getAttribute("EDate_" + Sid)).trim().length() > 0){EDate = (String)session.getAttribute("EDate_" + Sid);}
  
  String Pro_G = (String)session.getAttribute("Pro_G_" + Sid);
  
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
<body style='background:#ffffff'>
<form name='Pro_G'  action='Pro_G.do' method='post' target='mFrame'>
<div id='down_bg_2'>
	<table style='margin:auto' border=0 cellPadding=0 cellSpacing=0 bordercolor='#3491D6' borderColorDark='#ffffff' width='100%'>  
	  <tr height='25px' class='sjtop'>
	    <td width='85%' align='left'>
	    	站点名称:
				<select  name='Func_Cpm_Id' style='width:100px;height:20px' onChange="doSelect()" >					
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
				<select name='Func_Corp_Id' style='width:150px;height:20px' onChange='doSelect()'>
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
	    	图表:
	      <select name='Func_Sub_Id'  style='width:90px;height:20px' onChange='doSelect()'>
	        <option value='1' <%=(currStatus.getFunc_Sub_Id() == 1 ?"SELECTED":"")%>>趋势图表</option>
	        <option value='2' <%=(currStatus.getFunc_Sub_Id() == 2 ?"SELECTED":"")%>>柱状图表</option>
	        <!--
	        <option value='3' <%=(currStatus.getFunc_Sub_Id() == 3 ?"SELECTED":"")%>>比例图表</option>
	        -->
	      </select>
	      模式:
	      <select name='Func_Sel_Id' style='width:90px;height:20px' onChange='doSelect()'>
	      	<option value='0' <%=(currStatus.getFunc_Sel_Id() == 0 ?"SELECTED":"")%>>按年分析</option>
	        <option value='1' <%=(currStatus.getFunc_Sel_Id() == 1 ?"SELECTED":"")%>>按月分析</option>
	        <option value='2' <%=(currStatus.getFunc_Sel_Id() == 2 ?"SELECTED":"")%>>按周分析</option>
	        <option value='3' <%=(currStatus.getFunc_Sel_Id() == 3 ?"SELECTED":"")%>>按日分析</option>
	      </select>
	      <select id="BYear" name="BYear" style="width:60px;height:20px;">
        <%
        for(int j=2012; j<=2049; j++)
        {
        %>
          <option value="<%=j%>" <%=(BYear == j?"selected":"")%>><%=j%>年</option>
        <%
        }
        %>
        </select>
	      <select id="BMonth" name="BMonth" style="width:50px;height:20px;">
        <%
        for(int k=1; k<13; k++)
        {
       	%>
       		<option value="<%=k%>" <%=(BMonth == k?"selected":"")%>><%=k%>月</option>
       	<%
       	}
       	%>
        </select>
	      <select id="BWeek" name="BWeek" style="width:70px;height:20px;">
        	<option value="1" <%=(BWeek == 1?"selected":"")%>>第一周</option>
        	<option value="2" <%=(BWeek == 2?"selected":"")%>>第二周</option>
        	<option value="3" <%=(BWeek == 3?"selected":"")%>>第三周</option>
        	<option value="4" <%=(BWeek == 4?"selected":"")%>>第四周</option>
        	<option value="5" <%=(BWeek == 5?"selected":"")%>>第五周</option>
        </select>      
	      <input type='text' id='BDate' name='BDate' style='width:90px;height:18px;' value='<%=BDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
	      -
	      <select id="EYear" name="EYear" style="width:60px;height:20px;">
        <%
        for(int j=2012; j<=2049; j++)
        {
        %>
          <option value="<%=j%>" <%=(EYear == j?"selected":"")%>><%=j%>年</option>
        <%
        }
        %>
        </select>
	      <select id="EMonth" name="EMonth" style="width:50px;height:20px;">
        <%
        for(int k=1; k<13; k++)
        {
       	%>
       		<option value="<%=k%>" <%=(EMonth == k?"selected":"")%>><%=k%>月</option>
       	<%
       	}
       	%>
        </select>
        <select id="EWeek" name="EWeek" style="width:70px;height:20px;">
        	<option value="1" <%=(EWeek == 1?"selected":"")%>>第一周</option>
        	<option value="2" <%=(EWeek == 2?"selected":"")%>>第二周</option>
        	<option value="3" <%=(EWeek == 3?"selected":"")%>>第三周</option>
        	<option value="4" <%=(EWeek == 4?"selected":"")%>>第四周</option>
        	<option value="5" <%=(EWeek == 5?"selected":"")%>>第五周</option>
        </select>
        <input type='text' id='EDate' name='EDate' style='width:90px;height:18px;' value='<%=EDate%>' onClick='WdatePicker({readOnly:true})' class='Wdate' maxlength='10'>
	    </td>
      <td width='15%' align='right'>
        <img style='cursor:hand' onClick='doSelect()' src='../skin/images/mini_button_search.gif'>
      </td>
    </tr>
    <%
	  switch(currStatus.getFunc_Sub_Id())
		{
			case 1://趋势图表
		%>
				<tr height='30' valign='top'>
		    	<td width='100%' align='center' colspan=2>
		      	<div id='container' style='width:100%;height:350px;margin: 0 auto'></div>
		    	</td>
		  	</tr>
		<%
				break;
			case 2://柱状图表
		%>
				<tr height='30' valign='top'>
		    	<td width='100%' align='center' colspan=2>
		      	<div id='container' style='width:100%;height:350px;margin: 0 auto'></div>
		    	</td>
		  	</tr>
		<%
				break;
			case 3://比例图表
		%>
				<tr height='30' valign='top' style='background:#ffffff'>
		    	<td width='100%' align='center' colspan=2>
		    		<table width='100%' border=0>
							<tr height=30>
		<%
							if(null != Pro_G && Pro_G.length() > 4)
							{
								Pro_G = Pro_G.substring(4);
								String[] list = Pro_G.split("\\~");
								for(int i=0; i<list.length; i++)
								{
									if(i%2 == 0 && i > 0)
									{
		%>
										</tr>
										<tr height=30>
		<%
									}
									
		%>
								  <td width='50%' align=center>
										<div id="container<%=i%>" style="width:100%;height:350px;margin: 0 auto"></div>
									</td>
		<%
								}
								switch(list.length%2)
								{
									case 1:
		%>
										<td width='50%' align=center>&nbsp;</td>
		<%
										break;
								}
							}
		%>
							</tr>
						</table>
		    	</td>
		  	</tr>
		<%
				break;
		}
	  %>
  </table>
	<input name="Cmd"    type="hidden"  value="20">
	<input name="Sid"    type="hidden"  value="<%=Sid%>">
	<input name="Cpm_Id" type="hidden"  value="">
	<input type="button" id="CurrButton"  onClick="doSelect()" style="display:none">
</div>
</form>
</body>
<SCRIPT LANGUAGE=javascript>
//window.parent.frames.lFrame.document.getElementById('CurrJsp').innerText = 'Pro_G.jsp';

switch(parseInt(<%=currStatus.getFunc_Sel_Id()%>))
{
	case 0://按年分析
			document.getElementById('BYear').style.display = '';
			document.getElementById('BMonth').style.display = 'none';
			document.getElementById('BWeek').style.display = 'none';
			document.getElementById('BDate').style.display = 'none';
			document.getElementById('EYear').style.display = '';
			document.getElementById('EMonth').style.display = 'none';
			document.getElementById('EWeek').style.display = 'none';
			document.getElementById('EDate').style.display = 'none';
		break;
	case 1://按月分析
			document.getElementById('BYear').style.display = '';
			document.getElementById('BMonth').style.display = '';
			document.getElementById('BWeek').style.display = 'none';
			document.getElementById('BDate').style.display = 'none';
			document.getElementById('EYear').style.display = '';
			document.getElementById('EMonth').style.display = '';
			document.getElementById('EWeek').style.display = 'none';
			document.getElementById('EDate').style.display = 'none';
		break;
	case 2://按周分析
			document.getElementById('BYear').style.display = '';
			document.getElementById('BMonth').style.display = '';
			document.getElementById('BWeek').style.display = '';
			document.getElementById('BDate').style.display = 'none';
			document.getElementById('EYear').style.display = '';
			document.getElementById('EMonth').style.display = '';
			document.getElementById('EWeek').style.display = '';
			document.getElementById('EDate').style.display = 'none';
		break;
	case 3://按日分析
			document.getElementById('BYear').style.display = 'none';
			document.getElementById('BMonth').style.display = 'none';
			document.getElementById('BWeek').style.display = 'none';
			document.getElementById('BDate').style.display = '';
			document.getElementById('EYear').style.display = 'none';
			document.getElementById('EMonth').style.display = 'none';
			document.getElementById('EWeek').style.display = 'none';
			document.getElementById('EDate').style.display = '';
		break;
}

function doSelect()
{
	switch(parseInt(Pro_G.Func_Sel_Id.value))
	{
		case 0://按年分析(跨度不超过12年)
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value)) < 0)
			{
				alert('截止年度需大于开始年度!');
				return;
			}
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value) + 1) > 12)
			{
				alert('年度跨越不超过12年!');
				return;
			}
			break;
		case 1://按月分析(跨度不超过12月)
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value)) < 0)
			{
				alert('截止月份需大于开始月份!');
				return;
			}
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value)) == 0 && (parseInt(Pro_G.EMonth.value) - parseInt(Pro_G.BMonth.value)) < 0)
			{
				alert('截止月份需大于开始月份!');
				return;
			}
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value))*12 + (parseInt(Pro_G.EMonth.value) - parseInt(Pro_G.BMonth.value) + 1) > 12)
			{
				alert('月份跨越不超过12月!');
				return;
			}
			break;
		case 2://按周分析(跨度不超过12周)
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value)) < 0)
			{
				alert('截止周需大于开始周!');
				return;
			}
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value)) == 0 && (parseInt(Pro_G.EMonth.value) - parseInt(Pro_G.BMonth.value)) < 0)
			{
				alert('截止周需大于开始周!');
				return;
			}
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value)) == 0 && (parseInt(Pro_G.EMonth.value) - parseInt(Pro_G.BMonth.value)) == 0 && (parseInt(Pro_G.EWeek.value) - parseInt(Pro_G.BWeek.value)) < 0)
			{
				alert('截止周需大于开始周!');
				return;
			}
			if((parseInt(Pro_G.EYear.value) - parseInt(Pro_G.BYear.value))*12*5 + (parseInt(Pro_G.EMonth.value) - parseInt(Pro_G.BMonth.value))*5 + (parseInt(Pro_G.EWeek.value) - parseInt(Pro_G.BWeek.value) + 1) > 12)
			{
				alert('周跨越不超过12周!');
				return;
			}
			break;
		case 3://按日分析(跨度不超过15天)
			var days = new Date(Pro_G.EDate.value.replace(/-/g, "/")).getTime() - new Date(Pro_G.BDate.value.replace(/-/g, "/")).getTime();
			/**var dcnt = parseInt(days/(1000*60*60*24));
			if(dcnt < 0)
			{
				alert('截止日期需大于开始日期');
				return;
			}
			if((dcnt + 1) > 15)
			{
				alert('日期跨越不超过15天');
				return;
			}**/
			break;
	}
  Pro_G.Cpm_Id.value = Pro_G.Func_Cpm_Id.value;
  //window.parent.frames.lFrame.document.getElementById('id').value;
  Pro_G.submit();
}

<%
switch(currStatus.getFunc_Sub_Id())
{
	case 1://趋势图表
		{
%>		
			$(function () 
			{
				var json_serie = [];
				var json_xAxis = [];
				var data_serie = [];
				var data_xAxis = [];
				<%
				if(null != Pro_G && Pro_G.length() > 4)
				{
					Pro_G = Pro_G.substring(4);
					String[] temp1 = Pro_G.split("\\~");
					for(int i=0; i<temp1.length; i++)
					{
						String[] temp2 = temp1[i].split("\\^");
					  if(temp2.length > 4)//有数据
					  {
					  	String Cpm_Id   = temp2[0];
					    String Cpm_Name = temp2[1];
					    String Oil_Id   = temp2[2];
					    String Oil_Unit = temp2[3];
				      for(int j=4; j<temp2.length; j++)
							{
								String[] temp3 = temp2[j].split("\\@");
								for(int k=0; k<temp3.length; k++)
								{
									String CTime = temp3[k].split("\\$")[0];
									String CData = temp3[k].split("\\$")[1];
									
				%>
		
									data_serie.push(parseFloat('<%=CData%>'));
									data_xAxis.push('<%=CTime%>');
				<%
								}
						  }
				%>
						  json_serie.push({'name': '<%=Cpm_Name%>[<%=Oil_Unit%>]', 'data': data_serie});
						  json_xAxis.push({'categories': data_xAxis});
						  data_serie = [];
						  data_xAxis = [];
				<%
						}
					}
				}
				%>
				
				document.getElementById('container').style.height = document.body.offsetHeight + 'px';
			  var chart;
			  $(document).ready(function() {
			      chart = new Highcharts.Chart({
			          chart: {
			              renderTo: 'container',
			              type: 'line',
			              marginRight: 130,
			              marginBottom: 25
			          },
			          title: 
			          {
				          	<%
										switch(currStatus.getFunc_Sel_Id())
										{
											case 0:
										%>
													text: '年份',
										<%
												break;
											case 1:
										%>
													text: '月份',
										<%
												break;
											case 2:
										%>
													text: '周期',
										<%
												break;
											case 3:
										%>
													text: '日期',
										<%
												break;
										}
										%>
			              x: -20 //center
			          },
			          subtitle: 
			          {
			              text: '',
			              x: -20
			          },
			          series:json_serie,
			          xAxis:json_xAxis,
			          yAxis: 
			          {
			              title: {
			                  text: 'Y轴:数量'
			              },
			              plotLines: [{
			                  value: 0,
			                  width: 1,
			                  color: '#808080'
			              }]
			          },
			          tooltip: 
			          {
			              formatter: function() {
			                      return '<b>'+ this.series.name +'</b><br/>'+
			                      this.x +': '+ this.y;
			              }
			          },
			          legend: 
			          {
			              layout: 'vertical',
			              align: 'right',
			              verticalAlign: 'top',
			              x: -10,
			              y: 100,
			              borderWidth: 0
			          }			          
			      });
			  });
			});
			
<%	}
		break;
	case 2://柱状图表
		{
%>
			$(function ()
			{
				var json_serie = [];
				var json_xAxis = [];
				var data_serie = [];
				var data_xAxis = [];
				<%
				if(null != Pro_G && Pro_G.length() > 4)
				{
					Pro_G = Pro_G.substring(4);
					String[] temp1 = Pro_G.split("\\~");
					for(int i=0; i<temp1.length; i++)
					{
						String[] temp2 = temp1[i].split("\\^");
					  if(temp2.length > 4)//有数据
					  {
					  	String Cpm_Id   = temp2[0];
					    String Cpm_Name = temp2[1];
					    String Oil_Id   = temp2[2];
					    String Oil_Unit = temp2[3];
				      for(int j=4; j<temp2.length; j++)
							{
								String[] temp3 = temp2[j].split("\\@");
								for(int k=0; k<temp3.length; k++)
								{
									String CTime = temp3[k].split("\\$")[0];
									String CData = temp3[k].split("\\$")[1];
				%>
									data_serie.push(parseFloat('<%=CData%>'));
									data_xAxis.push('<%=CTime%>');
				<%
								}
						  }
				%>
						  json_serie.push({'name': '<%=Cpm_Name%>[<%=Oil_Unit%>]', 'data': data_serie});
						  json_xAxis.push({'categories': data_xAxis});
						  data_serie = [];
						  data_xAxis = [];
				<%
						}
					}
				}
				%>
				
				document.getElementById('container').style.height = document.body.offsetHeight + 'px';
			  var chart;
			  $(document).ready(function(){
			      chart = new Highcharts.Chart({
			          chart: {
			              renderTo: 'container',
			              type: 'column',
			              marginRight: 130,
			        			marginBottom: 25
			          },
			          title: 
			          {
			              <%
										switch(currStatus.getFunc_Sel_Id())
										{
											case 0:
										%>
													text: '年份',
										<%
												break;
											case 1:
										%>
													text: '月份',
										<%
												break;
											case 2:
										%>
													text: '周期',
										<%
												break;
											case 3:
										%>
													text: '日期',
										<%
												break;
										}
										%>
										x: -20 //center
			          },
			          subtitle:
			          {
			              text: '',
			              x: -20
			          },
			          series:json_serie,
			          xAxis:json_xAxis,
			          yAxis:
			          {
			              min: 0,
			              title: 
			              {
			                  text: 'Y轴:数量'
			              }
			          },
			          legend: 
			          {
			              layout: 'vertical',
			              backgroundColor: '#FFFFFF',
			              align: 'right',
			              verticalAlign: 'top',
			              x: 10,
			              y: 100,
			              floating: true,
			              shadow: true
			          },
			          tooltip: 
			          {
			              formatter: function() 
			              {
			                  return ''+
			                      this.x +': '+ this.y;
			              }
			          },
			          plotOptions: 
			          {
			              column: 
			              {
			                  pointPadding: 0.2,
			                  borderWidth: 0
			              }
			          }
			      });
			  });
			});
			
<%
		}
		break;
	case 3://比例图表
		{
%>
			$(function ()
			{
				var json_serie = [];
				var data_serie = [];
				var chart;
				<%
				if(null != Pro_G && Pro_G.length() > 4)
				{
					Pro_G = Pro_G.substring(4);
					String[] temp1 = Pro_G.split("\\~");
					for(int i=0; i<temp1.length; i++)
					{
						String[] temp2 = temp1[i].split("\\^");
					  if(temp2.length > 4)//有数据
					  {
				%>
							json_serie = [];
							data_serie = [];
				<%
					  	String Cpm_Id   = temp2[0];
					    String Cpm_Name = temp2[1];
					    String Oil_Id   = temp2[2];
					    String Oil_Unit = temp2[3];
				      for(int j=4; j<temp2.length; j++)
							{
								String[] temp3 = temp2[j].split("\\@");
								for(int k=0; k<temp3.length; k++)
								{
									String CTime = temp3[k].split("\\$")[0];
									String CData = temp3[k].split("\\$")[1];
				%>
									data_serie.push(['<%=CTime%>', parseFloat('<%=CData%>')]);
				<%
								}
						  }
				%>
							json_serie.push({'type': 'pie', 'name': '占比', 'data': data_serie});
							chart = new Highcharts.Chart(
						  {
						    chart: 
						    {
						      renderTo: 'container<%=i%>',
						      plotBackgroundColor: null,
						      plotBorderWidth: null,
						      plotShadow: false
						    },
						    title: 
						    {
						    	<%
									switch(currStatus.getFunc_Sel_Id())
									{
										case 0:
									%>
												text: '<%=Cpm_Name%>[年份]'
									<%
											break;
										case 1:
									%>
												text: '<%=Cpm_Name%>[月份]'
									<%
											break;
										case 2:
									%>
												text: '<%=Cpm_Name%>[周期]'
									<%
											break;
										case 3:
									%>
												text: '<%=Cpm_Name%>[日期]'
									<%
											break;
									}
									%>
						    },
						    tooltip: 
						    {
							    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',
						    	percentageDecimals: 1
						    },
						    plotOptions: 
						    {
						      pie: 
						      {
						        allowPointSelect: true,
						        cursor: 'pointer',
						        dataLabels: 
						        {
						          enabled: true,
						          color: '#000000',
						          connectorColor: '#000000',
						          formatter: function() 
						          {
						              return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(1) +' %';
						          }
						        },
						        showInLegend: false
						      }
						    },
						    series:json_serie
						  });
				<%
						}
					}
				}
				%>
				
			});
		}
<%
		}
		break;
}
%>
</script>
</html>