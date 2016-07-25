package bean;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import rmi.Rmi;
import rmi.RmiBean;
import util.*;

public class ProGxtjBean extends RmiBean 
{	
	public final static long serialVersionUID = RmiBean.RMI_PRO_L;
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public ProGxtjBean()
	{
		super.className = "ProGxtjBean";
	}	
	public void ExecCmd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		ProLCrmBean pCrmBean = new ProLCrmBean();
		
    	switch(currStatus.getFunc_Sub_Id())
		{
    		case 1://购销站级月报表
    			msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
    			switch(currStatus.getCmd())
    				{
    					case 0:
    						request.getSession().setAttribute("Pro_GX_ZYB_" + Sid, ((Object)msgBean.getMsg()));
    						currStatus.setJsp("Pro_GX_ZYB.jsp?Sid=" + Sid);	    					
    					
    						
    						pCrmBean = new ProLCrmBean();
    						pCrmBean.setCpm_Id(Cpm_Id);
    						pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
    						pCrmBean.setVecDa(currStatus.getVecDate());
    						//查询所有加注客户
    						msgBean =pRmi.RmiExec(3, pCrmBean, 0); 
    						request.getSession().setAttribute("Pro_Crm_User_" + Sid, ((Object)msgBean.getMsg()));  
    						
    						//根据所有信息
    						msgBean =pRmi.RmiExec(2, pCrmBean, 0); 
    						request.getSession().setAttribute("Pro_O_ZYB_" + Sid, ((Object)msgBean.getMsg()));
    					break;   
    				}
    			request.getSession().setAttribute("Month_" + Sid, Month);
    	    	request.getSession().setAttribute("Year_" + Sid, Year);
    	    break;
			
    		case 2://购销统计站级年报表
    		
    			switch(currStatus.getCmd())
				{
					case 1:	
						ProLBean pBean = new ProLBean();
		    			pBean.setCpm_Id(Cpm_Id);
		    			pBean.setYear(Year);		    			
		    			pBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
		    			msgBean = pRmi.RmiExec(6, pBean, 0);
						request.getSession().setAttribute("Pro_GX_ZNB_" + Sid, ((Object)msgBean.getMsg()));											
						currStatus.setJsp("Pro_GX_ZNB.jsp?Sid=" + Sid);		
						
						pCrmBean = new ProLCrmBean();
						pCrmBean.setCpm_Id(Cpm_Id);
						pCrmBean.setYear(Year);
						pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
						//先查询所有客户
						msgBean =pRmi.RmiExec(5, pCrmBean, 0); 
						request.getSession().setAttribute("Pro_Crm_ZUser_" + Sid, ((Object)msgBean.getMsg()));  
						//在查询所有信息
						msgBean =pRmi.RmiExec(4, pCrmBean, 0); 
						request.getSession().setAttribute("Pro_O_ZNB_" + Sid, ((Object)msgBean.getMsg()));
						
					break;
				}	    			
	    		request.getSession().setAttribute("Year_" + Sid, Year);		
    		break;
    		case 3:  //购销统计公司级月报表    			
    			switch(currStatus.getCmd())
				{
    				case 2:
    					msgBean = pRmi.RmiExec(1, this, 0);
    					request.getSession().setAttribute("Pro_GX_GYB_" + Sid, ((Object)msgBean.getMsg()));
						currStatus.setJsp("Pro_GX_GYB.jsp?Sid=" + Sid);	    
						
						pCrmBean = new ProLCrmBean();
						pCrmBean.setCpm_Id(Cpm_Id);
						pCrmBean.setYear(Year);
						pCrmBean.setVecDa(currStatus.getVecDate());
						pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
						//先查询所有客户
						msgBean =pRmi.RmiExec(6, pCrmBean, 0); 
						request.getSession().setAttribute("Pro_Crm_GUser_" + Sid, ((Object)msgBean.getMsg()));  
						//在查询所有信息
						msgBean =pRmi.RmiExec(7, pCrmBean, 0); 
						request.getSession().setAttribute("Pro_O_GYB_" + Sid, ((Object)msgBean.getMsg()));
    				
    				
    				break;
				}
			request.getSession().setAttribute("Month_" + Sid, Month);
	    	request.getSession().setAttribute("Year_" + Sid, Year);
    		break;
    		case 4: //购销统计公司年报表
    			
    			switch(currStatus.getCmd())
				{
					case 3:	
						ProLBean pBean = new ProLBean();		    			
		    			pBean.setYear(Year);		    			
		    			pBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
		    			msgBean = pRmi.RmiExec(7, pBean, 0);
						request.getSession().setAttribute("Pro_GX_GNB_" + Sid, ((Object)msgBean.getMsg()));											
						currStatus.setJsp("Pro_GX_GNB.jsp?Sid=" + Sid);		
						
						pCrmBean = new ProLCrmBean();
						pCrmBean.setYear(Year);
						pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
						//先查询所有客户
						msgBean =pRmi.RmiExec(8, pCrmBean, 0); 
						request.getSession().setAttribute("Pro_Crm_GGUser_" + Sid, ((Object)msgBean.getMsg()));  
						//在查询所有信息
						msgBean =pRmi.RmiExec(9, pCrmBean, 0); 
						request.getSession().setAttribute("Pro_O_GNB_" + Sid, ((Object)msgBean.getMsg()));
						
					break;
				}	    			
	    		request.getSession().setAttribute("Year_" + Sid, Year);					
    		break;
			
		}
		
