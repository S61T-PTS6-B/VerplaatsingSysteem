/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

/**
 *
 * @author Casvan
 */
import Controller.CarTrackerHandler;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proftaak.Model.CarTrackerDAO;
 
@Stateless
@Path("/ftocservice")
public class FtoCService {
 
    @EJB
    private CarTrackerHandler cartrackerhandler;
    JSONArray jsonArray;
	  @GET
	  @Produces("application/json")
	  public Response convertFtoC() throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32)*5/9; 
                List<CarTrackerDAO> cartrackers = cartrackerhandler.getCarTrackers();
                
                for(CarTrackerDAO c : cartrackers){
                    jsonObject.put("cartracker" + c.getId(), c.getId());
                }
		
 
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	  }
 
	  @Path("{f}")
	  @GET
	  @Produces("application/json")
	  public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
               JSONObject jsonObject;
              if (f != 0) {
                  jsonArray = new JSONArray();
                  
                  List<CarTrackerDAO> carList = cartrackerhandler.getCarTrackerById((int) f);
                  for(CarTrackerDAO c : carList)
                  {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", c.getId());
                    jsonObject.put("carId", c.getAutoid());
                    jsonObject.put("lat", c.getLatitude());
                    jsonObject.put("long", c.getLongitude());
                    jsonArray.put(jsonObject);
                  }
                 
                
                  
                  

              } else {
                  List<CarTrackerDAO> cartrackers = cartrackerhandler.getCarTrackers();
                  jsonArray = new JSONArray();
                  for (CarTrackerDAO c : cartrackers) {
                      jsonObject = new JSONObject();
                      jsonObject.put("carId", c.getId());
                      jsonObject.put("lat", c.getLatitude());
                      jsonObject.put("long", c.getLongitude());
                      jsonArray.put(jsonObject);
                  }
              }
		
		 
		
 
		String result = jsonArray.toString();
		return Response.status(200).entity(result).build();
	  }
          
          
}