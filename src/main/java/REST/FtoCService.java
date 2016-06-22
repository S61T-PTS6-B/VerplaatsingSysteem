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
import Controller.VerplaatsingSysteem;
import Encryptie.AESencrp;
import Politie.PolitieConnector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proftaak.Model.CarTrackerDAO;
import proftaak.Model.Data;
import proftaak.Model.DataId;

@Stateless
@Path("/carTrackers")
public class FtoCService {

    @EJB
    private CarTrackerHandler cartrackerhandler;
    JSONArray jsonArray;
    String level;
    String cartrackerids;
    String time;
    String splitter = ":";
    List<Integer> carids = new ArrayList<Integer>();

    @Path("getCT")
    @GET
    @Consumes("text/plain")
    @Produces("application/json")
    public Response getTrackers(@QueryParam("code") String f) throws JSONException, Exception {
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
                jsonObject.put("licenseplate", c.getLicensePlate());
                jsonObject.put("lat", c.getLatitude());
                jsonObject.put("long", c.getLongitude());
                array.put(jsonObject);
            }
            jsonArray.put(array);
        }
        return Response.status(200).entity(jsonArray.toString()).build();
    }

    //http://localhost:8080/VerplaatsingSysteem/Rest/carTrackers/getMonth?code=fleI0VKkYt2njqQHHqUfvxn1xy4IGw3sjxEnV/jR3Hs=
    @Path("getMonth")
    @GET
    @Consumes("text/plain")
    @Produces("application/json")
    public Response getbyMonth(@QueryParam("code") String f) throws JSONException, Exception {
        JSONObject jsonObject;
        Boolean eerstekeer = true;
        JSONArray array = new JSONArray();
        AESencrp encrp = new AESencrp();
        f = f.replace(" ", "+");
        String encrpstring = AESencrp.decrypt(f);
        DataId data = (DataId) getData(encrpstring);
        JSONObject Container = new JSONObject();
        for (CarTrackerDAO c : cartrackerhandler.getCarTrackers(data.getId(), Integer.valueOf(data.getMonth()), Integer.valueOf(data.getYear()))) {
            jsonObject = new JSONObject();
            if (eerstekeer) {
                Container.put("licenseplate", c.getLicensePlate());
                eerstekeer = false;
            }
            jsonObject.put("lat", c.getLatitude());
            jsonObject.put("long", c.getLongitude());
            Calendar ca = Calendar.getInstance();
            ca.setTimeInMillis(c.getDate());
            jsonObject.put("date", ca.getTime());
            array.put(jsonObject);
        }
        Container.put("locations", array);
        String response = AESencrp.encrypt(Container.toString());
        return Response.status(200).entity(response).build();
    }

    //http://localhost:8080/VerplaatsingSysteem/Rest/carTrackers/allMonth?code=6SaKNcdjwWHe6UEvguLzlWjDHF7pYgY7uJSv7jiELc4=
    @Path("allMonth")
    @GET
    @Consumes("text/plain")
    @Produces("application/json")
    public Response getallbyMonth(@QueryParam("code") String f) throws JSONException, Exception {
        String lastCar = "";
        boolean eersteauto = true;
        JSONObject jsonObject;
        JSONArray array = new JSONArray();
        String encrp = AESencrp.decrypt(f);
        Data data = getData(encrp);
        JSONObject Container = new JSONObject();
        JSONArray arrayresponse = new JSONArray();
        for (CarTrackerDAO c : cartrackerhandler.getCarTrackersbyMotnh(Integer.valueOf(data.getMonth()), Integer.valueOf(data.getYear()))) {
            jsonObject = new JSONObject();
            if(eersteauto){
                Container = new JSONObject();
                array = new JSONArray();
                Container.put("licenseplate", c.getLicensePlate());
                lastCar = c.getLicensePlate();
                eersteauto = false;
            }
            else if (!lastCar.equals(c.getLicensePlate())) {
                Container.put("locations", array);
                arrayresponse.put(Container);
                Container = new JSONObject();
                array = new JSONArray();
                Container.put("licenseplate", c.getLicensePlate());
                lastCar = c.getLicensePlate();
            }
            jsonObject.put("lat", c.getLatitude());
            jsonObject.put("long", c.getLongitude());
            Calendar ca = Calendar.getInstance();
            ca.setTimeInMillis(c.getDate());
            jsonObject.put("date", ca.getTime());
            array.put(jsonObject);
        }
        String response = AESencrp.encrypt(arrayresponse.toString());
        return Response.status(200).entity(response).build();
    }
    
    @Path("getPolitie")
    @GET
    @Consumes("text/plain")
    @Produces("application/json")
    public Response getPolitie(@QueryParam("code") String f) throws JSONException, Exception {
        JSONObject jsonObject;
        Boolean eerstekeer = true;
        JSONArray array = new JSONArray();
        AESencrp encrp = new AESencrp();
        f = f.replace(" ", "+");
        String encrpstring = AESencrp.decrypt(f);
        String[] antwoord = encrpstring.split("&");
        int indexid = antwoord[0].indexOf("=");
        int indexmillis = antwoord[1].indexOf("=");
        String id = antwoord[0].substring(indexid + 1);
        String millis = antwoord[1].substring(indexmillis +1);
        PolitieConnector con = PolitieConnector.getInstance();
        con.setLicenseplate(id);
        JSONObject Container = new JSONObject();
        for (CarTrackerDAO c : cartrackerhandler.getCarTrackersPolitie(id,Long.valueOf(millis))) {
            jsonObject = new JSONObject();
            if (eerstekeer) {
                Container.put("licenseplate", c.getLicensePlate());
                eerstekeer = false;
            }
            jsonObject.put("lat", c.getLatitude());
            jsonObject.put("long", c.getLongitude());
            Calendar ca = Calendar.getInstance();
            ca.setTimeInMillis(c.getDate());
            jsonObject.put("date", ca.getTime());
            array.put(jsonObject);
        }
        Container.put("locations", array);
        String response = AESencrp.encrypt(Container.toString());
        return Response.status(200).entity(response).build();
    }
    
    @Path("getMonitoring")
    @GET
    @Produces("application/json")
    public Response getMonitor() throws JSONException, Exception {
        VerplaatsingSysteem systeem = VerplaatsingSysteem.getInstance();
        int messages = systeem.getMessages();
        int packages = systeem.getPackages();
        JSONObject object = new JSONObject();
        object.put("messages", messages);
        object.put("packages", packages);
        return Response.status(200).entity(object.toString()).build();
    }
    
    @Path("getStatus")
    @GET
    @Produces("application/json")
    public Response getStatus() throws JSONException, Exception {
        return Response.ok().build();
    }
    
    
    
    public Data getData(String data) {
        String[] antwoord = data.split("&");
        Data data2;
        switch (antwoord.length) {
            case 3:
                {
                    int idindex = antwoord[0].indexOf("=");
                    int monthindex = antwoord[1].indexOf("=");
                    int yearindex = antwoord[2].indexOf("=");
                    String id = antwoord[0].substring(idindex + 1);
                    String month = antwoord[1].substring(monthindex + 1);
                    String year = antwoord[2].substring(yearindex + 1);
                    data2 = new DataId(id, month, year);
                    break;
                }
            default:
                {
                    int monthindex = antwoord[0].indexOf("=");
                    int yearindex = antwoord[1].indexOf("=");
                    String month = antwoord[0].substring(monthindex + 1);
                    String year = antwoord[1].substring(yearindex + 1);
                    data2 = new Data(month, year);
                    break;
                }
        }

        return data2;
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
