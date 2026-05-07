package com.teamfive.dauntlessdungeoneer.combat.abilities.tank;

import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.BlockAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.CleaveAction; 


public class CleaveAbility extends Ability {

    public CleaveAbility() {
        super("Cleave", 20);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new CleaveAction(actor, target));
    }
}