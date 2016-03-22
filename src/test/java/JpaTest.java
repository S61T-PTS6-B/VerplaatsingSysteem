/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import proftaak.Model.CarTrackerDAO;

/**
 *
 * @author Casvan
 */
public class JpaTest {
    
    EntityManager em;
    public JpaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
  //      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Verplaatsingsysteem_war_1.0-SNAPSHOTPU");
   //     em = emf.createEntityManager();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void JPATest(){
       // em.getTransaction().begin();
       // CarTrackerDAO car = new CarTrackerDAO();
       // car.setId(2);
       // car.setSumamry("vette shit hier");
       // em.persist(car);
       // em.getTransaction().commit();
        
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
