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

    public MorrisBoard convertInput(String standardInput) {
        var result = new MorrisBoard();
        if(standardInput.length() != 24 || !standardInput.matches("[xWB]+")){
            System.out.println("Input string is not valid ");
        }
        return result;
    }

}