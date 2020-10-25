package javaplain;

import java.util.ArrayList;

public class Method {
	private String type;
	private String pri_pub;
	private String name;
	private boolean abs;
	private boolean isFinal;
	private boolean isStatic;
	private ArrayList<DataMem> localVar;
	private ArrayList<Call> call;
	private ArrayList<Param> param;
	
	public Method(String type)
	{
		this.name="";
		this.type=type;
		this.pri_pub="";
		this.abs=false;
		this.localVar = new ArrayList<DataMem>();
		this.call= new ArrayList<Call>();
		this.param = new ArrayList<Param>();
		this.isFinal =false;
		this.isStatic=false;
	}
	
	public void addName(String name)
	{
		this.name=name;
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
	
	public void addVar(DataMem var)
	{
		this.localVar.add(var);
	}
	
	public void addCall(Call c)
	{
		this.call.add(c);
	}
	
	public void addParam(Param p)
	{
		this.param.add(p);
	}
	
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
	
	public ArrayList<DataMem> getVar()
	{
		return this.localVar;
	}
	
	public ArrayList<Call> getCall()
	{
		return this.call;
	}
	
	public ArrayList<Param> getParam()
	{
		return this.param;
	}
}
