package com.teamfive.dauntlessdungeoneer.combat.systems;

import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class TargetingSystem {

    public boolean isValidEnemyTarget(Entity attacker, Entity target) {
        CombatantComponent attackerCombatant = attacker.getComponent(CombatantComponent.class);
        CombatantComponent targetCombatant = target.getComponent(CombatantComponent.class);

        if (attackerCombatant == null || targetCombatant == null) {
            return false;
        }

        if (!attackerCombatant.isAlive || !targetCombatant.isTargetable) {
            return false;
        }

        return attackerCombatant.team != targetCombatant.team;
    }

    public boolean isValidAllyTarget(Entity attacker, Entity target) {
        CombatantComponent attackerCombatant = attacker.getComponent(CombatantComponent.class);
        CombatantComponent targetCombatant = target.getComponent(CombatantComponent.class);
        if (attackerCombatant == null || targetCombatant == null) {
            return false;
        }
        if (!attackerCombatant.isAlive || !targetCombatant.isTargetable) {
            return false;
        }
        return attackerCombatant.team == targetCombatant.team;
    }

    public boolean isValidSelfTarget(Entity attacker, Entity target) {
        return attacker == target;
    }
}
