package bean;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rmi.Rmi;
import rmi.RmiBean;
import util.*;

public class CrmInfoBean extends RmiBean 
{
	public final static long serialVersionUID = RmiBean.RMI_CRM_INFO;
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public CrmInfoBean() 
	{
		super.className = "CrmInfoBean";
	}
	
	public void ExecCmd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		//类型
		Func_Sub_Id = currStatus.getFunc_Sub_Id()+"";
		if(Func_Sub_Id.equals("9"))
		{
			Func_Sub_Id = "";
		}
		
		msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
		switch(currStatus.getCmd())
		{
			case 10://添加
			case 11://编辑
				currStatus.setResult(MsgBean.GetResult(msgBean.getStatus()));
				msgBean = pRmi.RmiExec(0, this, 0);
			case 0://查询
		    	request.getSession().setAttribute("Crm_Info_" + Sid, ((Object)msgBean.getMsg()));
		    	currStatus.setJsp("Crm_Info.jsp?Sid=" + Sid);
		    	break;
		}
		
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
	   	response.sendRedirect(currStatus.getJsp());
	}
	
	public String getSql(int pCmd)
	{
		String Sql = "";
		switch (pCmd)
		{
			case 0://查询
				Sql = " select t.id, t.cname, t.brief, t.tel, t.ctime, t.ctype " +
					  " from crm_info t " +
					  " where t.ctype like '"+ Func_Sub_Id +"%'" +
					  " order by t.id";
				break;
			case 1://所有查询
				Sql = " select t.id, t.cname, t.brief, t.tel, t.ctime, t.ctype " +
					  " from crm_info t " +
					  " order by t.id";
				break;
			case 10://添加
				Sql = " insert into crm_info(id, cname, brief, tel, ctime, ctype)" +
					  " values('"+ Id +"', '"+ CName +"', '"+ Brief +"', '"+ Tel +"', '"+ CTime +"', '"+ CType +"')";
				break;
			case 11://编辑
				Sql = " update crm_info t set t.cname = '"+ CName +"', t.brief = '"+ Brief +"', t.tel = '"+ Tel +"', t.ctime = '"+ CTime +"', t.ctype = '"+ CType +"' " +
					  " where t.id = '"+ Id +"'";
				break;
		}
		return Sql;
	}
	
	public boolean getData(ResultSet pRs)
	{
		boolean IsOK = true;
		try
		{
			setId(pRs.getString(1));
			setCName(pRs.getString(2));
			setBrief(pRs.getString(3));
			setTel(pRs.getString(4));
			setCTime(pRs.getString(5));
			setCType(pRs.getString(6));
		}
		catch (SQLException sqlExp)
		{
			sqlExp.printStackTrace();
		}
		return IsOK;
	}
	
	public boolean getHtmlData(HttpServletRequest request)
	{
		boolean IsOK = true;
		try
		{
			setId(CommUtil.StrToGB2312(request.getParameter("Id")));
			setCName(CommUtil.StrToGB2312(request.getParameter("CName")));
			setBrief(CommUtil.StrToGB2312(request.getParameter("Brief")));
			setTel(CommUtil.StrToGB2312(request.getParameter("Tel")));
			setCTime(CommUtil.StrToGB2312(request.getParameter("CTime")));
			setCType(CommUtil.StrToGB2312(request.getParameter("CType")));
			
			
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
		}
		catch (Exception Exp)
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String Id;
	private String CName;
	private String Brief;
	private String Tel;
	private String CTime;
	private String CType;
	
	private String Sid;
	private String Func_Sub_Id;
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCName() {
		return CName;
	}

	public void setCName(String cName) {
		CName = cName;
	}

	public String getBrief() {
		return Brief;
	}

	public void setBrief(String brief) {
		Brief = brief;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getCTime() {
		return CTime;
	}

	public void setCTime(String cTime) {
		CTime = cTime;
	}

	public String getCType() {
		return CType;
	}

	public void setCType(String cType) {
		CType = cType;
	}

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public String getFunc_Sub_Id() {
		return Func_Sub_Id;
	}

	public void setFunc_Sub_Id(String funcSubId) {
		Func_Sub_Id = funcSubId;
	}
}