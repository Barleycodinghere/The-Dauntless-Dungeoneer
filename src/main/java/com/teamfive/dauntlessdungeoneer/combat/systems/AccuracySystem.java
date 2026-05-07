package com.teamfive.dauntlessdungeoneer.combat.systems;

import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;
import com.teamfive.dauntlessdungeoneer.combat.results.HitResult;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

import java.util.Random;

public class AccuracySystem {

    private final Random random = new Random();

    public HitResult determineHit(Entity attacker, Entity target) {
        StatsComponent attackerStats = attacker.getComponent(StatsComponent.class);
        StatsComponent targetStats = target.getComponent(StatsComponent.class);

        if (attackerStats == null || targetStats == null) {
            return new HitResult(false, 0);
        }

        int hitChance = attackerStats.getAC() - targetStats.getSpeed();

        CombatantComponent attackerCombatant = attacker.getComponent(CombatantComponent.class);
        if (attackerCombatant != null && attackerCombatant.team == CombatantComponent.Team.PLAYER) {
            hitChance += 15; // Give player characters a better chance to hit
        }

        // Keeps hit chance reasonable -- adjust as needed
        int min = 10;
        int max = 98;
        hitChance = clamp(hitChance, min, max);


        int roll = random.nextInt(100) + 1; 
        boolean didHit = roll <= hitChance;

        return new HitResult(didHit, hitChance);
    }

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
