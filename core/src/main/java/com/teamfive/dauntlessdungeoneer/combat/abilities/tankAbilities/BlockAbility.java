package com.teamfive.dauntlessdungeoneer.combat.abilities.tank;

import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.BlockAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class BlockAbility extends Ability {

    public BlockAbility() {
        super("Block", 10);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new BlockAction(actor));
    }
}