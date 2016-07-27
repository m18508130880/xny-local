package net.appsvr;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import bean.BaseCmdBean;
import util.CmdUtil;
import util.Cmd_Sta;
import util.CommUtil;
import util.DBUtil;

public class AppJiNanXnyDataReqBean extends BaseCmdBean 
{	
	
	private String Dev_Id = "";
	private String Dev_Name = "";
	private String Dev_Attr_Id = "";
	private String Dev_Attr_Name = "";
	private String Dev_CTime = "";
	private byte[] Dev_CData = new byte[512];
	private String Dev_Unit = "";
	
	/** 构造器
	 * @param action
	 * @param seq
	 * @param dbUtil
	 */
	public AppJiNanXnyDataReqBean(int action, String seq, DBUtil dbUtil) 
	{
		super(action, seq, dbUtil);
	}

	@Override
	public void parseReqest(String srcKey, String strRequest, byte[] strData) 
	{
		// TODO Auto-generated method stub
		this.setActionSource(srcKey);
		this.setReserve(strRequest.substring(0, 20));
		this.setAction(Integer.parseInt(strRequest.substring(24, 28)));
		Dev_Id        = CommUtil.BSubstring(strRequest, 28, 10).trim();
		Dev_Name      = CommUtil.BSubstring(strRequest, 38, 30).trim();
		Dev_Attr_Id   = CommUtil.BSubstring(strRequest, 68, 4).trim();
		Dev_Attr_Name = CommUtil.BSubstring(strRequest, 72, 20).trim();
		Dev_CTime     = CommUtil.BSubstring(strRequest, 92, 20).trim();
		Dev_Unit      = CommUtil.BSubstring(strRequest, 624, 10).trim();
		System.arraycopy(strData, 152, Dev_CData, 0, 512);   //复制数组 
		//CommUtil.printMsg(Dev_CData, Dev_CData.length);
	}

