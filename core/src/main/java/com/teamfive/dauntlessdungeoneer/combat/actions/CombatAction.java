package com.teamfive.dauntlessdungeoneer.combat.actions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public abstract class CombatAction {

    public enum TargetType {
        ENEMY_SINGLE,
        ALLY_SINGLE,
        SELF,
        NONE
    }

    public final Entity actor;
    public final Entity target;
    public final TargetType targetType;

    public CombatAction(Entity actor, Entity target, TargetType targetType) {
        this.actor = actor;
        this.target = target;
        this.targetType = targetType;
    }

    public abstract CombatResult resolve(CombatResolver resolver);
}
