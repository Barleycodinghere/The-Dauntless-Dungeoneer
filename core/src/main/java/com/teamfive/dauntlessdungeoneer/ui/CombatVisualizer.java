package com.teamfive.dauntlessdungeoneer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

public class CombatVisualizer {
    private final Stage stage;
    private final Texture fireballTexture;

    public CombatVisualizer(Stage stage) {
        this.stage = stage;
        // Load once to prevent memory leaks
        this.fireballTexture = new Texture(Gdx.files.internal("EntityImages/Fireball.png"));
    }

    public void playFireballEffect(Entity caster, Entity target, Runnable onHit) {
        Actor casterActor = stage.getRoot().findActor("unit_" + caster.getId());
        Actor targetActor = stage.getRoot().findActor("unit_" + target.getId());

        if (casterActor != null && targetActor != null) {
            // Get center coordinates of the 200x200 frames
            Vector2 start = casterActor.localToStageCoordinates(new Vector2(100, 100));
            Vector2 end = targetActor.localToStageCoordinates(new Vector2(100, 100));

            Projectile fireball = new Projectile(fireballTexture, start, end, 0.5f, onHit);
            stage.addActor(fireball);
        } else {
            onHit.run(); // Fallback if UI is missing
        }
    }

    public void dispose() {
        fireballTexture.dispose();
    }
}
