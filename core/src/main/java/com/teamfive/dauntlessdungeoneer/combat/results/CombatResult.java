package com.teamfive.dauntlessdungeoneer.combat.results;

import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class CombatResult {

    public final Entity attacker;
    public final Entity defender;

    public final boolean didHit;
    public final int damageDealt;

    public CombatResult(Entity attacker, Entity target,boolean didHit, int damageDealt) {
        this.attacker = attacker;
        this.defender = target;
        this.didHit = didHit;
        this.damageDealt = damageDealt;
    }
}
