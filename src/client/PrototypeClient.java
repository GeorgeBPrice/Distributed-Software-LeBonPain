/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.net.*;
import java.io.*;
import java.util.LinkedList;
import server.domain.Customer;
import server.domain.DeliverySchedule;
import server.domain.Product;
import server.domain.Order;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import javax.crypto.*;


/**
 *
 * @authors George Price
 */

public class PrototypeClient {
       
    // Declare socket, connection, streams, public key
    int pubKeyLength;
    byte []  bytesPublicKey = null; 
    private static PublicKey publicKey;
    private Socket socket = null;
    private int serverPort;	
    ObjectInputStream in;
    ObjectOutputStream out;
    
    // Declare LinkedLists for each class Object
    LinkedList <Customer> customers;
    LinkedList <Product> products;
    LinkedList <DeliverySchedule> schedules;

    public PrototypeClient() throws NoSuchAlgorithmException, UnknownHostException, 
            IOException {

        // create new instances of local Lists to manage the Objects with
        customers = new LinkedList<>();
        products = new LinkedList<>();
        schedules = new LinkedList<>();
        
        // attemp to connect to server, and start object streams
        try {
            
            // initialise port and socket
            serverPort = 8888;
            socket = new Socket("localhost", serverPort); 
            System.out.println(socket.getInetAddress());
            
            // initialise streams
            out =new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream( socket.getInputStream());
            System.out.println("output "+ out.getClass()+ "input "+in.getClass());
        
        } catch (IOException e) {
            System.out.println("Connection:"+e.getMessage());
        }
    }
    
    // function to process RSA encryption at login
    public  byte [] encrypt( String message) throws Exception {
        
        // initialise cipher key
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);          
        byte [] cipherData = cipher.doFinal(message.getBytes("UTF-8"));
        
