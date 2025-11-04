package service;

import model.dao.TransactionDAO;
import model.dto.CategoryAmount;
import model.dto.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import interfaces.ITransactionService;

public class TransactionService implements ITransactionService {

    private static final TransactionDAO transactionDAO = TransactionDAO.getInstance();
    private static ITransactionService transactionService;

    private TransactionService() {
    }

    public static ITransactionService getInstance() {
        if (transactionService == null) {
            transactionService = new TransactionService();
        }
        return transactionService;
    }

    @Override
    public int createTransaction(Transaction transaction) throws SQLException {
        Objects.requireNonNull(transaction, "Transaction cannot be null");

        return transactionDAO.createTransaction(transaction);
    }

    @Override
    public List<Transaction> getAllTransactionsByUserId(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getAllTransactionsByUserId(userId);
    }

    @Override
    public List<Transaction> getExpensesByUserId(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getExpensesByUserId(userId);
    }

    @Override
    public List<Transaction> getIncomeByUserId(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getIncomeByUserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByCategory(int userId, int categoryId) throws SQLException {
        validateUserId(userId);
        validateCategoryId(categoryId);
        return transactionDAO.getTransactionsByCategory(userId, categoryId);
    }

    @Override
    public List<Transaction> getExpensesByCategory(int userId, int categoryId) throws SQLException {
        validateUserId(userId);
        validateCategoryId(categoryId);
        return transactionDAO.getExpensesByCategory(userId, categoryId);
    }

    @Override
    public List<Transaction> getIncomeByCategory(int userId, int categoryId) throws SQLException {
        validateUserId(userId);
        validateCategoryId(categoryId);
        return transactionDAO.getIncomeByCategory(userId, categoryId);
    }

    @Override
    public List<Transaction> getExpensesByDate(int userId, LocalDate date) throws SQLException {
        validateUserId(userId);
        Objects.requireNonNull(date, "Date cannot be null");
        return transactionDAO.getExpensesByDate(userId, date);
    }

    @Override
    public List<Transaction> getIncomeByDate(int userId, LocalDate date) throws SQLException {
        validateUserId(userId);
        Objects.requireNonNull(date, "Date cannot be null");
        return transactionDAO.getIncomeByDate(userId, date);
    }

    @Override
    public List<Transaction> getTopFiveTransactionsByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveTransactionsByAmount(userId);
    }

    @Override
    public List<Transaction> getTopFiveExpensesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveExpensesByAmount(userId);
    }

    @Override
    public List<Transaction> getTopFiveIncomeByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveIncomeByAmount(userId);
    }

    @Override
    public List<CategoryAmount> getTopFiveCategoriesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveCategoriesByAmount(userId);
    }

    @Override
    public List<CategoryAmount> getTopFiveExpenseCategoriesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveExpenseCategoriesByAmount(userId);
    }

    @Override
    public List<CategoryAmount> getTopFiveIncomeCategoriesByAmount(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTopFiveIncomeCategoriesByAmount(userId);
    }

    @Override
    public List<Transaction> getExpenseTransactionsByDateRange(int userId, LocalDate start, LocalDate end)
            throws SQLException {
        validateUserId(userId);

        return transactionDAO.getExpenseTransactionsByDateRange(userId, start, end);
    }

    @Override
    public List<Transaction> getIncomeTransactionsByDateRange(int userId, LocalDate start, LocalDate end)
            throws SQLException {
        validateUserId(userId);

        return transactionDAO.getIncomeTransactionsByDateRange(userId, start, end);
    }

    @Override
    public boolean updateTransaction(Transaction transaction) throws SQLException {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        validateTransaction(transaction);
        return transactionDAO.updateTransaction(transaction);
    }

    @Override
    public boolean deleteTransaction(int transactionId, int userId) throws SQLException {
        validateTransactionId(transactionId);
        validateUserId(userId);
        return transactionDAO.deleteTransaction(transactionId, userId);
    }

    @Override
    public int getTotalIncome(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTotalIncome(userId);

    }

    @Override
    public int getTotalExpense(int userId) throws SQLException {
        validateUserId(userId);
        return transactionDAO.getTotalExpense(userId);

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

    private void validatePaymentMethodId(int paymentMethodId) {
        if (paymentMethodId <= 0) {
            throw new IllegalArgumentException("Invalid Payment Method id");

        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.doubleValue() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

    }

    private void validateTransactionStatus(Transaction.TransactionType transactionType) {
        Objects.requireNonNull(transactionType, "Transaction type caanot be null");

        switch (transactionType) {
            case income:
            case expense:
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
        }
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getTransactionId() <= 0) {
            throw new IllegalArgumentException("Invalid Transaction id");
        }

        validateUserId(transaction.getAppuserId());
        validateCategoryId(transaction.getCategoryId());
        validatePaymentMethodId(transaction.getPaymentMethodId());
        validateAmount(transaction.getAmount());
        validateTransactionStatus(transaction.getTransactionType());

    }
}
