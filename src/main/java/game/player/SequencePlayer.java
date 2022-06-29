package game.player;

import org.nd4j.common.util.ArrayUtil;
import util.BoardUtils;

public class SequencePlayer implements GamePlayer{
    private final int[] sequence;

    public SequencePlayer(int[] sequence) {
        this.sequence = sequence;
    }

    @Override
    public int makeMove(int[] currentBoard) {
        for(int numInSequence : sequence){
            if(ArrayUtil.contains(BoardUtils.getEmptyBoardSquares(currentBoard), numInSequence))
                return numInSequence;
        }
        throw new IllegalStateException("Trying to make move on empty board");
    }
}
