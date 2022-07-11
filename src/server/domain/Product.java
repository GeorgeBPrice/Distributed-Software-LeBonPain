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

public class Product implements Serializable {
    private String name;
    private String unit;
    private String ingredients;
    private int quantity;
    private double price;
    private int id;
    private static final long serialVersionUID = 4L;


    // parametised object constructor 
    public Product(String name, String unit, String ingredients, 
        int quantity, double price, int id) {
        this.name = name;
        this.unit = unit;
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
    }

    // Constructor for Product objects in streams
    public Product(Product another) {      
       this(another.getName(),another.getUnit(),
                another.getIngredients(),another.getQuantity(), 
                    another.getPrice(), another.getId());        
    } 
    
    // Constructor to insert finshed command "done"
    public Product(String name) {
        this.name = name;
        this.unit = "";
        this.ingredients = "";
        this.quantity = 0;
        this.price = 0.0;
        this.id = id;
    }
    
    // Constructor to pass ID of product to be deleted
    public Product(Integer id) {
        this.name = "";
        this.unit = "";
        this.ingredients = "";
        this.quantity = 0;
        this.price = 0.0;
        this.id = id;
    }
    
    // getters and setters for Product object
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getId() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    

    // toString ouput option for Product object, as a string
    @Override
    public String toString() {
       
        return "Product: " + name + ", " + quantity + " " 
                + unit + ", $" + price + "\nContains: " + ingredients;        
    }
    
}
