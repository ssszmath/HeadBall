package com.nikitosh.headball.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Wall extends Actor {
    protected Body body;

    public Body getBody() {
        return body;
    }
}
