package game.player;

import org.nd4j.common.util.ArrayUtil;
import util.BoardUtils;

import java.util.Scanner;

public class ConsolePlayer implements GamePlayer{
    public static final Scanner input = new Scanner(System.in);

    @Override
    public int makeMove(int[] currentBoard) {
        if(BoardUtils.isCrossMove(currentBoard)){
            System.out.println("Cross Turn");
        }else {
            System.out.println("Circles turn");
        }
        System.out.println(BoardUtils.getBoardAsNiceString(currentBoard));
        int inputFromPlayer;
        do{
            inputFromPlayer = input.nextInt();
        }while(!ArrayUtil.contains(BoardUtils.getEmptyBoardSquares(currentBoard), inputFromPlayer));
        return inputFromPlayer;
    }
}
