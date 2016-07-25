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

public class ProITankBean extends RmiBean 
{	
	public final static long serialVersionUID = RmiBean.RMI_PRO_I_TANK;
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public ProITankBean()
	{
		super.className = "ProITankBean";  
	}
	
	public void ExecCmd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		
		
		msgBean = pRmi.RmiExec(currStatus.getCmd(), this, currStatus.getCurrPage());
		switch(currStatus.getCmd())
		{					
								
		    case 0://²éÑ¯
		    	request.getSession().setAttribute("Pro_I_Tank_" + Sid, ((Object)msgBean.getMsg()));
		    	currStatus.setTotalRecord(msgBean.getCount());		    						    
		    	break;		    			 		    
		}		
	}
	

	public String getSql(int pCmd)
	{
		String Sql = "";
		switch (pCmd) 
		{
			case 0:
				Sql = " SELECT t.sn, t.cpm_id, t.order_id, t.tank_no, t.ctime, t.Pre_Tank_V, t.Lat_Tank_V, t.Unload, t.Pre_Temper, t.Lat_Temper, t.Pre_Press, t.Lat_Press " +
					  " FROM pro_i_tank t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +			  	 
				  	  " and t.order_id = '"+ Func_Type_Id +"'" +	
				  	  " and t.ctime = '"+ CTime +"'" +	
				  	  " order by t.tank_no asc " ;
												
				break;
			case 10://Ìí¼Ó
				Sql = " insert into pro_i_tank ( cpm_id, order_id, tank_no, ctime, Pre_Tank_V, Lat_Tank_V, Unload, Pre_Temper, Lat_Temper, Pre_Press, Lat_Press)" +
					  " values('"+ Cpm_Id +"', '"+ Order_Id +"',  '"+ Tank_No +"', '"+ CTime +"', '"+ Pre_Tank_V +"', '"+ Lat_Tank_V +"', '"+ Unload +"', '"+ Pre_Temper +"', '"+ Lat_Temper +"', '"+ Pre_Press +"', '"+ Lat_Press +"')";
				break;
			case 12://ÐÞ¸Ä
				Sql = " update pro_i_tank t  set t.Pre_Tank_V='"+ Pre_Tank_V +"', t.Lat_Tank_V='"+ Lat_Tank_V +"', t.Unload='"+ Unload +"', t.Pre_Temper='"+ Pre_Temper +"',t.Lat_Temper= '"+ Lat_Temper +"', t.Pre_Press='"+ Pre_Press +"', t.Lat_Press='"+ Lat_Press +"'  where t.sn = '"+ SN +"' ";								
				break;
	
		}
		return Sql;
	}
	public boolean getData(ResultSet pRs) 
	{
		boolean IsOK = true;
		try
		{
			setSN(pRs.getString(1));
			setCpm_Id(pRs.getString(2));	
			setOrder_Id(pRs.getString(3));
			setTank_No(pRs.getString(4));
			setCTime(pRs.getString(5));
			setPre_Tank_V(pRs.getString(6));
			setLat_Tank_V(pRs.getString(7));
			setUnload(pRs.getString(8));
			setPre_Temper(pRs.getString(9));
			setLat_Temper(pRs.getString(10));
			setPre_Press(pRs.getString(11));
			setLat_Press(pRs.getString(12));
			
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
			setSN(CommUtil.StrToGB2312(request.getParameter("SN")));
			setCpm_Id(CommUtil.StrToGB2312(request.getParameter("Cpm_Id")));
			setOrder_Id(CommUtil.StrToGB2312(request.getParameter("Order_Id")));
			setTank_No(CommUtil.StrToGB2312(request.getParameter("Tank_No")));
			setCTime(CommUtil.StrToGB2312(request.getParameter("CTime")));
			setPre_Tank_V(CommUtil.StrToGB2312(request.getParameter("Pre_Tank_V")));
			setLat_Tank_V(CommUtil.StrToGB2312(request.getParameter("Lat_Tank_V")));
			setUnload(CommUtil.StrToGB2312(request.getParameter("Unload")));
			setPre_Temper(CommUtil.StrToGB2312(request.getParameter("Pre_Temper")));
			setLat_Temper(CommUtil.StrToGB2312(request.getParameter("Lat_Temper")));
			setPre_Press(CommUtil.StrToGB2312(request.getParameter("Pre_Press")));
			setLat_Press(CommUtil.StrToGB2312(request.getParameter("Lat_Press")));
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
			
		}
		catch (Exception Exp)
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String Sid;
	private String SN;	
	private String Cpm_Id;
	private String Order_Id;
	private String Tank_No;
	private String CTime;	
	private String Pre_Tank_V;
	private String Lat_Tank_V;
	private String Unload;
	private String Pre_Temper;
	private String Lat_Temper;
	private String Pre_Press;
	private String Lat_Press;
	
	
	private String Func_Corp_Id;
	private String Func_Type_Id;
	
	
	public String getFunc_Corp_Id() {
		return Func_Corp_Id;
	}

	public void setFunc_Corp_Id(String func_Corp_Id) {
		Func_Corp_Id = func_Corp_Id;
	}

	public String getFunc_Type_Id() {
		return Func_Type_Id;
	}

	public void setFunc_Type_Id(String func_Type_Id) {
		Func_Type_Id = func_Type_Id;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public String getCpm_Id() {
		return Cpm_Id;
	}

	public void setCpm_Id(String cpm_Id) {
		Cpm_Id = cpm_Id;
	}
	

	public String getCTime() {
		return CTime;
	}

	public void setCTime(String cTime) {
		CTime = cTime;
	}

	public String getOrder_Id() {
		return Order_Id;
	}

	public void setOrder_Id(String order_Id) {
		Order_Id = order_Id;
	}

	public String getTank_No() {
		return Tank_No;
	}

	public void setTank_No(String tank_No) {
		Tank_No = tank_No;
	}

	public String getPre_Tank_V() {
		return Pre_Tank_V;
	}

	public void setPre_Tank_V(String pre_Tank_V) {
		Pre_Tank_V = pre_Tank_V;
	}

	public String getLat_Tank_V() {
		return Lat_Tank_V;
	}

	public void setLat_Tank_V(String lat_Tank_V) {
		Lat_Tank_V = lat_Tank_V;
	}

	public String getUnload() {
		return Unload;
	}

	public void setUnload(String unload) {
		Unload = unload;
	}

	public String getPre_Temper() {
		return Pre_Temper;
	}

	public void setPre_Temper(String pre_Temper) {
		Pre_Temper = pre_Temper;
	}

	public String getLat_Temper() {
		return Lat_Temper;
	}

	public void setLat_Temper(String lat_Temper) {
		Lat_Temper = lat_Temper;
	}

	public String getPre_Press() {
		return Pre_Press;
	}

	public void setPre_Press(String pre_Press) {
		Pre_Press = pre_Press;
	}

	public String getLat_Press() {
		return Lat_Press;
	}

	public void setLat_Press(String lat_Press) {
		Lat_Press = lat_Press;
	}
	
}