package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @MockBean
    private CarSellerService carService;

    @Mock
    private SellerService<Product> service;

    @Mock
    private Model model;

    @MockBean
    private ProductSellerServiceImpl productService;

    @Nested
    @DisplayName("GET Requests")
    class GetRequests{
        @Test
        void testCreateProductPage() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.get("/product/create"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("CreateProduct"))
                    .andExpect(MockMvcResultMatchers.model().attributeExists("product"));
        }

        @Test
        void testProductListPage() throws Exception {
            Product product1 = new Product();
            product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
            product1.setName("Sampo Cap Bambang");
            product1.setQuantity(100);

            Product product2 = new Product();
            product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
            product2.setName("Sampo Cap Usep");
            product2.setQuantity(50);

            Product product3 = new Product();
            product3.setId("2ff6edb4-5527-4b17-bbdf-92724b61864b");
            product3.setName("Sampo Cap Asap");
            product3.setQuantity(25);

            List<Product> productList = new ArrayList<>();
            productList.add(product1);
            productList.add(product2);
            productList.add(product3);

            when(productService.findAll()).thenReturn(productList);

            mockMvc.perform(MockMvcRequestBuilders.get("/product/list"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("ProductList"))
                    .andExpect(MockMvcResultMatchers.model().attribute("products", productList));

            verify(productService, times(1)).findAll();
        }

        @Test
        void testEditProductPage() throws Exception {
            Product product = new Product();
            product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
            product.setName("Sampo Cap Bambang");
            product.setQuantity(100);

            when(productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);

            mockMvc.perform(MockMvcRequestBuilders.get("/product/edit")
                            .param("productId", "eb558e9f-1c39-460e-8860-71af6af63bd6")
                    ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("EditProduct"))
                    .andExpect(MockMvcResultMatchers.model().attribute("product", product));

            verify(productService, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        }
    }

    @Nested
    @DisplayName("POST Requests")
    class PostRequests{
        @Test
        void testDelete() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.post("/product/delete")
                            .param("productId", "eb558e9f-1c39-460e-8860-71af6af63bd6")
                    ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl("list"));

            verify(productService, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        }
        @Test
        void createProductPost() throws Exception {
            Product product = new Product();
            product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
            product.setName("Sampo Cap Bambang");
            product.setQuantity(100);

            mockMvc.perform(MockMvcRequestBuilders.post("/product/create")
                    .flashAttr("product", product)
            ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl("list"));

            verify(productService, times(1)).create(product);
        }

        @Test
        void editProductPost() throws Exception {
            Product product = new Product();
            product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
            product.setName("Sampo Cap Bambang");
            product.setQuantity(100);

            mockMvc.perform(MockMvcRequestBuilders.post("/product/edit")
                            .flashAttr("product", product)
                    ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl("list"));

            verify(productService, times(1)).update(product);
        }

    }
}