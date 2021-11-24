package application;

import client.dao.ClientDaoImpl;
import client.domain.Client;
import order.dao.OrderDaoImpl;
import order.domain.Order;
import product.dao.ProductDaoImpl;
import product.domain.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Menu {
    
    public static void mainMenu() throws SQLException {
        System.out.println("Main menu: There are (1-4) menu \n'#1 `client` menu', '#2 `product` menu', '#3 `order` menu', " +
                "'#4 `stats` menu'");
        System.out.println("Input number or name of menu to start, or quit to finish the programme");
        switch (Utils.simpleStringConsoleInput()) {
            case "client", "1" -> clientMenu();
            case "product", "2" -> productMenu();
            case "order", "3" -> orderMenu();
            case "stats", "4" -> statsMenu();
            case "quit", "q" -> System.out.println("Programme is finished.");
            default -> {
                System.out.println("Incorrect input, try again.");
                System.out.println();
                mainMenu();
            }
        }
    }
    
    public static void clientMenu() throws SQLException {
        System.out.println("Main menu -> Client menu (DataBase - webshopdb, Table - `clients`):");
        System.out.println("There are (1-6) methods, input number or name of method 1: 'get', 2: 'getall'," +
                " 3: 'update', 4: 'create', 5: 'delete', 6: 'groupdelete' ");
        System.out.println("Input 'info' to get more info or 'q' - to step back");
        switch (Utils.simpleStringConsoleInput()) {
            case "get", "1" -> {
                System.out.println("Main menu -> Client menu -> method get");
                System.out.println("Input positive client id");
                Connection connection = Utils.openConnection();
                ClientDaoImpl clientDao = new ClientDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                Client client = clientDao.get(id);
                if (client.getId() == null) {
                    System.out.println("Client with id:" + id + " doesn't exist");
                } else {
                    System.out.println(client);
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                clientMenu();
            }
            case "getall", "2" -> {
                System.out.println("Main menu -> Client menu -> method getall");
                System.out.println("Whole list of clients");
                Connection connection = Utils.openConnection();
                ClientDaoImpl clientDao = new ClientDaoImpl(connection);
                List<Client> allClients = clientDao.getAll();
                for (Client client : allClients) {
                    System.out.println(client);
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                clientMenu();
            }
            case "update", "3" -> {
                System.out.println("Main menu -> Client menu -> method update");
                System.out.println("Input positive client id which you want to update");
                Connection connection = Utils.openConnection();
                ClientDaoImpl clientDao = new ClientDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                if (clientDao.get(id).getId() == null) {
                    System.out.println("Client with id:" + id + " doesn't exist, you will be backed in Client menu");
                    System.out.println("-----------------------------------------------------------------------------------");
                    clientMenu();
                }
                System.out.println("Input data about client 'name', 'lastname', 'phone(***-**-***)' use 'space' as delimiter");
                Client client = Client.createClientManual(Utils.simpleStringConsoleInput());
                if (clientDao.update(id, client)) {
                    System.out.println("Client with id:" + id + " was updated");
                } else {
                    System.out.println("Client with id:" + id + " doesn't exist");
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                clientMenu();
            }
            case "create", "4" -> {
                System.out.println("Main menu -> Client menu -> method create");
                System.out.println("Input '1' to create client manually or '2' to create random client");
                Connection connection = Utils.openConnection();
                ClientDaoImpl clientDao = new ClientDaoImpl(connection);
                switch (Utils.simpleStringConsoleInput()) {
                    case "1" -> {
                        System.out.println("Input data about client 'name', 'lastname', 'phone(***-**-***)' use 'space' as delimiter");
                        System.out.println(clientDao.create(Client.createClientManual(Utils.simpleStringConsoleInput())));
                        System.out.println("The client was created");
                    }
                    case "2" -> {
                        Client randomClient = Client.createRandomClient();
                        System.out.println("Random client -> '" + randomClient.getName() + " " + randomClient.getLastname()
                                + "' " + randomClient.getPhone());
                        clientDao.create(randomClient);
                        System.out.println("The client was created");
                    }
                    default -> {
                        System.out.println("Incorrect input data. You'll be backed in Client menu");
                        clientMenu();
                    }
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                clientMenu();
            }
            case "delete", "5" -> {
                System.out.println("Main menu -> Client menu -> method delete");
                System.out.println("Input positive client id which you want to delete");
                Connection connection = Utils.openConnection();
                ClientDaoImpl clientDao = new ClientDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                if (clientDao.delete(id)) {
                    System.out.println("Client with id:" + id + " was deleted");
                } else {
                    System.out.println("Client with id:" + id + " doesn't exist");
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                clientMenu();
            }
            case "groupdelete", "6" -> {
                System.out.println("Main menu -> Client menu -> method Group delete ");
                System.out.println("Input positive client id it will delete all clients which id bigger than value");
                Connection connection = Utils.openConnection();
                ClientDaoImpl clientDao = new ClientDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                if (clientDao.deleteIdAboveThan(id)) {
                    System.out.println("Clients with id bigger than: " + id + " were deleted");
                } else {
                    System.out.println("This range is already clear");
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                clientMenu();
            }
            case "q" -> mainMenu();
            case "info" -> {
                System.out.println("The method get is: to get client from table by id");
                System.out.println("The method getAll is: to get all clients from table `clients`");
                System.out.println("The method create is: to insert client into table `clients`");
                System.out.println("The method update is: to update client in table `clients`");
                System.out.println("The method delete is: to delete client in table `clients`");
                System.out.println("The method groupdelete is: to delete clients in table `clients` where id bigger than input value");
                System.out.println();
                clientMenu();
            }
            default -> {
                System.out.println("Incorrect input data. You'll be backed in client menu");
                clientMenu();
            }
        }
    }
    
    public static void productMenu() throws SQLException {
        System.out.println("Main menu -> Product menu (DataBase - webshopdb, Table - `products`):");
        System.out.println("There are (1-5) methods, input number or name of method 1: 'get', 2: 'getall'," +
                " 3: 'update', 4: 'create', 5: 'delete' ");
        System.out.println("Input 'info' to get more info or 'q' - to step back");
        switch (Utils.simpleStringConsoleInput()) {
            case "get", "1" -> {
                System.out.println("Main menu -> Product menu -> method get");
                System.out.println("Input positive product id");
                Connection connection = Utils.openConnection();
                ProductDaoImpl productDao = new ProductDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                Product product = productDao.get(id);
                if (product.getId() == null) {
                    System.out.println("Product with id:" + id + " doesn't exist");
                } else {
                    System.out.println(product);
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                productMenu();
            }
            case "getall", "2" -> {
                System.out.println("Main menu -> Product menu -> method getall");
                System.out.println("Whole list of products");
                Connection connection = Utils.openConnection();
                ProductDaoImpl productDao = new ProductDaoImpl(connection);
                List<Product> all = productDao.getAll();
                for (Product product : all) {
                    System.out.println(product);
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                productMenu();
            }
            case "update", "3" -> {
                System.out.println("Main menu -> Product menu -> method update");
                System.out.println("Input positive product id which you want to update");
                Connection connection = Utils.openConnection();
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                ProductDaoImpl productDao = new ProductDaoImpl(connection);
                if (productDao.get(id).getId() == null) {
                    System.out.println("Product with id:" + id + " doesn't exist, you will be backed in Product menu");
                    System.out.println("-----------------------------------------------------------------------------------");
                    productMenu();
                }
                System.out.println("Input data about a product 'name', 'category', 'price' use 'space' as delimiter");
                Product product = Product.createProduct(Utils.simpleStringConsoleInput());
                System.out.println(productDao.update(id, product));
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                productMenu();
            }
            case "create", "4" -> {
                System.out.println("Main menu -> Product menu -> method create");
                System.out.println("Input data about a product 'name', 'category', 'price' use 'space' as delimiter");
                Connection connection = Utils.openConnection();
                ProductDaoImpl productDao = new ProductDaoImpl(connection);
                Product product = Product.createProduct(Utils.simpleStringConsoleInput());
                if (productDao.create(product)) {
                    System.out.println(product.getProductName() + " " + product.getCategory() + " " + product.getPrice());
                    System.out.println(product + "\nThe product was created");
                } else {
                    System.out.println("The product wasn't created");
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                productMenu();
            }
            case "delete", "5" -> {
                System.out.println("Main menu -> Product menu -> method delete");
                System.out.println("Input positive product id which you want to delete");
                Connection connection = Utils.openConnection();
                ProductDaoImpl productDao = new ProductDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                if (productDao.delete(id)) {
                    System.out.println("Product with id:" + id + " was deleted");
                } else {
                    System.out.println("Product with id:" + id + " doesn't exist");
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                productMenu();
            }
            case "q" -> mainMenu();
            case "info" -> {
                System.out.println("The method get is: to get product from table by id");
                System.out.println("The method getAll is: to get all product from table `products`");
                System.out.println("The method create is: to insert product into table `products`");
                System.out.println("The method update is: to update product in table `products`");
                System.out.println("The method delete is: to delete product in table `products`");
                System.out.println();
                productMenu();
            }
            default -> {
                System.out.println("Incorrect input data. You'll be backed in Product menu");
                productMenu();
            }
        }
    }
    
    public static void orderMenu() throws SQLException {
        System.out.println("Main menu -> Order menu (DataBase - webshopdb, Table - `order`):");
        System.out.println("There are (1-9) methods, input number or name of method 1: 'get', 2: 'getall'," +
                " 3: 'update', 4: 'create', 5: 'delete', 6: `client(id) - [product list(id)]`, 7: `client list(id) - [product list(id)]`, " +
                "\n8: `client - [product list]`, 9: `client list - [product list]`");
        System.out.println("Input 'info' to get more info or 'q' - to step back");
        switch (Utils.simpleStringConsoleInput()) {
            case "get", "1" -> {
                System.out.println("Main menu -> Order menu -> method get");
                System.out.println("Input positive order id");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                Order order = orderDao.get(id);
                if (order.getId() == null) {
                    System.out.println("Order with id:" + id + " doesn't exist");
                } else {
                    System.out.println(order);
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "getall", "2" -> {
                System.out.println("Main menu -> Order menu -> method getall");
                System.out.println("Whole list of orders");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                List<Order> all = orderDao.getAll();
                for (Order order : all) {
                    System.out.println(order);
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "update", "3" -> {
                System.out.println("Main menu -> Order menu -> method update");
                System.out.println("Input positive order id which you want to update");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                Order orderCheckExist = orderDao.get(id);
                if (orderCheckExist.getId() == null) {
                    System.out.println("Order with id:" + id + " doesn't exist, you will be backed in Order menu");
                    System.out.println("-----------------------------------------------------------------------------------");
                    orderMenu();
                }
                Order order = orderDao.createOrderManual();
                orderDao.update(id, order);
                System.out.println("Order with id:" + id + " was updated");
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "create", "4" -> {
                System.out.println("Main menu -> Order menu -> method create");
                System.out.println("Input data about an order 'client id' and 'product id' use 'space' as delimiter");
                System.out.println("Be careful you can create 'order' only for clients which exist, " +
                        "order maybe without products (id = 0) but if you need to add product, its have to exist");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                
                if (orderDao.create(orderDao.createOrderManual())) {
                    System.out.println("The order was created");
                } else {
                    System.out.println("The order wasn't created");
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "delete", "5" -> {
                System.out.println("Main menu -> Order menu -> method delete");
                System.out.println("Input positive order id which you want to delete");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                if (orderDao.delete(id)) {
                    System.out.println("Order with id:" + id + " was deleted");
                } else {
                    System.out.println("Order with id:" + id + " wasn't deleted");
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "client(id) - [product list(id)]", "6" -> {
                System.out.println("Main menu -> Order menu -> method client(id) - [product list(id)]");
                System.out.println("Input client id to get list of id products");
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                Order order = orderDao.getAllOrderClient(id);
                if (order.getIdClient() == null) {
                    System.out.println("Client with id: " + id + " doesn't exist, you will be backed in Order menu");
                    System.out.println("-----------------------------------------------------------------------------------");
                    orderMenu();
                } else {
                    System.out.println("Client id: " + order.getIdClient() + " -> list of products id "
                            + order.getOrderIndexList());
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "client list(id) - [product list(id)]", "7" -> {
                System.out.println("Main menu -> Order menu -> method client list(id) - [product list(id)]");
                System.out.println("List of clients id with lists of id products");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                List<Order> allOrderGroupByClient = orderDao.getAllOrderGroupByClient();
                for (Order order : allOrderGroupByClient) {
                    System.out.println("Client id: " + order.getIdClient() + " -> list of products id "
                            + order.getOrderIndexList());
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "client - [product list]", "8" -> {
                System.out.println("Main menu -> Order menu -> method client - [product list]");
                System.out.println("List of clients with lists of products");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                Map<Client, List<Product>> orders = orderDao.getAllClientsWithProducts(orderDao.getAllOrderGroupByClient());
                for (Client client : orders.keySet()) {
                    System.out.println(client + " -> " + orders.get(client));
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "client list - [product list]", "9" -> {
                System.out.println("Main menu -> Order menu -> method client list - [product list]");
                System.out.println("Input client id, to get list of products");
                Long id = Utils.parseToLong(Utils.simpleStringConsoleInput());
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                Order order = orderDao.getAllOrderClient(id);
                if (order.getIdClient() == null) {
                    System.out.println("Client with id: " + id + " doesn't exist, you will be backed in Order menu");
                    System.out.println("-----------------------------------------------------------------------------------");
                    orderMenu();
                } else {
                    System.out.println(orderDao.getClientWithProducts(order));
                }
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                orderMenu();
            }
            case "q" -> mainMenu();
            case "info" -> {
                System.out.println("The method 1 get is: to get order from table by id");
                System.out.println("The method 2 getAll is: to get all order from table `order`");
                System.out.println("The method 3 create is: to insert order into table `order`");
                System.out.println("The method 4 update is: to update order in table `order`");
                System.out.println("The method 5 delete is: to delete order in table `order`");
                System.out.println("The method 6 `client(id) - [product list(id)]` is: " +
                        "to get client id and list of id products in table `order`");
                System.out.println("The method 7 `client list(id) - [product list(id)]` is: " +
                        "to get list of clients id and list of id products in table `order`");
                System.out.println("The method 8 `client - [product list]` is: " +
                        "to get information about client and list of products which he bought.");
                System.out.println("The method 9 `client list - [product list]` is: " +
                        "to get information about all clients and list of products which they bought.");
                
                System.out.println();
                orderMenu();
            }
            default -> {
                System.out.println("Incorrect input data. You'll be backed in Order menu");
                orderMenu();
            }
        }
    }
    
    public static void statsMenu() throws SQLException {
        System.out.println("Main menu -> Statistic menu (DataBase - webshopdb):");
        System.out.println("There are (1-2) methods, input number or name of method 1: 'stats', 2: 'shop'");
        System.out.println("Input 'info' to get more info or 'q' - to step back");
        switch (Utils.simpleStringConsoleInput()) {
            case "stats", "1" -> {
                System.out.println("Main menu -> Statistic menu -> method stats");
                System.out.println("Statistic of all clients");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                orderDao.getAllClientStatistic();
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("-----------------------------------------------------------------------------------");
                statsMenu();
            }
            case "shop", "2" -> {
                System.out.println("Main menu -> Statistic menu -> method shop");
                System.out.println("Statistic of Webshop");
                Connection connection = Utils.openConnection();
                OrderDaoImpl orderDao = new OrderDaoImpl(connection);
                orderDao.getShopStatistic();
                if (connection != null) {
                    Utils.closeConnection(connection);
                }
                System.out.println("----------------------------------------------------------------------------------");
                statsMenu();
            }
            case "q" -> mainMenu();
            case "info" -> {
                System.out.println("The method stats is: to get list of clients with amount of products and its total price");
                System.out.println("The method shop is: to get amount of sold products and its total cost");
                System.out.println();
                statsMenu();
            }
            default -> {
                System.out.println("Incorrect input data. You'll be backed in Statistic menu");
                statsMenu();
            }
        }
    }
}