        // return the cipher key
        System.out.println("Encrypted cipher key processed.");
        return cipherData;  
    }
   
    // sends register new customer request to server, to insert into database
    public void sendCustomer(LinkedList<Customer> customers) {

        try{
            out.writeObject("addCustomer");

        while(customers.size() > 0){
            Customer data = customers.removeFirst();
            System.out.println(data);
            out.writeObject(data);
        }

        if (customers.size() == 0)
            out.writeObject(new Customer("finished"));
        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        }   

    }
    
    // sends request to server to view customers in the customer database table
    public LinkedList<Customer> receiveCustomer() {
        Customer data;
        LinkedList<Customer> customerList  = new LinkedList<>();
        System.out.println("\n**Entered into receiveCustomers()**");
        try{
            out.writeObject("readCustomer");
        while (true){
            data = (Customer)in.readObject();
                if (data.getFirstName().equalsIgnoreCase("finished"))
                    break;

            customerList.add(new Customer(data));
        }

        System.out.println("Retrieved the following Customers: " + customerList);
        return customerList; 

        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        } catch( ClassNotFoundException e){
            System.out.println("IO:"+e.getMessage());
        } 

        return null;
    }
    
    // processes the logic and security for the login process
    public String receiveCustomerLogin(String user, String pass) throws IOException, 
            NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        
        String username = user;
        String password = pass;
        String loginType = "";
        int pubKeyLength;
        
        try{
            out.writeObject("login");
            System.out.println("\n**Login process commenced**");

            // initialise and parse
            String pubKeyLengthString = (String) in.readObject();
            pubKeyLength = Integer.parseInt(pubKeyLengthString);           
            bytesPublicKey = new byte[pubKeyLength];
            
            //read the PublicKey in bytes sent from the sever
            bytesPublicKey = (byte[]) in.readObject();
            
            //generate the key speciifcation for encoding
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(bytesPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            
            //extract the PublicKey
            publicKey = keyFactory.generatePublic(pubKeySpec);
            
            //ecrypt the password
            byte[] encodedmessage =  encrypt(password);
            
            //send the encrypted password length
            out.writeInt(encodedmessage.length);
            
            //send the encrypted password in bytes
            out.write(encodedmessage, 0, encodedmessage.length);
            System.out.println("Encrypted login sent.");
            
            out.writeObject(username);
           
        while (true){
            String login = (String) in.readObject();
            if(login.equals("Customer")){
                loginType = "Customer";
                System.out.println("Setting Login to customer role");
                break;
            }
            else if(login.equals("Admin")){
                loginType = "Admin";
                System.out.println("Setting Login control to Admin role");
                break;
            }
            else if(login.equals("Incorrect")){
                System.out.println("Incorrect Login Details used.");
                loginType = "Incorrect";
                break;
            }
        }

        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        } catch( ClassNotFoundException e){
            System.out.println("IO:"+e.getMessage());
        } 
        return loginType;
       
    }
    
    // sends new product to server, to be saved to database
    public void sendProduct(LinkedList<Product> products) {

        try{
            out.writeObject("addProduct");

        while(products.size() > 0){
            Product data = products.removeFirst();
            System.out.println(data);
            out.writeObject(data);
        }

        if (products.size() == 0)
            out.writeObject(new Product("finished"));
        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        }   

    }
    
    // sends new product details to server, to update product database with
    public void editProduct(LinkedList<Product> products) {

        try{
            out.writeObject("editProduct");

        while(products.size() > 0){
            Product data = products.removeFirst();
            System.out.println(data);
            out.writeObject(data);
        }

        if (products.size() == 0)
            out.writeObject(new Product("finished"));
        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        }   

    }
    
    // sends request to delete product from server side database
    public void deleteProduct(LinkedList<Product> products) {

        try{
            out.writeObject("deleteProduct");

        while(products.size() > 0){
            Product data = products.removeFirst();
            System.out.println(data);
            out.writeObject(data);
        }

        if (products.size() == 0)
            out.writeObject(new Product("finished"));
        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        }   

    }    
    
    // receives list of products from the server's database
    public LinkedList<Product> receiveProduct() {
        Product data;
        LinkedList<Product> productList  = new LinkedList<>();
        try{
            out.writeObject("readProduct");
        while (true){
            data = (Product)in.readObject();
                if (data.getName().equalsIgnoreCase("finished"))
                    break;

            productList.add(new Product(data));
        }

        System.out.println("Retrieved the following Products: " + productList);
        return productList; 

        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        } catch( ClassNotFoundException e){
            System.out.println("IO:"+e.getMessage());
        } 

        return null;
    } 
    
    // sends new delivery schedules to the server to be saved in database
    public void sendDeliverySchedule(LinkedList<DeliverySchedule> schedule) {

        try{
            out.writeObject("addSchedule");

        while(schedule.size() > 0){
            DeliverySchedule data = schedule.removeFirst();
            System.out.println(data);
            out.writeObject(data);
        }

        if (schedule.size() == 0)
            out.writeObject(new DeliverySchedule("finished"));
        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        }   

    }
     
    // used to receive delivery schedules from server database
    public LinkedList<DeliverySchedule> receiveDeliverySchedule() {
        DeliverySchedule data;
        LinkedList<DeliverySchedule> scheduleList  = new LinkedList<>();
        try{
            out.writeObject("readSchedule");
        while (true){
            data = (DeliverySchedule)in.readObject();
                if (data.getDeliveryDay().equalsIgnoreCase("finished"))
                    break;

            scheduleList.add(new DeliverySchedule(data));
        }

        System.out.println("Retrieved the following Delivery Schedule: " + scheduleList);
        return scheduleList; 

        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        } catch( ClassNotFoundException e){
            System.out.println("IO:"+e.getMessage());
        } 

        return null;
    }    
    
    // used to send customer placed orders to the server for processing
    //(NOT YET COMPLETED)
    public void sendOrder(LinkedList<Order> orders) {

        try{
            out.writeObject("addOrder");

        while(orders.size() > 0){
            Order data = orders.removeFirst();
            System.out.println(data);
            out.writeObject(data);
        }

        if (orders.size() == 0)
            out.writeObject(new Order("finished"));
        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        }   

    }
    
    // used to display orders
    // (NOT YET COMPLETED)
    public LinkedList<Order> displayOrder() {
        Order data;
        LinkedList<Order> orderList  = new LinkedList<>();
        try{
            out.writeObject("readOrder");
        while (true){
            data = (Order)in.readObject();
                if (data.getDeliverySchedule().equalsIgnoreCase("finished"))
                    break;
            orderList.add(new Order(data.getOrderID(), data.getCustomerID(),
                data.getTotalCost(), data.getDeliveryCost(), data.getDeliverySchedule(), 
                    data.getOrderItems()));
        }

        System.out.println("Retrieved the following Order: " + orderList);
        return orderList; 

        } catch (IOException e) {
            System.err.println("IOException:  " + e.getMessage());
        } catch( ClassNotFoundException e){
            System.out.println("IO:"+e.getMessage());
        } 
        return null;
    }  
}


