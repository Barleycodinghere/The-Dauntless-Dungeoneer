package com.teamfive.dauntlessdungeoneer.combat.components;

import com.teamfive.dauntlessdungeoneer.ecs.Component;

public class CombatantComponent implements Component {

    public enum Team {
        PLAYER,
        ENEMY,
        NEUTRAL
    }

    public Team team;
    public boolean isAlive = true;

    public boolean canAct = true;
    public boolean isTargetable = true;

    public CombatantComponent(Team team) {
        this.team = team;
    }
}
