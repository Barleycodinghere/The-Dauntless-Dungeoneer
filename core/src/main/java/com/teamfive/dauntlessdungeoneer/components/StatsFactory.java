package com.teamfive.dauntlessdungeoneer.components;

public class StatsFactory {
    public static StatsComponent createStats(PlayerClass playerClass) {

        return switch (playerClass) {

            case DPS -> new StatsComponent(
                80,     // HP
                50,         // Mana
                5,          // AC
                10,         // Speed
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
    }
}
