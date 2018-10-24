package com.dit.thecampusculinaries;

import java.util.List;

public class OrderItems {
    private String quantity;
    private String item_name;
    private String price;

    //Constructors

    public OrderItems(String quantity, String item_name, String price) {
        this.quantity = quantity;
        this.item_name = item_name;
        this.price = price;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
