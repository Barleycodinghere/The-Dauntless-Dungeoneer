package com.teamfive.dauntlessdungeoneer.items;

public class Item {
    //every item has an ID, a name,and a type
    //every item also has a quantity
    //but is this the best implementation?
    private int itemId;
    private String name;
    private int type;
    //type 1 is weapon, type 2 is armor, type 3 is consumable
    private int statBonus;
    /*the idea is that we use a switch case checking the type:
    * if type 1, the attack is raised when equipped
    * if type 2, the defense is raised when equipped
    * if type 3, a message is returned telling you that you are unable to equip them
    * */
    private int quantity;
    // for items of types 1 and 2, the quantity should be hard capped at 1,
    // and type 3 should be capped at 10

    public Item(int itemId, String name, int type, int statBonus, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.statBonus = statBonus;
        this.quantity = quantity;
    }

    //
    public String getName() { return name; }
    public int getType() { return type; }
    public int getStatBonus() {return statBonus;}
    public int getQuantity() { return quantity; }
}
