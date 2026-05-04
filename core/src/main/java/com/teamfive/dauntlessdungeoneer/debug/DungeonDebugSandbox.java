package com.teamfive.dauntlessdungeoneer.debug;

import com.teamfive.dauntlessdungeoneer.dungeon.core.Dungeon;
import com.teamfive.dauntlessdungeoneer.dungeon.events.RoomEventResolver;
import com.teamfive.dauntlessdungeoneer.dungeon.generation.DungeonGenerator;
import com.teamfive.dauntlessdungeoneer.dungeon.generation.DungeonValidator;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonRoom;
import com.teamfive.dauntlessdungeoneer.dungeon.navigation.DungeonNavigator;
import com.teamfive.dauntlessdungeoneer.dungeon.navigation.RoomTransition;

public class DungeonDebugSandbox {

    public static void main(String[] args) {
        DungeonGenerator generator = new DungeonGenerator();
        Dungeon dungeon = generator.generateDungeon(5, 3);


        DungeonNavigator navigator = new DungeonNavigator();
        DungeonValidator validator = new DungeonValidator();
        DungeonPrinter printer = new DungeonPrinter();
        RoomEventResolver resolver = new RoomEventResolver();

        printer.printGraph(dungeon.getGraph());
        validateDungeon(dungeon, validator);
        testNavigation(dungeon, navigator,resolver);
    }


    private static void validateDungeon(Dungeon dungeon, DungeonValidator validator) {
        System.out.println("\n=== DUNGEON VALIDATION ===");

        System.out.println("Dungeon valid: " + validator.isValid(dungeon));
        System.out.println("Has start room: " + validator.hasStart(dungeon));
        System.out.println("Has boss room: " + validator.hasBossRoom(dungeon));
        System.out.println("Boss reachable: " + validator.isBossReachable(dungeon));
        System.out.println("All rooms reachable: " + validator.areAllRoomsReachable(dungeon));

        System.out.println();
    }

    private static void testNavigation(Dungeon dungeon, DungeonNavigator navigator, RoomEventResolver resolver) {
        System.out.println("\n=== NAVIGATION TEST ===");

        resolver.resolveRoom(dungeon.getCurrentRoom());

        while (!dungeon.isAtBossRoom()) {
            System.out.println("Current room: " + dungeon.getCurrentRoom());
            System.out.println("Visited rooms: " + dungeon.getState().getVisitedRoomIds());

            for (RoomTransition transition : navigator.getAvailableTransitions(dungeon)) {
                System.out.println("Available move: "
                    + transition.getFromRoomId()
                    + " -> "
                    + transition.getToRoomId());
            }

            int nextRoomId = dungeon.getState().getCurrentRoomId() + 1;
            System.out.println("Moving to room: " + nextRoomId);

            navigator.moveTo(dungeon, nextRoomId);

            resolver.resolveRoom(dungeon.getCurrentRoom());

            System.out.println();
        }

        System.out.println("Reached boss room: " + dungeon.getCurrentRoom());
        System.out.println("Visited rooms: " + dungeon.getState().getVisitedRoomIds());

        testInvalidMove(dungeon, navigator);
        testBacktracking(dungeon, navigator);
    }

    private static void testInvalidMove(Dungeon dungeon, DungeonNavigator navigator) {
        System.out.println("\n=== INVALID MOVE TEST ===");

        try {
            navigator.moveTo(dungeon, 999);
        } catch (Exception e) {
            System.out.println("Correctly caught invalid move: " + e.getMessage());
        }
    }

    private static void testBacktracking(Dungeon dungeon, DungeonNavigator navigator) {
        System.out.println("\n=== BACKTRACK TEST ===");

        while (dungeon.getState().getCurrentRoomId() != dungeon.getStartRoomId()) {
            int currentRoomId = dungeon.getState().getCurrentRoomId();
            int previousRoomId = currentRoomId - 1;

            System.out.println("Moving back: " + currentRoomId + " -> " + previousRoomId);
            navigator.moveTo(dungeon, previousRoomId);
        }

        System.out.println("Back at start room: " + dungeon.getCurrentRoom());
        System.out.println("Visited rooms: " + dungeon.getState().getVisitedRoomIds());
    }
}
