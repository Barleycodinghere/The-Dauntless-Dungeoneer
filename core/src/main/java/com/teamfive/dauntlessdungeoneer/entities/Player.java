package com.teamfive.dauntlessdungeoneer.entities;

import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.components.*;

public class Player extends Entity {

    private final PlayerClass playerClass;

    public Player( PlayerClass playerClass) {
        super();
        this.playerClass = playerClass;

        // Default components for a player
        addComponent(StatsComponent.class, StatsFactory.createStats(playerClass));
        // This adds inventory for a player
        addComponent(InventoryComponent.class, new InventoryComponent());
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }
}
