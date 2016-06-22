/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author casva
 */
@ManagedBean(name = "verplaatsingBean")
@SessionScoped
public class VerplaatsingBean {
    
    public VerplaatsingBean(){
        
    }
    
     public void CarLocations(ActionEvent event) throws IOException, ProtocolException, Exception{
        URL url = new URL("http://localhost:9233/MonitoringSysteem/Rest/statusmessages/postmessage");
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
//Dit is het object zelf
        
        String input = "{systeemnaam : verplaatsingsysteem,message : homo}";
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        
        
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
        conn.disconnect();
    
   }
    
}
