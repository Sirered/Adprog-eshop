package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    Order order;

    @Test
    void testCreatePaymentByVoucherCodeCorrect() {
        Map<String, String> paymentData = Map.of(
                "voucherCode", "ESHOP1234ABC5678"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeWrongLength() {
        Map<String, String> paymentData = Map.of(
                "voucherCode", "ESHOP12345678"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP12345678", payment.getPaymentData().get("voucherCode"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeDoesNotStartWithEshop() {
        Map<String, String> paymentData = Map.of(
                "voucherCode", "MOUNTAIN12345678"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("MOUNTAIN12345678", payment.getPaymentData().get("voucherCode"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeWrongAmountOfDigits() {
        Map<String, String> paymentData = Map.of(
                "voucherCode", "ESHOP1234ABCD567"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABCD567", payment.getPaymentData().get("voucherCode"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeLowered() {
        Map<String, String> paymentData = Map.of(
                "voucherCode", "ESHOP1234ABCD567".toLowerCase()
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABCD567".toLowerCase(), payment.getPaymentData().get("voucherCode"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherNoVoucher() {
        Map<String, String> paymentData = Map.of(
                "bankName", "BCA",
                "referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2"
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData));
    }

    @Test
    void testCreatePaymentByBankTransferCorrect() {
        Map<String, String> paymentData = Map.of(
                "bankName", "BCA",
                "referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bankTransfer", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("9b420ba0-8a05-4fe6-9810-6fd6be40cbb2", payment.getPaymentData().get("referenceCode"));
        assertEquals("SUCCESS", payment.getStatus());
    }
    @Test
    void testCreatePaymentByBankTransferEmptyBankName() {
        Map<String, String> paymentData = Map.of(
                "bankName", "",
                "referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bankTransfer", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("", payment.getPaymentData().get("bankName"));
        assertEquals("9b420ba0-8a05-4fe6-9810-6fd6be40cbb2", payment.getPaymentData().get("referenceCode"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByBankTransferNullBankName() {
        Map<String, String> paymentData = Map.of(
                "bankName", null,
                "referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bankTransfer", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertNull(payment.getPaymentData().get("bankName"));
        assertEquals("9b420ba0-8a05-4fe6-9810-6fd6be40cbb2", payment.getPaymentData().get("referenceCode"));
        assertEquals("REJECTED", payment.getStatus());
    }
    @Test
    void testCreatePaymentByBankTransferEmptyReferenceCode() {
        Map<String, String> paymentData = Map.of(
                "bankName", "BCA",
                "referenceCode", ""
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bankTransfer", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("", payment.getPaymentData().get("referenceCode"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByBankTransferNullReferenceCode() {
        Map<String, String> paymentData = Map.of(
                "bankName", "BCA",
                "referenceCode", null
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bankTransfer", paymentData);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertNull(payment.getPaymentData().get("referenceCode"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentByBankTransferNoBank() {
        Map<String, String> paymentData = Map.of(
                "referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2"
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bankTransfer", paymentData));
    }
    @Test
    void testCreatePaymentByBankTransferNoReferenceCode() {
        Map<String, String> paymentData = Map.of(
                "bankName", "BCA"
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bankTransfer", paymentData));
    }

    @Test
    void testCreatePaymentInvalidMethodName() {
        Map<String, String> paymentData = Map.of(
                "bankName", "BCA",
                "referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2"
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Payment("13652556-012a-4c07-b546-54eb1396d79b", "bank", paymentData));
    }

    @Test
    void testSetStatusToSuccess() {
        Map<String, String> paymentData = Map.of(
                "voucherCode", "ESHOP1234ABCD567".toLowerCase()
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData);
        assertEquals("REJECTED", payment.getStatus());
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusInvalidStatus() {
        Map<String, String> paymentData = Map.of(
                "voucherCode", "ESHOP1234ABCD567"
        );
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData);
        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("MEOW"));
    }
}
