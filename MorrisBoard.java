// Bitboard representation of the Morris Board game
public class MorrisBoard {
    public long white; // Bitboard representing the positions of player 1's pieces
    public long black; // Bitboard representing the positions of player 2's pieces
    // public byte white_remaining; // Number of pieces remaining for player 1
    // public byte black_remaining; // Number of pieces remaining for player 2

    public MorrisBoard() {
        // Create a new MorrisBoard in the opening phase
        white = 0L;
        black = 0L;
        // white_remaining = 9;
        // black_remaining = 9;

    }

    public MorrisBoard(String standardInput) {
        // var result = new MorrisBoard();
        if (standardInput.length() != 24 || !standardInput.matches("[xWB]+")) {
            System.out.println("Input string is not valid ");
        } else {
            for (int i = 0; i < standardInput.length(); i++) {
                char c = standardInput.charAt(i);
                if (c == 'W') {
                    white |= (1L << i);
                }
                if (c == 'B') {
                    black |= (1L << i);
                }
            }
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

    public String convertBitBoardToStandardString(MorrisBoard board) {
        char[][] rows = new char[3][3];
        for (int i = 0; i < 24; i++) {
            long mask = 1L << i;
            if ((board.white & mask) != 0) {
                rows[i / 8][i % 8 / 3] = 'W';
            } else if ((board.black & mask) != 0) {
                rows[i / 8][i % 8 / 3] = 'B';
            } else {
                rows[i / 8][i % 8 / 3] = 'x';
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char[] row : rows) {
            sb.append(row);
        }
        return sb.toString();
    }

}