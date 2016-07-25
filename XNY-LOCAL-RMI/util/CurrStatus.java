package util;

import java.io.Serializable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class CurrStatus implements Serializable
{
	private static final long serialVersionUID = 100;
  	private String CheckCode = "";
	private int Cmd;
	private int CurrPage;
	private int TotalRecord;
	private int TotalPages;
	private int Func_Id;
	private int Func_Sub_Id;
	private int Func_Sel_Id;
	private String Func_Corp_Id;
	private String Func_Type_Id;
	private Vector<Object> VecDate;
	private String Result = null;
	private int Curr_Status;
	private String Jsp;
	
	
	public String getCheckCode() {
		return CheckCode;
	}
	public void setCheckCode(String checkCode) {
		CheckCode = checkCode;
	}
	public int getCmd() {
		return Cmd;
	}
	public void setCmd(int cmd) {
		Cmd = cmd;
	}
	public int getCurrPage() {
		return CurrPage;
	}
	public void setCurrPage(int currPage) {
		CurrPage = currPage;
	}
	public int getTotalRecord() {
		return TotalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		TotalRecord = totalRecord;
		TotalPages = (totalRecord + MsgBean.CONST_PAGE_SIZE -1)/MsgBean.CONST_PAGE_SIZE;
	}
	public int getTotalPages() {
		return TotalPages;
	}
	public void setTotalPages(int totalPages) {
		TotalPages = totalPages;
	}
	public int getFunc_Id() {
		return Func_Id;
	}
	public void setFunc_Id(int func_Id) {
		Func_Id = func_Id;
	}
	public int getFunc_Sub_Id() {
		return Func_Sub_Id;
	}
	public void setFunc_Sub_Id(int func_Sub_Id) {
		Func_Sub_Id = func_Sub_Id;
	}
	public int getFunc_Sel_Id() {
		return Func_Sel_Id;
	}
	public void setFunc_Sel_Id(int func_Sel_Id) {
		Func_Sel_Id = func_Sel_Id;
	}
	public String getFunc_Corp_Id() {
		return Func_Corp_Id;
	}
	public void setFunc_Corp_Id(String funcCorpId) {
		Func_Corp_Id = funcCorpId;
	}
	public String getFunc_Type_Id() {
		return Func_Type_Id;
	}
	public void setFunc_Type_Id(String funcTypeId) {
		Func_Type_Id = funcTypeId;
	}
	public Vector<Object> getVecDate() {
		return VecDate;
	}
	public void setVecDate(Vector<Object> vecDate) {
		VecDate = vecDate;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public int getCurr_Status() {
		return Curr_Status;
	}
	public void setCurr_Status(int curr_Status) {
		Curr_Status = curr_Status;
	}
	public String getJsp() {
		return Jsp;
	}
	public void setJsp(String jsp) {
		Jsp = jsp;
	}
	
	public void getHtmlData(HttpServletRequest request, boolean pFromZone) 
	{	
		try
		{
			if(pFromZone)
			{
				CurrPage = 1;
			}
			else
			{
				Cmd = CommUtil.StrToInt(request.getParameter("Cmd"));
				CurrPage = CommUtil.StrToInt(request.getParameter("CurrPage"));
				CurrPage = CurrPage==0?1:CurrPage;		
				Func_Id = CommUtil.StrToInt(request.getParameter("Func_Id"));
				Func_Sub_Id = CommUtil.StrToInt(request.getParameter("Func_Sub_Id"));
				Func_Sel_Id = CommUtil.StrToInt(request.getParameter("Func_Sel_Id"));		
				VecDate = CommUtil.getDate(request.getParameter("BTime"),request.getParameter("ETime"));			
				Func_Corp_Id = CommUtil.StrToGB2312(request.getParameter("Func_Corp_Id"));
				Func_Type_Id = CommUtil.StrToGB2312(request.getParameter("Func_Type_Id"));
			}
		}catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
	}
	
	public String GetPageHtml(String pForm) 
	{
		String s = "";
		int TotalPages = (TotalRecord + MsgBean.CONST_PAGE_SIZE -1)/MsgBean.CONST_PAGE_SIZE;
		if(0 == TotalRecord)
		{
			CurrPage = 0;
		}
		s += "页数：<strong>" + CurrPage + "</strong>/<strong>" + TotalPages + "</strong><span>[共<b>" + TotalRecord +"</b>条记录]</span>";
		s += "<a href=# onclick='GoPage(1)'>&nbsp;首页</a> ";		
		s += "<a href=# onclick='GoPage(" + (CurrPage - 1) +")'>&nbsp;上一页</a> ";	 	
		
		int beginPage = CurrPage - 4 < 1 ? 1 : CurrPage - 4; 
		if(beginPage <= TotalPages) 
		{
			for(int i=beginPage, j=0; i<=TotalPages&&j<6; i++,j++)
			{
				if(i == CurrPage)
				   s+="<a href=# onclick='GoPage("+i+")'><strong>"+i+"</strong></a> ";
				else
		           s+="<a href=# onclick='GoPage("+i+")'>"+i+"</a> ";
			}
		}
		if(CurrPage == TotalPages)
		{
			s+="<a >&nbsp;下一页</a>";		
			s+="<a >&nbsp;末页</a> ";
		}
		else
		{
			s+="<a href=# onclick='GoPage("+(CurrPage+1)+")'>&nbsp;下一页</a> ";
			s+="<a href=# onclick='GoPage("+TotalPages+")'>&nbsp;末页</a> ";
			
		}
		s+="到第<input name='ToPage' type='text' size='2'>页";
		s+="<input type='button' style='width:40px;height:20px' onClick='GoPage(" + pForm + ".ToPage.value)' value='确定'/>";
		return s; 
	}
}
