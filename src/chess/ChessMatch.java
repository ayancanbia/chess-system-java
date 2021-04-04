package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); //this class is responsible for knowing the size of a board
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() { //this method returns a pieces matrix corresponding to the match
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; /*the board class has a matrix of pieces of the type Piece, 
		but the method in the ChessMatch class, returns the "ChessPiece", because it is in the chess layer.
		For application, we don't want to release pieces of the "Piece" type, but of the "ChessPiece" type.
		The program can only see the chess piece, and not the inner piece of the board.*/
		for (int i=0; i<board.getRows(); i++) { //go through the matrix of pieces on the board, and for each piece do a downcasting to "ChessPiece"
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); //for each position i:j of the board, do the matrix mat in the line:i column:j receive the board.piece(i, j)
			}
		}
		return mat; 
	}
	
	private void initialSetup() { //responsible for starting the chess match, putting the pieces on the board
		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
		board.placePiece(new King(board, Color.BLACK), new Position(0,4));
	}
}
