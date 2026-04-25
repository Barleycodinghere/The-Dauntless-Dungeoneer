package com.teamfive.dauntlessdungeoneer.combat.actions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class AttackAction extends CombatAction{

    public AttackAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ENEMY_SINGLE);
    }

    public CombatResult resolve(CombatResolver resolver) {
        return resolver.resolveAttack(this);
    }
}
