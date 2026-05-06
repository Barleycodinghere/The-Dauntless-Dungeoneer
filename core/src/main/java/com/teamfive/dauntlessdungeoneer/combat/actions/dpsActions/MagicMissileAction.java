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
        // Perform 1 attack
        CombatResult result1 = resolver.resolveAttack(new AttackAction(actor, target));

        return result1;
    }
}