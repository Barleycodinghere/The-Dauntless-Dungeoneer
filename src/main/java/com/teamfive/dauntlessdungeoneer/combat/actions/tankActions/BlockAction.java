package com.teamfive.dauntlessdungeoneer.combat.actions.tankActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class BlockAction extends CombatAction {

    public BlockAction(Entity actor) {
        super(actor, actor, TargetType.SELF);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        Entity blocker = actor;

        // Calculate base damage as block heal amount
        int baseDamage = Math.max(1, Math.round(blocker.getComponent(com.teamfive.dauntlessdungeoneer.components.StatsComponent.class).getMaxHP() * 0.1f));

        return resolver.resolveHeal(blocker, baseDamage);
    }
}