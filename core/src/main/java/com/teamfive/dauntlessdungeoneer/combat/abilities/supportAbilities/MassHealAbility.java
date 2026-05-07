package com.teamfive.dauntlessdungeoneer.combat.abilities.support;

import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.MassHealAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class MassHealAbility extends Ability {

    public MassHealAbility() {
        super("Mass Heal", 30);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new MassHealAction(actor, target));
    }
}