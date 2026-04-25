package com.teamfive.dauntlessdungeoneer.combat.managers;

import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

import java.util.List;

public class CombatManager {

    private final TurnManager turnManager;
    private final CombatResolver combatResolver;

    private List<Entity> combatants;
    private boolean combatActive = false;

    public CombatManager(TurnManager turnManager, CombatResolver combatResolver) {
        this.turnManager = turnManager;
        this.combatResolver = combatResolver;
    }

    public void startCombat(List<Entity> combatants) {
        this.combatants = combatants;
        this.combatActive = true;

        turnManager.setCombatants(combatants);
    }

    public Entity getCurrentCombatant() {
        return turnManager.getCurrentCombatant();
    }

    public CombatResult performAttack(Entity attacker, Entity target) {
        if (!combatActive) {
            return null;
        }

        CombatResult result = combatResolver.resolveAttack(attacker, target);

        checkCombatEnd();

        turnManager.advanceTurn();

        return result;
    }

    private void checkCombatEnd() {
        boolean playerAlive = false;
        boolean enemyAlive = false;

        for (Entity e : combatants) {
            CombatantComponent combatant = e.getComponent(CombatantComponent.class);

            if (combatant != null || !combatant.isAlive) continue;

            if ( combatant.team == CombatantComponent.Team.PLAYER) {
                playerAlive = true;
            } else if (combatant.team == CombatantComponent.Team.ENEMY) {
                enemyAlive = true;
            }
        }

        if (!playerAlive || !enemyAlive) {
            combatActive = false;
        }
    }

    public boolean isCombatActive() {
        return combatActive;
    }
}
