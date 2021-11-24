package interfaces;

import client.domain.Client;
import order.domain.Order;
import product.domain.Product;

import java.util.List;
import java.util.Map;

public interface OrderDao extends Crud<Order>{
    Map<Client, List<Product>> getAllClientsWithProducts(List<Order> orderList);
    Map<Client, List<Product>> getClientWithProducts(Order order);
    Order getAllOrderClient(Long idClient);
    List<Order> getAllOrderGroupByClient();
    void getAllClientStatistic();
    void getShopStatistic();
}
