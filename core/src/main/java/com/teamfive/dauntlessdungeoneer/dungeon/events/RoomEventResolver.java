package com.teamfive.dauntlessdungeoneer.dungeon.events;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.RoomType;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonRoom;

public class RoomEventResolver {

    public void resolveRoom(DungeonRoom room) {
        switch (room.getRoomType()) {
            case START -> handleStart();
            case COMBAT -> handleCombat();
            case TREASURE -> handleTreasure();
            case EMPTY -> handleEmpty();
            case ELITE -> handleElite();
            case BOSS -> handleBoss();
        }
    }

    private void handleStart() {
        System.out.println("You enter the dungeon...");
    }

    private void handleCombat() {
        System.out.println("Combat begins!");
    }

    private void handleTreasure() {
        System.out.println("You found treasure!");
    }

    private void handleEmpty() {
        System.out.println("The room is quiet...");
    }

    private void handleElite() {
        System.out.println("A powerful enemy appears!");
    }

    private void handleBoss() {
        System.out.println("The boss stands before you!");
    }
}
