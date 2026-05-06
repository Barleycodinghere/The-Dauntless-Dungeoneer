package com.teamfive.dauntlessdungeoneer.components;

public class StatsFactory {
    public static StatsComponent createStats(CharacterClass characterClass) {

        if (characterClass instanceof PlayerClass pc) {
            return switch (pc) {

                case DPS -> new StatsComponent(
                    80,     // HP
                    50,         // Mana
                    60,          // AC
                    20,         // Speed
                    15,         // ATK
                    5           // DEF
                );

                case TANK -> new StatsComponent(
                    150,
                    30,
                    15,
                    4,
                    8,
                    12
                );

                case SUPPORT -> new StatsComponent(
                    90,
                    100,
                    6,
                    8,
                    7,
                    6
                );
            };
        } else if (characterClass instanceof MonsterClass mc) {
            return switch (mc) {

                case GOBLIN -> new StatsComponent(
                    20,     //1/4 of DPS HP
                    12,     //1/4 of DPS Mana
                    60,     //DPS AC
                    20,     //DPS Speed
                    15,     //DPS ATK
                    5       //DPS DEF
                );

                case DOG -> new StatsComponent(
                    38,     //1/4 of Tank HP
                    8,      //1/4 of Tank Mana
                    15,     //Tank AC
                    4,      //Tank Speed
                    8,      //Tank ATK
                    12      //Tank DEF
                );
            };
        }
        // Default fallback
        return new StatsComponent(50, 50, 10, 10, 10, 10);
    }
}
