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
        return productMongoRepository.save(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return productMongoRepository.findById(id);
    }

    @Override
    public List<Product> all() {
        return productMongoRepository.findAll();
    }

    @Override
    public Product deleteById(String id) {
        Optional<Product> product = productMongoRepository.findById(id);
        product.ifPresent(p -> productMongoRepository.deleteById(id));
        return null;
    }

    @Override
    public Product update(Product product, String productId) {
        Optional<Product> existingProduct = productMongoRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            // copiamos los datos del nuevo objeto al existente
            productToUpdate.setName(product.getName());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setTags(product.getTags());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setImageUrl(product.getImageUrl());
            return productMongoRepository.save(productToUpdate);
        } else {
            throw new RuntimeException("Product with id " + productId + " not found");
        }
    }
}
