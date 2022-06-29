package reinforcedai;

import game.Game;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.util.ArrayUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import util.BoardUtils;

public class NetUtil {
    public static int getMaxValueIndex(final float[] probabilityOfMoveValues, final int[] currentStates) {
        int maxAt = -1;
        float maxValue = Integer.MIN_VALUE;

        for (int i = 0; i < probabilityOfMoveValues.length; i++) {
            if(currentStates[i] == 0 &&  probabilityOfMoveValues[i] > maxValue) {
                maxAt = i;
                maxValue = probabilityOfMoveValues[i];
            }
        }

        return maxAt;
    }

    public static INDArray toINDArray(int[] gameState){
        return Nd4j.create(new int[][]{gameState});
    }



    public static void updateNetwork(int[] oldBoardState, MultiLayerNetwork network, double scoreForMove){
        INDArray stateBefore =  toINDArray(oldBoardState);
        INDArray output = network.output(stateBefore);
        INDArray updateOutput = output.putScalar(getMaxValueIndex(output.toFloatVector(), oldBoardState), scoreForMove);

        network.fit(stateBefore, updateOutput);
    }

    public static double getScore(int[] oldGameState, Game currentNextGame, int nextPlayersSymbol){
        if(BoardUtils.evaluateBoard(currentNextGame.getCurrentBoard()) != 0) {
            return 10.0;
        }

        int[] board = ArrayUtil.copy(currentNextGame.getCurrentBoard());
        for(int i  = 0; i < 9 ; i++){
            if(board[i] == 0){
                board[i] = nextPlayersSymbol;
                if(BoardUtils.evaluateBoard(board) == nextPlayersSymbol){
                    board[i] = 0;
                    return -1.0;
                }
                board[i] = 0;
            }
        }

        int currentPlayerSymbol = nextPlayersSymbol*-1;
        for(int i  = 0; i < 9 ; i++){
            if(oldGameState[i] == 0){
                board[i] = currentPlayerSymbol;
                if(BoardUtils.evaluateBoard(board) == currentPlayerSymbol){
                    board[i] = 0;
                    return -1.0;
                }
                board[i] = 0;
            }
        }


        return 0.5;
    }
}
