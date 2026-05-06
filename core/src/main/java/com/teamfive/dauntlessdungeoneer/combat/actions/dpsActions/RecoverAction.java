package com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.components.MonsterClass;

public class RecoverAction extends CombatAction {

    public RecoverAction(Entity actor, Entity target) {
        super(actor, target, TargetType.ALLY_SINGLE);
    }

    @Override
    public CombatResult resolve(CombatResolver resolver) {
        if (!(target instanceof Player) || ((Player) target).getPlayerClass() instanceof MonsterClass) {
            return new CombatResult(actor, target, false, 0, 0, 0, false, "Can only heal PlayerClass.");
        }

        StatsComponent stats = target.getComponent(StatsComponent.class);
        if (stats.getCurrentHP() >= stats.getMaxHP()) {
            return new CombatResult(actor, target, false, 0, 0, 0, false, "Target is already at full HP.");
        }

        int baseDamage = Math.max(1, Math.round(actor.getComponent(StatsComponent.class).getMaxHP() * 0.1f));

        return resolver.resolveHealTarget(target, baseDamage);
    }
}