package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    OrderRepository orderRepository;
    List<Payment> payments;
    List<Order> orders;
    Map<String,String> paymentData1;
    Map<String,String> paymentData2;

    @BeforeAll
    static void mocking() {
        UUID mockUUID = UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b");
        mockStatic(UUID.class);
        when(UUID.randomUUID()).thenReturn(mockUUID);
    }

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();
        paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment successPayment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData1,
                PaymentStatus.SUCCESS.getValue());
        payments.add(successPayment1);
        Map<String,String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678");
        Payment rejectedPayment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData,
                PaymentStatus.REJECTED.getValue());
        payments.add(rejectedPayment1);
        paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "BCA");
        paymentData2.put("referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2");


        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order2);
    }

    @Test
    void testAddPaymentSuccessful() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        order.setStatus(OrderStatus.SUCCESS.getValue());
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.addPayment(orders.get(0), payment.getMethod(), payment.getPaymentData());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(order.getId(), paymentService.getPaymentMapping().get(payment.getId()));
    }
    @Test
    void testAddPaymentRejected() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());
        order.setStatus(OrderStatus.FAILED.getValue());

        Payment result = paymentService.addPayment(orders.get(1), payment.getMethod(), payment.getPaymentData());
        assertEquals(order.getId(), paymentService.getPaymentMapping().get(payment.getId()));
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(PaymentStatus.REJECTED.getValue(),result.getStatus());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testAddPaymentNoSuchOrder() {
        Payment payment = payments.get(1);
        Order order = new Order("zczc", orders.get(0).getProducts(), orders.get(0).getOrderTime(), orders.get(0).getAuthor());
        doReturn(null).when(orderRepository).findById(order.getId());
        assertThrows(NoSuchElementException.class,
                () -> paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData()));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }


    @Test
    void testCreatePaymentByVoucherCodeWrongLength() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData1.put("voucherCode", "ESHOP12345678");
        Payment payment = paymentService.addPayment(orders.get(0), "voucherCode", paymentData1);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP12345678", payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeDoesNotStartWithEshop() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData1.put("voucherCode", "MOUNTAIN12345678");
        Payment payment = paymentService.addPayment(orders.get(0), "voucherCode", paymentData1);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("MOUNTAIN12345678", payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeWrongAmountOfDigits() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData1.put("voucherCode", "ESHOP1234ABCD567");
        Payment payment = paymentService.addPayment(orders.get(0), "voucherCode", paymentData1);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("ESHOP1234ABCD567", payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeLowered() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData1.put("voucherCode", "eshop1234abc5678");
        Payment payment = paymentService.addPayment(orders.get(0), "voucherCode", paymentData1);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertEquals("eshop1234abc5678", payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherCodeNullVoucher() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData1.put("voucherCode", null);
        Payment payment = paymentService.addPayment(orders.get(0), "voucherCode", paymentData1);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("voucherCode", payment.getMethod());
        assertNull(payment.getPaymentData().get("voucherCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByVoucherNotInData() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(orders.get(0), "voucherCode", paymentData2));
    }

    @Test
    void testCreatePaymentByBankTransferCorrect() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        Payment payment = paymentService.addPayment(orders.get(0), "bankTransfer", paymentData2);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("bankTransfer", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("9b420ba0-8a05-4fe6-9810-6fd6be40cbb2", payment.getPaymentData().get("referenceCode"));
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }
    @Test
    void testCreatePaymentByBankTransferEmptyBankName() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData2.put("bankName", "");
        Payment payment = paymentService.addPayment(orders.get(0), "bankTransfer", paymentData2);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("bankTransfer", payment.getMethod());
        assertEquals("", payment.getPaymentData().get("bankName"));
        assertEquals("9b420ba0-8a05-4fe6-9810-6fd6be40cbb2", payment.getPaymentData().get("referenceCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByBankTransferNullBankName() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData2.put("bankName", null);
        Payment payment = paymentService.addPayment(orders.get(0), "bankTransfer", paymentData2);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("bankTransfer", payment.getMethod());
        assertNull(payment.getPaymentData().get("bankName"));
        assertEquals("9b420ba0-8a05-4fe6-9810-6fd6be40cbb2", payment.getPaymentData().get("referenceCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
    @Test
    void testCreatePaymentByBankTransferEmptyReferenceCode() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData2.put("referenceCode", "");
        Payment payment = paymentService.addPayment(orders.get(0), "bankTransfer", paymentData2);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("bankTransfer", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("", payment.getPaymentData().get("referenceCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByBankTransferNullReferenceCode() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentData2.put("referenceCode", null);
        Payment payment = paymentService.addPayment(orders.get(0), "bankTransfer", paymentData2);
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("bankTransfer", payment.getMethod());
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertNull(payment.getPaymentData().get("referenceCode"));
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentByBankTransferNoBank() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("referenceCode", "9b420ba0-8a05-4fe6-9810-6fd6be40cbb2");
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(orders.get(0), "bankTransfer", paymentData));
    }
    @Test
    void testCreatePaymentByBankTransferNoReferenceCode() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(orders.get(0), "bankTransfer", paymentData));
    }

    @Test
    void testCreatePaymentInvalidMethodName() {
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.addPayment(orders.get(0), "bank", paymentData2));
    }

    @Test
    void testSetStatusToSuccess(){
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        order.setStatus(OrderStatus.SUCCESS.getValue());
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentService.addPayment(order, payment.getMethod(),payment.getPaymentData());
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(order.getId());


        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).getPayment(payment.getId());
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderRepository, times(2)).save(order);
    }

    @Test
    void testSetStatusToReject(){
        Payment payment = payments.get(0);
        Order order = orders.get(0);
        order.setStatus(OrderStatus.FAILED.getValue());
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        doReturn(order).when(orderRepository).findById(order.getId());
        paymentService.addPayment(order, payment.getMethod(),payment.getPaymentData());
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(order);


        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).getPayment(payment.getId());
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderRepository, times(2)).save(order);
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "MEOW"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidPaymentId() {
        doReturn(null).when(paymentRepository).getPayment("zczc");
        Payment payment = new Payment("zczc", "voucherCode", paymentData1);
        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus(payment, OrderStatus.SUCCESS.getValue()));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentIfPaymentExists() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfPaymentDoesNotExist() {
        doReturn(null).when(paymentRepository).getPayment("zczc");
        assertNull(paymentService.getPayment("zczc"));
    }

    @Test
    void testGetAllPayment() {
        doReturn(payments).when(paymentRepository).getAllPayments();
        List<Payment> results = paymentService.getAllPayments();
        for (int i = 0; i < payments.size(); i++) {
            assertEquals(payments.get(i).getId(), results.get(i).getId());
        }
        assertEquals(2,results.size());
    }
}
