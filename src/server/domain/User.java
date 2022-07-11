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

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private boolean isAdmin;
    private int id;
    private static final long serialVersionUID = 1L;

    public User(int id){
        this.id = id;
    }
    
    // default User constructor
    public User(String firstName, String lastName, String email, 
                String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
    
    // constructor for User logging in
    public User(String firstName, String lastName, String email, 
            String phone, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    
    // constructor for User orders
    public User(int id, String firstName, String lastName, String email, 
            String phone, String password, boolean isAdmin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isAdmin = isAdmin;
    } 
    
    // Constructor for User objects in streams
    public User(User another) {      
       this( another.getId(), another.getFirstName(),another.getLastName(),
                another.getEmail(),another.getPhone(), another.getPassword(),
                    another.getIsAdmin());        
    } 

    // Constructor to insert finshed command 
    public User(String firstName) {
        this.firstName = firstName;
        this.lastName = "";
        this.email = "";
        this.phone = "";
        this.password = "";
    }
       
    // getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getIsAdmin() {
        return isAdmin;
    }
    
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName 
                + "\nPhone: " + phone 
                + "\nEmail: " + email 
                + "\nAdmin: " + isAdmin
                + "\nID: " + id;
    }
    
}
