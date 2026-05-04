package com.teamfive.dauntlessdungeoneer.dungeon.generation;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.RoomType;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonGraph;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonRoom;

import java.util.Random;
import java.util.Set;

public class RoomTypeAssigner {

    private final Random random = new Random();

    public void assignRoomTypes(DungeonGraph graph, int startRoomId, int bossRoomId) {
        for (DungeonRoom room : graph.getRooms().values()) {
            int roomId = room.getRoomId();

            if (roomId == startRoomId) {
                room.setRoomType(RoomType.START);
            } else if (roomId == bossRoomId) {
                room.setRoomType(RoomType.BOSS);
            } else {
                room.setRoomType(getRandomRoomType());
            }
        }
    }

    private RoomType getRandomRoomType() {
        int roll = random.nextInt(100);

        if (roll < 60) {
            return RoomType.COMBAT;
        } else if (roll < 75) {
            return RoomType.EMPTY;
        } else if (roll < 90) {
            return RoomType.TREASURE;
        } else {
            return RoomType.ELITE;
        }
    }
}
