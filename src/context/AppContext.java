package context;

import controller.AppUserController;
import java.sql.Connection;
import model.dao.AppUserDAO;
import model.dao.CategoryDAO;
import model.dao.PaymentMethodDAO;
import model.dao.TransactionDAO;
import service.AppUserService;
import util.DBConfig;

public class AppContext {

    private AppContext() {
    }

    //db connections
    public static Connection getDBConnection() {
        return DBConfig.getInstance();

    }

    //dDAOs
    public static AppUserDAO getAppUserDAO() {
        return AppUserDAO.getInstance();
    }

    public static CategoryDAO getCategoryDAO() {
        return CategoryDAO.getInstance();
    }

    public static PaymentMethodDAO getPaymentMethodDAO() {
        return PaymentMethodDAO.getInstance();
    }

    public static TransactionDAO getTransactionDAO() {
        return TransactionDAO.getInstance();
    }

    //services
    public static AppUserService getAppUserService() {
        return AppUserService.getInstance();
    }

    //controllers

    public static AppUserController getAppUserController() {
        return AppUserController.getInstance();
    }
}
