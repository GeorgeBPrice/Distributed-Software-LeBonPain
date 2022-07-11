/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import server.domain.Customer;
import server.domain.Product;
import server.domain.DeliverySchedule;
import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.security.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.*;
import java.security.spec.X509EncodedKeySpec;
import java.sql.PreparedStatement;
import server.domain.Order;


/**
 *
 * @authors George Price
 */

public class PrototypeServer {

    // Declare LinkedLists for class objects
    LinkedList <Customer> customers;
    LinkedList <Product> products;
    LinkedList <DeliverySchedule> schedules;
    LinkedList <Order> orders;
    DbConnection dbConnection = new DbConnection();
    
    //Private Key Variables
    private final KeyPairGenerator keyPairGen;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    
    // Define server port and socket, and client socket, and binary data 
    static ServerSocket serverSocket;
    final static int SERVER_PORT = 8888;
    static Socket clientSocket;
    
    String checkUsername, checkLoginPassword; //Verifying user login
    boolean checkAdmin;
    boolean checkIsAdmin;
    boolean customerLoggedIn;
    boolean adminLoggedIn;
    boolean isAdmin = false;
    
    static PrototypeServer server;

    // initialising lists and server/socket
    public PrototypeServer() throws NoSuchAlgorithmException, IOException {
        
        // Initialise linked lists and file
        customers = new LinkedList<>();
        products = new LinkedList<>();
        schedules = new LinkedList<>();
        orders = new LinkedList<>();
        
        keyPairGen =  KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGen.genKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
        
        // set preference for IPv4, and initialise the server socket
        System.setProperty("java.net.preferIPv4Stack" , "true");     
        serverSocket = new ServerSocket(SERVER_PORT);     
    }
    
    // generate keys for secure login process
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
    
    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public KeyPairGenerator getKeyPairGen() {
        return keyPairGen;
    }
    
    // starting socket connection 
    public void createClientThread() throws IOException {       
        System.out.println("Server waiting for client requests... \n");
        
        // open connection to Server  
        while(true) {
            clientSocket = serverSocket.accept();
            ClientConnection connectC = new ClientConnection(clientSocket, customers, products, 
                    schedules, orders, publicKey, server.getPrivateKey(), dbConnection);
            System.out.println("New data connection made: " + clientSocket.getInetAddress());

        }
     
    }
    
