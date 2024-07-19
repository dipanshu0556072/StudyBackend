package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "imageUrl")
    private List<String> imageUrl;

    @Column(name = "brand")
    private String brand;

    @Column(name = "title")
    private String title;

    @Column(name = "discountedPrice")
    private String discountedPrice;

    @Column(name = "price")
    private String price;

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    private List<Size> size;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "topLevelCategory")
    private String topLevelCategory;

    @Column(name = "secondLevelCategory")
    private String secondLevelCategory;

    @Column(name = "thirdLevelCategory")
    private String thirdLevelCategory;

    @Column(name = "description", length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "product_pincodes",joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "pincode")
    private List<String> pincode;

    @ElementCollection
    @CollectionTable(name = "product_countries",joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "country")
    private List<String> country;

    @Column(name = "wearType")
    private String wearType;

    @Column(name = "fabric")
    private String fabric;

    @Column(name = "fit")
    private String fit;

    @Column(name = "materialCare")
    private String materialCare;

    @Column(name = "productCode")
    private String productCode;

    @Column(name = "seller")
    private String seller;


    //contructor

    public Product() {
    }

    public Product(List<String> imageUrl, String brand, String title,String discountedPrice, String price, List<Size> size, int quantity, String topLevelCategory, String secondLevelCategory, String thirdLevelCategory, String description, List<String> pincode, List<String> country, String wearType, String fabric, String fit, String materialCare, String productCode, String seller) {
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.title = title;
        this.discountedPrice = discountedPrice;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.topLevelCategory = topLevelCategory;
        this.secondLevelCategory = secondLevelCategory;
        this.thirdLevelCategory = thirdLevelCategory;
        this.description = description;
        this.pincode = pincode;
        this.country = country;
        this.wearType = wearType;
        this.fabric = fabric;
        this.fit = fit;
        this.materialCare = materialCare;
        this.productCode = productCode;
        this.seller = seller;
    }

    //getter & setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTopLevelCategory() {
        return topLevelCategory;
    }

    public void setTopLevelCategory(String topLevelCategory) {
        this.topLevelCategory = topLevelCategory;
    }

    public String getSecondLevelCategory() {
        return secondLevelCategory;
    }

    public void setSecondLevelCategory(String secondLevelCategory) {
        this.secondLevelCategory = secondLevelCategory;
    }

    public String getThirdLevelCategory() {
        return thirdLevelCategory;
    }

    public void setThirdLevelCategory(String thirdLevelCategory) {
        this.thirdLevelCategory = thirdLevelCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPincode() {
        return pincode;
    }

    public void setPincode(List<String> pincode) {
        this.pincode = pincode;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public String getWearType() {
        return wearType;
    }

    public void setWearType(String wearType) {
        this.wearType = wearType;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getMaterialCare() {
        return materialCare;
    }

    public void setMaterialCare(String materialCare) {
        this.materialCare = materialCare;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
