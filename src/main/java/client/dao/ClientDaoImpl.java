package client.dao;

import client.domain.Client;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements interfaces.ClientDao {
    Connection connection;
    
    public ClientDaoImpl(Connection connection) {
        this.connection = connection;
    }
    
    public Client get(Long id) {
        Client client = new Client();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clients WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                client.setLastname(resultSet.getString("lastname"));
                client.setPhone(resultSet.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
    
    public List<Client> getAll() {
        List<Client> clientList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clients");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                clientList.add(new Client(id,name,lastname,phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    @Override
    public Boolean create(Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO clients (name, lastname, phone) VALUES (?,?,?)"
            );
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getLastname());
            preparedStatement.setString(3, client.getPhone());
            int result = preparedStatement.executeUpdate();
            return result>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Boolean update(Long id, Client client) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE clients SET name=?, lastname=?, phone=? WHERE id=?");
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getLastname());
            preparedStatement.setString(3, client.getPhone());
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE id=?");
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            return result>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Boolean deleteIdAboveThan(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE id >" + id);
            int result = preparedStatement.executeUpdate();
            return result>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
