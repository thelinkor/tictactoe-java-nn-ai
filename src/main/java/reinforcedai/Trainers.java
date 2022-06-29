package reinforcedai;

import game.Game;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.util.ArrayUtil;
import reinforcedai.ais.NNai;
import util.BoardUtils;

import java.util.Random;

public class Trainers {
    public static long NUMBER_OF_GAME_ROUNDS = 50_000;

    public static void trainMultiLayerNetworks(MultiLayerNetwork crossNetwork, MultiLayerNetwork circleNetwork){
        Game game = new Game();
        MultiLayerNetwork currentPlayer;
        for(int i = 0 ; i < NUMBER_OF_GAME_ROUNDS ; i++){
            game.reset();
            int tryCount = 0;
            while(!game.boardIsFilled() && BoardUtils.evaluateBoard(game.getCurrentBoard()) == 0 && tryCount< 9){
                tryCount++;

                int[] oldBoardState = ArrayUtil.copy(game.getCurrentBoard());
                boolean moveIsCircle = game.isCirclesTurn();
                if(moveIsCircle){
                    currentPlayer = circleNetwork;
                }else{
                    currentPlayer = crossNetwork;
                }

                int nextMove = determineMove(currentPlayer, oldBoardState);
                game.makeMoveInPosition(nextMove);

                double scoreForMove = NetUtil.getScore(oldBoardState, game,  game.isCirclesTurn() ? Game.CIRCLE_MOVE : Game.CROSS_MOVE);

                NetUtil.updateNetwork(oldBoardState, currentPlayer, scoreForMove);
            }
        }
    }

    public static void trainMultiLayerVersusNetworks(MultiLayerNetwork crossNetwork, MultiLayerNetwork circleNetwork){
        Game game = new Game();
        MultiLayerNetwork currentPlayer;
        for(int i = 0 ; i < NUMBER_OF_GAME_ROUNDS ; i++){
            game.reset();
            int tryCount = 0;
            while(!game.boardIsFilled() && BoardUtils.evaluateBoard(game.getCurrentBoard()) == 0 && tryCount< 9){
                tryCount++;

                int[] oldBoardState = ArrayUtil.copy(game.getCurrentBoard());
                boolean moveIsCircle = game.isCirclesTurn();
                if(moveIsCircle){
                    currentPlayer = circleNetwork;
                }else{
                    currentPlayer = crossNetwork;
                }

                int nextMove = determineMove(currentPlayer, oldBoardState);
                game.makeMoveInPosition(nextMove);

                double scoreForMove = NetVSNetTrainer.getScore(game,
                        game.isCirclesTurn() ? crossNetwork : circleNetwork ,
                        game.isCirclesTurn() ? circleNetwork : crossNetwork);

                NetUtil.updateNetwork(oldBoardState, currentPlayer, scoreForMove);
            }
        }
    }

    public static final long NN_CHOICE_SEED =  513L;
    public static void trainMultiLayerVersusNetworks(NNai[] crossNetworks, NNai[] circleNetworks){
        trainMultiLayerVersusNetworks(crossNetworks, circleNetworks, NUMBER_OF_GAME_ROUNDS);
    }

    public static void trainMultiLayerVersusNetworks(NNai[] crossNetworks, NNai[] circleNetworks, long numberOFGameRounds){
        Game game = new Game();
        NNai currentPlayer;
        NNai nextPlayer;
        Random networkChoiceRandom =  new Random(NN_CHOICE_SEED);
        int crossIndex;
        int circleIndex;
        for(int i = 0 ; i < numberOFGameRounds ; i++){
            game.reset();
            int tryCount = 0;
            crossIndex = networkChoiceRandom.nextInt(crossNetworks.length);
            circleIndex = networkChoiceRandom.nextInt(circleNetworks.length);
            while(!game.boardIsFilled() && BoardUtils.evaluateBoard(game.getCurrentBoard()) == 0 && tryCount< 9){
                tryCount++;

                int[] oldBoardState = ArrayUtil.copy(game.getCurrentBoard());
                boolean moveIsCircle = game.isCirclesTurn();
                if(moveIsCircle){
                    currentPlayer = circleNetworks[circleIndex];
                    nextPlayer = crossNetworks[circleIndex];
                }else{
                    currentPlayer = crossNetworks[crossIndex];
                    nextPlayer = circleNetworks[circleIndex];
                }

                int nextMove = determineMove(currentPlayer.getNeuralNet(), oldBoardState);
                game.makeMoveInPosition(nextMove);

                double scoreForMove = currentPlayer.getScore(game,currentPlayer.getNeuralNet(),nextPlayer.getNeuralNet());

                NetUtil.updateNetwork(oldBoardState, currentPlayer.getNeuralNet(), scoreForMove);
            }
        }
    }



    public static Random epsilonRandom = new Random(NetConfig.SEED+1);
    public static double epsilon = 1.0;
    public static double epsilonMovement  = 0.001;
    public static int determineMove(MultiLayerNetwork currentPlayer, int[] oldBoardState){
        if(epsilon > epsilonRandom.nextDouble()){
            epsilon -= epsilonMovement;
            int[] emptySquares = BoardUtils.getEmptyBoardSquares(oldBoardState);
            return emptySquares[epsilonRandom.nextInt(emptySquares.length)];
        }
        return NetUtil.getMaxValueIndex(currentPlayer.output(NetUtil.toINDArray(oldBoardState),false).toFloatVector(), oldBoardState);
    }

}
