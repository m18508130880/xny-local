package net.appsvr;

public class CnoocData20Bean
{
	String CTime     = "";
	String Order_Id  = "";
	int Order_Value  = 0;
	int    Value     = 0;
	String Car_Id    = "";
	String Car_Owner = "";
	String Worker    = "";
	String Oil_CType = "";
	
	public String getCTime() {
		return CTime;
	}
	public void setCTime(String cTime) {
		CTime = cTime;
	}
	public String getOrder_Id() {
		return Order_Id;
	}
	public void setOrder_Id(String orderId) {
		Order_Id = orderId;
	}
	public int getOrder_Value() {
		return Order_Value;
	}
	public void setOrder_Value(int orderValue) {
		Order_Value = orderValue;
	}
	public int getValue() {
		return Value;
	}
	public void setValue(int value) {
		Value = value;
	}
	public String getCar_Id() {
		return Car_Id;
	}
	public void setCar_Id(String carId) {
		Car_Id = carId;
	}
	public String getCar_Owner() {
		return Car_Owner;
	}
	public void setCar_Owner(String carOwner) {
		Car_Owner = carOwner;
	}
	public String getWorker() {
		return Worker;
	}
	public void setWorker(String worker) {
		Worker = worker;
	}
	public String getOil_CType() {
		return Oil_CType;
	}
	public void setOil_CType(String oilCType) {
		Oil_CType = oilCType;
	}
}