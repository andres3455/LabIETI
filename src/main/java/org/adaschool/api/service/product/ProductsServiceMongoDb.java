package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;
import org.adaschool.api.repository.product.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceMongoDb implements ProductsService {

    private final ProductMongoRepository productMongoRepository;

    @Autowired
    public ProductsServiceMongoDb(ProductMongoRepository productMongoRepository) {
        this.productMongoRepository = productMongoRepository;
    }

    @Override
    public Product save(Product product) {
        //TODO implement this method
        return null;
    }

    @Override
    public Optional<Product> findById(String id) {
        //TODO implement this method
        return Optional.empty();
    }

    @Override
    public List<Product> all() {
        //TODO implement this method
        return null;
    }

    @Override
    public void deleteById(String id) {
        //TODO implement this method
    }

    @Override
    public Product update(Product product, String productId) {
        //TODO implement this method
        return null;
    }
}
