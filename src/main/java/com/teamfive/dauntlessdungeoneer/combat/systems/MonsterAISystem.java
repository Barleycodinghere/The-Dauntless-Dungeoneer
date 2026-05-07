package com.teamfive.dauntlessdungeoneer.combat.systems;

import com.badlogic.gdx.math.MathUtils;
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.abilities.AbilityLoadout;
import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MonsterAISystem {

    private final CombatManager combatManager;
    private final Team playerTeam;
    private final Consumer<String> logger;
    private final Consumer<Player> deathHandler;

    public MonsterAISystem(CombatManager combatManager, Team playerTeam, Consumer<String> logger, Consumer<Player> deathHandler) {
        this.combatManager = combatManager;
        this.playerTeam = playerTeam;
        this.logger = logger;
        this.deathHandler = deathHandler;
    }

    public void executeTurn(Entity enemy) {
        if (!(enemy instanceof Player)) return;
        Player monster = (Player) enemy;
        StatsComponent stats = monster.getComponent(StatsComponent.class);
        if (stats == null) return;

        // 1. Find a valid target (any alive player)
        ArrayList<Player> candidates = new ArrayList<>();
        for (Player p : playerTeam.getMembers()) {
            CombatantComponent cc = p.getComponent(CombatantComponent.class);
            if (cc != null && cc.isAlive) {
                candidates.add(p);
            }
        }

        if (candidates.isEmpty()) {
            logger.accept("The monsters have no one left to attack!");
            return;
        }

        Player target = candidates.get(MathUtils.random(candidates.size() - 1));

        // 2. Ability Selection Logic
        AbilityLoadout loadout = monster.getAbilityLoadout();
        ArrayList<Ability> usableAbilities = new ArrayList<>();

        // Loop through all potential slots (0-3)
        for (int i = 0; i < 4; i++) {
            Ability ability = loadout.get(i);
            // Only consider it if it's not null and monster has enough mana
            if (ability != null && stats.getCurrentMana() >= ability.getManaCost()) {
                usableAbilities.add(ability);
            }
        }

        // Default to Basic Attack if somehow nothing else is found, else pick random usable
        Ability chosenAbility = usableAbilities.isEmpty() ? loadout.get(0) : 
                                usableAbilities.get(MathUtils.random(usableAbilities.size() - 1));

        // 3. Execute
        logger.accept(monster.getName() + " uses " + chosenAbility.getName() + " on " + target.getName() + "!");
        CombatResult result = chosenAbility.execute(monster, target, combatManager);

        // Deduct Mana
        stats.useMana(chosenAbility.getManaCost());

        // 4. Process the Result
        if (result != null && result.didHit) {
            logger.accept("The attack hit for " + result.damageDealt + " damage.");
            if (result.targetDefeated) {
                logger.accept(target.getName() + " has been defeated!");
                deathHandler.accept(target);
            }
        } else {
            logger.accept("The attack missed!");
        }
    }
}
