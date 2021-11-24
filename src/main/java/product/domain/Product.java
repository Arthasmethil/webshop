package product.domain;

import application.Utils;

import java.util.Objects;

public class Product {
    private Long id;
    private String productName;
    private String category;
    private Double price;
    
    public Product() {}
    
    public Product(String productName, String category, Double price) {
        this.productName = productName;
        this.category = category;
        this.price = price;
    }
    
    public Product(Long id, String productName, String category, Double price) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public static Product createProduct (String productData) {
        boolean matcher = productData.matches("\\w*[a-zA-Zа-яА-Я](.[0-9])?\\s+\\w*[a-zA-Zа-яА-Я](.[0-9])?\\s+[0-9]{1,22}");
        if (!matcher) {
            System.out.println("Wrong data, try again");
            return createProduct(Utils.simpleStringConsoleInput());
        }
        String[] nameCategoryPrice = productData.split(" ");
        if (nameCategoryPrice[0].length() > 64 || nameCategoryPrice[1].length() > 64 || nameCategoryPrice[2].length() > 22) {
            System.out.println("Use only letters for name/category (length <= 64 signs) and digits for price(length < 22 signs)");
            return createProduct(Utils.simpleStringConsoleInput());
        }
        return new Product(
                nameCategoryPrice[0],
                nameCategoryPrice[1],
                Double.parseDouble(nameCategoryPrice[2])
        );
    }
    
    @Override
    public String toString() {
        return "Item id: " + id +
                ", device: '" + productName + '\'' +
                ", category: '" + category + '\'' +
                ", price: " + price +
                '$';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && productName.equals(product.productName) && category.equals(product.category) && price.equals(product.price);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, productName, category, price);
    }
}
