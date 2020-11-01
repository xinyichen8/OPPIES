package javaplain;

import java.util.ArrayList;

public class Class
{
	private String name;
	private String assV;
	private ArrayList<Method> method;
	private ArrayList<DataMem> dm;
	private ArrayList<String> agg;
	private ArrayList<String> ass;

	private boolean isFinal;
	private boolean isStatic;
	private String extend;
	private boolean protect;
	private boolean fin;
	private boolean strifp;
	private boolean isAbs;
	private ArrayList<String> child;
	private ArrayList<String> implement;


	public Class()
	{
		this.name="";
		this.extend="";
		this.child = new ArrayList<String>();
		this.implement=new ArrayList<String>();
		this.isFinal=false;
		this.isStatic=false;
		this.method=new ArrayList<Method>();
		this.dm=new ArrayList<DataMem>();
		this.agg = new ArrayList<String>();
		this.ass = new ArrayList<>();
		this.assV = "";
	}
	public void addAssV(String name) {
		this.assV=name;
	}
	public String getAssV(){
		return this.assV;
	}

	public void addAss(String a){ass.add(a);};
	public ArrayList<String> getAss(){
		return this.ass;
	}
	public void addAgg(String a){agg.add(a);};
	public ArrayList<String> getAgg(){
		return this.agg;
	}
	
	public void addName(String name)
	{
		this.name=name;
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
	
	public String getParent()
	{
		return this.extend;
	}

	public void setChild(String c) {this.child.add(c);}
	public ArrayList<String> child() { return this.child;}
	
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
				return this.name+" extends "+this.extend + " implements "+sb.toString();
			}
		}
		else if(implement.size()==0)
		{
			if(!extend.equals(""))
				return this.name+" extends "+this.extend;
		}
		return this.name;
	}


	public boolean isProtect() {
		return protect;
	}

	public void setProtect(boolean protect) {
		this.protect = protect;
	}

	public boolean isFin() {
		return fin;
	}

	public void setFin(boolean fin) {
		this.fin = fin;
	}

	public boolean isStrifp() {
		return strifp;
	}

	public void setStrifp(boolean strifp) {
		this.strifp = strifp;
	}

	public boolean isAbs() {
		return isAbs;
	}

	public void setAbs(boolean isAbs) {
		this.isAbs = isAbs;
	}



}
