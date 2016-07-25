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

public class CcmInfoBean extends RmiBean 
{
	public final static long serialVersionUID = RmiBean.RMI_CCM_INFO;
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public CcmInfoBean() 
	{
		super.className = "CcmInfoBean";
	}
	 
	public void ExecCmd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
		switch(currStatus.getCmd())
		{
			case 15 ://…æ≥˝
			case 10://ÃÌº”
			case 11://±‡º≠
				currStatus.setResult(MsgBean.GetResult(msgBean.getStatus()));
				msgBean = pRmi.RmiExec(0, this, 0);
			case 0://≤È—Ø
		    	request.getSession().setAttribute("Ccm_Info_" + Sid, ((Object)msgBean.getMsg()));		    	
		    	//CorpInfo
		    	CorpInfoBean corpBean = new CorpInfoBean();
		    	msgBean = pRmi.RmiExec(0, corpBean, 0);
		    	request.getSession().setAttribute("Corp_Info_" + Sid, (CorpInfoBean)((ArrayList<?>)msgBean.getMsg()).get(0));		    	
		    	currStatus.setJsp("Ccm_Info.jsp?Sid=" + Sid + "&Crm_Id=" + Crm_Id);
		    	break;
		}
		
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
	   	response.sendRedirect(currStatus.getJsp());
	}
	
	//ø®∫≈≤Ÿ◊˜
	public void CardAdd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		PrintWriter outprint = response.getWriter();
		String Resp = "9999";
		
		msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
		if(msgBean.getStatus() == MsgBean.STA_SUCCESS)
		{
			Resp = "0000";
		}
		
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
		outprint.write(Resp);
	}
	//≥µ¡æ–≈œ¢œ‘ æ
	public void IdCar(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone)
	{
		try 
		{
			getHtmlData(request);
			currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
			currStatus.getHtmlData(request, pFromZone);
			String str = "";
			String str1 = "";
			String ctype="";
			ArrayList carlist;
			PrintWriter outprint = response.getWriter();
			msgBean = pRmi.RmiExec(3, this, 0);		
			if(null != msgBean.getMsg())
			{
				 carlist = (ArrayList)msgBean.getMsg();
				if(carlist != null)
				{
					Iterator iterator = carlist.iterator();
					while(iterator.hasNext())
					{
						CcmInfoBean Bean = (CcmInfoBean)iterator.next();
						ctype = Bean.getCType();
						str = Bean.getCrm_Id()+","+Bean.getId()+","+Bean.getCType()+","+Bean.getOwner()+","+Bean.getBottle()+","+Bean.getIC()+","+Bean.getCName()+","+Bean.getBrief();				
						CorpInfoBean corpBean = new CorpInfoBean();
						msgBean = pRmi.RmiExec(0, corpBean, 0);
						if(null != msgBean.getMsg())
						{
							 carlist = (ArrayList)msgBean.getMsg();
							if(carlist != null)
							{
								 iterator = carlist.iterator();
								while(iterator.hasNext())
								{
									corpBean = (CorpInfoBean)iterator.next();	
									String[] strs = corpBean.getCar_Info().split(";");
									str1 = strs[0];
									for(int j =0;j<strs.length&&strs[j].length()>0;j++)
									{
										String[] substr = strs[j].split(",");
										if(ctype.equals(substr[0]))
										{
											str1 = substr[0]+","+substr[1];
											break;
										}else{
											str1 = "2";
										}
									}
								}
							}
						}			
					}
				}
			}else
			{
			 str = "1";	
			 }				
			request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
			outprint.write(str+","+str1);
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
			case 0://≤È—Ø
				Sql = " select t.crm_id, t.id, t.ctype, t.owner, t.bottle, t.ic,t.cname,t.brief " +
					  " from view_ccomp_info t " +
					  " where t.crm_id = '"+ Crm_Id +"' " +
					  " order by t.id";
				break;
				//≥µ≈∆πÈ Ùµÿ≤È—Ø
			case 1:
				Sql = " SELECT a.crm_id, a.id, a.ctype, a.owner, a.bottle, a.ic,a.cname, a.brief  FROM (SELECT t.crm_id, SUBSTRING(t.id,1,3) AS id, t.ctype, t.owner, t.bottle, t.ic,t.cname,t.brief  FROM view_ccomp_info t ) AS a GROUP BY a.id ORDER BY a.id DESC ";
				
				break;								
			case 10://ÃÌº”
				Sql = " insert into ccm_info(crm_id, id, ctype, owner, bottle)" +
					  " values('"+ Crm_Id +"', '"+ Id +"', '"+ CType +"', '"+ Owner +"', '"+ Bottle +"')";
				break;
			case 11://±‡º≠
				Sql = " update ccm_info t set t.ctype = '"+ CType +"', t.owner = '"+ Owner +"', t.bottle = '"+ Bottle +"' " +
					  " where t.id = '"+ Id +"'";
				break;
			case 13://ÃÌº”ø®∫≈
		    	Sql = " update ccm_info t set t.ic = concat(t.ic, '"+ IC +"') where t.id = '"+ Id +"'";
		    	break;
		    case 14://…æ≥˝ø®∫≈°¢÷ÿ÷√ø®∫≈
		    	Sql = " update ccm_info t set t.ic = '"+ IC +"' where t.id = '"+ Id +"'";
		    	break;
		    case 15://…æ≥˝≥µ¡æ
		    	Sql = " delete from ccm_info where id = '"+ Id +"' ";
		    	break;
			case 3://≥µ¡æ–≈œ¢≤È—Ø
				Sql = "select a.crm_id,a.id,a.ctype,a.owner,a.bottle,a.ic,a.cname,a.brief  from view_ccomp_info a where a.id = '"+Id+"'";
				break;
		}
		return Sql;
	}
	
	public boolean getData(ResultSet pRs)
	{
		boolean IsOK = true;
		try
		{
			setCrm_Id(pRs.getString(1));
			setId(pRs.getString(2));
			setCType(pRs.getString(3));
			setOwner(pRs.getString(4));
			setBottle(pRs.getString(5));
			setIC(pRs.getString(6));
			
			setCName(pRs.getString(7));
			setBrief(pRs.getString(8));
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
			setCrm_Id(CommUtil.StrToGB2312(request.getParameter("Crm_Id")));
			setId(CommUtil.StrToGB2312(request.getParameter("Id")));
			setCType(CommUtil.StrToGB2312(request.getParameter("CType")));
			setOwner(CommUtil.StrToGB2312(request.getParameter("Owner")));
			setBottle(CommUtil.StrToGB2312(request.getParameter("Bottle")));
			setIC(CommUtil.StrToGB2312(request.getParameter("IC")));
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
			setCName(CommUtil.StrToGB2312(request.getParameter("CName")));
			setBrief(CommUtil.StrToGB2312(request.getParameter("Brief")));
		}
		catch (Exception Exp)
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String Crm_Id;
	private String Id;
	private String CType;
	private String Owner;
	private String Bottle;
	private String IC;
	private String Sid;
	private String CName;
	private String Brief;
	

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
	public String getCrm_Id() {
		return Crm_Id;
	}
	public void setCrm_Id(String crmId) {
		Crm_Id = crmId;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCType() {
		return CType;
	}

	public void setCType(String cType) {
		CType = cType;
	}

	public String getOwner() {
		return Owner;
	}

	public void setOwner(String owner) {
		Owner = owner;
	}

	public String getBottle() {
		return Bottle;
	}

	public void setBottle(String bottle) {
		Bottle = bottle;
	}

	public String getIC() {
		return IC;
	}

	public void setIC(String iC) {
		IC = iC;
	}

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}
}