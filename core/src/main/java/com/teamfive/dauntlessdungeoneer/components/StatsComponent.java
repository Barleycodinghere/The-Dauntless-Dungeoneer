package com.teamfive.dauntlessdungeoneer.components;
import com.teamfive.dauntlessdungeoneer.ecs.Component;
import com.teamfive.dauntlessdungeoneer.items.Item;

public class StatsComponent implements Component {

    private int maxHP;
    private int currentHP;
    private int maxMana;
    private int currentMana;

    private int AC;
    private int speed;
    private int attack;
    private int defense;

    private int bonusAttack;
    private int bonusDefense;

    public StatsComponent(int hp, int mana, int ac, int speed, int attack, int defense) {
        this.maxHP = hp;
        this.currentHP = hp;
        this.maxMana = mana;
        this.currentMana = mana;
        this.AC = ac;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.bonusAttack = 0;
        this.bonusDefense = 0;
    }

    // --- getters ---
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }

    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }

    public int getAC() { return AC; }
    public int getSpeed() { return speed; }
    public int getAttack() { return attack + bonusAttack;}
    public int getDefense() { return defense + bonusDefense; }

    // --- basic stat changes ---
    public void takeDamage(int amount) {
        currentHP = Math.max(0, currentHP - amount);
    }
    public void heal(int amount) {
        currentHP = Math.min(currentHP + amount, maxHP);
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

    // --- bonus stat changes ---
    public void equip(Item equip) {
        Item[] Hands = new Item[2];
        //hand 0 is the weapon, and hand 1 is the armor
        //equip.getStatBonus();
        switch (equip.getType()) {
            case 1:
                Hands[0] = equip;
                System.out.println("Weapon Equipped");
                bonusAttack = equip.getStatBonus();
                break;
            case 2:
                Hands[1] = equip;
                System.out.println("Defense Equipped");
                bonusDefense = equip.getStatBonus();
                break;
            default:
                System.out.println("This is a consumable, it cannot be equipped.");
        }

    }
}
