package com.app.ecom.Service;

import com.app.ecom.Entity.Product;
import com.app.ecom.Repository.ProductRepository;
import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setCategory(savedProduct.getCategory());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setActive(savedProduct.getActive());
        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
    }

    public Optional<ProductResponse> updateProduct(ProductRequest productRequest, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }


    public List<ProductResponse> getAllProduct() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());

    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    if (existingProduct.getActive() == false) {
                        return false;
                    }
                    existingProduct.setActive(false);
                    productRepository.save(existingProduct);
                    return true;
                }).orElse(false);
    }

    public Optional<ProductResponse> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponse);

    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
