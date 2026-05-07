package com.teamfive.dauntlessdungeoneer.combat.abilities.tank;

import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.HeavyAttackAction;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class HeavyAttackAbility extends Ability {

    public HeavyAttackAbility() {
        super("Heavy Attack", 20);
    }

    @Override
    public CombatResult execute(Entity actor, Entity target, CombatManager combatManager) {
        return combatManager.performAction(new HeavyAttackAction(actor, target));
    }
}