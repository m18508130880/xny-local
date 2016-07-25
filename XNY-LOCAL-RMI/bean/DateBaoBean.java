package bean;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import rmi.Rmi;
import rmi.RmiBean;
import util.*;

public class DateBaoBean extends RmiBean 
{	
	public final static long serialVersionUID = RmiBean.RMI_DATE_BAO;
	public long getClassId()
	{
		return serialVersionUID;
	}

	public DateBaoBean() 
	{
		super.className = "DateBaoBean";
	}
	
	public void ExecCmd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
	
		
		msgBean = pRmi.RmiExec(currStatus.getCmd(), this, currStatus.getCurrPage());
		switch(currStatus.getCmd())
		{
			case 10 ://添加				
			case 0: //查询
				msgBean = pRmi.RmiExec(0, this, 0);
				request.getSession().setAttribute("Pro_L_Bao_" + Sid, ((Object)msgBean.getMsg()));
				currStatus.setJsp("Pro_L_D.jsp?Sid=" + Sid);
				break;				
		}
		ProLBean pBean = new ProLBean();
		pBean.setCpm_Id(Cpm_Id);
		pBean.currStatus = currStatus;
		msgBean = pRmi.RmiExec(4, pBean, 0);
		request.getSession().setAttribute("Pro_L_D_" + Sid, ((Object)msgBean.getMsg()));
		
