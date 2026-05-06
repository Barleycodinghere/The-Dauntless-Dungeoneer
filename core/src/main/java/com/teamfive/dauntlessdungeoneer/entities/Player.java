package com.teamfive.dauntlessdungeoneer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.components.*;

public class Player extends Entity {

    private final Texture texture;
    private final TextureRegion sprite;
    private final CharacterClass playerClass;
    private final String name;

    public Player(CharacterClass playerClass) {
        super();
        this.playerClass = playerClass;
        this.name = getDefaultName(playerClass);

        String texturePath = getTexturePath(playerClass);
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new TextureRegion(texture);

        setSize(80, 80);

        // Default components for a player
        addComponent(StatsComponent.class, StatsFactory.createStats(playerClass));
        // This ass ths inventory for a player
        addComponent(InventoryComponent.class, new InventoryComponent());
    }

    private static String getDefaultName(CharacterClass characterClass) {
        if (characterClass instanceof PlayerClass) {
            PlayerClass pc = (PlayerClass) characterClass;
            switch (pc) {
                case TANK: return "Leeroy";
                case DPS: return "Mr. Bigglesworth";
                case SUPPORT: return "Mochi";
                default: return "Unknown";
            }
        } else if (characterClass instanceof MonsterClass) {
            MonsterClass mc = (MonsterClass) characterClass;
            switch (mc) {
                case GOBLIN: return "Goblin";
                case DOG: return "Dog";
                default: return "Unknown";
            }
        }
        return "Unknown";
    }

    private static String getTexturePath(CharacterClass characterClass) {
        if (characterClass instanceof PlayerClass) {
            PlayerClass pc = (PlayerClass) characterClass;
            switch (pc) {
                case TANK: return "EntityImages/Warrior.png";
                case DPS: return "EntityImages/Wizard.png";
                case SUPPORT: return "EntityImages/Healer.png";
                default: return "EntityImages/Warrior.png"; // fallback
            }
        } else if (characterClass instanceof MonsterClass) {
            MonsterClass mc = (MonsterClass) characterClass;
            switch (mc) {
                case GOBLIN: return "EntityImages/Goblin.png";
                case DOG: return "EntityImages/Dog.png";
                default: return "EntityImages/Warrior.png"; // fallback
            }
        }
        return "EntityImages/Warrior.png"; // fallback
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color originalColor = batch.getColor();
        batch.setColor(originalColor.r, originalColor.g, originalColor.b, originalColor.a * parentAlpha);
        batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
        batch.setColor(originalColor);
    }

    public CharacterClass getPlayerClass() {
        return playerClass;
    }

    public String getName() {
        return name;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public boolean isAlive() {
        StatsComponent stats = getComponent(StatsComponent.class);
        return stats != null && stats.getCurrentHP() > 0;
    }
}
