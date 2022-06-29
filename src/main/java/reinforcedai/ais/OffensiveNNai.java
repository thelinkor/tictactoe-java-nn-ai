package reinforcedai.ais;

import game.Game;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import reinforcedai.NetConfig;
import reinforcedai.NetUtil;
import util.BoardUtils;

public class OffensiveNNai  implements NNai{
    private final MultiLayerNetwork net;

    public OffensiveNNai() {
        this.net = NetConfig.createNet();
    }

    public OffensiveNNai(MultiLayerNetwork net) {
        this.net = net;
    }

    @Override
    public String getName() {
        return "Offensive";
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
            return 0;
        }else if((currentNextGame.isCirclesTurn() && BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.CROSS_MOVE)||
                (!currentNextGame.isCirclesTurn() && BoardUtils.evaluateBoard(game.getCurrentBoard())== Game.CIRCLE_MOVE)){
            return 1;
        }else{
            return 0;
        }
    }
}
