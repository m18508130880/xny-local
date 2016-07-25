<%@ page contentType="text/html; charset=gb2312" %>  
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %>
<%@ page import="util.*" %>
<%@ taglib uri="/WEB-INF/limitvalidatetag.tld" prefix="Limit"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中新能源LNG公司级信息化管理平台</title>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="../skin/js/util.js"></script>
<script type="text/javascript" src="../skin/js/day.js"></script>
<%

	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	ArrayList User_FP_Role = (ArrayList)session.getAttribute("User_FP_Role_" + Sid);
	UserInfoBean UserInfo  = (UserInfoBean)session.getAttribute("UserInfo_" + Sid);
	ArrayList User_Manage_Role   = (ArrayList)session.getAttribute("User_Manage_Role_" + Sid);
	String HP_LoginId = UserInfo.getHP_LoginId();
	String HP_LoginPwd = UserInfo.getHP_LoginPwd();
	String HP_LoginIp = UserInfo.getHP_LoginIp();
	String HP_LoginPort = UserInfo.getHP_LoginPort();
	if(null == HP_LoginId){HP_LoginId = "";}
	if(null == HP_LoginPwd){HP_LoginPwd = "";}
	if(null == HP_LoginIp){HP_LoginIp = "";}
	if(null == HP_LoginPort){HP_LoginPort = "";}
	
	//功能权限
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
	
	//管理权限
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
	
	String p3 = CommUtil.StrToGB2312(request.getParameter("p3"));
	if(null == p3)
	{
		p3 = "1";
	}		
	ArrayList User_User_Info  = (ArrayList)session.getAttribute("User_User_Info_" + Sid);
	String SYS_List = "";
								if( null != User_User_Info )
								{
									Iterator iterator = User_User_Info.iterator();
									while(iterator.hasNext())
									{
										UserInfoBean usertBean = (UserInfoBean)iterator.next();		
										String sys = 	usertBean.getSys_Id();							
										SYS_List  = SYS_List+sys;
									
									}
								}
	
										
%>
</head>
<body style="background:#0B80CC;">
<div id="PARENT" >
	<ul id="nav">
		<li id="li01" style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='01' ctype='1'/>"><a href="#" onClick="doGIS()"            >GIS监控</a></li>
		<li id="li02" style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='02' ctype='1'/>"><a href="#" onClick="DoMenu('UserMenu2')">销售统计</a></li>
			 <ul id="UserMenu2" class="collapsed">
				<!-- 
				 <li id="Display0201"><a href="#" onClick="doPro_R()"           style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0201' ctype='1'/>">资源调度</a></li>
	   		 <li id="Display0203"><a href="#" onClick="doPro_O()"           style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0203' ctype='1'/>">加注记录</a></li>
				-->
				<li id="Display0202"><a href="#" onClick="doPro_I()"           style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0202' ctype='1'/>">卸车记录</a></li>
				<li id="Display0204"><a href="#" onClick="doGraph()"                                                                                              >图表分析</a></li>
			 </ul>			
		<li id="li03" style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='03' ctype='1'/>"><a href="#" onClick="DoMenu('UserMenu3')">报表统计</a></li>	 
			 <ul id="UserMenu3" class="collapsed">
				 <li id="Display0301"><a href="#" onClick="doPro_L_Crm()"		 	  style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0301' ctype='1'/>">销量确认表</a></li>
				 <li id="Display0302"><a href="#" onClick="doPro_L()"					  style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0302' ctype='1'/>">场站报表</a></li>
				 <li id="Display0303"><a href="#" onClick="doPro_L_Crp()"			  style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0303' ctype='1'/>">公司报表</a></li>			
	   	 </ul>
 	 	<li id="li04" style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='04' ctype='1'/>"><a href="#" onClick="DoMenu('UserMenu4')">生产数据</a></li>
			 <ul id="UserMenu4" class="collapsed">
				 <li id="Display0401"><a href="#" onClick="doEnv()"                                                                                                >实时数据</a></li>
	   		 <li id="Display0402"><a href="#" onClick="doHis()"                                                                                                >历史数据</a></li>
				<!-- <li id="Display0403"><a href="#" onClick="doGra()"                                                                                            >数据图表</a></li>-->
			 </ul>
		
    <li id="li05" style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='05' ctype='1'/>"><a href="#" onClick="doAlarm_Info()">告警管理</a></li>
	   <!-- <ul id="UserMenu5" class="collapsed">
				<li id="Display0501"><a href="#" onClick="doAlarm_Info()"       style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0501' ctype='1'/>" >告警日志</a></li>
	   		<li id="Display0502"><a href="#" onClick="doAlert_Info()"       style="display:<Limit:limitValidate userrole='<%=FpList%>' fpid='0502' ctype='1'/>" >联动日志</a></li> 
	   	</ul>--> 
	</ul>
