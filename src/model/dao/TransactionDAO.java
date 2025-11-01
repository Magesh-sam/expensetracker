package model.dao;

import context.AppContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.pojo.Transaction;

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
