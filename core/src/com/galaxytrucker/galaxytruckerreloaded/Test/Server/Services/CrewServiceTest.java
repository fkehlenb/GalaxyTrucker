package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.CrewService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrewServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private CrewService service = new CrewService();

    private ShipDAO shipDAO = new ShipDAO();

    /**
     * testing whether a crew member that is not actually on the ship can be added to ship with this method
     * should not happen
     */
    @Test
    public void testCrewMoved() {
        List<Room> rooms = new ArrayList<>();

        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 0));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        tiles.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));

        Room room = new Room();
        room.setId(UUID.randomUUID().hashCode());
        room.setTiles(tiles);
        rooms.add(room);

        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 0, 1));
        tiles1.add(new Tile(UUID.randomUUID().hashCode(), 1, 1));
        Room room1 = new Room();
        room1.setId(UUID.randomUUID().hashCode());
        room1.setTiles(tiles1);
        rooms.add(room1);
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setSystems(rooms);
        s.setAssociatedUser("testuser");
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }
        Crew crew = new Crew();
        crew.setId(UUID.randomUUID().hashCode());

        ResponseObject res = service.moveCrewToRoom(s, crew, room1);
        Assert.assertFalse(res.isValidRequest());
    }
}
