package bean;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import rmi.Rmi;
import rmi.RmiBean;
import util.*;

public class ProLCrmBean extends RmiBean 
{	
	public final static long serialVersionUID = RmiBean.RMI_PRO_L_CRM;
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public ProLCrmBean()
	{
		super.className = "ProLCrmBean";
	}
	
	public void ExecCmd(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) throws ServletException, IOException
	{
		getHtmlData(request);
		currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
		currStatus.getHtmlData(request, pFromZone);
		
		//类型
		Func_Corp_Id = currStatus.getFunc_Corp_Id();
		if(null == Func_Corp_Id || Func_Corp_Id.equals("9999"))
		{
			Func_Corp_Id = "";
		}
				
		switch(currStatus.getCmd())
		{
			case 0://客户统计
				msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
		    	request.getSession().setAttribute("Pro_L_Crm_" + Sid, ((Object)msgBean.getMsg()));
		    	currStatus.setJsp("Pro_L_Crm.jsp?Sid=" + Sid);
		    	
		    	msgBean = pRmi.RmiExec(1, this, 0);
		    	request.getSession().setAttribute("Pro_L_Crm_All_" + Sid, ((Object)msgBean.getMsg()));
		    	
		    	break;
			case 1:
				PLCrmBean pcBean = new PLCrmBean();
				pcBean.setCpm_Id(Cpm_Id);
				pcBean.setFunc_Corp_Id(Func_Corp_Id);
				pcBean.currStatus = currStatus;
				//日期
				msgBean = pRmi.RmiExec(3, pcBean, 0);
				request.getSession().setAttribute("Pro_Crm_Date_" + Sid, ((Object)msgBean.getMsg()));
				//公司
				msgBean = pRmi.RmiExec(4, pcBean, 0);
				request.getSession().setAttribute("Pro_Crm_User_" + Sid, ((Object)msgBean.getMsg()));
				//数据
				msgBean = pRmi.RmiExec(5, pcBean, 0);
				request.getSession().setAttribute("Pro_Crm_MX_" + Sid, ((Object)msgBean.getMsg()));
				currStatus.setJsp("Pro_L_CS.jsp?Sid=" + Sid);
				break;
		    	
		    	
		    	
		    	
		}
		//所有客户
		CrmInfoBean crmBean = new CrmInfoBean();
		msgBean = pRmi.RmiExec(1, crmBean, 0);
    	request.getSession().setAttribute("Crm_Info_" + Sid, ((Object)msgBean.getMsg()));
		
		//所有业务
    	ProRBean RBean = new ProRBean();
    	msgBean = pRmi.RmiExec(1, RBean, 0);
    	request.getSession().setAttribute("Pro_R_Buss_" + Sid, ((Object)msgBean.getMsg()));
    	
    	//所有类型
    	msgBean = pRmi.RmiExec(2, RBean, 0);
    	request.getSession().setAttribute("Pro_R_Type_" + Sid, ((Object)msgBean.getMsg()));
    	
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
	   	response.sendRedirect(currStatus.getJsp());
	}
	//销量确认表导出
	
