package com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction;

public class MagicMissileAction extends CombatAction {

    public MagicMissileAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ENEMY_SINGLE);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        // Perform 3 attacks
        CombatResult result1 = resolver.resolveAttack(new AttackAction(actor, target));
        CombatResult result2 = resolver.resolveAttack(new AttackAction(actor, target));
        CombatResult result3 = resolver.resolveAttack(new AttackAction(actor, target));

        // Combine results
        boolean didHit = result1.didHit || result2.didHit || result3.didHit;
        int totalDamage = result1.damageDealt + result2.damageDealt + result3.damageDealt;
        int targetHpBefore = result1.targetHpBefore;
        int targetHpAfter = result3.targetHpAfter;
        boolean targetDefeated = result3.targetDefeated;

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