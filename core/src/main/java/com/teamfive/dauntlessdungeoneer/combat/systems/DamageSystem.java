package com.teamfive.dauntlessdungeoneer.combat.systems;

import com.teamfive.dauntlessdungeoneer.combat.results.DamageResult;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class DamageSystem {

    public DamageResult calculateDamage(Entity attacker, Entity target) {
        StatsComponent attackerStats = attacker.getComponent(StatsComponent.class);
        StatsComponent targetStats = target.getComponent(StatsComponent.class);

        if (attackerStats == null || targetStats == null) {
            return new DamageResult(0,0,0);
        }

        int baseDamage = attackerStats.getAttack();
        int defenseReduction = targetStats.getDefense();

        int finalDamage = baseDamage - defenseReduction;

        finalDamage = Math.max(finalDamage,1);

        return new DamageResult(baseDamage, defenseReduction, finalDamage);
    }
}
