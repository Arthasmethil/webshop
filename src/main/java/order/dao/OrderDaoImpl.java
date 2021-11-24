package order.dao;

import application.Utils;
import client.dao.ClientDaoImpl;
import client.domain.Client;
import interfaces.ClientDao;
import order.domain.Order;
import product.dao.ProductDaoImpl;
import product.domain.Product;

import java.sql.*;
import java.util.*;

public class OrderDaoImpl implements interfaces.OrderDao {
    
    Connection connection;
    
    public OrderDaoImpl(Connection connection) {
        this.connection = connection;
    }
    
    public Order get(Long id) {
        Order order = new Order();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `order` WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
                order.setIdClient(resultSet.getLong("idClient"));
                if (resultSet.getLong("idProduct") < 0) {
                    order.getOrderIndexList().add(0L);
                }
                order.getOrderIndexList().add(resultSet.getLong("idProduct"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
    
    public Order getAllOrderClient(Long idClient) {
        Order order = new Order();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `order` WHERE idClient=?");
            preparedStatement.setLong(1, idClient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order.setIdClient(resultSet.getLong("idClient"));
                if (resultSet.getLong("idProduct") < 0) {
                    order.getOrderIndexList().add(0L);
                }
                order.getOrderIndexList().add(resultSet.getLong("idProduct"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
    
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `order` ORDER BY id");
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setIdClient(resultSet.getLong("idClient"));
                if (resultSet.getLong("idProduct") < 1) {
                    order.getOrderIndexList().add(null);
                } else {
                    order.getOrderIndexList().add(resultSet.getLong("idProduct"));
                }
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
    
    public List<Order> getAllOrderGroupByClient() {
        List<Order> orderList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `order` ORDER BY idClient");
            while (resultSet.next()) {
                Order order = new Order();
                boolean duplicate = false;
                for (Order id : orderList) {
                    if (id.getIdClient() == resultSet.getLong("idClient")) {
                        if (resultSet.getLong("idProduct") < 1) {
                            id.getOrderIndexList().add(null);
                        } else {
                            id.getOrderIndexList().add(resultSet.getLong("idProduct"));
                        }
                        duplicate = true;
                    }
                }
                if (duplicate) {
                    duplicate = false;
                } else {
                    if (resultSet.getLong("idProduct") < 1) {
                        order.setIdClient(resultSet.getLong("idClient"));
                        order.getOrderIndexList().add(null);
                        orderList.add(order);
                    } else {
                        order.setIdClient(resultSet.getLong("idClient"));
                        order.getOrderIndexList().add(resultSet.getLong("idProduct"));
                        orderList.add(order);
                    }
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
    
    @Override
    public Boolean create(Order order) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO `order` (`idClient`, `idProduct`) VALUES (?,?)"
            );
            preparedStatement.setLong(1, order.getIdClient());
            preparedStatement.setLong(2, order.getIdProduct());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Boolean update(Long id, Order order) {
        try {
            if (order.getIdProduct() == null) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `order` SET idClient=?, idProduct=NULL WHERE id=?");
                preparedStatement.setLong(1, order.getIdClient());
                preparedStatement.setLong(2, id);
                int result = preparedStatement.executeUpdate();
                return result > 0;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `order` SET idClient=?, idProduct=? WHERE id=?");
            preparedStatement.setLong(1, order.getIdClient());
            preparedStatement.setLong(2, order.getIdProduct());
            preparedStatement.setLong(3, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Boolean delete(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `order` WHERE id =?");
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Map<Client, List<Product>> getAllClientsWithProducts(List<Order> orderList) {
        Map<Client, List<Product>> mapClientItems = new LinkedHashMap<>();
        for (Order order : orderList) {
            ClientDaoImpl clientDao = new ClientDaoImpl(this.connection);
            ProductDaoImpl productDao = new ProductDaoImpl(this.connection);
            Client client = clientDao.get(order.getIdClient());
            List<Product> productList = new ArrayList<>();
            if (order.getOrderIndexList().size() > 1) {
                for (Long index : order.getOrderIndexList()) {
                    if (index == null) {
                        productList.add(null);
                    } else {
                        productList.add(productDao.get(index));
                    }
                }
            } else if (order.getOrderIndexList().get(0) == null) {
                productList.add(null);
            } else {
                productList.add(productDao.get(order.getOrderIndexList().get(0)));
            }
            mapClientItems.put(client, productList);
        }
        return mapClientItems;
    }
    
    public Order createOrderManual () {
        ClientDao client = new ClientDaoImpl(this.connection);
        ProductDaoImpl product = new ProductDaoImpl(this.connection);
        System.out.println("Input client id");
        Long idClient = Utils.parseToLong(Utils.simpleStringConsoleInput());
        if (client.get(idClient).getId() == null) {
            System.out.println("Client with id:" + idClient + " doesn't exist");
            return createOrderManual();
        } else {
            System.out.println("Input product id, enter `0` to create order without products");
            Long idProduct = Utils.parseToLong(Utils.simpleStringConsoleInput());
            if (idProduct == 0) {
                return new Order(idClient);
            }
            if (product.get(idProduct).getId() == null) {
                System.out.println("Product with id:" + idProduct + " doesn't exist");
                return createOrderManual();
            } else {
                    return new Order(idClient, idProduct);
            }
        }
    }
    
    public Map<Client, List<Product>> getClientWithProducts(Order order) {
        Map<Client, List<Product>> clientProducts = new HashMap<>();
        ClientDaoImpl clientDao = new ClientDaoImpl(this.connection);
        ProductDaoImpl productDao = new ProductDaoImpl(this.connection);
        List<Product> productList = new ArrayList<>();
        Client client = clientDao.get(order.getIdClient());
        if (order.getOrderIndexList().size() > 1) {
            for (Long index : order.getOrderIndexList()) {
                productList.add(productDao.get(index));
            }
        } else {
            productList.add(productDao.get(order.getOrderIndexList().get(0)));
        }
        clientProducts.put(client, productList);
        return clientProducts;
    }
    
    @Override
    public void getAllClientStatistic() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT webshopdb.clients.id as id, \n" +
                    "webshopdb.clients.name as name, \n" +
                    "webshopdb.clients.lastname as lastname,\n" +
                    "COUNT(idProduct) as amount,  \n" +
                    "SUM(webshopdb.products.price) as total FROM webshopdb.order\n" +
                    "LEFT JOIN webshopdb.products ON webshopdb.order.idProduct = webshopdb.products.id\n" +
                    "LEFT JOIN webshopdb.clients ON webshopdb.order.idClient = webshopdb.clients.id \n" +
                    "GROUP BY idClient\n" +
                    "ORDER BY id");
            while (resultSet.next()) {
                String string = "id:" + resultSet.getLong("id") +
                        " " + resultSet.getString("name") +
                        " " + resultSet.getString("lastname") +
                        " amount of products: " + resultSet.getLong("amount") +
                        ", total price: " + resultSet.getDouble("total") + "$";
                System.out.println(string);
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
    }
    
    @Override
    public void getShopStatistic() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  \n" +
                    "COUNT(idProduct) as amount,  \n" +
                    "SUM(webshopdb.products.price) as total FROM webshopdb.order\n" +
                    "LEFT JOIN webshopdb.products ON webshopdb.order.idProduct = webshopdb.products.id\n" +
                    "LEFT JOIN webshopdb.clients ON webshopdb.order.idClient = webshopdb.clients.id ");
            while (resultSet.next()) {
                String string = "The shop sold: " + resultSet.getLong("amount") + " products " +
                        ", total price: " + resultSet.getDouble("total") + "$";
                System.out.println(string);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
