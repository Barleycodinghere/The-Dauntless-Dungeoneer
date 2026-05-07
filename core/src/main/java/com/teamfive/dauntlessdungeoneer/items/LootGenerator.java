package com.teamfive.dauntlessdungeoneer.items;
import java.util.Random;

public class LootGenerator {

    private static final Random rand = new Random();

    // The loot pool
    private static final Item[] lootPool = {
        new Item(1,"Short Sword",1,10,1),
        new Item(2,"Broad Sword",1,50,1),
        new Item(3,"Magic Orb",1,20,1),
        new Item(4,"Staff",1,30,1),
        new Item(5,"Shield",2,10,1),
        new Item(6,"Light Armor",2,30,1),
        new Item(7,"Heavy Armor",2,50,1),
        new Item(8,"Recovery Potion",3,50,1),
        new Item(9,"Harmful Potion",3,20,1),
        new Item(10,"Harmful++ Potion",3,100,1),
    };

    // Random item from pool
    public static Item generateRandomItem() {
        int i = rand.nextInt(lootPool.length);

        Item base = lootPool[i];

        // IMPORTANT: return a NEW instance so quantities don't share
        return new Item(
            base.getItemId(),
            base.getName(),
            base.getType(),
            base.getStatBonus(),
            1
        );
    }

    //chance roll (percent = 50 is 50%)
    public static boolean rollDropChance(int percent) {
        return rand.nextInt(100) < percent;
    }
}
