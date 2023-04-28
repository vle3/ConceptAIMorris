import java.util.ArrayList;

public class MoveGenerator {
    // MorrisBoard board;
    public MoveGenerator() {

    };

    public ArrayList<MorrisBoard> GenerateAdd(MorrisBoard board) {
        var result = new ArrayList<MorrisBoard>();

        System.out.println();
        // System.out.println("test convert " + (int)board.white);

        // var emptyWhite = checkEmpty(board.white);
        // System.out.println("Empty spaces White on bitboard: " +
        // Long.toBinaryString(emptyWhite));
        // var emptyBlack = checkEmpty(board.black);
        // System.out.println("Empty spaces Black on bitboard: " +
        // Long.toBinaryString(emptyBlack));

        for (int i = 0; i < 24; i++) {
            var b = copyFrom(board);
            if (isSquareEmpty(b.white, i) && isSquareEmpty(b.black, i)) {

                b.white |= (1L << i);
                if(checkCloseMillWhite(i, board)) {
                    //generateRemove(b, L);
                }
                // System.out.println("Player 1 Can go: ");
                // for (int j = 0; j < 24; j++) {
                // System.out.print((b.white >> j) & 1);
                // if ((j + 1) % 3 == 0) {
                // System.out.print(" ");
                // }
                // }
                // System.out.println();
                result.add(b);
                // break;
            }

        }

        // for (var r : result) {
        // System.out.println("Player 1 Can go: ");
        // for (int j = 0; j < 24; j++) {
        // System.out.print((r.white >> j) & 1);
        // if ((j + 1) % 3 == 0) {
        // System.out.print(" ");
        // }
        // }
        // System.out.println();
        // }
        // System.out.println("Possible move: " + result.size());

        // System.out.println("Test: long to number: (0)" +
        // Long.toString(result.get(0).white));
        // System.out.println("Test: long to number: (1)" +
        // Long.toString(result.get(1).white));
        // System.out.println("Test: long to number: (3)" +
        // Long.toString(result.get(3).white));

        return result;
    }

    private long checkEmpty(long bitboard) {
        long occupied = bitboard;
        long empty = ~occupied;
        return empty;
    }

    private boolean isSquareEmpty(long bitboard, int square) {
        long mask = 1L << square;
        return (bitboard & mask) == 0L;
    }

    private MorrisBoard copyFrom(MorrisBoard board) {
        var result = new MorrisBoard();
        result.white = board.white;
        result.black = board.black;
        return result;
    }

    private boolean checkCloseMillWhite(int place, MorrisBoard board) {
        var tempBoard = copyFrom(board);
        if (isSquareEmpty(tempBoard.white, place) && isSquareEmpty(tempBoard.black, place)) {
            tempBoard.white |= (1L << place);
        }
        switch(Integer.parseInt(Long.toString(tempBoard.white))){
            case 7:
            case 56:
            case 73:
            case 146: 
            case 292:
            case 448:
            case 3584:
            case 28672:
            case 34880:
            case 135424: 
            case 229376: 
            case 263176:
            case 1056800:
            case 1835008:
            case 2097665:
            case 4784128:
            case 8404996:
            case 14680064:
                return true;
            default:
                return false;
        }
    }

    private boolean checkCloseMillBlack(int place, MorrisBoard board) {
        var tempBoard = copyFrom(board);
        if (isSquareEmpty(tempBoard.white, place) && isSquareEmpty(tempBoard.black, place)) {
            tempBoard.black |= (1L << place);
        }
        switch(Integer.parseInt(Long.toString(tempBoard.black))){
            case 7:
            case 56:
            case 73:
            case 146: 
            case 292:
            case 448:
            case 3584:
            case 28672:
            case 34880:
            case 135424: 
            case 229376: 
            case 263176:
            case 1056800:
            case 1835008:
            case 2097665:
            case 4784128:
            case 8404996:
            case 14680064:
                return true;
            default:
                return false;
        }
    }

    private ArrayList<MorrisBoard> GenerateRemove(MorrisBoard board, ArrayList<MorrisBoard> boardList){
        var result = new ArrayList<MorrisBoard>();
        for(int i = 0; i < 24; i++){
            if(!isSquareEmpty(board.black, i)){
                if(!checkCloseMillBlack(i, board)){
                    var b = copyFrom(board);
                    long mask = ~(1L << i);
                    b.black &= mask;
                    result.add(b);
                }
            }
        }
        if(result.size() == 0){
            for(int i = 0 ; i < 24; i++){
                if(!isSquareEmpty(board.black, i))
                {
                    var b = copyFrom(board);
                    long mask = ~(1L << i);
                    b.black &= mask;
                    result.add(b);
                }
            }
        }
        return result;
    }
}
