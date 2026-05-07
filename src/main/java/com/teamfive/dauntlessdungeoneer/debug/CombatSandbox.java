package com.teamfive.dauntlessdungeoneer.debug;

import com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction;
import com.teamfive.dauntlessdungeoneer.combat.managers.TurnManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.*;
import com.teamfive.dauntlessdungeoneer.components.PlayerClass;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

import java.util.List;

public class CombatSandbox {

    public static void main(String[] args) {

        Entity player = TestEntityFactory.createPlayer(PlayerClass.DPS);
        Entity goblin = TestEntityFactory.createGoblin();

        List<Entity> combatants = List.of(player, goblin);

        CombatLogger.logCombatStart(combatants);

        TargetingSystem targetingSystem = new TargetingSystem();
        AccuracySystem accuracySystem = new AccuracySystem();
        DamageSystem damageSystem = new DamageSystem();
        HealthSystem healthSystem = new HealthSystem();

        CombatResolver combatResolver = new CombatResolver(
            targetingSystem,
            accuracySystem,
            damageSystem,
            healthSystem
        );

        TurnManager turnManager = new TurnManager();
        turnManager.setCombatants(combatants);

        int turnNumber = 1;

        while (true) {
            Entity current = turnManager.getCurrentCombatant();

            CombatLogger.logTurnStart(current,turnNumber);

            Entity target;

            if(current == player) {
                target = goblin;
            } else {
                target = player;
            }

            CombatResult result = combatResolver.resolveAction(
                new AttackAction(current, target));

            CombatLogger.logResult(result);

            if (result.targetDefeated) {
                CombatLogger.logCombatEnd(current);
                break;
            }

            turnManager.advanceTurn();
            turnNumber++;
        }
/*
        CombatLogger.logTurnStart(player,1);

        AttackAction attackAction = new AttackAction(player,goblin);

        CombatResult result = combatResolver.resolveAction(attackAction);
        CombatLogger.logResult(result);

        CombatLogger.logCombatEnd(player);*/
    }
}
