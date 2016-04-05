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
import Encryptie.AESencrp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
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
    String level;
    String cartrackerids;
    String time;
    String splitter = ":";
    List<Integer> carids = new ArrayList<Integer>();

    @GET
    @Produces("application/json")
    public Response convertFtoC() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        Double fahrenheit = 98.24;
        Double celsius;
        celsius = (fahrenheit - 32) * 5 / 9;
        List<CarTrackerDAO> cartrackers = cartrackerhandler.getCarTrackers();

        for (CarTrackerDAO c : cartrackers) {
            jsonObject.put("cartracker" + c.getId(), c.getId());
        }

        String result = jsonObject.toString();
        return Response.status(200).entity(result).build();
    }

    @Path("{f}")
    @GET
    @Consumes("text/plain")
    @Produces("application/json")
    public Response convertFtoCfromInput(@PathParam("f") String f) throws JSONException, Exception {
        JSONObject jsonObject;
        AESencrp encrp = new AESencrp();
        String gegevens = f.replace("()()", "/");
        gegevens = AESencrp.decrypt(gegevens);
        getVariables(gegevens);
        jsonArray = new JSONArray();
        List<List<CarTrackerDAO>> bigCarList = cartrackerhandler.getCarTrackerByList(carids);
        for (List<CarTrackerDAO> carList : bigCarList) {
            JSONArray array = new JSONArray();
            for (CarTrackerDAO c : carList) {
                jsonObject = new JSONObject();
                jsonObject.put("id", c.getId());
                jsonObject.put("carId", c.getAutoid());
                jsonObject.put("lat", c.getLatitude());
                jsonObject.put("long", c.getLongitude());
                array.put(jsonObject);
            }
            jsonArray.put(array);
        }
        return Response.status(200).entity(jsonArray.toString()).build();
    }

    public void getVariables(String input) {
        int begin = 0;
        int end = 0;

        end = input.indexOf(splitter);
        time = input.substring(0, end);
        begin = end + 1;
        end = input.indexOf(splitter, begin + 1);
        cartrackerids = input.substring(begin, end);
        listCartrackers(cartrackerids);
        begin = end + 1;
        level = input.substring(begin);
    }

    public void listCartrackers(String cartrackerids) {
        String tussen = "#";
        int counter = 1;
        int begin = 0;
        int end = 0;
        for (int i = 0; i < counter; i++) {
            end = cartrackerids.indexOf(tussen, begin);
            if (end != -1) {
                carids.add(Integer.valueOf(cartrackerids.substring(begin, end)));
                counter++;
            } else {
                carids.add(Integer.valueOf(cartrackerids.substring(begin)));
            }
            begin = end + 1;
        }
    }
}
