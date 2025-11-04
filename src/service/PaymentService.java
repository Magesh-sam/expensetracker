package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import context.AppContext;
import model.dao.PaymentMethodDAO;
import model.dto.PaymentMethod;
import util.Validation;

public class PaymentService {

    private static final PaymentMethodDAO paymentMethodDAO = AppContext.getPaymentMethodDAO();
    private static PaymentService paymentService;

    private PaymentService() {

    }

    public static PaymentService getInstance() {
        if (paymentService == null) {
            paymentService = new PaymentService();
        }
        return paymentService;
    }

    public int createPaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        Objects.requireNonNull(paymentMethod, "Payment Method cannot be null");
        validatePaymentMethod(paymentMethod);
        return paymentMethodDAO.createPaymentMethod(paymentMethod);
    }

    public PaymentMethod getPaymentMethodById(int paymentMethodId) throws SQLException {
        if (paymentMethodId <= 0) {
            throw new IllegalArgumentException("Invalid Payment Method id");
        }
        return paymentMethodDAO.getPaymentMethodById(paymentMethodId);
    }

    public List<PaymentMethod> getAllPaymentMethods() throws SQLException {
        return paymentMethodDAO.getAllPaymentMethods();
    }

    public boolean updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        Objects.requireNonNull(paymentMethod, "Payment Method cannot be null");
        validatePaymentMethod(paymentMethod);
        return paymentMethodDAO.updatePaymentMethod(paymentMethod);
    }

    public boolean deletePaymentMethod(int paymentMethodId) throws SQLException {
        if (paymentMethodId <= 0) {
            throw new IllegalArgumentException("Invalid Payment Method id");
        }
        return paymentMethodDAO.deletePaymentMethod(paymentMethodId);
    }

    private void validatePaymentMethod(PaymentMethod paymentMethod) {
        Objects.requireNonNull(paymentMethod, "Payment Method cannot be null");
        if (!Validation.isNonEmpty(paymentMethod.getName())) {
            throw new IllegalArgumentException("Payment Method name cannot be empty");
        }
        if (paymentMethod.getPaymentMethodId() <= 0) {
            throw new IllegalArgumentException("Invalid Payment Method id");
        }
    }

}
