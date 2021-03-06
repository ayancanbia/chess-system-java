package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
		
		Position p = new Position(0, 0);
				
		//above
		p.setValues(position.getRow() - 1, position.getColumn()); //-1 is the row above the position, and the column is the same
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //while the position exists and there is not a piece on there, the position is true
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left
		p.setValues(position.getRow(), position.getColumn() - 1); //-1 is the column left to the position, and the row is the same
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //while the position exists and there is not a piece on there, the position is true
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//right
		p.setValues(position.getRow(), position.getColumn() + 1); //+1 is the column right to the position, and the row is the same
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //while the position exists and there is not a piece on there, the position is true
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below
		p.setValues(position.getRow() + 1, position.getColumn()); //+1 is the row below the position, and the column is the same
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //while the position exists and there is not a piece on there, the position is true
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}	
	
	
	
	
}
