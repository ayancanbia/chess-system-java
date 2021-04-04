package application;

import chess.ChessPiece;

public class UI {

	public static void printBoard(ChessPiece[][] pieces) {
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
		
	}
	
	private static void printPiece(ChessPiece piece) { //aux method to print ONE PIECE!!	
		if (piece == null) { // it means that didn't have any piece in this position on the board
			System.out.print("-"); //position on the board
		}
		else {
			System.out.print(piece);
		}
		System.out.print(" "); //empty space: the pieces won't be stuck together
	}
}
