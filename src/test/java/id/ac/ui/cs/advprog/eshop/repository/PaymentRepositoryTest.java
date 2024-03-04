package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment successPayment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData1);
        payments.add(successPayment1);
        Payment rejectedPayment1 = new Payment("ed2a3070-1c4c-4cb9-81c2-44ca6cfb1c63", "bankTransfer", paymentData1);
        payments.add(rejectedPayment1);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.getPayment(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);
        Payment newPayment = new Payment(payment.getId(), payment.getMethod(), payment.getPaymentData());
        newPayment.setStatus(PaymentStatus.REJECTED.getValue());
        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.getPayment(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }

    @Test
    void testGetPaymentFound() {
        for (Payment payment: payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.getPayment(payments.get(2).getId());
        assertEquals(payments.get(2).getId(), findResult.getId());
        assertEquals(payments.get(2).getMethod(), findResult.getMethod());
        assertEquals(payments.get(2).getPaymentData(), findResult.getPaymentData());
        assertEquals(payments.get(2).getStatus(), findResult.getStatus());
    }

    @Test
    void testGetPaymentNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.getPayment("zczc");
        assertNull(findResult);
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.getAllPayments();
        assertEquals(2, paymentList.size());
        assertEquals(payments.get(1).getId(), paymentList.get(1).getId());
        assertEquals(payments.get(1).getMethod(), paymentList.get(1).getMethod());
        assertEquals(payments.get(1).getPaymentData(), paymentList.get(1).getPaymentData());
        assertEquals(payments.get(1).getStatus(), paymentList.get(1).getStatus());
        assertEquals(payments.get(2).getId(), paymentList.get(2).getId());
        assertEquals(payments.get(2).getMethod(), paymentList.get(2).getMethod());
        assertEquals(payments.get(2).getPaymentData(), paymentList.get(2).getPaymentData());
        assertEquals(payments.get(2).getStatus(), paymentList.get(2).getStatus());
    }
}
