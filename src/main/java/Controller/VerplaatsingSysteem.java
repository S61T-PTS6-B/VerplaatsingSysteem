/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import org.json.JSONObject;

/**
 *
 * @author casva
 */
public class VerplaatsingSysteem {
    private static int messages;
    private static VerplaatsingSysteem instance = null;
    private static int packages;
     
    public static VerplaatsingSysteem getInstance(){
        if(instance == null) {
           instance = new VerplaatsingSysteem();
           messages = 0;
           packages = 0;
        }
        return instance;
    }
    
    public void improveMessages(){
        messages++;
    }
    
    public int getMessages(){
        return messages;
    }
    
    public void improvePackages(){
        packages++;
    }
    
    public int getPackages(){
        return packages;
    }
}
