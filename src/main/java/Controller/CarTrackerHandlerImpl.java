/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author Casvan
 */
@Local
@Stateless
public class CarTrackerHandlerImpl implements CarTrackerHandler{
    @PersistenceContext(unitName = "com.mycompany_Verplaatsingsysteem_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @Override
    public boolean addCarTracker(CarTrackerDAO car) {
        try{ 
        entityManager.persist(car);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<CarTrackerDAO> getCarTrackers() {
          Query query = entityManager.createQuery(
      "SELECT c FROM CarTrackerDAO c");
         return (List<CarTrackerDAO>)query.getResultList();
         
    }

    @Override
    public List<CarTrackerDAO> getCarTrackerById(int id) {
           List<CarTrackerDAO> ct = (List<CarTrackerDAO>) entityManager.createQuery("SELECT t FROM CarTrackerDAO t WHERE t.carId = :id").setParameter("id", id).getResultList();
        return ct;
       
    }

 
    
    
}
