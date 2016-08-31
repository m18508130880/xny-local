package net.appsvr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import net.Md5;
import net.MsgHeadBean;
import net.TcpClient;
import net.TcpSvrBase;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import container.ActionContainer;
import bean.BaseCmdBean;
import util.*;

public class TcpSvrAppGateWay extends TcpSvrBase
{
	private int m_iPort    = 0;
	private int m_iTimeOut = 0;
	private int m_iStatus  = 0;
	DBUtil m_DbUtil = null;
	TcpClient m_TcpClient = null;
	
	//��½�ͻ����б�
	private static Hashtable<String, ClientSocket> objClientTable = null;
	private static Byte markClientTable = new Byte((byte)1);      //ͬ����
	
	//��ȡ�����ļ�����
	public TcpSvrAppGateWay(DBUtil dbUtil)throws Exception
	{
		SAXReader reader  = new SAXReader();
		Document document = reader.read(new FileInputStream("Config.xml"));
		Element root = document.getRootElement();
		m_iPort      = Integer.parseInt(root.element("app_server").element("server_prot").getText());
		m_iTimeOut   = Integer.parseInt(root.element("app_server").element("server_timeout").getText());
		m_iStatus    = Integer.parseInt(root.element("app_client").element("client_sta").getText());
		m_DbUtil     = dbUtil;
		
		if(1 == m_iStatus)
		{
			m_TcpClient = new TcpClient(m_DbUtil);
			m_TcpClient.init();
		}
	}
	
	//��ʼ��
	public boolean Initialize()
	{
		if(!init(m_iPort, m_iTimeOut))
			return false;
		objClientTable = new Hashtable<String, ClientSocket>();
		MsgCtrl msgCtrl = new MsgCtrl();
		msgCtrl.start();
		return true;
	}
	
	public String CheckClient(byte[] Buffer, Socket objClient)
	{
		String ret = null;
		try
		{
			DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(Buffer));
			DinStream.readInt();
			int Cmd = CommUtil.converseInt(DinStream.readInt());
			if(Cmd_Sta.COMM_LOGON != Cmd)
			{
				return null;
			}
			
			//������֤
			String Status = new String(Buffer, 20, 4);
			String PId = new String(Buffer, 24, 20);
			String TimeStamp = new String(Buffer, 44, 14);
			String strMd5 = new String(Buffer, 58, 32);
//			String checkResult = checkClient(Status, PId, TimeStamp, strMd5);
//			if(!checkResult.substring(0, 4).equalsIgnoreCase("0000"))
			{
				//return null;
			}
			ret = PId;
//			
//			//��֤�Ƿ��Ѵ���
			if(objClientTable.containsKey(PId))
			{
				CommUtil.PRINT("Id Already Exist!" + PId);
				ClientClose(PId);
			}
			
			//�½�ͨ��
			ClientSocket objChannel= new ClientSocket();	
			if(!objChannel.init(objClient, PId))
			{
				CommUtil.LOG("ClientId [" + PId + "] ClientSocket init failed!");
			}
			synchronized(markClientTable)
			{
				objClientTable.put(PId , objChannel);
			}
			
			//����ͨ��IP
			CommUtil.LOG("CPM_IP:" + objClient.getInetAddress().toString());
			String pSql = "update device_detail t set t.link_url = '"+ objClient.getInetAddress().toString().substring(1) +"' where t.id = '"+ PId.trim() +"'";
			//m_DbUtil.doUpdate(pSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return ret;
	}
	
	public String checkClient(String strStatus, String strId, String strTimestamp, String strOriginalMd5)
	{
		String ret = "3006";
		String password = m_DbUtil.APC(CommUtil.StrRightFillSpace(strId, 40)+ strStatus + "0001");
		String strData = strId + strTimestamp + password;
		String Temp = CommUtil.BytesToHexString(new Md5().encrypt(strData.getBytes()), 16);
		CommUtil.LOG("Client[" + strId + "] TimeStamp[" + strTimestamp + "] OldMd5[" + strOriginalMd5 + "] NewMd5[" + Temp + "] DbMsg[" + password + "]");
		
		if(Temp.equalsIgnoreCase(strOriginalMd5))
		{
			ret = "0000";
		}
		return ret;
	}