</div>
</body>
<script language='javascript'>
//初始化
switch(parseInt('<%=p3%>'))
{
	default:
	case 1:
			if('<Limit:limitValidate userrole='<%=FpList%>' fpid='01' ctype='1'/>' == '')
			{
				window.parent.frames.mFrame.location = 'MapMain_Map.jsp?Sid=<%=Sid%>';
			}
			else
			{
				window.parent.frames.mFrame.location = 'User_Info.jsp?Sid=<%=Sid%>';
			}
		break;
	case 2:
		break;
	case 3:
			if('<Limit:limitValidate userrole='<%=FpList%>' fpid='03' ctype='1'/>' == '')
			{
				window.parent.frames.mFrame.location = 'Device_Detail.do?Cmd=1&Sid=<%=Sid%>';
			}
			else
			{
				window.parent.frames.mFrame.location = 'User_Info.jsp?Sid=<%=Sid%>';
			}
		break;
}
//菜单Menu
var LastLeftID = "";
function DoMenu(emid)
{
	 var obj = document.getElementById(emid); 
	 obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
	 if((LastLeftID!="")&&(emid!=LastLeftID)) //关闭上一个Menu
	 {
	  	document.getElementById(LastLeftID).className = "collapsed";
	 }
	 LastLeftID = emid;
}
//菜单颜色变化
var LastsubID = "";
function DoDisplay(emid)
{
	 var obj = document.getElementById(emid); 
	 obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
	 if((LastsubID!="")&&(emid!=LastsubID)) //关闭上一个
	 {
	  	document.getElementById(LastsubID).className = "collapsed";
	 }
	 LastsubID = emid;
}


function doGIS()
{
	window.parent.frames.mFrame.location = 'MapMain_Map.jsp?Sid=<%=Sid%>';
}


/****************************************销售统计*****************************************************************/
//资源调度
function doPro_R()
{
	window.parent.frames.mFrame.location = "Pro_R.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Sub_Id=9&Func_Corp_Id=9999&Func_Sel_Id=9";
}
//卸车记录
function doPro_I()
{
	window.parent.frames.mFrame.location = "Pro_I.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Sub_Id=0&Func_Corp_Id=9999";
}
//加注记录
function doPro_O()
{
	window.parent.frames.mFrame.location = "Pro_O.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Sub_Id=9&Func_Corp_Id=3002";
}

//图标分析
function doGraph()
{
	//按月分析
	var BTime = showPreviousFirstDay().format("yyyy-MM-dd");
  var BYear  = BTime.substring(0,4);
  var BMonth = BTime.substring(5,7);
  var EYear  = BTime.substring(0,4);
  var EMonth = BTime.substring(5,7);
  <%
	if("9999999999".equals(Manage_List) )
	{
		Manage_List = "0100000001";
	
	}		
	%>
  window.parent.frames.mFrame.location = "Pro_G.do?Cmd=20&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Corp_Id=3002&Func_Sub_Id=1&Func_Sel_Id=1&BYear="+BYear+"&BMonth="+BMonth+"&EYear="+EYear+"&EMonth="+EMonth;
}

