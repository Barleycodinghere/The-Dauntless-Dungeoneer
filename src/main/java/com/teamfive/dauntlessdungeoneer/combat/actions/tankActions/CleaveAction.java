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
        int cleaveCounter = 2;
        int totalDamageDealt = 0;
        boolean anyHit = false;
        int targetHpBefore = 0;
        int targetHpAfter = 0;
        boolean targetDefeated = false;

        while (cleaveCounter > 0) {
            CombatResult attackResult = resolver.resolveAttack(new com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction(actor, target));
            if (cleaveCounter == 2) {
                targetHpBefore = attackResult.targetHpBefore;
            }
            if (attackResult.didHit) {
                totalDamageDealt += attackResult.damageDealt;
                anyHit = true;
            }
            targetHpAfter = attackResult.targetHpAfter;
            targetDefeated = attackResult.targetDefeated;
            cleaveCounter--;
        }

        return new CombatResult(
            actor,
            target,
            anyHit,
            totalDamageDealt,
            targetHpBefore,
            targetHpAfter,
            targetDefeated
        );
    }
}