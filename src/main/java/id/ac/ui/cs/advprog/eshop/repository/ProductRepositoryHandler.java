package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryHandler extends RepositoryHandler<Product>{
    @Override
    public Product update(ShopRepository<Product> shopRepository, Product updatedProduct) {
        Product product = shopRepository.findById(updatedProduct.getId());

        if (product == null) return null;

        product.setName(updatedProduct.getName());
        product.setQuantity(updatedProduct.getQuantity());
        return product;
    }
}
