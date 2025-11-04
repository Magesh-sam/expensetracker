package model.dao;

import context.AppContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.dto.CategoryAmount;
import model.dto.Transaction;

public class TransactionDAO {

    private static TransactionDAO transactionDAO;
    private static final Connection conn = AppContext.getDBConnection();

    private TransactionDAO() {
    }

    public static TransactionDAO getInstance() {
        if (transactionDAO == null) {
            transactionDAO = new TransactionDAO();
        }
        return transactionDAO;
    }

    // add a transaction
    public int createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction_tracker (category_id,app_user_id,payment_method_id,amount,transaction_type) VALUES (?,?,?,?,?::transaction_type)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getCategoryId());
            pstmt.setInt(2, transaction.getAppuserId());
            pstmt.setInt(3, transaction.getPaymentMethodId());
            pstmt.setBigDecimal(4, transaction.getAmount());
            pstmt.setString(5, transaction.getTransactionType().toString());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Transaction creation failed. No ID obtained");
            }
        }
    }

    // get all transactions by user
    public List<Transaction> getAllTransactionsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM transaction_tracker WHERE app_user_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getExpensesByUserId(int userId) throws SQLException {
        return getTransactionsByUserIdAndType(userId, Transaction.TransactionType.expense);
    }

    public List<Transaction> getIncomeByUserId(int userId) throws SQLException {
        return getTransactionsByUserIdAndType(userId, Transaction.TransactionType.income);
    }

    public List<Transaction> getTransactionsByCategory(int userId, int categoryId) throws SQLException {
        String sql = "SELECT * FROM transaction_tracker WHERE app_user_id = ? AND category_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getExpensesByCategory(int userId, int categoryId) throws SQLException {
        return getTransactionsByCategoryAndType(userId, categoryId, Transaction.TransactionType.expense);
    }

    public List<Transaction> getIncomeByCategory(int userId, int categoryId) throws SQLException {
        return getTransactionsByCategoryAndType(userId, categoryId, Transaction.TransactionType.income);
    }

    public List<Transaction> getTransactionsByDate(int userId, LocalDate date) throws SQLException {
        String sql = "SELECT * FROM transaction_tracker WHERE app_user_id = ? AND DATE(created_at) = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, java.sql.Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getTopFiveTransactionsByAmount(int userId) throws SQLException {
        String sql = "SELECT * FROM transaction_tracker WHERE app_user_id = ? ORDER BY amount DESC LIMIT 5";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getTopFiveExpensesByAmount(int userId) throws SQLException {
        return getTopFiveTransactionsByAmountAndType(userId, Transaction.TransactionType.expense);
    }

    public List<Transaction> getTopFiveIncomeByAmount(int userId) throws SQLException {
        return getTopFiveTransactionsByAmountAndType(userId, Transaction.TransactionType.income);
    }

    public List<CategoryAmount> getTopFiveCategoriesByAmount(int userId) throws SQLException {
        String sql = "SELECT c.id category_id, c.name category_name, SUM(amount) total_amount FROM transaction_tracker t join category c on t.category_id = c.id WHERE t.app_user_id = ? GROUP BY c.id ORDER BY total_amount DESC LIMIT 5";
        List<CategoryAmount> categories = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(new CategoryAmount(rs.getInt("category_id"), rs.getString("category_name"), rs.getBigDecimal("total_amount")));
                }
            }
        }
        return categories;
    }

    public List<CategoryAmount> getTopFiveExpenseCategoriesByAmount(int userId) throws SQLException {
        return getTopFiveCategoriesByAmountAndType(userId, Transaction.TransactionType.expense);
    }

    public List<CategoryAmount> getTopFiveIncomeCategoriesByAmount(int userId) throws SQLException {
        return getTopFiveCategoriesByAmountAndType(userId, Transaction.TransactionType.income);
    }

    public boolean updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transaction_tracker SET category_id = ?,payment_method_id = ?,amount = ?,transaction_type = ?::transaction_type WHERE id = ? AND app_user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getCategoryId());
            pstmt.setInt(2, transaction.getPaymentMethodId());
            pstmt.setBigDecimal(3, transaction.getAmount());
            pstmt.setString(4, transaction.getTransactionType().toString());
            pstmt.setInt(5, transaction.getTransactionId());
            pstmt.setInt(6, transaction.getAppuserId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteTransaction(int transactionId, int userId) throws SQLException {
        String sql = "DELETE FROM transaction_tracker WHERE id = ? AND app_user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private List<Transaction> getTransactionsByUserIdAndType(int userId, Transaction.TransactionType type) throws SQLException {
        String sql = "SELECT * FROM transaction_tracker WHERE app_user_id = ? AND transaction_type = ?::transaction_type";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, type.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    private List<Transaction> getTransactionsByCategoryAndType(int userId, int categoryId, Transaction.TransactionType type) throws SQLException {
        String sql = "SELECT * FROM transaction_tracker WHERE app_user_id = ? AND category_id = ? AND transaction_type = ?::transaction_type";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, categoryId);
            pstmt.setString(3, type.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    private List<Transaction> getTopFiveTransactionsByAmountAndType(int userId, Transaction.TransactionType type) throws SQLException {
        String sql = "SELECT * FROM transaction_tracker WHERE app_user_id = ? AND transaction_type = ?::transaction_type ORDER BY amount DESC LIMIT 5";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, type.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    private List<CategoryAmount> getTopFiveCategoriesByAmountAndType(int userId, Transaction.TransactionType type) throws SQLException {
        String sql = "SELECT c.id category_id, c.name category_name, SUM(amount) total_amount FROM transaction_tracker t join category c on t.category_id = c.id WHERE t.app_user_id = ? AND t.transaction_type = ?::transaction_type GROUP BY c.id ORDER BY total_amount DESC LIMIT 5";
        List<CategoryAmount> categories = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, type.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(new CategoryAmount(rs.getInt("category_id"), rs.getString("category_name"), rs.getBigDecimal("total_amount")));
                }
            }
        }
        return categories;
    }

    private Transaction map(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("id"));
        t.setCategoryId(rs.getInt("category_id"));
        t.setAppuserId(rs.getInt("app_user_id"));
        t.setPaymentMethodId(rs.getInt("payment_method_id"));
        t.setAmount(rs.getBigDecimal("amount"));
        t.setTransactionType(Transaction.TransactionType.valueOf(rs.getString("transaction_type")));
        t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return t;
    }

}
