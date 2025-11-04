package context;

import controller.AppUserController;
import java.sql.Connection;
import model.dao.AppUserDAO;
import model.dao.CategoryDAO;
import model.dao.PaymentMethodDAO;
import model.dao.TransactionDAO;
import model.dto.AppUser;
import service.AppUserService;
import util.DBConfig;
import view.AppUserView;
import view.ExpenseView;
import view.FunctionalView;
import view.IncomeView;
import service.CategoryService;
import service.PaymentService;
import service.TransactionService;
import controller.TransactionController;
import interfaces.IAppUserDAO;
import interfaces.ICategoryDAO;
import interfaces.IPaymentMethodDAO;
import interfaces.ITransactionService;
import controller.CategoryController;
import controller.PaymentController;

public class AppContext {

    private AppContext() {
    }

    private static AppUser currentUser = null;

    // db connections
    public static Connection getDBConnection() {
        return DBConfig.getInstance();

    }

    // dDAOs
    public static IAppUserDAO getAppUserDAO() {
        return AppUserDAO.getInstance();
    }

    public static ICategoryDAO getCategoryDAO() {
        return CategoryDAO.getInstance();
    }

    public static IPaymentMethodDAO getPaymentMethodDAO() {
        return PaymentMethodDAO.getInstance();
    }

    public static TransactionDAO getTransactionDAO() {
        return TransactionDAO.getInstance();
    }

    // services
    public static AppUserService getAppUserService() {
        return AppUserService.getInstance();
    }

    public static ITransactionService getTransactionService() {
        return TransactionService.getInstance();
    }

    public static CategoryService getCategoryService() {
        return CategoryService.getInstance();
    }

    public static PaymentService getPaymentService() {
        return PaymentService.getInstance();
    }

    // controllers
    public static AppUserController getAppUserController() {
        return AppUserController.getInstance();
    }

    public static TransactionController getTransactionController() {
        return TransactionController.getInstance();
    }

    public static CategoryController getCategoryController() {
        return CategoryController.getInstance();
    }

    public static PaymentController getPaymentController() {
        return PaymentController.getInstance();
    }

    // views
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

    public static AppUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(AppUser currentUser) {
        AppContext.currentUser = currentUser;
    }

}
