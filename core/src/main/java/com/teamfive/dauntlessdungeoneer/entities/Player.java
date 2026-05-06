package com.teamfive.dauntlessdungeoneer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.components.*;

public class Player extends Entity {

    private final Texture texture;
    private final TextureRegion sprite;
    private final PlayerClass playerClass;
    private final String name;

    public Player(PlayerClass playerClass) {
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

    private static String getDefaultName(PlayerClass playerClass) {
        switch (playerClass) {
            case TANK: return "Leeroy";
            case DPS: return "Mr. Bigglesworth";
            case SUPPORT: return "Mochi";
            case GOBLIN: return "Goblin";
            case DOG: return "Dog";
            default: return "Unknown";
        }
    }

    private static String getTexturePath(PlayerClass playerClass) {
        switch (playerClass) {
            case TANK: return "EntityImages/Warrior.png";
            case DPS: return "EntityImages/Wizard.png";
            case SUPPORT: return "EntityImages/Healer.png";
            case GOBLIN: return "EntityImages/Goblin.png";
            case DOG: return "EntityImages/Dog.png";
            default: return "EntityImages/Warrior.png"; // fallback
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color originalColor = batch.getColor();
        batch.setColor(originalColor.r, originalColor.g, originalColor.b, originalColor.a * parentAlpha);
        batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
        batch.setColor(originalColor);
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public String getName() {
        return name;
    }
}
