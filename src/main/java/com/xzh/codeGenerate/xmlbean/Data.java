package com.xzh.codeGenerate.xmlbean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="data")

public class Data {

	@XmlElement
	private String	tables;
	
	@XmlElement
	private String	path;
	
	@XmlElement
	private String	ingnorefields;
	
	@XmlElement
	private String	httpmethods;
	
	@XmlElement
	private String	controllername;
	
	@XmlElementWrapper(name="custommethods")
	@XmlElement(name="customMethod")
	private List<CustomMethod> customMethodlist;

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIngnorefields() {
		return ingnorefields;
	}

	public void setIngnorefields(String ingnorefields) {
		this.ingnorefields = ingnorefields;
	}

	public String getHttpmethods() {
		return httpmethods;
	}

	public void setHttpmethods(String httpmethods) {
		this.httpmethods = httpmethods;
	}

	public String getControllername() {
		return controllername;
	}

	public void setControllername(String controllername) {
		this.controllername = controllername;
	}

	public List<CustomMethod> getCustomMethodlist() {
		return customMethodlist;
	}

	public void setCustomMethodlist(List<CustomMethod> customMethodlist) {
		this.customMethodlist = customMethodlist;
	}

}
