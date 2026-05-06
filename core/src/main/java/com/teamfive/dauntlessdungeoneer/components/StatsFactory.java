package com.teamfive.dauntlessdungeoneer.components;

public class StatsFactory {
    public static StatsComponent createStats(PlayerClass playerClass) {

        return switch (playerClass) {

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

            case GOBLIN -> new StatsComponent(
                20,     // 1/4 of DPS HP
                12,     // 1/4 of DPS Mana
                15,     // 1/4 of DPS AC
                5,      // 1/4 of DPS Speed
                4,      // 1/4 of DPS ATK
                1       // 1/4 of DPS DEF
            );

            case DOG -> new StatsComponent(
                38,     // 1/4 of Tank HP
                8,      // 1/4 of Tank Mana
                4,      // 1/4 of Tank AC
                1,      // 1/4 of Tank Speed
                2,      // 1/4 of Tank ATK
                3       // 1/4 of Tank DEF
            );
        };
    }
}
