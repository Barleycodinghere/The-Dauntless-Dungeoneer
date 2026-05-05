package com.teamfive.dauntlessdungeoneer.components;
import com.teamfive.dauntlessdungeoneer.ecs.Component;
import com.teamfive.dauntlessdungeoneer.items.Item;


public class InventoryComponent implements Component {

    //private List<Item> Inventory = new ArrayList<>(8);
    private Item[] Inventory = new Item[8];
    /*
    the program should be able to take an item and add it to the inventory.
    it checks the inventory arraylist to see if something of matching ID,
    if there is one, the quantity goes up by 1, else it'll add it to a new slot
    * */
    public void addItem(Item item) {
            //1. if there is a matching item, it gets stacked.
            for (int i = 0; i < Inventory.length; i++) {
                //note: I added the "if inventory[i] is not null" just in case of null pointer exceptions
                if (Inventory[i] != null && Inventory[i].getItemId() == item.getItemId()) {
                    Inventory[i].addQuantity(1);
                    return;
                }
            }

            //2. there is not matching item, lets check for space.
            for (int i = 0; i < Inventory.length; i++) {
            if (Inventory[i] == null) {
                Inventory[i] = item;
                return;
            }
        }
            //3. there is no matching item, no space either, the item is not added.
            System.out.println("Your Bag is Full!! Drop an item in order to pick up new ones.");
    }

    public void removeItem(Item item) {
        for (int i = 0; i < Inventory.length; i++) {
            if (Inventory[i] != null && Inventory[i].getItemId() == item.getItemId()) {
                //drop 1 copy of the item
                Inventory[i].addQuantity(-1);

                //remove if there are 0 copies
                if (Inventory[i].getQuantity() <= 0) {
                    Inventory[i] = null;
                }
                return;
            }
        }
        //If the item isn't found:
        System.out.println("Item not found.");
    }
    public void getInventory() {
            for (Item item : Inventory) {
            System.out.print("|" + item);
        }
    }
}

