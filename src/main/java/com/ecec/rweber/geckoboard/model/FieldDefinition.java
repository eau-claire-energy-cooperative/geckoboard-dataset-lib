package com.ecec.rweber.geckoboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonRootName("data")
public class FieldDefinition {
	
	@JsonProperty("type")
	private DataType m_type = null;
	
	@JsonIgnore
	private String m_columnName = null; //ignore since column name is wrapped
	
	@JsonProperty("name")
	private String m_name = null;
	
	@JsonProperty("optional")
	private boolean m_optional = false;
	
	public FieldDefinition(DataType type, String columnName){
		this(type,columnName,columnName.substring(0,1).toUpperCase() + columnName.substring(1).toLowerCase());
	}
	
	public FieldDefinition(DataType type, String columnName, String name){
		m_type = type;
		m_columnName = columnName;
		m_name = name;
	}
	
	@JsonIgnore
	public String getColumnName(){
		return this.m_columnName;
	}
}
