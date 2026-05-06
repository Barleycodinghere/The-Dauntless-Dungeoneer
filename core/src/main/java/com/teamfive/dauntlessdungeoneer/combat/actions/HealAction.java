package com.teamfive.dauntlessdungeoneer.combat.actions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class HealAction extends CombatAction {

    public HealAction(Entity actor) {
        super(actor, actor, TargetType.SELF);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        Entity healer = actor;

        // Calculate base damage as heal amount
        int baseDamage = Math.max(1, Math.round(healer.getComponent(com.teamfive.dauntlessdungeoneer.components.StatsComponent.class).getMaxHP() * 0.1f));

        return resolver.resolveHeal(healer, baseDamage);
    }
}