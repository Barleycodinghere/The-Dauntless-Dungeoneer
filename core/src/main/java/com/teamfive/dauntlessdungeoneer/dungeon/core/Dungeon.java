package com.teamfive.dauntlessdungeoneer.dungeon.core;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.DungeonStatus;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonGraph;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonRoom;

public class Dungeon {

    private final DungeonGraph graph;
    private DungeonState state;

    private final int startRoomId;
    private final int bossRoomId;
    public Dungeon(DungeonGraph graph, int startRoomId, int bossRoomId) {
        this.graph = graph;
        this.startRoomId = startRoomId;
        this.bossRoomId = bossRoomId;
        this.state = new DungeonState(startRoomId);
    }

    public DungeonGraph getGraph() {
        return graph;
    }

    public DungeonState getState() {
        return state;
    }

    public int getStartRoomId() {
        return startRoomId;
    }

    public int getBossRoomId() {
        return bossRoomId;
    }

    public DungeonRoom getCurrentRoom() {
        return graph.getRoom(state.getCurrentRoomId());
    }

    public boolean isAtBossRoom() {
        return state.getCurrentRoomId() == bossRoomId;
    }
}
