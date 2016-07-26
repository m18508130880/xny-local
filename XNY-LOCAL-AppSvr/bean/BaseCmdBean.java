package bean;

import net.appsvr.AppCnoocDataReqBean;
import net.appsvr.AppDeviceAlarmReqBean;
import net.appsvr.AppDeviceAlertReqBean;
import net.appsvr.AppDeviceDataReqBean;
import net.appsvr.AppDeviceStatusReqBean;
import util.*;

public abstract class BaseCmdBean
{
	public static long m_SessionId = (new java.util.Date().getTime()/1000);  //  此刻时长
	private String actionSource = "";   //动作源   Cpm_Id
	private String Reserve = "";        //预留
	private String Status = "0000";     //状态
	private int Action = 0;	            //命令值   Cmd    
	private int TestTime = (int)(new java.util.Date().getTime()/1000);       //  测试时间
	private String Seq = "";            //从线程启动时长值 ++
	public DBUtil m_DbUtil = null;
	
	public BaseCmdBean(int action, String seq, DBUtil dbUtil)
	{
		Action = action;
		Seq = seq;
		m_DbUtil = dbUtil;
	}
	
	public static BaseCmdBean getBean(int Cmd, DBUtil dbUtil)
	{
		BaseCmdBean retBean = null;
		switch(Cmd)
		{
			case Cmd_Sta.CMD_SUBMIT_1000:  //1000
				retBean = new AppDeviceStatusReqBean(Cmd, SessionId(), dbUtil);
				break;
			case Cmd_Sta.CMD_SUBMIT_1001:  //1001
				retBean = new AppDeviceDataReqBean(Cmd, SessionId(), dbUtil);
				break;
			case Cmd_Sta.CMD_SUBMIT_1003:  //1003
				retBean = new AppDeviceAlarmReqBean(Cmd, SessionId(), dbUtil);
				break;
			case Cmd_Sta.CMD_SUBMIT_1004:  //1004
				retBean = new AppDeviceAlertReqBean(Cmd, SessionId(), dbUtil);
				break;
			case Cmd_Sta.CMD_SUBMIT_1011:  //1011  集合数据解析
				retBean = new AppCnoocDataReqBean(Cmd, SessionId(), dbUtil);
				break;
		}
		return retBean;
	}
	
	public static synchronized String SessionId()
	{
		long ret = m_SessionId++;
		return CommUtil.LongToStringLeftFillZero(ret, 20);
	}
	
	public String GetSessionId()
	{
		return Seq;
	}
	
	public abstract void parseReqest(String key, String strRequest, byte[] strData);
	public abstract int execRequest();

	public abstract void parseReponse(String strResponse);
	public abstract void execResponse();
	
	public abstract void noticeTimeOut();
	
	public String getActionSource() {
		return actionSource;
	}

	public void setActionSource(String actionSource) {
		this.actionSource = actionSource;
	}

	public String getReserve() {
		return Reserve;
	}

	public void setReserve(String reserve) {
		Reserve = reserve;
	}

	public int getAction() {
		return Action;
	}

	public void setAction(int action) {
		Action = action;
	}

	public int getTestTime() {
		return TestTime;
	}

	public void setTestTime(int testTime) {
		TestTime = testTime;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getSeq() {
		return Seq;
	}

	public void setSeq(String seq) {
		Seq = seq;
	}
}
