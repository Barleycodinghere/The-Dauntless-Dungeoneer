package com.teamfive.dauntlessdungeoneer.combat.abilities;

import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction; 
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class BasicAttackAbility extends Ability {

    public BasicAttackAbility() {
        super("Basic Attack", 0); 
    }

    @Override
    public CombatResult execute(Entity user, Entity target, CombatManager manager) {
        // This triggers your standard attack logic
        return manager.performAction(new AttackAction(user, target));
    }
}
