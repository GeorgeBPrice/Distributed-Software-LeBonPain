/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.DbConnection;
import server.domain.Customer;
import server.domain.DeliverySchedule;
import server.domain.Order;
import server.domain.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javax.swing.JOptionPane;
import java.util.Optional;
import javafx.scene.control.ButtonType;

/**
 * Main FXML Controller class
 *
 * @authors George Price
 */

public class ProtoypeController {

    @FXML
    private Tab adminTab;
    
    @FXML
    private Tab adminTabTwo;
    
    @FXML
    private Tab customerTab;

    @FXML
    private Tab homeTab;
    
    @FXML
    private Tab registerTab;
    
    @FXML
    private Tab checkoutTab;
    
    @FXML
    private TextField inputUserName;
    
    @FXML PasswordField inputUserPassword;

    @FXML
    private TextField inputFldEmailAddress;

    @FXML
    private TextField inputFldFirstName;

    @FXML
    private TextField inputFldLastName;

    @FXML
    private TextField inputFldPhone;

    @FXML
    private TextField inputFldProductName;
    
    @FXML
    private TextField inputFldPostcode;
    
    @FXML
    private TextField inputFldCost;
    
    @FXML
    private PasswordField inputFldPassword;
    
    @FXML
    private TextField inputEditPrice;

    @FXML
    private TextField inputEditProductName;

    @FXML
    private TextField inputEditQuantity;

    @FXML
    private TextField inputEditUnit;
    
    @FXML
    private TextField inputProductID;
    
    @FXML
    private TextArea txtAreaEditIngredients;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnClear;
    
    @FXML
    private Button btnSelectProduct;

    @FXML
    private Button btnClearProduct;
    
    @FXML
    private Button btnClearEditProduct;

    @FXML
    private Button btnDisplaySchedule;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnShowProducts;
    
    @FXML
    private Button btnAddDeliverySchedule;
    
    @FXML
    private Button btnClearSchedule;
    
    @FXML
    private Button btnShowCustomers;
    
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;
        
    @FXML
    private Button btnSearchCustomers;
    
    @FXML
    private Button btnShowProductsAdmin;
    
    @FXML
    private Button btnEditProduct;
        
    @FXML
    private Button btnDeleteProduct;
    
    @FXML
    private Button btnAddToCart;
    
    @FXML
    private Button btnViewCart;
            
    @FXML
    private Button btnCheckout;
    
    @FXML
    private Label labelRegisterSuccess;

    @FXML
    private Label labelPleaseSelect;    
    
    @FXML
    private TextField inputFldSearchCustomers;

    @FXML
    private TextField inputFldQuantity;

    @FXML
    private ComboBox<String> unitsCombobox;
    
    @FXML
    private ComboBox<String> daysCombobox;

    @FXML
    private TextField inputFldUnitPrice;

    @FXML
    private AnchorPane paneAdmin;

    @FXML
    private AnchorPane paneCustomer;

    @FXML
    private AnchorPane paneHome;
    
    @FXML
    private AnchorPane paneRegister;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private RadioButton radioBtnAdmin;

    @FXML
    private RadioButton radioBtnCustomer;
    
    @FXML
    private TextArea txtAreaDeliveryAddress;

    @FXML
    private TextArea txtAreaProdIngredients;
    
    @FXML
    private TextArea txtAreaErrorMessages;
    
    @FXML
    private TextArea txtAreaErrorMessagesTwo;

    @FXML
    private TableView<DeliverySchedule> scheduleTable;
    
    @FXML
    private TableColumn<DeliverySchedule, Integer> scheduleCol1;

    @FXML
    private TableColumn<DeliverySchedule, String> scheduleCol2;

    @FXML
    private TableColumn<DeliverySchedule, Double> scheduleCol3;
    
    @FXML
    private TableView<Product> productTable;
    
    @FXML
    private TableView<Product> productTableAdmin;
        
    @FXML
    private TableColumn<Product, String> productColIDAdmin;
            
    @FXML
    private TableColumn<Product, String> productCol1Admin;

    @FXML
    private TableColumn<Product, Integer> productCol2Admin;

    @FXML
    private TableColumn<Product, Integer> productCol3Admin;

    @FXML
    private TableColumn<Product, Double> productCol4Admin;

