package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/Product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProductList(@RequestBody List<Product> products) {
        productService.createProductList(products);
        return new ResponseEntity<>("Product List created successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/productList")
    public List<Product> getProductList() {
        return productService.getAllProducts();
    }

    //get product by product id
    @GetMapping("/productId={productId}")
    public ResponseEntity<Object> getProductById(@PathVariable("productId") Long productId) {
        Product product = productService.productById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with product Id:" + productId);
        }
    }

    //update a product by productId
    @PutMapping("/productIdForUpdate={productId}")
    public ResponseEntity<String> updateProduct(@RequestBody Product product, @PathVariable("productId") Long productId) {
        if (productService.productById(productId) != null) {
            productService.updateByProductId(productId, product);
            return ResponseEntity.accepted().body("Product updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with Id:" + productId);
        }
    }

    //delete all products from database
    @DeleteMapping("/delete/deleteAllProducts")
    public ResponseEntity<String> deleteUsers() {
        productService.deleteAllProducts();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted all products from the database successfully!");
    }

    //Delete product by productId from database
    @DeleteMapping("/delete/productId={productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable("productId") Long productId) {
        return productService.deleteProductByProductId(productId);
    }

    //check product is available at pincode
    @GetMapping("/search/productId={productId}/pincode={pincode}")
    public ResponseEntity<String>searchProduct(@PathVariable("productId") Long productId,@PathVariable("pincode") String pincode){
        return productService.searchProductByPincode(productId,pincode);
    }

    //check the product by product color
    @GetMapping("/productId={productId}/productColor={productColor}")
    public int searchByProductColor(@PathVariable("productId") Long productId,@PathVariable("productColor") String productColor){
        return productService.checkProductStockCount(productId,productColor);
    }

}
