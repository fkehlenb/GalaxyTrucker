package com.galaxytrucker.galaxytruckerreloaded.Server.Database;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Database {

    /**
     * Database connection source
     */
    private ConnectionSource source;


    /**
     * Load or create h2 database
     */
    public void setup() {
        String url = "jdbc:h2:./database";
        try {
            source = new JdbcConnectionSource(url);
            Dao<User, String> userDao = DaoManager.createDao(source, User.class);
            try {
                TableUtils.createTable(source, User.class);
            } catch (Exception f) {
                f.printStackTrace();
            }
            User u = new User("test");
            userDao.create(u);
            User user2 = userDao.queryForId("test");
            System.out.println(user2.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Backup database
     */
    public void backupDatabase() {

    }
}
