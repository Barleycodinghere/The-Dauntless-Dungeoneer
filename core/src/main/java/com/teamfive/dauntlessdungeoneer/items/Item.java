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

    Item lightSword = new Item(1,"Short Sword",1,10,1);
    Item heavySword = new Item(2,"Broad Sword",1,50,1);
    Item orb = new Item(3,"Magic Orb", 1,20,1);
    Item staff = new Item(4,"Staff",1,30,1);
    Item shield = new Item(5,"Shield",2,10,1);
    Item lightArmor = new Item(6,"Light Armor",2,30,1);
    Item heavyArmor = new Item(7,"Heavy Armor",2,50,1);
    Item recoveryPotion = new Item(8,"Recovery Potion",3,50,2);
    Item damagePotion = new Item(9,"Harmful Potion",3,20,2);





}
