package interfaces;

import java.sql.SQLException;
import java.util.List;

import model.dto.PaymentMethod;

public interface IPaymentMethodDAO {

    int createPaymentMethod(PaymentMethod paymentMethod) throws SQLException;

    PaymentMethod getPaymentMethodById(int paymentMethodId) throws SQLException;

    List<PaymentMethod> getAllPaymentMethods() throws SQLException;

    boolean updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException;

    boolean deletePaymentMethod(int paymentMethodId) throws SQLException;

}