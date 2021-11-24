package order.domain;


import java.util.*;

public class Order {
    private List<Long> orderIndexList = new ArrayList<>();
    private Long id;
    private Long idClient;
    private Long idProduct;
    private Long amount;
    private Double totalPrice;
    
    public Order() {
    }
    
    public Order(Long idClient, Long idProduct) {
        this.idClient = idClient;
        this.idProduct = idProduct;
    }
    
    public Order(Long idClient) {
        this.idClient = idClient;
        this.idProduct = null;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public List<Long> getOrderIndexList() {
        return orderIndexList;
    }
    
    public void setOrderIndexList(List<Long> orderIndexList) {
        this.orderIndexList = orderIndexList;
    }
    
    public Long getIdClient() {
        return idClient;
    }
    
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
    
    public Long getIdProduct() {
        return idProduct;
    }
    
    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }
    
    public Long getAmount() {
        return amount;
    }
    
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    
    public Double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    @Override
    public String toString() {
        return "order id: " + id + " client id: " + idClient +
                " -> id items: " + orderIndexList;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return idClient.equals(order.idClient);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idClient);
    }
}
