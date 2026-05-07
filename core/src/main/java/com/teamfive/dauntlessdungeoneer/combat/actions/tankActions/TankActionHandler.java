package com.teamfive.dauntlessdungeoneer.combat.actions.tankActions;

import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.ui.CombatLogBox;

public class TankActionHandler {
    private final CombatManager combatManager;
    private final CombatLogBox combatLogBox;
    private final Runnable clearTargetSelection;
    private final java.util.function.Consumer<Player> handleCharacterDeath;
    private Entity currentTarget;

    public TankActionHandler(CombatManager combatManager, CombatLogBox combatLogBox, Runnable clearTargetSelection,
                             java.util.function.Consumer<Player> handleCharacterDeath) {
        this.combatManager = combatManager;
        this.combatLogBox = combatLogBox;
        this.clearTargetSelection = clearTargetSelection;
        this.handleCharacterDeath = handleCharacterDeath;
    }

    public void setCurrentTarget(Entity target) {
        this.currentTarget = target;
    }

    public void performHeavyAttack() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            addCombatLog("No attacker available.");
            return;
        }

        String attackerName = ((Player) attacker).getName();
        addCombatLog(attackerName + " casts Heavy Attack!");

        CombatResult result = combatManager.performAction(new HeavyAttackAction(attacker, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform Heavy Attack.");
            return;
        }

        Player defenderPlayer = (Player) result.defender;
        String defenderName = defenderPlayer.getName();

        if (!result.didHit) {
            addCombatLog(attackerName + " cast Heavy Attack at " + defenderName + " but missed.");
        } else {
            addCombatLog(attackerName + " hit " + defenderName + " for " + result.damageDealt + " damage with Heavy Attack.");
        }

        if (result.targetDefeated) {
            addCombatLog(attackerName + " defeated " + defenderName + " with Heavy Attack!");
            handleCharacterDeath.accept(defenderPlayer);
        }

        refreshUI();
    }

    public void performCleave() {
        if (!validateCombatState() || !validateTarget()) return;

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            addCombatLog("No attacker available.");
            return;
        }

        String attackerName = ((Player) attacker).getName();
        addCombatLog(attackerName + " casts Cleave!");

        CombatResult result = combatManager.performAction(new CleaveAction(attacker, currentTarget));

        if (result == null) {
            addCombatLog("Could not perform Cleave.");
            return;
        }

        Player defenderPlayer = (Player) result.defender;
        String defenderName = defenderPlayer.getName();

        if (!result.didHit) {
            addCombatLog(attackerName + " cast Cleave at " + defenderName + " but missed.");
        } else {
            addCombatLog(attackerName + " hit " + defenderName + " for " + result.damageDealt + " damage with Cleave.");
        }

        if (result.targetDefeated) {
            addCombatLog(attackerName + " defeated " + defenderName + " with Cleave!");
            handleCharacterDeath.accept(defenderPlayer);
        }

        refreshUI();
    }

    public void performBlock() {
        if (!validateCombatState()) return;

        Entity blocker = combatManager.getCurrentCombatant();
        if (blocker == null) {
            addCombatLog("No blocker available.");
            return;
        }

        String blockerName = ((Player) blocker).getName();
        addCombatLog(blockerName + " casts Block!");

        CombatResult result = combatManager.performAction(new BlockAction(blocker));

        if (result == null) {
            addCombatLog("Could not perform Block.");
            return;
        }

        addCombatLog(blockerName + " healed for " + result.damageDealt + " HP with Block.");

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
    }

    private void addCombatLog(String message) {
        if (combatLogBox != null) {
            combatLogBox.addEntry(message);
        }
    }
}
