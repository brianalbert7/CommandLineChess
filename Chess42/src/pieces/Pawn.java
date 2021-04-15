package pieces;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * This class represents the Pawn piece.  It implements the Piece interface, giving it its basic functionalities as a chess piece.
 * A pawn will have one field, side, that is either 'b' for black or 'w' for white.
 *
 */
public class Pawn implements Piece {
	/**
	 * side represents the color of the piece
	 * firstMove is true if the piece hasn't moved yet and false if it has
	 * totalMoves counts the number of moves the piece has made
	 */
	char side;
	boolean firstMove = true;
	int totalMoves = 0;
	
	/**
	 * Creates a new Pawn with the designated side
	 * @param side The side this piece belongs in, 'b' for black or 'w' for white
	 */
	public Pawn(char side) {
		this.side = side;
	}

	@Override
	public void move(Piece[][] board, String command) {
		// TODO Auto-generated method stub
		String[] commands = command.split(" ");

		int currRow =  rankFileToIndex(commands[0])[0];
		int currCol = rankFileToIndex(commands[0])[1];

		int newRow = rankFileToIndex(commands[1])[0];
		int newCol = rankFileToIndex(commands[1])[1];

		firstMove = false;

		boolean isWhite;
		if (side == 'w'){
			isWhite = true;
		} else {
			isWhite = false;
		}

		// If pawn is promoting 
		if (isWhite && newRow == 0){
			if (commands.length == 3){
				char promotion = commands[2].charAt(0);
				switch(promotion){
					case 'R': 
						board[newRow][newCol] = new Rook('w');
						board[newRow][newCol].setFirstMove(false);
						board[currRow][currCol] = null;
						break;
					case 'N': 
						board[newRow][newCol] = new Knight('w');
						board[currRow][currCol] = null;
						break;
					case 'B': 
						board[newRow][newCol] = new Bishop('w');
						board[currRow][currCol] = null;
						break;
					case 'Q': 
						board[newRow][newCol] = new Queen('w');
						board[currRow][currCol] = null;
						break;
				}
			} else {
				board[newRow][newCol] = new Queen('w');
				board[currRow][currCol] = null;
			}
			
		} else if (!isWhite && newRow == 7) {
			if (commands.length == 3){
				char promotion = commands[2].charAt(0);
				switch(promotion){
					case 'R': 
						board[newRow][newCol] = new Rook('b');
						board[newRow][newCol].setFirstMove(false);
						board[currRow][currCol] = null;
					case 'N': 
						board[newRow][newCol] = new Knight('b');
						board[currRow][currCol] = null;
					case 'B': 
						board[newRow][newCol] = new Bishop('b');
						board[currRow][currCol] = null;
					case 'Q': 
						board[newRow][newCol] = new Queen('b');
						board[currRow][currCol] = null;
				}
			} else {
				board[newRow][newCol] = new Queen('b');
				board[currRow][currCol] = null;
			}

		} else {
			if (isWhite){
				//enpassant
				if(Math.abs(currRow - newRow) == 1 && Math.abs(currCol - newCol) == 1 
						&& board[currRow][newCol] != null && board[newRow][newCol] == null) {
					board[currRow][newCol] = null;
				}
				board[newRow][newCol] = new Pawn('w');
				board[newRow][newCol].setFirstMove(false);
				board[newRow][newCol].setTotalMoves(totalMoves + 1);
				board[currRow][currCol] = null;
				
			} else {
				//enpassant
				if(Math.abs(currRow - newRow) == 1 && Math.abs(currCol - newCol) == 1 
						&& board[currRow][newCol] != null && board[newRow][newCol] == null) {
					board[currRow][newCol] = null;
				}
				board[newRow][newCol] = new Pawn('b');
				board[newRow][newCol].setFirstMove(false);
				board[newRow][newCol].setTotalMoves(totalMoves + 1);
				board[currRow][currCol] = null;
			}
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

		// Check if pawn is moving forward
		if (currCol == newCol){
			if (Math.abs(currRow-newRow) == 1){
				// If pawn moving forward one space
				if (isWhite){
					if (newRow < currRow){
						if (currRow + 1 == newRow && board[newRow][newCol] == null) {
							return true;
						} else if (currRow - 1 == newRow && board[newRow][newCol] == null) {
							return true;
						}
					} else {
						return false;
					}
				} else {
					if (currRow < newRow){
						if (currRow + 1 == newRow && board[newRow][newCol] == null) {
							return true;
						} else if (currRow - 1 == newRow && board[newRow][newCol] == null) {
							return true;
						}
					} else {
						return false;
					}
				}
			}

			// If pawn is moving forward two spaces
			if (Math.abs(currRow-newRow) == 2){
				// Make sure it is moving up in the same file
				if (currCol != newCol){
					return false;
				}
				
				// Check to see if it is the pawns first move
				if (firstMove){
					// If pawn is white
					if (isWhite){
						if (board[newRow][newCol] == null && board[currRow-1][currCol] == null) {
							return true;
						} else {
							return false;
						}
					}

					// If pawn is black
					if (!isWhite) {
						if (board[newRow][newCol] == null && board[currRow+1][currCol] == null) {
							return true;
						} else {
							return false;
						}
					}
				} else{
					return false;
				}
			}
		}

		// If pawn is taking
		if (Math.abs(currCol-newCol) == 1 && Math.abs(currRow-newRow) == 1){
			//check for en passant
			if(isWhite) {
				if(currRow == 3) {
					if(board[currRow][newCol] instanceof Pawn && board[currRow][newCol].getTotalMoves() == 1) {
						return true;
					}
				}
			}
			else {
				if(currRow == 4) {
					if(board[currRow][newCol] instanceof Pawn && board[currRow][newCol].getTotalMoves() == 1) {
						return true;
					}
				}
			}
			
			// Return false if no piece to capture
			if (board[newRow][newCol] == null){
				return false;
			} else {
				// Make sure piece in new square is opposite color
				if (isWhite && board[newRow][newCol].getColor() == 'b'){
					return true;
				} else if (!isWhite && board[newRow][newCol].getColor() == 'w') {
					return true;
				} else {
					return false;
				}
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
		return side + "p";
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
		return this.totalMoves;
	}
}
