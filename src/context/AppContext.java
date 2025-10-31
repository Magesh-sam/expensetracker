package context;

import java.sql.Connection;

import model.dao.AppUserDAO;
import util.DBConfig;

public class AppContext {

    private static Connection dbConnection;

    private AppContext() {
    }

    public static Connection getDBConnection() {
        return DBConfig.getInstance();

    }

    public static AppUserDAO getAppUserDAO() {
        return AppUserDAO.getInstance();
    }
}
