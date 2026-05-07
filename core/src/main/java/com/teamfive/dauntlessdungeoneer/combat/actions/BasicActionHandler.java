package com.teamfive.dauntlessdungeoneer.combat.actions;

import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.ui.CombatAreaView;
import com.teamfive.dauntlessdungeoneer.ui.CombatLogBox;

public class BasicActionHandler {
    private final CombatManager combatManager;
    private final CombatAreaView combatAreaView;
    private final CombatLogBox combatLogBox;
    private final Team playerTeam;
    private final Team enemyTeam;
    private final Runnable clearTargetSelection;
    private final java.util.function.Consumer<Player> handleCharacterDeath;
    private Entity currentTarget;

    public BasicActionHandler(CombatManager combatManager, CombatAreaView combatAreaView, CombatLogBox combatLogBox,
                              Team playerTeam, Team enemyTeam, Runnable clearTargetSelection,
                              java.util.function.Consumer<Player> handleCharacterDeath) {
        this.combatManager = combatManager;
        this.combatAreaView = combatAreaView;
        this.combatLogBox = combatLogBox;
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
        this.clearTargetSelection = clearTargetSelection;
        this.handleCharacterDeath = handleCharacterDeath;
    }

    public void setCurrentTarget(Entity target) {
        this.currentTarget = target;
    }

    public void performBasicAttack() {
        if (!combatManager.isCombatActive()) {
            addCombatLog("Combat has ended.");
            return;
        }

        if (!isPlayerTurn()) {
            addCombatLog("It is not your turn yet.");
            return;
        }

        if (currentTarget == null) {
            addCombatLog("Select a target before attacking.");
            return;
        }

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            addCombatLog("No attacker available.");
            return;
        }

        CombatResult result = combatManager.performAction(new AttackAction(attacker, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform attack.");
            return;
        }

        Player defenderPlayer = (Player) result.defender;
        String attackerName = ((Player) attacker).getName();
        String defenderName = defenderPlayer.getName();

        if (!result.didHit) {
            addCombatLog("Player " + attackerName + " attacked " + defenderName + " but missed.");
        } else {
            addCombatLog("Player " + attackerName + " hit " + defenderName + " for " + result.damageDealt + " damage.");
        }

        if (result.targetDefeated) {
            addCombatLog("Player " + attackerName + " defeated " + defenderName + "!");
            handleCharacterDeath.accept(defenderPlayer);
        }

        refreshUI();
    }

    private boolean isPlayerTurn() {
        if (combatManager == null || !combatManager.isCombatActive()) {
            return false;
        }

        Entity current = combatManager.getCurrentCombatant();
        if (current == null) {
            return false;
        }

        com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent combatant = 
            current.getComponent(com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent.class);
        return combatant != null && combatant.team == com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent.Team.PLAYER;
    }

    private void refreshUI() {
        clearTargetSelection.run();
        combatAreaView.refresh(playerTeam.getMembers(), enemyTeam.getMembers(), selectedTarget -> {
            currentTarget = selectedTarget;
            addCombatLog("Target selected: " + ((Player) selectedTarget).getName() + ". Press Basic Attack.");
        });
    }

    private void addCombatLog(String message) {
        if (combatLogBox != null) {
            combatLogBox.addEntry(message);
        }
    }
}
