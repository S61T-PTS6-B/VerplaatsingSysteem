/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author Casvan
 */
@WebServlet(name = "OpenController", urlPatterns = {"/OpenController"})
public class OpenController extends HttpServlet {
    
    @EJB
    private CarTrackerHandler cartrackerhandler;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CarTrackerDAO cartracker = new CarTrackerDAO();
        cartracker.setLatitude(5.030303030);
        cartracker.setLongitude(6.03030300330);
         CarTrackerDAO cartracker2 = new CarTrackerDAO();
        cartracker2.setLatitude(224.030303030);
        cartracker2.setLongitude(63);
         CarTrackerDAO cartracker3 = new CarTrackerDAO();
        cartracker3.setLatitude(83.0229376);
        cartracker3.setLongitude(182.27115);
         CarTrackerDAO cartracker4 = new CarTrackerDAO();
        cartracker4.setLatitude(-83.0900909);
        cartracker4.setLongitude(6.03030300330);
        cartrackerhandler.addCarTracker(cartracker);
        cartrackerhandler.addCarTracker(cartracker2);
        cartrackerhandler.addCarTracker(cartracker3);
        cartrackerhandler.addCarTracker(cartracker4);
        
        
        
        request.getRequestDispatcher("Open.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
