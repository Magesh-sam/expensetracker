package interfaces;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import model.dto.CategoryAmount;
import model.dto.Transaction;

public interface ITransactionService {

    int createTransaction(Transaction transaction) throws SQLException;

    List<Transaction> getAllTransactionsByUserId(int userId) throws SQLException;

    List<Transaction> getExpensesByUserId(int userId) throws SQLException;

    List<Transaction> getIncomeByUserId(int userId) throws SQLException;

    List<Transaction> getTransactionsByCategory(int userId, int categoryId) throws SQLException;

    List<Transaction> getExpensesByCategory(int userId, int categoryId) throws SQLException;

    List<Transaction> getIncomeByCategory(int userId, int categoryId) throws SQLException;

    List<Transaction> getExpensesByDate(int userId, LocalDate date) throws SQLException;

    List<Transaction> getIncomeByDate(int userId, LocalDate date) throws SQLException;

    List<Transaction> getTopFiveTransactionsByAmount(int userId) throws SQLException;

    List<Transaction> getTopFiveExpensesByAmount(int userId) throws SQLException;

    List<Transaction> getTopFiveIncomeByAmount(int userId) throws SQLException;

    List<CategoryAmount> getTopFiveCategoriesByAmount(int userId) throws SQLException;

    List<CategoryAmount> getTopFiveExpenseCategoriesByAmount(int userId) throws SQLException;

    List<CategoryAmount> getTopFiveIncomeCategoriesByAmount(int userId) throws SQLException;

    List<Transaction> getExpenseTransactionsByDateRange(int userId, LocalDate start, LocalDate end)
            throws SQLException;

    List<Transaction> getIncomeTransactionsByDateRange(int userId, LocalDate start, LocalDate end)
            throws SQLException;

    boolean updateTransaction(Transaction transaction) throws SQLException;

    boolean deleteTransaction(int transactionId, int userId) throws SQLException;

    int getTotalIncome(int userId) throws SQLException;

    int getTotalExpense(int userId) throws SQLException;

}