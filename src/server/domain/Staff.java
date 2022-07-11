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

public class Staff extends User implements Serializable {
    private static final long serialVersionUID = 111L;

    public Staff (int id){
        super(id);
    }
    
    // regular Staff class constructor
    public Staff(String firstName, String lastName, String email, 
                String phone, String password) {
        super(firstName, lastName, email, phone, password);
    }
    
    // constructor for logging in
    public Staff(String firstName, String lastName, String email, String phone,
                String password, boolean isAdmin) {
        super(firstName, lastName, email, phone, password, isAdmin);
    }
    
    // constructor for orders
    public Staff(int id, String firstName, String lastName, String email, 
                String phone, String password, boolean isAdmin) {
        super(id, firstName, lastName, email, phone, password, isAdmin);
    } 
    
    // Constructor for Staff objects in streams
    public Staff(Staff another) {      
       this( another.getId(), another.getFirstName(),another.getLastName(),
                another.getEmail(),another.getPhone(), another.getPassword(), 
                another.getIsAdmin());        
    } 

    // Constructor to insert finshed command 
    public Staff(String firstName) {
        super(firstName);
    }
    
    @Override
    public String toString() {
        return "Customer: " + getFirstName() + " " + getLastName()
                + "\nPhone: " + getPhone() 
                + "\nEmail: " + getEmail() 
                + "\nAdmin: " + getIsAdmin()
                + "\nID: " + getId();
    }
    
}
