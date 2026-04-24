package com.teamfive.dauntlessdungeoneer.entities;

import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.components.*;

public class Player extends Entity {
    private final PlayerClass playerClass;

    public Player(int id, PlayerClass playerClass) {
        super(id);
        this.playerClass = playerClass;

        // Default components for a player
        addComponent(StatsComponent.class, StatsFactory.createStats(playerClass));
        // This ass ths inventory for a player
        addComponent(InventoryComponent.class, new InventoryComponent());
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }
}
