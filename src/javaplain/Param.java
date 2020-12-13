package javaplain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Param
{
	private String type;
	private String name;

	public Param() {
	}

	public Param(String type) {
		this.type = type;
	}

	@JsonProperty("param")
	public void setType(String type) {
		this.type = type;
	}


	public Param(String name, String type)
	{
		this.type=type;
		this.name=name;
	}
	
	public String getParam()
	{
		return this.type+" "+this.name;
	}
}
