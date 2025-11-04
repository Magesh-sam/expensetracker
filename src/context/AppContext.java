package context;

import controller.AppUserController;
import java.sql.Connection;
import model.dao.AppUserDAO;
import model.dao.CategoryDAO;
import model.dao.PaymentMethodDAO;
import model.dao.TransactionDAO;
import model.dto.Transaction;
import service.AppUserService;
import util.DBConfig;
import view.AppUserView;
import view.ExpenseView;
import view.FunctionalView;
import view.IncomeView;

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

    public static TransactionService getTransactionService() {
        return TransactionService.getInstance();
    }

    public static CategoryService getCategoryService() {
        return CategoryService.getInstance();
    }

    //controllers
    public static AppUserController getAppUserController() {
        return AppUserController.getInstance();
    }

    public static TransactionController getTransactionController() {
        return TransactionController.getInstance();
    }

    public static CategoryController getCategoryController() {
        return CategoryController.getInstance();
    }

    //views
    public static AppUserView getAppUserView() {
        return AppUserView.getInstance();
    }

    public static FunctionalView getFunctionalView() {
        return FunctionalView.getInstance();
    }

    public static ExpenseView getExpenseView() {
        return ExpenseView.getInstance();
    }

    public static IncomeView getIncomeView() {
        return IncomeView.getInstance();
    }
}
