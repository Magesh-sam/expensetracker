package model.dao;

import context.AppContext;
import interfaces.IPaymentMethodDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.dto.PaymentMethod;

public class PaymentMethodDAO implements IPaymentMethodDAO {

    private static final Connection conn = AppContext.getDBConnection();
    private static IPaymentMethodDAO paymentMethodDAO;

    private PaymentMethodDAO() {

    }

    public static IPaymentMethodDAO getInstance() {
        if (paymentMethodDAO == null) {
            paymentMethodDAO = new PaymentMethodDAO();
        }
        return paymentMethodDAO;
    }

    @Override
    public int createPaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        String sql = "INSERT INTO payment_method (name) VALUES (?) ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public PaymentMethod getPaymentMethodById(int paymentMethodId) throws SQLException {
        String sql = "SELECT * FROM payment_method WHERE payment_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentMethodId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() throws SQLException {
        String sql = "SELECT * FROM payment_method";
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                paymentMethods.add(map(rs));
            }
        }
        return paymentMethods;
    }

    @Override
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        String sql = "UPDATE payment_method SET name = ? WHERE payment_method_id = ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, paymentMethod.getName());
            pstmt.setInt(2, paymentMethod.getPaymentMethodId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deletePaymentMethod(int paymentMethodId) throws SQLException {
        String sql = "DELETE FROM payment_method WHERE payment_method_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
