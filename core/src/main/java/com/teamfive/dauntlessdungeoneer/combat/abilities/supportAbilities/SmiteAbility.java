package com.teamfive.dauntlessdungeoneer.combat.abilities.support;

import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.SmiteAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class SmiteAbility extends Ability {

    public SmiteAbility() {
        super("Smite", 15);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new SmiteAction(actor, target));
    }
}