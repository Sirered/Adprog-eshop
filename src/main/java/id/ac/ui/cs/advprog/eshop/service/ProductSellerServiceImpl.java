package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.RepositoryHandler;
import id.ac.ui.cs.advprog.eshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductSellerServiceImpl implements SellerService<Product>{
    @Autowired
    private ShopRepository<Product> productRepository;

    @Autowired
    private RepositoryHandler<Product> repositoryHandler;

    @Override
    public Product create(Product product) {
        repositoryHandler.create(productRepository, product);
        return product;
    }


    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProducts = new ArrayList<>();
        productIterator.forEachRemaining(allProducts::add);
        return allProducts;
    }


    public Product findById(String id){
        Product product = productRepository.findById(id);
        return product;
    }


    public Product update(Product product) {
        repositoryHandler.update(productRepository, product);
        return product;
    }


    public void delete(String id) {
        repositoryHandler.delete(productRepository, id);
    }
}
