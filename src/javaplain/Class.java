package javaplain;

import java.util.ArrayList;

public class Class 
{
	private String name;
	private ArrayList<Method> method;
	private ArrayList<DataMem> dm;
	private boolean isFinal;
	private boolean isStatic;
	private String extend;
	private ArrayList<String> implement;
	public Class(String name)
	{
		this.name=name;
		this.extend="";
		this.implement=new ArrayList<String>();
		this.isFinal=false;
		this.isStatic=false;
		this.method=new ArrayList<Method>();
		this.dm=new ArrayList<DataMem>();
	}
	
	public void addMethod(Method m)
	{
		method.add(m);
	}
	
	public void addDM(DataMem d)
	{
		this.dm.add(d);
	}
	
	public void setfinal(boolean f)
	{
		isFinal=f;
	}
	
	public void setStatic(boolean s)
	{
		isStatic=s;
	}
	
	public void setExtend(String e)
	{
		this.extend=e;
	}
	
	public void addImplement(String i)
	{
		this.implement.add(i);
	}
	
	public ArrayList<Method> getMethod()
	{
		return this.method;
	}
	
	public ArrayList<DataMem> getDM()
	{
		return this.dm;
	}
	
	public boolean isf()
	{
		return this.isFinal;
	}
	
	public boolean iss()
	{
		return this.isStatic;
	}
	
	public String parent()
	{
		return this.extend;
	}
	
	public String getName()
	{
		
		if(implement.size()!=0)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i<implement.size(); i++)
			{
				if(i<implement.size()-1)
				{
					sb.append(implement.get(i)+", ");
				}
				else
				{
					sb.append(implement.get(i));
				}
			}
			if(extend.equals(""))
			{
				return this.name+" implements "+sb.toString();
			}
			else
			{
				return this.name+" extend "+this.extend + " implements "+sb.toString();
			}
		}
		else if(implement.size()==0)
		{
			if(!extend.equals(""))
				return this.name+" extend "+this.extend;
		}
		return this.name;
	}
}
