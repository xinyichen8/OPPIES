package javaplain;

public class DataMem 
{
	private String type;
	private String pri_pub;
	private String name;
	private boolean abs;
	private boolean isFinal;
	private boolean isStatic;
	
	public DataMem(String name)
	{
		this.name=name;
		type="";
		pri_pub="";
		abs=false;
		isFinal =false;
		isStatic=false;
		
	}
	
	public void settype(String t)
	{
		this.type=t;
	}
	
	public void setp(String p)
	{
		this.pri_pub=p;
	}
	
	public void setabs(boolean a)
	{
		this.abs=a;
	}
	
	public void setf(boolean f)
	{
		this.isFinal=f;
	}
	
	public void sets(boolean s)
	{
		this.isStatic=s;
	}
	
	//Getter functions
	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getp()
	{
		if(this.pri_pub.equals("")){
			return "null";
		}
		return this.pri_pub;
	}
	
	public boolean getabs()
	{
		return this.abs;
	}
	
	public boolean getf()
	{
		return this.isFinal;
	}
	
	public boolean gets()
	{
		return this.isStatic;
	}
}
