/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import java.util.List;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author Casvan
 */
public interface CarTrackerHandler {
    public boolean addCarTracker(CarTrackerDAO car);
    public List<CarTrackerDAO> getCarTrackers();
    public List<CarTrackerDAO> getCarTrackerById(int id);
    public List<List<CarTrackerDAO>> getCarTrackerByList(List<Integer> ids);
    public List<CarTrackerDAO> getCarTrackers(String id,int maandid, int jaar);
    public List<CarTrackerDAO> getCarTrackersbyMotnh(int maandid, int jaar);
    public List<CarTrackerDAO> getCarTrackersPolitie(String id,long millis);
}
