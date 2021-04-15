package pieces;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * This class represents the Knight piece.  It implements the Piece interface, giving it its basic functionalities as a chess piece.
 * A Knight will have one field, side, that is either 'b' for black or 'w' for white.
 *
 */
public class Knight implements Piece {
	/**
	 * side represents the color of the piece
	 * firstMove is true if the piece hasn't moved yet and false if it has
	 * totalMoves counts the number of moves the piece has made
	 */
	char side;
	boolean firstMove = true;
	int totalMoves = 0;
	
	/**
	 * Creates a knight with the designated side
	 * @param side The side this piece belongs in, 'b' for black or 'w' for white
	 */
	public Knight(char side) {
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
			board[newRow][newCol] = new Knight('w');
			board[currRow][currCol] = null;
		} else {
			board[newRow][newCol] = new Knight('b');
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

		if ((currRow+2 == newRow && currCol+1 == newCol) || (currRow+1 == newRow && currCol+2 == newCol) 
		|| (currRow+2 == newRow && currCol-1 == newCol) || (currRow+1 == newRow && currCol-2 == newCol) 
		|| (currRow-2 == newRow && currCol-1 == newCol) || (currRow-1 == newRow && currCol-2 == newCol) 
		|| (currRow-2 == newRow && currCol+1 == newCol) || (currRow-1 == newRow && currCol+2 == newCol)){
			if (board[newRow][newCol] == null){
				return true;
			} else if (board[newRow][newCol] != null && isWhite && board[newRow][newCol].getColor() == 'b') {
				return true;
			} else if (board[newRow][newCol] != null && !isWhite && board[newRow][newCol].getColor() == 'w'){
				return true;
			}
		}

		return false;
	}
	
	public String toString() {
		return side + "N";
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
