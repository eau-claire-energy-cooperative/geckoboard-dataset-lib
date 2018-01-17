package com.ecec.rweber.geckoboard.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	public static void main(String[] args){
		List<DataRow> rows = new ArrayList<DataRow>();
		
		DataRow one = new DataRow();
		one.addData(DataType.NUMBER, "test", "55");
		one.addData(DataType.STRING, "name", "test");
		rows.add(one);
		
		ObjectMapper mapper = new ObjectMapper();
	    String result;
		try {
			result = mapper.writeValueAsString(rows);
		    System.out.println(result);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
