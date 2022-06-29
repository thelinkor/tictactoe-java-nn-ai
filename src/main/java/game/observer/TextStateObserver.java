package game.observer;

import game.Game;
import util.BoardUtils;

public class TextStateObserver implements GameRoundObserver{
    @Override
    public void notify(Game gameToObserve) {
        if(BoardUtils.evaluateBoard(gameToObserve.getCurrentBoard())!= 0) {
            System.out.println("Game Over!");
        }else if(gameToObserve.isCirclesTurn()){
            System.out.println("Circle Move");
        }else{
            System.out.println("Cross Move");
        }
        System.out.println(BoardUtils.getBoardAsNiceString(gameToObserve.getCurrentBoard()));

    }
}
