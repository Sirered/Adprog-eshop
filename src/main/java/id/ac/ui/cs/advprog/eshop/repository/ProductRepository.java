package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        product.setId(String.valueOf(UUID.randomUUID()));
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public int findIndexById(String id) {
        for (int i = 0; i < productData.size(); i++) {
            Product product = productData.get(i);

            if (product.getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public Product findProductByIndex(int index) {
        return productData.get(index);
    }

    public Product editProduct(Product product) {
        String id = product.getId();
        int index = findIndexById(id);
        productData.set(index, product);
        return product;
    }

    public void deleteProduct(String id) {
        int index = findIndexById(id);
        productData.remove(index);
    }
}
