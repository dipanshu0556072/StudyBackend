package com.example.demo.service.impl;

import com.example.demo.entity.Product;
import com.example.demo.entity.Size;
import com.example.demo.entity.User;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repo.ProductRepo;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    @Transactional
    public void createProductList(List<Product> products) {
        productRepo.saveAll(products);
    }

    //get all productList
    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    //get product by productId
    @Override
    public Product productById(Long productId) {
        return productRepo.findById(productId).orElse(null);
    }

    //update a product
    @Override
    public void updateByProductId(Long productId, Product product) {
        Optional<Product> targetProduct = productRepo.findById(productId);

        if (targetProduct.isPresent()) {
            Product product1 = targetProduct.get();

            if (product.getBrand() != null) {
                product1.setBrand(product.getBrand());
            }
            if (product.getProductCode() != null) {
                product1.setProductCode(product.getProductCode());
            }

            if (product.getCountry() != null) {
                product1.setCountry(product.getCountry());
            }
            productRepo.save(product1);
        }
    }

    //delete all products from database
    public void deleteAllProducts() {
        productRepo.deleteAll();
    }


    //delete product by productId
    public ResponseEntity<String> deleteProductByProductId(Long productId) {
        if (productRepo.findById(productId).isPresent()) {
            productRepo.deleteById(productId);
            return ResponseEntity.ok("Product deleted successfully from database with id:" + productId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in database with given id:" + productId);
        }
    }

    //search product is available at your pincode
    public ResponseEntity<String> searchProductByPincode(Long productId, String pincode) {
        if (productRepo.findById(productId).isPresent()) {
            Product product = productRepo.findById(productId).get();
            if (product.getPincode().contains(pincode)) {
                return ResponseEntity.accepted().body("Product is available on this pincode:" + pincode);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not available on this pincode:" + pincode);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in database with given id :" + productId);
        }
    }

    //check the stock count of the product by product color
    @Override
    public int checkProductStockCount(Long productId,String productColor){
        if(productRepo.findById(productId).isPresent()){
            Product product=productRepo.findById(productId).get();
            int totalQuantity=0;
            for(Size size:product.getSize()){
                 if(size.getColor().equalsIgnoreCase(productColor)){
                   totalQuantity+=size.getQuantity();
                 }
            }
            return totalQuantity;
        }else{
            throw new ProductNotFoundException("Product not found in database with given id :" + productId);
        }
    }
}
