package net.appsvr;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CnoocDataZXBean
{
	String CardNo;
	String AccName;
	String CarNumber;
	String HolderName;
	String CarType;
	String CarBottle;
	
	public String getSql(int Cmd)
	{
		String Sql = "";
		switch(Cmd)
		{
			case 0://≤È—Ø
				Sql = " select t.CardNo, t.AccName, t.CarNumber, t.HolderName, t.CarType, t.CarBottle " +
					  " from InterfaceCardInfo t " + 
					  " where t.CardNo = '"+ CardNo +"' ";
				break;
		}	      
		return Sql;		
	}
	
	public boolean getData(ResultSet pRs)  
	{
		boolean IsOK = true;
		try
		{	
			setCardNo(pRs.getString(1));
			setAccName(pRs.getString(2));
			setCarNumber(pRs.getString(3));
			setHolderName(pRs.getString(4));
			setCarType(pRs.getString(5));
			setCarBottle(pRs.getString(6));
		} 
		catch (SQLException sqlExp) 
		{
			sqlExp.printStackTrace();
		}		
		return IsOK;
	}

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo;
		if(null == CardNo)
			CardNo = "";
	}

	public String getAccName() {
		return AccName;
	}

	public void setAccName(String accName) {
		AccName = accName;
		if(null == AccName)
			AccName = "";
	}

	public String getCarNumber() {
		return CarNumber;
	}

	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
		if(null == CarNumber)
			CarNumber = "";
	}

	public String getHolderName() {
		return HolderName;
	}

	public void setHolderName(String holderName) {
		HolderName = holderName;
		if(null == HolderName)
			HolderName = "";
	}

	public String getCarType() {
		return CarType;
	}

	public void setCarType(String carType) {
		CarType = carType;
		if(null == CarType)
			CarType = "";
	}

	public String getCarBottle() {
		return CarBottle;
	}

	public void setCarBottle(String carBottle) {
		CarBottle = carBottle;
		if(null == CarBottle)
			CarBottle = "";
	}
}