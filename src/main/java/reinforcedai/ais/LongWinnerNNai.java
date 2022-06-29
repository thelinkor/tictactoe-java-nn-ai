package reinforcedai.ais;

import game.Game;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import reinforcedai.NetConfig;
import reinforcedai.NetUtil;
import util.BoardUtils;

public class LongWinnerNNai implements NNai{

    private final MultiLayerNetwork net;

    public LongWinnerNNai() {
        this.net = NetConfig.createNet();
    }

    public LongWinnerNNai(MultiLayerNetwork net) {
        this.net = net;
    }

    @Override
    public String getName() {
        return "LongWinnerAi";
    }

    @Override
    public MultiLayerNetwork getNeuralNet() {
        return net;
    }

    @Override
    public double getScore(Game currentNextGame, MultiLayerNetwork currentPlayer, MultiLayerNetwork nextPlayer) {
        MultiLayerNetwork theExameningNetwork = nextPlayer;
        Game game = currentNextGame.clone();
        while(!game.boardIsFilled() && BoardUtils.evaluateBoard(game.getCurrentBoard())== 0){
            int move = NetUtil.getMaxValueIndex(theExameningNetwork.output(NetUtil.toINDArray(game.getCurrentBoard()),false).toFloatVector(), game.getCurrentBoard());
            game.makeMoveInPosition(move);
            theExameningNetwork = theExameningNetwork == currentPlayer ? nextPlayer : currentPlayer;
        }
        if(BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.EMPTY_SQUARE) {
            return 1;
        }else if((currentNextGame.isCirclesTurn() && BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.CROSS_MOVE)||
                (!currentNextGame.isCirclesTurn() && BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.CIRCLE_MOVE)){
            return 10;
        }else{
            return 0;
        }
    }
}
