package product.dao;

import interfaces.Crud;
import product.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements Crud <Product> {
    Connection connection;
    
    public ProductDaoImpl(Connection connection) {
        this.connection = connection;
    }
    
    public Product get(Long id) {
        Product product = new Product();
        if (id < 1) return null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getLong("id"));
                product.setProductName(resultSet.getString("device"));
                product.setCategory(resultSet.getString("category"));
                product.setPrice(resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
    
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String device = resultSet.getString("device");
                String category = resultSet.getString("category");
                Double price = resultSet.getDouble("price");
                productList.add(new Product(id,device,category,price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Boolean create(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO products (device, category, price) VALUES (?,?,?)"
            );
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getCategory());
            preparedStatement.setDouble(3, product.getPrice());
            int result = preparedStatement.executeUpdate();
            return result>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Boolean update(Long id, Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET device=?, category=?, price=? WHERE id=?");
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getCategory());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setLong(4, id);
            int result = preparedStatement.executeUpdate();
            return result>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Boolean delete(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id=?");
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            return result>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
