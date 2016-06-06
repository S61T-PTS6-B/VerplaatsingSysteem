/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proftaak.Model;

/**
 *
 * @author casva
 */
public class DataId extends Data{
    
    private String id;
    
    public DataId(String id,String month, String year) {
        super(month, year);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
}
