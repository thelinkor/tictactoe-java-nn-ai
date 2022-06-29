package reinforcedai.ais;

import game.Game;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public interface NNai {
    String getName();

    MultiLayerNetwork getNeuralNet();

    double getScore(Game game, MultiLayerNetwork currentPlayer, MultiLayerNetwork nextPlayer);
}
