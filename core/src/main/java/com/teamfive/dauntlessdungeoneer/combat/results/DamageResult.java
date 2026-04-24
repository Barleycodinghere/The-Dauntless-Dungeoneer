package com.teamfive.dauntlessdungeoneer.combat.results;

public class DamageResult {

    public final int baseDamage;
    public final int defenseReduction;
    public final int finalDamage;

    public DamageResult(int baseDamage, int defenseReduction, int finalDamage) {
        this.baseDamage = baseDamage;
        this.defenseReduction = defenseReduction;
        this.finalDamage = finalDamage;
    }
}
