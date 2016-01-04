package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;
import com.nikitosh.headball.ui.GameButtonStyle;
import com.nikitosh.headball.ui.GameTextButton;
import com.nikitosh.headball.ui.GameTextButtonTouchable;

public class SettingsScreen implements Screen {
    private Stage stage;
    private Table settingsTable;
    private Table backButtonTable;

    private final Game game;
    private Drawable[] drawables;
    private int soundState = GameSettings.getBoolean("sound") ? 0 : 1;
    private int musicState = GameSettings.getBoolean("music") ? 0 : 1;
    private SelectBox<String> selectBox;

    public SettingsScreen(final Game game) {
        this.game = game;
        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        Button backButton = new GameTextButtonTouchable("Back");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (soundState == 0) {
                    GameSettings.putBoolean("sound", true);
                } else {
                    GameSettings.putBoolean("sound", false);
                }
                if (musicState == 0) {
                    GameSettings.putBoolean("music", true);
                } else {
                    GameSettings.putBoolean("music", false);
                }
                GameSettings.putString("control", selectBox.getSelected());
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        });
        backButtonTable.add(backButton).top().left().expand().pad(Constants.UI_ELEMENTS_INDENT).row();

        settingsTable = new Table();
        settingsTable.setFillParent(true);

        drawables = new Drawable[2];
        drawables[0] = AssetLoader.skin.getDrawable("red_boxCheckmark");
        drawables[1] = AssetLoader.skin.getDrawable("red_boxCross");

        Button soundTextButton = new GameTextButton("Sound");
        settingsTable.add(soundTextButton).pad(Constants.UI_ELEMENTS_INDENT);

        final Button soundButton = new Button(new GameButtonStyle("red_boxCheckmark"));
        soundButton.getStyle().up = drawables[soundState];
        settingsTable.add(soundButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        Button musicTextButton = new GameTextButton("Music");
        settingsTable.add(musicTextButton).pad(Constants.UI_ELEMENTS_INDENT);

        final Button musicButton = new Button(new GameButtonStyle("red_boxCheckmark"));
        musicButton.getStyle().up = drawables[musicState];
        settingsTable.add(musicButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundState = 1 - soundState;
                soundButton.getStyle().up = drawables[soundState];
            }
        });
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicState = 1 - musicState;
                musicButton.getStyle().up = drawables[musicState];
            }
        });


        Button controlButton = new GameTextButton("Control");
        settingsTable.add(controlButton).pad(Constants.UI_ELEMENTS_INDENT);

        selectBox = new SelectBox<String>(AssetLoader.defaultSkin);
        selectBox.setItems(new String[] {"Buttons", "Joystick"});
        selectBox.setSelected(GameSettings.getString("control"));
        settingsTable.add(selectBox).pad(Constants.UI_ELEMENTS_INDENT);

        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);
        stack.addActor(new Image(AssetLoader.menuTexture));
        stack.addActor(backButtonTable);
        stack.addActor(settingsTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
