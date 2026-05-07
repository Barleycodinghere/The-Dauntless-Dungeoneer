package com.teamfive.dauntlessdungeoneer.combat.abilities;

import com.teamfive.dauntlessdungeoneer.components.PlayerClass;
import com.teamfive.dauntlessdungeoneer.components.MonsterClass;
import com.teamfive.dauntlessdungeoneer.components.CharacterClass;

import com.teamfive.dauntlessdungeoneer.combat.abilities.dps.*;
import com.teamfive.dauntlessdungeoneer.combat.abilities.tank.*;
import com.teamfive.dauntlessdungeoneer.combat.abilities.support.*;
import com.teamfive.dauntlessdungeoneer.combat.abilities.BasicAttackAbility;

import java.util.List;

public class AbilityFactory {

    public static AbilityLoadout createLoadout(CharacterClass characterClass) {

        if (characterClass instanceof PlayerClass pc) {
            return switch (pc) {
                case DPS -> getDPSLoadout();
                case TANK -> getTankLoadout();
                case SUPPORT -> getSupportLoadout();
            };
        }

        if (characterClass instanceof MonsterClass mc) {
            return switch (mc) {
                case DOG -> getTankLoadout();    // Dog acts like a Tank
                case GOBLIN -> getDPSLoadout();  // Goblin acts like a DPS
                default -> new AbilityLoadout(List.of(new BasicAttackAbility()));
            };
        }

        return new AbilityLoadout(List.of(new BasicAttackAbility()));
    }

    private static AbilityLoadout getTankLoadout() {
        return new AbilityLoadout(List.of(
                new BasicAttackAbility(),
                new HeavyAttackAbility(),
                new CleaveAbility(),
                new BlockAbility()
        ));
    }

    private static AbilityLoadout getDPSLoadout() {
        return new AbilityLoadout(List.of(
                new BasicAttackAbility(),
                new FireballAbility(),
                new MagicMissileAbility(),
                new RecoverAbility()
        ));
    }

    private static AbilityLoadout getSupportLoadout() {
        return new AbilityLoadout(List.of(
                new BasicAttackAbility(),
                new SmiteAbility(),
                new MassHealAbility(),
                new HealAbility()
        ));
    }
}