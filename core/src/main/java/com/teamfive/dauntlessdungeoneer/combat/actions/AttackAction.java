package com.teamfive.dauntlessdungeoneer.combat.actions;

import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class AttackAction extends CombatAction{

    public AttackAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ENEMY_SINGLE);
    }
}
