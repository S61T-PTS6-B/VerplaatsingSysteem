/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Politie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author casva
 */
public class PolitieConnector {
       private static PolitieConnector instance = null;
       private String Licenseplate = "";
    
    
    public static PolitieConnector getInstance(){
        if(instance == null) {
           instance = new PolitieConnector();
        }
        return instance;
    }

    public String getLicenseplate() {
        return Licenseplate;
    }

    public void setLicenseplate(String Licenseplate) {
        this.Licenseplate = Licenseplate;
    }
}
