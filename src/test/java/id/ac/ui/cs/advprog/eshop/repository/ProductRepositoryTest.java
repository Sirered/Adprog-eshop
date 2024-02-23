package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditIfOnlyOne() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId(product1.getId());
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.editProduct(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        assertEquals(product2.getId(), savedProduct.getId());
        assertEquals(product2.getName(), savedProduct.getName());
        assertEquals(product2.getQuantity(), savedProduct.getQuantity());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditIfThereAreOthers() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setId(product1.getId());
        product3.setName("Sampo Cap Asap");
        product3.setQuantity(25);
        productRepository.editProduct(product3);

        Iterator<Product> productIterator = productRepository.findAll();

        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        assertEquals(product3.getId(), savedProduct.getId());
        assertEquals(product3.getName(), savedProduct.getName());
        assertEquals(product3.getQuantity(), savedProduct.getQuantity());

        assertTrue(productIterator.hasNext());
        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertEquals(product2.getName(), savedProduct.getName());
        assertEquals(product2.getQuantity(), savedProduct.getQuantity());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteIfOnlyOne() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        productRepository.deleteProduct(product.getId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteIfMultiple() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setId("2ff6edb4-5527-4b17-bbdf-92724b61864b");
        product3.setName("Sampo Cap Asap");
        product3.setQuantity(25);
        productRepository.create(product3);

        productRepository.deleteProduct(product2.getId());

        Iterator<Product> productIterator = productRepository.findAll();

        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        assertEquals(product1.getName(), savedProduct.getName());
        assertEquals(product1.getQuantity(), savedProduct.getQuantity());

        assertTrue(productIterator.hasNext());
        savedProduct = productIterator.next();
        assertEquals(product3.getId(), savedProduct.getId());
        assertEquals(product3.getName(), savedProduct.getName());
        assertEquals(product3.getQuantity(), savedProduct.getQuantity());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindIndexById() {
        Product product1 = new Product();
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setName("Sampo Cap Asap");
        product3.setQuantity(25);
        productRepository.create(product3);

        assertEquals(0, productRepository.findIndexById(product1.getId()));
        assertEquals(1, productRepository.findIndexById(product2.getId()));
        assertEquals(2, productRepository.findIndexById(product3.getId()));
        assertEquals(-1, productRepository.findIndexById("eb558e9f-nop3-r34l-8860-71af6af63bd6"));
    }

    @Test
    void testFindProductByIndex() {
        Product product1 = new Product();
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setName("Sampo Cap Asap");
        product3.setQuantity(25);
        productRepository.create(product3);

        assertEquals(product1, productRepository.findProductByIndex(0));
        assertEquals(product2, productRepository.findProductByIndex(1));
        assertEquals(product3, productRepository.findProductByIndex(2));
    }
}
