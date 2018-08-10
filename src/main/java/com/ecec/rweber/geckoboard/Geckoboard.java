package com.ecec.rweber.geckoboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import com.ecec.rweber.geckoboard.model.DataRow;
import com.ecec.rweber.geckoboard.model.FieldDefinition;

public class Geckoboard {
	public final String m_url = "https://api.geckoboard.com/";
	
	private Client m_http = null;
	
	public Geckoboard(String apiKey){

		//create the client config for authentication
		ClientConfig clientConfig = new ClientConfig();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(apiKey, "");
	    clientConfig.register(feature) ;
		
		m_http = ClientBuilder.newClient(clientConfig);
	}
	
	private WebTarget createRequest(String ... path){
		WebTarget result = m_http.target(this.m_url);
		
		for(int i = 0; i < path.length; i ++)
		{
			result = result.path(path[i]);
		}
		
		return result;
	}
	
	public boolean test(){
		boolean result = false;
		
		try{
			WebTarget target = m_http.target(this.m_url);
			Response response = target.request().get();
			
			result = response.getStatus() == 200;
		}
		catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean create(String dataset, FieldDefinition ... fields ){
		//convenience for create
		return this.create(dataset, null, fields);
	}
	
	public boolean create(String dataset, String unique, FieldDefinition ... fields ){
		boolean result = false;
		
		//get the dataset in the right format
		Map<String,Object> jsonData = new HashMap<String,Object>();
		Map<String,FieldDefinition> fieldList = new HashMap<String,FieldDefinition>();
		
		for(int count = 0; count < fields.length; count ++){
			fieldList.put(fields[count].getColumnName(),fields[count]);
		}
		
		jsonData.put("fields", fieldList);
		
		//if we have a unique entry
		if(unique != null)
		{
			jsonData.put("unique_by",Collections.singletonList(unique));
		}
		
		try{
			//send the http request
			WebTarget target = this.createRequest("datasets",dataset);
			Response response = target.request().put(Entity.entity(jsonData,MediaType.APPLICATION_JSON_TYPE));
			
			result =  response.getStatus() == 200;
		}
		catch(Exception e)
		{
			//don't pring anything here, just return false
		}
		
		return result;
		
	}
	
	public boolean delete(String dataset){
		boolean result = false;
		
		try{
			Response response = this.createRequest("datasets",dataset).request().delete(); 
		
			result =  response.getStatus() == 200;
		}
		catch(Exception e)
		{
			//don't pring anything here, just return false
		}
		
		return result;
	}
	
	public boolean set(String dataset, List<DataRow> data){
		//convenience for replace
		return this.replace(dataset, data);
	}
	
	public boolean replace(String dataset, List<DataRow> data){
		boolean result = false;
		
		//wrap the list
		Map<String,Object> jsonData = new HashMap<String,Object>();
		jsonData.put("data", data);
		
		try{
			WebTarget target = this.createRequest("datasets",dataset,"data");
			Response response = target.request().put(Entity.entity(jsonData,MediaType.APPLICATION_JSON_TYPE));
		
			result = response.getStatus() == 200;
		}
		catch(Exception e)
		{
			//don't pring anything here, just return false
		}
		
		return result;
	}
	
	public boolean append(String dataset, DataRow row){
		//convenience for when there is only one row to add
		List<DataRow> data = new ArrayList<DataRow>();
		data.add(row);
		
		return this.append(dataset,data);
	}
	
	public boolean append(String dataset, List<DataRow> data){
		boolean result = false;
		
		//wrap the list
		Map<String,Object> jsonData = new HashMap<String,Object>();
		jsonData.put("data", data);
		
		try{
			WebTarget target = this.createRequest("datasets",dataset,"data");
			Response response = target.request().post(Entity.entity(jsonData,MediaType.APPLICATION_JSON_TYPE));
		
			result =  response.getStatus() == 200;
		}
		catch(Exception e)
		{
			//don't pring anything here, just return false
		}
		
		return result;
	}
}
