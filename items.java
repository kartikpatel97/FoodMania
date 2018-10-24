package com.dit.thecampusculinaries;

public class items {
    private int id;
    private String item_name;
    private int price;

    //Constructors

    public items(int id,String item_name, int price) {
        this.id = id;
        this.item_name = item_name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}