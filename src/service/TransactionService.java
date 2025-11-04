package service;

import context.AppContext;
import model.dao.TransactionDAO;
import model.dto.CategoryAmount;
import model.dto.Transaction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TransactionService {

    private static final TransactionDAO transactionDAO = TransactionDAO.getInstance();
    private static TransactionService transactionService;

    private TransactionService() {
    }

    public static TransactionService getInstance() {
        if (transactionService == null) {
            transactionService = new TransactionService();
        }
        return transactionService;
    }

    public int createTransaction(Transaction transaction) throws SQLException {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        return transactionDAO.createTransaction(transaction);
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getAllTransactionsByUserId(userId);
    }

    public List<Transaction> getExpensesByUserId(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getExpensesByUserId(userId);
    }

    public List<Transaction> getIncomeByUserId(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getIncomeByUserId(userId);
    }

    public List<Transaction> getTransactionsByCategory(int userId, int categoryId) throws SQLException {
        validateUserId(userId);
        validateCategoryId(categoryId);
        return transactionDAO.getTransactionsByCategory(userId, categoryId);
    }

    public List<Transaction> getExpensesByCategory(int userId, int categoryId) throws SQLException {
        validateUserId(userId);
        validateCategoryId(categoryId);
        return transactionDAO.getExpensesByCategory(userId, categoryId);
    }

    public List<Transaction> getIncomeByCategory(int userId, int categoryId) throws SQLException {
        validateUserId(userId);
        validateCategoryId(categoryId);
        return transactionDAO.getIncomeByCategory(userId, categoryId);
    }

    public List<Transaction> getTransactionsByDate(int userId, LocalDate date) throws SQLException {
        validateUserId(userId);
        Objects.requireNonNull(date, "Date cannot be null");
        return transactionDAO.getTransactionsByDate(userId, date);
    }

    public List<Transaction> getTopFiveTransactionsByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveTransactionsByAmount(userId);
    }

    public List<Transaction> getTopFiveExpensesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveExpensesByAmount(userId);
    }

    public List<Transaction> getTopFiveIncomeByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveIncomeByAmount(userId);
    }

    public List<CategoryAmount> getTopFiveCategoriesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveCategoriesByAmount(userId);
    }

    public List<CategoryAmount> getTopFiveExpenseCategoriesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveExpenseCategoriesByAmount(userId);
    }

    public List<CategoryAmount> getTopFiveIncomeCategoriesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveIncomeCategoriesByAmount(userId);
    }

    public boolean updateTransaction(Transaction transaction) throws SQLException {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        return transactionDAO.updateTransaction(transaction);
    }

    public boolean deleteTransaction(int transactionId, int userId) throws SQLException {
        validateTransactionId(transactionId);
        validateUserId(userId);
        return transactionDAO.deleteTransaction(transactionId, userId);
    }

    private void validateUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user id");
        }
    }

    private void validateCategoryId(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category id");
        }
    }

    private void validateTransactionId(int transactionId) {
        if (transactionId <= 0) {
            throw new IllegalArgumentException("Invalid transaction id");
        }
    }
}
