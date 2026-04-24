package com.teamfive.dauntlessdungeoneer.combat.results;

public class HitResult {

    public final boolean didHit;
    public final int hitChance;

    public HitResult(boolean didHit, int hitChance) {
        this.didHit = didHit;
        this.hitChance = hitChance;
    }

}
