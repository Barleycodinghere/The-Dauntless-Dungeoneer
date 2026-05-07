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
        int missileCounter = 3;
        int totalDamageDealt = 0;
        boolean anyHit = false;

        while (missileCounter > 0) {
            CombatResult attackResult = resolver.resolveAttack(new AttackAction(actor, target));
            if (attackResult.didHit) {
                totalDamageDealt += attackResult.damageDealt;
                anyHit = true;
            }
            missileCounter--;
        }

        // Return combined result
        return new CombatResult(actor, target, anyHit, totalDamageDealt, 0, 0, false);
    }
}