/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Websocket;

import Controller.CarTrackerHandler;
import java.io.IOException;
import java.io.Reader;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import org.eclipse.persistence.internal.oxm.record.json.JSONReader;
import org.json.JSONObject;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author Casvan
 */
@Stateless
public class CartrackerDecoder implements Decoder.TextStream<CarTrackerDAO> {
    
    @EJB
    private CarTrackerHandler carTrackerHandler;
    private  int count = 10;
    public CartrackerDecoder(){
        
    }
    @Override
    public CarTrackerDAO decode(Reader arg0) throws DecodeException, IOException, NullPointerException {
        System.out.println("decoder");
        count++;
        JsonReader jsonReader = Json.createReader(arg0);
        JsonObject jsontje = jsonReader.readObject();
        JSONObject json = new JSONObject(jsontje.toString());
        double lat =  json.getDouble("lat");
        double longtitude = json.getDouble("long");
        
        CarTrackerDAO cartracker = new CarTrackerDAO();
        cartracker.setLatitude(lat);
        cartracker.setLongitude(longtitude);
        cartracker.setAutoid(json.getInt("carId"));
        carTrackerHandler.addCarTracker(cartracker);
        return cartracker;
        
    }

    @Override
    public void init(EndpointConfig config) {
      
    }

    @Override
    public void destroy() {
        
    }
    
}
