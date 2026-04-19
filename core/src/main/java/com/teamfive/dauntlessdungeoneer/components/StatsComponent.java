package com.teamfive.dauntlessdungeoneer.components;

import com.teamfive.dauntlessdungeoneer.ecs.Component;

public class StatsComponent implements Component {

    private int maxHP;
    private int currentHP;
    private int maxMana;
    private int currentMana;

    private int AC;
    private int speed;
    private int attack;
    private int defense;

    public StatsComponent(int hp, int mana, int ac, int speed, int attack, int defense) {
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxMana = mana;
        this.currentMana = mana;
        this.AC = ac;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
    }

    // --- getters ---
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }

    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }

    public int getAC() { return AC; }
    public int getSpeed() { return speed; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }

    // --- basic stat changes ---
    public void takeDamage(int amount) {
        currentHP = Math.max(0, currentHP - amount);
    }

    public void useMana(int amount) {
        currentMana = Math.max(0, currentMana - amount);
    }

    public void restoreMana(int amount) {
        currentMana = Math.min(maxMana, currentMana + amount);
    }

    public boolean isAlive() {
        return currentHP > 0;
    }
}
