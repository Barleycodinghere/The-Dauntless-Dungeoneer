package com.teamfive.dauntlessdungeoneer.entities;

import com.badlogic.gdx.utils.Array;
import com.teamfive.dauntlessdungeoneer.components.PlayerClass;

public class Team {
    private final Array<Player> members = new Array<>();

    public void addMember(Player player) {
        members.add(player);
    }

    public Array<Player> getMembers() {
        return members;
    }
    
    // Helper to create a quick team for testing
    public static Team createDefaultPlayerTeam() {
        Team team = new Team();
        team.addMember(new Player(PlayerClass.TANK));
        team.addMember(new Player(PlayerClass.DPS));
        team.addMember(new Player(PlayerClass.SUPPORT));
        return team;
    }
}