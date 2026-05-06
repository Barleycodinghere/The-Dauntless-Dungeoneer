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
        // Perform 1 attack
        CombatResult result = resolver.resolveAttack(new AttackAction(actor, target));

        if (result.didHit) {
            // Double the damage
            int doubledDamage = result.damageDealt * 2;
            int newHpAfter = result.targetHpBefore - doubledDamage;
            boolean defeated = newHpAfter <= 0;
            return new CombatResult(result.attacker, result.defender, true, doubledDamage, result.targetHpBefore, newHpAfter, defeated);
        } else {
            return result;
        }
    }
}