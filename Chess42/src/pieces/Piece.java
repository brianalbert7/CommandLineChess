package pieces;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * This interface lays out the basic commands that a chess piece should be able to do: move.
 * There is an additional method that allows each piece to check if a command is legal for its type.
 *
 */
public interface Piece {
	/**
	 * This method gives a piece a command to move to somewhere on the board.  It will run validateCommand to see whether or not the move was legal.
	 * If there is a piece sitting at the end point, it will be checked whether or not this piece can be legally captured.
	 * If so, the piece is captured and the calling piece will be placed there.
	 * @param board The board field within a Board object.
	 * @param command The string input that signifies the command to move a piece in a certain square to another square.
	 * @see #validateCommand(Piece[][], String)
	 */
	public void move(Piece[][] board, String command);
	
	
	/**
	 * Helper method do see if a move is valid given the current board state.
	 * @param board The board field within a Board object.
	 * @param command The string input that signifies the command to move a piece in a certain square to another square.
	 * @return true or false depending on if the move is valid or not.
	 */
	public boolean validateCommand(Piece[][] board, String command);
	
	
	/**
	 * Getter to get the side of a piece, either 'b' or 'w'.
	 * @return 'b' or 'w' depending on which side the piece belongs to
	 */
	public char getColor();

    /**
	 * Getter to get a boolean indicating if it is the pieces first move.
	 * @return true if it is the pieces first move and false if the piece has already moved.
	 */
	public boolean getFirstMove();
	
	
	/**
	 * Setter to change whether or not a piece has moved already.
	 * @param moved True or false whether or not the piece has moved
	 */
	public void setFirstMove(boolean moved);

	/**
	 * Set the total number of moves this piece has done.
	 * @param moves new total number of moves.
	 */
	public void setTotalMoves(int moves);
	
	/**
	 * Get the total number of moves this piece has done.
	 * @return The total number of moves done.
	 */
	public int getTotalMoves();
}