	public byte[] GetActiveTestBuf()
	{
		byte[] byteData = null;
		try
		{
			ByteArrayOutputStream boutStream = new ByteArrayOutputStream();
			DataOutputStream doutStream = new DataOutputStream(boutStream);
			doutStream.writeInt(CommUtil.converseInt(CmdUtil.MSGHDRLEN));
			doutStream.writeInt(CommUtil.converseInt(CmdUtil.COMM_ACTIVE_TEST));
			doutStream.writeInt(0);
			doutStream.writeInt(CommUtil.converseInt(GetSeq()));
			doutStream.writeInt(0);
			byteData = boutStream.toByteArray();
			doutStream.close();
			boutStream.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			byteData = null;
		}
		return byteData;
	}
	
	public void ClientStatusNotify(String strClientKey, int iStatus)
	{
		switch(iStatus)
		{
			case STATUS_CLIENT_ONLINE:
			{
				//CPM���ػָ�����
				String OffStr = "";
				OffStr = CommUtil.StrBRightFillSpace("", 20)
					   + "0000"
					   + "1004"
					   + CommUtil.StrBRightFillSpace("", 10)
					   + CommUtil.StrBRightFillSpace("", 30)
					   + CommUtil.StrBRightFillSpace("", 4)
					   + CommUtil.StrBRightFillSpace("", 20)
					   + "7"
					   + CommUtil.StrBRightFillSpace((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), 20)
					   + CommUtil.StrBRightFillSpace("���ػָ�����", 128);
				SetRecvMsgList((strClientKey + new String(EnCode(Cmd_Sta.COMM_SUBMMIT, OffStr))).getBytes());
				break;
			}
			case STATUS_CLIENT_OFFLINE:
			{
				//CPM��������
				String OffStr = "";
				OffStr = CommUtil.StrBRightFillSpace("", 20)
					   + "0000"
					   + "1004"
					   + CommUtil.StrBRightFillSpace("", 10)
					   + CommUtil.StrBRightFillSpace("", 30)
					   + CommUtil.StrBRightFillSpace("", 4)
					   + CommUtil.StrBRightFillSpace("", 20)
					   + "6"
					   + CommUtil.StrBRightFillSpace((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()), 20)
					   + CommUtil.StrBRightFillSpace("��������", 128);
				SetRecvMsgList((strClientKey + new String(EnCode(Cmd_Sta.COMM_SUBMMIT, OffStr))).getBytes());
				break;
			}
		}
	}
	
