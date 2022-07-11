/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.domain;
import java.io.Serializable;

/**
 *
 * @author George Price
 */

public class Customer extends User implements Serializable {
    private String deliveryAddress;
    private static final long serialVersionUID = 11L;

    public Customer (int id){
        super(id);
    }
    
    // regular customer class constructor
    public Customer(String firstName, String lastName, String email, 
                String phone, String deliveryAddress, String password) {
        super(firstName, lastName, email, phone, password);
        this.deliveryAddress = deliveryAddress;
    }
    
    // constructor for logging in
    public Customer(String firstName, String lastName, String email, String phone, 
                String deliveryAddress, String password, boolean isAdmin) {
        super(firstName, lastName, email, phone, password, isAdmin);
        this.deliveryAddress = deliveryAddress;
    }
    
    // constructor for orders
    public Customer(int id, String firstName, String lastName, String email, String phone, 
                String deliveryAddress, String password, boolean isAdmin) {
        super(id, firstName, lastName, email, phone, password, isAdmin);
        this.deliveryAddress = deliveryAddress;
    } 
    
    // Constructor for Customer objects in streams
    public Customer(Customer another) {      
       this( another.getId(), another.getFirstName(),another.getLastName(),
                another.getEmail(),another.getPhone(), 
                    another.getDeliveryAddress(), another.getPassword(), another.getIsAdmin());        
    } 

    // Constructor to insert finshed command 
    public Customer(String firstName) {
        super(firstName);
    }
       
    // getters and setters
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String address) {
        this.deliveryAddress = address;
    }
    
    @Override
    public String toString() {
        return "Customer: " + getFirstName() + " " + getLastName()
                + "\nPhone: " + getPhone() 
                + "\nEmail: " + getEmail() 
                + "\nAddress: " + deliveryAddress
                + "\nAdmin: " + getIsAdmin()
                + "\nID: " + getId();
    }
    
}
