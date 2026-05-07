package com.teamfive.dauntlessdungeoneer.debug;

import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

import java.util.List;

public class CombatLogger {

    public static void logCombatStart(List<Entity> combatants) {
        // print combatants
        System.out.println("=== Combat Start ===");

        for (Entity entity : combatants) {
            System.out.println("- " +getDisplayName(entity));
        }

        System.out.println();
    }

    public static void logTurnStart(Entity entity, int turnNumber) {
        //print whose turn it is
        System.out.println("Turn " + turnNumber + ": " + getDisplayName(entity) +"'s turn");
    }

    public static void logAction(String message) {
        // print action message
        System.out.println(message);
    }

    public static void logResult(CombatResult result) {
        // print result details

        String attackerName = getDisplayName(result.attacker);
        String targetName = getDisplayName(result.defender);

        System.out.println(attackerName + " attacks " + targetName);

        if (!result.didHit) {
            System.out.println("-> MISS");
            System.out.println();
            return;
        }

        System.out.println("-> HIT!");
        System.out.println("Damage: " + result.damageDealt);
        System.out.println(
            targetName + " HP: " +
                result.targetHpBefore + " -> " + result.targetHpAfter
        );
        if (result.targetDefeated) {
            System.out.println(targetName + " DEFEATED!");
        }

        System.out.println();
    }

    public static void logCombatEnd(Entity winner) {
        //print winner
        System.out.println();
        System.out.println("=== Combat End ===");
        System.out.println("Winner: " + getDisplayName(winner));
    }

    public static String getDisplayName(Entity entity) {
        String baseName = "Entity#" + entity.getId();

        if (entity.hasComponent(NameComponent.class)) {
            String name =entity.getComponent(NameComponent.class).getName();
            return name + " (" +  baseName + ")";
        }
        return baseName;
    }

}
