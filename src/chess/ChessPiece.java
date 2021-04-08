package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color; // class attribute

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { //the color can't be changed, only accessed
		return color;
	}
	
	public ChessPosition getChessPosition() { //get the chess position, cause the Position position of the Piece class is protected
		return ChessPosition.fromPosition(position); //method returns a chess position 
	}
	
	
	protected boolean isThereOpponentPiece(Position position) { 
		ChessPiece p = (ChessPiece)getBoard().piece(position); //chesspiece receive the piece on the position 
		return p != null && p.getColor() != color; //test if the piece color is not the same as the moving piece
	}
	
	
	

}
