package com.ecec.rweber.geckoboard.model;
import java.util.HashMap;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public void addData(DataType type, String column, DateTime value){
		//helper method for adding date/time values
		String result = null;
		
		if(type == DataType.DATE){
			//use the date format
			DateTimeFormatter geckoFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
			result = geckoFormat.print(value);
		}
		else if(type == DataType.DATETIME)
		{
			//use the datetime format
			DateTimeFormatter geckoFormatter = ISODateTimeFormat.dateTime();
			result = geckoFormatter.print(value);
		}
		else
		{
			//just send the string
			result = value.toString();
		}
		
		this.addData(type, column,result);
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
	
	@Override
	public String toString(){
		String result = "";
		
		try {
			result = new ObjectMapper().writeValueAsString(m_data);
		} catch (JsonProcessingException e) {

		}
		
		return result;
	}
	
	@JsonAnyGetter
	public Map<String,Object> getData(){
		return m_data;
	}
}
