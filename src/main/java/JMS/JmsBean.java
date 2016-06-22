/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Controller.CarTrackerHandler;
import Controller.VerplaatsingSysteem;
import Politie.PolitieConnector;
import Send.ClientSend;
import Send.FileSend;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.json.JSONArray;
import org.json.JSONObject;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author casva
 */
@MessageDriven(mappedName = "jms/Verplaatsing/queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode",
            propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})

public class JmsBean implements MessageListener {

    @Resource
    private MessageDrivenContext mdctx;
    
    @EJB
    private CarTrackerHandler cartrackerHandler;

    public JmsBean() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            VerplaatsingSysteem verplaatsing = VerplaatsingSysteem.getInstance();
            System.out.println("message received");
            PolitieConnector con = PolitieConnector.getInstance();
            if(con.getLicenseplate().equals(message.getStringProperty("carName"))){
                ClientSend.Send(message.getStringProperty("locations"));
            }
            verplaatsing.improvePackages();
            List<CarTrackerDAO> trackers = new ArrayList<>();
            CarTrackerDAO cartracker;
            JSONObject object;
            object = new JSONObject(message.getStringProperty("locations"));
            JSONArray array =  object.getJSONArray("locations");
            for(int i = 0; i < array.length() ; i++){
                object = (JSONObject) array.get(i);
                cartracker = new CarTrackerDAO();
                cartracker.setLicensePlate(message.getStringProperty("carName"));
                long dateStr = object.getLong("date");
                cartracker.setDate(dateStr);
                cartracker.setLatitude(object.getDouble("lat"));
                cartracker.setLongitude(object.getDouble("long"));
                cartrackerHandler.addCarTracker(cartracker);
                trackers.add(cartracker);
                verplaatsing.improveMessages();
            }
            trackers.sort(CarTrackerComparator);
            int laatste = trackers.size();
            FileSend.Send(trackers.get(laatste - 1).getJson());
            
            
        } catch (JMSException ex) {
            Logger.getLogger(JmsBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static Comparator<CarTrackerDAO> CarTrackerComparator 
                          = new Comparator<CarTrackerDAO>() {

	    public int compare(CarTrackerDAO fruit1, CarTrackerDAO fruit2) {
	    	
	      long fruitName1 = fruit1.getDate();
	      long fruitName2 = fruit2.getDate();
	      
	      //ascending order
	      return Long.compare(fruitName1, fruitName2);
	      
	      //descending order
	      //return fruitName2.compareTo(fruitName1);
	    }

	};
}
