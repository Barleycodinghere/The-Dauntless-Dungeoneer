package com.teamfive.dauntlessdungeoneer.combat.abilities.dps;

import com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions.RecoverAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class RecoverAbility extends Ability {

    public RecoverAbility() {
        super("Recover", 10);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new RecoverAction(actor, target));
    }
}