    @FXML
    private TableColumn<Product, String> productCol5Admin;
    
    @FXML
    private TableView<Customer> customerTable;
    
    @FXML
    private TableColumn<Customer, String> customerCol1;

    @FXML
    private TableColumn<Customer, String> customerCol2;

    @FXML
    private TableColumn<Customer, String> customerCol3;

    @FXML
    private TableColumn<Customer, String> customerCol4;

    @FXML
    private TableColumn<Customer, String> customerCol5;  
    
    @FXML
    private Button btnShopClearCart;
    
    @FXML
    private Button btnOrderShowOrder;
    
    @FXML
    private Button btnShowCustomerOrders;
         
    @FXML
    private TableView<Product> productTableShop;
    
    @FXML
    private TableView<Product> productTableCart;
    
    @FXML
    private TableView<Order> orderTable;
    
    @FXML
    private TableColumn<Product, String> productColShopID;
     
    @FXML
    private TableColumn<Product, String> productColShopName;
    
    @FXML
    private TableColumn<Product, Integer> productColShopQty;
    
    @FXML
    private TableColumn<Product, Integer> productColShopUnit;
    
    @FXML
    private TableColumn<Product, Double> productColShopPrice;
    
    @FXML
    private TableColumn<Product, String> productColShopIngr;
    
    @FXML
    private TableColumn<Product, String> productColCartName;
    
    @FXML
    private TableColumn<Product, Integer> productColCartQty;
    
    @FXML
    private TableColumn<Product, Double> productColCartPrice;
    
    @FXML
    private TableColumn<Order, Integer> orderTableOrderNumber;

    @FXML
    private TableColumn<Order, String> orderTableCustomerName;

    @FXML
    private TableColumn<Order, String> orderTableProductName;
    
    @FXML
    private TableView<Order> orderTableAdmin;
    
    @FXML
    private TableColumn<Order, Integer> orderColOrderNumber;

    @FXML
    private TableColumn<Order, String> orderColCustomer;

    @FXML
    private TableColumn<Order, String> orderColProduct;
    
    
    /* Declare client */
    PrototypeClient prototypeClient;
    
    /* Declare LinkedLists for class objects */
    private LinkedList<Product> productList;
    private LinkedList<Customer> customerList;
    private LinkedList<DeliverySchedule> scheduleList;
    private LinkedList<Order> orderList;
    private LinkedList<Product> productResult;
    private LinkedList<Customer> customerResult;
    private LinkedList<DeliverySchedule> scheduleResult;
    private LinkedList<Order> orderResult;
    private LinkedList<Customer> logins;
    private LinkedList<Order> orderDetails;
    
    //variables for determining login type
    String username, loginPassword; // login
    String checkUsername, checkLoginPassword; //Verifying user login
    boolean checkAdmin;
    boolean checkIsAdmin;
    boolean customerLoggedIn;
    boolean adminLoggedIn;
    boolean isAdmin = false;
    
    /* Declare TableViews */
    TableView tableView;
    
    /* Input and control variables */
    // for DeliverySchedule
    String deliveryDay;
    Integer postcode;
    Double cost;
    // for Customer
    String firstName;
    String lastName;
    String email;
    String phone;
    String deliveryAddress;
    String password;
    // for Product
    String name;
    String unit;
    String ingredients;
    Integer quantity;
    Double price;
    Integer id;
    // for search & delete functions
    String searchWord;
    //To get login session
    String login;    
    
