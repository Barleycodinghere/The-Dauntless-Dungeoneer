package com.teamfive.dauntlessdungeoneer.combat.systems;

import com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.CombatAction;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.results.DamageResult;
import com.teamfive.dauntlessdungeoneer.combat.results.HitResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class CombatResolver {

    private final TargetingSystem targetingSystem;
    private final AccuracySystem accuracySystem;
    private final DamageSystem damageSystem;
    private final HealthSystem healthSystem;

    public CombatResolver(
        TargetingSystem targetingSystem,
        AccuracySystem accuracySystem,
        DamageSystem damageSystem,
        HealthSystem healthSystem
    ) {
        this.targetingSystem = targetingSystem;
        this.accuracySystem = accuracySystem;
        this.damageSystem = damageSystem;
        this.healthSystem = healthSystem;
    }

    public CombatResult resolveAction(CombatAction action) {

        Entity attacker = action.actor;
        Entity target = action.target;

        if (action instanceof AttackAction) {
            // Validate target
            if (!targetingSystem.isValidEnemyTarget(attacker, target)) {
                return new CombatResult(attacker, target, false, 0);
            }

            // Accuracy check
            HitResult hitResult = accuracySystem.determineHit(attacker, target);

            if (!hitResult.didHit) {
                return new CombatResult(attacker, target, false, 0);
            }

            // Damage calculation
            DamageResult damageResult = damageSystem.calculateDamage(attacker, target);

            // Apply damage
            healthSystem.applyDamage(target, damageResult.finalDamage);

            // Return result
            return new CombatResult(
                attacker,
                target,
                true,
                damageResult.finalDamage
            );
        }
        return new CombatResult(attacker, target, false, 0);
    }
}
