package com.teamfive.dauntlessdungeoneer.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.components.*;

public class Player extends Entity {

    private static Texture playerTexture;
    private final TextureRegion sprite;
    private final PlayerClass playerClass;
    private final String name;

    public Player(PlayerClass playerClass) {
        super();
        this.playerClass = playerClass;
        this.name = getDefaultName(playerClass);

        if (playerTexture == null) {
            playerTexture = createPlayerTexture();
        }
        this.sprite = new TextureRegion(playerTexture);

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
            default: return "Unknown";
        }
    }

    private static Texture createPlayerTexture() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.SKY);
        pixmap.fillCircle(8, 8, 8);
        pixmap.setColor(Color.GOLD);
        pixmap.drawCircle(8, 8, 7);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
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
