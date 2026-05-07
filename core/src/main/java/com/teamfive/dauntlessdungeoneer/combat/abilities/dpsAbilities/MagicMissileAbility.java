package com.teamfive.dauntlessdungeoneer.combat.abilities.dps;

import com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions.MagicMissileAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class MagicMissileAbility extends Ability {

    public MagicMissileAbility() {
        super("Magic Missile", 15);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new MagicMissileAction(actor, target));
    }
}