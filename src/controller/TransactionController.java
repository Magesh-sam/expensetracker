package controller;

import context.AppContext;
import model.dao.TransactionDAO;
import model.dto.CategoryAmount;
import model.dto.Transaction;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransactionController {

    private static TransactionController transactionController;
    private static final TransactionDAO transactionDAO = TransactionDAO.getInstance();

    private TransactionController() {
    }

    public static TransactionController getInstance() {
        if (transactionController == null) {
            transactionController = new TransactionController();
        }
        return transactionController;
    }

    public int createTransaction(Transaction transaction) {
        try {
            return transactionDAO.createTransaction(transaction);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) {
        try {
            return transactionDAO.getAllTransactionsByUserId(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getExpensesByUserId(int userId) {
        try {
            return transactionDAO.getExpensesByUserId(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getIncomeByUserId(int userId) {
        try {
            return transactionDAO.getIncomeByUserId(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getExpensesByCategory(int userId, int categoryId) {
        try {
            return transactionDAO.getExpensesByCategory(userId, categoryId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getIncomeByCategory(int userId, int categoryId) {
        try {
            return transactionDAO.getIncomeByCategory(userId, categoryId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTransactionsByDate(int userId, LocalDate date) {
        try {
            return transactionDAO.getTransactionsByDate(userId, date);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveExpensesByAmount(int userId) {
        try {
            return transactionDAO.getTopFiveExpensesByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveIncomeByAmount(int userId) {
        try {
            return transactionDAO.getTopFiveIncomeByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveExpenseCategoriesByAmount(int userId) {
        try {
            return transactionDAO.getTopFiveExpenseCategoriesByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveIncomeCategoriesByAmount(int userId) {
        try {
            return transactionDAO.getTopFiveIncomeCategoriesByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateTransaction(Transaction transaction) {
        try {
            return transactionDAO.updateTransaction(transaction);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteTransaction(int transactionId, int userId) {
        try {
            return transactionDAO.deleteTransaction(transactionId, userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
