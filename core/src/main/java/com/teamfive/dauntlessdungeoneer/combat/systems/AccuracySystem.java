package com.teamfive.dauntlessdungeoneer.combat.systems;

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

        // Keeps hit chance reasonable -- adjust as needed
        int min = 5;
        int max = 95;
        hitChance = clamp(hitChance,min,max);

        int roll =  random.nextInt((max - min) + 1) + min;

        boolean didHit = roll <= hitChance;

        return new HitResult(didHit,hitChance);
    }

    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
