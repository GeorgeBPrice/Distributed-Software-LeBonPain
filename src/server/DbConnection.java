/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringJoiner;
import server.domain.Product;

/**
 *
 * @authors George Price
 */

public class DbConnection {

    // connectipon parameters for INSERTING into DB (secure and encrypt this in the future)
    private static final String DATABASE_USERNAME = "root";     // use an obscure name
    private static final String DATABASE_PASSWORD = "root";     // use strong password
    private static String database_url;
    private Connection dbConnection;
    private String insert_query;
    
    
    // make a connection with the customer database
    public Connection databaseConnection() {
        database_url = "jdbc:mysql://localhost:3306/lebonpain?useSSL=false";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(database_url, DATABASE_USERNAME, DATABASE_PASSWORD);
        
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
        return dbConnection;    
    }
 
    // prepares statement to insert new customer registration
    public synchronized void insertCustomerRecord(String fullName, String lastName, String email,
            String phone, String deliveryAddress, String password, boolean isAdmin) throws SQLException {
        
        database_url = "jdbc:mysql://localhost:3306/lebonpain?useSSL=false";
        insert_query = "INSERT INTO customer (firstName, lastName, email, phone, deliveryAddress, password, isAdmin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // establish a Connection with SQLdatabase 
        try (Connection connection = DriverManager
            .getConnection(database_url, DATABASE_USERNAME, DATABASE_PASSWORD);
                
            // prepare Statement with class object values
            PreparedStatement preparedStatement = connection.prepareStatement(insert_query)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, deliveryAddress);
            preparedStatement.setString(6, password);
            preparedStatement.setBoolean(7, isAdmin);
                        
            // debug ouput what will be inserted into database
            System.out.println(preparedStatement);

            // run the SQL update query
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    } 
      
    // prepares statement to insert new product into database
    public synchronized void insertProductRecord(String name, String unit, String ingredients, 
            Integer quantity, Double price) throws SQLException {

        database_url = "jdbc:mysql://localhost:3306/lebonpain?useSSL=false";
        insert_query = "INSERT INTO product (name, unit, ingredients, quantity, price) VALUES (?, ?, ?, ?, ?)";
        
        // establish a Connection with SQLdatabase 
        try (Connection connection = DriverManager
            .getConnection(database_url, DATABASE_USERNAME, DATABASE_PASSWORD);
                
            // prepare Statement with class object values
            PreparedStatement preparedStatement = connection.prepareStatement(insert_query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, unit);
            preparedStatement.setString(3, ingredients);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setDouble(5, price);
            
            // ouput what was saved to database
            System.out.println(preparedStatement);
            
            // run the SQL update query
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    // prepares statement to edit products in the database
    public void editProductRecord(String name, String unit, String ingredients, 
            Integer quantity, Double price, Integer id) throws SQLException {

        database_url = "jdbc:mysql://localhost:3306/lebonpain?useSSL=false";
        insert_query = "UPDATE product SET name =?, unit =?, ingredients =?, quantity =?, price =? WHERE id =?";
        
        // establish a Connection with SQLdatabase
        try (Connection connection = DriverManager
            .getConnection(database_url, DATABASE_USERNAME, DATABASE_PASSWORD);
                
            // prepare Statement with class object values
            PreparedStatement preparedStatement = connection.prepareStatement(insert_query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, unit);
            preparedStatement.setString(3, ingredients);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setDouble(5, price);
            preparedStatement.setInt(6, id);
            
            // ouput what was saved to database
            System.out.println(preparedStatement);
            
            // run the SQL update query
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    // prepares statement to delete product records from the database
    public void deleteProductRecord(Integer id) throws SQLException {

        database_url = "jdbc:mysql://localhost:3306/lebonpain?useSSL=false";
        insert_query = "DELETE from PRODUCT where id =?";
        
        // establish a Connection with SQLdatabase 
        try (Connection connection = DriverManager
            .getConnection(database_url, DATABASE_USERNAME, DATABASE_PASSWORD);
                
            // prepare Statement with class object values
            PreparedStatement preparedStatement = connection.prepareStatement(insert_query)) {
            preparedStatement.setInt(1, id);
            
            // ouput what was deleted in the database
            System.out.println(preparedStatement);
            
            // run the SQL update query
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    // prepares statement to insert new delivery schedule into the database
    public synchronized void insertScheduleRecord(String deliveryDay, Integer postcode, 
            Double cost) throws SQLException {

        database_url = "jdbc:mysql://localhost:3306/lebonpain?useSSL=false";
        insert_query = "INSERT INTO schedule (deliveryDay, postcode, cost) VALUES (?, ?, ?)";
        
        // establish a Connection with SQLdatabase 
        try (Connection connection = DriverManager
            .getConnection(database_url, DATABASE_USERNAME, DATABASE_PASSWORD);
                
            // prepare Statement with class object values
            PreparedStatement preparedStatement = connection.prepareStatement(insert_query)) {
            preparedStatement.setString(1, deliveryDay);
            preparedStatement.setInt(2, postcode);
            preparedStatement.setDouble(3, cost);           
                        
            // ouput what was saved to database
            System.out.println(preparedStatement);
           
            // run the SQL update query
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    // prepares statement to insert a new order into the database
    //(NOT YET COMPLETED)
    public synchronized void insertOrderRecord(int orderID, int customerID, double totalCost, double deliveryCost, 
        String deliverySchedule, ArrayList<Product> orderItems) throws SQLException{
        
        database_url = "jdbc:mysql://localhost:3306/lebonpain?useSSL=false";
        insert_query = "INSERT INTO orders (orderID, customerID, totalCost, deliveryCost,"
                + "deliverySchedule, orderItems) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DriverManager
            .getConnection(database_url, DATABASE_USERNAME, DATABASE_PASSWORD);
                
            // prepare Statement with class object values
            PreparedStatement preparedStatement = connection.prepareStatement(insert_query)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, customerID);
            preparedStatement.setDouble(3, totalCost);
            preparedStatement.setDouble(4, deliveryCost);
            preparedStatement.setString(5, deliverySchedule);

            
            // THIS PART NEEDS WORK (multiple products need to be added per order)
            // Maybe adding the ordered items to another table entirely, will be cleaner
            String productItem;
            for (Product item : orderItems) {
                productItem = item.toString();
                preparedStatement.setString(6, productItem);
                //preparedStatement.addBatch();
            }   
            
            // OTHERWISE could try using a String builder instead
            /*StringJoiner productItems = new StringJoiner(",");
            for (Product item : orderItems)
                productItems.add(orderItems.toString());
            preparedStatement.setString(6, productItems.toString());*/
                        
            // ouput what was saved to database
            System.out.println(preparedStatement);
           
            // run the SQL update query
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

}