    // logic to process login key and encryption logic
    public String decrypt(byte [] encodedMessage) throws NoSuchAlgorithmException, 
            NoSuchPaddingException, InvalidKeyException, 
                InvalidAlgorithmParameterException, IllegalBlockSizeException, 
                    BadPaddingException {
        
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey, cipher.getParameters());
        return new String(cipher.doFinal(encodedMessage));
    } 
    
    // Launches the GUI and the client thread
    public static void main (String args[]) {
        try {
            server = new PrototypeServer();
            PublicKey publicKey =  server.getPublicKey();
            server.createClientThread();
        } catch (IOException e) {
            System.out.println("IOException: "+ e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: "+ e.getMessage());
        }
    } 
    
    // handles connections for client data requests
    class ClientConnection extends Thread {

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket clientSocket;
        String searchWord;
        private LinkedList<Customer> customerList;
        private LinkedList<Product> productList;
        private LinkedList<DeliverySchedule> scheduleList;
        private LinkedList<Order> orderList;
        private Order invoice;
        PublicKey publicKey;
        PrivateKey privateKey;
        

        // Constructor to process serialised objects through, and encrypted keys
        public ClientConnection (Socket aClientSocket,LinkedList<Customer> customers, LinkedList<Product> products,
            LinkedList<DeliverySchedule> schedules, LinkedList<Order> orders, PublicKey key, PrivateKey privateKey,  DbConnection dbConnection){
            publicKey =key;
            this.privateKey = privateKey;

            customerList = new LinkedList<>();
            productList = new LinkedList<>();
            scheduleList = new LinkedList<>();
            orderList = new LinkedList<>();
            customerList = customers;
            productList = products;
            scheduleList = schedules;
            orderList = orders;

            try {
               clientSocket = aClientSocket;
                in = new ObjectInputStream(clientSocket.getInputStream());
                out =new ObjectOutputStream(clientSocket.getOutputStream());              
                this.start();
                
                System.out.println("New connection started: "+ in.getClass());
                
            } catch(IOException e) {
                System.out.println("Connection:"+e.getMessage());
            }
        }
      
        // search List objects for their respective keywords
        public List<Customer> searchEntryCustomer(String firstName){
            Predicate<Customer> find = r -> (r.getFirstName().equalsIgnoreCase(firstName)); 
            LinkedList <Customer> list= new LinkedList<>();   
            return list;
        }
        
        public List<Product> searchEntryProduct(String firstName){
            Predicate<Product> find = r -> (r.getName().equalsIgnoreCase(firstName));
            LinkedList <Product> list= new LinkedList<>();   
            return list;
        }
        
        public List<DeliverySchedule> searchEntrySchedule(String firstName){
            Predicate<DeliverySchedule> find = r -> (r.getDeliveryDay().equalsIgnoreCase(firstName));
            LinkedList <DeliverySchedule> list = new LinkedList<>();               
            return list;
        }

        public List<Order> searchEntryOrder(String finished){
            Predicate<Order> find = r -> (r.getDeliverySchedule().equalsIgnoreCase(finished));
            LinkedList <Order> list = new LinkedList<>();               
            return list;
        }         

        // run() launches the input stream read/writes, of the serialised data traffic
        @Override
        public void run() {
            Customer dataCustomer;
            Product dataProduct;
            DeliverySchedule dataSchedule;
            Order dataOrder;
            String encodedKey =null, publicKeyString;
            String messageString =null;
            X509EncodedKeySpec pubKeySpec =null;
            
            try {            
                while (true){

                    // assign String to determine the Object type being transmitted
                    String option = (String) in.readObject();               

                    /* 
                     * Check for Customer 'add' or 'read' instruction words
                     * then add to SQL database, or query the database
                     */ 

                    if (option.equalsIgnoreCase("addCustomer")) {
                        while((dataCustomer = (Customer)in.readObject())!= null) { 

                            if (dataCustomer.getFirstName().equalsIgnoreCase("finished")) {
                                System.out.println("No more Customer found");
                                break;
                            }

                            // then add the transmitted object data to the serverside local List      
                            customerList.add(new Customer(dataCustomer));
                            
                            // save customer to SQL database called 'customer'
                            for (Customer customer : customerList) {
                                dbConnection.insertCustomerRecord(customer.getFirstName(), 
                                        customer.getLastName(), 
                                        customer.getEmail(),
                                        customer.getPhone(), 
                                        customer.getDeliveryAddress(), 
                                        customer.getPassword(),
                                        customer.getIsAdmin());
                            }
                            
                            // ouput messages and clear local list for next run()
                            System.out.println("Customer saved to database!");
                            customerList.clear();
                        }
                        
                    } else if (option.equalsIgnoreCase("login")) {
                        try {
                        System.out.println("\n****Entered into Login****");
                        // generate the encoded key
                        byte[] bytesPubKey = publicKey.getEncoded();
                        System.out.println("PublicKey size in bytes: " + bytesPubKey.length);
                        int pubKeyLength = bytesPubKey.length;
                        String pubKeyLengthString = String.valueOf(pubKeyLength);
                        System.out.println("The key length as a String: " + pubKeyLengthString);
                        
                        out.writeObject(pubKeyLengthString);
                        System.out.println("The length sent to client is: " + pubKeyLengthString);
                        
                        out.writeObject(bytesPubKey);
                        System.out.println("Full key sent to client: " + bytesPubKey);
                        
                        // read the size of encrypted message to be sent from client
                        int messageLength = in.readInt();
                        System.out.println("Read the length of message");
                        byte [] encodedmessage = new byte [messageLength];
                        System.out.println("Added the messge Length to byte array");

                        // read the encryped password sent from client
                        in.read(encodedmessage,0, encodedmessage.length);
                        System.out.println("Read full encryptes messgae");

                        // ouput decrypted password
                        String password = decrypt(encodedmessage);
                        System.out.println("The message decrypted from client: " + password);
                        
                        String username = (String) in.readObject();                                               
                        
                        // query customer table in SQL database, and retrieve data
                        DbConnection con = new DbConnection();
                        Connection connectDatabase = con.databaseConnection();
                        String query = "SELECT * from mdhs.customer";  
                        try {
                            Statement statement = connectDatabase.createStatement();
                            ResultSet queryResult = statement.executeQuery(query);

                            while(queryResult.next()) {

                                // get values from each retrieved customer, add to local list
                                customerList.add(new Customer(
                                    queryResult.getString("firstName"),
                                    queryResult.getString("lastName"),
                                    queryResult.getString("email"),
                                    queryResult.getString("phone"),
                                    queryResult.getString("deliveryAddress"),
                                    queryResult.getString("password"),
                                    queryResult.getBoolean("isAdmin")));
                            } 
                            //System.out.println("******Customer from DataBase******\n" + customerList);
                        } catch (Exception e) {
                            System.out.println("Exception" + e.getMessage());
                        }   

                        //Search customerList for matching entry
                        Predicate<Customer> find = r -> (r.getEmail().equalsIgnoreCase(username));
                        System.out.println("Searching for customer with username: " + username);
                        List<Customer> list = customerList.stream().filter(find)
                            .collect(Collectors.toList());
                        System.out.println("\nThis is the list found)" + list + "\n");
                        list.forEach((e) -> {
                            checkUsername = e.getEmail();
                            System.out.println(e.getEmail());
                            checkLoginPassword = e.getPassword();
                            System.out.println(e.getPassword());
                            checkIsAdmin = e.getIsAdmin();
                            System.out.println(e.getIsAdmin());
                        });

                        //check details returned from server against details entered in client
                        if (list.isEmpty()){
                            System.out.println("No matching Username or Password found");
                            out.writeObject("Incorrect");
                        } else if (checkUsername.equals(username) && checkLoginPassword.equals(password)){
                            customerLoggedIn = true;
                            System.out.println("Username and Password match for Customer");
                            
                            // If the username and password match AND the user has admin access, apply this to the session. 
                            if(checkIsAdmin == true){
                                adminLoggedIn = true;
                                System.out.println("Admin Credentials supplied.");
                            }
                        } else if (checkUsername != username || checkLoginPassword != password){ 
                            System.out.println("No matching Username or Password found");
                            out.writeObject("Incorrect");
                        } else {
                            System.out.println("No matching Username or Password found");
                            out.writeObject("Incorrect");
                        }

                        // then pass back to client what the logged in user type is
                        if (adminLoggedIn == true){
                            out.writeObject("Admin");
                            System.out.println("Writing Admin");
                            adminLoggedIn =false;
                        } else if (customerLoggedIn == true){
                            out.writeObject("Customer");
                            System.out.println("Writing Customer");
                            customerLoggedIn = false;
                        }
                        
                        // clear local list for next user who attempts to login
                        customerList.clear();
                        
                        } catch (NoSuchAlgorithmException e){
                            
                            System.out.println("Algorithm: "+ e.getMessage());}
                                catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e){
                            System.out.println("invalid key spec: "+ e.getMessage());}
                                catch (InvalidKeyException e){
                            System.out.println("invalid key: "+ e.getMessage());
                        } catch (InvalidAlgorithmParameterException ex) {
                            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        } else if (option.equalsIgnoreCase("readCustomer")) {
                        
                        // query customer table in SQL database, and retrieve data
                        DbConnection con = new DbConnection();
                        Connection connectDatabase = con.databaseConnection();
                        String query = "SELECT * from mdhs.customer";       

                        try {
                            Statement statement = connectDatabase.createStatement();
                            ResultSet queryResult = statement.executeQuery(query);

                            while(queryResult.next()) {

                                // get values from each retrieved customer, add to local list
                                customerList.add(new Customer(
                                    queryResult.getInt("id"),
                                    queryResult.getString("firstName"),
                                    queryResult.getString("lastName"),
                                    queryResult.getString("email"),
                                    queryResult.getString("phone"),
                                    queryResult.getString("deliveryAddress"),
                                    queryResult.getString("password"),
                                    queryResult.getBoolean("isAdmin")));
                            } 
                            System.out.println("******Customer from DataBase******\n" + customerList);
                        } catch (Exception e) {
                            System.out.println("Exception" + e.getMessage());
                        }                           

                        // finish up!
                        while(customerList.size()>0)
                            out.writeObject(customerList.removeFirst());
                        if (customerList.size() == 0)
                            out.writeObject(new Customer("finished"));
                    } 
                                                                               
                    /* 
                     * Check for Product 'add' or 'read' instruction words
                     * then add to SQL database, or query the database
                     */        
                    else if (option.equalsIgnoreCase("addProduct")) {
                        while((dataProduct = (Product)in.readObject())!= null) { 

                            if (dataProduct.getName().equalsIgnoreCase("finished")) {
                                System.out.println("No more Products found");
                                break;
                            }

                            // then add the transmitted object data to the serverside local List      
                            productList.add(new Product(dataProduct));
                            
                            // save customer to SQL database called 'product'
                            for (Product product : productList) {
                                dbConnection.insertProductRecord(
                                        product.getName(), 
                                        product.getUnit(), 
                                        product.getIngredients(),
                                        product.getQuantity(), 
                                        product.getPrice());
                            }
                            
                            // ouput messages and clear local list for next run()
                            System.out.println("Product saved to database!");
                            productList.clear();
                        }

                    } else if (option.equalsIgnoreCase("editProduct")) {
                        while((dataProduct = (Product)in.readObject())!= null) { 

                            if (dataProduct.getName().equalsIgnoreCase("finished")) {
                                break;
                            }

                            // then add the transmitted object data to the serverside local List      
                            productList.add(new Product(dataProduct));
                            
                            // save customer to SQL database called 'product'
                            DbConnection dbConnection = new DbConnection();
                            for (Product product : productList) {
                                dbConnection.editProductRecord (
                                        product.getName(), 
                                        product.getUnit(), 
                                        product.getIngredients(),
                                        product.getQuantity(), 
                                        product.getPrice(),
                                        product.getId());
                            }
                            
                            // ouput messages and clear local list for next run()
                            System.out.println("Product edited in database!");
                            productList.clear();
                        }

                    } else if (option.equalsIgnoreCase("deleteProduct")) {
                        while((dataProduct = (Product)in.readObject())!= null) { 

                            if (dataProduct.getName().equalsIgnoreCase("finished")) {
                                break;
                            }

                            // then add the transmitted object data to the serverside local List      
                            productList.add(new Product(dataProduct));
                            
                            // save delete record from SQL, using product ID
                            DbConnection dbConnection = new DbConnection();
                            for (Product product : productList) {
                                dbConnection.deleteProductRecord(
                                        product.getId());
                            }
                            
                            // ouput messages and clear local list for next run()
                            System.out.println("Product edited in database!");
                            productList.clear();
                        }                   
                    
                    } else if (option.equalsIgnoreCase("readProduct")) {
                        
                        // query product table in SQL database, and retrieve data
                        DbConnection con = new DbConnection();
                        Connection connectDatabase = con.databaseConnection();
                        String query = "SELECT * from mdhs.product";       

                        try {
                            Statement statement = connectDatabase.createStatement();
                            ResultSet queryResult = statement.executeQuery(query);

                            while(queryResult.next()) {

                                // get values from each retrieved product, add to local list
                                productList.add(new Product(
                                    queryResult.getString("name"),
                                    queryResult.getString("unit"),
                                    queryResult.getString("ingredients"),
                                    queryResult.getInt("quantity"),
                                    queryResult.getDouble("price"),
                                    queryResult.getInt("id")));          
                            } 
                        } catch (Exception e) {
                            System.out.println("Exception" + e.getMessage());
                        }                           

                        // finish up!
                        while(productList.size()>0)
                            out.writeObject(productList.removeFirst());
                        if (productList.size() == 0)
                            out.writeObject(new Product("finished"));
                    }
                    
                    /* 
                     * Check for Delivery Schedule 'add' or 'read' instruction words
                     * then add to SQL database, or query/read the database
                     */ 
                    else if (option.equalsIgnoreCase("addSchedule")) {
                        while((dataSchedule = (DeliverySchedule)in.readObject())!= null) { 

                            if (dataSchedule.getDeliveryDay().equalsIgnoreCase("finished")) {
                                System.out.println("No more Schedules found");
                                break;
                            }

                            // then add the transmitted object data to the serverside local List      
                            scheduleList.add(new DeliverySchedule(dataSchedule));
                            
                            // save schedule to SQL database called 'schedule'
                            for (DeliverySchedule schedule : scheduleList) {
                                dbConnection.insertScheduleRecord(
                                        schedule.getDeliveryDay(), 
                                        schedule.getPostcode(), 
                                        schedule.getCost());
                            }
                            
                            // ouput messages and clear local list for next run()
                            System.out.println("Schedule saved to database!");
                            scheduleList.clear();
                        }

                    } else if (option.equalsIgnoreCase("readSchedule")) {
                        
                        // query schedule table in SQL database, and retrieve data
                        DbConnection con = new DbConnection();
                        Connection connectDatabase = con.databaseConnection();
                        String query = "SELECT * from mdhs.schedule";       

                        try {                            
                            Statement statement = connectDatabase.createStatement();
                            ResultSet queryResult = statement.executeQuery(query);

                            while(queryResult.next()) {

                                // get values from each retrieved schedule, add to local list
                                scheduleList.add(new DeliverySchedule(
                                    queryResult.getString("deliveryDay"),
                                    queryResult.getInt("postcode"),
                                    queryResult.getDouble("cost")));          
                            } 
                        } catch (Exception e) {
                            System.out.println("Exception" + e.getMessage());
                        }                           

                        // finish up!
                        while(scheduleList.size()>0)
                            out.writeObject(scheduleList.removeFirst());
                        if (scheduleList.size() == 0)
                            out.writeObject(new DeliverySchedule("finished"));
                    } 
                    
                    /* 
                     * Order Functionality, Check for addOrder or readOrder words
                     * then add to SQL orders database, or query/read the database
                     */ 
                    
                    
                    //(NOT YET COMPLETED)
                    else if (option.equalsIgnoreCase("addOrder")) {
//                        while((dataOrder = (Order)in.readObject())!= null) { 
//
//                            if (dataOrder.getDeliverySchedule().equalsIgnoreCase("finished")) {
//                                break;
//                            }
//
//                            // then add the transmitted object data to the serverside local List      
//                            orderList.add(new Order(dataOrder));
//                            
//                            // save order to SQL database called 'order'
//                            for (Order order : orderList) {
//                                dbConnection.insertOrderRecord(
//                                        order.getOrderID(), 
//                                        order.getCustomerID(), 
//                                        order.getTotalCost(),
//                                        order.getDeliveryCost(),
//                                        order.getDeliverySchedule(),
//                                        order.getOrderItems());
//                            }
//                            
//                            // ouput messages and clear local list for next run()
//                            System.out.println("Order saved to database!");
//                            orderList.clear();
//                        }                       
                    } 
                    
                    //(NOT YET COMPLETED)
                    else if (option.equalsIgnoreCase("readOrders")) {                        
//                        // query order table in SQL database, and retrieve all order data
//                        DbConnection con = new DbConnection();
//                        Connection connectDatabase = con.databaseConnection();
//                        String query = "SELECT * from mdhs.order";       
//
//                        try {                            
//                            Statement statement = connectDatabase.createStatement();
//                            ResultSet queryResult = statement.executeQuery(query);
//
//                            while(queryResult.next()) {
//
//                                // get values from each retrieved orders, add to local list
//                                orderList.add(new Order(
//                                    queryResult.getInt("orderID"), 
//                                    queryResult.getInt("customerID"), 
//                                    queryResult.getDouble("totalCost"),
//                                    queryResult.getDouble("deliveryCost"),
//                                    queryResult.getString("deliverySchedule"),
//                                    queryResult.getArrayList("orderItems")));  // need to add ordered items to a string, or a whole another table
//                            } 
//                        } catch (Exception e) {
//                            System.out.println("Exception" + e.getMessage());
//                        }                           
//
//                        // finish up!
//                        while(orderList.size()>0)
//                            out.writeObject(orderList.removeFirst());
//                        if (orderList.size() == 0)
//                            out.writeObject(new Order("finished"));
                    } 
                    

                } // end while() block for all if else statements
            
            // catch for try() block
            } catch(EOFException e) {
                 System.out.println("EOFile Exception:"+e.getMessage());
            } catch(IOException | ClassNotFoundException e) {
               System.out.println("IO Exception:"+e.getMessage());
            } catch (SQLException e) {
                System.out.println("SQL Exception:"+e.getMessage());
            }

        } // end run()

    } // end Connection class

}
