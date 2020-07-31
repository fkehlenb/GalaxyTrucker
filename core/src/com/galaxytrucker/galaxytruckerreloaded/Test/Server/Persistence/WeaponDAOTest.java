package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateWeaponException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.WeaponNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class WeaponDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO WeaponDAO = new WeaponDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        Weapon testObject = new Weapon();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setBurst(5);
        try {
            WeaponDAO.persist(testObject);
            entityManager.getTransaction().begin();
            Weapon testObject1 = entityManager.find(Weapon.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject1.getBurst());
        }
        catch(DuplicateWeaponException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        Weapon testObject = new Weapon();
        testObject.setId(1);
        testObject.setBurst(5);
        try {
            WeaponDAO.persist(testObject);
        }
        catch (DuplicateWeaponException e) {
            Assert.fail();
        }
        Weapon testObject1 = new Weapon();
        testObject1.setId(1);
        testObject1.setBurst(7);
        try {
            WeaponDAO.persist(testObject1);
        }
        catch(DuplicateWeaponException e) {
            entityManager.getTransaction().begin();
            Weapon testObject2 = entityManager.find(Weapon.class, testObject1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject2.getBurst());
        }
    }

    /**
     * test updating an exisitng 
     */
    @Test
    public void testUpdate() {
        Weapon testObject = new Weapon();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setBurst(5);
        try {
            WeaponDAO.persist(testObject);
        }
        catch(DuplicateWeaponException e) {
            Assert.fail();
        }
        testObject.setBurst(6);
        try {
            WeaponDAO.update(testObject);
            entityManager.getTransaction().begin();
            Weapon testObject1 = entityManager.find(Weapon.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(6, testObject1.getBurst());
        }
        catch(WeaponNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test updating a non existing
     */
    @Test(expected = WeaponNotFoundException.class)
    public void testUpdateNonExisting() throws WeaponNotFoundException {
        Weapon testObject = new Weapon();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setBurst(5);
        WeaponDAO.update(testObject);
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        Weapon testObject = new Weapon();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setBurst(5);
        try {
            WeaponDAO.persist(testObject);
            WeaponDAO.remove(testObject);
            entityManager.getTransaction().begin();
            Weapon testObject1 = entityManager.find(Weapon.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(testObject1);
        }
        catch (DuplicateWeaponException | WeaponNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = WeaponNotFoundException.class)
    public void testRemoveNonExisting() throws WeaponNotFoundException{
        Weapon testObject = new Weapon();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setBurst(5);
        WeaponDAO.remove(testObject);
    }
}