//---------------------------------------------------报表统计-------------------------------------------------
//购销统计表
function doPro_GX()
{
	var BTime = showPreviousFirstDay().format("yyyy-MM-dd");
  var ETime = showPreviousLastDay().format("yyyy-MM-dd");
  var Year  = BTime.substring(0,4);
  var Month = BTime.substring(5,7);
	window.parent.frames.mFrame.location = "Pro_GX_ZYB.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id=0100000001&Func_Cpm_Id=0100000001&Func_Sub_Id=1&Func_Corp_Id=3002&BTime="+BTime+"&ETime="+ETime+"&Year="+Year+"&Month="+Month;
}
//销量确认表
function doPro_L_Crm()
{
	window.parent.frames.mFrame.location = "Pro_L_Crm.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Cpm_Id=<%=Manage_List%>&Func_Sub_Id=9&Func_Corp_Id=9999&Crm_Id=";
}
//对账表
function doPro_L_Stat()
{
	window.parent.frames.mFrame.location = "Pro_L_Stat.do?Cmd=1&Sid=<%=Sid%>&Cpm_Id=0100000001&Func_Cpm_Id=0100000001&DW_ID=0000000001&Func_Sub_Id=9&Func_Corp_Id=1000";
}
//次数对账
function doPro_L_CS()
{
	window.parent.frames.mFrame.location = "Pro_L_Crm.do?Cmd=1&Sid=<%=Sid%>&Func_Cpm_Id=<%=Manage_List%>&Func_Sub_Id=9&Func_Corp_Id=9999&Cpm_Id=";
}
//场站报表
function doPro_L()
{
  var BTime = showPreviousFirstDay().format("yyyy-MM-dd");
  var ETime = showPreviousLastDay().format("yyyy-MM-dd");
  var Year  = BTime.substring(0,4);
  var Month = BTime.substring(5,7);
  
	window.parent.frames.mFrame.location = "Pro_L.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Cpm_Id=<%=Manage_List%>&Func_Sub_Id=1&Func_Corp_Id=3002&BTime="+BTime+"&ETime="+ETime+"&Year="+Year+"&Month="+Month;
}
//公司报表
function doPro_L_Crp()
{
	var BTime = showPreviousFirstDay().format("yyyy-MM-dd");
  var ETime = showPreviousLastDay().format("yyyy-MM-dd");
  var Year  = BTime.substring(0,4);
  var Month = BTime.substring(5,7);
	window.parent.frames.mFrame.location = "Pro_L_Crp.do?Cmd=1&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Cpm_Id=<%=Manage_List%>&Func_Sub_Id=1&Func_Corp_Id=3002&BTime="+BTime+"&ETime="+ETime+"&Year="+Year+"&Month="+Month;
}
//槽车统计表
function doPro_CC()
{
	
	window.parent.frames.mFrame.location = "Pro_I_CC.do?Cmd=1&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Func_Sub_Id=0&Func_Sel_Id=3&Func_Corp_Id=9999";
}


/****************************************************生产数据*************************************************************/
//生产实时数据
function doEnv()
{
	window.parent.frames.mFrame.location = 'Env.do?Cmd=0&Id=<%=Manage_List%>&Level=2&Sid=<%=Sid%>&Func_Sub_Id=1';
}
//生产历史数据
function doHis()
{
	window.parent.frames.mFrame.location = 'Env.do?Cmd=2&Id=<%=Manage_List%>&Level=2&Sid=<%=Sid%>';
}
//生产数据图表
function doGra()
{
	var TDay = new Date().format("yyyy-MM-dd");
	window.parent.frames.mFrame.location = "Graph.do?Cmd=20&Id=0100000001&Sid=<%=Sid%>&Level="+'4'+"&BTime="+TDay+"&Func_Sub_Id=1";
}

/**************************************************告警管理*****************************************************************/
//告警日志
function doAlarm_Info()
{
	window.parent.frames.mFrame.location = "Alert_Info.do?Cmd=0&Sid=<%=Sid%>&Cpm_Id=<%=Manage_List%>&Id=<%=Manage_List%>&Func_Sub_Id=9&Func_Corp_Id=9999&Func_Sel_Id=9";
	
}
//联动日志
function doAlert_Info()
{
	window.parent.frames.mFrame.location = "Alarm_Info.do?Cmd=0&Sid=<%=Sid%>";
}

</script>
</html>