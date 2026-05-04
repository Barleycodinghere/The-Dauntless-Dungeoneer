package com.teamfive.dauntlessdungeoneer.dungeon.graph;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.ConnectionType;

import java.util.*;

public class DungeonGraph {

    private final Map<Integer, DungeonRoom> rooms;
    private final List<DungeonConnection> connections;

    public DungeonGraph() {
        this.rooms = new HashMap<Integer, DungeonRoom>();
        this.connections = new ArrayList<DungeonConnection>();
    }

    public void addRoom(DungeonRoom room) {
        if (rooms.containsKey(room.getRoomId())) {
            throw new IllegalArgumentException("Room with ID " + room.getRoomId() + " already exists");
        }

        rooms.put(room.getRoomId(), room);
    }

    public DungeonRoom getRoom(int roomId) {
        DungeonRoom room = rooms.get(roomId);

        if (room == null) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }

        return room;
    }

    public void connectRooms(int roomAId, int roomBId, ConnectionType connectionType) {
        if (!rooms.containsKey(roomAId) || !rooms.containsKey(roomBId)) {
            throw new IllegalArgumentException("Both rooms must exist before connecting.");
        }

        if (roomAId == roomBId) {
            throw new IllegalArgumentException("A room cannot connect to itself.");
        }

        if (areRoomsConnected(roomAId,roomBId)) {
            throw new IllegalArgumentException("Rooms " + roomAId + " and " + roomBId + " are already connected.");
        }

        connections.add(new DungeonConnection(roomAId,roomBId,connectionType));
    }

    public boolean areRoomsConnected(int roomAId, int roomBId) {
        for (DungeonConnection connection : connections) {
            boolean sameDirection = connection.getRoomAId() == roomAId && connection.getRoomBId() == roomBId;
            boolean oppositeDirection = connection.getRoomAId() == roomBId && connection.getRoomBId() == roomAId;

            if (sameDirection || oppositeDirection) {
                return true;
            }
        }
        return false;
    }

    public List<DungeonRoom> getConnectedRooms(int roomId) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }

        List<DungeonRoom> connectedRooms = new  ArrayList<>();

        for (DungeonConnection connection : connections) {
            if (connection.connects(roomId)) {
                int otherRoomId = connection.getOtherRoomId(roomId);
                connectedRooms.add(getRoom(otherRoomId));
            }
        }
        return connectedRooms;
    }

    public List<DungeonConnection> getConnectionsForRoom(int roomId) {
        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist.");
        }

        List<DungeonConnection> roomConnections = new  ArrayList<>();

        for (DungeonConnection connection : connections) {
            if (connection.connects(roomId)) {
                roomConnections.add(connection);
            }
        }
        return roomConnections;
    }

    public Map<Integer, DungeonRoom> getRooms() {
        return Collections.unmodifiableMap(rooms);
    }

    public List<DungeonConnection> getConnections() {
        return Collections.unmodifiableList(connections);
    }
}