		ProLCrmBean ProLCrm = new ProLCrmBean();
    	ProLCrm.setCpm_Id(Cpm_Id);
    	ProLCrm.currStatus = currStatus;
    	msgBean = pRmi.RmiExec(0, ProLCrm, 0);
    	request.getSession().setAttribute("Pro_L_Crm_" + Sid, ((Object)msgBean.getMsg()));
    	
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
	   	response.sendRedirect(currStatus.getJsp());
	}
	
	public void WxhDate(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone)
	{
		try 
		{
			getHtmlData(request);
			currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
			currStatus.getHtmlData(request, pFromZone);
			String str = "";			
			ArrayList carlist;
			PrintWriter outprint = response.getWriter();
			msgBean = pRmi.RmiExec(0, this, 0);		
			if(null != msgBean.getMsg())
			{
				 carlist = (ArrayList)msgBean.getMsg();
				if(carlist != null)
				{
					Iterator iterator = carlist.iterator();
					while(iterator.hasNext())
					{
						DateBaoBean Bean = (DateBaoBean)iterator.next();						
						str = Bean.getZ_Person()+","+Bean.getC_Person()+","+Bean.getDanger()+","+Bean.getPeccancy()+","+Bean.getXiaoFang()+","+Bean.getBaoJing()+","+Bean.getTongXun()+","+Bean.getJiJiu();				
					}
				}
			}else
			{
			 str = "1";	
			 }				
			request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
			outprint.write(str);
		}
		catch (Exception Ex)
		{
			Ex.printStackTrace();
		}
	}
	
	public String getSql(int pCmd)
	{
		String Sql = "";
		switch (pCmd)
		{
			case 0://查询
				Sql = " select t.sn, t.cpm_id, t.ctime, t.z_person, t.c_person, t.danger, t.peccancy, t.xiaofang, t.baojing, t.tongxun, t.jijiu " +
					  " from date_bao t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.ctime = '"+currStatus.getVecDate().get(0).toString().substring(0,10)+"' " ;					 
				break;
				
			case 10: //添加
				
				Sql = " insert into date_bao(cpm_id, ctime, z_person, c_person, danger, peccancy, xiaofang, baojing, tongxun, jijiu )"+
					  " value('"+ Cpm_Id +"', '"+ CTime +"', '"+ Z_Person +"', '"+ C_Person +"', '"+ Danger +"', '"+ Peccancy +"', '"+ XiaoFang +"', '"+ BaoJing +"', '"+ TongXun +"', '"+ JiJiu +"')";
				break;
			case 11:
				
				Sql = " update date_bao t set t.z_person = '"+ Z_Person +"', t.c_person = '"+ C_Person +"', t.danger= '"+ Danger +"', t.peccancy= '"+ Peccancy +"', t.xiaofang='"+ XiaoFang +"', t.baojing='"+ BaoJing +"', t.tongxun='"+ TongXun +"', t.jijiu = '"+ JiJiu +"' " +
					  " where t.cpm_id = '"+ Cpm_Id +"' and t.ctime = '"+ CTime +"' ";
				
				break;
			case 12 :
				Sql = "delete from date_bao where cpm_id= '"+ Cpm_Id +"' and CTime = '"+CTime+"'";
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
			setCTime(pRs.getString(3));
			setZ_Person(pRs.getString(4));
			setC_Person(pRs.getString(5));
			setDanger(pRs.getString(6));
			setPeccancy(pRs.getString(7));
			setXiaoFang(pRs.getString(8));
			setBaoJing(pRs.getString(9));
			setTongXun(pRs.getString(10));
			setJiJiu(pRs.getString(11));		
		
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
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
			setSN(CommUtil.StrToGB2312(request.getParameter("SN")));
			setCpm_Id(CommUtil.StrToGB2312(request.getParameter("Cpm_Id")));
			setCTime(CommUtil.StrToGB2312(request.getParameter("CTime")));
			setZ_Person(CommUtil.StrToGB2312(request.getParameter("Z_Person")));
			setC_Person(CommUtil.StrToGB2312(request.getParameter("C_Person")));
			setDanger(CommUtil.StrToGB2312(request.getParameter("Danger")));
			setPeccancy(CommUtil.StrToGB2312(request.getParameter("Peccancy")));
			setXiaoFang(CommUtil.StrToGB2312(request.getParameter("XiaoFang")));
			setBaoJing(CommUtil.StrToGB2312(request.getParameter("BaoJing")));
			setTongXun(CommUtil.StrToGB2312(request.getParameter("TongXun")));
			setJiJiu(CommUtil.StrToGB2312(request.getParameter("JiJiu")));
			setFunc_Corp_Id(CommUtil.StrToGB2312(request.getParameter("Func_Corp_Id")));
								
		}
		catch (Exception Exp)
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String SN;
	private String Cpm_Id;
	private String CTime;
	private String Z_Person;
	private String C_Person;
	private String Danger;
	private String Peccancy;
	private String XiaoFang;
	private String BaoJing;
	private String TongXun;
	private String JiJiu;
	
	private String Sid;
	private String Func_Corp_Id;
	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
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

	public String getZ_Person() {
		return Z_Person;
	}

	public void setZ_Person(String z_Person) {
		Z_Person = z_Person;
	}

	public String getC_Person() {
		return C_Person;
	}

	public void setC_Person(String c_Person) {
		C_Person = c_Person;
	}

	public String getDanger() {
		return Danger;
	}

	public void setDanger(String danger) {
		Danger = danger;
	}

	public String getPeccancy() {
		return Peccancy;
	}

	public void setPeccancy(String peccancy) {
		Peccancy = peccancy;
	}

	public String getXiaoFang() {
		return XiaoFang;
	}

	public void setXiaoFang(String xiaoFang) {
		XiaoFang = xiaoFang;
	}

	public String getBaoJing() {
		return BaoJing;
	}

	public void setBaoJing(String baoJing) {
		BaoJing = baoJing;
	}

	public String getTongXun() {
		return TongXun;
	}

	public void setTongXun(String tongXun) {
		TongXun = tongXun;
	}

	public String getJiJiu() {
		return JiJiu;
	}

	public void setJiJiu(String jiJiu) {
		JiJiu = jiJiu;
	}

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public String getFunc_Corp_Id() {
		return Func_Corp_Id;
	}

	public void setFunc_Corp_Id(String func_Corp_Id) {
		Func_Corp_Id = func_Corp_Id;
	}


}
