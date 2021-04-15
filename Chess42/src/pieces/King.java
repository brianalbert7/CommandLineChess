package pieces;
import chess.Board;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * This class represents the King piece.  It implements the Piece interface, giving it its basic functionalities as a chess piece.
 * A king will have one field, side, that is either 'b' for black or 'w' for white.
 *
 */
public class King implements Piece {
	/**
	 * side represents the color of the piece
	 * firstMove is true if the piece hasn't moved yet and false if it has
	 * totalMoves counts the number of moves the piece has made
	 */
	char side;
	boolean firstMove = true;
	int totalMoves = 0;
	
	/**
	 * Creates a King with the designated side
	 * @param side The side this piece belongs in, 'b' for black or 'w' for white
	 */
	public King(char side) {
		this.side = side;
	}

	@Override
	public void move(Piece[][] board, String command) {
		// TODO Auto-generated method stub
		String[] commands = command.split(" ");
		int[] indexOne = Board.rankFileToIndex(commands[0]);
		int[] indexTwo = Board.rankFileToIndex(commands[1]);
		int firstR = indexOne[0];
		int firstC = indexOne[1];
		int secondR = indexTwo[0];
		int secondC = indexTwo[1];
		
		//check the castling
		//white castle to the right
		if((firstR == 7 && firstC == 4 && secondR == 7 && secondC == 6)) {
			board[7][6] = new King(side);
			board[7][5] = new Rook(side);
			board[7][4] = null;
			board[7][7] = null;
			board[7][6].setFirstMove(false);
		}
		//white castle to the left
		else if((firstR == 7 && firstC == 4 && secondR == 7 && secondC == 2)) {
			board[7][2] = new King(side);
			board[7][3] = new Rook(side);
			board[7][4] = null;
			board[7][0] = null;
			board[7][2].setFirstMove(false);
		}
		//black castle to the right
		else if((firstR == 0 && firstC == 4 && secondR == 0 && secondC == 6)) {
			board[0][6] = new King(side);
			board[0][5] = new Rook(side);
			board[0][4] = null;
			board[0][7] = null;
			board[0][6].setFirstMove(false);
		}
		//black castle to the left
		else if((firstR == 0 && firstC == 4 && secondR == 0 && secondC == 2)) {
			board[0][2] = new King(side);
			board[0][3] = new Rook(side);
			board[0][4] = null;
			board[0][0] = null;
			board[0][2].setFirstMove(false);
		}
		else {
			board[firstR][firstC] = null;
			board[secondR][secondC] = new King(side);
			board[secondR][secondC].setFirstMove(false);
		}
		
	}

	@Override
	public boolean validateCommand(Piece[][] board, String command) {
		// TODO Auto-generated method stub
		String[] commands = command.split(" ");
		int[] indexOne = Board.rankFileToIndex(commands[0]);
		int[] indexTwo = Board.rankFileToIndex(commands[1]);
		int firstR = indexOne[0];
		int firstC = indexOne[1];
		int secondR = indexTwo[0];
		int secondC = indexTwo[1];
		
		if(firstR == secondR && firstC == secondC) return false;

		//check for castling
		if((firstR == 7 && firstC == 4 && secondR == 7 && secondC == 6)) {
			if(board[7][4].getFirstMove() && board[7][7].getFirstMove() && board[7][5] == null && board[7][6] == null && board[7][7] instanceof Rook) {
				return true;
			}
			return false;
		}
		//white castle to the left
		else if((firstR == 7 && firstC == 4 && secondR == 7 && secondC == 2)) {
			if(board[7][4].getFirstMove() && board[7][0].getFirstMove() && board[7][1] == null && board[7][2] == null && board[7][3] == null && board[7][0] instanceof Rook) {
				return true;
			}
			return false;
		}
		//black castle to the right
		else if((firstR == 0 && firstC == 4 && secondR == 0 && secondC == 6)) {
			if(board[0][4].getFirstMove() && board[0][7].getFirstMove() && board[0][5] == null && board[0][6] == null && board[0][7] instanceof Rook) {
				return true;
			}
			return false;
		}
		//black castle to the left
		else if((firstR == 0 && firstC == 4 && secondR == 0 && secondC == 2)) {
			if(board[0][4].getFirstMove() && board[0][0].getFirstMove() && board[0][1] == null && board[0][2] == null && board[0][3] == null && board[0][0] instanceof Rook) {
				return true;
			}
			return false;
		}
		//regular move
		else {
			//see if its a legal move (total move of one space NSEW)
			if(Math.abs(firstR - secondR) > 1 || Math.abs(firstC - secondC) > 1) {
				return false;
			}
			if(board[secondR][secondC] != null && board[secondR][secondC].getColor() == this.getColor()) {
				return false;
			}
		}
		//finally, see if this would cause a check state.
		
		Piece[][] boardClone = Board.deepClone(board);
		boardClone[firstR][firstC] = null;
		boardClone[secondR][secondC] = new King(side);
		if(Board.inCheck(side, boardClone)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the string representation of this piece.
	 * It will be a 2 character string, the first character is the side the piece is on ('b' for black or 'w' for white).
	 * The second character is the designated character for this piece.
	 */
	public String toString() {
		return side + "K";
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
