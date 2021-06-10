package knew.newnew;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.json.JSONObject;

//import com.google.gson.Gson;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class CrunchifyRESTService {
	
	@POST
	@Path("/Service")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) throws ClassNotFoundException {
		StringBuilder crunchifyBuilder = new StringBuilder();
		String data;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
		data = crunchifyBuilder.toString();
		
		JSONObject jo = new JSONObject(data);
		String name = jo.getString("name");
		String sport = jo.getString("sport");
		int age = jo.getInt("age");
		int id = jo.getInt("id");
		
        System.out.println(name);
        System.out.println(sport);
        System.out.println(age);
        System.out.println(id);
        
        AccessDB myObj = new AccessDB();
        //myObj.getPerson(); 
        
        myObj.addPerson(name, sport, age, id);
        
        //sometimes TOMCAT wont be able to use the connector driver and db operations wont work so 
        //Copy the JDBC-driver JAR file (mine was ojdbc6) into $JAVA_HOME/jre/lib/ext (for me it was C:\Program Files\Java\jdk-11.0.10\lib but it can also be C:\Program Files\Java\jdk1.7.0_80\jre\lib\ext).
        //just
        
        String newName = name + " new name";
        String newsport = sport + " new sport";
        int newAge = age + 1;
        int newId = id + 1;
        
        JSONObject ju = new JSONObject();
        ju.put("newName", newName);
        ju.put("newsport", newsport);
        ju.put("newAge", newAge);
        ju.put("newId", newId);
        
        String newResponse = ju.toString();
        
        return Response.status(200).entity(newResponse).build();
        
		// return HTTP response 200 in case of success
		//return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}
 
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "CrunchifyRESTService Successfully started..";
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
		
		@GET
		@Path("/ver")
		//@Produces(MediaType.TEXT_PLAIN)
		public Response verResponse(@QueryParam("id") int id) throws ClassNotFoundException {
			String result = "Hi " + id;// testing to see if the value of id was identified uniquely
			System.out.println(id); // testing to see if the value of id was identified uniquely
			
			AccessDB myObj1 = new AccessDB();
			result = myObj1.getPersonB(id);
			int newLastId = myObj1.getLastId();
			System.out.println(newLastId);
			// return HTTP response 200 in case of success
			return Response.status(200).entity(result).build();
	}

}
