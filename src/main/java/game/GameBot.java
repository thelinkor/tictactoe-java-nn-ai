package game;

import game.observer.GameRoundObserver;
import game.observer.ObserveNothing;
import game.player.GamePlayer;
import util.BoardUtils;

public class GameBot {
    private final Game game;
    private final GamePlayer crossPlayer;
    private final GamePlayer circlePlayer;
    private final GameRoundObserver roundObserver;

    public GameBot(GamePlayer crossPlayer, GamePlayer circlePlayer) {
        this(crossPlayer, circlePlayer, new ObserveNothing());
    }

    public GameBot(GamePlayer crossPlayer, GamePlayer circlePlayer, GameRoundObserver roundObserver) {
        this.game = new Game();
        this.crossPlayer = crossPlayer;
        this.circlePlayer = circlePlayer;
        this.roundObserver = roundObserver;
    }

    public void runGameRound(){
        do{
            doSingleMove();
            roundObserver.notify(game);
        }while(BoardUtils.evaluateBoard(game.getCurrentBoard()) == Game.EMPTY_SQUARE && !game.boardIsFilled());
    }

    public void doSingleMove(){
        if(game.isCirclesTurn()) {
            game.makeMoveInPosition(circlePlayer.makeMove(game.getCurrentBoard()));
        }else{
            game.makeMoveInPosition(crossPlayer.makeMove(game.getCurrentBoard()));
        }
    }

    public Game getGame() {
        return game;
    }
}
