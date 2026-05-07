package com.teamfive.dauntlessdungeoneer.combat.actions;

import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.ui.CombatLogBox;

import java.util.function.Consumer;

public class ActionHandler {

    private final CombatManager combatManager;
    private final CombatLogBox combatLogBox;
    private final Runnable clearTargetSelection;
    private final Consumer<Player> handleCharacterDeath;

    private Entity currentTarget;

    public ActionHandler(
            CombatManager combatManager,
            CombatLogBox combatLogBox,
            Runnable clearTargetSelection,
            Consumer<Player> handleCharacterDeath) {

        this.combatManager = combatManager;
        this.combatLogBox = combatLogBox;
        this.clearTargetSelection = clearTargetSelection;
        this.handleCharacterDeath = handleCharacterDeath;
    }

    public void setCurrentTarget(Entity target) {
        this.currentTarget = target;
    }

    public void execute(CombatAction action, String actionName) {

        if (!combatManager.isCombatActive()) {
            log("Combat has ended.");
            return;
        }

        if (!isPlayerTurn()) {
            log("It is not your turn.");
            return;
        }

        if (currentTarget == null) {
            log("Select a target first.");
            return;
        }

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            log("No attacker available.");
            return;
        }

        CombatResult result = combatManager.performAction(action);

        if (result == null) {
            log("Could not perform " + actionName + ".");
            return;
        }

        Player defender = (Player) result.defender;
        Player attackerPlayer = (Player) attacker;

        if (!result.didHit) {
            log(attackerPlayer.getName() + " used " + actionName + " but missed " + defender.getName());
        } else {
            log(attackerPlayer.getName() + " hit " + defender.getName()
                    + " for " + result.damageDealt + " damage using " + actionName);
        }

        if (result.targetDefeated) {
            log(defender.getName() + " was defeated!");
            handleCharacterDeath.accept(defender);
        }

        clearTargetSelection.run();
    }

    private boolean isPlayerTurn() {
        if (combatManager == null || !combatManager.isCombatActive()) return false;

        Entity current = combatManager.getCurrentCombatant();
        if (current == null) return false;

        var combatant = current.getComponent(
                com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent.class
        );

        return combatant != null &&
                combatant.team ==
                        com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent.Team.PLAYER;
    }

    private void log(String message) {
        if (combatLogBox != null) {
            combatLogBox.addEntry(message);
        }
    }
}