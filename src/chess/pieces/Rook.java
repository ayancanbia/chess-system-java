package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) { // this constructor is informing who is the board, what is the color of the piece
		super(board, color);
	}

	
	@Override
	public String toString() {
		return "R"; // this letter will appear on the board
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}	
}
