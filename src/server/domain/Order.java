/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class Order implements Serializable {
    
    private int orderID;
    private int customerID;
    private double totalCost;
    private double deliveryCost;
    private String deliverySchedule;
    private ArrayList<Product> orderItems;
    private static final long serialVersionUID = 3L;

    // paremeterless consctructor
    public Order(){
    }

    // default Order constructor
    public Order(int orderID, int customerID, double totalCost, double deliveryCost, 
            String deliverySchedule, ArrayList<Product> orderItems) {
        
        this.orderID = orderID;
        this.customerID = customerID;
        this.totalCost = totalCost;
        this.deliveryCost = deliveryCost;
        this.deliverySchedule = deliverySchedule;
        this.orderItems = orderItems;
    }
    
    // Constructor to insert finshed command 
    public Order(String finished) {
        this.deliverySchedule = deliverySchedule;
    }

    // getters and setters for order values
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDeliverySchedule() {
        return deliverySchedule;
    }

    public void setDeliverySchedule(String deliverySchedule) {
        this.deliverySchedule = deliverySchedule;
    }

    public ArrayList<Product> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<Product> orderItems) {
        this.orderItems = orderItems;
    }

    // generic toString for Order class
    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", customerID=" + customerID +
                ", totalCost=" + totalCost + ", deliveryCost=" + deliveryCost + 
                ", deliverySchedule=" + deliverySchedule + ", orderItems=" + orderItems + '}';
    }      
    
}
