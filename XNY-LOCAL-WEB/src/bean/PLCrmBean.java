package bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import rmi.RmiBean;
import util.*;

/** 销量确认表
 * @author cui
 *
 */
public class PLCrmBean extends RmiBean 
{	
	public final static long serialVersionUID = RmiBean.RMI_P_L_CRM;
	public long getClassId()
	{
		return serialVersionUID;
	}
	
	public PLCrmBean()
	{
		super.className = "PLCrmBean";
		
	}		 
	public String getSql(int pCmd)
	{
		String Sql = "";
		switch (pCmd) 
		{
		case 0://场站日报客户信息查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
					  " from view_pro_l_crm t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
					  " and t.ctime = date_format('"+VecDa.get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +				  	  
				  	  " order by  t.crm_id, t.ctime asc ";
			
			break;
		case 2://公司日报客户信息查询
				Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, ROUND(SUM(t.value_i),2) AS value_i, ROUND(SUM(t.value_i_gas),2) AS value_i_gas ,SUM(t.value_i_cnt) as value_i_cnt " +
					  " from view_pro_l_crm t " +
					  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
					  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
					  " and t.ctime = date_format('"+VecDa.get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +	
					  " group by t.crm_id "+
				  	  " order by  t.crm_id, t.ctime asc ";
			break;
			
		case 3://查询日期
			Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
				  " from view_pro_l_crm t " +
				  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
				  " and t.ctime >= date_format('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  " and t.ctime <= date_format('"+currStatus.getVecDate().get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  " group by t.ctime " +
				  " order by t.ctime ";
			break;
			
		case 4://查询客户
			Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
				  " from view_pro_l_crm t " +
				  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
				  " and t.ctime >= date_format('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
			      " and t.ctime <= date_format('"+currStatus.getVecDate().get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  " group by t.crm_id " +
				  " order by t.ctime ";
			break;
		case 5://查询数据
			Sql = " select t.crm_id, t.crm_name, t.cpm_id, t.cpm_name, t.ctime, t.oil_ctype, t.value_i, t.value_i_gas, t.value_i_cnt " +
				  " from view_pro_l_crm t " +
				  " where instr('"+ Cpm_Id +"', t.cpm_id) > 0 " +
				  " and t.oil_ctype like '"+ Func_Corp_Id +"%'" +
				  " and t.ctime >= date_format('"+currStatus.getVecDate().get(0).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  " and t.ctime <= date_format('"+currStatus.getVecDate().get(1).toString()+"', '%Y-%m-%d %H-%i-%S')" +
				  " order by t.crm_id, t.ctime";
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