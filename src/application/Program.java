package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {
			UI.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.print("Source: "); //origin position
			ChessPosition source = UI.readChessPosition(sc);//reading the chessposition
			
			System.out.println();
			System.out.print("Target: "); //target position?
			ChessPosition target = UI.readChessPosition(sc); //reading the chessposition
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
		
		
	}

}
