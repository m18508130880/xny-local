<%@ page contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ page import="bean.*"%>
<%@ page import="util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中新能源LNG公司级信息化管理平台</title>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<link type="text/css" href="../skin/css/style.css" rel="stylesheet"/>
<script language=javascript>document.oncontextmenu=function(){window.event.returnValue=false;};</script>
<%
	
	String Sid = CommUtil.StrToGB2312(request.getParameter("Sid"));
	
%>
</head>
<body style="background:#0B80CC;">
<div id="PARENT">
	<ul id="nav">
		<li>
			<a href="#" onclick="DoMenu('ChildMenu1')"                                          >公司信息</a>
			<ul id="ChildMenu1" class="collapsed">
				<li><a href="Corp_Info.do?Cmd=0&Sid=<%=Sid%>"                 target="mFrame">公司配置</a></li>
	   		<li><a href="User_Role.do?Cmd=0&Sid=<%=Sid%>"                 target="mFrame">功能权限</a></li>
				<li><a href="User_Role.do?Cmd=1&Sid=<%=Sid%>"                 target="mFrame">管理权限</a></li>
				<li><a href="User_Info.do?Cmd=1&Func_Corp_Id=99&Sid=<%=Sid%>" target="mFrame">公司人员</a></li>
	   	</ul>
		</li>
		<li>
			<a href="#" onclick="DoMenu('ChildMenu2')"                                          >站级信息</a>
			<ul id="ChildMenu2" class="collapsed">
				<li><a href="Device_Detail.do?Cmd=0&Sid=<%=Sid%>"             target="mFrame">站级配置</a></li>
				<li><a href="Map.jsp?Sid=<%=Sid%>"                            target="mFrame">站级标注</a></li>
				
			<!--<li><a href="User_Info.do?Cmd=3&Func_Corp_Id=9999999999&Sid=<%=Sid%>" target="mFrame">站级人员</a></li>-->
	   	</ul>
		</li>

	</ul>
</div>
</body>
<SCRIPT LANGUAGE=javascript>
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

function DoHide(emid)
{ 
	 document.getElementById(emid).className = "collapsed";	 
}
</SCRIPT>
</html>