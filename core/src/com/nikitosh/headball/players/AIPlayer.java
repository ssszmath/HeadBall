package com.nikitosh.headball.players;

import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.actors.Ball;
import com.nikitosh.headball.actors.Footballer;

public class AIPlayer implements Player {
    private GameWorld gameWorld;
    private int footballerNumber;

    private static final float HIT_RADIUS = 15f * Constants.WORLD_TO_BOX;
    private static final float JUMP_RADIUS = 45f * Constants.WORLD_TO_BOX;


    public AIPlayer(GameWorld gameWorld, int footballerNumber) {
        this.gameWorld = gameWorld;
        this.footballerNumber = footballerNumber;
    }

    @Override
    public Move getMove() {

        Footballer[] footballers = gameWorld.getFootballers();
        Ball ball = gameWorld.getBall();

        float ballPositionX;
        float ballPositionY = ball.getPosition().y;
        float myPositionX;
        float myPositionY = footballers[footballerNumber].getPosition().y;

        Move move = new Move(false, false, false, false);
        if (footballerNumber == 1) {
            ballPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX - ball.getPosition().x;
            myPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX - footballers[footballerNumber].getPosition().x;
        }
        else {
            ballPositionX = ball.getPosition().x;
            myPositionX = footballers[footballerNumber].getPosition().x;
        }

        if (ballPositionY < myPositionY - Footballer.getFootballerRadius()) {
            move.setLeft(true);
        }
        else {
            if (ballPositionX < myPositionX) {
                if (myPositionX - ballPositionX < JUMP_RADIUS && myPositionY > ballPositionY) {
                    move.setJump(true);
                    move.setLeft(true);
                }
                else {
                    move.setLeft(true);
                }
            }
            else {
                if (ballPositionX - myPositionX < HIT_RADIUS) {
                    move.setHit(true);
                    move.setRight(true);
                }
                else if (ballPositionX - myPositionX < JUMP_RADIUS && ballPositionY > myPositionY) {
                    move.setLeft(true);
                }
                else {
                    move.setRight(true);
                }
            }
        }
        if (footballerNumber == 1) {
            boolean tmp = move.isLeft();
            move.setLeft(move.isRight());
            move.setRight(tmp);
        }
        return move;
    }
}