package com.teamfive.dauntlessdungeoneer.combat.abilities.dps;

import com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions.FireballAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class FireballAbility extends Ability {

    public FireballAbility() {
        super("Fireball", 25);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new FireballAction(actor, target));
    }
}