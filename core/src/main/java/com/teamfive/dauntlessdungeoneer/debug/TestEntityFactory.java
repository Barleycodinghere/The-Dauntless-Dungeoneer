package com.teamfive.dauntlessdungeoneer.debug;

import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;
import com.teamfive.dauntlessdungeoneer.components.PlayerClass;
import com.teamfive.dauntlessdungeoneer.components.StatsComponent;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.entities.Player;

public class TestEntityFactory {

    public static Entity createPlayer(PlayerClass playerClass) {
        Player player = new Player(playerClass);
        CombatantComponent combatant = new CombatantComponent(CombatantComponent.Team.PLAYER);
        NameComponent nameComponent = new NameComponent("Player");

        player.addComponent(CombatantComponent.class, combatant);
        player.addComponent(NameComponent.class, nameComponent);

        return player;
    }

    public static Entity createGoblin () {
        Entity goblin = new Entity();
        StatsComponent statsComponent = new StatsComponent(
            60,      //hp
            1,          //mana
            55,          //ac
            13,          //speed
            13,          //atk
            4           //def
        );
        NameComponent nameComponent = new NameComponent("Goblin");
        CombatantComponent combatantComponent = new CombatantComponent(CombatantComponent.Team.ENEMY);

        goblin.addComponent(StatsComponent.class, statsComponent);
        goblin.addComponent(NameComponent.class, nameComponent);
        goblin.addComponent(CombatantComponent.class, combatantComponent);

        return goblin;
    }
}
