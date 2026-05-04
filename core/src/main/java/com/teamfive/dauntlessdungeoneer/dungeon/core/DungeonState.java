package com.teamfive.dauntlessdungeoneer.dungeon.core;

import com.teamfive.dauntlessdungeoneer.dungeon.enums.DungeonStatus;
import com.teamfive.dauntlessdungeoneer.dungeon.enums.RoomType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DungeonState {

    private int currentRoomId;
    private final Set<Integer> visitedRoomIds;
    private DungeonStatus status;

    public DungeonState(int startingRoomId) {
       this.currentRoomId = startingRoomId;
       this.visitedRoomIds = new HashSet<>();
       this.visitedRoomIds.add(startingRoomId);
       this.status = DungeonStatus.NOT_STARTED;
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoomId(int currentRoomId) {
        this.currentRoomId = currentRoomId;
        this.visitedRoomIds.add(currentRoomId);
    }

    public boolean hasVisitedRoomId(int roomId) {
        return visitedRoomIds.contains(roomId);
    }

    public Set<Integer> getVisitedRoomIds() {
        return Collections.unmodifiableSet(visitedRoomIds);
    }

    public DungeonStatus getStatus() {
        return status;
    }

    public void setStatus(DungeonStatus status) {
        this.status = status;
    }

}
