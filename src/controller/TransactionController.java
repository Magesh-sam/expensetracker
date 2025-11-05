package controller;

import service.TransactionService;
import model.dto.CategoryAmount;
import model.dto.Transaction;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import interfaces.ITransactionService;

public class TransactionController {

    private static TransactionController transactionController;
    private static final ITransactionService transactionService = TransactionService.getInstance();

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
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) {
        try {
            return transactionService.getAllTransactionsByUserId(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> getExpensesByUserId(int userId) {
        try {
            return transactionService.getExpensesByUserId(userId);
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getIncomeByUserId(int userId) {
        try {
            return transactionService.getIncomeByUserId(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getExpensesByCategory(int userId, int categoryId) {
        try {
            return transactionService.getExpensesByCategory(userId, categoryId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getIncomeByCategory(int userId, int categoryId) {
        try {
            return transactionService.getIncomeByCategory(userId, categoryId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTransactionsByCategory(int userId, int categoryId) {
        try {
            return transactionService.getTransactionsByCategory(userId, categoryId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveTransactionsByAmount(int userId) {
        try {
            return transactionService.getTopFiveTransactionsByAmount(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveCategoriesByAmount(int userId) {
        try {
            return transactionService.getTopFiveCategoriesByAmount(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getExpenseByDate(int userId, LocalDate date) {
        try {
            return transactionService.getExpensesByDate(userId, date);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getIncomeByDate(int userId, LocalDate date) {
        try {
            return transactionService.getIncomeByDate(userId, date);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveExpensesByAmount(int userId) {
        try {
            return transactionService.getTopFiveExpensesByAmount(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getTopFiveIncomeByAmount(int userId) {
        try {
            return transactionService.getTopFiveIncomeByAmount(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveExpenseCategoriesByAmount(int userId) {
        try {
            return transactionService.getTopFiveExpenseCategoriesByAmount(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CategoryAmount> getTopFiveIncomeCategoriesByAmount(int userId) {
        try {
            return transactionService.getTopFiveIncomeCategoriesByAmount(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> getExpenseTransactionsByDateRange(int userId, LocalDate start, LocalDate end) {
        try {

            return transactionService.getExpenseTransactionsByDateRange(userId, start, end);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public List<Transaction> getIncomeTransactionsByDateRange(int userId, LocalDate start, LocalDate end) {
        try {

            return transactionService.getIncomeTransactionsByDateRange(userId, start, end);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public boolean updateTransaction(Transaction transaction) {
        try {
            return transactionService.updateTransaction(transaction);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteTransaction(int transactionId, int userId) {
        try {
            return transactionService.deleteTransaction(transactionId, userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int getTotalIncome(int userId) {
        try {
            return transactionService.getTotalIncome(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getTotalExpense(int userId) {
        try {
            return transactionService.getTotalExpense(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
