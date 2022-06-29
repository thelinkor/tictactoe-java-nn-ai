package reinforcedai;

import game.Game;
import game.GameBot;
import game.player.GamePlayer;
import game.player.SequencePlayer;
import reinforcedai.ais.NNai;
import util.BoardUtils;

public class AiTester {
    public static final int[][] SEQUENCES_TO_TEST_AGAINST = {
            {4,8,0,2,6,7,1,3,5},
            {0,1,2,3,4,5,6,7,8},
            {4,2,6,0,8,5,3,1,7},
            {4,1,7,6,8,0,3,5,2},
            {2,5,8,0,1,4,3,6,7},
            {1,4,0,2,6,8,7,5,3}
    };
    public static void testCrossAi(NNai crossAItoTest){
        GamePlayer crossPlayer  = new NNGamePlayer(crossAItoTest.getNeuralNet());
        System.out.printf("%40s","Cross player "+  crossAItoTest.getName() + ": ");
        int winCountOrTieCount = 0;
        for(int[] sequence : SEQUENCES_TO_TEST_AGAINST){
            int winner = getWinner(crossPlayer,new SequencePlayer(sequence));
            if(winner == Game.EMPTY_SQUARE ){
                System.out.print("_ ");
                winCountOrTieCount++;
            }else if(winner == Game.CIRCLE_MOVE){
                System.out.print("L ");
            }else{
                System.out.print("W ");
                winCountOrTieCount++;
            }

        }
        System.out.printf("%s%1.2f","Rate: " , (double)winCountOrTieCount/ SEQUENCES_TO_TEST_AGAINST.length);

    }

    public static void testCircleAi(NNai circleAItoTest){
        GamePlayer circlePlayer  = new NNGamePlayer(circleAItoTest.getNeuralNet());
        System.out.printf("%40s","Circle player "+  circleAItoTest.getName() + ": ");
        int winCountOrTieCount = 0;
        for(int[] sequence : SEQUENCES_TO_TEST_AGAINST){
            int winner = getWinner(new SequencePlayer(sequence), circlePlayer);
            if(winner == Game.EMPTY_SQUARE ){
                System.out.print("_ ");
                winCountOrTieCount++;
            }else if(winner == Game.CIRCLE_MOVE){
                System.out.print("W ");
                winCountOrTieCount++;
            }else{
                System.out.print("L ");
            }
        }
        System.out.printf("%s%1.2f","Rate: " , (double)winCountOrTieCount/ SEQUENCES_TO_TEST_AGAINST.length);
    }

    public static int getWinner(GamePlayer cross, GamePlayer circle){
        GameBot gameBot = new GameBot(cross, circle);
        gameBot.runGameRound();
        return BoardUtils.evaluateBoard(gameBot.getGame().getCurrentBoard());
    }
}
