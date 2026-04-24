package com.teamfive.dauntlessdungeoneer.items;

public class Item {
    //every item has an ID, a name,and a type
    //every item also has a quantity
    //but is this the best implementation?
    private int itemId;
    private String name;
    private String type;
    private int quantity;

    public Item(int itemId, String name, String type, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }

    //
    public String getName() { return name; }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
}
