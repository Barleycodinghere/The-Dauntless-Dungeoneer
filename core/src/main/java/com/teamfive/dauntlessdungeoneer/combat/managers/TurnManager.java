package com.teamfive.dauntlessdungeoneer.combat.managers;

import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TurnManager {

    private final List<Entity> turnOrder = new ArrayList<>();
    private int currentTurnIndex = 0;

    public void setCombatants(List<Entity> combatants) {
        turnOrder.clear();
        turnOrder.addAll(combatants);

        turnOrder.sort(Comparator.comparingInt(this::getSpeed).reversed());
    }

    public Entity getCurrentCombatant() {
        if (turnOrder.isEmpty())
            return null;

        return turnOrder.get(currentTurnIndex);
    }

    public void advanceTurn() {
        if (turnOrder.isEmpty())
            return;

        do {
            currentTurnIndex = (currentTurnIndex + 1) % turnOrder.size();
        } while (!isAbleToTakeTurn(turnOrder.get(currentTurnIndex)));
    }

    private int getSpeed(Entity entity) {
        StatsComponent stats = entity.getComponent(StatsComponent.class);

        if (stats == null) {
            return  0;
        }

        return stats.getSpeed();
    }

    private boolean isAbleToTakeTurn(Entity entity) {
        CombatantComponent combatant = entity.getComponent(CombatantComponent.class);

        return combatant != null
            && combatant.isAlive
            && combatant.canAct;
    }
}
