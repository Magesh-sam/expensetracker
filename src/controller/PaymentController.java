package controller;

import java.sql.SQLException;
import java.util.List;

import context.AppContext;
import model.dto.PaymentMethod;
import service.PaymentService;

public class PaymentController {
    private static final PaymentService paymentService = AppContext.getPaymentService();
    private static PaymentController paymentController;

    private PaymentController() {

    }

    public static PaymentController getInstance() {
        if (paymentController == null) {
            paymentController = new PaymentController();
        }
        return paymentController;
    }

    public int createPaymentMethod(PaymentMethod paymentMethod) {
        try {
            return paymentService.createPaymentMethod(paymentMethod);

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        try {
            return paymentService.getAllPaymentMethods();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    // public boolean updatePaymentMethod()

}
