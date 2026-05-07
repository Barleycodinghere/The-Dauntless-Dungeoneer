package com.teamfive.dauntlessdungeoneer.combat.abilities;

import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public abstract class Ability {

    protected final String name;
    protected final int manaCost;

    public Ability(String name, int manaCost) {
        this.name = name;
        this.manaCost = manaCost;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public abstract CombatResult execute(Entity actor, Entity target, CombatManager combatManager);
}