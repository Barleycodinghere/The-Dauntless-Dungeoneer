package com.teamfive.dauntlessdungeoneer.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Projectile extends Image {
    public Projectile(Texture texture, Vector2 start, Vector2 end, float duration, Runnable onHit) {
        super(texture);
        this.setSize(100, 100); 
        this.setOrigin(50, 50);
        this.setPosition(start.x - 50, start.y - 50); // Center it on the start point

        this.addAction(Actions.sequence(
            Actions.moveTo(end.x - 50, end.y - 50, duration), 
            Actions.run(onHit), 
            Actions.removeActor()
        ));
    }
}
