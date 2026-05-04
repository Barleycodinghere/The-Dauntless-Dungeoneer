package com.teamfive.dauntlessdungeoneer.dungeon.graph;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.RoomType;

public class DungeonRoom {

    private final int roomId;
    private RoomType roomType;

    public  DungeonRoom(int roomId, RoomType roomType) {
        this.roomId = roomId;
        this.roomType = roomType;
    }

    public int getRoomId() {
        return roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room " + roomId + " [" + roomType + "]";
    }

}
