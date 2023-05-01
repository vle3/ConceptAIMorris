import java.util.ArrayList;

public class MoveGenerator {
    // MorrisBoard board;
    public MoveGenerator() {

    };

    public ArrayList<MorrisBoard> GenerateMoveOpening(MorrisBoard board) {
        return GenerateAdd(board);
    }

    public ArrayList<MorrisBoard> GenerateAdd(MorrisBoard board) {
        var result = new ArrayList<MorrisBoard>();
        System.out.println();

        for (int i = 0; i < 24; i++) {
            var b = copyFrom(board);
            if (isSquareEmpty(b.white, i) && isSquareEmpty(b.black, i)) {
                // showBoard(b);
                b.white |= (1L << i); // List of possible white moves without close mill removing //b[location] = W;
                if (checkCloseMill(i, b, true)) {
                    GenerateRemove(board, result);
                }
                result.add(b);
            }

        }

        return result;
    }

    private boolean isSquareEmpty(long bitboard, int square) {
        long mask = 1L << square;
        return (bitboard & mask) == 0L;
    }

    public static MorrisBoard copyFrom(MorrisBoard board) {
        var result = new MorrisBoard();
        result.white = board.white;
        result.black = board.black;
        return result;
    }

    private boolean checkCloseMill(int place, MorrisBoard board, boolean maximizingPlayer) {
        var tempBoard = copyFrom(board);
        if (isSquareEmpty(tempBoard.white, place) && isSquareEmpty(tempBoard.black, place) && maximizingPlayer == true) {
            tempBoard.white |= (1L << place);
        } else if (isSquareEmpty(tempBoard.white, place) && isSquareEmpty(tempBoard.black, place)
                && maximizingPlayer == false) {
            tempBoard.black |= (1L << place);
        }
        switch (Integer.parseInt(Long.toString(tempBoard.white))) {
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

    private void GenerateRemove(MorrisBoard board, ArrayList<MorrisBoard> boardList) {
        for (int i = 0; i < 24; i++) {
            if (!isSquareEmpty(board.black, i)) { // if board[location] == black
                if (!checkCloseMill(i, board, true) && !checkCloseMill(i, board, false)) {
                    // if not closeMill for both black and white at the location
                    var b = copyFrom(board);
                    var mask = ~(1L << i); // create a mask with a 0 in the bitIndex position and 1s everywhere else

                    // set that location on the board to empty
                    b.white &= mask;
                    b.black &= mask;

                    boardList.add(b);
                }
            }
        }

        if (boardList.size() == 0) {
            for (int i = 0; i < 24; i++) {
                if (!isSquareEmpty(board.black, i)) {
                    var b = copyFrom(board);

                    var mask = ~(1L << i); // create a mask with a 0 in the bitIndex position and 1s everywhere else

                    // set that location on the board to empty
                    b.white &= mask;
                    b.black &= mask;

                    boardList.add(b);

                }
            }
        }
    }

    public ArrayList<MorrisBoard> GenerateMovesMidgameEndgame(MorrisBoard board){
        if(Long.bitCount(board.white) == 3){
            return GenerateHopping(board)            ;
        }
        else{
            return GenerateMove(board);
        }
    }

    public ArrayList<MorrisBoard> GenerateHopping(MorrisBoard board){
        var result = new ArrayList<MorrisBoard>();
        for(int i = 0; i < 24; i++){
            if(!isSquareEmpty(board.white, i)){
                for(int j = 0; j < 24; j++){
                    var b = copyFrom(board);

                    //b[alpha] = empty;
                    var mask = ~(1L << i); // create a mask with a 0 in the bitIndex position and 1s everywhere else
                        // set that location on the board to empty
                        b.white &= mask;
                        b.black &= mask;
                    
                    //b[beta] = W;
                    if(checkCloseMill(j, b, true)){
                        GenerateRemove(b, result);
                    }
                    else{
                        result.add(b);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<MorrisBoard> GenerateMove(MorrisBoard board) {
        var result = new ArrayList<MorrisBoard>();
        for (int i = 0; i < 24; i++) {
            if (isSquareEmpty(board.white, i)) {
                // list of neighbor
                var n = getNeighbors(i); // list of neighbor of current location/i
                for (int j : n) {
                    if (isSquareEmpty(board.white, j) && isSquareEmpty(board.black, j)) {
                        var b = copyFrom(board);
                        // b[location] = empty
                        var mask = ~(1L << i); // create a mask with a 0 in the bitIndex position and 1s everywhere else
                        // set that location on the board to empty
                        b.white &= mask;
                        b.black &= mask;

                        // b[j] = W set W to position j
                        board.white |= 1L << j;

                        if (checkCloseMill(j, b, true)) {
                            GenerateRemove(b, result);
                        } else {
                            result.add(b);
                        }
                    }
                }
            }
        }
        return result;
    }

    // list of neighbor
    public int[] getNeighbors(int j) {
        switch (j) {
            case 0:
                return new int[] { 1, 3, 9 };
            case 1:
                return new int[] { 0, 2, 4 };
            case 2:
                return new int[] { 1, 5, 14 };
            case 3:
                return new int[] { 0, 4, 6, 10 };
            case 4:
                return new int[] { 1, 3, 5, 7 };
            case 5:
                return new int[] { 2, 4, 8, 13 };
            case 6:
                return new int[] { 3, 7, 11 };
            case 7:
                return new int[] { 4, 6, 8 };
            case 8:
                return new int[] { 5, 7, 12 };
            case 9:
                return new int[] { 0, 10, 21 };
            case 10:
                return new int[] { 3, 9, 11, 18 };
            case 11:
                return new int[] { 6, 10, 15 };
            case 12:
                return new int[] { 8, 13, 17 };
            case 13:
                return new int[] { 5, 12, 14, 20 };
            case 14:
                return new int[] { 2, 13, 23 };
            case 15:
                return new int[] { 11, 16 };
            case 16:
                return new int[] { 15, 17, 19 };
            case 17:
                return new int[] { 12, 16 };
            case 18:
                return new int[] { 10, 19 };
            case 19:
                return new int[] { 16, 18, 20, 22 };
            case 20:
                return new int[] { 13, 19 };
            case 21:
                return new int[] { 0, 22 };
            case 22:
                return new int[] { 19, 21, 23 };
            case 23:
                return new int[] { 14, 22 };
            default:
                return new int[] {};
        }

    }

    public static void showBoard(MorrisBoard board) {
        // Print the bitboard representation of the MorrisBoard
        System.out.println("Player 1 pieces:");
        for (int i = 0; i < 24; i++) {
            System.out.print((board.white >> i) & 1);
            if ((i + 1) % 3 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();

        // Print the bitboard representation of the MorrisBoard
        System.out.println("Player 2 pieces:");
        for (int i = 0; i < 24; i++) {
            System.out.print((board.black >> i) & 1);
            if ((i + 1) % 3 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
        // return result;
    }
}
