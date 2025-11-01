package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import context.AppContext;
import model.dto.PaymentMethod;

public class PaymentMethodDAO {

    private static final Connection conn = AppContext.getDBConnection();
    private static PaymentMethodDAO paymentMethodDAO;

    private PaymentMethodDAO() {

    }

    public static PaymentMethodDAO getInstance() {
        if (paymentMethodDAO == null) {
            paymentMethodDAO = new PaymentMethodDAO();
        }
        return paymentMethodDAO;
    }

    public int createPayementMethod(PaymentMethod paymentMethod) throws SQLException {
        String SQL = "INSERT INTO payment_method (name) VALUES (?) ";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, paymentMethod.getName());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Payment method creation failed. No Id obtained");
            }
        }
    }

    public PaymentMethod getPaymentMethodById(int paymentMethodId) throws SQLException {
        String SQL = "SELECT * FROM payment_method WHERE payment_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, paymentMethodId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<PaymentMethod> getAllPaymentMethods() throws SQLException {
        String SQL = "SELECT * FROM payment_method";
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                paymentMethods.add(map(rs));
            }
        }
        return paymentMethods;
    }

    public boolean updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        String SQL = "UPDATE payment_method SET name = ? WHERE payment_method_id = ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, paymentMethod.getName());
            pstmt.setInt(2, paymentMethod.getPaymentMethodId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deletePaymentMethod(int paymentMethodId) throws SQLException {
        String SQL = "DELETE FROM payment_method WHERE payment_method_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, paymentMethodId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private PaymentMethod map(ResultSet rs) throws SQLException {
        PaymentMethod pm = new PaymentMethod();
        pm.setPaymentMethodId(rs.getInt(1));
        pm.setName(rs.getString("name"));
        return pm;
    }
}
