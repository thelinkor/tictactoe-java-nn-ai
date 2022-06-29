package reinforcedai;

import game.player.GamePlayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public class NNGamePlayer implements GamePlayer {
    private final MultiLayerNetwork network;

    public NNGamePlayer(MultiLayerNetwork network) {
        this.network = network;
    }

    @Override
    public int makeMove(int[] currentBoard) {
        return NetUtil.getMaxValueIndex(network.output(NetUtil.toINDArray(currentBoard),false).toFloatVector(),currentBoard);
    }
}
