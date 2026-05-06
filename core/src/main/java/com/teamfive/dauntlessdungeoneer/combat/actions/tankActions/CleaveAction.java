package com.teamfive.dauntlessdungeoneer.combat.actions.tankActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class CleaveAction extends CombatAction {

    public CleaveAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ENEMY_SINGLE);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        // Perform 2 attacks on the same target
        CombatResult result1 = resolver.resolveAttack(new com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction(actor, target));
        CombatResult result2 = resolver.resolveAttack(new com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction(actor, target));

        // Combine results
        boolean didHit = result1.didHit || result2.didHit;
        int totalDamage = result1.damageDealt + result2.damageDealt;
        int targetHpBefore = result1.targetHpBefore;
        int targetHpAfter = result2.targetHpAfter;
        boolean targetDefeated = result2.targetDefeated;

        return new CombatResult(
            actor,
            target,
            didHit,
            totalDamage,
            targetHpBefore,
            targetHpAfter,
            targetDefeated
        );
    }
}