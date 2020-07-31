package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateTraderException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.TraderNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TraderDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Test the trader database access object
 */
public class TraderDAOTest {

    /**
     * EntityManager
     */
    private EntityManager entityManager = Database.getEntityManager();

    /**
     * Trader DAO
     */
    private TraderDAO traderDAO = new TraderDAO();

    /**
     * Test adding a new trader to the database
     */
    @Test
    public void testPersist() {
        Trader trader = new Trader();
        trader.setId(UUID.randomUUID().hashCode());
        trader.setHpStock(666);
        try {
            traderDAO.persist(trader);
            entityManager.getTransaction().begin();
            Trader trader1 = entityManager.find(Trader.class, trader.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(trader1.getHpStock(), 666);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * test persisting already exiting trader
     */
    @Test
    public void testPersistDuplicates() {
        Trader trader = new Trader();
        trader.setId(5);
        trader.setHpStock(42);
        trader.setFuelStock(42);
        try {
            traderDAO.persist(trader);
        }
        catch(DuplicateTraderException e) {
            Assert.fail();
        }

        Trader trader1 = new Trader();
        trader1.setId(5);
        trader1.setFuelStock(666);
        trader1.setHpStock(666);
        try {
            traderDAO.persist(trader1);
        }
        catch(DuplicateTraderException e) {
            entityManager.getTransaction().begin();
            Trader trader2 = entityManager.find(Trader.class, trader1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(42, trader2.getHpStock());
            Assert.assertEquals(42, trader2.getFuelStock());
        }
    }

    /**
     * Test editing an existing trader in the database
     */
    @Test
    public void testEdit() {
        Trader trader = new Trader();
        trader.setId(UUID.randomUUID().hashCode());
        trader.setFuelStock(666);
        try {
            traderDAO.persist(trader);
            entityManager.getTransaction().begin();
            Trader trader1 = entityManager.find(Trader.class, trader.getId());
            trader1.setFuelStock(10);
            traderDAO.update(trader1);
            entityManager.getTransaction().commit();
            Assert.assertEquals(trader1.getFuelStock(), 10);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * Test editing a non existing trader
     */
    @Test(expected = TraderNotFoundException.class)
    public void testEditNonExisting() throws TraderNotFoundException{
        Trader trader = new Trader();
        trader.setId(1);
        traderDAO.update(trader);
    }

    /**
     * Test removing an existing trader from the database
     */
    @Test
    public void testRemove() {
        Trader trader = new Trader();
        trader.setId(UUID.randomUUID().hashCode());
        try {
            traderDAO.persist(trader);
            traderDAO.remove(trader);
            entityManager.getTransaction().begin();
            Trader trader1 = entityManager.find(Trader.class,trader.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(trader1);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * test removing a non existing trader
     */
    @Test(expected = TraderNotFoundException.class)
    public void testRemoveNonExisting() throws TraderNotFoundException {
        Trader trader = new Trader();
        trader.setId(2);
        traderDAO.remove(trader);
    }


    /** Random planet name generator
     * @return a random planet name */
    public static String planetNameGenerator(){
        Random random = new Random();
        String alphabet = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
        alphabet = alphabet.replace(',',' ');
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<100;i++){
            builder.append(alphabet.toCharArray()[random.nextInt(alphabet.length())]);
        }
        return builder.toString();
    }

}
