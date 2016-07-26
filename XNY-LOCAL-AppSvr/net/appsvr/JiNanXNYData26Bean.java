package net.appsvr;

public class JiNanXNYData26Bean
{
	String CTime         = "";  //
	String Temperature   = "";  //流量计温度
	String Stress          ;     //流量计压力
	String Standard_Flow   ;     //标况流量
	String Cumulative_High ;     //累计高位
	String Cumulative_Low  ;     //累计低位
	public String getCTime()
	{
		return CTime;
	}
	public void setCTime(String cTime)
	{
		CTime = cTime;
	}
	public String getTemperature()
	{
		return Temperature;
	}
	public void setTemperature(String temperature)
	{
		Temperature = temperature;
	}
	public String getStress()
	{
		return Stress;
	}
	public void setStress(String stress)
	{
		Stress = stress;
	}
	public String getStandard_Flow()
	{
		return Standard_Flow;
	}
	public void setStandard_Flow(String standard_Flow)
	{
		Standard_Flow = standard_Flow;
	}
	public String getCumulative_High()
	{
		return Cumulative_High;
	}
	public void setCumulative_High(String cumulative_High)
	{
		Cumulative_High = cumulative_High;
	}
	public String getCumulative_Low()
	{
		return Cumulative_Low;
	}
	public void setCumulative_Low(String cumulative_Low)
	{
		Cumulative_Low = cumulative_Low;
	}
	

}