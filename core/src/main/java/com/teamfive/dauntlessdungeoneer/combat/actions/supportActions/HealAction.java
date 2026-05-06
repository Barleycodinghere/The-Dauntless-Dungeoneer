package com.teamfive.dauntlessdungeoneer.combat.actions.supportActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class HealAction extends CombatAction {

    public HealAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ALLY_SINGLE);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        Entity healer = actor;
        Entity healTarget = target;

        // Calculate 4 times base damage as heal amount
        int baseDamage = Math.max(1, Math.round(healer.getComponent(com.teamfive.dauntlessdungeoneer.components.StatsComponent.class).getMaxHP() * 0.1f));
        int healAmount = baseDamage * 4;

        return resolver.resolveHealTarget(healTarget, healAmount);
    }
}