		//所有业务
    	ProRBean RBean = new ProRBean();
    	msgBean = pRmi.RmiExec(1, RBean, 0);
    	request.getSession().setAttribute("Pro_R_Buss_" + Sid, ((Object)msgBean.getMsg()));
    	//查询倍率
    	CorpInfoBean ifBean  = new CorpInfoBean();
    	msgBean = pRmi.RmiExec(0, ifBean, 0);
    	request.getSession().setAttribute("Pro_R_Info_" + Sid, ((Object)msgBean.getMsg()));
    	//所有类型
    	msgBean = pRmi.RmiExec(2, RBean, 0);
    	request.getSession().setAttribute("Pro_R_Type_" + Sid, ((Object)msgBean.getMsg()));
    	
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
	   	response.sendRedirect(currStatus.getJsp());
	}
	//购销站级月报导出
	public void ZYBToExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		try {
				getHtmlData(request);
				currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
				currStatus.getHtmlData(request, pFromZone);
				
				DecimalFormat df=new DecimalFormat(".##");
				SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
				String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
				String SheetName = "站级月汇总";
				String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
			
				msgBean = pRmi.RmiExec(0, this, 0);
				ArrayList<?> temp0 = (ArrayList<?>)msgBean.getMsg();
				ProLCrmBean pCrmBean = new ProLCrmBean();
				pCrmBean.setCpm_Id(Cpm_Id);
				pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
				pCrmBean.setVecDa(currStatus.getVecDate());
				msgBean = pRmi.RmiExec(2, pCrmBean, 0);
				ArrayList<?> temp2 = (ArrayList<?>)msgBean.getMsg();
				msgBean = pRmi.RmiExec(3, pCrmBean, 0);
				ArrayList<?> temp3 = (ArrayList<?>)msgBean.getMsg();
				
				ArrayList CNameList   = new ArrayList();
			    List<String> CrmVa       = new ArrayList<String>(); 
				//String  Crm_ALL       = "";
				String T_Cpm_Id       = "";
				String  T_Cpm_Name     = "";
				String CTime          = "";
				String Value_O        = "0";
				String Value_O_Gas    = "0";
				String Value_I        = "0";
				String Value_I_Gas    = "0";
				String Value_R        = "0";
				String Value_R_Gas    = "0";
				String Value_PAL        = "0";
				String Value_PAL_Gas    = "0";
				String Crm_Str        = "";
					
				//本月累计
				double Value_O_All       = 0.0;
				double Value_O_Gas_All   = 0.0;
				double Value_I_All       = 0.0;
				double Value_I_Gas_All   = 0.0;	
				double Value_R_All       = 0.0;
				double Value_R_Gas_All   = 0.0;	
				double Value_PAL_All       = 0.0;
				double Value_PAL_Gas_All   = 0.0;	
				double Value_Crm_All     = 0.0;
				double Value_Crm_Gas_All = 0.0;	
					
				//统计数量
				int cnt              = 0;
				int n                = 0;
				WritableWorkbook book = Workbook.createWorkbook(new File(UPLOAD_PATH + UPLOAD_NAME + ".xls"));
	            WritableSheet sheet = book.createSheet(SheetName, 0);	   
	            WritableFont wf = new WritableFont(WritableFont.createFont("normal"), 18, WritableFont.BOLD , false);
				WritableCellFormat wff = new WritableCellFormat(wf);
				//wf.setColour(Colour.BLACK);//字体颜色
				wff.setAlignment(Alignment.CENTRE);//设置居中
				wff.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线				
				//字体格式2
				WritableFont wf2 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
				WritableCellFormat wff2 = new WritableCellFormat(wf2);
				//wf2.setColour(Colour.BLACK);//字体颜色	
				wff2.setAlignment(Alignment.CENTRE);//设置居中
				wff2.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
				int D_Index       = -1;
				Label label       = null;	
				
				if(null != temp3)
			 	{		    		
			 		Iterator itemp = temp3.iterator();			 		
			 		while(itemp.hasNext())
			 		{		 		 			
			 			ProLCrmBean mBean =(ProLCrmBean)itemp.next();
			 			n++;		 			
			 		}			
			 	}
				
				
				
				D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, Cpm_Name+"站"+Year+"年LNG资源购销统计表",wff);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, "");
		        sheet.addCell(label);
		        label = new Label(2, D_Index, "");
		        sheet.addCell(label);
		        label = new Label(3, D_Index, "");
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, "");
		        sheet.addCell(label);		 
		        label = new Label(5, D_Index, "");
		        sheet.addCell(label);		 
		        label = new Label(6, D_Index, "");
		        sheet.addCell(label);	
		        
		        for(int k = 1;k<n ;k++)
			       {
			    	   label = new Label(6+k, D_Index, "",wff2);
				        sheet.addCell(label);				    	   
			       }			       
		        sheet.mergeCells(0,D_Index,5+n,D_Index);
				
		        D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, "项目\\日期",wff2);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, "销售量",wff2);
		        sheet.addCell(label);
		        label = new Label(2, D_Index, "卸车量",wff2);
		        sheet.addCell(label);
		        label = new Label(3, D_Index, "库存量",wff2);
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, "盈亏量",wff2);
		        sheet.addCell(label);		 
		        label = new Label(5, D_Index, "购入气量",wff2);
		        sheet.addCell(label);		 			        	 			       					
		        label = new Label(6, D_Index, "各服务单位分销量",wff2);
		        sheet.addCell(label);		
		        		        		        
		       for(int k = 1;k<n ;k++)
		       {
		    	   label = new Label(6+k, D_Index, "",wff2);
			        sheet.addCell(label);				    	   
		       }
		       if(n > 1){
		       sheet.mergeCells(6,D_Index,5+n,D_Index);
		       }
		        
				
				if(null != temp0)					
				{
					Iterator iterator = temp0.iterator();
					while(iterator.hasNext())
					{
						ProLBean Bean = (ProLBean)iterator.next();
						cnt++;
						if(!T_Cpm_Id.equals(Bean.getCpm_Id()))
						{
							
							T_Cpm_Name = Bean.getCpm_Name();
							CTime = Bean.getCTime();
							Value_O = Bean.getValue_O();
							Value_O_Gas = Bean.getValue_O_Gas();
							Value_I = Bean.getValue_I();
							Value_I_Gas = Bean.getValue_I_Gas();
							Value_R = Bean.getValue_R();
							Value_R_Gas = Bean.getValue_R_Gas();
							Value_PAL = Bean.getValue_PAL();
							Value_PAL_Gas = Bean.getValue_PAL_Gas();
							
							Value_O_All     = Value_O_All + Double.parseDouble(Value_O);
							Value_O_Gas_All = Value_O_Gas_All + Double.parseDouble(Value_O_Gas);
							Value_I_All     = Value_I_All + Double.parseDouble(Value_I);
							Value_I_Gas_All = Value_I_Gas_All + Double.parseDouble(Value_I_Gas);
							Value_R_All     = Value_R_All + Double.parseDouble(Value_R);
							Value_R_Gas_All = Value_R_Gas_All + Double.parseDouble(Value_R_Gas);
							Value_PAL_All     = Value_PAL_All + Double.parseDouble(Value_PAL);
							Value_PAL_Gas_All = Value_PAL_Gas_All + Double.parseDouble(Value_PAL_Gas);
					
			        D_Index++;
			        sheet.setRowView(D_Index, 400);
			        sheet.setColumnView(D_Index, 20);
			        label = new Label(0, D_Index, "",wff2);
			        sheet.addCell(label);
			        label = new Label(1, D_Index, "液态LNG（kg）",wff2);
			        sheet.addCell(label);
			        label = new Label(2, D_Index, "液态LNG（kg）",wff2);
			        sheet.addCell(label);
			        label = new Label(3, D_Index, "液态LNG（kg）",wff2);
			        sheet.addCell(label);		  
			        label = new Label(4, D_Index, "液态LNG（kg）",wff2);
			        sheet.addCell(label);		 
			        label = new Label(5, D_Index, "液态LNG（kg）",wff2);
			        sheet.addCell(label);		 			        	 
			        sheet.mergeCells(0,1,0,2);
			
			        if(null != temp3)
				 	{
			    		int i  = 6;
				 		Iterator iters = temp3.iterator();
				 		while(iters.hasNext())
				 		{
				 			ProLCrmBean CrmBean =(ProLCrmBean)iters.next();
				 			String Crm_Name       =   CrmBean.getCrm_Name();
				 			CNameList.add(Crm_Name);
				 	
				 label = new Label(i, D_Index, Crm_Name,wff2);
				 sheet.addCell(label);			
				 i++;
				 		}
				 	}
			        
								D_Index++;
						        sheet.setRowView(D_Index, 400);
						        sheet.setColumnView(D_Index, 20);
						        label = new Label(0, D_Index, CTime.substring(5,10),wff2);
						        sheet.addCell(label);
						        label = new Label(1, D_Index, Value_O+"",wff2);
						        sheet.addCell(label);
						        label = new Label(2, D_Index, Value_I+"",wff2);
						        sheet.addCell(label);
						        label = new Label(3, D_Index, Value_R+"",wff2);
						        sheet.addCell(label);		  
						        label = new Label(4, D_Index, Value_PAL+"",wff2);
						        sheet.addCell(label);		 
						        label = new Label(5, D_Index, Value_I+"",wff2);
						        sheet.addCell(label);		 			        	 			       					
						       
						        if(null != CNameList && null != temp2)
								{
									Iterator cnamelist = CNameList.iterator();
									  int j = 6 ;
									while(cnamelist.hasNext())
									{
										
										String cname =(String)cnamelist.next();
										String Crm_Value         = "0.00";
										String Crm_Value_Gas     = "0.00";
										String Crm_Value2        = "";
										String Crm_Value_Gas2    = "";
										Value_Crm_All			 = 0.00;
										Value_Crm_Gas_All 		 = 0.00;									
										Iterator iter = temp2.iterator();
										while(iter.hasNext())
										{
											ProLCrmBean pBean =(ProLCrmBean)iter.next();
											String Crm_CTime      =   pBean.getCTime();
											String CrmName       	=   pBean.getCrm_Name();									
											if(cname.equals(CrmName) && CTime.substring(8,10).equals(Crm_CTime.substring(8,10)))
											{
												Crm_Value          =   pBean.getValue_I();
												Crm_Value_Gas      =   pBean.getValue_I_Gas();			
											}	
											if(cname.equals(CrmName))
											{
												Crm_Value2          =   pBean.getValue_I();
												Crm_Value_Gas2      =   pBean.getValue_I_Gas();
												Value_Crm_All       =  Value_Crm_All + Double.parseDouble(Crm_Value2); 	
												Value_Crm_Gas_All   =  Value_Crm_Gas_All + Double.parseDouble(Crm_Value_Gas2); 
												Crm_Str             = 	Value_Crm_All + "," +	Value_Crm_Gas_All;			
											}														
										}	
										CrmVa.add(Crm_Str);	
										label = new Label(j, D_Index, Crm_Value+"",wff2);
								        sheet.addCell(label);		 	
								        j++;
									}
									
								}
						}else
						{
							
							T_Cpm_Name = Bean.getCpm_Name();
							CTime = Bean.getCTime();
							Value_O = Bean.getValue_O();
							Value_O_Gas = Bean.getValue_O_Gas();
							Value_I = Bean.getValue_I();
							Value_I_Gas = Bean.getValue_I_Gas();
							Value_R = Bean.getValue_R();
							Value_R_Gas = Bean.getValue_R_Gas();
							Value_PAL = Bean.getValue_PAL();
							Value_PAL_Gas = Bean.getValue_PAL_Gas();
							
							Value_O_All     = Value_O_All + Double.parseDouble(Value_O);
							Value_O_Gas_All = Value_O_Gas_All + Double.parseDouble(Value_O_Gas);
							Value_I_All     = Value_I_All + Double.parseDouble(Value_I);
							Value_I_Gas_All = Value_I_Gas_All + Double.parseDouble(Value_I_Gas);
							Value_R_All     = Value_R_All + Double.parseDouble(Value_R);
							Value_R_Gas_All = Value_R_Gas_All + Double.parseDouble(Value_R_Gas);	
							Value_PAL_All     = Value_PAL_All + Double.parseDouble(Value_PAL);
							Value_PAL_Gas_All = Value_PAL_Gas_All + Double.parseDouble(Value_PAL_Gas);
							
							D_Index++;
						    sheet.setRowView(D_Index, 400);
						    sheet.setColumnView(D_Index, 20);
						    label = new Label(0, D_Index, CTime.substring(5,10),wff2);
						    sheet.addCell(label);
						    label = new Label(1, D_Index, Value_O,wff2);
						    sheet.addCell(label);
						    label = new Label(2, D_Index, Value_I,wff2);
						    sheet.addCell(label);
						    label = new Label(3, D_Index, Value_R,wff2);
						    sheet.addCell(label);		  
						    label = new Label(4, D_Index, Value_PAL,wff2);
						    sheet.addCell(label);		 
						    label = new Label(5, D_Index, Value_I,wff2);
						    sheet.addCell(label);		 			      
						        if(null != CNameList && null != temp2)
								{
									Iterator cnamelist = CNameList.iterator();
									int a = 6;
									while(cnamelist.hasNext())
									{
										String cname =(String)cnamelist.next();
										String Crm_Value = "0.00";
										String Crm_Value_Gas = "0.00";
										Iterator iter = temp2.iterator();
										while(iter.hasNext())
										{
											ProLCrmBean pCBean =(ProLCrmBean)iter.next();
											String Crm_CTime      =   pCBean.getCTime();
											String CrmName       	=   pCBean.getCrm_Name();									
											if(cname.equals(CrmName) && CTime.substring(8,10).equals(Crm_CTime.substring(8,10)))
											{
												Crm_Value          =   pCBean.getValue_I();
												Crm_Value_Gas      =   pCBean.getValue_I_Gas();											
																	
											}								
										}									
										label = new Label(a, D_Index, Crm_Value,wff2);
								        sheet.addCell(label);	
								        a++;
									}
								}								
						}	
						
						T_Cpm_Id = Bean.getCpm_Id();
						if(cnt == temp0.size())
						{	
							String str1 = df.format(Value_O_All);
							String str2 = df.format(Value_I_All);
							String str3 = df.format(Value_R_All);
							String str4 = df.format(Value_I_All);
							String str5 = df.format(Value_PAL_All);
							 D_Index++;
						     sheet.setRowView(D_Index, 400);
						     sheet.setColumnView(D_Index, 20);
						     label = new Label(0, D_Index, "本月合计",wff2);
						     sheet.addCell(label);
						     label = new Label(1, D_Index, str1,wff2);
						     sheet.addCell(label);
						     label = new Label(2, D_Index, str2,wff2);
						     sheet.addCell(label);
						     label = new Label(3, D_Index, str3,wff2);
						     sheet.addCell(label);		  
						     label = new Label(4, D_Index, str5,wff2);
						     sheet.addCell(label);		 
						     label = new Label(5, D_Index, str4,wff2);
						     sheet.addCell(label);		 			 
						 	if(null != CrmVa)
							{							
								Iterator vator = CrmVa.iterator();
								int b =6;
								while(vator.hasNext())
								{
								String vd1 = (String)vator.next();
								String[] vd2 =vd1.split(","); 							
										
								label = new Label(b, D_Index, vd2[0],wff2);
						        sheet.addCell(label);	
						        b++;
								}
							}
						
						
						}
					}			       				
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

		} catch (Exception e) {
			
		}

	}
	//购销站级年报导出
	public void ZNBToExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
		String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
		String SheetName = "站级年汇总" ;
		String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
		DecimalFormat df=new DecimalFormat(".##");
		
		ProLBean pBean = new ProLBean();
		pBean.setCpm_Id(Cpm_Id);
		pBean.setYear(Year);		    			
		pBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
		
		ProLCrmBean pCrmBean = new ProLCrmBean();
		pCrmBean.setCpm_Id(Cpm_Id);
		pCrmBean.setYear(Year);
		pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
		
		try {
				msgBean = pRmi.RmiExec(6, pBean, 0);
				ArrayList<?> Pro_GX_ZNB = (ArrayList<?>)msgBean.getMsg();
				msgBean =pRmi.RmiExec(5, pCrmBean, 0); 
				ArrayList<?> Pro_Crm_ZUser = (ArrayList<?>)msgBean.getMsg();
				msgBean =pRmi.RmiExec(4, pCrmBean, 0); 
				ArrayList<?> Pro_O_ZNB = (ArrayList<?>)msgBean.getMsg();
				
				List<String> CNameList   = new ArrayList<String>();
				List<String> CrmVa       = new ArrayList<String>();
				String T_Cpm_Id       = "";
				String T_Cpm_Name     = "";
				String CTime          = "";
				String DTime          = "";
				String Value_O        = "0";
				String Value_O_Gas    = "0";
				String Value_I        = "0";
				String Value_I_Gas    = "0";
				String Value_R        = "0";
				String Value_R_Gas    = "0";
				String Value_PAL       = "0";
				String Value_PAL_Gas    = "0";
					
				//本年累计
				String Value_O_Y     = "0";
				String Value_O_Gas_Y = "0";
				String Value_I_Y     = "0";
				String Value_I_Gas_Y = "0";
				String Value_R_Y     = "0";
				String Value_R_Gas_Y = "0";
				String Crm_Str       = "" ;
					
				double Value_O_All       = 0.0;
				double Value_O_Gas_All   = 0.0;
				double Value_I_All       = 0.0;
				double Value_I_Gas_All   = 0.0;	
				double Value_R_All       = 0.0;
				double Value_R_Gas_All   = 0.0;	
				double Value_PAL_All       = 0.0;
				double Value_PAL_Gas_All   = 0.0;	
				double Value_Crm_All       = 0.0;
				double Value_Crm_Gas_All   = 0.0;	
										
				//统计数量
				int cnt              = 0;				
				int  num             = 0;
				WritableWorkbook book = Workbook.createWorkbook(new File(UPLOAD_PATH + UPLOAD_NAME + ".xls"));
	            WritableSheet sheet = book.createSheet(SheetName, 0);	   
	            WritableFont wf = new WritableFont(WritableFont.createFont("normal"), 18, WritableFont.BOLD , false);
				WritableCellFormat wff = new WritableCellFormat(wf);
				//wf.setColour(Colour.BLACK);//字体颜色
				wff.setAlignment(Alignment.CENTRE);//设置居中
				wff.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线				
				//字体格式2
				WritableFont wf2 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
				WritableCellFormat wff2 = new WritableCellFormat(wf2);
				//wf2.setColour(Colour.BLACK);//字体颜色	
				wff2.setAlignment(Alignment.CENTRE);//设置居中
				wff2.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
				int D_Index       = -1;
				Label label       = null;		
					
				 if(null != Pro_Crm_ZUser)
				 	{
				 		Iterator iter = Pro_Crm_ZUser.iterator();				 	
				 		while(iter.hasNext())
				 		{
				 			ProLCrmBean CBean =(ProLCrmBean)iter.next();
				 			num++;

				 		}
				 	}					
				 
				D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, Cpm_Name+"站"+Year+"年LNG资源购销统计表",wff);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, "");
		        sheet.addCell(label);
		        label = new Label(2, D_Index, "");
		        sheet.addCell(label);
		        label = new Label(3, D_Index, "");
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, "");
		        sheet.addCell(label);		 
		        label = new Label(5, D_Index, "");
		        sheet.addCell(label);		 		       		        
		        for(int x =1;x<num;x++)
		        {
		        	
		        	label = new Label(5+x, D_Index, "");
			        sheet.addCell(label);				    	   
		       }
		       sheet.mergeCells(0,D_Index,4+num,D_Index);
		        
		        
		        
		        D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, "项目\\日期",wff2);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, "销售量",wff2);
		        sheet.addCell(label);
		        label = new Label(2, D_Index, "卸车量",wff2);
		        sheet.addCell(label);
		        label = new Label(3, D_Index, "运营亏损",wff2);
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, "购入气量",wff2);
		        sheet.addCell(label);		 
		        label = new Label(5, D_Index, "各服务单位分销量",wff2);
		        sheet.addCell(label);		 		       
		        for(int y =1;y<num;y++)
		        {
		        	
		        	label = new Label(5+y, D_Index, "");
			        sheet.addCell(label);				    	   
		       }
		        if(num > 1)
		        {
		       sheet.mergeCells(5,D_Index,4+num,D_Index);
		        }
		        D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, "",wff2);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, "液态LNG(kg)",wff2);
		        sheet.addCell(label);
		        label = new Label(2, D_Index, "液态LNG(kg)",wff2);
		        sheet.addCell(label);
		        label = new Label(3, D_Index, "液态LNG(kg)",wff2);
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, "液态LNG(kg)",wff2);
		        sheet.addCell(label);		 
		        sheet.mergeCells(0,1,0,2);			      	
		        if(null != Pro_Crm_ZUser)
			 	{
			 		Iterator iters = Pro_Crm_ZUser.iterator();
			 		int a = 5;
			 		while(iters.hasNext())
			 		{
			 			ProLCrmBean CrmBean =(ProLCrmBean)iters.next();
			 			String Crm_Name       =   CrmBean.getCrm_Name();
			 			CNameList.add(Crm_Name);						 			
			 			label = new Label(a, D_Index, Crm_Name,wff2);
				        sheet.addCell(label);	
				        a++;
			 		}
			 	}						 	
		        if(null != Pro_GX_ZNB)
				{										
					Iterator iterator = Pro_GX_ZNB.iterator();
					while(iterator.hasNext())
					{
						ProLBean Bean = (ProLBean)iterator.next();			
			
						if(!T_Cpm_Id.equals(Bean.getCpm_Id()))
						{																													
							CTime = Bean.getCTime();
							DTime = CTime.substring(4,6);
							Value_O = Bean.getValue_O();
							Value_O_Gas = Bean.getValue_O_Gas();
							Value_I = Bean.getValue_I();
							Value_I_Gas = Bean.getValue_I_Gas();
							Value_R = Bean.getValue_R();
							Value_R_Gas = Bean.getValue_R_Gas();
							Value_PAL = Bean.getValue_PAL();
							Value_PAL_Gas = Bean.getValue_PAL_Gas();
						
							Value_O_All     = Value_O_All + Double.parseDouble(Value_O);
							Value_O_Gas_All = Value_O_Gas_All + Double.parseDouble(Value_O_Gas);
							Value_I_All     = Value_I_All + Double.parseDouble(Value_I);
							Value_I_Gas_All = Value_I_Gas_All + Double.parseDouble(Value_I_Gas);
							Value_R_All     = Value_R_All + Double.parseDouble(Value_R);
							Value_R_Gas_All = Value_R_Gas_All + Double.parseDouble(Value_R_Gas);
							Value_PAL_All     = Value_PAL_All + Double.parseDouble(Value_PAL);
							Value_PAL_Gas_All = Value_PAL_Gas_All + Double.parseDouble(Value_PAL_Gas);
					
							D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, DTime+"月",wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, Value_O,wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, Value_I,wff2);
					        sheet.addCell(label);
					        label = new Label(3, D_Index, Value_PAL,wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, Value_I,wff2);
					        sheet.addCell(label);		 
					        
					        if(null != CNameList && null != Pro_O_ZNB)
						     {
						     	if(CrmVa!=null && !CrmVa.isEmpty())
						     	{
						     		CrmVa.clear();
						     	}						    			     
						     	Iterator cnameInter = CNameList.iterator();
						     	int b = 5;
						     	while(cnameInter.hasNext())
								{
						     		String cname =(String)cnameInter.next();
						     		String 	Crm_Value = "0.00";
						     		String  Crm_Value_Gas ="0.00";
						     		String Crm_Value2   ="";
						     		String Crm_Value_Gas2="";
						     		Value_Crm_All = 0.00;
						     		Value_Crm_Gas_All   = 0.0;
						     		Iterator crmiter = Pro_O_ZNB.iterator();
						     		while(crmiter.hasNext())
						     		{
						     			ProLCrmBean pCBean =(ProLCrmBean)crmiter.next();
						     			String Crm_CTime      =   pCBean.getCTime();
											String CrmName       	=   pCBean.getCrm_Name();											 	 	
									 	if(CrmName.equals(cname) && Crm_CTime.substring(4,6).equals(DTime))
									 	{									 	
												 Crm_Value          =   pCBean.getValue_I();
										     Crm_Value_Gas      =   pCBean.getValue_I_Gas();
										   							  										    										 
									 	}	
									 	if(CrmName.equals(cname))
									 	{
									 			Crm_Value2       =   pCBean.getValue_I();
										    Crm_Value_Gas2   =   pCBean.getValue_I_Gas();
										    Value_Crm_All     =  Value_Crm_All + Double.parseDouble(Crm_Value2); 	
										    Value_Crm_Gas_All =  Value_Crm_Gas_All + Double.parseDouble(Crm_Value_Gas2); 
										    Crm_Str           = 	Value_Crm_All + "," +	Value_Crm_Gas_All;					   									           
									 	}									 		 								 										 		
						     		 } 
						     		  CrmVa.add(Crm_Str);
						     		 label = new Label(b, D_Index, Crm_Value,wff2);
								     sheet.addCell(label);								     					     							     							     		 									     		 
									 b++;					 										 													 				 										 						 				 			 			
						     		} 						     				
						     }					
					  }
					}
				}	
		        D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, "本年累计",wff2);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, df.format(Value_O_All),wff2);
		        sheet.addCell(label);
		        label = new Label(2, D_Index, df.format(Value_I_All),wff2);
		        sheet.addCell(label);
		        label = new Label(3, D_Index, df.format(Value_PAL_All),wff2);
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, df.format(Value_I_All),wff2);
		        sheet.addCell(label);		 
		        if(null != CrmVa)
				{							
					Iterator vator = CrmVa.iterator();
					int c = 5;
					while(vator.hasNext())
					{
					String vd1 = (String)vator.next();
					String[] vd2 =vd1.split(","); 	
					
					label = new Label(c, D_Index, vd2[0],wff2);
				    sheet.addCell(label);		
				    c++;
					}
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
    
	            
		} catch (Exception e) {
			
		}		
	}
	//购销公司月报导出
	public void GYBToExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
		String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
		String SheetName = "公司购销月汇总";
		String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
		DecimalFormat df=new DecimalFormat(".##");

		try {
				msgBean = pRmi.RmiExec(1, this, 0);
				ArrayList<?> Pro_GX_GYB = (ArrayList<?>)msgBean.getMsg();
				ProLCrmBean	pCrmBean = new ProLCrmBean();
				pCrmBean.setCpm_Id(Cpm_Id);
				pCrmBean.setYear(Year);
				pCrmBean.setVecDa(currStatus.getVecDate());
				pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
				//先查询所有客户
				msgBean =pRmi.RmiExec(6, pCrmBean, 0);
				ArrayList<?> Pro_Crm_GUser = (ArrayList<?>)msgBean.getMsg();
				msgBean =pRmi.RmiExec(7, pCrmBean, 0);
				ArrayList<?> Pro_O_GYB = (ArrayList<?>)msgBean.getMsg();
				
				WritableWorkbook book = Workbook.createWorkbook(new File(UPLOAD_PATH + UPLOAD_NAME + ".xls"));
	            WritableSheet sheet = book.createSheet(SheetName, 0);	   
	            WritableFont wf = new WritableFont(WritableFont.createFont("normal"), 18, WritableFont.BOLD , false);
				WritableCellFormat wff = new WritableCellFormat(wf);
				//wf.setColour(Colour.BLACK);//字体颜色
				wff.setAlignment(Alignment.CENTRE);//设置居中
				wff.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线				
				//字体格式2
				WritableFont wf2 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
				WritableCellFormat wff2 = new WritableCellFormat(wf2);
				//wf2.setColour(Colour.BLACK);//字体颜色	
				wff2.setAlignment(Alignment.CENTRE);//设置居中
				wff2.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
				int D_Index       = -1;
				Label label       = null;		
				ArrayList CNameList   = new ArrayList();
				List<String> CrmVa       = new ArrayList<String>();  
				String  Crm_ALL       = "";
				String T_Cpm_Id       = "";
				String T_Cpm_Name     = "";
				String CTime          = "";
				String Value_O        = "0";
				String Value_O_Gas    = "0";
				String Value_I        = "0";
				String Value_I_Gas    = "0";
				String Value_R        = "0";
				String Value_R_Gas    = "0";
				String Value_PAL        = "0";
				String Value_PAL_Gas    = "0";
				String Crm_Str        = "";
				//本月累计
				double Value_O_All       = 0.0;
				double Value_O_Gas_All   = 0.0;
				double Value_I_All       = 0.0;
				double Value_I_Gas_All   = 0.0;	
				double Value_R_All       = 0.0;
				double Value_R_Gas_All   = 0.0;	
				double Value_PAL_All       = 0.0;
				double Value_PAL_Gas_All   = 0.0;
					
				double Value_Crm_All     = 0.0;
				double Value_Crm_Gas_All = 0.0;	
					
				//统计数量
				int cnt              = 0;
				int count            = 0;
				int num              = 0;
				if(null != Pro_Crm_GUser)
			 	{
			 		Iterator it = Pro_Crm_GUser.iterator();			 		
			 		while(it.hasNext())
			 		{
			 			ProLCrmBean CrmBean =(ProLCrmBean)it.next();
			 			num++;
			 
			 		}
			 	}
				
				
				
				D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, "珠海新能源有限公司"+Year+"年LNG资源购销统计表",wff);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, "");
		        sheet.addCell(label);
		        label = new Label(2, D_Index, "");
		        sheet.addCell(label);
		        label = new Label(3, D_Index, "");
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, "");
		        sheet.addCell(label);		 
		        label = new Label(5, D_Index, "");
		        sheet.addCell(label);	
		        label = new Label(6, D_Index, "");
		        sheet.addCell(label);	
		        label = new Label(7, D_Index, "");
		        sheet.addCell(label);	
		        label = new Label(8, D_Index, "");
		        sheet.addCell(label);	
		        label = new Label(9, D_Index, "");
		        sheet.addCell(label);	
		        label = new Label(10, D_Index, "");
		        sheet.addCell(label);	
				for(int b=1;b<num;b++)
				{
		        
					 label = new Label(10+b, D_Index, "",wff2);
				        sheet.addCell(label);				    	   
			       }			       
		        sheet.mergeCells(0,D_Index,9+num,D_Index);
		        
		        D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, "项目\\日期",wff2);
		        sheet.addCell(label);
		        label = new Label(1, D_Index, "销售量",wff2);
		        sheet.addCell(label);
		        label = new Label(2, D_Index, "");
		        sheet.addCell(label);
		        sheet.mergeCells(1,D_Index,2,D_Index);	
		        label = new Label(3, D_Index, "卸车量",wff2);
		        sheet.addCell(label);		  
		        label = new Label(4, D_Index, "");
		        sheet.addCell(label);		 
		        sheet.mergeCells(3,D_Index,4,D_Index);	
		        label = new Label(5, D_Index, "库存量",wff2);
		        sheet.addCell(label);	
		        label = new Label(6, D_Index, "");
		        sheet.addCell(label);	
		        sheet.mergeCells(5,D_Index,6,D_Index);	
		        label = new Label(7, D_Index, "盈亏量",wff2);
		        sheet.addCell(label);	
		        label = new Label(8, D_Index, "");
		        sheet.addCell(label);	
		        sheet.mergeCells(7,D_Index,8,D_Index);	
		        label = new Label(9, D_Index, "购入气量",wff2);
		        sheet.addCell(label);	
		        label = new Label(10, D_Index, "各服务单位分销量",wff2);
		        sheet.addCell(label);	
		        for(int c=1;c<num;c++)
				{
		        
					 label = new Label(10+c, D_Index, "",wff2);
				        sheet.addCell(label);				    	   
			       }			       
		        sheet.mergeCells(10,D_Index,9+num,D_Index);
		        sheet.mergeCells(0,1,0,2);
		        
		        
				if(null != Pro_GX_GYB)
				{
					Iterator iterator = Pro_GX_GYB.iterator();
					while(iterator.hasNext())
					{
						ProLBean Bean = (ProLBean)iterator.next();
						cnt++;
						if(!T_Cpm_Id.equals(Bean.getCpm_Id()))
						{
							
							T_Cpm_Name = Bean.getCpm_Name();
							CTime = Bean.getCTime();
							Value_O = Bean.getValue_O();
							Value_O_Gas = Bean.getValue_O_Gas();
							Value_I = Bean.getValue_I();
							Value_I_Gas = Bean.getValue_I_Gas();
							Value_R = Bean.getValue_R();
							Value_R_Gas = Bean.getValue_R_Gas();
							Value_PAL = Bean.getValue_PAL();
							Value_PAL_Gas = Bean.getValue_PAL_Gas();
							
							Value_O_All     = Value_O_All + Double.parseDouble(Value_O);
							Value_O_Gas_All = Value_O_Gas_All + Double.parseDouble(Value_O_Gas);
							Value_I_All     = Value_I_All + Double.parseDouble(Value_I);
							Value_I_Gas_All = Value_I_Gas_All + Double.parseDouble(Value_I_Gas);
							Value_R_All     = Value_R_All + Double.parseDouble(Value_R);
							Value_R_Gas_All = Value_R_Gas_All + Double.parseDouble(Value_R_Gas);
							Value_PAL_All     = Value_PAL_All + Double.parseDouble(Value_PAL);
							Value_PAL_Gas_All = Value_PAL_Gas_All + Double.parseDouble(Value_PAL_Gas);
						  
						  D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, "");
					        sheet.addCell(label);
					        label = new Label(1, D_Index, "液态LNG(kg)",wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, "折合气态(m3)",wff2);
					        sheet.addCell(label);	
					        label = new Label(3, D_Index, "液态LNG(kg)",wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, "折合气态(m3)",wff2);
					        sheet.addCell(label);		 	
					        label = new Label(5, D_Index, "液态LNG(kg)",wff2);
					        sheet.addCell(label);	
					        label = new Label(6, D_Index, "折合气态(m3)",wff2);
					        sheet.addCell(label);							       
					        if(null != Pro_Crm_GUser)
						 	{
						 		Iterator iters = Pro_Crm_GUser.iterator();
						 		int a = 10;
						 		while(iters.hasNext())
						 		{
						 			ProLCrmBean CrmBean =(ProLCrmBean)iters.next();
						 			String Crm_Name       =   CrmBean.getCrm_Name();
						 			count++;
						 			CNameList.add(Crm_Name);
						 	label = new Label(a, D_Index, Crm_Name,wff2);
							sheet.addCell(label);	
							a++;
						 		}
						 	}
			
					        D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, CTime.substring(5,10),wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, Value_O,wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, Value_O_Gas,wff2);
					        sheet.addCell(label);	
					        label = new Label(3, D_Index, Value_I,wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, Value_I_Gas,wff2);
					        sheet.addCell(label);		 	
					        label = new Label(5, D_Index, Value_R,wff2);
					        sheet.addCell(label);	
					        label = new Label(6, D_Index, Value_R_Gas,wff2);
					        sheet.addCell(label);		
					        label = new Label(7, D_Index, Value_PAL,wff2);
					        sheet.addCell(label);	
					        label = new Label(8, D_Index, Value_PAL_Gas,wff2);
					        sheet.addCell(label);		
					        label = new Label(9, D_Index, Value_I,wff2);
					        sheet.addCell(label);	
					        if(null != CNameList && null != Pro_O_GYB)
							{
								if(CrmVa!=null && !CrmVa.isEmpty())
							   {
							     			CrmVa.clear();
							   }		
								Iterator cnamelist = CNameList.iterator();
								int b =10;
								while(cnamelist.hasNext())
								{
									String cname =(String)cnamelist.next();
									String Crm_Value = "0.00";
									String Crm_Value_Gas = "0.00";
									String Crm_Value2      ="";
									String Crm_Value_Gas2  ="";
									Value_Crm_All = 0.00;
								  Value_Crm_Gas_All = 0.00;
									Iterator iter = Pro_O_GYB.iterator();
									while(iter.hasNext())
									{
										ProLCrmBean pCBean =(ProLCrmBean)iter.next();
										String Crm_CTime      =   pCBean.getCTime();
										String CrmName       	=   pCBean.getCrm_Name();									
										if(cname.equals(CrmName) && CTime.substring(8,10).equals(Crm_CTime.substring(8,10)))
										{
											Crm_Value          =   pCBean.getValue_I();
											Crm_Value_Gas      =   pCBean.getValue_I_Gas();			
										}	
										if(cname.equals(CrmName))
										{
											Crm_Value2          =   pCBean.getValue_I();
											Crm_Value_Gas2      =   pCBean.getValue_I_Gas();
											Value_Crm_All       =  Value_Crm_All + Double.parseDouble(Crm_Value2); 	
											Value_Crm_Gas_All   =  Value_Crm_Gas_All + Double.parseDouble(Crm_Value_Gas2); 
											Crm_Str             = 	Value_Crm_All + "," +	Value_Crm_Gas_All;			
										}																					
									}
								     CrmVa.add(Crm_Str);
								     
								  label = new Label(b, D_Index, Crm_Value,wff2);
								  sheet.addCell(label);	
								  b++;
								}
							}				        
					}else
					{
						T_Cpm_Name = Bean.getCpm_Name();
						  CTime = Bean.getCTime();
							Value_O = Bean.getValue_O();
							Value_O_Gas = Bean.getValue_O_Gas();
							Value_I = Bean.getValue_I();
							Value_I_Gas = Bean.getValue_I_Gas();
							Value_R = Bean.getValue_R();
							Value_R_Gas = Bean.getValue_R_Gas();
							Value_PAL = Bean.getValue_PAL();
							Value_PAL_Gas = Bean.getValue_PAL_Gas();
							
							Value_O_All     = Value_O_All + Double.parseDouble(Value_O);
							Value_O_Gas_All = Value_O_Gas_All + Double.parseDouble(Value_O_Gas);
							Value_I_All     = Value_I_All + Double.parseDouble(Value_I);
							Value_I_Gas_All = Value_I_Gas_All + Double.parseDouble(Value_I_Gas);
							Value_R_All     = Value_R_All + Double.parseDouble(Value_R);
							Value_R_Gas_All = Value_R_Gas_All + Double.parseDouble(Value_R_Gas);
							Value_PAL_All     = Value_PAL_All + Double.parseDouble(Value_PAL);
							Value_PAL_Gas_All = Value_PAL_Gas_All + Double.parseDouble(Value_PAL_Gas);
						
						  D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, CTime.substring(5,10),wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, Value_O,wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, Value_O_Gas,wff2);
					        sheet.addCell(label);	
					        label = new Label(3, D_Index, Value_I,wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, Value_I_Gas,wff2);
					        sheet.addCell(label);		 	
					        label = new Label(5, D_Index, Value_R,wff2);
					        sheet.addCell(label);	
					        label = new Label(6, D_Index, Value_R_Gas,wff2);
					        sheet.addCell(label);		
					        label = new Label(7, D_Index, Value_PAL,wff2);
					        sheet.addCell(label);	
					        label = new Label(8, D_Index, Value_PAL_Gas,wff2);
					        sheet.addCell(label);		
					        label = new Label(9, D_Index, Value_I,wff2);
					        sheet.addCell(label);	
					        if(null != CNameList && null != Pro_O_GYB)
							{
								Iterator cnamelist = CNameList.iterator();
								int c = 10;
								while(cnamelist.hasNext())
								{
									String cname =(String)cnamelist.next();
									String Crm_Value = "0.00";
									String Crm_Value_Gas = "0.00";
									Iterator iter = Pro_O_GYB.iterator();
									while(iter.hasNext())
									{
										ProLCrmBean pBean =(ProLCrmBean)iter.next();
										String Crm_CTime      =   pBean.getCTime();
										String CrmName       	=   pBean.getCrm_Name();									
										if(cname.equals(CrmName) && CTime.substring(8,10).equals(Crm_CTime.substring(8,10)))
										{
											Crm_Value          =   pBean.getValue_I();
											Crm_Value_Gas      =   pBean.getValue_I_Gas();											
																
										}								
									}
										 
									 label = new Label(c, D_Index, Crm_Value,wff2);
									  sheet.addCell(label);	
									  c++;
								}
							}
						
					}
						T_Cpm_Id = Bean.getCpm_Id();
						if(cnt == Pro_GX_GYB.size())
						{			
						
							 D_Index++;
						        sheet.setRowView(D_Index, 400);
						        sheet.setColumnView(D_Index, 20);
						        label = new Label(0, D_Index, "本月累计",wff2);
						        sheet.addCell(label);
						        label = new Label(1, D_Index, df.format(Value_O_All),wff2);
						        sheet.addCell(label);
						        label = new Label(2, D_Index, df.format(Value_O_Gas_All),wff2);
						        sheet.addCell(label);	
						        label = new Label(3, D_Index, df.format(Value_I_All),wff2);
						        sheet.addCell(label);		  
						        label = new Label(4, D_Index, df.format(Value_I_Gas_All),wff2);
						        sheet.addCell(label);		 	
						        label = new Label(5, D_Index, df.format(Value_R_All),wff2);
						        sheet.addCell(label);	
						        label = new Label(6, D_Index, df.format(Value_R_Gas_All),wff2);
						        sheet.addCell(label);		
						        label = new Label(7, D_Index, df.format(Value_PAL_All),wff2);
						        sheet.addCell(label);	
						        label = new Label(8, D_Index, df.format(Value_PAL_Gas_All),wff2);
						        sheet.addCell(label);		
						        label = new Label(9, D_Index, df.format(Value_I_All),wff2);
						        sheet.addCell(label);	
							
						        if(null != CrmVa)
								{							
									Iterator vator = CrmVa.iterator();
									int d = 10;
									while(vator.hasNext())
									{
									String vd1 = (String)vator.next();
									String[] vd2 =vd1.split(","); 							
									label = new Label(d, D_Index, vd2[0],wff2);
							        sheet.addCell(label);		
							        d++;
									}
								}
							
							
						}
				}
			}