	public void XLQRExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		try {
				getHtmlData(request);
				currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
				currStatus.getHtmlData(request, pFromZone);
			
				//类型
				Func_Corp_Id = currStatus.getFunc_Corp_Id();
				if(null == Func_Corp_Id || Func_Corp_Id.equals("9999"))
				{
					Func_Corp_Id = "";
				}
				
				String BDate = currStatus.getVecDate().get(0).toString().substring(0,10);
				String EDate = currStatus.getVecDate().get(1).toString().substring(0,10);
				
				SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
				String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
				String SheetName = "客户对张表";
				String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
				
				msgBean = pRmi.RmiExec(0, this, 0);
				ArrayList<?> temp0 = (ArrayList<?>)msgBean.getMsg();	
				
				CrmInfoBean infoBean  = new CrmInfoBean();
				msgBean = pRmi.RmiExec(1, infoBean, 0);
				ArrayList<?> temp1 = (ArrayList<?>)msgBean.getMsg();
				
				int D_Index       = -1;
				Label label       = null;
				String T_Crm_Name = "";
				String CrmList    = "";
				if(null != temp0)
				{
					WritableWorkbook book = Workbook.createWorkbook(new File(UPLOAD_PATH + UPLOAD_NAME + ".xls"));
		            WritableSheet sheet = book.createSheet(SheetName, 0);	   
		            WritableFont wf = new WritableFont(WritableFont.createFont("normal"), 18, WritableFont.BOLD , false);
					WritableCellFormat wff = new WritableCellFormat(wf);
					//wf.setColour(Colour.BLACK);//字体颜色
					wff.setAlignment(Alignment.CENTRE);//设置居中
					//wff.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线				
					//字体格式2
					WritableFont wf2 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
					WritableCellFormat wff2 = new WritableCellFormat(wf2);
					wf2.setColour(Colour.BLACK);//字体颜色	
					wff2.setAlignment(Alignment.CENTRE);//设置居中
					wff2.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
					//字体格式3
					WritableFont wf3 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
					WritableCellFormat wff3 = new WritableCellFormat(wf3);			
					//wff3.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线											
					//字体格式4
		            WritableFont wf4 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
					WritableCellFormat wff4 = new WritableCellFormat(wf4);
					wf4.setColour(Colour.BLACK);//字体颜色
					wff4.setAlignment(Alignment.CENTRE);//设置居中
					wff4.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
					wff4.setBackground(jxl.format.Colour.TURQUOISE);//设置单元格的背景颜色
																			
					Iterator ri = temp0.iterator();
					while(ri.hasNext())
					{				
						ProLCrmBean pBean = (ProLCrmBean)ri.next();	
						CrmList += pBean.getCrm_Id()+",";
					}
					if(null != temp1)
					{
						Iterator init = temp1.iterator();
						while(init.hasNext())
						{
							CrmInfoBean inBean = (CrmInfoBean)init.next();
							String Id  = inBean.getId();
							Crm_Name   = inBean.getBrief();
							Double Total_Value = 0.00;
							Double Total_Value_Gas = 0.00;		
							if(CrmList.contains(inBean.getId()+","))
							{
							
								D_Index++;
								sheet.setRowView(D_Index, 400);
								sheet.setColumnView(D_Index, 30);
								label = new Label(0, D_Index, "中海油珠海新能源有限公司"+Cpm_Name+"站LNG销售量确认单",wff);
				            	sheet.addCell(label);
				            	label = new Label(1, D_Index, "");
				            	sheet.addCell(label);
				            	label = new Label(2, D_Index, "");
				            	sheet.addCell(label);
				            	label = new Label(3, D_Index, "");
				            	sheet.addCell(label);		           
				            	sheet.mergeCells(0,D_Index,3,D_Index);
							
				            	D_Index++;
				            	sheet.setRowView(D_Index, 400);
				            	sheet.setColumnView(D_Index, 25);
				            	label = new Label(0, D_Index, "客户:"+Crm_Name,wff3);
				            	sheet.addCell(label);
				            	label = new Label(1, D_Index, "");
				            	sheet.addCell(label);
				            	sheet.mergeCells(0,D_Index,1,D_Index);
				            	label = new Label(2, D_Index, "结算起止时间:"+BDate+"至"+EDate,wff3);
				            	sheet.addCell(label);
				            	label = new Label(3, D_Index, "");
				            	sheet.addCell(label);		           
				            	sheet.mergeCells(2,D_Index,3,D_Index);
				            
				            	D_Index++;
				            	sheet.setRowView(D_Index, 400);
				            	sheet.setColumnView(D_Index, 25);
				            	label = new Label(0, D_Index, "时间",wff4);
				            	sheet.addCell(label);
				            	label = new Label(1, D_Index, "销售气量",wff4);
				            	sheet.addCell(label);
				            	label = new Label(2, D_Index, "");
				            	sheet.addCell(label);
				            	sheet.mergeCells(1,D_Index,2,D_Index);
				            	label = new Label(3, D_Index, "备注",wff4);
				            	sheet.addCell(label);		           
				            
				            	D_Index++;
				            	sheet.setRowView(D_Index, 400);
				            	sheet.setColumnView(D_Index, 25);
				            	label = new Label(0, D_Index, "",wff4);
				            	sheet.addCell(label);
				            	label = new Label(1, D_Index, "液态(kg)",wff4);
				            	sheet.addCell(label);
				            	label = new Label(2, D_Index, "折合气态(Nm3)",wff4);
				            	sheet.addCell(label);		            
				            	label = new Label(3, D_Index, "",wff4);
				            	sheet.addCell(label);		           
				            	sheet.mergeCells(0,2,0,3);
				            	sheet.mergeCells(3,2,3,3);				
					
					 Iterator<?> it = temp0.iterator();
					 while(it.hasNext())
					{															
						ProLCrmBean cBean = (ProLCrmBean)it.next();						
						if(Id.equals(cBean.getCrm_Id())){	
						T_Crm_Name  = inBean.getCName();
						Crm_Name    = cBean.getCrm_Name();
						CTime         = cBean.getCTime();
						Value_I       = cBean.getValue_I();
						Value_I_Gas   = cBean.getValue_I_Gas();
						Total_Value += Double.parseDouble(cBean.getValue_I());
						Total_Value_Gas += Double.parseDouble(cBean.getValue_I_Gas());																	
		             		           
						D_Index++;
				        sheet.setRowView(D_Index, 400);
				        sheet.setColumnView(D_Index, 25);
				        label = new Label(0, D_Index, CTime,wff2);
				        sheet.addCell(label);
				        label = new Label(1, D_Index, Value_I,wff2);
				        sheet.addCell(label);
				        label = new Label(2, D_Index, Value_I_Gas,wff2);
				        sheet.addCell(label);		            
				        label = new Label(3, D_Index, "",wff2);
				        sheet.addCell(label);		          	
				            
				       
						}		
					}
						 D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 25);
					        label = new Label(0, D_Index, "合计:",wff2);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, (new BigDecimal(Total_Value).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP))+" ",wff2);
					        sheet.addCell(label);
					        label = new Label(2, D_Index, (new BigDecimal(Total_Value_Gas).divide(new BigDecimal(1),2,java.math.RoundingMode.HALF_UP))+" ",wff2);
					        sheet.addCell(label);		            
					        label = new Label(3, D_Index, "",wff2);
					        sheet.addCell(label);		
					        
					        D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 25);
					        label = new Label(0, D_Index, Cpm_Name+"负责人: ",wff3);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, "");
					        sheet.addCell(label);
					        sheet.mergeCells(0,D_Index,1,D_Index);
					        label = new Label(2, D_Index, "客户公司:"+" "+T_Crm_Name,wff3);
					        sheet.addCell(label);		            
					        label = new Label(3, D_Index, "",wff3);
					        sheet.addCell(label);		      
					        sheet.mergeCells(2,D_Index,3,D_Index);
					        D_Index++;
					        sheet.setRowView(D_Index, 400);
					        sheet.setColumnView(D_Index, 25);
					        label = new Label(0, D_Index, "",wff3);
					        sheet.addCell(label);
					        label = new Label(1, D_Index, "",wff3);
					        sheet.addCell(label);
					        sheet.mergeCells(0,D_Index,1,D_Index);
					        label = new Label(2, D_Index, "客户代表(签章):",wff3);
					        sheet.addCell(label);		            
					        label = new Label(3, D_Index, "",wff3);
					        sheet.addCell(label);		      
					        sheet.mergeCells(2,D_Index,3,D_Index);
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
				}			
		} catch (Exception e) {
			
		}		
	}
	
	
	public void DZBExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		try {
				getHtmlData(request);
				currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
				currStatus.getHtmlData(request, pFromZone);
			
				//类型
				Func_Corp_Id = currStatus.getFunc_Corp_Id();
				if(null == Func_Corp_Id || Func_Corp_Id.equals("9999"))
				{
					Func_Corp_Id = "";
				}
			
				//状态
				Func_Sub_Id = currStatus.getFunc_Sub_Id() + "";
				if(Func_Sub_Id.equals("9"))
				{
					Func_Sub_Id = "";
				}
			
				//单号
				Func_Type_Id = currStatus.getFunc_Type_Id();
				if(null == Func_Type_Id)
				{
					Func_Type_Id = "";
				}
				String BDate = currStatus.getVecDate().get(0).toString().substring(0,10);
				String EDate = currStatus.getVecDate().get(1).toString().substring(0,10);
				
				SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
				String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
				String SheetName = "次数对账表";
				String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
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
				int Cnt_Car       = 0;
				int   cell        = 0;
				int           j   = 1;
				int   num         = 0;
				Double 		Total_Value_All = 0.0;	
				Label label       = null;	
				List<String> Pro_L_Day = new ArrayList<String>();	
				List<Double> Pro_Value_Day = new ArrayList<Double>();
				PLCrmBean pBean = new PLCrmBean();
				pBean.setCpm_Id(Cpm_Id);
				pBean.setFunc_Corp_Id(Func_Corp_Id);
				pBean.currStatus = currStatus;
				msgBean = pRmi.RmiExec(5, pBean, 0);//数据
				ArrayList<?> temp0 = (ArrayList<?>)msgBean.getMsg();
				msgBean = pRmi.RmiExec(3, pBean, 0);//日期
				ArrayList<?> temp2 = (ArrayList<?>)msgBean.getMsg();
				msgBean = pRmi.RmiExec(4, pBean, 0);//车辆
				ArrayList<?> temp3 = (ArrayList<?>)msgBean.getMsg();	
				if(null != temp2)
				{
		    		Iterator ri = temp2.iterator();
					while(ri.hasNext())
					{
						
						PLCrmBean dBean = (PLCrmBean)ri.next();	
						num++;
					}
				}
				
				if(null != temp0)
				{
					Iterator staIter = temp0.iterator();
					PLCrmBean 	OBean = (PLCrmBean)staIter.next();
					Cpm_Name = OBean.getCpm_Name();
					
				}
				D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, Cpm_Name+"站次数对账表"+BDate+"至"+EDate,wff);
		        sheet.addCell(label);		        
		        for(int a = 1; a<num; a++)
		        {
		        	label = new Label(a, D_Index, "",wff2);
			        sheet.addCell(label);
		        	
		        }
		        sheet.mergeCells(0,D_Index,2+num,D_Index);
		        
		        
		        D_Index++;
		        sheet.setRowView(D_Index, 400);
		        sheet.setColumnView(D_Index, 20);
		        label = new Label(0, D_Index, "单位(次)",wff2);
		        sheet.addCell(label);		        

		    	if(null != temp2)
				{
		    		Iterator riter = temp2.iterator();
					while(riter.hasNext())
					{
						cell++;
						PLCrmBean dayBean = (PLCrmBean)riter.next();
						Pro_L_Day.add(dayBean.getCTime().substring(5,10));
						Pro_Value_Day.add(0.00);
						 dayBean.getCTime().substring(8,10);
						
				label = new Label(cell, D_Index, dayBean.getCTime().substring(8,10)+"号",wff2);
				sheet.addCell(label);
						
			
					}
				}
		    	label = new Label(cell+1, D_Index, "合计",wff2);
			    sheet.addCell(label);
					
			   // **********************遍历所有车辆********************************************************************************/													
				if(null != temp3)
				{
					Iterator riter = temp3.iterator();
					while(riter.hasNext())
					{
						PLCrmBean carBean 				= (PLCrmBean)riter.next();
						String carName 					= carBean.getCrm_Name();
						
						
						
						
						
						Double Total_Value_Car 	= 0.00;						
						Cnt_Car ++;
						
				D_Index++;
				sheet.setRowView(D_Index, 400);
				sheet.setColumnView(D_Index, 20);				
				label = new Label(0, D_Index, carName,wff2);
				sheet.addCell(label);
			
						if(null != Pro_L_Day)
						{
							Iterator dayIter = Pro_L_Day.iterator();
							int i = 0;	
							j  = 1;
							while(dayIter.hasNext())
							{
								
								boolean flagHaveData = false;		//判断该车该日是否有数据，有则加数据td，无则加空白td
								String tmpDay = (String)dayIter.next();									
								if(null != temp0)
								{
									Iterator valueIter = temp0.iterator();									
									while(valueIter.hasNext())
									{
										
										PLCrmBean valueBean 	= (PLCrmBean)valueIter.next();
										String 		CValue 		= valueBean.getValue_I_Cnt();										//当前ProOBean的Value
										String 		CDay 			= valueBean.getCTime().substring(5,10);		//当前ProOBean的日期
										String 		CUnqStr		= valueBean.getCrm_Name();		
										
										//当前ProOBean的车牌号
										if(tmpDay.equals(CDay) && carName.equals(CUnqStr))
										{
											flagHaveData = true;
											Total_Value_Car += Double.parseDouble(CValue);
											Total_Value_All += Double.parseDouble(CValue);
											Pro_Value_Day.set(i, Pro_Value_Day.get(i) + Double.parseDouble(CValue));
									label = new Label(j, D_Index, CValue,wff2);
									sheet.addCell(label);	
										}																					
									}	
								}
								if(!flagHaveData)
								{
					label = new Label(j, D_Index, "0",wff2);
					sheet.addCell(label);	
								}
								j++;
								i++;							
							}							
						}
					label = new Label(j, D_Index, Double.toString(Total_Value_Car),wff2);
					sheet.addCell(label);						
					}
				}
	
				D_Index++;
				sheet.setRowView(D_Index, 400);
				sheet.setColumnView(D_Index, 20);				
				label = new Label(0, D_Index, "合计:",wff2);
				sheet.addCell(label);
				int a = 0;
				if(null != Pro_Value_Day)
				{
					Iterator dayIter = Pro_Value_Day.iterator();
					while(dayIter.hasNext())
					{
						a++;
						Double Value_Day = (Double)dayIter.next();
			label = new Label(a, D_Index, Double.toString(Value_Day),wff2);
			sheet.addCell(label);
					}
				}
				label = new Label(a+1, D_Index, Double.toString(Total_Value_All),wff2);
				sheet.addCell(label);			
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
			case 0://客户统计
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
					  " from view_pro_l_crm t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  "   and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
					  "   and t.crm_id like '" + Crm_Id +"%' " +
					  "   and t.ctime >= date_format('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  	  "   and t.ctime <= date_format('"+currStatus.getVecDate().get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  	  "   order by t.cpm_id, t.crm_id, t.ctime asc ";
				break;
			case 1://客户统计-全部
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
					  " from view_pro_l_crm t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " order by t.cpm_id, t.crm_id, t.ctime asc ";
				break;
			case 2://购销统计站级月统计客户信息查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
					  " from view_pro_l_crm t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
					  " and t.ctime >= date_format('"+VecDa.get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " and t.ctime <= date_format('"+VecDa.get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " order by t.cpm_id, t.crm_id, t.ctime asc ";				  
				break;
			case 3:   //购销统计站级月统计客户查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
					  " from view_pro_l_crm t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
					  " and t.ctime >= date_format('"+VecDa.get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " and t.ctime <= date_format('"+VecDa.get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " group by t.crm_name "+
					  " order by t.cpm_id, t.crm_id, t.ctime asc ";	
				break;
			case 4:  //购销统计站级年统计客户信息查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"01' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-01-01' " +
				  	  " and t.ctime <= '"+Year+"-01-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"02' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-02-01' " +
				  	  " and t.ctime <= '"+Year+"-02-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"03' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-03-01' " +
				  	  " and t.ctime <= '"+Year+"-03-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"04' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-04-01' " +
				  	  " and t.ctime <= '"+Year+"-04-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"05' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-05-01' " +
				  	  " and t.ctime <= '"+Year+"-05-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"06' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-06-01' " +
				  	  " and t.ctime <= '"+Year+"-06-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"07' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-07-01' " +
				  	  " and t.ctime <= '"+Year+"-07-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"08' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-08-01' " +
				  	  " and t.ctime <= '"+Year+"-08-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"09' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-09-01' " +
				  	  " and t.ctime <= '"+Year+"-09-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"10' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-10-01' " +
				  	  " and t.ctime <= '"+Year+"-10-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"11' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-11-01' " +
				  	  " and t.ctime <= '"+Year+"-11-31' " +
				  	  " group by t.cpm_id,t.crm_id " +
				  	  ")" +
				  	  "UNION" +
				  	  "(" +
				  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"12' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
			  	  	  " from view_pro_l_crm t " +
				  	  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  	  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
				  	  " and t.ctime >= '"+Year+"-12-01' " +
				  	  " and t.ctime <= '"+Year+"-12-31' " +
				  	  " group by t.cpm_id,t.crm_id" +
				  	  ")" +
				  	"order by cpm_id,ctime,crm_id";
				break;
			case 5: //站级购销年统计客户查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
					  " from view_pro_l_crm t  " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  " and t.ctime >= '"+Year+"-01-01' " +
					  " and t.ctime <= '"+Year+"-12-31' " +
					  " group by t.cpm_id,t.crm_id";
			break;
			case 6: //公司购销月统计客户查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
					  " from view_pro_l_crm t " +
					  " where t.oil_ctype like '"+ Func_Corp_Id +"%'" +
					  " and t.ctime >= date_format('"+VecDa.get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " and t.ctime <= date_format('"+VecDa.get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " group by t.crm_name "+
					  " order by t.cpm_id, t.crm_id, t.ctime asc ";	
			break;
			case 7://公司购销月统计客户信息查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, ROUND(SUM(t.value_i),2) as value_i, ROUND(SUM(t.value_i_gas),2) as value_i_gas, t.value_i_cnt  " +
					  " from view_pro_l_crm t  " +
					  " where  t.oil_ctype LIKE '"+ Func_Corp_Id +"%'" +
					  " and t.ctime >= date_format('"+VecDa.get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " and t.ctime <= date_format('"+VecDa.get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " group by substr(t.ctime, 8, 10),t.crm_name" +
					  " order by  t.ctime asc";
			break;
			case 8://公司级购销年统计客户查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
						  " from view_pro_l_crm t  " +
						  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
						  " and t.ctime >= '"+Year+"-01-01' " +
						  " and t.ctime <= '"+Year+"-12-30' " +
						  " group by t.crm_id";
			break;
			case 9://公司级购销年统计客户详细信息
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"01' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  " from view_pro_l_crm t " +
					  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  " and t.ctime >= '"+Year+"-01-01' " +
					  " and t.ctime <= '"+Year+"-01-31' " +
					  " group by t.crm_id " +
					  "UNION" +
					  "(" +
					  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"02' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  " from view_pro_l_crm t " +
					  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  " and t.ctime >= '"+Year+"-02-01' " +
					  " and t.ctime <= '"+Year+"-02-31' " +
					  " group by t.crm_id " +
					  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"03' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-03-01' " +
					  	  " and t.ctime <= '"+Year+"-03-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"04' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-04-01' " +
					  	  " and t.ctime <= '"+Year+"-04-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"05' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-05-01' " +
					  	  " and t.ctime <= '"+Year+"-05-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"06' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-06-01' " +
					  	  " and t.ctime <= '"+Year+"-06-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"07' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-07-01' " +
					  	  " and t.ctime <= '"+Year+"-07-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"08' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-08-01' " +
					  	  " and t.ctime <= '"+Year+"-08-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"09' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-09-01' " +
					  	  " and t.ctime <= '"+Year+"-09-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"10' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-10-01' " +
					  	  " and t.ctime <= '"+Year+"-10-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"11' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-11-01' " +
					  	  " and t.ctime <= '"+Year+"-11-31' " +
					  	  " group by t.crm_id " +
					  	  ")" +
					  	  "UNION" +
					  	  "(" +
					  	  " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, '"+ Year +"12' as ctime,t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas, t.value_i_cnt " +
				  	  	  " from view_pro_l_crm t " +
					  	  " where t.oil_ctype = '"+ Func_Corp_Id +"' " +
					  	  " and t.ctime >= '"+Year+"-12-01' " +
					  	  " and t.ctime <= '"+Year+"-12-31' " +
					  	  " group by t.crm_id" +
					  	  ")" +
					  	"order by ctime,crm_id";
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
			setCrm_Name(pRs.getString(2));
			setCpm_Id(pRs.getString(3));
			setCpm_Name(pRs.getString(4));
			setCTime(pRs.getString(5));
			setOil_CType(pRs.getString(6));
			setValue_I(pRs.getString(7));
			setValue_I_Gas(pRs.getString(8));
			setValue_I_Cnt(pRs.getString(9));
			
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
			setCrm_Name(CommUtil.StrToGB2312(request.getParameter("Crm_Name")));
			setCpm_Id(CommUtil.StrToGB2312(request.getParameter("Cpm_Id")));
			setCpm_Name(CommUtil.StrToGB2312(request.getParameter("Cpm_Name")));
			setCTime(CommUtil.StrToGB2312(request.getParameter("CTime")));
			setOil_CType(CommUtil.StrToGB2312(request.getParameter("Oil_CType")));
			setValue_I(CommUtil.StrToGB2312(request.getParameter("Value_I")));
			setValue_I_Gas(CommUtil.StrToGB2312(request.getParameter("Value_I_Gas")));
			setValue_I_Cnt(CommUtil.StrToGB2312(request.getParameter("Value_I_Cnt")));
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
		}
		catch (Exception Exp)
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String Crm_Id;
	private String Crm_Name;
	private String Cpm_Id;
	private String Cpm_Name;
	private String CTime;
	private String Oil_CType;
	private String Value_I;
	private String Value_I_Gas;
	private String Value_I_Cnt;
	
	
	private String Year;
	
	private String Sid;
	private String Func_Sub_Id;
	private String Func_Corp_Id;
	private String Func_Type_Id;
	private Vector<Object> VecDa;
	
	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public Vector<Object> getVecDa() {
		return VecDa;
	}

	public void setVecDa(Vector<Object> vecDa) {
		VecDa = vecDa;
	}

	public String getFunc_Sub_Id() {
		return Func_Sub_Id;
	}

	public void setFunc_Sub_Id(String func_Sub_Id) {
		Func_Sub_Id = func_Sub_Id;
	}

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

	public String getCrm_Id() {
		return Crm_Id;
	}

	public void setCrm_Id(String crmId) {
		Crm_Id = crmId;
	}

	public String getCrm_Name() {
		return Crm_Name;
	}

	public void setCrm_Name(String crmName) {
		Crm_Name = crmName;
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

	public String getOil_CType() {
		return Oil_CType;
	}

	public void setOil_CType(String oilCType) {
		Oil_CType = oilCType;
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

	public String getSid() {
		return Sid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}
}