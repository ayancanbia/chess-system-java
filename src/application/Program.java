package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while (!chessMatch.getCheckMate()) { //while the match isnt in check mate
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: "); //origin position
				ChessPosition source = UI.readChessPosition(sc);//reading the chessposition
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves); //overload of printBoard, responsible to print the board coloring the possible moves of the source position
				
				System.out.println();
				System.out.print("Target: "); //target position?
				ChessPosition target = UI.readChessPosition(sc); //reading the chessposition
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if (capturedPiece != null) { //if the movement results in a capture, add the piece to the list
					captured.add(capturedPiece);
					}
				
				if(chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine().toUpperCase();
					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}
				
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}
