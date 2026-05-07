package com.teamfive.dauntlessdungeoneer.combat.abilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbilityLoadout {

    private final ArrayList<Ability> abilities;

    public AbilityLoadout(List<Ability> abilities) {
        this.abilities = new ArrayList<>(abilities);
    }

    public Ability get(int index) {
        if (index < 0 || index >= abilities.size()) return null;
        return abilities.get(index);
    }

    public List<Ability> getAll() {
        return Collections.unmodifiableList(abilities);
    }

    public int size() {
        return abilities.size();
    }

    public boolean hasAbility(int index) {
        return index >= 0 && index < abilities.size() && abilities.get(index) != null;
    }

    public boolean isValidSlot(int index) {
        return index >= 0 && index < abilities.size();
    }
}