    // initialising the controller class, linked lists and comboboxes
    public void initialize() throws NoSuchAlgorithmException {
        
        // Initialise the LinkedLists and their objects
        productList = new LinkedList<>();
        customerList = new LinkedList<>();
        scheduleList = new LinkedList<>();
        orderList = new LinkedList<>();
        orderDetails = new LinkedList<>();
        productResult = new LinkedList<>();
        customerResult = new LinkedList<>();
        scheduleResult = new LinkedList<>();
        orderResult = new LinkedList<>();
        logins = new LinkedList<>();
        
        // set admin screen customer table view cells
        customerCol1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerCol2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customerCol3.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerCol4.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerCol5.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
        
        // set product table view cells
        productColIDAdmin.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCol1Admin.setCellValueFactory(new PropertyValueFactory<>("name"));
        productCol2Admin.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productCol3Admin.setCellValueFactory(new PropertyValueFactory<>("unit"));
        productCol4Admin.setCellValueFactory(new PropertyValueFactory<>("price"));
        productCol5Admin.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        
        // set shop screen product table view cells
        productColShopID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColShopName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productColShopQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productColShopUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        productColShopPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productColShopIngr.setCellValueFactory(new PropertyValueFactory<>("ingredients"));        
        
        // set shop screen cart table view cells
        productColCartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productColCartQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productColCartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // set order list table cells
        orderTableOrderNumber.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderTableCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        orderTableProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
                       
        // set order list table cells for Admin screen
        orderColOrderNumber.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderColCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        orderColProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));        
        
        // set client screen schedule table view cells
        scheduleCol1.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        scheduleCol2.setCellValueFactory(new PropertyValueFactory<>("deliveryDay"));
        scheduleCol3.setCellValueFactory(new PropertyValueFactory<>("cost"));

        // Declare, populate and set the unitsCombobox
        ObservableList<String> units = FXCollections.observableArrayList(
                "ml","litre","gm");
        unitsCombobox.setItems(units);
        
        // Declare, populate and set the daysCombobox
        ObservableList<String> days = FXCollections.observableArrayList(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday");
        daysCombobox.setItems(days);
        
        // then try initialise the Prototype TCP Client
        try {
            prototypeClient = new PrototypeClient();
            System.out.println("Prototype MDHS Client Launched!"); 
            tabPane.getTabs().remove(customerTab);
            tabPane.getTabs().remove(adminTab);
            tabPane.getTabs().remove(adminTabTwo);
            tabPane.getTabs().remove(checkoutTab);
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }
    } 
    
    @FXML
    private void btnHomeScreenOnClick(ActionEvent event) {
        // Switch to Customer Tab pane
        tabPane.getSelectionModel().select(homeTab);
    }
    
    @FXML
    private void btnRegisterOnClick(ActionEvent event) {
        // Switch to Customer Tab pane
        tabPane.getSelectionModel().select(registerTab);
    }
    
    @FXML
    void btnLoginOnClick(ActionEvent event) throws InvalidKeySpecException, Exception {
        
        // set login details as variables
        username = inputUserName.getText();
        password = inputUserPassword.getText();
        String loginType = "";  
        login = username;
        
        
        // Simple validation to check input fields are not empty
        if (inputUserName.getText().isEmpty() || inputUserPassword.getText().isEmpty()) {           
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Incomplete");
            alert.setHeaderText("Empty Login Fields.");
            alert.setContentText("Please enter your Login details");
            alert.showAndWait(); 

        } else {  
        
            // connect with client and pass login details to Server for processing
            loginType = this.prototypeClient.receiveCustomerLogin(username, password);

            if (loginType.equals("Incorrect")) {
                // Simple alert if User login details are incorrect
                Alert alertUserNotFound = new Alert(AlertType.INFORMATION);
                alertUserNotFound.setTitle("User Not Found");
                alertUserNotFound.setContentText("An incorrect email address or "
                        + "password was entered, \nplease try again.");
                alertUserNotFound.showAndWait();
            }
            
            else if (loginType.equals("Admin")) {
                adminLoggedIn = true;
                System.out.println("Admin Credentials supplied. Full System Functionality Applied.");
                tabPane.getTabs().add(adminTab);
                tabPane.getTabs().add(adminTabTwo);
                tabPane.getTabs().add(customerTab);
                tabPane.getTabs().add(checkoutTab);
                tabPane.getSelectionModel().select(adminTab);             
            }
            
            else if (loginType.equals("Customer")) {
                customerLoggedIn = true;
                System.out.println("Username and Password match. Logging in.");
                tabPane.getTabs().remove(registerTab);
                tabPane.getTabs().add(customerTab);
                tabPane.getTabs().add(checkoutTab);
                tabPane.getSelectionModel().select(customerTab);               
            }

        } 
    }
    
    // Displays delivery schedules in the CUSTOMER screen
    @FXML
    void btnDisplayScheduleOnClick(ActionEvent event) {      

        // connect with client and retrieve delivery schedules from MYSQL database
        this.scheduleResult = this.prototypeClient.receiveDeliverySchedule();
        
        // clears cells in the table (stops doubling up data output)
        scheduleTable.getItems().clear();                
        
        // ouput the delivery schedule to the GUI tableView
        for (DeliverySchedule sched : scheduleResult) {
            scheduleTable.getItems().add(new DeliverySchedule(sched));
        }
        
    }

    // Displays products in the CUSTOMER screen
    @FXML
    void btnShowProductsOnClick(ActionEvent event) {
        
        // connect with client and retrieve prodcts from MYSQL database
        this.productResult = this.prototypeClient.receiveProduct();
        
        // clears cells in the table (stops doubling up data output)
        productTable.getItems().clear();               

        // ouput the products to the GUI tableView
        for (Product prod : productResult) {
            productTable.getItems().add(new Product(prod));
        }
    }
    
    // Displays products in the ADMIN screen
    @FXML
    void btnShowProductsAdminOnClick(ActionEvent event) {       
        // display products on Tableview, using refresh script
        refreshListedProducts();
    }
    
    // displays customers in the ADMIN screen
    @FXML
    void btnShowCustomersOnClick(ActionEvent event) throws SQLException {
               
        // connect with client and retrieve customers from MYSQL database
        this.customerResult = this.prototypeClient.receiveCustomer();
        
        // clears cells in the table (stops doubling up data output)
        customerTable.getItems().clear();        
       
        // ouput the customers to the GUI tableView
        for (Customer customer : customerResult) {
            customerTable.getItems().add(new Customer(customer));
        }
           
    }
    
    // button searches through customers in the database, on the ADMIN screen    
    @FXML
    void btnSearchCustomersOnClick(ActionEvent event) throws SQLException, Exception {     
        
         // connect with client and retrieve customers from MYSQL database
        this.customerResult = this.prototypeClient.receiveCustomer();
        
        // clears cells in the table (stops doubling up data output)
        customerTable.getItems().clear();        

        // ouput the customers to the GUI tableView
        searchWord = inputFldSearchCustomers.getText();
        
        for (Customer customer : customerResult) {
            
            if (inputFldSearchCustomers.getText().isEmpty()) {
                txtAreaErrorMessagesTwo.setText("Please enter a search value, "
                        + "such as a name, email address, or phone number.");
            } else if (customer.toString().contains(searchWord)) {              
                customerTable.getItems().add(new Customer(customer));
            } 
        }              
    }    
    
    // button ActionEvent to register new customer to database
    @FXML
    void btnAddCustomerOnClick(ActionEvent event) throws SQLException {        
        
        // Assign local variables to their related JavaFX GUI fields
        firstName = inputFldFirstName.getText();
        lastName = inputFldLastName.getText();
        email = inputFldEmailAddress.getText();
        phone = inputFldPhone.getText();
        deliveryAddress = txtAreaDeliveryAddress.getText();
        password = inputFldPassword.getText();
        
        // Initialise checkboxes for registration testing purposes only
        ToggleGroup accountType = new ToggleGroup();
        radioBtnCustomer.setToggleGroup(accountType);
        radioBtnAdmin.setToggleGroup(accountType);
        
        // Alert for validation check
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Attention");
        alert.setHeaderText("Empty Fields");
        alert.setContentText("Please fill all the fields, thank you.");
        
        // Simple validation to check GUI input fields are not empty
        if (       inputFldFirstName.getText().isEmpty() 
                || inputFldLastName.getText().isEmpty()
                || inputFldEmailAddress.getText().isEmpty()
                || inputFldPhone.getText().isEmpty()
                || txtAreaDeliveryAddress.getText().isEmpty()
                || inputFldPassword.getText().isEmpty()) {
            alert.showAndWait();           
        } else {                          
            
            
            // determine what type of user account checkbox is selected
            if (radioBtnAdmin.isSelected()) {
                isAdmin = true;
            } else {
                isAdmin = false;
            }

            // Pass local values and add them to a new Customer object
            customerList.add(new Customer(firstName, lastName, email, 
                phone, deliveryAddress, password, isAdmin));
                       
            // send customer data to SERVER side, writing to the SQL database
            System.out.println("Client connection from controller initiated!");
            this.prototypeClient.sendCustomer(customerList);        
            
            // send success message to GUI
            labelRegisterSuccess.setVisible(true);

            // CLEAR FIELDS and OBJECT
            this.customerList.clear();
            clearCustomer();
        }
    }
    
    // button ActionEvent to add new product to database
    @FXML
    void btnAddProductOnClick(ActionEvent event) {
        
        // Assign local text variables to their related JavaFX GUI fields
        id = 0; //ID placeholder
        name = inputFldProductName.getText();
        unit = (String)unitsCombobox.getValue();
        ingredients = txtAreaProdIngredients.getText();
   
        // Simple validation to check GUI input fields are not empty, and numbers
        if (       inputFldProductName.getText().isEmpty() 
                || txtAreaProdIngredients.getText().isEmpty()
                || unitsCombobox.getSelectionModel().isEmpty()
                || inputFldQuantity.getText() != null 
                && !inputFldQuantity.getText().matches("\\d+")
                || inputFldUnitPrice.getText() == null 
                && !inputFldUnitPrice.getText().matches("\\d+\\.\\d+")) {           
            txtAreaErrorMessages.setText("\nPlease correctly fill all fields!");            
        } else {
            
            // parse integers and doubles, and assign to local variables
            quantity = Integer.parseInt(inputFldQuantity.getText());
            price = Double.parseDouble(inputFldUnitPrice.getText());

            // Take GUI field values and add them to a new Product object
            productList.add(new Product(name, unit, ingredients, quantity, price, id));

            // Success message to GUI
            txtAreaErrorMessages.setText("Product sucessfully added:" 
                    + this.productList); 

            // send product to SERVER side, writing to the SQL database
            System.out.println("Client connection from controller initiated!");
            this.prototypeClient.sendProduct(productList); 
            
            // REFRESH the products Tableview
            refreshListedProducts();            

            // CLEAR FIELDS and OBJECT
            this.productList.clear();
            clearProduct();

        }
    }
    
    // ActionEvents for admin to edit products
    @FXML
    void btnEditProductOnClick(ActionEvent event) {

        // Assign local text variables to their related JavaFX GUI fields
        name = inputEditProductName.getText();
        unit = inputEditUnit.getText();
        ingredients = txtAreaEditIngredients.getText();
   
        // Simple validation to check GUI input fields are not empty, and numbers
        if (       inputEditProductName.getText().isEmpty() 
                || inputEditUnit.getText().isEmpty()
                || txtAreaEditIngredients.getText().isEmpty()
                || inputEditQuantity.getText() != null 
                && !inputEditQuantity.getText().matches("\\d+")
                || inputEditPrice.getText() == null 
                && !inputEditPrice.getText().matches("\\d+\\.\\d+")
                || inputProductID.getText() == null
                && !inputProductID.getText().matches("\\d+")) {           
            txtAreaErrorMessages.setText("\nPlease correctly fill all fields!");            
        } else {
            
            // parse integers and doubles, and assign to local variables
            quantity = Integer.parseInt(inputEditQuantity.getText());
            price = Double.parseDouble(inputEditPrice.getText());
            id = Integer.parseInt(inputProductID.getText());

            // Take GUI field values and add them to a new Product object
            productList.add(new Product(name, unit, ingredients, quantity, price, id));
            
            // Success message to GUI
            txtAreaErrorMessages.setText("Product sucessfully edited:" 
                    + this.productList);
           
            // send product to SERVER side, writing to the SQL database
            System.out.println("Client connection from controller initiated!");
            this.prototypeClient.editProduct(productList); 

            // REFRESH the products Tableview
            refreshListedProducts();

            // CLEAR FIELDS and OBJECT
            this.productList.clear();
            clearProductEdit();            
        }
    }
    
    // button to delete products in Admin screen
    @FXML
    void btnDeleteProductOnClick(ActionEvent event) {
         
        // Add the selected tableview row as a local Product object
        Product selectedProduct = productTableAdmin.getSelectionModel().getSelectedItem();
        
        // convert the product ID found in the selected table 
        String productId = Integer.toString(selectedProduct.getId());
        
        // Alert for delete confirmation
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setContentText("Are you sure you want to DELETE this product?");
        
        if (!productId.isEmpty()) {
          
            // process delete request if OK is clicked
            Optional<ButtonType> btnClicked = alert.showAndWait();
            
            if (btnClicked.get() == ButtonType.OK){
                // parse integers and doubles, and assign to local variables
                id = Integer.parseInt(productId);

                // Take GUI field values and add them to a new Product object
                productList.add(new Product(id));

                // Success message to GUI
                txtAreaErrorMessages.setText("Product sucessfully deleted!");

                // send product to SERVER side, writing to the SQL database
                System.out.println("Client connection from controller initiated!");
                this.prototypeClient.deleteProduct(productList);

                // REFRESH the products Tableview
                refreshListedProducts();          

                // CLEAR FIELDS and OBJECT
                this.productList.clear();                                        
            }
            
            else {
                // do nothing, cancel leaves alert popup
            }
        }
    }
    
    // button to select products for editting in Admin screen
    @FXML
    void btnSelectProductOnClick(ActionEvent event) {
        
        // Add the selected tableview row as a local Product object
        Product selectedProduct = productTableAdmin.getSelectionModel().getSelectedItem();

        // Assign it's values to the input fields, to prefil them for editing.
        inputEditProductName.setText(selectedProduct.getName());
        inputEditPrice.setText(Double.toString(selectedProduct.getPrice()));  
        txtAreaEditIngredients.setText(selectedProduct.getIngredients());
        inputEditQuantity.setText(Integer.toString(selectedProduct.getQuantity()));
        inputEditUnit.setText(selectedProduct.getUnit());
        inputProductID.setText(Integer.toString(selectedProduct.getId()));       
    }
    
    @FXML
    void refreshListedProducts() {
        // REFRESH the products Tableview
        this.productResult = this.prototypeClient.receiveProduct();

        // clears cells in the table (stops doubling up data output)
        productTableAdmin.getItems().clear();

        // ouput the products to the GUI tableView
        for (Product prod : productResult) {
            productTableAdmin.getItems().add(new Product(prod));
        } 
    
    }
    
    // Displays products in the Customer Shop screen
    @FXML
    void btnShowProductsShopOnClick(ActionEvent event) {
        
        // connect with client and retrieve prodcts from MYSQL database
        this.productResult = this.prototypeClient.receiveProduct();
        
        // clears cells in the table (stops doubling up data output)
        productTableShop.getItems().clear();               

        // ouput the products to the GUI tableView
        
        for (Product prod : productResult) {
            productTableShop.getItems().add(new Product(prod));
        }
    }
    
    // button ActionEvent to add new delivery schedule to database
    @FXML
    void btnAddDeliveryScheduleOnClick(ActionEvent event) {
        
        // Assign local text variables to their related JavaFX GUI fields      
        deliveryDay = (String)daysCombobox.getValue();
            
        // Simple validation to check GUI input fields are not empty, and numbers
        if (       daysCombobox.getSelectionModel().isEmpty()
                || inputFldPostcode.getText() != null 
                && !inputFldPostcode.getText().matches("\\d+")
                || inputFldCost.getText() == null 
                && !inputFldCost.getText().matches("\\d+\\.\\d+")) {           
            txtAreaErrorMessages.setText("\nPlease correctly fill all fields!");            
        } else {
            
            // parse integers and doubles, and assign to local variables
            postcode = Integer.parseInt(inputFldPostcode.getText());
            cost = Double.parseDouble(inputFldCost.getText());              
            
            // Pass GUI field values and add them to a new schedule object
            scheduleList.add(new DeliverySchedule(deliveryDay,postcode,cost));

            // Success message to GUI
            txtAreaErrorMessages.setText("Delivery Schedule sucessfully added:" 
                    + this.scheduleList); 

            // send product to SERVER side, writing to the SQL database
            System.out.println("Client connection from controller initiated!");
            this.prototypeClient.sendDeliverySchedule(scheduleList);                      
            
            // CLEAR FIELDS and OBJECT
            this.scheduleList.clear();
            clearSchedule();
        }              
    }      
    
    // ActionEvents for customer checkout and cart
    // (NOT YET COMPLETED)
    @FXML
    void btnAddToCartOnClick(ActionEvent event) {
        
        // Add the selected tableview row as a local Product object
        Product selectedProduct = productTableShop.getSelectionModel().getSelectedItem();              
        
        if (selectedProduct != null) {
            productTableCart.getItems().add(selectedProduct);
        
        } else {           
            // prompt user to select a product
            labelPleaseSelect.setVisible(true);
        }
    }
    
    // button to place order in Customer shop screen
    // (NOT YET COMPLETED)
    @FXML
    void btnCheckoutOnClick (ActionEvent event){
        // add logic here, checkout coming soon.
        
        // Temporary alert info
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not yet implemented!");
        alert.setContentText("The shopping cart and checkout is not yet completed.");
        
        // display alert
        Optional<ButtonType> btnClicked = alert.showAndWait();

    }
    
    // Display order on Customers screen
    // (NOT YET COMPLETED)
    @FXML
    void btnViewOrderOnClick(ActionEvent event) {
        
        // Temporary alert info
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not yet implemented!");
        alert.setContentText("The order system is not yet completed.");
        
        // display alert
        Optional<ButtonType> btnClicked = alert.showAndWait();
        
//        this.orderResult = this.prototypeClient.displayOrder();
//        
//        // clear table first
//        orderTable.getItems().clear();
//        
//        // output customer orders here
//        for(Order order : orderResult){       
//            orderTable.getItems().add(new Order(order.getOrderID(), order.getCustomerID(),
//                order.getTotalCost(), order.getDeliveryCost(), order.getDeliverySchedule(), 
//                    order.getOrderItems()));
//            
//            System.out.println("Order details: " + order.toString());        
//        }
    }
    
    // Display all customer orders in Admin Screen
    // (NOT YET COMPLETED)
    @FXML
    void btnShowCustomerOrdersOnClick(ActionEvent event) {
        
        // Temporary alert info
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not yet implemented!");
        alert.setContentText("The order system is not yet completed.");
        
        // display alert
        Optional<ButtonType> btnClicked = alert.showAndWait();

//        // connect with client and retrieve orders from MYSQL database
//        this.orderResult = this.prototypeClient.displayOrder();
//        
//        // clears cells in the table (stops doubling up data output)
//        orderTableAdmin.getItems().clear();
//       
//        // add each order to the admin order screens
//        for (Order order : orderResult) {
//            orderTable.getItems().add(new Order(order.getOrderID(), order.getCustomerID(),
//                order.getTotalCost(), order.getDeliveryCost(), order.getDeliverySchedule(), 
//                    order.getOrderItems()));
//        }       
    }
        
    /* 'clear' buttons action events, for any all elements */
    @FXML
    void btnClearOnClick(ActionEvent event) {
        clearCustomer();
    }
    
    @FXML
    void btnClearProdOnClick(ActionEvent event) {    
        clearProduct();
    }
    
    @FXML
    void btnClearEditProductOnClick(ActionEvent event) {    
        clearProductEdit();
    }
    
    @FXML
    void btnClearScheduleOnClick(ActionEvent event) {
        clearSchedule();
    }
    
    @FXML
    void btnShopClearCart(ActionEvent event){
        clearCart();
    }
    
    @FXML
    void btnExitOnClick(ActionEvent event) {
        Platform.exit();
    }   
       
    /* clears the fields in each respective data input screens */
    public void clearProduct(){
        inputFldProductName.clear();
        inputFldQuantity.clear();
        unitsCombobox.valueProperty().set(null);
        inputFldUnitPrice.clear();
        txtAreaProdIngredients.clear();
    }
    
    public void clearProductEdit(){
        inputEditProductName.clear();
        inputEditPrice.clear();
        txtAreaEditIngredients.clear();
        inputEditQuantity.clear();
        inputEditUnit.clear();
        inputProductID.clear();
    }
    
    public void clearCustomer() {
        inputFldFirstName.clear();
        inputFldLastName.clear();  
        inputFldPhone.clear();
        inputFldEmailAddress.clear();
        txtAreaDeliveryAddress.clear();
        inputFldPassword.clear();
    }
    
    public void clearSchedule() {
        inputFldPostcode.clear();
        inputFldCost.clear();
        daysCombobox.valueProperty().set(null);
    }
    
    public void clearCart(){
        orderResult.clear();
        productTableCart.getItems().clear();
    }
    
}
