package com.ecec.rweber.geckoboard.model;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DataRow {
	@JsonIgnore
	private Map<String,Object> m_data = null;
	
	public DataRow(){
		m_data = new HashMap<String,Object>();
	}
	
	public void addData(String column, double value){
		//we know it's a doube already, just add it
		m_data.put(column, value);
	}
	
	public void addData(String column, int value){
		//we know it's an int already, just add it
		m_data.put(column, value);
	}
	
	public void addData(DataType type, String column, String value){
		
		if(type == DataType.NUMBER || type == DataType.PERCENTAGE){
			
			//check if decimal
			if(value.contains("."))
			{
				m_data.put(column, Double.parseDouble(value));
			}
			else
			{
				m_data.put(column, Integer.parseInt(value));
			}
		}
		else {
			m_data.put(column,value);
		}
	}
	
	@JsonAnyGetter
	public Map<String,Object> getData(){
		return m_data;
	}
}
