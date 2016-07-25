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

/** 实时库存
 * @author cui
 *
 */
public class ProRBean extends RmiBean 
{	
	public final static long serialVersionUID = RmiBean.RMI_PRO_R;
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public ProRBean()
	{
		super.className = "ProRBean";
	}
	
	/** 增删改查
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
		
		msgBean = pRmi.RmiExec(currStatus.getCmd(), this, 0);
		switch(currStatus.getCmd())
		{
			case 10://添加
			case 11://编辑
			case 12://纠偏
				currStatus.setResult(MsgBean.GetResult(msgBean.getStatus()));
				msgBean = pRmi.RmiExec(0, this, 0);
			case 13://卸车计划更改
				currStatus.setResult(MsgBean.GetResult(msgBean.getStatus()));
				msgBean = pRmi.RmiExec(0, this, 0);
				
		    case 0://查询
		    	request.getSession().setAttribute("Pro_R_" + Sid, ((Object)msgBean.getMsg()));
		    	
		    	//所有类型
		    	msgBean = pRmi.RmiExec(2, this, 0);
		    	request.getSession().setAttribute("Pro_R_Type_" + Sid, ((Object)msgBean.getMsg()));
		    	
		    	currStatus.setJsp("Pro_R.jsp?Sid=" + Sid);
		    	break;		
		    case 3://历史记录查询		    	
		    	request.getSession().setAttribute("Pro_R_" + Sid, ((Object)msgBean.getMsg()));		    	
		    	//所有类型
		    	msgBean = pRmi.RmiExec(2, this, 0);
		    	request.getSession().setAttribute("Pro_R_Type_" + Sid, ((Object)msgBean.getMsg()));
		    	
		    	currStatus.setJsp("Pro_R_LS.jsp?Sid=" + Sid);
		    	break;
		}
		
		request.getSession().setAttribute("CurrStatus_" + Sid, currStatus);
	   	response.sendRedirect(currStatus.getJsp());
	}
	

	/** 明细导出 Excel 表格
	 * @param request
	 * @param response
	 * @param pRmi
	 * @param pFromZone
	 */
	public void ExportToExcel(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone) 
	{
		try
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
			
			//状态
			Func_Sub_Id = currStatus.getFunc_Sub_Id() + "";
			if(Func_Sub_Id.equals("9"))
			{
				Func_Sub_Id = "";
			}
			
			//清除历史
			
			//生成当前
			SimpleDateFormat SimFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String BT = currStatus.getVecDate().get(0).toString().substring(5,10);
			String ET = currStatus.getVecDate().get(1).toString().substring(5,10);
			String SheetName = "_" + BT + "," + ET;
			String UPLOAD_NAME = SimFormat.format(new Date()) + "_" + BT + "," + ET;
			
			msgBean = pRmi.RmiExec(0, this, 0);
			ArrayList<?> temp = (ArrayList<?>)msgBean.getMsg();
			if(temp != null)
			{
				WritableWorkbook book = Workbook.createWorkbook(new File(UPLOAD_PATH + UPLOAD_NAME + ".xls"));
	            WritableSheet sheet = book.createSheet(SheetName, 0);
	            WritableFont wf = new WritableFont(WritableFont.createFont("normal"), 18, WritableFont.BOLD , false);
				WritableCellFormat wff = new WritableCellFormat(wf);
				wf.setColour(Colour.BLACK);//字体颜色
				wff.setAlignment(Alignment.CENTRE);//设置居中
				wff.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
				wff.setBackground(jxl.format.Colour.TURQUOISE);//设置单元格的背景颜色			
				
				//字体格式2
				WritableFont wf2 = new WritableFont(WritableFont.createFont("normal"), 10, WritableFont.BOLD , false);
				WritableCellFormat wff2 = new WritableCellFormat(wf2);
				wf2.setColour(Colour.BLACK);//字体颜色
				wff2.setAlignment(Alignment.CENTRE);//设置居中
				wff2.setBorder(Border.ALL, BorderLineStyle.THIN);//设置边框线
				int D_Index            = -1;
				Label label       = null;
				
				  D_Index++;
		            sheet.setRowView(D_Index, 400);
		            sheet.setColumnView(D_Index, 12);
		            label = new Label(0, D_Index, "中海油珠海新能源有限公司资源调度表", wff);
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
		            sheet.mergeCells(0,D_Index,9,D_Index);
				
		            D_Index++;
		            sheet.setRowView(D_Index, 400);
		            sheet.setColumnView(D_Index, 12);
		            label = new Label(0, D_Index, "序号", wff2);
		            sheet.addCell(label);
		            label = new Label(1, D_Index, "日期", wff2);
		            sheet.addCell(label);
		            label = new Label(2, D_Index, "站点", wff2);
		            sheet.addCell(label);
		            label = new Label(3, D_Index, "储罐号", wff2);
		            sheet.addCell(label);
		            label = new Label(4, D_Index, "燃料类型", wff2);
		            sheet.addCell(label);
		            label = new Label(5, D_Index, "卸车计划", wff2);
		            sheet.addCell(label);
		            label = new Label(6, D_Index, "当前库存", wff2);
		            sheet.addCell(label);
		            label = new Label(7, D_Index, "预警阀值", wff2);
		            sheet.addCell(label);
		            label = new Label(8, D_Index, "预警状态", wff2);
		            sheet.addCell(label);		
		            label = new Label(9, D_Index, "运营状态", wff2);
		            sheet.addCell(label);	
		           
	            
	            Iterator<?> iterator = (Iterator<?>)temp.iterator();
				int i = 0;
				while(iterator.hasNext())
				{
					i++;
					ProRBean Bean = (ProRBean)iterator.next();					
					String D_Cpm_Name = Bean.getCpm_Name();
					String D_Oil_CType = Bean.getOil_CType();
					String D_Value = Bean.getValue();
					String D_Value_Ware = Bean.getValue_Ware();
					String D_Status = Bean.getStatus();
					//String D_Operator_Name = Bean.getOperator_Name();
					String D_Tank_NO = Bean.getTank_No();
					String D_Value_Plan = Bean.getValue_Plan();
					String CTime      = Bean.getCTime();
					String D_CTime    = CTime.substring(0, 10);
					String P_Unit       = Bean.getPUnit();
					String V_Unit       = Bean.getVUnit();
					String W_Unit        = Bean.getWUnit();
					if(null == D_Oil_CType){D_Oil_CType = "1000";}
					if(null == D_Value){D_Value = "0";}
					if(null == D_Value_Ware){D_Value_Ware = "0";}
					
					CorpInfoBean Corp_Info = (CorpInfoBean)request.getSession().getAttribute("User_Corp_Info_" + Sid);
					String D_Oil_Info = "";
					if(null != Corp_Info)
					{
						D_Oil_Info = Corp_Info.getOil_Info();
						if(null == D_Oil_Info)
						{
							D_Oil_Info = "";
						}
					}
					
					String str_Value_Ware = "正常";
					if(Double.parseDouble(D_Value) < Double.parseDouble(D_Value_Ware))
					{
						str_Value_Ware = "偏低";
					}
					
					String D_Oil_CName = "无";
					if(D_Oil_Info.trim().length() > 0)
					{
					  String[] List = D_Oil_Info.split(";");
					  for(int j=0; j<List.length && List[j].length()>0; j++)
					  {
					  	String[] subList = List[j].split(",");
					  	if(subList[0].equals(D_Oil_CType))
					  	{
					  		D_Oil_CName = subList[1];
					  		break;
					  	}
					  }
					}				
					
					String str_Status = "";
					switch(Integer.parseInt(D_Status))
					{
						case 0:
							str_Status = "在售";
							break;
						case 1:
							str_Status = "停售";
							break;
					}
					
					/**String D_Unit = "";	
					switch(Integer.parseInt(D_Oil_CType))
					{
						default:
						case 1000://汽油
						case 1010://90#汽油
						case 1011://90#无铅汽油
						case 1012://90#清洁汽油
						case 1020://92#汽油
						case 1021://92#无铅汽油
						case 1022://92#清洁汽油
						case 1030://93#汽油
						case 1031://93＃无铅汽油
						case 1032://93#清洁汽油
						case 1040://95#汽油
						case 1041://95#无铅汽油
						case 1042://95#清洁汽油
						case 1050://97#汽油
						case 1051://97#无铅汽油
						case 1052://97#清洁汽油
						case 1060://120＃汽油
						case 1080://其他车用汽油
						case 1090://98#汽油
						case 1091://98#无铅汽油
						case 1092://98＃清洁汽油
						case 1100://车用汽油
						case 1200://航空汽油
						case 1201://75#航空汽油
						case 1202://95#航空汽油
						case 1203://100#航空汽油
						case 1204://其他航空汽油
						case 1300://其他汽油
						case 2000://柴油
						case 2001://0#柴油
						case 2002://+5#柴油
						case 2003://+10#柴油
						case 2004://+15#柴油
						case 2005://+20#柴油
						case 2006://-5#柴油
						case 2007://-10#柴油
						case 2008://-15#柴油
						case 2009://-20#柴油
						case 2010://-30#柴油
						case 2011://-35#柴油
						case 2015://-50#柴油
						case 2100://轻柴油
						case 2016://其他轻柴油
						case 2200://重柴油
						case 2012://10#重柴油
						case 2013://20#重柴油
						case 2014://其他重柴油
						case 2300://军用柴油
						case 2301://-10#军用柴油
						case 2900://其他柴油
								D_Unit = "L";
							break;
						case 3001://CNG
						case 3002://LNG
								D_Unit = "kg";
							break;
					}**/
					D_Index++;
				    sheet.setRowView(D_Index, 400);
				    sheet.setColumnView(D_Index, 12);
		            label = new Label(0,D_Index,String.valueOf(i),wff2);    //序号
		            sheet.addCell(label);
		            label = new Label(1,D_Index,D_CTime,wff2);         //日期
		            sheet.addCell(label);
		            label = new Label(2,D_Index,D_Cpm_Name,wff2);      //站点
		            sheet.addCell(label);
		            label = new Label(3,D_Index,D_Tank_NO,wff2); //储罐号
		            sheet.addCell(label);
		            label = new Label(4,D_Index,D_Oil_CName,wff2);      //燃料类型
		            sheet.addCell(label);
		            label = new Label(5,D_Index,D_Value_Plan+P_Unit,wff2);          //卸车计划
		            sheet.addCell(label);
		            label = new Label(6,D_Index,D_Value+V_Unit,wff2);     //当前库存
		            sheet.addCell(label);		            
		            label = new Label(7,D_Index,D_Value_Ware+W_Unit,wff2);     //预警阀值
		            sheet.addCell(label);		    
		            label = new Label(8,D_Index,str_Value_Ware,wff2);     //预警状态
		            sheet.addCell(label);		            
		            label = new Label(9,D_Index,str_Status,wff2);     //运营状态
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
	}
	
	/**
	 * @param request
	 * @param response
	 * @param pRmi
	 * @param pFromZone
	 */
	public void ExDate(HttpServletRequest request, HttpServletResponse response, Rmi pRmi, boolean pFromZone)
	{
		try {
			getHtmlData(request);
			currStatus = (CurrStatus)request.getSession().getAttribute("CurrStatus_" + Sid);
			currStatus.getHtmlData(request, pFromZone);			
			String str = "0";;
			ArrayList<?> carlist;
			PrintWriter outprint = response.getWriter();
			msgBean = pRmi.RmiExec(4, this, 0);		
			if(null != msgBean.getMsg())
			{		
				carlist = (ArrayList<?>)msgBean.getMsg();				
				if(carlist != null)
				{
					Iterator<?> iterator = carlist.iterator();
					while(iterator.hasNext())
					{
						ProRBean Bean = (ProRBean)iterator.next();
						if(Bean.getCpm_Id().length()>0)
						{
							str ="1";
						}
					}
					
				}
			}					
			outprint.write(str);
			
		} catch (Exception e) {
			
		}				
	}
		
	/* (non-Javadoc)
	 * @see rmi.RmiBean#getSql(int)
	 */
	public String getSql(int pCmd)
	{
		String Sql = "";
		switch (pCmd)
		{
			case 0://查询
				Sql = " select a.cpm_id, a.cpm_name, a.oil_ctype, a.value, a.value_ware, a.status, a.ctime, a.operator, a.tank_no, a.value_plan, a.punit, a.vunit, a.wunit, a.operator_name" +
					  " from (select * from view_pro_r t " +
					  	"where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  	"and t.oil_ctype like '"+ Func_Corp_Id +"%' " +
					  	"and t.status like '"+ Func_Sub_Id +"%' " +
					  	"and SUBSTR(t.ctime,1,10) = CURDATE()  " +
					  	"order by t.ctime desc) a  " +
					  "group by a.cpm_id, a.oil_ctype, a.tank_no  ";
				break;
			case 1://所有业务
				Sql = " select t.cpm_id, t.cpm_name, t.oil_ctype, t.value, t.value_ware, t.status, t.ctime, t.operator, t.tank_no, t.value_plan, t.punit, t.vunit, t.wunit, t.operator_name" +
				  	  " from view_pro_r t " +
				  	  " order by t.cpm_id, t.oil_ctype asc ";
				break;
			case 2://所有类型
				Sql = " select '' as cpm_id, '' as cpm_name, t.oil_ctype, '' as value, '' as value_ware, '' as status, '' as ctime, '' as operator, '' as tank_no, '' as value_plan, '' as punit, ''as vunit, ''as wunit, '' as operator_name" +
			  	  	  " from view_pro_r t " +
			  	  	  " group by t.oil_ctype " +
			  	  	  " order by t.oil_ctype desc ";
				break;
			case 3://历史查询
				Sql = " select t.cpm_id, t.cpm_name, t.oil_ctype, t.value, t.value_ware, t.status, t.ctime, t.operator, t.tank_no, t.value_plan, t.punit, t.vunit, t.wunit, t.operator_name" +
					  " from view_pro_r t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
					  " and t.ctime >= date_format('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  	  " and t.ctime <= date_format('"+currStatus.getVecDate().get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
					  " ORDER BY t.cpm_id,t.tank_no,t.ctime desc";					
				break;
			case 4://查询记录
				Sql = " select t.cpm_id, t.cpm_name, t.oil_ctype, t.value, t.value_ware, t.status, t.ctime, t.operator, t.tank_no, t.value_plan, t.punit, t.vunit, t.wunit, t.operator_name" +
					  " from view_pro_r t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.oil_ctype = '"+ Oil_CType +"'" +
					  " and t.tank_no = '"+ Tank_No +"'"+
					  " and SUBSTR(t.ctime,1,10) = '"+ CTime +"' ";
				
				break;
						
				
			case 10://添加
				Sql = " insert into pro_r(cpm_id, oil_ctype, value, value_ware, status, ctime, operator, tank_no, value_plan, punit, vunit, wunit)" +
					  " values('"+ Cpm_Id +"', '"+ Oil_CType +"', '"+ Value +"', '"+ Value_Ware +"', '"+ Status +"', DATE_FORMAT(now(), '%Y-%m-%d %H:%i:%S'), '"+ Operator +"','"+ Tank_No +"','"+ Value_Plan +"','"+ PUnit +"','"+ VUnit +"','"+ WUnit +"')";
				break;
			case 11://编辑
				Sql = " update pro_r t set t.status = '"+ Status +"', t.operator = '"+ Operator +"' where t.cpm_id = '"+ Cpm_Id +"' and t.oil_ctype = '"+ Oil_CType +"' and t.tank_no = '"+ Tank_No +"'";
				break;
			case 12://纠偏
				Sql = " update pro_r t set t.value  = '"+ Value +"',  t.value_ware = '"+ Value_Ware +"', t.operator = '"+ Operator +"' where t.cpm_id = '"+ Cpm_Id +"' and t.oil_ctype = '"+ Oil_CType +"' ";
				break;
			case 13:
				Sql = " update pro_r t set t.value_plan  = '"+ Value_Plan +"',  t.value = '"+ Value +"', t.value_ware = '"+ Value_Ware +"' where t.cpm_id = '"+ Cpm_Id +"' and t.oil_ctype = '"+ Oil_CType +"' and t.tank_no = '"+ Tank_No +"'";
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
			setOil_CType(pRs.getString(3));
			setValue(pRs.getString(4));
			setValue_Ware(pRs.getString(5));
			setStatus(pRs.getString(6));
			setCTime(pRs.getString(7));
			setOperator(pRs.getString(8));
			setTank_No(pRs.getString(9));
			setValue_Plan(pRs.getString(10));
			setPUnit(pRs.getString(11));
			setVUnit(pRs.getString(12));
			setWUnit(pRs.getString(13));
			setOperator_Name(pRs.getString(14));
		}
		catch (SQLException sqlExp)
		{
			sqlExp.printStackTrace();
		}
		return IsOK;
	}
	
	/** 获取request页面 Status、Cpm_Name、Cpm_Id、 等数据
	 * @param request
	 * @return
	 */
	public boolean getHtmlData(HttpServletRequest request)
	{
		boolean IsOK = true;
		try
		{
			setCpm_Id(CommUtil.StrToGB2312(request.getParameter("Cpm_Id")));
			setCpm_Name(CommUtil.StrToGB2312(request.getParameter("Cpm_Name")));
			setOil_CType(CommUtil.StrToGB2312(request.getParameter("Oil_CType")));
			setValue(CommUtil.StrToGB2312(request.getParameter("Value")));
			setValue_Ware(CommUtil.StrToGB2312(request.getParameter("Value_Ware")));
			setStatus(CommUtil.StrToGB2312(request.getParameter("Status")));
			setCTime(CommUtil.StrToGB2312(request.getParameter("CTime")));
			setTank_No(CommUtil.StrToGB2312(request.getParameter("Tank_No")));
			setValue_Plan(CommUtil.StrToGB2312(request.getParameter("Value_Plan")));
			setOperator(CommUtil.StrToGB2312(request.getParameter("Operator")));
			setOperator_Name(CommUtil.StrToGB2312(request.getParameter("Operator_Name")));
			setPUnit(CommUtil.StrToGB2312(request.getParameter("PUnit")));
			setVUnit(CommUtil.StrToGB2312(request.getParameter("VUnit")));
			setWUnit(CommUtil.StrToGB2312(request.getParameter("WUnit")));			
			
			setSid(CommUtil.StrToGB2312(request.getParameter("Sid")));
		}
		catch (Exception Exp)
		{
			Exp.printStackTrace();
		}
		return IsOK;
	}
	
	private String Cpm_Id;
	private String Cpm_Name;
	private String Oil_CType;
	private String Value;
	private String Value_Ware;
	private String Status;
	private String CTime;
	private String Operator;
	private String Operator_Name;
	private String Tank_No;
	private String Value_Plan;
	private String PUnit;
	private String VUnit;
	private String WUnit;
	
	private String Sid;
	private String Func_Sub_Id;
	private String Func_Corp_Id;
	
	public String getPUnit() {
		return PUnit;
	}

	public void setPUnit(String pUnit) {
		PUnit = pUnit;
	}

	

	public String getVUnit() {
		return VUnit;
	}

	public void setVUnit(String vUnit) {
		VUnit = vUnit;
	}

	public String getWUnit() {
		return WUnit;
	}

	public void setWUnit(String wUnit) {
		WUnit = wUnit;
	}
	
	public String getTank_No() {
		return Tank_No;
	}

	public void setTank_No(String tank_No) {
		Tank_No = tank_No;
	}

	public String getValue_Plan() {
		return Value_Plan;
	}

	public void setValue_Plan(String value_Plan) {
		Value_Plan = value_Plan;
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

	public String getOil_CType() {
		return Oil_CType;
	}

	public void setOil_CType(String oilCType) {
		Oil_CType = oilCType;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
	
	public String getValue_Ware() {
		return Value_Ware;
	}

	public void setValue_Ware(String valueWare) {
		Value_Ware = valueWare;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getCTime() {
		return CTime;
	}

	public void setCTime(String cTime) {
		CTime = cTime;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
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

	public String getFunc_Sub_Id() {
		return Func_Sub_Id;
	}

	public void setFunc_Sub_Id(String funcSubId) {
		Func_Sub_Id = funcSubId;
	}

	public String getFunc_Corp_Id() {
		return Func_Corp_Id;
	}

	public void setFunc_Corp_Id(String funcCorpId) {
		Func_Corp_Id = funcCorpId;
	}
}