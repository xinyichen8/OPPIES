package javaplain;

public class Param 
{
	private String type;
	private String name;
	public Param(String name, String type)
	{
		this.type=type;
		this.name=name;
	}
	
	public String getParam()
	{
		return type+" "+name;
	}
}
