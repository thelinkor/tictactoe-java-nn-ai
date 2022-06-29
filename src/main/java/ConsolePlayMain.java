import game.player.ConsolePlayer;
import game.Game;
import game.GameBot;
import util.BoardUtils;

public class ConsolePlayMain {

    public static void main(String[] args){
        GameBot gameBot = new GameBot(new ConsolePlayer(), new ConsolePlayer()) ;
        gameBot.runGameRound();
        System.out.println("\n Game over!");
        System.out.println(BoardUtils.getBoardAsNiceString(gameBot.getGame().getCurrentBoard()));
        System.out.println(BoardUtils.evaluateBoard(gameBot.getGame().getCurrentBoard()) == Game.EMPTY_SQUARE ? "Game is a tie" :
                            BoardUtils.evaluateBoard(gameBot.getGame().getCurrentBoard()) == Game.CIRCLE_MOVE ? "Circle wins" :
                            "Cross Wins");
    }

}