	public byte[] EnCode(int msgCode, String pData)
	{
		byte[] byteData = null;
		try
		{
			ByteArrayOutputStream boutStream = new ByteArrayOutputStream();
			DataOutputStream doutStream = new DataOutputStream(boutStream);
			{
				doutStream.writeInt(CommUtil.converseInt(CmdUtil.MSGHDRLEN + pData.getBytes().length));//����
				doutStream.writeInt(CommUtil.converseInt(msgCode));
				doutStream.writeInt(CommUtil.converseInt(0));
				doutStream.writeInt(CommUtil.converseInt(GetSeq()));
				doutStream.writeInt(CommUtil.converseInt(0));
				doutStream.write(pData.getBytes());
			}
			byteData = boutStream.toByteArray();
			boutStream.close();
			doutStream.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return byteData;
	}
	
	public static boolean DisPatch(int msgCode, String clientKey, String pData)
	{
		boolean ret = false;
		try
		{
			synchronized(markClientTable)
			{
				if(!objClientTable.isEmpty() && objClientTable.containsKey(clientKey))
				{
					CommUtil.LOG("Succee DisPatch Client[" + clientKey + "] Data[" + pData + "]");
					ClientSocket objChannel = (ClientSocket) objClientTable.get(clientKey);	
					objChannel.SendMsg(msgCode, pData);
					ret = true;
				}
				else
				{
					CommUtil.LOG("Failed DisPatch Client[" + clientKey + "] Data[" + pData + "]");
				}
			}
		}
		catch(Exception e)
		{
		}
		return ret;
	}
	
	public synchronized void ClientClose(String pClientKey)
	{
		try
		{			
			synchronized(markClientTable)
			{
				if(!objClientTable.isEmpty() && objClientTable.containsKey(pClientKey))
				{
					ClientSocket objChannel = (ClientSocket) objClientTable.get(pClientKey);
					if(null != objChannel.objSocket && !objChannel.objSocket.isClosed())
					{
						//�ص�SOCKET����
						objChannel.objSocket.close();
						objChannel.objSocket = null;
						ClientStatusNotify(pClientKey, STATUS_CLIENT_OFFLINE);
					}
					//�ڹ�ϣ�����Ƴ��ͻ���
					objClientTable.remove(pClientKey);			
				}
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	private class MsgCtrl extends Thread
	{
		public void run()
		{
			String dealData = "";
			while (true)
			{
				try
				{
					byte[] data = (byte[])GetRecvMsgList();                    //ȡ�ý����߳������б�
					if(null ==  data || data.length < Cmd_Sta.CONST_MSGHDRLEN) //Cmd_Sta.CONST_MSGHDRLEN: ��ͷ����
					{
						sleep(10); //ms
						continue;
					}
					String strClientKey = new String(data, 0, 20);   // �ͻ���key Cpm_Id [0100000001          ]
					DataInputStream DinStream = new DataInputStream(new ByteArrayInputStream(data));
					DinStream.skip(20);   
					MsgHeadBean msgHead = new MsgHeadBean();         //��ͷ
					msgHead.setUnMsgLen(CommUtil.converseInt(DinStream.readInt())); //ͨ�Ű�����
					msgHead.setUnMsgCode(CommUtil.converseInt(DinStream.readInt()));//ҵ������
					msgHead.setUnStatus(CommUtil.converseInt(DinStream.readInt())); //״̬
					msgHead.setUnMsgSeq(CommUtil.converseInt(DinStream.readInt())); //���к�
					msgHead.setUnReserve(CommUtil.converseInt(DinStream.readInt()));//�����ֶ�
					DinStream.close();
					// Data    (290�ֽ�) = [0100000001          ][��ͷ                             ] + dealData 
					// dealData(250�ֽ�) = [                  95000010010431080001����������                    0026��������            2016-07-26 15:01:03 41EE64D2437B45B6409800000000400044220205420CEAC7                                                                                          ]
					dealData = new String(data, 40, data.length - 40);
					
					String dealReserve = dealData.substring(0, 20);        //������
					String dealCmd = dealData.substring(24, 28);           //����ָ��(1001)
					switch(msgHead.getUnMsgCode())
					{
						case Cmd_Sta.COMM_SUBMMIT: //�ͻ����ύ
						{
							CommUtil.LOG("PlatForm Submit [" + strClientKey + "] " + "[" + dealData + "]");
							BaseCmdBean cmdBean = BaseCmdBean.getBean(Integer.parseInt(dealCmd), m_DbUtil);	
							if(null != cmdBean)
							{                    //  Cpm_Id       ���ݰ�        
								cmdBean.parseReqest(strClientKey, dealData, data);
								cmdBean.execRequest();
								
								if(1 == m_iStatus)  //�ͻ����ϴ�?
								{
									//�ϴ�
									m_TcpClient.SetSendMsg(strClientKey + dealData, 1);
								}
							}
							break;
						}
						case Cmd_Sta.COMM_DELIVER://��Ӧ
						{
							CommUtil.LOG("PlatForm Deliver [" + strClientKey + "] " + "[" + dealData + "]");
							BaseCmdBean cmdBean = ActionContainer.GetAction(dealReserve);
							if(null != cmdBean)
							{
								cmdBean.parseReponse(dealData);
								cmdBean.execResponse();
							}
							break;
						}
					}
				}
				catch(Exception ex)
				{
					CommUtil.LOG(ex.toString());
					ex.printStackTrace();
					CommUtil.PRINT("TcpSvrAppGateWay Exception [" + dealData+"]");
					continue;
				}
			}//while
		}
	}
}//TcpSvrCls
