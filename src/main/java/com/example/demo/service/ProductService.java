package com.example.demo.service;

import com.example.demo.entity.Product;

import java.util.List;

public interface ProductService {
    void createProductList(List<Product> products);
    List<Product> getAllProducts();

    //get product by productId
    Product productById(Long targetId);

    //update a product by productId
    void updateByProductId(Long productId,Product product);
    //delete all products
    void deleteAllProducts();
    //search product is available at your pincode
    int checkProductStockCount(Long productId,String productColor);
}