	@Override
	public int execRequest()
	{
		// TODO Auto-generated method stub
		int ret = Cmd_Sta.STA_ERROR;
		try
		{	
			/**************************济南章丘瑞烨法兰流量计**************************/
			if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_27))
			{
				//数据解析   421DF247 42D40000 00000000 00000000  44210C29    42B27EE4
				//          温度             压力      脉冲频率    标况流量      累计百位以上   累计百位以下
				JiNanXnyData26Bean Data26Bean = new JiNanXnyData26Bean();				
				byte[] _Value1 = new byte[14];
				byte[] _Value2 = new byte[20];
				byte[] _Value3 = new byte[16];
				byte[] _Value4 = new byte[20];
				byte[] _Value5 = new byte[20];
				byte[] _Value6 = new byte[4];
				
				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
				System.out.println(Dev_CData.toString());
				
				DinStream.read(_Value1, 0, 14);
				Data26Bean.setCTime(new String(_Value1));
				
				DinStream.read(_Value2, 0, 20);
				Data26Bean.setTemperature((new String(_Value2)));
				
				DinStream.read(_Value3, 0, 20);
				Data26Bean.setStress((new String(_Value3)));
				
				DinStream.read(_Value4, 0, 20);
				Data26Bean.setStandard_Flow((new String(_Value4)));
						
				DinStream.read(_Value5, 0, 16);
				Data26Bean.setCumulative_High(new String(_Value5));
				
				DinStream.read(_Value6, 0, 20);
				Data26Bean.setCumulative_Low(new String(_Value6));
				
				DinStream.close();
				
				System.out.println("CTime:" + Data26Bean.getCTime());
				System.out.println("Order_Id:" + Data26Bean.getTemperature());
				System.out.println("Order_Value:" + new BigDecimal(Data26Bean.getStress()));
				System.out.println("Value:" + new BigDecimal(Data26Bean.getStandard_Flow()));
				System.out.println("Car_Id:" + Data26Bean.getCumulative_High());
				System.out.println("Car_Owner:" + Data26Bean.getCumulative_Low());
			
				
				String Sql = "insert into pro_i(cpm_id, ctime, order_id, order_value, value, oil_ctype, car_id, car_owner, worker)" +
		  	  	  			 "values('"+ this.getActionSource().trim() +"', " +
		  	  	  			 "date_format('"+ Data26Bean.getCTime().trim() +"', '%Y-%m-%d %H-%i-%S'), " +
		  	  	  			 "'"+ Data26Bean.getTemperature().trim() +"', " +
			  	  	     	 "'"+ Data26Bean.getStress().trim() +"', " +			  	  	     	 
			  	  	     	 "'"+ Data26Bean.getStandard_Flow().trim() +"', " +		  
			  	  	     	 "'"+ Data26Bean.getCumulative_High().trim() +"', " +
			  	  	     	 "'"+ Data26Bean.getCumulative_Low().trim() +"')";
				
				if(m_DbUtil.doUpdate(Sql))
				{
					ret = Cmd_Sta.STA_SUCCESS;
				}
				
			}
			/**************************济南章丘伊莱特流量计**************************/
			else if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_28))
			{//数据解析   421DF247 42D40000 00000000 00000000  44210C29    42B27EE4
				//          温度             压力      脉冲频率    标况流量      累计百位以上   累计百位以下
				JiNanXnyData26Bean Data26Bean = new JiNanXnyData26Bean();				
				byte[] _Value1 = new byte[14];
				byte[] _Value2 = new byte[20];
				byte[] _Value3 = new byte[16];
				byte[] _Value4 = new byte[20];
				byte[] _Value5 = new byte[20];
				byte[] _Value6 = new byte[4];
				
				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
				System.out.println(Dev_CData.toString());
				
				DinStream.read(_Value1, 0, 14);
				Data26Bean.setCTime(new String(_Value1));
				
				DinStream.read(_Value2, 0, 20);
				Data26Bean.setTemperature((new String(_Value2)));
				
				DinStream.read(_Value3, 0, 20);
				Data26Bean.setStress((new String(_Value3)));
				
				DinStream.read(_Value4, 0, 20);
				Data26Bean.setStandard_Flow((new String(_Value4)));
						
				DinStream.read(_Value5, 0, 16);
				Data26Bean.setCumulative_High(new String(_Value5));
				
				DinStream.read(_Value6, 0, 20);
				Data26Bean.setCumulative_Low(new String(_Value6));
				
				DinStream.close();
				
				System.out.println("CTime:" + Data26Bean.getCTime());
				System.out.println("Order_Id:" + Data26Bean.getTemperature());
				System.out.println("Order_Value:" + new BigDecimal(Data26Bean.getStress()));
				System.out.println("Value:" + new BigDecimal(Data26Bean.getStandard_Flow()));
				System.out.println("Car_Id:" + Data26Bean.getCumulative_High());
				System.out.println("Car_Owner:" + Data26Bean.getCumulative_Low());
			
				
				String Sql = "insert into pro_i(cpm_id, ctime, order_id, order_value, value, oil_ctype, car_id, car_owner, worker)" +
		  	  	  			 "values('"+ this.getActionSource().trim() +"', " +
		  	  	  			 "date_format('"+ Data26Bean.getCTime().trim() +"', '%Y-%m-%d %H-%i-%S'), " +
		  	  	  			 "'"+ Data26Bean.getTemperature().trim() +"', " +
			  	  	     	 "'"+ Data26Bean.getStress().trim() +"', " +			  	  	     	 
			  	  	     	 "'"+ Data26Bean.getStandard_Flow().trim() +"', " +		  
			  	  	     	 "'"+ Data26Bean.getCumulative_High().trim() +"', " +
			  	  	     	 "'"+ Data26Bean.getCumulative_Low().trim() +"')";
				
				if(m_DbUtil.doUpdate(Sql))
				{
					ret = Cmd_Sta.STA_SUCCESS;
				}
				
			}
			
			//回复
			setStatus(CommUtil.IntToStringLeftFillZero(ret, 4));
			execResponse();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return ret;
	}

	public void noticeTimeOut()
	{
		
	}
	
	@SuppressWarnings("unused")
	private String EncodeSendMsg()
	{
		String ret = null;
		return ret;
	}
	
	@Override
	public void parseReponse(String strResponse){
		// TODO Auto-generated method stub
		this.setStatus(strResponse.substring(20, 24));
	}

	@Override
	public void execResponse() {
		// TODO Auto-generated method stub
		String sendStr = EncodeRespMsg();
		if(null != sendStr)
		{
			String key = getReserve();
			TcpSvrAppGateWay.DisPatch(CmdUtil.COMM_DELIVER, this.getActionSource(), key + sendStr);
		}
	}	

	private String EncodeRespMsg()
	{
		String ret = null;
		ret = getStatus() + getAction();
		return ret;
	}

	public String getDev_Id() {
		return Dev_Id;
	}

	public void setDev_Id(String devId) {
		Dev_Id = devId;
	}

	public String getDev_Name() {
		return Dev_Name;
	}

	public void setDev_Name(String devName) {
		Dev_Name = devName;
	}

	public String getDev_Attr_Id() {
		return Dev_Attr_Id;
	}

	public void setDev_Attr_Id(String devAttrId) {
		Dev_Attr_Id = devAttrId;
	}

	public String getDev_Attr_Name() {
		return Dev_Attr_Name;
	}

	public void setDev_Attr_Name(String devAttrName) {
		Dev_Attr_Name = devAttrName;
	}

	public String getDev_CTime() {
		return Dev_CTime;
	}

	public void setDev_CTime(String devCTime) {
		Dev_CTime = devCTime;
	}

	public byte[] getDev_CData() {
		return Dev_CData;
	}

	public void setDev_CData(byte[] devCData) {
		Dev_CData = devCData;
	}

	public String getDev_Unit() {
		return Dev_Unit;
	}
	
	public void setDev_Unit(String devUnit) {
		Dev_Unit = devUnit;
	}
}