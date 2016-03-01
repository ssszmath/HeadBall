package com.nikitosh.headball.players;

import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.actors.Ball;
import com.nikitosh.headball.actors.Footballer;

public class AIPlayer implements Player {
    private GameWorld gameWorld;
    private int footballerNumber;

    private static final float HIT_RADIUS = 40f * Constants.WORLD_TO_BOX;
    private static final float JUMP_RADIUS = 55f * Constants.WORLD_TO_BOX;
    private static final float DEFENCE_POSITION = Constants.VIRTUAL_WIDTH / 8 * Constants.WORLD_TO_BOX;
    private static final float VISION_RADIUS = Constants.VIRTUAL_WIDTH / 8 * Constants.WORLD_TO_BOX;
    private static final float EPS = 3f * Constants.WORLD_TO_BOX;
    private static final float DEFENCE_ZONE = Constants.VIRTUAL_WIDTH / 4 * Constants.WORLD_TO_BOX;
    
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

        Move move = new Move();
        if (footballerNumber == 1) {
            ballPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX - ball.getPosition().x;
            myPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX
                    - footballers[footballerNumber].getPosition().x;
        } else {
            ballPositionX = ball.getPosition().x;
            myPositionX = footballers[footballerNumber].getPosition().x;
        }

        if ((ballPositionX - myPositionX > VISION_RADIUS
                && ballPositionX > (Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX) / 2  - EPS)
                && myPositionX < Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX / 2) {
            if (myPositionX > DEFENCE_POSITION) {
                move.setState(Constants.LEFT, true);
            } else if (myPositionX < DEFENCE_POSITION - EPS) {
                move.setState(Constants.RIGHT, true);
            }
        } else if (ballPositionY < myPositionY - footballers[footballerNumber].getRadius()) {
            move.setState(Constants.LEFT, true);
        } else {
            if (ballPositionX < myPositionX - footballers[footballerNumber].getRadius()) {
                if (myPositionX - ballPositionX < JUMP_RADIUS / 2f && myPositionY > ballPositionY) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                } else {
                    move.setState(Constants.LEFT, true);
                }
            } else {
                if (ballPositionX - myPositionX > footballers[footballerNumber].getRadius() / 4
                        && ballPositionX - myPositionX < HIT_RADIUS) {
                    move.setState(Constants.HIT, true);
                    move.setState(Constants.RIGHT, true);
                } else if ((Math.abs(ballPositionX - myPositionX) < JUMP_RADIUS / 3
                        && ballPositionY > myPositionY)
                        || (Math.abs(ballPositionX - myPositionX) < VISION_RADIUS
                        && ballPositionY > myPositionY + footballers[footballerNumber].getRadius() * 2
                        && myPositionX > DEFENCE_POSITION && myPositionX < DEFENCE_ZONE)) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                } else if (Math.abs(ballPositionX - myPositionX) < VISION_RADIUS && ballPositionY > myPositionY) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.RIGHT, true);
                } else {
                    move.setState(Constants.RIGHT, true);
                }
            }
        }

        if (footballerNumber == 1) {
            boolean tmp = move.getState(Constants.LEFT);
            move.setState(Constants.LEFT, move.getState(Constants.RIGHT));
            move.setState(Constants.RIGHT, tmp);
        }
        return move;
    }
}
