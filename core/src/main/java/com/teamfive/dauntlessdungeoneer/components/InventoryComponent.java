package com.teamfive.dauntlessdungeoneer.components;
import com.teamfive.dauntlessdungeoneer.ecs.Component;
import com.teamfive.dauntlessdungeoneer.items.Item;

import java.util.ArrayList;
import java.util.List;

public class InventoryComponent implements Component {

    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }
}

