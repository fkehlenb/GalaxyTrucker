package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.BattleService;
import org.junit.Test;

import javax.persistence.EntityManager;

public class BattleServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private BattleService service = new BattleService();

    /**
     * test successful add to queue
     */
    @Test
    public void testAddToQueue() {
        RequestObject request = new RequestObject();
    }

    /**
     * test successful play moves
     */
    @Test
    public void playMoves() {
    }

    /**
     * test successful get updated data
     */
    @Test
    public void getUpdatedData() {
    }

    /**
     * test successful fetch opponent after relog
     */
    @Test
    public void fetchOpponentAfterRelog() {
    }

    /**
     * test successful flee fight
     */
    @Test
    public void fleeFight() {
    }

    /**
     * test unsuccessful add to queue
     */
    @Test
    public void failTestAddToQueue() {

    }

    /**
     * test unsuccessful play moves
     */
    @Test
    public void failPlayMoves() {
    }

    /**
     * test unsuccessful get updated data
     */
    @Test
    public void failGetUpdatedData() {
    }

    /**
     * test unsuccessful fetch opponent after relog
     */
    @Test
    public void failFetchOpponentAfterRelog() {
    }

    /**
     * test unsuccessful flee fight
     */
    @Test
    public void failFleeFight() {
    }
}