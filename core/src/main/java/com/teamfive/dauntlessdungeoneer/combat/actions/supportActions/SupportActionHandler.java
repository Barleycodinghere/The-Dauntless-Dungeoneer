package com.teamfive.dauntlessdungeoneer.combat.actions.supportActions;

import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.ui.CombatAreaView;
import com.teamfive.dauntlessdungeoneer.ui.CombatLogBox;

public class SupportActionHandler {
    private final CombatManager combatManager;
    private final CombatAreaView combatAreaView;
    private final CombatLogBox combatLogBox;
    private final Team playerTeam;
    private final Team enemyTeam;
    private final Runnable clearTargetSelection;
    private final java.util.function.Consumer<Player> handleCharacterDeath;
    private Entity currentTarget;

    public SupportActionHandler(CombatManager combatManager, CombatAreaView combatAreaView, CombatLogBox combatLogBox,
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

    public void performSmite() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            addCombatLog("No attacker available.");
            return;
        }

        String attackerName = ((Player) attacker).getName();
        addCombatLog(attackerName + " casts Smite!");

        CombatResult result = combatManager.performAction(new SmiteAction(attacker, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform Smite.");
            return;
        }

        Player defenderPlayer = (Player) result.defender;
        String defenderName = defenderPlayer.getName();

        if (!result.didHit) {
            addCombatLog(attackerName + " cast Smite at " + defenderName + " but missed.");
        } else {
            addCombatLog(attackerName + " hit " + defenderName + " for " + result.damageDealt + " damage with Smite.");
        }

        if (result.targetDefeated) {
            addCombatLog(attackerName + " defeated " + defenderName + " with Smite!");
            handleCharacterDeath.accept(defenderPlayer);
        }

        refreshUI();
    }

    public void performMassHeal() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity healer = combatManager.getCurrentCombatant();
        if (healer == null) {
            addCombatLog("No healer available.");
            return;
        }

        String healerName = ((Player) healer).getName();
        addCombatLog(healerName + " casts Mass Heal!");

        CombatResult result = combatManager.performAction(new MassHealAction(healer, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform Mass Heal.");
            return;
        }

        Player targetPlayer = (Player) result.defender;
        String targetName = targetPlayer.getName();

        addCombatLog(healerName + " healed " + targetName + " for " + result.damageDealt + " HP with Mass Heal.");

        refreshUI();
    }

    public void performHeal() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity healer = combatManager.getCurrentCombatant();
        if (healer == null) {
            addCombatLog("No healer available.");
            return;
        }

        String healerName = ((Player) healer).getName();
        addCombatLog(healerName + " casts Heal!");

        CombatResult result = combatManager.performAction(new HealAction(healer, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform Heal.");
            return;
        }

        Player targetPlayer = (Player) result.defender;
        String targetName = targetPlayer.getName();

        addCombatLog(healerName + " healed " + targetName + " for " + result.damageDealt + " HP with Heal.");

        refreshUI();
    }

    private boolean validateCombatState() {
        if (!combatManager.isCombatActive()) {
            addCombatLog("Combat has ended.");
            return false;
        }

        if (!isPlayerTurn()) {
            addCombatLog("It is not your turn yet.");
            return false;
        }

        return true;
    }

    private boolean validateTarget() {
        if (currentTarget == null) {
            addCombatLog("Select a target before casting.");
            return false;
        }
        return true;
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
