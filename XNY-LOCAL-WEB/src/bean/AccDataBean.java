package bean;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;




import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;




import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import rmi.Rmi;
import rmi.RmiBean;
import util.*;


/** 
 * 累积流量数据处理bean(每天的流量用量) 
 * AccDataBean数据处理bean
 * @author cui
 * 
 */
public class AccDataBean extends RmiBean 
{	
	public final static long serialVersionUID = RmiBean.RMI_ACC_DATA;
	
	/* 获得DataBean的 serialVersionUID (non-Javadoc)
	 * @see rmi.RmiBean#getClassId()
	 */
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public AccDataBean()
	{
		super.className = "AccDataBean";
	}
	
	/** 累积流量 查询
	 * @param request
	 * @param response
	 * @param pRmi
	 * @param pFromZone
	 * @throws ServletException
	 * @throws IOException
	 */
	public void ExecCmd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		switch(currStatus.getCmd())
		{
		    case 0://各站点：累积流量
		    	msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
		    	request.getSession().setAttribute("Acc_Data_Sta_" + Sid, ((Object)msgBean.getMsg()));
		    	currStatus.setJsp("Acc_Data_Sta.jsp?Sid=" + Sid);	
		    	break;
		    	
		    case 1://日用量总表
		    	msgBean = pRmi.RmiExec(currStatus.getCmd(), this, currStatus.getCurrPage());
		    	currStatus.setTotalRecord(msgBean.getCount());
		    	request.getSession().setAttribute("Acc_Data_Day_" + Sid, ((Object)msgBean.getMsg()));
		    	currStatus.setJsp("Acc_Data_Day.jsp?Sid=" + Sid);
		    	break;
		    	
		    case 2://月用量总表
		    	msgBean = pRmi.RmiExec(currStatus.getCmd(), this, currStatus.getCurrPage());
		    	currStatus.setTotalRecord(msgBean.getCount());
		    	request.getSession().setAttribute("Acc_Data_Month_" + Sid, ((Object)msgBean.getMsg()));
		    	currStatus.setJsp("Acc_Data_Month.jsp?Sid=" + Sid);
		    	break;
		}
		
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
	   	response.sendRedirect(currStatus.getJsp());
	   
	}
	
	/** 明细导出excel
	 * @param request
	 * @param response
	 * @param pRmi
	 * @param pFromZone
	 */
	public void ExportToExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{/*
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		try
		{
			getHtmlData(request);
			currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
			currStatus.getHtmlData(request, pFromZone);
			
			//清除历史
			//生成当前
			SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
			String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
			String SheetName = "_" + BT + "," + ET;
			String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
			
			msgBean = pRmi.RmiExec(2, this, 0);
			ArrayList<?> temp = (ArrayList<?>)msgBean.getMsg();
			if(temp != null)
			{
				WritableWorkbook book = Workbook.createWorkbook(new File(UPLOAD_PATH + UPLOAD_NAME + ".xls"));
	            WritableSheet sheet = book.createSheet(SheetName, 0);
	            sheet.setColumnView(0, 20);
	            Label label1 = new Label(0, 0, "站点");
	            Label label2 = new Label(1, 0, "设备");
	            Label label3 = new Label(2, 0, "参数");
	            Label label4 = new Label(3, 0, "时间");
	            Label label5 = new Label(4, 0, "数值");            
	            Label label6 = new Label(5, 0, "级别");
	            Label label7 = new Label(6, 0, "描述");
	            sheet.addCell(label1);
	            sheet.addCell(label2);
	            sheet.addCell(label3);
	            sheet.addCell(label4);
	            sheet.addCell(label5);
	            sheet.addCell(label6);
	            sheet.addCell(label7);
	            
	            Iterator<?> iterator = (Iterator<?>)temp.iterator();
				int i = 0;
				while(iterator.hasNext())
				{
					i++;
					AccDataBean Bean = (AccDataBean)iterator.next();
					String D_Cpm_Name = Bean.getCpm_Name();
					String D_CName = Bean.getCName();
					String D_Attr_Name = Bean.getAttr_Name();
					String D_CTime = Bean.getCTime();
					String D_Value = Bean.getValue();
					String D_Unit = Bean.getUnit();
					String D_Lev = Bean.getLev();
					String D_Des = Bean.getDes();
					
					if(null == D_Value){D_Value = "";}
					if(null == D_Unit){D_Unit = "";}
					if(null == D_Lev){D_Lev = "";}
					if(null == D_Des){D_Des = "";}
					
					String str_Lev = "无";
					String str_Des = "无";
					if(D_Lev.length() > 0)
					{
						str_Lev = D_Lev;
					}
					if(D_Des.length() > 0)
					{
						str_Des = D_Des;
					}
					
					sheet.setColumnView(i, 20);
		            Label label = new Label(0,i,D_Cpm_Name);  //站点
		            sheet.addCell(label);
		            label = new Label(1,i,D_CName);           //设备
		            sheet.addCell(label);
		            label = new Label(2,i,D_Attr_Name);       //参数
		            sheet.addCell(label);
		            label = new Label(3,i,D_CTime);           //时间
		            sheet.addCell(label);
		            label = new Label(4,i,D_Value+D_Unit);    //数值
		            sheet.addCell(label);
		            label = new Label(5,i,str_Lev);           //级别
		            sheet.addCell(label);
		            label = new Label(6,i,str_Des);           //描述
		            sheet.addCell(label);
				}
				book.write();
	            book.close();
	            try
	    		{ 
	    			PrintWriter out = response.getWriter();
	    			out.print(UPLOAD_NAME);
	    		}
	    		catch(Exception exp)
	    		{
	    		   exp.printStackTrace();	
	    		}	            
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	*/}
	
	/** 数据图表 Graph
	 * @param request
	 * @param response
	 * @param pRmi
	 * @param pFromZone
	 */
	public void Graph(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		try
		{
			getHtmlData(request);
			currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
			currStatus.getHtmlData(request, pFromZone);
		    
			switch(currStatus.getFunc_Sub_Id())
			{
			    case 1://时均值
			    	break;
			    case 2://日均值
			    	request.getSession().setAttribute("Month_" + Sid, Month);
			    	request.getSession().setAttribute("Year_" + Sid, Year);
			    	break;
			}
			
			msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
			switch(currStatus.getCmd())
			{
				case 20://数据图表
			    	request.getSession().setAttribute("Graph_" + Sid, (Object)msgBean.getMsg());
					currStatus.setJsp("Graph.jsp?Sid=" + Sid);
					break;
			}
			
			request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
		   	response.sendRedirect(currStatus.getJsp());
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
	}

	public String getSql(int pCmd)
	{
		String Sql = "";
		switch (pCmd)
		{
			case 0://累积流量 (历史) 站点用气表
			   Sql = " select t.sn, t.cpm_id, t.cpm_name, t.id, t.cname, t.attr_id, t.attr_name, t.ctime, t.b_value, t.e_value, t.value, t.unit,  t.des " +
					 " from view_acc_data_day t " +
					 " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					 " and t.ctime >= date_format('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					 " and t.ctime <= date_format('"+currStatus.getVecDate().get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					 " order by t.ctime desc ";
			   break;
			case 1://日用量总表
			   Sql = " select t.sn, t.cpm_id, t.cpm_name, t.id, t.cname, t.attr_id, t.attr_name, t.ctime, t.b_value, t.e_value, t.value, t.unit,  t.des " +
					 " from view_acc_data_day t " +
			  	  	 " where t.ctime = date_format('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m-%d')" +
			  	  	 " order by t.cpm_id ";
			   break;
			case 2://月用量总表
				   Sql = " select t.sn, t.cpm_id, t.cpm_name, t.id, t.cname, t.attr_id, t.attr_name, t.ctime, (t.e_value - sum(t.value)) as b_value , t.e_value, sum(t.value) value , t.unit,  t.des " +
						 " from (SELECT * FROM view_acc_data_day WHERE (DATE_FORMAT(ctime, '%Y-%m') = DATE_FORMAT('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m'))  ORDER BY ctime DESC) t " + 
						 " GROUP BY CPM_ID ";
				   break;
			
			case 20://数据图表
			   Sql = " {? = call rmi_graph('"+ Id +"', '"+ currStatus.getFunc_Sub_Id() +"', '"+ currStatus.getVecDate().get(0).toString().substring(0,10) +"')}";
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
			setCpm_Name(pRs.getString(3));		
			setId(pRs.getString(4));
			setCName(pRs.getString(5));		
			setAttr_Id(pRs.getString(6));
			setAttr_Name(pRs.getString(7));			
			setCTime(pRs.getString(8));
			setB_Value(pRs.getString(9));
			setE_Value(pRs.getString(10));
			setValue(pRs.getString(11));
			setUnit(pRs.getString(12));
			setDes(pRs.getString(13));
		} 
		catch (SQLException sqlExp) 
		{
			sqlExp.printStackTrace();
		}		
		return IsOK;
	}
	
	/** 获取request页面 SN、ID、Cpm_Id、Level 等数据
	 * @param request
	 * @return
	 */
	public boolean getHtmlData(HttpServletRequest request) 
	{
		boolean IsOK = true;
		try 
		{	
			setSN(CommUtil.StrToGB2312(request.getParameter("SN")));
			setCpm_Id(CommUtil.StrToGB2312(request.getParameter("Cpm_Id")));
			setCpm_Name(CommUtil.StrToGB2312(request.getParameter("Cpm_Name")));
			setId(CommUtil.StrToGB2312(request.getParameter("Id")));
			setCName(CommUtil.StrToGB2312(request.getParameter("CName")));
			setAttr_Id(CommUtil.StrToGB2312(request.getParameter("Attr_Id")));
			setAttr_Name(CommUtil.StrToGB2312(request.getParameter("Attr_Name")));		
			setCTime(CommUtil.StrToGB2312(request.getParameter("CTime")));		
			setB_Value(CommUtil.StrToGB2312(request.getParameter("B_Value")));
			setE_Value(CommUtil.StrToGB2312(request.getParameter("E_Value")));
			setValue(CommUtil.StrToGB2312(request.getParameter("Value")));
			setUnit(CommUtil.StrToGB2312(request.getParameter("Unit")));
			setDes(CommUtil.StrToGB2312(request.getParameter("Des")));
			
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
			setLevel(CommUtil.StrToGB2312(request.getParameter("Level")));
			setYear(CommUtil.StrToGB2312(request.getParameter("Year")));
			setMonth(CommUtil.StrToGB2312(request.getParameter("Month")));		
			setFunc_Cpm_Id(CommUtil.StrToGB2312(request.getParameter("Func_Cpm_Id")));
		}
		catch (Exception Exp) 
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String SN;
	private String Cpm_Id;
	private String Cpm_Name;
	private String Id;
	private String CName;
	private String Attr_Id;
	private String Attr_Name;
	private String CTime;
	private String B_Value;
	private String E_Value;
	private String Value;
	private String Unit;
	private String Des;

	private String Sid;
	private String Level;
	private String Year;
	private String Month;
	
	private String Func_Cpm_Id;

	public String getSN()
	{
		return SN;
	}

	public void setSN(String sN)
	{
		SN = sN;
	}

	public String getCpm_Id()
	{
		return Cpm_Id;
	}

	public void setCpm_Id(String cpm_Id)
	{
		Cpm_Id = cpm_Id;
	}
	
	public String getCpm_Name()
	{
		return Cpm_Name;
	}

	public void setCpm_Name(String cpm_Name)
	{
		Cpm_Name = cpm_Name;
	}

	public String getId()
	{
		return Id;
	}

	public void setId(String id)
	{
		Id = id;
	}

	public String getCName()
	{
		return CName;
	}

	public void setCName(String cName)
	{
		CName = cName;
	}

	public String getAttr_Id()
	{
		return Attr_Id;
	}

	public void setAttr_Id(String attr_Id)
	{
		Attr_Id = attr_Id;
	}

	public String getAttr_Name()
	{
		return Attr_Name;
	}

	public void setAttr_Name(String attr_Name)
	{
		Attr_Name = attr_Name;
	}

	public String getCTime()
	{
		return CTime;
	}

	public void setCTime(String cTime)
	{
		CTime = cTime;
	}

	public String getB_Value()
	{
		return B_Value;
	}

	public void setB_Value(String b_Value)
	{
		B_Value = b_Value;
	}

	public String getE_Value()
	{
		return E_Value;
	}

	public void setE_Value(String e_Value)
	{
		E_Value = e_Value;
	}

	public String getValue()
	{
		return Value;
	}

	public void setValue(String value)
	{
		Value = value;
	}

	public String getUnit()
	{
		return Unit;
	}

	public void setUnit(String unit)
	{
		Unit = unit;
	}

	public String getDes()
	{
		return Des;
	}

	public void setDes(String des)
	{
		Des = des;
	}

	public String getSid()
	{
		return Sid;
	}

	public void setSid(String sid)
	{
		Sid = sid;
	}

	public String getLevel()
	{
		return Level;
	}

	public void setLevel(String level)
	{
		Level = level;
	}

	public String getYear()
	{
		return Year;
	}

	public void setYear(String year)
	{
		Year = year;
	}

	public String getMonth()
	{
		return Month;
	}

	public void setMonth(String month)
	{
		Month = month;
	}

	public String getFunc_Cpm_Id()
	{
		return Func_Cpm_Id;
	}

	public void setFunc_Cpm_Id(String func_Cpm_Id)
	{
		Func_Cpm_Id = func_Cpm_Id;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	
	
	
}