
class Main {
    public static void main(String[] args) {
        // Create a new MorrisBoard in the opening phase
        MorrisBoard board = new MorrisBoard();

        // Set a piece for player 1 at position 3
        int position = 3;
        board.white |= (1L << position); // add bits 3 

        // Print the bitboard representation of the MorrisBoard
        System.out.println("Player 1 pieces:");
        for (int i = 0; i < 24; i++) {
            System.out.print((board.white >> i) & 1);
            if ((i + 1) % 3 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();

        // Set a piece for player 2 at position 15
        position = 15;
        board.black |= (1L << position);

        // Print the bitboard representation of the MorrisBoard
        System.out.println("Player 2 pieces:");
        for (int i = 0; i < 24; i++) {
            System.out.print((board.black >> i) & 1);
            if ((i + 1) % 3 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
