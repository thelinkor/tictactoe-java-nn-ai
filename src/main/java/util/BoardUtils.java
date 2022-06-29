package util;

import game.Game;

import java.util.Arrays;

public class BoardUtils {
    private static final int[][] LINES = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    private BoardUtils(){
        //
    }

    public static String getBoardAsNiceString(int[] board){
        return String.format("%s %s %s \n%s %s %s \n%s %s %s"
                , intToSym(board[0])
                , intToSym(board[1])
                , intToSym(board[2])

                , intToSym(board[3])
                , intToSym(board[4])
                , intToSym(board[5])

                , intToSym(board[6])
                , intToSym(board[7])
                , intToSym(board[8]));
    }

    private static String intToSym(int num){
        return num == Game.EMPTY_SQUARE ? "_" :
               num == Game.CIRCLE_MOVE ? "0" :
               num == Game.CROSS_MOVE ? "X" :
        "ERROR";
    }

    public static boolean isCrossMove(int[] board){
        return Arrays.stream(board).filter(x -> x == Game.CROSS_MOVE).sum() == Arrays.stream(board).filter(x -> x == Game.CIRCLE_MOVE).sum();
    }

    public static int evaluateBoard(int[] currentBoard){
        for(int[] line : LINES){
            if(currentBoard[line[0]] != Game.EMPTY_SQUARE
                    && currentBoard[line[0]] == currentBoard[line[1]]
                    && currentBoard[line[0]] == currentBoard[line[2]])
                return currentBoard[line[0]];
        }
        return Game.EMPTY_SQUARE;
    }

    public static int[] getEmptyBoardSquares(int[] board){
        int count = 0;
        for(int i = 0 ; i < board.length ; i++){
            if(board[i] == 0)
                count++;
        }
        int[] emptySquares = new int[count];
        count = 0;
        for(int i = 0 ; i < board.length ; i++){
            if(board[i] == 0){
                emptySquares[count] = i;
                count++;
            }
        }
        return emptySquares;
    }
}
