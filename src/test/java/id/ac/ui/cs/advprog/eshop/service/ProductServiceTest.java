package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(productRepository.create(product)).thenReturn(product);
        Product created = productService.create(product);

        assertNotNull(created.getProductId());
        assertEquals(product.getProductId(), created.getProductId());

        assertEquals(0, productRepository.findIndexById(product.getProductId()));
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        Product product3 = new Product();
        product3.setProductId("2ff6edb4-5527-4b17-bbdf-92724b61864b");
        product3.setProductName("Sampo Cap Asap");
        product3.setProductQuantity(25);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
        assertEquals(product3, result.get(2));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        when(productRepository.findIndexById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(2);

        when(productRepository.findProductByIndex(2)).thenReturn(product);

        assertEquals(product, productService.findProductById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        verify(productRepository, times(1)).findIndexById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        verify(productRepository, times(1)).findProductByIndex(2);
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        assertEquals(product, productService.edit(product));
        verify(productRepository, times(1)).editProduct(product);
    }

    @Test
    void testDelete() {
        productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        verify(productRepository, times(1)).deleteProduct("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
}
