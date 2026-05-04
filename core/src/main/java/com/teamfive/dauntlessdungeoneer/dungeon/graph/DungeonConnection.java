package com.teamfive.dauntlessdungeoneer.dungeon.graph;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.ConnectionType;

import java.sql.Struct;

public class DungeonConnection {

    private final int roomAId;
    private final int roomBId;
    private final ConnectionType connectionType;

    public DungeonConnection(int roomAId, int roomBId, ConnectionType connectionType) {
        this.roomAId = roomAId;
        this.roomBId = roomBId;
        this.connectionType = connectionType;
    }

    public int getRoomAId() {
        return roomAId;
    }

    public int getRoomBId() {
        return roomBId;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public boolean connects(int roomId) {
        return roomId == roomAId || roomId == roomBId;
    }

    public int getOtherRoomId(int roomId) {
        if (roomId != roomAId &&  roomId != roomBId) {
            throw new IllegalArgumentException("Room " + roomId + " is not part of this connection.");
        }
        return roomId == roomAId ? roomBId : roomAId;
    }

    @Override
    public String toString() {
        return roomAId + " <--> " + roomBId + " [" + connectionType + "] ";
    }
}
