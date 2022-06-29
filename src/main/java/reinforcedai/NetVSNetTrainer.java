package reinforcedai;

import game.Game;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import util.BoardUtils;

public class NetVSNetTrainer {

    public static double getScore(Game currentNextGame, MultiLayerNetwork currentPlayer, MultiLayerNetwork nextPlayer){
        MultiLayerNetwork theExameningNetwork = nextPlayer;
        Game game = currentNextGame.clone();
        while(!game.boardIsFilled() && BoardUtils.evaluateBoard(game.getCurrentBoard())== 0){
            int move = NetUtil.getMaxValueIndex(theExameningNetwork.output(NetUtil.toINDArray(game.getCurrentBoard()),false).toFloatVector(), game.getCurrentBoard());
            game.makeMoveInPosition(move);
            theExameningNetwork = theExameningNetwork == currentPlayer ? nextPlayer : currentPlayer;
        }
        if(BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.EMPTY_SQUARE) {
            return 0;
        }else if((currentNextGame.isCirclesTurn() && BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.CROSS_MOVE)||
                (!currentNextGame.isCirclesTurn() && BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.CIRCLE_MOVE)){
            return 1;
        }else{
            return -1;
        }
    }
}
