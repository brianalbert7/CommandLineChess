package pieces;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * This class represents the Rook piece.  It implements the Piece interface, giving it its basic functionalities as a chess piece.
 * A Rook will have one field, side, that is either 'b' for black or 'w' for white.
 *
 */
public class Rook implements Piece {
	/**
	 * side represents the color of the piece
	 * firstMove is true if the piece hasn't moved yet and false if it has
	 * totalMoves counts the number of moves the piece has made
	 */
	char side;
	boolean firstMove = true;
	int totalMoves = 0;

	/**
	 * Creates a new Rook with the designated side
	 * @param side The side this piece belongs in, 'b' for black or 'w' for white
	 */
	public Rook(char side) {
		this.side = side;
	}

	@Override
	public void move(Piece[][] board, String command) {
		// TODO Auto-generated method stub
		String[] commands = command.split(" ");

		firstMove = false;

		int currRow =  rankFileToIndex(commands[0])[0];
		int currCol = rankFileToIndex(commands[0])[1];

		int newRow = rankFileToIndex(commands[1])[0];
		int newCol = rankFileToIndex(commands[1])[1];

		boolean isWhite;
		if (side == 'w'){
			isWhite = true;
		} else {
			isWhite = false;
		}

		if (isWhite){
			board[newRow][newCol] = new Rook('w');
			board[newRow][newCol].setFirstMove(false);
			board[currRow][currCol] = null;
		} else {
			board[newRow][newCol] = new Rook('b');
			board[newRow][newCol].setFirstMove(false);
			board[currRow][currCol] = null;
		}
	}

	@Override
	public boolean validateCommand(Piece[][] board, String command) {
		// TODO Auto-generated method stub
		String[] commands = command.split(" ");

		boolean isWhite;
		if (side == 'w'){
			isWhite = true;
		} else {
			isWhite = false;
		}

		int currRow =  rankFileToIndex(commands[0])[0];
		int currCol = rankFileToIndex(commands[0])[1];

		int newRow = rankFileToIndex(commands[1])[0];
		int newCol = rankFileToIndex(commands[1])[1];
		
		// Moving left
		if ((currCol - newCol) > 0 && currRow == newRow) {
			// Check if pieces are blocking the move
			for (int i = newCol+1; i < currCol; i++){
				if (board[newRow][i] != null) {
					return false;
				} 
			}

			if (board[newRow][newCol] != null && isWhite && board[newRow][newCol].getColor() == 'b'){
				return true;
			} else if (board[newRow][newCol] != null && !isWhite && board[newRow][newCol].getColor() == 'w'){
				return true;
			} else if (board[newRow][newCol] == null){
				return true;
			} else {
				return false;
			}

		// Moving right
		} else if ((currCol - newCol) < 0 && currRow == newRow) {
			// Check if pieces are blocking the move
			for (int i = currCol+1; i < newCol; i++){
				if (board[newRow][i] != null) {
					return false;
				} 
			}

			if (board[newRow][newCol] != null && isWhite && board[newRow][newCol].getColor() == 'b'){
				return true;
			} else if (board[newRow][newCol] != null && !isWhite && board[newRow][newCol].getColor() == 'w'){
				return true;
			} else if (board[newRow][newCol] == null){
				return true;
			} else {
				return false;
			}

		// Moving up
		} else if ((currRow-newRow) > 0 && currCol == newCol){
			// Check if pieces are blocking the move
			for (int i = newRow+1; i < currRow; i++){
				if (board[i][newCol] != null) {
					return false;
				} 
			}

			if (board[newRow][newCol] != null && isWhite && board[newRow][newCol].getColor() == 'b'){
				return true;
			} else if (board[newRow][newCol] != null && !isWhite && board[newRow][newCol].getColor() == 'w'){
				return true;
			} else if (board[newRow][newCol] == null){
				return true;
			} else {
				return false;
			}

		// Moving down
		} else if ((currRow-newRow) < 0 && currCol == newCol){
			// Check if pieces are blocking the move
			for (int i = currRow+1; i < newRow; i++){
				if (board[i][newCol] != null) {
					return false;
				} 
			}

			if (board[newRow][newCol] != null && isWhite && board[newRow][newCol].getColor() == 'b'){
				return true;
			} else if (board[newRow][newCol] != null && !isWhite && board[newRow][newCol].getColor() == 'w'){
				return true;
			} else if (board[newRow][newCol] == null){
				return true;
			} else {
				return false;
			}
		}

		return false;
	}
	
	/**
	 * Returns the string representation of this piece.
	 * It will be a 2 character string, the first character is the side the piece is on ('b' for black or 'w' for white).
	 * The second character is the designated character for this piece.
	 */
	public String toString() {
		return side + "R";
	}

	@Override
	public char getColor() {
		// TODO Auto-generated method stub
		return this.side;
	}

	@Override
	public boolean getFirstMove() {
		// TODO Auto-generated method stub
		return this.firstMove;
	}

	/**
	 * Takes a String designating a rank/file for a piece and returns what the coordinates in our matrix would be.
	 * @param fileRank The string designation of file/rank, for example, "e2".
	 * @return A set of coordinates where [0] would be the row, and [1] would be the column.  For example, "e2" would return [6, 4]
	 */
	public static int[] rankFileToIndex(String fileRank) {
		int[] toReturn = new int[2];
		char file = fileRank.charAt(1);
		char rank = fileRank.charAt(0);
		
		toReturn[0] = 8 - Character.getNumericValue(file);
		toReturn[1] = ((int)rank) - 97;
		
		return toReturn;
	}

	@Override
	public void setFirstMove(boolean moved) {
		// TODO Auto-generated method stub
		this.firstMove = moved;
	}

	@Override
	public void setTotalMoves(int moves) {
		this.totalMoves = moves;
	}

	@Override
	public int getTotalMoves() {
		// TODO Auto-generated method stub
		return this.totalMoves;
	}
}
