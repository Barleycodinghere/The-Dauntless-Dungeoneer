package com.teamfive.dauntlessdungeoneer.combat.actions.supportActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.components.MonsterClass;

public class MassHealAction extends CombatAction {

    public MassHealAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ALLY_SINGLE);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        if (!(target instanceof Player) || ((Player) target).getPlayerClass() instanceof MonsterClass) {
            return new CombatResult(actor, target, false, 0, 0, 0, false, "Can only heal PlayerClass.");
        }

        int healCounter = 2;
        int totalHealAmount = 0;
        int healAmount = Math.max(1, Math.round(actor.getComponent(com.teamfive.dauntlessdungeoneer.components.StatsComponent.class).getMaxHP() * 0.1f));

        while (healCounter > 0) {
            CombatResult healResult = resolver.resolveHealTarget(target, healAmount);
            if (healResult.didHit) {
                totalHealAmount += healResult.damageDealt;
            }
            healCounter--;
        }

        // Return a combined result
        return new CombatResult(actor, target, true, totalHealAmount, 0, 0, false);
    }
}