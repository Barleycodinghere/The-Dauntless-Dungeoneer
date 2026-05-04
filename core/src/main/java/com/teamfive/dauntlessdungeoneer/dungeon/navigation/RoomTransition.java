package com.teamfive.dauntlessdungeoneer.dungeon.navigation;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.DungeonStatus;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonConnection;

public class RoomTransition {

    private final int fromRoomId;
    private final int toRoomId;
    private final DungeonConnection connection;

    public RoomTransition(int fromRoomId, int toRoomId, DungeonConnection connection) {
        this.fromRoomId = fromRoomId;
        this.toRoomId = toRoomId;
        this.connection = connection;
    }

    public int getFromRoomId() {
        return fromRoomId;
    }
    public int getToRoomId() {
        return toRoomId;
    }

    public DungeonConnection getConnection() {
        return connection;
    }
}
