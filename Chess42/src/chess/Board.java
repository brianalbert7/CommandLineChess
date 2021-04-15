package chess;

import pieces.*;

/**
 * @author Elijah Ongoco + Brian Albert
 * This class is for a Board object, which has two fields: turn (String) and board (Piece[][])
 * turn will always either have "white" or "black" signifying whose turn it is to move.
 * board is an 8x8 matrix, where each index is a space on the board.  The Piece object is detailed in the pieces package.
 *
 */
public class Board {
	/**
	 * turn represents the color of the player who is up
	 * board represents the chess board
	 */
	String turn;
	Piece[][] board;

	
	/**
	 * Initializes a Board object, with the default starting player as "white", and it fills up the 
	 * 8x8 board with a standard starting chess layout.  White will be on the bottom part of the board, black will be on top.
	 */
	public Board() {
		this.turn = "white";
		this.board = new Piece[8][8];
		
		//go across the board left to right placing all the pieces in the right places, black on 'top' white on 'bottom'
		for(int i = 0; i < 8; i++) {
			//place pawns
			this.board[1][i] = new Pawn('b');
			this.board[6][i] = new Pawn('w');
		}
		//place rooks
		this.board[0][0] = new Rook('b');
		this.board[0][7] = new Rook('b');
		this.board[7][0] = new Rook('w');
		this.board[7][7] = new Rook('w');
		//place knights
		this.board[0][1] = new Knight('b');
		this.board[0][6] = new Knight('b');
		this.board[7][1] = new Knight('w');
		this.board[7][6] = new Knight('w');
		//place bishops
		this.board[0][2] = new Bishop('b');
		this.board[0][5] = new Bishop('b');
		this.board[7][2] = new Bishop('w');
		this.board[7][5] = new Bishop('w');
		//place queens
		this.board[0][3] = new Queen('b');
		this.board[7][3] = new Queen('w');
		//place kings
		this.board[0][4] = new King('b');
		this.board[7][4] = new King('w');
	}
	
	/**
	 * Takes an index in a 2D array and returns what the rank and file for that index would be given our implementation of the chess board.
	 * @param row The row of the index
	 * @param column The column of the index
	 * @return The file/rank designation.  For example, board[6][4] would return "e2".
	 */
	public static String indexToRankFile(int row, int column) {
		String toReturn = "";
		char rank = (char) (column + 97);
		int file = (8 - row);
		toReturn += rank;
		toReturn += file;
		return toReturn;
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
	
	/**
	 * Checks whether or not the current state of the board is in check for the specified king.
	 * @param board The board to be assessed.
	 * @param side 'b' for checking if black is in check, 'w' for checking if white is in check.
	 * @return true or false depending on if the current state of the board is in check or not.
	 */
	public static boolean inCheck(char side, Piece[][] board) {
		//if side is black, then check all white pieces.  otherwise, check the black pieces
		char checkFor = side == 'b' ? 'w' : 'b';
		
		//find where the king is first
		int[] kingIndex = new int[2];
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null && board[i][j].getColor() == side && board[i][j] instanceof King) {
					kingIndex[0] = i;
					kingIndex[1] = j;
					break;
				}
			}
		}
		
		//convert index to rank/file
		String kingRankFile = indexToRankFile(kingIndex[0], kingIndex[1]);
		
		//check if every non-king white piece is unable to currently attack the king.  if not, then the board is IN CHECK!
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null && !(board[i][j] instanceof King) && board[i][j].getColor() == checkFor) {
					String toKing = "";
					toKing += indexToRankFile(i, j);
					toKing += " " + kingRankFile;
					if(board[i][j].validateCommand(board, toKing)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks whether or not the current state of the board is in checkmate, provided it is already in check.
	 * @param side For which side the checkmate may be against
	 * @param board The board state of which to check
	 * @return True if it is checkmate, false if it is not
	 */
	public static boolean inCheckmate(char side, Piece[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null && board[i][j].getColor() == side) {
					String current = indexToRankFile(i, j);
					for(int r = 0; r < board.length; r++) {
						for(int s = 0; s < board[0].length; s++) {
							String toCheck = indexToRankFile(r, s);
							String testCommand = current + " " + toCheck;
							Piece[][] clone = Board.deepClone(board);
							if(clone[i][j].validateCommand(clone, testCommand)) {
								clone[i][j].move(clone, testCommand);
								if(!Board.inCheck(side, clone)) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Prints the board in the terminal after every move.  
	 * Done on an existing board object, on its Piece[][] field.
	 */
	public void printBoard() {
		//print board, put white/black spaces in right places otherwise
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[0].length; j++) {
				//first 2 if statements are to check if you need to put a black/white space
				if(this.board[i][j] == null && i % 2 == 0) {
					if(j % 2 == 1) {
						System.out.print("##  ");
					}
					else {
						System.out.print("    ");
					}
				}
				else if(this.board[i][j] == null && i % 2 == 1) {
					if(j % 2 == 1) {
						System.out.print("    ");
					}
					else {
						System.out.print("##  ");
					}
				}
				else {
					System.out.print(this.board[i][j] + "  ");
				}
			}
			//put numbers on right
			System.out.println((8-i));
		}
		//put letters on bottom
		for(int i = 0; i < this.board.length; i++) {
			System.out.print(" " + (char)(i + 97) + "  ");
		}
		System.out.println("\n");
	}
	
	/**
	 * deepClone returns a deep clone of the board
	 * @param toClone the board to be cloned 
	 * @return the new cloned board
	 */
	public static Piece[][] deepClone(Piece[][] toClone) {
		Piece[][] toReturn = new Piece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(toClone[i][j] != null) {
					if(toClone[i][j] instanceof Bishop) {
						toReturn[i][j] = new Bishop(toClone[i][j].getColor());
					}
					if(toClone[i][j] instanceof King) {
						toReturn[i][j] = new King(toClone[i][j].getColor());
					}
					if(toClone[i][j] instanceof Knight) {
						toReturn[i][j] = new Knight(toClone[i][j].getColor());
					}
					if(toClone[i][j] instanceof Pawn) {
						toReturn[i][j] = new Pawn(toClone[i][j].getColor());
					}
					if(toClone[i][j] instanceof Queen) {
						toReturn[i][j] = new Queen(toClone[i][j].getColor());
					}
					if(toClone[i][j] instanceof Rook) {
						toReturn[i][j] = new Rook(toClone[i][j].getColor());
					}
				}
				else toReturn[i][j] = null;
			}
		}
		return toReturn;
	}
}