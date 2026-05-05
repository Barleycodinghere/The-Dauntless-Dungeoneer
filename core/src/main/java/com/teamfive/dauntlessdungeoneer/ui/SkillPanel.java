package com.teamfive.dauntlessdungeoneer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class SkillPanel {

    public interface SkillListener {
        void onSkill(int index);
    }

    private final Table root;
    private final TextButton basicAttackButton;
    private final List<TextButton> skillButtons = new ArrayList<>();

    public SkillPanel(Skin skin, SkillListener listener) {
        root = new Table();

        TextButton.TextButtonStyle abilityStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        abilityStyle.up = skin.newDrawable("white", Color.valueOf("#787276"));
        abilityStyle.fontColor = Color.WHITE;

        TextButton basicButton = null;
        for (int i = 1; i <= 4; i++) {
            final int skillIndex = i;
            TextButton skillBtn = new TextButton(skillIndex == 1 ? "Basic Attack" : "Skill " + skillIndex, abilityStyle);
            skillBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    listener.onSkill(skillIndex);
                }
            });
            if (skillIndex == 1) {
                basicButton = skillBtn;
            } else {
                skillBtn.setDisabled(true);
            }
            skillButtons.add(skillBtn);
            root.add(skillBtn).width(120).height(50).pad(10).left().row();
        }
        basicAttackButton = basicButton;
    }

    public Actor getActor() {
        return root;
    }

    public void setBasicAttackEnabled(boolean enabled) {
        basicAttackButton.setDisabled(!enabled);
    }
}