/***************************************各站购销月报表*********************************************************************/		
				msgBean = pRmi.RmiExec(2, this, 0);
				ArrayList<?> Namelist = (ArrayList<?>)msgBean.getMsg();				 								 				 
				 if(null != Namelist)
				 {					 
					 Iterator itname = Namelist.iterator();
					while(itname.hasNext())
					{
						ProLBean gtBean = (ProLBean)itname.next();		
						Cpm_Id = gtBean.getCpm_Id();
						Cpm_Name = gtBean.getCpm_Name();
						 
						msgBean = pRmi.RmiExec(0, this, 0);
						ArrayList<?> temp0 = (ArrayList<?>)msgBean.getMsg();
						
						ProLCrmBean pcBean = new ProLCrmBean();
						pcBean.setCpm_Id(Cpm_Id);
						pcBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
						pcBean.setVecDa(currStatus.getVecDate());
						
						msgBean = pRmi.RmiExec(2, pcBean, 0);
						ArrayList<?> temp2 = (ArrayList<?>)msgBean.getMsg();
						
						msgBean = pRmi.RmiExec(3, pcBean, 0);
						ArrayList<?> temp3 = (ArrayList<?>)msgBean.getMsg();		
						
						String psheetName =Cpm_Name+"站";
						sheet = book.createSheet(psheetName, 1);
						
						ArrayList Z_CNameList   = new ArrayList();
					    List<String> Z_CrmVa       = new ArrayList<String>(); 
						//String  Crm_ALL       = "";
						String Z_Cpm_Id       = "";
						String  Z_Cpm_Name     = "";
						String Z_CTime          = "";
						String Z_Value_O        = "0";
						String Z_Value_O_Gas    = "0";
						String Z_Value_I        = "0";
						String Z_Value_I_Gas    = "0";
						String Z_Value_R        = "0";
						String Z_Value_R_Gas    = "0";
						String Z_Value_PAL        = "0";
						String Z_Value_PAL_Gas    = "0";
						String Z_Crm_Str        = "";
							
						//本月累计
						double Z_Value_O_All       = 0.0;
						double Z_Value_O_Gas_All   = 0.0;
						double Z_Value_I_All       = 0.0;
						double Z_Value_I_Gas_All   = 0.0;	
						double Z_Value_R_All       = 0.0;
						double Z_Value_R_Gas_All   = 0.0;	
						double Z_Value_PAL_All       = 0.0;
						double Z_Value_PAL_Gas_All   = 0.0;	
						double Z_Value_Crm_All     = 0.0;
						double Z_Value_Crm_Gas_All = 0.0;								
						//统计数量
						int Z_cnt                  = 0;
						int n                      = 0;
						D_Index                    = -1;
						label                      = null;
						
						
						if(null != temp3)
					 	{		    		
					 		Iterator itemp = temp3.iterator();			 		
					 		while(itemp.hasNext())
					 		{		 		 			
					 			ProLCrmBean mBean =(ProLCrmBean)itemp.next();
					 			n++;		 			
					 		}			
					 	}
						
						
						
						D_Index++;
				        sheet.setRowView(D_Index, 400);
				        sheet.setColumnView(D_Index, 20);
				        label = new Label(0, D_Index, Cpm_Name+"站"+Year+"年LNG资源购销统计表",wff);
				        sheet.addCell(label);
				        label = new Label(1, D_Index, "");
				        sheet.addCell(label);
				        label = new Label(2, D_Index, "");
				        sheet.addCell(label);
				        label = new Label(3, D_Index, "");
				        sheet.addCell(label);		  
				        label = new Label(4, D_Index, "");
				        sheet.addCell(label);		 
				        label = new Label(5, D_Index, "");
				        sheet.addCell(label);		 
				        label = new Label(6, D_Index, "");
				        sheet.addCell(label);					        
				        for(int k = 1;k<n ;k++)
					       {
					    	   label = new Label(6+k, D_Index, "",wff2);
						        sheet.addCell(label);				    	   
					       }			       
				       
				        sheet.mergeCells(0,D_Index,5+n,D_Index);
				        D_Index++;
				        sheet.setRowView(D_Index, 400);
				        sheet.setColumnView(D_Index, 20);
				        label = new Label(0, D_Index, "项目\\日期",wff2);
				        sheet.addCell(label);
				        label = new Label(1, D_Index, "销售量",wff2);
				        sheet.addCell(label);
				        label = new Label(2, D_Index, "卸车量",wff2);
				        sheet.addCell(label);
				        label = new Label(3, D_Index, "库存量",wff2);
				        sheet.addCell(label);		  
				        label = new Label(4, D_Index, "盈亏量",wff2);
				        sheet.addCell(label);		 
				        label = new Label(5, D_Index, "购入气量",wff2);
				        sheet.addCell(label);		 			        	 			       					
				        label = new Label(6, D_Index, "各服务单位分销量",wff2);
				        sheet.addCell(label);		
				        		        		        
				       for(int g = 1;g<n ;g++)
				       {
				    	   label = new Label(6+g, D_Index, "",wff2);
					        sheet.addCell(label);				    	   
				       }
				       if(n >1 ){
				       sheet.mergeCells(6,D_Index,5+n,D_Index);
				       }
				        
						
						if(null != temp0)					
						{
							Iterator iterator = temp0.iterator();
							while(iterator.hasNext())
							{
								ProLBean Bean = (ProLBean)iterator.next();
								Z_cnt++;
								if(!Z_Cpm_Id.equals(Bean.getCpm_Id()))
								{
									
									Z_Cpm_Name = Bean.getCpm_Name();
									Z_CTime = Bean.getCTime();
									Z_Value_O = Bean.getValue_O();
									Z_Value_O_Gas = Bean.getValue_O_Gas();
									Z_Value_I = Bean.getValue_I();
									Z_Value_I_Gas = Bean.getValue_I_Gas();
									Z_Value_R = Bean.getValue_R();
									Z_Value_R_Gas = Bean.getValue_R_Gas();
									Z_Value_PAL = Bean.getValue_PAL();
									Z_Value_PAL_Gas = Bean.getValue_PAL_Gas();
									
									Z_Value_O_All     = Z_Value_O_All + Double.parseDouble(Z_Value_O);
									Z_Value_O_Gas_All = Z_Value_O_Gas_All + Double.parseDouble(Z_Value_O_Gas);
									Z_Value_I_All     = Z_Value_I_All + Double.parseDouble(Z_Value_I);
									Z_Value_I_Gas_All = Z_Value_I_Gas_All + Double.parseDouble(Z_Value_I_Gas);
									Z_Value_R_All     = Z_Value_R_All + Double.parseDouble(Z_Value_R);
									Z_Value_R_Gas_All = Z_Value_R_Gas_All + Double.parseDouble(Z_Value_R_Gas);
									Z_Value_PAL_All     = Z_Value_PAL_All + Double.parseDouble(Z_Value_PAL);
									Z_Value_PAL_Gas_All = Z_Value_PAL_Gas_All + Double.parseDouble(Z_Value_PAL_Gas);
							
					        D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, "",wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, "液态LNG（kg）",wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, "液态LNG（kg）",wff2);
					        sheet.addCell(label);
					        label = new Label(3, D_Index, "液态LNG（kg）",wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, "液态LNG（kg）",wff2);
					        sheet.addCell(label);		 
					        label = new Label(5, D_Index, "液态LNG（kg）",wff2);
					        sheet.addCell(label);		 			        	 
					        sheet.mergeCells(0,1,0,2);
					
					        if(null != temp3)
						 	{
					    		int i  = 6;
						 		Iterator iters = temp3.iterator();
						 		while(iters.hasNext())
						 		{
						 			ProLCrmBean Z_CrmBean =(ProLCrmBean)iters.next();
						 			String Z_Crm_Name       =   Z_CrmBean.getCrm_Name();
						 			Z_CNameList.add(Z_Crm_Name);
						 	
						 label = new Label(i, D_Index, Z_Crm_Name,wff2);
						 sheet.addCell(label);			
						 i++;
						 		}
						 	}
					        
										D_Index++;
								        sheet.setRowView(D_Index, 400);
								        sheet.setColumnView(D_Index, 20);
								        label = new Label(0, D_Index, Z_CTime.substring(5,10),wff2);
								        sheet.addCell(label);
								        label = new Label(1, D_Index, Z_Value_O+"",wff2);
								        sheet.addCell(label);
								        label = new Label(2, D_Index, Z_Value_I+"",wff2);
								        sheet.addCell(label);
								        label = new Label(3, D_Index, Z_Value_R+"",wff2);
								        sheet.addCell(label);		  
								        label = new Label(4, D_Index, Z_Value_PAL+"",wff2);
								        sheet.addCell(label);		 
								        label = new Label(5, D_Index, Z_Value_I+"",wff2);
								        sheet.addCell(label);		 			        	 			       					
								       
								        if(null != Z_CNameList && null != temp2)
										{
											Iterator cnamelist = Z_CNameList.iterator();
											  int j = 6 ;
											while(cnamelist.hasNext())
											{
												
												String Z_cname =(String)cnamelist.next();
												String Z_Crm_Value         = "0.00";
												String Z_Crm_Value_Gas     = "0.00";
												String Z_Crm_Value2        = "";
												String Z_Crm_Value_Gas2    = "";
												Z_Value_Crm_All			 = 0.00;
												Z_Value_Crm_Gas_All 		 = 0.00;									
												Iterator iter = temp2.iterator();
												while(iter.hasNext())
												{
													ProLCrmBean pBean =(ProLCrmBean)iter.next();
													String Z_Crm_CTime      =   pBean.getCTime();
													String Z_CrmName       	=   pBean.getCrm_Name();									
													if(Z_cname.equals(Z_CrmName) && Z_CTime.substring(8,10).equals(Z_Crm_CTime.substring(8,10)))
													{
														Z_Crm_Value          =   pBean.getValue_I();
														Z_Crm_Value_Gas      =   pBean.getValue_I_Gas();			
													}	
													if(Z_cname.equals(Z_CrmName))
													{
														Z_Crm_Value2          =   pBean.getValue_I();
														Z_Crm_Value_Gas2      =   pBean.getValue_I_Gas();
														Z_Value_Crm_All       =  Z_Value_Crm_All + Double.parseDouble(Z_Crm_Value2); 	
														Z_Value_Crm_Gas_All   =  Z_Value_Crm_Gas_All + Double.parseDouble(Z_Crm_Value_Gas2); 
														Z_Crm_Str             = 	Z_Value_Crm_All + "," +	Z_Value_Crm_Gas_All;			
													}														
												}	
												Z_CrmVa.add(Z_Crm_Str);	
												label = new Label(j, D_Index, Z_Crm_Value+"",wff2);
										        sheet.addCell(label);		 	
										        j++;
											}
											
										}
								}else
								{
									
									Z_Cpm_Name = Bean.getCpm_Name();
									Z_CTime = Bean.getCTime();
									Z_Value_O = Bean.getValue_O();
									Z_Value_O_Gas = Bean.getValue_O_Gas();
									Z_Value_I = Bean.getValue_I();
									Z_Value_I_Gas = Bean.getValue_I_Gas();
									Z_Value_R = Bean.getValue_R();
									Z_Value_R_Gas = Bean.getValue_R_Gas();
									Z_Value_PAL = Bean.getValue_PAL();
									Z_Value_PAL_Gas = Bean.getValue_PAL_Gas();
										
									Z_Value_O_All     = Z_Value_O_All + Double.parseDouble(Z_Value_O);
									Z_Value_O_Gas_All = Z_Value_O_Gas_All + Double.parseDouble(Z_Value_O_Gas);
									Z_Value_I_All     = Z_Value_I_All + Double.parseDouble(Z_Value_I);
									Z_Value_I_Gas_All = Z_Value_I_Gas_All + Double.parseDouble(Z_Value_I_Gas);
									Z_Value_R_All     = Z_Value_R_All + Double.parseDouble(Z_Value_R);
									Z_Value_R_Gas_All = Z_Value_R_Gas_All + Double.parseDouble(Z_Value_R_Gas);	
									Z_Value_PAL_All     = Z_Value_PAL_All + Double.parseDouble(Z_Value_PAL);
									Z_Value_PAL_Gas_All = Z_Value_PAL_Gas_All + Double.parseDouble(Z_Value_PAL_Gas);
									
									D_Index++;
								    sheet.setRowView(D_Index, 400);
								    sheet.setColumnView(D_Index, 20);
								    label = new Label(0, D_Index, Z_CTime.substring(5,10),wff2);
								    sheet.addCell(label);
								    label = new Label(1, D_Index, Z_Value_O,wff2);
								    sheet.addCell(label);
								    label = new Label(2, D_Index, Z_Value_I,wff2);
								    sheet.addCell(label);
								    label = new Label(3, D_Index, Z_Value_R,wff2);
								    sheet.addCell(label);		  
								    label = new Label(4, D_Index, Z_Value_PAL,wff2);
								    sheet.addCell(label);		 
								    label = new Label(5, D_Index, Z_Value_I,wff2);
								    sheet.addCell(label);		 			      
								        if(null != Z_CNameList && null != temp2)
										{
											Iterator Z_cnamelist = Z_CNameList.iterator();
											int a = 6;
											while(Z_cnamelist.hasNext())
											{
												String Z_cname =(String)Z_cnamelist.next();
												String Z_Crm_Value = "0.00";
												String Z_Crm_Value_Gas = "0.00";
												Iterator iter = temp2.iterator();
												while(iter.hasNext())
												{
													ProLCrmBean pCBean =(ProLCrmBean)iter.next();
													String Z_Crm_CTime      =   pCBean.getCTime();
													String Z_CrmName       	=   pCBean.getCrm_Name();									
													if(Z_cname.equals(Z_CrmName) && Z_CTime.substring(8,10).equals(Z_Crm_CTime.substring(8,10)))
													{
														Z_Crm_Value          =   pCBean.getValue_I();
														Z_Crm_Value_Gas      =   pCBean.getValue_I_Gas();											
																			
													}								
												}									
												label = new Label(a, D_Index, Z_Crm_Value,wff2);
										        sheet.addCell(label);	
										        a++;
											}
										}								
								}	
								
								Z_Cpm_Id = Bean.getCpm_Id();
								if(Z_cnt == temp0.size())
								{	
									String str1 = df.format(Z_Value_O_All);
									String str2 = df.format(Z_Value_I_All);
									String str3 = df.format(Z_Value_R_All);
									String str4 = df.format(Z_Value_I_All);
									String str5 = df.format(Z_Value_PAL_All);
									 D_Index++;
								     sheet.setRowView(D_Index, 400);
								     sheet.setColumnView(D_Index, 20);
								     label = new Label(0, D_Index, "本月合计",wff2);
								     sheet.addCell(label);
								     label = new Label(1, D_Index, str1,wff2);
								     sheet.addCell(label);
								     label = new Label(2, D_Index, str2,wff2);
								     sheet.addCell(label);
								     label = new Label(3, D_Index, str3,wff2);
								     sheet.addCell(label);		  
								     label = new Label(4, D_Index, str5,wff2);
								     sheet.addCell(label);		 
								     label = new Label(5, D_Index, str4,wff2);
								     sheet.addCell(label);		 			 
								 	if(null != Z_CrmVa)
									{							
										Iterator vator = Z_CrmVa.iterator();
										int b =6;
										while(vator.hasNext())
										{
										String vd1 = (String)vator.next();
										String[] vd2 =vd1.split(","); 							
												
										label = new Label(b, D_Index, vd2[0],wff2);
								        sheet.addCell(label);	
								        b++;
										}
									}																
								}
							}
							}		       				
						}
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
		} catch (Exception e) {
			
		}
				
	}
	//购销公司年报导出
	public void GNBToExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
		String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
		String SheetName = "公司购销年汇总";
		String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
		DecimalFormat df=new DecimalFormat(".##");
	try {
		ProLBean pBean = new ProLBean();		    			
		pBean.setYear(Year);		    			
		pBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
		msgBean = pRmi.RmiExec(7, pBean, 0);
		ArrayList<?> Pro_GX_GNB = (ArrayList<?>)msgBean.getMsg();				
		ProLCrmBean pCrmBean = new ProLCrmBean();
		pCrmBean.setYear(Year);
		pCrmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
		msgBean =pRmi.RmiExec(8, pCrmBean, 0); 
		ArrayList<?> Pro_Crm_GGUser = (ArrayList<?>)msgBean.getMsg(); 
		msgBean =pRmi.RmiExec(9, pCrmBean, 0); 
		ArrayList<?> Pro_O_GNB = (ArrayList<?>)msgBean.getMsg(); 
		
		WritableWorkbook book = Workbook.createWorkbook(new File(UPLOAD_PATH + UPLOAD_NAME + ".xls"));
        WritableSheet sheet = book.createSheet(SheetName, 0);	   
        WritableFont wf = new WritableFont(WritableFont.createFont("normal"), 18, WritableFont.BOLD , false);
		WritableCellFormat wff = new WritableCellFormat(wf);
		//wf.setColour(Colour.BLACK);//字体颜色
		wff.setAlignment(Alignment.CENTRE);//设置居中
		wff.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线				
		//字体格式2
		WritableFont wf2 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
		WritableCellFormat wff2 = new WritableCellFormat(wf2);
		//wf2.setColour(Colour.BLACK);//字体颜色	
		wff2.setAlignment(Alignment.CENTRE);//设置居中
		wff2.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
		int D_Index       = -1;
		Label label       = null;		
	
		 ArrayList CNameList   = new ArrayList();
		  List<String> CrmVa       = new ArrayList<String>();
		  String T_Cpm_Id       = "";
		  String T_Cpm_Name     = "";
			String CTime          = "";
			String DTime          = "";
			String Value_O        = "0";
			String Value_O_Gas    = "0";
			String Value_I        = "0";
			String Value_I_Gas    = "0";
			String Value_R        = "0";
			String Value_R_Gas    = "0";
			String Value_PAL        = "0";
			String Value_PAL_Gas    = "0";
			
			//本年累计
			
			String Crm_Str       = "" ;
			
			double Value_O_All       = 0.0;
			double Value_O_Gas_All   = 0.0;
			double Value_I_All       = 0.0;
			double Value_I_Gas_All   = 0.0;	
			double Value_R_All       = 0.0;
			double Value_R_Gas_All   = 0.0;	
			double Value_PAL_All       = 0.0;
			double Value_PAL_Gas_All   = 0.0;	
			double Value_Crm_All       = 0.0;
			double Value_Crm_Gas_All   = 0.0;	
			
			//统计数量
			int count            = 0;
			int num              = 0;
			if(null != Pro_Crm_GGUser)
		 	{
		 		Iterator it = Pro_Crm_GGUser.iterator();
		 		while(it.hasNext())
		 		{
		 			ProLCrmBean CBean =(ProLCrmBean)it.next();
		 			num++;
		 		}
		 	}
			
			D_Index++;
	        sheet.setRowView(D_Index, 400);
	        sheet.setColumnView(D_Index, 20);
	        label = new Label(0, D_Index, "珠海新能源有限公司"+Year+"年LNG资源购销统计表",wff);
	        sheet.addCell(label);
	        label = new Label(1, D_Index, "");
	        sheet.addCell(label);
	        label = new Label(2, D_Index, "");
	        sheet.addCell(label);
	        label = new Label(3, D_Index, "");
	        sheet.addCell(label);		  
	        label = new Label(4, D_Index, "");
	        sheet.addCell(label);		 
	        label = new Label(5, D_Index, "");	       	
	        for(int a = 1; a<num; a++)
	        {
	        	 label = new Label(5+a, D_Index, "",wff2);
			     sheet.addCell(label);				    	   
		       }			       
	        sheet.mergeCells(0,D_Index,4+num,D_Index);
	        
	        D_Index++;
	        sheet.setRowView(D_Index, 400);
	        sheet.setColumnView(D_Index, 20);
	        label = new Label(0, D_Index, "项目\\日期",wff2);
	        sheet.addCell(label);
	        label = new Label(1, D_Index, "销售量",wff2);
	        sheet.addCell(label);
	        label = new Label(2, D_Index, "卸车量",wff2);
	        sheet.addCell(label);
	        label = new Label(3, D_Index, "运营亏损",wff2);
	        sheet.addCell(label);		  
	        label = new Label(4, D_Index, "购入气量",wff2);
	        sheet.addCell(label);		 
	        label = new Label(5, D_Index, "各服务单位分销量",wff2);	  
	        sheet.addCell(label);	
	        for(int b = 1; b<num; b++)
	        {
	        	 label = new Label(5+b, D_Index, "",wff2);
			     sheet.addCell(label);				    	   
		       }			       
	        sheet.mergeCells(5,D_Index,4+num,D_Index) ; 
	        //合并
	        
	        D_Index++;
	        sheet.setRowView(D_Index, 400);
	        sheet.setColumnView(D_Index, 20);
	        label = new Label(0, D_Index, "",wff2);
	        sheet.addCell(label);
	        label = new Label(1, D_Index, "液态LNG(kg)",wff2);
	        sheet.addCell(label);
	        label = new Label(2, D_Index, "液态LNG(kg)",wff2);
	        sheet.addCell(label);
	        label = new Label(3, D_Index, "液态LNG(kg)",wff2);
	        sheet.addCell(label);		  
	        label = new Label(4, D_Index, "液态LNG(kg)",wff2);
	        sheet.addCell(label);
	        sheet.mergeCells(0,1,0,2);	
	        if(null != Pro_Crm_GGUser)
		 	{
		 		Iterator iters = Pro_Crm_GGUser.iterator();
		 		int a = 5;
		 		while(iters.hasNext())
		 		{
		 			ProLCrmBean CrmBean =(ProLCrmBean)iters.next();
		 			String Crm_Name       =   CrmBean.getCrm_Name();
		 			count++;
		 			CNameList.add(Crm_Name);
		 			label = new Label(a, D_Index, Crm_Name,wff2);
			        sheet.addCell(label);
			        a++;
		 		}
		 	}
	
	        if(null != Pro_GX_GNB)
			{
				Iterator iterator = Pro_GX_GNB.iterator();
				while(iterator.hasNext())
				{
					ProLBean Bean = (ProLBean)iterator.next();						
					if(!T_Cpm_Id.equals(Bean.getCpm_Id()))
					{																					
						CTime = Bean.getCTime();
						DTime = CTime.substring(4,6);
						Value_O = Bean.getValue_O();
						Value_O_Gas = Bean.getValue_O_Gas();
						Value_I = Bean.getValue_I();
						Value_I_Gas = Bean.getValue_I_Gas();
						Value_R = Bean.getValue_R();
						Value_R_Gas = Bean.getValue_R_Gas();
						Value_PAL = Bean.getValue_PAL();
						Value_PAL_Gas = Bean.getValue_PAL_Gas();
					
						Value_O_All     = Value_O_All + Double.parseDouble(Value_O);
						Value_O_Gas_All = Value_O_Gas_All + Double.parseDouble(Value_O_Gas);
						Value_I_All     = Value_I_All + Double.parseDouble(Value_I);
						Value_I_Gas_All = Value_I_Gas_All + Double.parseDouble(Value_I_Gas);
						Value_R_All     = Value_R_All + Double.parseDouble(Value_R);
						Value_R_Gas_All = Value_R_Gas_All + Double.parseDouble(Value_R_Gas);
						Value_PAL_All     = Value_PAL_All + Double.parseDouble(Value_PAL);
						Value_PAL_Gas_All = Value_PAL_Gas_All + Double.parseDouble(Value_PAL_Gas);
	
						D_Index++;
				        sheet.setRowView(D_Index, 400);
				        sheet.setColumnView(D_Index, 20);
				        label = new Label(0, D_Index, DTime+"月",wff2);
				        sheet.addCell(label);
				        label = new Label(1, D_Index, Value_O,wff2);
				        sheet.addCell(label);
				        label = new Label(2, D_Index, Value_I,wff2);
				        sheet.addCell(label);
				        label = new Label(3, D_Index, Value_PAL,wff2);
				        sheet.addCell(label);		  
				        label = new Label(4, D_Index, Value_I,wff2);
				        sheet.addCell(label);
				        
				        if(null != CNameList && null != Pro_O_GNB)
					     {
					     		if(CrmVa!=null && !CrmVa.isEmpty())
					     		{
					     			CrmVa.clear();
					     		}			
					     		Iterator cnameInter = CNameList.iterator();
					     		int b = 5;
					     	while(cnameInter.hasNext())
									{
					     		String cname =(String)cnameInter.next();
					     		String 	Crm_Value = "0.00";
					     		String  Crm_Value_Gas ="0.00";
					     		String Crm_Value2   ="";
					     		String Crm_Value_Gas2="";
					     		Value_Crm_All = 0.00;
					     		Value_Crm_Gas_All   = 0.0;
					     		Iterator crmiter = Pro_O_GNB.iterator();
					     		while(crmiter.hasNext())
					     		{
					     			ProLCrmBean pCBean =(ProLCrmBean)crmiter.next();
					     			String Crm_CTime      =   pCBean.getCTime();
										String CrmName       	=   pCBean.getCrm_Name();											 	 	
								 	if(cname.equals(CrmName) && Crm_CTime.substring(4,6).equals(DTime))
								 		{									 	
											 Crm_Value          =   pCBean.getValue_I();
									     Crm_Value_Gas      =   pCBean.getValue_I_Gas();
								 		}
								 	if(CrmName.equals(cname))
								 		{
								 			Crm_Value2       =   pCBean.getValue_I();
									    Crm_Value_Gas2   =   pCBean.getValue_I_Gas();
									    Value_Crm_All     =  Value_Crm_All + Double.parseDouble(Crm_Value2); 	
									    Value_Crm_Gas_All =  Value_Crm_Gas_All + Double.parseDouble(Crm_Value_Gas2); 
									    Crm_Str           = 	Value_Crm_All + "," +	Value_Crm_Gas_All;					   									           
								 		}			
					     		 } 
					     		 CrmVa.add(Crm_Str);
					     		 label = new Label(b, D_Index, Crm_Value,wff2);
							      sheet.addCell(label);
							      b++;
					     		} 		
					     }
				}
			}
		}
	        
	        D_Index++;
	        sheet.setRowView(D_Index, 400);
	        sheet.setColumnView(D_Index, 20);
	        label = new Label(0, D_Index, "本年累计",wff2);
	        sheet.addCell(label);
	        label = new Label(1, D_Index, df.format(Value_O_All),wff2);
	        sheet.addCell(label);
	        label = new Label(2, D_Index, df.format(Value_I_All),wff2);
	        sheet.addCell(label);
	        label = new Label(3, D_Index, df.format(Value_PAL_All),wff2);
	        sheet.addCell(label);		  
	        label = new Label(4, D_Index, df.format(Value_I_All),wff2);
	        sheet.addCell(label);
	        
	        if(null != CrmVa)
			{							
				Iterator vator = CrmVa.iterator();
				int c = 5;
				while(vator.hasNext())
				{
				String vd1 = (String)vator.next();
				String[] vd2 =vd1.split(","); 							
				label = new Label(c, D_Index, vd2[0],wff2);
			    sheet.addCell(label);		
			    c++;
				}
			}
/*****************************************站级年统计明细**********************************************************************/	
	        msgBean = pRmi.RmiExec(3, this, 0);
	        ArrayList<?> temp3 = (ArrayList<?>)msgBean.getMsg();	
	        if(null != temp3)
			 {					 
				Iterator ite = temp3.iterator();				
				while(ite.hasNext())
				{
					ProLBean gtBean = (ProLBean)ite.next();		
					Cpm_Id = gtBean.getCpm_Id();
					Cpm_Name = gtBean.getCpm_Name();
					
					ProLBean pLBean = new ProLBean();
					pLBean.setCpm_Id(Cpm_Id);
					pLBean.setYear(Year);		    			
					pLBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
					
					ProLCrmBean pCmBean = new ProLCrmBean();
					pCmBean.setCpm_Id(Cpm_Id);
					pCmBean.setYear(Year);
					pCmBean.setFunc_Corp_Id(currStatus.getFunc_Corp_Id());
					msgBean = pRmi.RmiExec(6, pLBean, 0);
					ArrayList<?> Pro_GX_ZNB = (ArrayList<?>)msgBean.getMsg();
					msgBean =pRmi.RmiExec(5, pCmBean, 0); 
					ArrayList<?> Pro_Crm_ZUser = (ArrayList<?>)msgBean.getMsg();
					msgBean =pRmi.RmiExec(4, pCmBean, 0); 
					ArrayList<?> Pro_O_ZNB = (ArrayList<?>)msgBean.getMsg();
					String psheetName =Cpm_Name+"站";
					sheet = book.createSheet(psheetName, 1);
					CNameList   = new ArrayList<String>();
					CrmVa       = new ArrayList<String>();
					T_Cpm_Id       = "";
					T_Cpm_Name     = "";
					 CTime          = "";
					 DTime          = "";
					 Value_O        = "0";
					 Value_O_Gas    = "0";
					 Value_I        = "0";
					 Value_I_Gas    = "0";
					 Value_R        = "0";
					 Value_R_Gas    = "0";
					 Value_PAL        = "0";
					 Value_PAL_Gas    = "0";
						
					//本年累计
					String Value_O_Y     = "0";
					String Value_O_Gas_Y = "0";
					String Value_I_Y     = "0";
					String Value_I_Gas_Y = "0";
					String Value_R_Y     = "0";
					String Value_R_Gas_Y = "0";
					 Crm_Str       = "" ;
						
					 Value_O_All       = 0.0;
					 Value_O_Gas_All   = 0.0;
					 Value_I_All       = 0.0;
					 Value_I_Gas_All   = 0.0;	
					 Value_R_All       = 0.0;
					 Value_R_Gas_All   = 0.0;
					 Value_PAL_All       = 0.0;
					 Value_PAL_Gas_All   = 0.0;	
					 Value_Crm_All       = 0.0;
					 Value_Crm_Gas_All   = 0.0;	
											
					//统计数量
					int cnt              = 0;				
					    num              = 0;					
					    D_Index          = -1;
						label            = null;		
								
							 if(null != Pro_Crm_ZUser)
							 	{
							 		Iterator iter = Pro_Crm_ZUser.iterator();				 	
							 		while(iter.hasNext())
							 		{
							 			ProLCrmBean CBean =(ProLCrmBean)iter.next();
							 			num++;

							 		}
							 	}					
							 
							D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, Cpm_Name+"站"+Year+"年LNG资源购销统计表",wff);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, "");
					        sheet.addCell(label);
					        label = new Label(2, D_Index, "");
					        sheet.addCell(label);
					        label = new Label(3, D_Index, "");
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, "");
					        sheet.addCell(label);		 
					        label = new Label(5, D_Index, "");
					        sheet.addCell(label);		 		       		        
					        for(int x =1;x<num;x++)
					        {
					        	
					        	label = new Label(5+x, D_Index, "");
						        sheet.addCell(label);				    	   
					       }
					       sheet.mergeCells(0,D_Index,4+num,D_Index);
					        
					        
					        
					        D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, "项目\\日期",wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, "销售量",wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, "卸车量",wff2);
					        sheet.addCell(label);
					        label = new Label(3, D_Index, "运营亏损",wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, "购入气量",wff2);
					        sheet.addCell(label);		 
					        label = new Label(5, D_Index, "各服务单位分销量",wff2);
					        sheet.addCell(label);		 		       
					        for(int y =1;y<num;y++)
					        {
					        	
					        	label = new Label(5+y, D_Index, "");
						        sheet.addCell(label);				    	   
					       }
					        if(num > 1)
					        {
					       sheet.mergeCells(5,D_Index,4+num,D_Index);
					        }
					        D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, "",wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, "液态LNG(kg)",wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, "液态LNG(kg)",wff2);
					        sheet.addCell(label);
					        label = new Label(3, D_Index, "液态LNG(kg)",wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, "液态LNG(kg)",wff2);
					        sheet.addCell(label);		 
					        sheet.mergeCells(0,1,0,2);			      	
					        if(null != Pro_Crm_ZUser)
						 	{
						 		Iterator iters = Pro_Crm_ZUser.iterator();
						 		int a = 5;
						 		while(iters.hasNext())
						 		{
						 			ProLCrmBean CrmBean =(ProLCrmBean)iters.next();
						 			String Crm_Name       =   CrmBean.getCrm_Name();
						 			CNameList.add(Crm_Name);						 			
						 			label = new Label(a, D_Index, Crm_Name,wff2);
							        sheet.addCell(label);	
							        a++;
						 		}
						 	}						 	
					        if(null != Pro_GX_ZNB)
							{										
								Iterator iterator = Pro_GX_ZNB.iterator();
								while(iterator.hasNext())
								{
									ProLBean Bean = (ProLBean)iterator.next();			
						
									if(!T_Cpm_Id.equals(Bean.getCpm_Id()))
									{																													
										CTime = Bean.getCTime();
										DTime = CTime.substring(4,6);
										Value_O = Bean.getValue_O();
										Value_O_Gas = Bean.getValue_O_Gas();
										Value_I = Bean.getValue_I();
										Value_I_Gas = Bean.getValue_I_Gas();
										Value_R = Bean.getValue_R();
										Value_R_Gas = Bean.getValue_R_Gas();
										Value_PAL = Bean.getValue_PAL();
										Value_PAL_Gas = Bean.getValue_PAL_Gas();
									
										Value_O_All     = Value_O_All + Double.parseDouble(Value_O);
										Value_O_Gas_All = Value_O_Gas_All + Double.parseDouble(Value_O_Gas);
										Value_I_All     = Value_I_All + Double.parseDouble(Value_I);
										Value_I_Gas_All = Value_I_Gas_All + Double.parseDouble(Value_I_Gas);
										Value_R_All     = Value_R_All + Double.parseDouble(Value_R);
										Value_R_Gas_All = Value_R_Gas_All + Double.parseDouble(Value_R_Gas);
										Value_PAL_All     = Value_PAL_All + Double.parseDouble(Value_PAL);
										Value_PAL_Gas_All = Value_PAL_Gas_All + Double.parseDouble(Value_PAL_Gas);
								
										D_Index++;
								        sheet.setRowView(D_Index, 400);
								        sheet.setColumnView(D_Index, 20);
								        label = new Label(0, D_Index, DTime+"月",wff2);
								        sheet.addCell(label);
								        label = new Label(1, D_Index, Value_O,wff2);
								        sheet.addCell(label);
								        label = new Label(2, D_Index, Value_I,wff2);
								        sheet.addCell(label);
								        label = new Label(3, D_Index, Value_PAL,wff2);
								        sheet.addCell(label);		  
								        label = new Label(4, D_Index, Value_I,wff2);
								        sheet.addCell(label);		 
								        
								        if(null != CNameList && null != Pro_O_ZNB)
									     {
									     	if(CrmVa!=null && !CrmVa.isEmpty())
									     	{
									     		CrmVa.clear();
									     	}						    			     
									     	Iterator cnameInter = CNameList.iterator();
									     	int b = 5;
									     	while(cnameInter.hasNext())
											{
									     		String cname =(String)cnameInter.next();
									     		String 	Crm_Value = "0.00";
									     		String  Crm_Value_Gas ="0.00";
									     		String Crm_Value2   ="";
									     		String Crm_Value_Gas2="";
									     		Value_Crm_All = 0.00;
									     		Value_Crm_Gas_All   = 0.0;
									     		Iterator crmiter = Pro_O_ZNB.iterator();
									     		while(crmiter.hasNext())
									     		{
									     			ProLCrmBean pCBean =(ProLCrmBean)crmiter.next();
									     			String Crm_CTime      =   pCBean.getCTime();
														String CrmName       	=   pCBean.getCrm_Name();											 	 	
												 	if(CrmName.equals(cname) && Crm_CTime.substring(4,6).equals(DTime))
												 	{									 	
															 Crm_Value          =   pCBean.getValue_I();
													     Crm_Value_Gas      =   pCBean.getValue_I_Gas();
													   							  										    										 
												 	}	
												 	if(CrmName.equals(cname))
												 	{
												 			Crm_Value2       =   pCBean.getValue_I();
													    Crm_Value_Gas2   =   pCBean.getValue_I_Gas();
													    Value_Crm_All     =  Value_Crm_All + Double.parseDouble(Crm_Value2); 	
													    Value_Crm_Gas_All =  Value_Crm_Gas_All + Double.parseDouble(Crm_Value_Gas2); 
													    Crm_Str           = 	Value_Crm_All + "," +	Value_Crm_Gas_All;					   									           
												 	}									 		 								 										 		
									     		 } 
									     		  CrmVa.add(Crm_Str);
									     		 label = new Label(b, D_Index, Crm_Value,wff2);
											     sheet.addCell(label);								     					     							     							     		 									     		 
												 b++;					 										 													 				 										 						 				 			 			
									     		} 						     				
									     }					
								  }
								}
							}	
					        D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 20);
					        label = new Label(0, D_Index, "本年累计",wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, df.format(Value_O_All),wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, df.format(Value_I_All),wff2);
					        sheet.addCell(label);
					        label = new Label(3, D_Index, df.format(Value_PAL_All),wff2);
					        sheet.addCell(label);		  
					        label = new Label(4, D_Index, df.format(Value_I_All),wff2);
					        sheet.addCell(label);		 
					        if(null != CrmVa)
							{							
								Iterator vator = CrmVa.iterator();
								int c = 5;
								while(vator.hasNext())
								{
								String vd1 = (String)vator.next();
								String[] vd2 =vd1.split(","); 	
								
								label = new Label(c, D_Index, vd2[0],wff2);
							    sheet.addCell(label);		
							    c++;
								}
							}
					

				}
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
	        
	        
	} catch (Exception e) {
		
		}							
	}
	

	public String getSql(int pCmd)
	{
		String Sql = "";
		switch (pCmd) 
		{			
		case 0://月报-每日明细
			Sql = " select t.cpm_id, t.cpm_name, t.ctime, t.value_i, t.value_i_gas, t.value_i_cnt, t.value_o, t.value_o_gas, t.value_r, t.value_r_gas, t.cpm_ctime, t.cpm_ctype, t.value_pal, t.value_pal_gas " +
				  " from view_pro_l t " +
				  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  " and t.oil_ctype = '"+ currStatus.getFunc_Corp_Id() +"' " +
				  " and t.ctime >= '"+currStatus.getVecDate().get(0).toString().substring(0,10)+"' " +
				  " and t.ctime <= '"+currStatus.getVecDate().get(1).toString().substring(0,10)+"' " +
				  " order by t.cpm_id, t.ctime asc ";
			break;
		case 1://公司购销月统计销售量等
			Sql = " select t.cpm_id, t.cpm_name, t.ctime, ROUND(SUM(t.value_i),2) as value_i, ROUND(SUM(t.value_i_gas),2) as value_i_gas, t.value_i_cnt, ROUND(SUM(t.value_o),2) as value_o, ROUND(SUM(t.value_o_gas),2) as value_o_gas, ROUND(SUM(t.value_r),2) as value_r, ROUND(SUM(t.value_r_gas),2) as value_r_gas, t.cpm_ctime, t.cpm_ctype , ROUND(SUM(t.value_pal),2) as value_pal, ROUND(SUM(t.value_pal_gas),2) as value_pal_gas " +
				  " from view_pro_l t  " +
				  " where t.oil_ctype = '"+ currStatus.getFunc_Corp_Id() +"' " +
				  " and t.ctime >= '"+currStatus.getVecDate().get(0).toString().substring(0,10)+"' " +
				  " and t.ctime <= '"+currStatus.getVecDate().get(1).toString().substring(0,10)+"' " +
				  " group by substr(t.ctime, 8, 10)" +
				  " order by   t.ctime asc";
		break;
		case 2://公司购销统计查询站点
			Sql = " select t.cpm_id, t.cpm_name, t.ctime, t.value_i, t.value_i_gas, t.value_i_cnt, t.value_o, t.value_o_gas, t.value_r, t.value_r_gas, t.cpm_ctime, t.cpm_ctype, t.value_pal, t.value_pal_gas " +
				  " FROM view_pro_l t   " +
				  " where t.oil_ctype = '"+ currStatus.getFunc_Corp_Id() +"' " +
				  " and t.ctime >= '"+currStatus.getVecDate().get(0).toString().substring(0,10)+"' " +
				  " and t.ctime <= '"+currStatus.getVecDate().get(1).toString().substring(0,10)+"' " +
				  " GROUP BY t.cpm_id " +
				  " ORDER BY  t.cpm_id";
			break;
		case 3 ://购销统计年查询站点
			Sql = " select t.cpm_id, t.cpm_name, t.ctime, t.value_i, t.value_i_gas, t.value_i_cnt, t.value_o, t.value_o_gas, t.value_r, t.value_r_gas, t.cpm_ctime, t.cpm_ctype, t.value_pal, t.value_pal_gas " +
				  " FROM view_pro_l t   " +
				  " where t.oil_ctype = '"+ currStatus.getFunc_Corp_Id() +"' " +
				  " and t.ctime >= '"+Year+"-01-01' " +
				  " and t.ctime <= '"+Year+"-12-30' " +
				  " GROUP BY t.cpm_id " +
				  " ORDER BY  t.cpm_id";
			break;
		}
		return Sql;
	}
	
	public boolean getData(ResultSet pRs) 
	{
		boolean IsOK = true;
		try
		{
			setCpm_Id(pRs.getString(1));
			setCpm_Name(pRs.getString(2));
			setCTime(pRs.getString(3));
			setValue_I(pRs.getString(4));
			setValue_I_Gas(pRs.getString(5));
			setValue_I_Cnt(pRs.getString(6));
			setValue_O(pRs.getString(7));
			setValue_O_Gas(pRs.getString(8));
			setValue_R(pRs.getString(9));
			setValue_R_Gas(pRs.getString(10));
			setCpm_CTime(pRs.getString(11));
			setCpm_CType(pRs.getString(12));		
			setValue_PAL(pRs.getString(13));
			setValue_PAL_Gas(pRs.getString(14));
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
			setCpm_Id(CommUtil.StrToGB2312(request.getParameter("Cpm_Id")));
			setCpm_Name(CommUtil.StrToGB2312(request.getParameter("Cpm_Name")));
			setCTime(CommUtil.StrToGB2312(request.getParameter("CTime")));
			setValue_I(CommUtil.StrToGB2312(request.getParameter("Value_I")));
			setValue_I_Gas(CommUtil.StrToGB2312(request.getParameter("Value_I_Gas")));
			setValue_I_Cnt(CommUtil.StrToGB2312(request.getParameter("Value_I_Cnt")));
			setValue_O(CommUtil.StrToGB2312(request.getParameter("Value_O")));
			setValue_O_Gas(CommUtil.StrToGB2312(request.getParameter("Value_O_Gas")));
			setValue_R(CommUtil.StrToGB2312(request.getParameter("Value_R")));
			setValue_R_Gas(CommUtil.StrToGB2312(request.getParameter("Value_R_Gas")));
			setValue_PAL(CommUtil.StrToGB2312(request.getParameter("Value_PAL")));
			setValue_PAL_Gas(CommUtil.StrToGB2312(request.getParameter("Value_PAL_Gas")));
			setCpm_CTime(CommUtil.StrToGB2312(request.getParameter("Cpm_CTime")));
			setCpm_CType(CommUtil.StrToGB2312(request.getParameter("Cpm_CType")));
			
			setYear(CommUtil.StrToGB2312(request.getParameter("Year")));
			setMonth(CommUtil.StrToGB2312(request.getParameter("Month")));
			setWeek(CommUtil.StrToGB2312(request.getParameter("Week")));
			setOperator_Name(CommUtil.StrToGB2312(request.getParameter("Operator_Name")));
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
			
			setBYear(CommUtil.StrToGB2312(request.getParameter("BYear")));
			setBMonth(CommUtil.StrToGB2312(request.getParameter("BMonth")));
			setEYear(CommUtil.StrToGB2312(request.getParameter("EYear")));
			setEMonth(CommUtil.StrToGB2312(request.getParameter("EMonth")));
			setBWeek(CommUtil.StrToGB2312(request.getParameter("BWeek")));
			setEWeek(CommUtil.StrToGB2312(request.getParameter("EWeek")));
			setBDate(CommUtil.StrToGB2312(request.getParameter("BDate")));
			setEDate(CommUtil.StrToGB2312(request.getParameter("EDate")));
		}
		catch (Exception Exp)
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String Cpm_Id;
	private String Cpm_Name;
	private String CTime;
	private String Value_I;
	private String Value_I_Gas;
	private String Value_I_Cnt;
	private String Value_O;
	private String Value_O_Gas;
	private String Value_R;
	private String Value_R_Gas;
	private String Value_PAL;
	private String Value_PAL_Gas;	
	private String Cpm_CTime;
	private String Cpm_CType;
	
	private String Crm_Name;
	private String Crm_Value_I;
	private String Crm_Value_I_Gas;
	
	
	private String Year;
	private String Month;
	private String Week;
	private String Operator_Name;
	private String Sid;
		
	private String Func_Corp_Id;
	
	//图表分析
	String BYear   = "";
	String BMonth  = "";
	String EYear   = "";
	String EMonth  = "";
	String BWeek   = "";
	String EWeek   = "";
	String BDate   = "";
	String EDate   = "";
	String TimeAll = "";
	
	
	
	
	
	
	
	
	
	
	
	public String getValue_PAL() {
		return Value_PAL;
	}

	public void setValue_PAL(String value_PAL) {
		Value_PAL = value_PAL;
	}

	public String getValue_PAL_Gas() {
		return Value_PAL_Gas;
	}

	public void setValue_PAL_Gas(String value_PAL_Gas) {
		Value_PAL_Gas = value_PAL_Gas;
	}

	public String getFunc_Corp_Id() {
		return Func_Corp_Id;
	}

	public void setFunc_Corp_Id(String func_Corp_Id) {
		Func_Corp_Id = func_Corp_Id;
	}
	
	public String getCrm_Name() {
		return Crm_Name;
	}

	public void setCrm_Name(String crm_Name) {
		Crm_Name = crm_Name;
	}

	public String getCrm_Value_I() {
		return Crm_Value_I;
	}

	public void setCrm_Value_I(String crm_Value_I) {
		Crm_Value_I = crm_Value_I;
	}

	public String getCrm_Value_I_Gas() {
		return Crm_Value_I_Gas;
	}

	public void setCrm_Value_I_Gas(String crm_Value_I_Gas) {
		Crm_Value_I_Gas = crm_Value_I_Gas;
	}

	public String getCpm_Id() {
		return Cpm_Id;
	}

	public void setCpm_Id(String cpmId) {
		Cpm_Id = cpmId;
	}

	public String getCpm_Name() {
		return Cpm_Name;
	}

	public void setCpm_Name(String cpmName) {
		Cpm_Name = cpmName;
	}

	public String getCTime() {
		return CTime;
	}

	public void setCTime(String cTime) {
		CTime = cTime;
	}

	public String getValue_I() {
		return Value_I;
	}

	public void setValue_I(String valueI) {
		Value_I = valueI;
	}

	public String getValue_I_Gas() {
		return Value_I_Gas;
	}

	public void setValue_I_Gas(String valueIGas) {
		Value_I_Gas = valueIGas;
	}
	
	public String getValue_I_Cnt() {
		return Value_I_Cnt;
	}

	public void setValue_I_Cnt(String valueICnt) {
		Value_I_Cnt = valueICnt;
	}

	public String getValue_O() {
		return Value_O;
	}

	public void setValue_O(String valueO) {
		Value_O = valueO;
	}

	public String getValue_O_Gas() {
		return Value_O_Gas;
	}

	public void setValue_O_Gas(String valueOGas) {
		Value_O_Gas = valueOGas;
	}

	public String getValue_R() {
		return Value_R;
	}

	public void setValue_R(String valueR) {
		Value_R = valueR;
	}

	public String getValue_R_Gas() {
		return Value_R_Gas;
	}

	public void setValue_R_Gas(String valueRGas) {
		Value_R_Gas = valueRGas;
	}
	
	public String getCpm_CTime() {
		return Cpm_CTime;
	}

	public void setCpm_CTime(String cpmCTime) {
		Cpm_CTime = cpmCTime;
	}

	public String getCpm_CType() {
		return Cpm_CType;
	}

	public void setCpm_CType(String cpmCType) {
		Cpm_CType = cpmCType;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

	public String getWeek() {
		return Week;
	}

	public void setWeek(String week) {
		Week = week;
	}

	public String getOperator_Name() {
		return Operator_Name;
	}

	public void setOperator_Name(String operatorName) {
		Operator_Name = operatorName;
	}

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public String getBYear() {
		return BYear;
	}

	public void setBYear(String bYear) {
		BYear = bYear;
	}

	public String getBMonth() {
		return BMonth;
	}

	public void setBMonth(String bMonth) {
		BMonth = bMonth;
	}

	public String getEYear() {
		return EYear;
	}

	public void setEYear(String eYear) {
		EYear = eYear;
	}

	public String getEMonth() {
		return EMonth;
	}

	public void setEMonth(String eMonth) {
		EMonth = eMonth;
	}

	public String getBWeek() {
		return BWeek;
	}

	public void setBWeek(String bWeek) {
		BWeek = bWeek;
	}

	public String getEWeek() {
		return EWeek;
	}

	public void setEWeek(String eWeek) {
		EWeek = eWeek;
	}

	public String getBDate() {
		return BDate;
	}

	public void setBDate(String bDate) {
		BDate = bDate;
	}

	public String getEDate() {
		return EDate;
	}

	public void setEDate(String eDate) {
		EDate = eDate;
	}

	public String getTimeAll() {
		return TimeAll;
	}

	public void setTimeAll(String timeAll) {
		TimeAll = timeAll;
	}
}