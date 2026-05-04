package com.teamfive.dauntlessdungeoneer.debug;

import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonGraph;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonRoom;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class DungeonPrinter {

    public void printGraph(DungeonGraph graph) {
        System.out.println("=== DUNGEON GRAPH ===");

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        int startId = 0;
        queue.add(startId);
        visited.add(startId);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            DungeonRoom room = graph.getRoom(current);

            System.out.print(room + " -> ");

            for (DungeonRoom neighbor : graph.getConnectedRooms(current)) {
                System.out.print("[" + neighbor.getRoomId() + "]");

                if (!visited.contains(neighbor.getRoomId())) {
                    visited.add(neighbor.getRoomId());
                    queue.add(neighbor.getRoomId());
                }
            }

            System.out.println();
        }
    }
}
