package com.teamfive.dauntlessdungeoneer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

public class CombatLogBox {
    private final Table root;
    private final Label logLabel;
    private final ScrollPane scrollPane;
    private final List<String> entries = new ArrayList<>();
    private final int maxEntries;

    public CombatLogBox(Skin skin, int width, int height, int maxEntries) {
        this.maxEntries = maxEntries;

        root = new Table();
        Drawable background = skin.newDrawable("white", Color.DARK_GRAY);
        root.setBackground(background);

        Table content = new Table();
        logLabel = new Label("Combat log:\n", skin);
        logLabel.setWrap(true);
        logLabel.setAlignment(Align.topLeft);
        logLabel.setColor(Color.WHITE);

        content.add(logLabel).width(width - 20).pad(10).top().left();
        content.top().left();

        scrollPane = new ScrollPane(content, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setOverscroll(false, false);
        scrollPane.setScrollbarsOnTop(true);

        root.add(scrollPane).width(width).height(height).top().left().pad(10);
    }

    public Actor getActor() {
        return root;
    }

    public void addEntry(String entry) {
        if (entries.size() >= maxEntries) {
            entries.remove(0);
        }
        entries.add(entry);
        updateLabel();
    }

    public void clear() {
        entries.clear();
        updateLabel();
    }

    private void updateLabel() {
        StringBuilder builder = new StringBuilder("Combat log:\n");
        for (String line : entries) {
            builder.append(line).append("\n");
        }
        logLabel.setText(builder.toString());
        scrollPane.layout();
        scrollPane.setScrollPercentY(1f);
    }
}
