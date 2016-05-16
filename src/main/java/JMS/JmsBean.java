/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMS;

import Controller.CarTrackerHandler;
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
import org.json.JSONObject;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author casva
 */
@MessageDriven(mappedName = "jms/KwetterGo/queue", activationConfig = {
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
            JSONObject json =  new JSONObject(message.getStringProperty("content"));
            CarTrackerDAO cartracker = new CarTrackerDAO();
            cartracker.setLatitude(json.getDouble("lat"));
            cartracker.setLongitude(json.getDouble("long"));
            cartracker.setAutoid(json.getInt("carId"));
            cartrackerHandler.addCarTracker(cartracker);
        } catch (JMSException ex) {
            Logger.getLogger(JmsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
