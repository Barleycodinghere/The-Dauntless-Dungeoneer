package com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction;

public class FireballAction extends CombatAction {

    public FireballAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ENEMY_SINGLE);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        // Perform 2 attacks
        CombatResult result1 = resolver.resolveAttack(new AttackAction(actor, target));
        CombatResult result2 = resolver.resolveAttack(new AttackAction(actor, target));

        // Combine results: if either hit, hit; total damage; etc.
        boolean didHit = result1.didHit || result2.didHit;
        int totalDamage = result1.damageDealt + result2.damageDealt;
        int targetHpBefore = result1.targetHpBefore;
        int targetHpAfter = result2.targetHpAfter; // after second attack
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