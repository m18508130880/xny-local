package net.appsvr;

public class JiNanXnyData26Bean
{
	String CTime          = "";        //
	float Temperature     = 0.0f;      //流量计温度
	float Stress          = 0.0f ;     //流量计压力
	float Standard_Flow   = 0.0f ;     //标况流量
	float Cumulative_High = 0.0f ;     //累计高位
	float Cumulative_Low  = 0.0f ;     //累计低位
	
	public String getCTime()
	{
		return CTime;
	}
	public void setCTime(String cTime)
	{
		CTime = cTime;
	}
	public float getTemperature()
	{
		return Temperature;
	}
	public void setTemperature(float temperature)
	{
		Temperature = temperature;
	}
	public float getStress()
	{
		return Stress;
	}
	public void setStress(float stress)
	{
		Stress = stress;
	}
	public float getStandard_Flow()
	{
		return Standard_Flow;
	}
	public void setStandard_Flow(float standard_Flow)
	{
		Standard_Flow = standard_Flow;
	}
	public float getCumulative_High()
	{
		return Cumulative_High;
	}
	public void setCumulative_High(float cumulative_High)
	{
		Cumulative_High = cumulative_High;
	}
	public float getCumulative_Low()
	{
		return Cumulative_Low;
	}
	public void setCumulative_Low(float cumulative_Low)
	{
		Cumulative_Low = cumulative_Low;
	}
	
}