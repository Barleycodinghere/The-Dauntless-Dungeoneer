package com.teamfive.dauntlessdungeoneer.dungeon.navigation;

import com.teamfive.dauntlessdungeoneer.dungeon.core.Dungeon;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonConnection;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonGraph;

import java.util.ArrayList;
import java.util.List;

public class DungeonNavigator {

    public List<RoomTransition> getAvailableTransitions(Dungeon dungeon) {
        int currentRoomId = dungeon.getState().getCurrentRoomId();
        DungeonGraph graph = dungeon.getGraph();

        List<DungeonConnection> connections = graph.getConnectionsForRoom(currentRoomId);
        List<RoomTransition> transitions = new ArrayList<>();

        for  (DungeonConnection connection : connections) {
            int toRoomId = connection.getOtherRoomId(currentRoomId);
            transitions.add(new RoomTransition(currentRoomId, toRoomId, connection));
        }

        return transitions;
    }

    public boolean canMoveTo(Dungeon dungeon, int targetRoomId) {
        return getAvailableTransitions(dungeon).stream().anyMatch((t -> t.getToRoomId() == targetRoomId));
    }

    public RoomTransition moveTo(Dungeon dungeon, int targetRoomId) {
        List<RoomTransition> transitions = getAvailableTransitions(dungeon);

        for ( RoomTransition transition : transitions ) {
            if (transition.getToRoomId() == targetRoomId) {
                dungeon.getState().setCurrentRoomId(targetRoomId);
                return transition;
            }
        }

        throw new IllegalArgumentException("Cannot move to room " +targetRoomId + " from current room." );
    }
}
