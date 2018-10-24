package com.dit.thecampusculinaries;

import java.util.List;

public class AdminOrderItems {
    private List<OrderItems> orderItems;
    private String orderFor;

    private String name;
    private String email;
    private String total;
    private String orderID;
    private String date;

    public AdminOrderItems(List<OrderItems> orderItems, String orderFor, String name, String email, String total,String orderID,String date) {
        this.orderItems = orderItems;
        this.orderFor = orderFor;
        this.name = name;
        this.email = email;
        this.total = total;
        this.orderID = orderID;
        this.date=date;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderFor() {
        return orderFor;
    }

    public void setOrderFor(String orderFor) {
        this.orderFor = orderFor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
