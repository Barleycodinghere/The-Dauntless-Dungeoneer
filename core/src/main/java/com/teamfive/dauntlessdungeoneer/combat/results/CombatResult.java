package com.teamfive.dauntlessdungeoneer.combat.results;

import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class CombatResult {

    public final Entity attacker;
    public final Entity defender;

    public final boolean didHit;
    public final int damageDealt;

    public final int targetHpBefore;
    public final int targetHpAfter;

    public final boolean targetDefeated;
    public final String message;

    public CombatResult(Entity attacker,
                        Entity defender,
                        boolean didHit,
                        int damageDealt,
                        int targetHpBefore,
                        int targetHpAfter,
                        boolean targetDefeated
    ) {
        this(attacker, defender, didHit, damageDealt, targetHpBefore, targetHpAfter, targetDefeated, "");
    }

    public CombatResult(Entity attacker,
                        Entity defender,
                        boolean didHit,
                        int damageDealt,
                        int targetHpBefore,
                        int targetHpAfter,
                        boolean targetDefeated,
                        String message
    ) {
        this.attacker = attacker;
        this.defender = defender;
        this.didHit = didHit;
        this.damageDealt = damageDealt;
        this.targetHpBefore = targetHpBefore;
        this.targetHpAfter = targetHpAfter;
        this.targetDefeated = targetDefeated;
        this.message = message;
    }
}
