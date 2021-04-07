package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {

	private Color color; // class attribute

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;

	}

	public Color getColor() { //the color can't be changed, only accessed
		return color;
	}

}
