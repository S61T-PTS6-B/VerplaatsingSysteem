/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Controller.CarTrackerHandler;
import Politie.PolitieConnector;
import Send.ClientSend;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            System.out.println("message received");
            PolitieConnector con = PolitieConnector.getInstance();
            if(!con.getLicenseplate().equals("")){
                ClientSend.Send(message.getStringProperty("locations"));
            }
            CarTrackerDAO cartracker;
            JSONObject object;
            object = new JSONObject(message.getStringProperty("locations"));
            JSONArray array =  object.getJSONArray("locations");
            for(int i = 0; i < array.length() ; i++){
                object = (JSONObject) array.get(i);
                cartracker = new CarTrackerDAO();
                cartracker.setLicensePlate(message.getStringProperty("carName"));
                String dateStr = object.getString("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date birthDate = sdf.parse(dateStr);
                cartracker.setDate(birthDate);
                cartracker.setLatitude(object.getDouble("lat"));
                cartracker.setLongitude(object.getDouble("long"));
                cartrackerHandler.addCarTracker(cartracker);
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(JmsBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JmsBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
