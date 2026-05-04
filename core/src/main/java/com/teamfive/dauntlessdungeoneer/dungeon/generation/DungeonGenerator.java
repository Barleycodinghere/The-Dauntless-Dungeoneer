package com.teamfive.dauntlessdungeoneer.dungeon.generation;

import com.teamfive.dauntlessdungeoneer.dungeon.core.Dungeon;
import com.teamfive.dauntlessdungeoneer.dungeon.enums.ConnectionType;
import com.teamfive.dauntlessdungeoneer.dungeon.enums.RoomType;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonGraph;
import com.teamfive.dauntlessdungeoneer.dungeon.graph.DungeonRoom;

import java.util.Random;

public class DungeonGenerator {

    private final Random random;
    private final RoomTypeAssigner roomTypeAssigner;

    public DungeonGenerator() {
        this.random = new Random();
        this.roomTypeAssigner = new RoomTypeAssigner();
    }

    public DungeonGenerator(long seed) {
        this.random = new Random(seed);
        this.roomTypeAssigner = new RoomTypeAssigner();
    }

    public Dungeon generateLinearDungeon(int roomCount) {
        if (roomCount < 2) {
            throw new IllegalArgumentException("A dungeon must have at least 2 rooms.");
        }

        DungeonGraph graph = new DungeonGraph();

        int startRoomId = 0;
        int bossRoomId = roomCount - 1;

        for (int i = 0; i < roomCount; i++) {
            RoomType roomType = RoomType.COMBAT;

            if (i == startRoomId) {
                roomType = RoomType.START;
            } else if (i == bossRoomId) {
                roomType = RoomType.BOSS;
            }

            graph.addRoom(new DungeonRoom(i, roomType));
        }

        for (int i = 0; i < roomCount - 1; i++) {
            graph.connectRooms(i, i+1, ConnectionType.NORMAL);
        }

        return new Dungeon(graph,startRoomId,bossRoomId);
    }

    public Dungeon generateDungeon(int mainPathLength, int branchCount) {
        if (mainPathLength < 2) {
            throw new IllegalArgumentException("Main path must have at least 2 rooms.");
        }

        if (branchCount < 0) {
            throw new IllegalArgumentException("Branch count cannot be negative.");
        }

        DungeonGraph graph = new DungeonGraph();

        int startRoomId = 0;
        int bossRoomId = mainPathLength - 1;
        int totalRoomCount = mainPathLength + branchCount;

        createRooms(graph, totalRoomCount);
        connectMainPath(graph, mainPathLength);
        createBranchConnections(graph, mainPathLength, branchCount, bossRoomId);

        roomTypeAssigner.assignRoomTypes(graph, startRoomId, bossRoomId);

        return new Dungeon(graph, startRoomId, bossRoomId);
    }

    private void connectMainPath(DungeonGraph graph, int mainPathLength) {
        for (int i = 0; i < mainPathLength - 1; i++) {
            graph.connectRooms(i, i + 1, ConnectionType.NORMAL);
        }
    }

    private void createRooms(DungeonGraph graph, int roomCount) {
        for (int i = 0; i < roomCount; i++) {
            graph.addRoom(new DungeonRoom(i, RoomType.COMBAT));
        }
    }

    private void createBranchConnections(DungeonGraph graph, int mainPathLength, int branchCount, int bossRoomId) {
        int nextRoomId = mainPathLength;

        for (int i = 0; i < branchCount; i++) {
            int branchRoomId = nextRoomId;
            nextRoomId++;

            int parentRoomId = getRandomBranchParent(mainPathLength, bossRoomId);

            graph.connectRooms(parentRoomId, branchRoomId, ConnectionType.NORMAL);
        }
    }

    private int getRandomBranchParent(int mainPathLength, int bossRoomId) {
        int parentRoomId;

        do {
            parentRoomId = random.nextInt(mainPathLength);
        } while (parentRoomId == bossRoomId);

        return parentRoomId;
    }
}
