package chess;
import pieces.*;
import java.util.Scanner;

/**
 * 
 * @author Elijah Ongoco + Brian Albert
 * Class where the main method for actually playing chess is located.  Loops until the game is over.
 * White starts first.
 *
 */
public class Chess {
	public static void main(String[] args) {
		Board current = new Board();
		current.printBoard();
		
		String userMove;
		boolean checkMate = false;
		boolean whitesMove = true;
		
		Scanner scan = new Scanner(System.in);
		
		while (!checkMate) {
			if (whitesMove) {
				System.out.print("White's move: ");
			} else {
				System.out.print("Black's move: ");
			}
			
			// Get coordinates for next move
			userMove = scan.nextLine();
			
			//get row/column from a rank/file.  there's a method for this in Board.java created after this code was written.  also, if there's a 2nd index and it's "draw?" then call a draw.
			String[] commands = userMove.split(" ");
			if(commands[0].equals("resign")) {
				if(whitesMove) {
					System.out.println("Black wins");
				}
				else {
					System.out.println("White wins");
				}
				System.exit(0);
			}
			char currFile = commands[0].charAt(1);
			char currRank = commands[0].charAt(0);
			
			//draw
			if(commands.length == 3 && commands[2].equals("draw?")) {
				if(whitesMove) {
					System.out.println();
					current.printBoard();
					System.out.print("Black's move: ");
				}
				else {
					System.out.println();
					current.printBoard();
					System.out.print("White's move: ");
				}
				userMove = scan.nextLine();
				System.exit(0);
			}
			
			int row = 8 - Character.getNumericValue(currFile);
			int col = ((int)currRank) - 97;

			if (current.board[row][col] != null && whitesMove && current.board[row][col].getColor() == 'w'
					&& current.board[row][col].validateCommand(current.board, userMove)) {
				//check to see if this would cause check
				Piece[][] checkCheckClone = Board.deepClone(current.board);
				checkCheckClone[row][col].move(checkCheckClone, userMove);
				if(Board.inCheck('w', checkCheckClone)) {
					//cant do this, would put king in check
					System.out.println("Illegal move, try again");
					continue;
				}
				
				current.board[row][col].move(current.board, userMove);
				System.out.println();
				current.printBoard();
				//see if this move caused a check
				if(Board.inCheck('b', current.board)) {
					if(Board.inCheckmate('b', current.board)) {
						System.out.println("Checkmate\nWhite wins");
						System.exit(0);
					}
					System.out.println("Check");
				}
				whitesMove = false;
			} else if (current.board[row][col] != null && !whitesMove && current.board[row][col].getColor() == 'b'
					&& current.board[row][col].validateCommand(current.board, userMove)){
				Piece[][] checkCheckClone = Board.deepClone(current.board);
				checkCheckClone[row][col].move(checkCheckClone, userMove);
				if(Board.inCheck('b', checkCheckClone)) {
					//cant do this, would put king in check
					System.out.println("Illegal move, try again");
					continue;
				}
				current.board[row][col].move(current.board, userMove);
				System.out.println();
				current.printBoard();
				//see if this move caused a check
				if(Board.inCheck('w', current.board)) {
					if(Board.inCheckmate('w', current.board)) {
						System.out.println("Checkmate\nBlack wins");
						System.exit(0);
					}
					System.out.println("Check");
				}
				whitesMove = true;
			}else {
				System.out.println("Illegal move, try again");
			}		
			
			
		}
		
		scan.close();
	}
}
