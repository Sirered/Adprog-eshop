package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment successPayment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "voucherCode", paymentData1);
        payments.add(successPayment1);
        paymentData1.put("voucherCode", "ESHOP12345678");
        Payment rejectedPayment1 = new Payment("ed2a3070-1c4c-4cb9-81c2-44ca6cfb1c63", "voucherCode", paymentData1);
        payments.add(rejectedPayment1);

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
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order2);
    }

    @Test
    void testAddPaymentSuccessful() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(payment);
        Order order = orders.get(0);
        doReturn(order).when(orderRepository).findById(order.getId());
        order.setStatus(OrderStatus.SUCCESS.getValue());
        UUID mockUUID = UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b");
        mockStatic(UUID.class);
        when(UUID.randomUUID()).thenReturn(mockUUID);
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.addPayment(orders.get(0), payment.getMethod(), payment.getPaymentData());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
        assertEquals(payment.getId(), result.getId());
        assertEquals(order.getId(), paymentService.getPaymentMapping().get(payment.getId()));
    }
    @Test
    void testAddPaymentRejected() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(payment);
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());
        order.setStatus(OrderStatus.FAILED.getValue());
        UUID mockUUID = UUID.fromString("ed2a3070-1c4c-4cb9-81c2-44ca6cfb1c63");
        mockStatic(UUID.class);
        when(UUID.randomUUID()).thenReturn(mockUUID);
        doReturn(order).when(orderRepository).save(order);

        Payment result = paymentService.addPayment(orders.get(1), payment.getMethod(), payment.getPaymentData());
        assertEquals(order.getId(), paymentService.getPaymentMapping().get(payment.getId()));
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testAddPaymentNoSuchOrder() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        doReturn(null).when(orderRepository).findById(order.getId());
        assertThrows(NoSuchElementException.class,
                () -> paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData()));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess(){
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        order.setStatus(OrderStatus.SUCCESS.getValue());
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(order);


        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusToReject(){
        Payment payment = payments.get(0);
        Order order = orders.get(0);
        order.setStatus(OrderStatus.FAILED.getValue());
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).save(order);


        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Payment payment = payments.get(1);
        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment.getId(), "MEOW"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidPaymentId() {
        doReturn(null).when(paymentRepository).getPayment("zczc");

        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus("zczc", OrderStatus.SUCCESS.getValue()));

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
