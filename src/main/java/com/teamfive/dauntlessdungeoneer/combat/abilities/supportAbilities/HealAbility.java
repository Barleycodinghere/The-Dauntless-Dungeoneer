package com.teamfive.dauntlessdungeoneer.combat.abilities.support;

import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.MassHealAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.HealAction; 

public class HealAbility extends Ability {

    public HealAbility() {
        super("Heal", 20);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        // You must return a CombatResult to satisfy the Ability interface
        return combatManager.performAction(new HealAction(actor, target));
    }
}