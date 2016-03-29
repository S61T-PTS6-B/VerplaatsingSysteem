/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Websocket;

import Controller.CarTrackerHandler;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CartrackerDecoder implements Decoder.Text<CarTrackerDAO> {
    
    @EJB
    private CarTrackerHandler carTrackerHandler;
    private  int count = 10;
    public CartrackerDecoder(){
        
    }
  

    @Override
    public void init(EndpointConfig config) {
      System.out.println("init");
    }

    @Override
    public void destroy() {
        
    }

    @Override
    public CarTrackerDAO decode(String s) throws DecodeException {
        System.out.println("decoder");
        count++;
        try{
        JSONObject json = new JSONObject(s);
        double lat =  json.getDouble("lat");
        double longtitude = json.getDouble("long");
        
        CarTrackerDAO cartracker = new CarTrackerDAO();
        cartracker.setLatitude(lat);
        cartracker.setLongitude(longtitude);
        cartracker.setAutoid(json.getInt("carId"));
        carTrackerHandler.addCarTracker(cartracker);
        }catch(Exception e){
            CarTrackerDAO cartracker = new CarTrackerDAO();
            cartracker.setId(0);
            return cartracker;
        }
        CarTrackerDAO cartracker = new CarTrackerDAO();
        cartracker.setId(1);
        return cartracker; 
    }

    @Override
    public boolean willDecode(String s) {
        if(s != null){
            try {
                decode(s);
            } catch (DecodeException ex) {
                Logger.getLogger(CartrackerDecoder.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        else{
            return false;
        }
    }
    
}
