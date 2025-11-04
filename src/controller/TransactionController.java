package controller;

import service.TransactionService;
import model.dto.CategoryAmount;
import model.dto.Transaction;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransactionController {

    private static TransactionController transactionController;
    private static final TransactionService transactionService = TransactionService.getInstance();

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
            return transactionService.createTransaction(transaction);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) {
        try {
            return transactionService.getAllTransactionsByUserId(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getExpensesByUserId(int userId) {
        try {
            return transactionService.getExpensesByUserId(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getIncomeByUserId(int userId) {
        try {
            return transactionService.getIncomeByUserId(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getExpensesByCategory(int userId, int categoryId) {
        try {
            return transactionService.getExpensesByCategory(userId, categoryId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getIncomeByCategory(int userId, int categoryId) {
        try {
            return transactionService.getIncomeByCategory(userId, categoryId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTransactionsByCategory(int userId, int categoryId) {
        try {
            return transactionService.getTransactionsByCategory(userId, categoryId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveTransactionsByAmount(int userId) {
        try {
            return transactionService.getTopFiveTransactionsByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveCategoriesByAmount(int userId) {
        try {
            return transactionService.getTopFiveCategoriesByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTransactionsByDate(int userId, LocalDate date) {
        try {
            return transactionService.getTransactionsByDate(userId, date);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveExpensesByAmount(int userId) {
        try {
            return transactionService.getTopFiveExpensesByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveIncomeByAmount(int userId) {
        try {
            return transactionService.getTopFiveIncomeByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveExpenseCategoriesByAmount(int userId) {
        try {
            return transactionService.getTopFiveExpenseCategoriesByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveIncomeCategoriesByAmount(int userId) {
        try {
            return transactionService.getTopFiveIncomeCategoriesByAmount(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean updateTransaction(Transaction transaction) {
        try {
            return transactionService.updateTransaction(transaction);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteTransaction(int transactionId, int userId) {
        try {
            return transactionService.deleteTransaction(transactionId, userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
