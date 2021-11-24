package order.dao;

import application.Utils;
import client.dao.ClientDaoImpl;
import client.domain.Client;
import order.domain.Order;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import product.dao.ProductDaoImpl;
import product.domain.Product;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderDaoImplTest {
    Connection connection;
    OrderDaoImpl orderDao;
    ClientDaoImpl clientDao;
    ProductDaoImpl productDao;
    
    @BeforeMethod
    public void setUp() throws Exception {
        connection = Utils.openConnection();
        orderDao = new OrderDaoImpl(connection);
        clientDao = new ClientDaoImpl(connection);
        productDao = new ProductDaoImpl(connection);
    }
    
    @Test
    public void getTest() {
        List<Order> orderList = orderDao.getAll();
        List<Long> orderIdList = new ArrayList<>();
        for (Order order: orderList) {
            orderIdList.add(order.getId());
        }
        Order order = orderDao.get(orderIdList.get((int)(Math.random() * orderIdList.size())));
        assertEquals("Order isn't correct", order.getId(), orderDao.get(order.getId()).getId());
    }
    
    @Test
    public void testGetAllOrderClient() {
        List<Order> orderList = orderDao.getAll();
        List<Long> productIdList = new ArrayList<>();
        for (Order order: orderList) {
            productIdList.add(order.getId());
        }
        Order order = orderDao.get(productIdList.get((int)(Math.random() * productIdList.size())));
        List<Long> amountOfProductsInOrder = orderDao.getAllOrderClient(order.getIdClient()).getOrderIndexList();
        int amountOfProducts = 0;
        for (Order orderCheck: orderList) {
            if (orderCheck.getIdClient() == order.getIdClient()) {
                amountOfProducts++;
            }
        }
        assertEquals(amountOfProducts, amountOfProductsInOrder.size());
    }
    
    @Test
    public void testGetAll() {
        int size = orderDao.getAll().size();
        assertTrue("Size isn't correct",size>0);
    }
    
    @Test
    public void testUpdate() {
        List<Product> productList = productDao.getAll();
        List<Long> productIdList = new ArrayList<>();
        for (Product product: productList) {
            productIdList.add(product.getId());
        }
        Product product = productDao.get(productIdList.get((int)(Math.random() * productIdList.size())));
        List<Order> orderList = orderDao.getAll();
        List<Long> orderIdList = new ArrayList<>();
        for (Order order: orderList) {
            orderIdList.add(order.getId());
        }
        Order order = orderDao.get(orderIdList.get((int)(Math.random() * orderIdList.size())));
        Order orderUpdated = new Order(order.getIdClient(), product.getId());
        assertTrue(orderDao.update(order.getId(), orderUpdated));
    }
    
    @Test
    public void testGetClientWithProducts() {
        List<Order> allOrders = orderDao.getAllOrderGroupByClient();
        List<Long> clientIdList = new ArrayList<>();
        for (Order order: allOrders) {
            clientIdList.add(order.getIdClient());
        }
        Order orderByClient = orderDao.getAllOrderClient(clientIdList.get((int)(Math.random() * clientIdList.size())));
        Map<Client, List<Product>> clientWithProducts = orderDao.getClientWithProducts(orderByClient);
        List<Long> createdData = orderByClient.getOrderIndexList();
        List<Product> values = clientWithProducts.get(clientDao.get(orderByClient.getIdClient()));
        List<Long> toCheckedData = new ArrayList<>();
        for (Product product:values) {
            if (product == null) {
                toCheckedData.add(0L);
            } else {
                toCheckedData.add(product.getId());
            }
        }
        assertEquals(createdData, toCheckedData);
    }

    @AfterMethod
    public void tearDown() {
        Utils.closeConnection(connection);
    }
}