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

public class AppCnoocDataReqBean extends BaseCmdBean 
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
	public AppCnoocDataReqBean(int action, String seq, DBUtil dbUtil) 
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
			/**************************购入数据**************************/
			if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_20))
			{
				CnoocData20Bean Data20Bean = new CnoocData20Bean();				
				byte[] _Value1 = new byte[14];
				byte[] _Value2 = new byte[20];
				byte[] _Value5 = new byte[16];
				byte[] _Value6 = new byte[20];
				byte[] _Value7 = new byte[20];
				byte[] _Value8 = new byte[4];
				
				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
				DinStream.read(_Value1, 0, 14);
				Data20Bean.setCTime(new String(_Value1));
				DinStream.read(_Value2, 0, 20);
				Data20Bean.setOrder_Id(new String(_Value2));
				Data20Bean.setOrder_Value(CommUtil.converseInt(DinStream.readInt()));
				Data20Bean.setValue(CommUtil.converseInt(DinStream.readInt()));
				DinStream.read(_Value5, 0, 16);
				Data20Bean.setCar_Id(new String(_Value5));
				DinStream.read(_Value6, 0, 20);
				Data20Bean.setCar_Owner(new String(_Value6));
				DinStream.read(_Value7, 0, 20);
				Data20Bean.setWorker(new String(_Value7));
				DinStream.read(_Value8, 0, 4);
				Data20Bean.setOil_CType(new String(_Value8));
				DinStream.close();
				
				System.out.println("CTime:" + Data20Bean.getCTime());
				System.out.println("Order_Id:" + Data20Bean.getOrder_Id());
				System.out.println("Order_Value:" + new BigDecimal(Data20Bean.getOrder_Value()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
				System.out.println("Value:" + new BigDecimal(Data20Bean.getValue()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
				System.out.println("Car_Id:" + Data20Bean.getCar_Id());
				System.out.println("Car_Owner:" + Data20Bean.getCar_Owner());
				System.out.println("Worker:" + Data20Bean.getWorker());
				System.out.println("Oil_CType:" +  Data20Bean.getOil_CType());
				
				String Sql = "insert into pro_i(cpm_id, ctime, order_id, order_value, value, oil_ctype, car_id, car_owner, worker)" +
		  	  	  			 "values('"+ this.getActionSource().trim() +"', " +
		  	  	  			 "date_format('"+ Data20Bean.getCTime().trim() +"', '%Y-%m-%d %H-%i-%S'), " +
		  	  	  			 "'"+ Data20Bean.getOrder_Id().trim() +"', " +
			  	  	     	 "'"+ new BigDecimal(Data20Bean.getOrder_Value()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP) +"', " +
			  	  	     	 "'"+ new BigDecimal(Data20Bean.getValue()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP) +"', " +
			  	  	     	 "'"+ Data20Bean.getOil_CType().trim() +"', " +			  	  	     	 
			  	  	     	 "'"+ Data20Bean.getCar_Id().trim() +"', " +		  
			  	  	     	 "'"+ Data20Bean.getCar_Owner().trim() +"', " +
			  	  	     	 "'"+ Data20Bean.getWorker().trim() +"')";
				
				if(m_DbUtil.doUpdate(Sql))
				{
					ret = Cmd_Sta.STA_SUCCESS;
				}
			}
			/**************************单次加注作业销售数据**************************/
			else if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_21))
			{
				//数据解析
				CnoocData21Bean Data21Bean = new CnoocData21Bean();
				byte[] _Value1 = new byte[14];
				byte[] _Value5 = new byte[10];
				byte[] _Value6 = new byte[16];
				byte[] _Value7 = new byte[20];
				byte[] _Value8 = new byte[20];
				byte[] _Value9 = new byte[20];
				byte[] _Value10= new byte[20];
				byte[] _Value11= new byte[40];
				byte[] _Value12= new byte[4];
				
				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
				DinStream.read(_Value1, 0, 14);
				Data21Bean.setCTime(new String(_Value1));
				Data21Bean.setValue(CommUtil.converseInt(DinStream.readInt()));
				Data21Bean.setPrice(CommUtil.converseShort(DinStream.readShort()));
				Data21Bean.setAmt(CommUtil.converseInt(DinStream.readInt()));
				DinStream.read(_Value5, 0, 10);
				Data21Bean.setWorker(new String(_Value5));
				DinStream.read(_Value6, 0, 16);
				Data21Bean.setCar_Id(new String(_Value6));
				DinStream.read(_Value7, 0, 20);
				Data21Bean.setIC(new String(_Value7));
				DinStream.read(_Value8, 0, 20);
				Data21Bean.setCar_Owner(new String(_Value8));
				DinStream.read(_Value9, 0, 20);
				Data21Bean.setCar_CType(new String(_Value9));
				DinStream.read(_Value10, 0, 20);
				Data21Bean.setCar_BH(new String(_Value10));
				DinStream.read(_Value11, 0, 40);
				Data21Bean.setCar_DW(new String(_Value11));
				DinStream.read(_Value12, 0, 4);
				Data21Bean.setOil_CType(new String(_Value12));
				DinStream.close();
				
				//从郑兴获取相关信息
				if(m_DbUtil._Status.equals("1"))
				{
					CnoocDataZXBean DataZXBean = new CnoocDataZXBean();
					DataZXBean.setCardNo(Data21Bean.getIC());
					LinkedList<?> sendList = m_DbUtil.getSelFromZX(DataZXBean.getSql(0));
					if(null == sendList || sendList.isEmpty())
					{
						Data21Bean.setCar_DW("无");
						Data21Bean.setCar_Id("无");
						Data21Bean.setCar_Owner("无");
						Data21Bean.setCar_CType("无");
						Data21Bean.setCar_BH("无");
					}
					else
					{
						DataZXBean = (CnoocDataZXBean)sendList.removeFirst();
						Data21Bean.setCar_DW(DataZXBean.getAccName());
						Data21Bean.setCar_Id(DataZXBean.getCarNumber());
						Data21Bean.setCar_Owner(DataZXBean.getHolderName());
						Data21Bean.setCar_CType(DataZXBean.getCarType());
						Data21Bean.setCar_BH(DataZXBean.getCarBottle());
					}
				}
				
				System.out.println("Oil_CType:" +  Data21Bean.getOil_CType());
				System.out.println("CTime:" +  Data21Bean.getCTime());
				System.out.println("Value:" +  new BigDecimal(Data21Bean.getValue()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
				System.out.println("Price:" +  new BigDecimal(Data21Bean.getPrice()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
				System.out.println("Amt:" +  new BigDecimal(Data21Bean.getAmt()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
				System.out.println("Worker:" +  Data21Bean.getWorker());
				
				System.out.println("IC:" +  Data21Bean.getIC());
				System.out.println("Car_Id:" +  Data21Bean.getCar_Id());
				System.out.println("Car_CType:" +  Data21Bean.getCar_CType());
				System.out.println("Car_Owner:" +  Data21Bean.getCar_Owner());
				System.out.println("Car_BH:" +  Data21Bean.getCar_BH());
				System.out.println("Car_DW:" +  Data21Bean.getCar_DW());
				
				String Unq_Flag = "0";
				String Unq_Str  = "";
				if(null != Data21Bean.getIC() && Data21Bean.getIC().length() > 0)
				{
					Unq_Flag = "0";
					Unq_Str  = Data21Bean.getIC().trim();
				}
				else
				{
					Unq_Flag = "1";
					Unq_Str  = Data21Bean.getCar_Id().trim();
				}
				
				String Sql = "insert into pro_o(cpm_id, oil_ctype, ctime, value, price, amt, worker, unq_flag, unq_str, car_ctype, car_owner, car_bh, car_dw)" +
		  	  	  			 "values('"+ this.getActionSource().trim() +"', " +
		  	  	  			 "'"+ Data21Bean.getOil_CType().trim() +"', " +
			  	  	     	 "date_format('"+ Data21Bean.getCTime().trim() +"', '%Y-%m-%d %H-%i-%S'), " +
			  	  	     	 "'"+ new BigDecimal(Data21Bean.getValue()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP) +"', " +
			  	  	     	 "'"+ new BigDecimal(Data21Bean.getPrice()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP) +"', " +
			  	  	     	 "'"+ new BigDecimal(Data21Bean.getAmt()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP) +"', " +
			  	  	     	 "'"+ Data21Bean.getWorker().trim() +"', " +
			  	  	     	 "'"+ Unq_Flag +"', " +
			  	  	     	 "'"+ Unq_Str +"', " +
			  	  	     	 "'"+ Data21Bean.getCar_CType().trim() +"', " +
			  	  	 		 "'"+ Data21Bean.getCar_Owner().trim() +"', " +
			  	  			 "'"+ Data21Bean.getCar_BH().trim() +"', " +
			  	  	     	 "'"+ Data21Bean.getCar_DW().trim() +"')";
				
				if(m_DbUtil.doUpdate(Sql))
				{
					ret = Cmd_Sta.STA_SUCCESS;
				}
			}
			/**************************昨日销售数据**************************/
			else if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_22))
			{
				//数据解析
				CnoocData22Bean Data22Bean = new CnoocData22Bean();
				byte[] _Value4 = new byte[4];
				
				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
				Data22Bean.setValue_All(CommUtil.converseInt(DinStream.readInt()));
				Data22Bean.setAmt_All(CommUtil.converseInt(DinStream.readInt()));
				Data22Bean.setCnt_All(CommUtil.converseInt(DinStream.readInt()));
				DinStream.read(_Value4, 0, 4);
				Data22Bean.setOil_CType(new String(_Value4));
				DinStream.close();
				
				System.out.println("22Value_All:" + new BigDecimal(Data22Bean.getValue_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
				System.out.println("22Amt_All:" + new BigDecimal(Data22Bean.getAmt_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
				System.out.println("22Cnt_All:" + Data22Bean.getCnt_All());
				System.out.println("22Oil_CType:" + Data22Bean.getOil_CType());
				
				
				
			}
//			/**************************上周销售数据**************************/
//			else if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_23))
//			{
//				//数据解析
//				CnoocData23Bean Data23Bean = new CnoocData23Bean();
//				byte[] _Value4 = new byte[4];
//				
//				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
//				Data23Bean.setValue_All(CommUtil.converseInt(DinStream.readInt()));
//				Data23Bean.setAmt_All(CommUtil.converseInt(DinStream.readInt()));
//				Data23Bean.setCnt_All(CommUtil.converseInt(DinStream.readInt()));
//				DinStream.read(_Value4, 0, 4);
//				Data23Bean.setOil_CType(new String(_Value4));
//				DinStream.close();
//				
//				System.out.println("23Value_All:" + new BigDecimal(Data23Bean.getValue_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
//				System.out.println("23Amt_All:" + new BigDecimal(Data23Bean.getAmt_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
//				System.out.println("23Cnt_All:" + Data23Bean.getCnt_All());
//				System.out.println("23Oil_CType:" + Data23Bean.getOil_CType());
//				
//				
//			}
//			/**************************上月销售数据**************************/
//			else if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_24))
//			{
//				//数据解析
//				CnoocData24Bean Data24Bean = new CnoocData24Bean();
//				byte[] _Value4 = new byte[4];
//				
//				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
//				Data24Bean.setValue_All(CommUtil.converseInt(DinStream.readInt()));
//				Data24Bean.setAmt_All(CommUtil.converseInt(DinStream.readInt()));
//				Data24Bean.setCnt_All(CommUtil.converseInt(DinStream.readInt()));
//				DinStream.read(_Value4, 0, 4);
//				Data24Bean.setOil_CType(new String(_Value4));
//				DinStream.close();
//				
//				System.out.println("24Value_All:" + new BigDecimal(Data24Bean.getValue_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
//				System.out.println("24Amt_All:" + new BigDecimal(Data24Bean.getAmt_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
//				System.out.println("24Cnt_All:" + Data24Bean.getCnt_All());
//				System.out.println("24Oil_CType:" + Data24Bean.getOil_CType());
//				
//			}
//			/**************************去年销售数据**************************/
//			else if((Dev_Id.substring(0,6) + Dev_Attr_Id).equals(Cmd_Sta.DATA_1011_25))
//			{
//				//数据解析
//				CnoocData25Bean Data25Bean = new CnoocData25Bean();
//				byte[] _Value4 = new byte[4];
//				
//				DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Dev_CData));
//				Data25Bean.setValue_All(CommUtil.converseInt(DinStream.readInt()));
//				Data25Bean.setAmt_All(CommUtil.converseInt(DinStream.readInt()));
//				Data25Bean.setCnt_All(CommUtil.converseInt(DinStream.readInt()));
//				DinStream.read(_Value4, 0, 4);
//				Data25Bean.setOil_CType(new String(_Value4));
//				DinStream.close();
//				
//				System.out.println("25Value_All:" + new BigDecimal(Data25Bean.getValue_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
//				System.out.println("25Amt_All:" + new BigDecimal(Data25Bean.getAmt_All()).divide(new BigDecimal(100),2,java.math.RoundingMode.HALF_UP));
//				System.out.println("25Cnt_All:" + Data25Bean.getCnt_All());
//				System.out.println("25Oil_CType:" + Data25Bean.getOil_CType());
//			
//			}
			
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