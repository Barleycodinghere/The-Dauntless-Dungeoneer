package com.teamfive.dauntlessdungeoneer.combat.actions.supportActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class MassHealAction extends CombatAction {

    public MassHealAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ALLY_SINGLE);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        // Perform 2 heals on the same target
        int healAmount = Math.max(1, Math.round(actor.getComponent(com.teamfive.dauntlessdungeoneer.components.StatsComponent.class).getMaxHP() * 0.1f));

        CombatResult result1 = resolver.resolveHealTarget(target, healAmount);
        CombatResult result2 = resolver.resolveHealTarget(target, healAmount);

        // Combine results: since it's healing, perhaps just the last result or sum
        // For simplicity, return the second heal result
        return result2;
    }
}