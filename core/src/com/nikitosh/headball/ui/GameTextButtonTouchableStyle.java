package com.nikitosh.headball.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.nikitosh.headball.utils.AssetLoader;

public class GameTextButtonTouchableStyle extends TextButton.TextButtonStyle {
    public GameTextButtonTouchableStyle() {
        super();
        up = AssetLoader.skin.getDrawable("red_button01");
        down = AssetLoader.skin.getDrawable("red_button00");
        pressedOffsetX = 1;
        pressedOffsetY = -1;
        font = AssetLoader.font;
    }
}