package com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions;

import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.ui.CombatAreaView;
import com.teamfive.dauntlessdungeoneer.ui.CombatLogBox;

public class DpsActionHandler {
    private final CombatManager combatManager;
    private final CombatAreaView combatAreaView;
    private final CombatLogBox combatLogBox;
    private final Team playerTeam;
    private final Team enemyTeam;
    private final Runnable clearTargetSelection;
    private final java.util.function.Consumer<Player> handleCharacterDeath;
    private Entity currentTarget;

    public DpsActionHandler(CombatManager combatManager, CombatAreaView combatAreaView, CombatLogBox combatLogBox,
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

    public void performFireball() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            addCombatLog("No attacker available.");
            return;
        }

        String attackerName = ((Player) attacker).getName();
        addCombatLog(attackerName + " casts Fireball!");

        CombatResult result = combatManager.performAction(new FireballAction(attacker, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform Fireball.");
            return;
        }

        Player defenderPlayer = (Player) result.defender;
        String defenderName = defenderPlayer.getName();

        if (!result.didHit) {
            addCombatLog(attackerName + " cast Fireball at " + defenderName + " but missed.");
        } else {
            addCombatLog(attackerName + " hit " + defenderName + " for " + result.damageDealt + " damage with Fireball.");
        }

        if (result.targetDefeated) {
            addCombatLog(attackerName + " defeated " + defenderName + " with Fireball!");
            handleCharacterDeath.accept(defenderPlayer);
        }

        refreshUI();
    }

    public void performMagicMissile() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            addCombatLog("No attacker available.");
            return;
        }

        String attackerName = ((Player) attacker).getName();
        addCombatLog(attackerName + " casts Magic Missile!");

        CombatResult result = combatManager.performAction(new MagicMissileAction(attacker, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform Magic Missile.");
            return;
        }

        Player defenderPlayer = (Player) result.defender;
        String defenderName = defenderPlayer.getName();

        if (!result.didHit) {
            addCombatLog(attackerName + " cast Magic Missile at " + defenderName + " but missed.");
        } else {
            addCombatLog(attackerName + " hit " + defenderName + " for " + result.damageDealt + " damage with Magic Missile.");
        }

        if (result.targetDefeated) {
            addCombatLog(attackerName + " defeated " + defenderName + " with Magic Missile!");
            handleCharacterDeath.accept(defenderPlayer);
        }

        refreshUI();
    }

    public void performRecover() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity healer = combatManager.getCurrentCombatant();
        if (healer == null) {
            addCombatLog("No healer available.");
            return;
        }

        String healerName = ((Player) healer).getName();
        addCombatLog(healerName + " casts Recover!");

        CombatResult result = combatManager.performAction(new RecoverAction(healer, currentTarget));

        if (result == null || !result.didHit) {
            addCombatLog("Could not perform Recover: " + (result != null ? result.message : "Unknown error"));
            return;
        }

        Player targetPlayer = (Player) result.defender;
        String targetName = targetPlayer.getName();
        addCombatLog(healerName + " healed " + targetName + " for " + result.damageDealt + " HP.");

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
