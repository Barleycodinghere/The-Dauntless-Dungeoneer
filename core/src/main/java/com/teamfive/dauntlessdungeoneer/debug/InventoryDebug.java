package com.teamfive.dauntlessdungeoneer.debug;

import com.teamfive.dauntlessdungeoneer.items.Item;
import com.teamfive.dauntlessdungeoneer.components.InventoryComponent;


public class InventoryDebug {

    public static void main(String[] args) {

        InventoryComponent inv = new InventoryComponent();

        Item lightSword = new Item(1,"Short Sword",1,10,1);
        Item heavySword = new Item(2,"Broad Sword",1,50,1);
        Item orb = new Item(3,"Magic Orb", 1,20,1);
        Item staff = new Item(4,"Staff",1,30,1);
        Item shield = new Item(5,"Shield",2,10,1);
        Item lightArmor = new Item(6,"Light Armor",2,30,1);
        Item heavyArmor = new Item(7,"Heavy Armor",2,50,1);
        Item recoveryPotion = new Item(8,"Recovery Potion",3,50,2);
        Item damagePotion = new Item(9,"Harmful Potion",3,20,2);


        System.out.println("=== ADDING ITEMS ===");
        inv.addItem(lightSword);
        inv.getInventory();
        System.out.println();

        inv.addItem(recoveryPotion); // you get potions in twos
        inv.getInventory();
        System.out.println();

        inv.addItem(recoveryPotion); // should stack with first two potions
        inv.getInventory();
        System.out.println();

        System.out.println("=== REMOVING ITEMS ===");
        inv.removeItem(recoveryPotion); // remove potion (should decrement)
        inv.getInventory();
        System.out.println();

        inv.removeItem(recoveryPotion); // remove again (should delete slot if 0)
        inv.getInventory();
        System.out.println();

        inv.removeItem(recoveryPotion); // remove again (should delete slot if 0)
        inv.getInventory();
        System.out.println();

        inv.removeItem(heavyArmor); // not found
        inv.getInventory();
        System.out.println();

    }
}
