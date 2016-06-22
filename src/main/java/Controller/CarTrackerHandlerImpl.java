/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class CarTrackerHandlerImpl implements CarTrackerHandler {

    @PersistenceContext(unitName = "com.mycompany_Verplaatsingsysteem_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;

    @Override
    public boolean addCarTracker(CarTrackerDAO car) {
        try {
            entityManager.persist(car);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<CarTrackerDAO> getCarTrackers() {
        Query query = entityManager.createQuery(
                "SELECT c FROM CarTrackerDAO c");
        return (List<CarTrackerDAO>) query.getResultList();

    }

    @Override
    public List<CarTrackerDAO> getCarTrackerById(int id) {
        List<CarTrackerDAO> ct = (List<CarTrackerDAO>) entityManager.createQuery("SELECT t FROM CarTrackerDAO t WHERE t.carId = :id").setParameter("id", id).getResultList();
        return ct;

    }

    @Override
    public List<List<CarTrackerDAO>> getCarTrackerByList(List<Integer> ids) {
        List<List<CarTrackerDAO>> cartrackers = new ArrayList<>();
        for (Integer i : ids) {
            Query query = entityManager.createQuery("SELECT c FROM CarTrackerDAO c WHERE c.carId = :id").setParameter("id", i);
            List<CarTrackerDAO> carids = (List<CarTrackerDAO>) query.getResultList();
            cartrackers.add(carids);
        }
        return cartrackers;
    }

    @Override
    public List<CarTrackerDAO> getCarTrackers(String id, int maandid, int jaar) {
        List<CarTrackerDAO> cartrackers = null;
        Calendar calendar = new GregorianCalendar();
        calendar.set(jaar, maandid -1, 25, 0, 0, 0);
        Date date = calendar.getTime();
        Calendar calendarafter = new GregorianCalendar();
        calendarafter.set(jaar, maandid, 25, 0, 0, 0);
        Date after = calendarafter.getTime();
        Query query = entityManager.createQuery("SELECT c FROM CarTrackerDAO c WHERE c.licensePlate = :id AND c.date BETWEEN :date AND :after ORDER BY c.date ASC ").setParameter("date", date.getTime()).setParameter("after", after.getTime()).setParameter("id", id).setMaxResults(10);
        List<CarTrackerDAO> carids = (List<CarTrackerDAO>) query.getResultList();
        return carids;

    }

    @Override
    public List<CarTrackerDAO> getCarTrackersbyMotnh(int maandid, int jaar) {
        List<CarTrackerDAO> cartrackers = null;
        Calendar calendar = new GregorianCalendar();
        calendar.set(2016, maandid -1, 25, 0, 0, 0);
        Date date = calendar.getTime();
        Calendar calendarafter = new GregorianCalendar();
        calendarafter.set(2016, maandid, 25, 0, 0, 0);
        Date after = calendarafter.getTime();
        Query query = entityManager.createQuery("SELECT c FROM CarTrackerDAO c WHERE c.date BETWEEN :date AND :after ORDER BY c.licensePlate,c.date ASC").setParameter("date", date.getTime()).setParameter("after", after.getTime()).setMaxResults(60);
        List<CarTrackerDAO> carids = (List<CarTrackerDAO>) query.getResultList();
        return carids;
    }

    @Override
    public List<CarTrackerDAO> getCarTrackersPolitie(String id, long millis) {
        List<CarTrackerDAO> cartrackers = null;
        Date after = new Date();
        Query query = entityManager.createQuery("SELECT c FROM CarTrackerDAO c WHERE c.licensePlate = :id AND c.date BETWEEN :date AND :after ORDER BY c.date ASC").setParameter("date", millis).setParameter("after", after.getTime()).setParameter("id", id);
        List<CarTrackerDAO> carids = (List<CarTrackerDAO>) query.getResultList();
        return carids;
    }

}
