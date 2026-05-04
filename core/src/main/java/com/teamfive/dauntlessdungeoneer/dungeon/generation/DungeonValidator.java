package com.teamfive.dauntlessdungeoneer.dungeon.generation;

import com.teamfive.dauntlessdungeoneer.dungeon.core.Dungeon;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonGraph;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonRoom;

import java.util.HashSet;
import java.util.Set;

public class DungeonValidator {

    public boolean isValid(Dungeon dungeon) {
        return hasStart(dungeon)
            && hasBossRoom(dungeon)
            && isBossReachable(dungeon)
            && areAllRoomsReachable(dungeon);
    }

    public boolean hasStart(Dungeon dungeon) {
        return dungeon.getGraph().getRooms().containsKey(dungeon.getStartRoomId());
    }

    public boolean hasBossRoom(Dungeon dungeon) {
        return dungeon.getGraph().getRooms().containsKey(dungeon.getBossRoomId());
    }

    public boolean isBossReachable(Dungeon dungeon) {
        Set<Integer> visited = getReachableRoomIds(dungeon);
        return visited.contains(dungeon.getBossRoomId());
    }

    public boolean areAllRoomsReachable(Dungeon dungeon) {
        Set<Integer> visited = getReachableRoomIds(dungeon);
        return visited.size() == dungeon.getGraph().getRooms().size();
    }

    private Set<Integer> getReachableRoomIds(Dungeon dungeon) {
        Set<Integer> visited = new HashSet<>();
        explore(dungeon.getGraph(), dungeon.getStartRoomId(), visited);
        return visited;
    }

    private void explore(DungeonGraph graph, int roomId, Set<Integer> visited) {
        if (visited.contains(roomId)) {
            return;
        }

        visited.add(roomId);

        for (DungeonRoom connectedRoom : graph.getConnectedRooms(roomId)) {
            explore(graph, connectedRoom.getRoomId(), visited);
        }
    }
}
