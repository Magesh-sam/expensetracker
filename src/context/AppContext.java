package context;

import java.sql.Connection;

import model.dao.AppUserDAO;
import model.dao.CategoryDAO;
import model.dao.PaymentMethodDAO;
import util.DBConfig;

public class AppContext {

    private AppContext() {
    }

    public static Connection getDBConnection() {
        return DBConfig.getInstance();

    }

    public static AppUserDAO getAppUserDAO() {
        return AppUserDAO.getInstance();
    }

    public static CategoryDAO getCategoryDAO() {
        return CategoryDAO.getInstance();
    }

    public static PaymentMethodDAO getPaymentMethodDAO() {
        return PaymentMethodDAO.getInstance();
    }
}
