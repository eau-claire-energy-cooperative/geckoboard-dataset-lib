package com.ecec.rweber.geckoboard.model;
import java.util.HashMap;

public class DataRow extends HashMap<String,Object>{
	private static final long serialVersionUID = -9126028571183555258L;

	public void addData(DataType type, String column, String value){
		
		if(type == DataType.NUMBER || type == DataType.PERCENTAGE){
			this.put(column, Double.parseDouble(value));
		}
		else {
			this.put(column,value);
		}
	}
}
