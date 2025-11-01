package model.dao;

import context.AppContext;
import model.dto.CategoryAmount;
import model.dto.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public int createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction (category_id,app_user_id,payment_method_id,amount,transaction_type) VALUES (?,?,?,?,?::transaction_type)";
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

    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transaction where transaction_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE app_user_id = ?";
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

    public List<Transaction> getAllTransactions() throws SQLException {
        String sql = "SELECT * FROM transaction";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                transactions.add(map(rs));
            }
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsByCategory(int categoryId) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE category_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsByPaymentMethod(int paymentMethodId) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE payment_method_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentMethodId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsByTransactionType(Transaction.TransactionType transactionType)
            throws SQLException {
        String sql = "SELECT * FROM transaction WHERE transaction_type = ?::transaction_type";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transactionType.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsByDate(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE DATE(created_at) = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE DATE(created_at) BETWEEN ? AND ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(startDate));
            pstmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsMonthAndYear(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE EXTRACT(YEAR FROM created_at) = ? AND EXTRACT(MONTH FROM created_at) = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, date.getYear());
            pstmt.setInt(2, date.getMonthValue());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(map(rs));
                }
            }
        }
        return transactions;
    }

    public List<Transaction> getTopFiveTransactionsByAmount() throws SQLException {
        String sql = "SELECT * FROM transaction ORDER BY amount DESC LIMIT 5";
        List<Transaction> topFiveTransactionsByAmount = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                topFiveTransactionsByAmount.add(map(rs));
            }
        }
        return topFiveTransactionsByAmount;
    }

    public List<CategoryAmount> getTopFiveCategoriesByAmount() throws SQLException {
        String sql = "SELECT c.id category_id, c.name category_name, SUM(amount) total_amount FROM transaction t join category c on t.category_id = c.id GROUP BY c.id ORDER BY total_amount DESC LIMIT 5";
        List<CategoryAmount> topFiveCategoriesByAmounts = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                topFiveCategoriesByAmounts.add(new CategoryAmount(rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("total_amount")));
            }
        }
        return topFiveCategoriesByAmounts;
    }

    public boolean updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE transaction SET category_id = ?,app_user_id = ?,payment_method_id = ?,amount = ?,transaction_type = ?::transaction_type WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getCategoryId());
            pstmt.setInt(2, transaction.getAppuserId());
            pstmt.setInt(3, transaction.getPaymentMethodId());
            pstmt.setBigDecimal(4, transaction.getAmount());
            pstmt.setString(5, transaction.getTransactionType().toString());
            pstmt.setInt(6, transaction.getTransactionId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteTransaction(int transactionId) throws SQLException {
        String sql = "DELETE FROM transaction WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Transaction map(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("id"));
        t.setCategoryId(rs.getInt("category_id"));
        t.setAppuserId(rs.getInt("app_user_id"));
        t.setPaymentMethodId(rs.getInt("payment_method_id"));
        t.setAmount(rs.getBigDecimal("amount"));
        t.setTransactionType(Transaction.TransactionType.valueOf(rs.getString("transaction_type")));
        t.setCreatedAt(rs.getTimestamp(7).toLocalDateTime());
        return t;
    }

}
