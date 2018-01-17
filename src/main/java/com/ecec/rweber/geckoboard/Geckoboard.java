package com.ecec.rweber.geckoboard;

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
		
		WebTarget target = m_http.target(this.m_url);
		Response response = target.request().get();
		
		return response.getStatus() == 200;
	}
	
	public boolean create(String dataset, FieldDefinition ... fields ){
		//get the dataset in the right format
		Map<String,Object> jsonData = new HashMap<String,Object>();
		Map<String,FieldDefinition> fieldList = new HashMap<String,FieldDefinition>();
		
		for(int count = 0; count < fields.length; count ++){
			fieldList.put(fields[count].getColumnName(),fields[count]);
		}
		
		jsonData.put("fields", fieldList);
		
		//send the http request
		WebTarget target = this.createRequest("datasets",dataset);
		Response response = target.request().put(Entity.entity(jsonData,MediaType.APPLICATION_JSON_TYPE));
		
		return response.getStatus() == 200;
		
	}
	
	public boolean delete(String dataset){
		Response response = this.createRequest("datasets",dataset).request().delete(); 
		
		return response.getStatus() == 200;
	}
	
	public boolean set(String dataset, List<DataRow> data){
		//convenience for replace
		return this.replace(dataset, data);
	}
	
	public boolean replace(String dataset, List<DataRow> data){
		//wrap the list
		Map<String,Object> jsonData = new HashMap<String,Object>();
		jsonData.put("data", data);
		
		WebTarget target = this.createRequest("datasets",dataset,"data");
		Response response = target.request().put(Entity.entity(jsonData,MediaType.APPLICATION_JSON_TYPE));
		
		return response.getStatus() == 200;
	}
	
	public boolean append(String dataset, List<DataRow> data){
		//wrap the list
		Map<String,Object> jsonData = new HashMap<String,Object>();
		jsonData.put("data", data);
		
		WebTarget target = this.createRequest("datasets",dataset,"data");
		Response response = target.request().post(Entity.entity(jsonData,MediaType.APPLICATION_JSON_TYPE));
		
		return response.getStatus() == 200;
	}
}
