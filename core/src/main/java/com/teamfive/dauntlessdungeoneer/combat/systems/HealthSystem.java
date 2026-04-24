package com.teamfive.dauntlessdungeoneer.combat.systems;

import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class HealthSystem {

    public void applyDamage(Entity target, int amount) {
        StatsComponent stats = target.getComponent(StatsComponent.class);
        CombatantComponent combatant = target.getComponent(CombatantComponent.class);

        if (stats == null || combatant == null || !combatant.isAlive) {
            return;
        }

        int damage = Math.max(amount, 0);
        stats.takeDamage(damage);

        if (stats.getCurrentHP() <= 0) {
            combatant.isAlive = false;
        }
    }

    public void applyHealing(Entity target, int amount) {
        StatsComponent stats = target.getComponent(StatsComponent.class);
        CombatantComponent combatant = target.getComponent(CombatantComponent.class);

        if (stats == null || combatant == null || !combatant.isAlive) {
            return;
        }

        int healing = Math.max(amount, 0);
        stats.heal(healing);
    }

    public boolean isDead(Entity entity) {
        CombatantComponent combatant = entity.getComponent(CombatantComponent.class);
        return combatant == null || !combatant.isAlive;
    }

    public boolean isAliveA(Entity entity) {
        CombatantComponent combatant = entity.getComponent(CombatantComponent.class);
        return combatant != null && combatant.isAlive;
    }
}
