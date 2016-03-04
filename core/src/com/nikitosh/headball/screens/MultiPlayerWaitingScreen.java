package com.nikitosh.headball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MultiPlayerWaitingScreen extends StageAbstractScreen {
    private static final int PORT = 12345;
    private static final String SERVER_ADDRESS = "";

    private static final String LOG_TAG = "MultiPlayerWaitingScreen";
    private static final String CONNECTION_ERROR_MESSAGE = "Connection failed!";

    public MultiPlayerWaitingScreen() {
        stack.addActor(new Image(AssetLoader.getDarkBackgroundTexture()));
        stack.addActor(new Label("Wait for a while!", AssetLoader.getGameSkin()));
    }

    @Override
    public void show() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress ipAddress = InetAddress.getByName(SERVER_ADDRESS);
                    Gdx.app.log(LOG_TAG, "Any of you heard of a socket with IP address "
                            + SERVER_ADDRESS + " and port " + PORT + "?");
                    Socket socket = new Socket(ipAddress, PORT);
                    Gdx.app.log(LOG_TAG, "Yes! I just got hold of the program.");
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    byte s = in.readByte();
                    Gdx.app.log(LOG_TAG, Byte.toString(s));
                    int playerNumber = 0;
                    if (s == 0) {
                        playerNumber = 0;
                    } else {
                        playerNumber = 1;
                    }
                    ScreenManager.getInstance().disposeCurrentScreen();
                    ScreenManager.getInstance().setScreen(new MultiPlayerScreen(
                            new MatchInfo(new Team("", ""), new Team("", ""), false, false),
                            playerNumber, in, out));
                } catch (Exception e) {
                    Gdx.app.error(LOG_TAG, CONNECTION_ERROR_MESSAGE, e);
                }
            }
        });
    }
}