package pieces;
import chess.*;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * This class represents the Queen piece.  It implements the Piece interface, giving it its basic functionalities as a chess piece.
 * A queen will have one field, side, that is either 'b' for black or 'w' for white.
 *
 */
public class Queen implements Piece {
	/**
	 * side represents the color of the piece
	 * firstMove is true if the piece hasn't moved yet and false if it has
	 * totalMoves counts the number of moves the piece has made
	 */
	char side;
	boolean firstMove = true;
	int totalMoves = 0;
	
	/**
	 * Creates a queen with the designated side
	 * @param side The side this piece belongs in, 'b' for black or 'w' for white
	 */
	public Queen(char side) {
		this.side = side;
	}

	@Override
	public void move(Piece[][] board, String command) {
		// TODO Auto-generated method stub
		String[] commands = command.split(" ");

		firstMove = false;
		char newSide = this.side;
		
		int currRow =  Board.rankFileToIndex(commands[0])[0];
		int currCol = Board.rankFileToIndex(commands[0])[1];

		int newRow = Board.rankFileToIndex(commands[1])[0];
		int newCol = Board.rankFileToIndex(commands[1])[1];
		
		board[currRow][currCol] = null;
		board[newRow][newCol] = new Queen(newSide);
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

		int currRow =  Board.rankFileToIndex(commands[0])[0];
		int currCol = Board.rankFileToIndex(commands[0])[1];
		int newRow = Board.rankFileToIndex(commands[1])[0];
		int newCol = Board.rankFileToIndex(commands[1])[1];
		
		//Bishop moves in diagonals and is not staying in place, check its path
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
		return side + "Q";
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
