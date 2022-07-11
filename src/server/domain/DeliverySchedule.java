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

public class DeliverySchedule implements Serializable {
    private int postcode;
    private String deliveryDay;
    private double cost;
    private static final long serialVersionUID = 2L;

    public DeliverySchedule(String deliveryDay, int postcode, double cost) {
        this.postcode = postcode;
        this.deliveryDay = deliveryDay;
        this.cost = cost;
    }
    
    // Constructor for Product objects in streams
    public DeliverySchedule(DeliverySchedule another) {      
       this(another.getDeliveryDay(),another.getPostcode(),
                another.getCost()
       );        
    } 
    
    // Constructor to insert finshed command
    public DeliverySchedule(String deliveryDay) {
        this.deliveryDay = deliveryDay;
        this.postcode = 0;
        this.cost = 0.0;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
        // toString ouput option for DeliverySchedule object
    @Override
    public String toString() {
        return "Postcode: " + postcode 
                + ",  Day: " + deliveryDay
                + ",  Cost: $" + cost;
    }
    
}
