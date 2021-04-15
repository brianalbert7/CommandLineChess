package pieces;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * This class represents the Bishop piece.  It implements the Piece interface, giving it its basic functionalities as a chess piece.
 * A bishop will have one field, side, that is either 'b' for black or 'w' for white.
 *
 */
public class Bishop implements Piece {
	/**
	 * side represents the color of the piece
	 * firstMove is true if the piece hasn't moved yet and false if it has
	 * totalMoves counts the number of moves the piece has made
	 */
	char side;
	boolean firstMove = true;
	int totalMoves = 0;
	
	/**
	 * Creates a new Bishop object given a side.
	 * @param side 'b' for black or 'w' for white.
	 */
	public Bishop(char side) {
		this.side = side;
	}

	/**
	 * move moves the bishop from old spot to new spot.
	 * @param board The board to use
	 * @param command The string command to parse through
	 */
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
			board[newRow][newCol] = new Bishop('w');
			board[currRow][currCol] = null;
		} else {
			board[newRow][newCol] = new Bishop('b');
			board[currRow][currCol] = null;
		}
	}

	/**
	 * validateCommand checks if the command entered is a valid bishop move
	 * @param board The board to use
	 * @param command The string command to parse through
	 * @return true if the command is valid, and false if not
	 */
	@Override
	public boolean validateCommand(Piece[][] board, String command) {
		// TODO Auto-generated method stub
		String[] commands = command.split(" ");

		int currRow =  rankFileToIndex(commands[0])[0];
		int currCol = rankFileToIndex(commands[0])[1];
		int newRow = rankFileToIndex(commands[1])[0];
		int newCol = rankFileToIndex(commands[1])[1];

		// Bishop moves in diagonals and is not staying in place, check its path
		if(Math.abs(currRow - newRow) == Math.abs(currCol - newCol) && Math.abs(currRow - newRow) > 0) {
			String state = "";
			if(currRow < newRow && currCol < newCol) state = "upright";
			else if(currRow < newRow && currCol > newCol) state = "upleft";
			else if(currRow > newRow && currCol < newCol) state = "downright";
			else state = "downleft";
			
			//check if the path is clear
			int iterateRow = currRow;
			int iterateCol = currCol;
			
			if(state.equals("upright") || state.equals("upleft")) iterateRow++;
			else iterateRow--;
			
			if(state.equals("upright") || state.equals("downright")) iterateCol++;
			else iterateCol--;
			
			while(iterateRow != newRow && iterateCol != newCol) {
				if(board[iterateRow][iterateCol] != null) {
					return false;
				}
				if(state.equals("upright") || state.equals("upleft")) iterateRow++;
				else iterateRow--;
				
				if(state.equals("upright") || state.equals("downright")) iterateCol++;
				else iterateCol--;
			}
			if(board[iterateRow][iterateCol] != null && board[iterateRow][iterateCol].getColor() != side) {
				return true;
			}
			else if(board[iterateRow][iterateCol] == null) return true;
			else return false;
		}
		return false;
	}
	
	/**
	 * Returns the string representation of this piece.
	 * It will be a 2 character string, the first character is the side the piece is on ('b' for black or 'w' for white).
	 * The second character is the designated character for this piece.
	 */
	public String toString() {
		return side + "B";
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
