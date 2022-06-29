package game;

import java.util.Arrays;

public class Game {
    public static final int CROSS_MOVE = 1;
    public static final int CIRCLE_MOVE = -1;
    public static final int EMPTY_SQUARE = 0;

    private final int[] currentBoard;
    private boolean isCirclesTurn;

    public Game(){
        this(new int[9], false);
    }

    public Game(int[] currentBoard, boolean isCirclesTurn) {
        this.currentBoard = currentBoard;
        this.isCirclesTurn = isCirclesTurn;
    }

    public void reset(){
        Arrays.fill(currentBoard, EMPTY_SQUARE);
        isCirclesTurn = false;
    }

    public void makeMoveInPosition(int x, int y){
        makeMoveInPosition(x + y*3);
    }

    public void makeMoveInPosition(int pos){
        if(currentBoard[pos] == EMPTY_SQUARE) {
            currentBoard[pos] = isCirclesTurn ? CIRCLE_MOVE : CROSS_MOVE;
            isCirclesTurn = !isCirclesTurn;
        }
    }

    public int[] getCurrentBoard() {
        return currentBoard;
    }

    public boolean isCirclesTurn() {
        return isCirclesTurn;
    }

    public boolean boardIsFilled(){
        for(int num : currentBoard){
            if(num == EMPTY_SQUARE)
                return false;
        }
        return true;
    }

    @Override
    public Game clone() {
        return new Game(Arrays.copyOf(currentBoard, currentBoard.length),  isCirclesTurn);
    }
}