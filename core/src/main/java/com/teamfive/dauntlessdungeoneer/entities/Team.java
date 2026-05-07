package com.teamfive.dauntlessdungeoneer.entities;

import java.util.ArrayList; 
import com.teamfive.dauntlessdungeoneer.components.PlayerClass;

public class Team {
    private final ArrayList<Player> members = new ArrayList<>();

    public void addMember(Player player) {
        members.add(player);
    }

    public ArrayList<Player> getMembers() {
        return members;
    }
    
    // Helper to create a quick team for testing
    public static Team createDefaultPlayerTeam() {
        Team team = new Team();
        team.addMember(new Player(PlayerClass.TANK));
        team.addMember(new Player(PlayerClass.DPS));
        team.addMember(new Player(PlayerClass.DPS));
        team.addMember(new Player(PlayerClass.SUPPORT));
        return team;
    }
}