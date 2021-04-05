package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row <1 || row > 8) 
			throw new ChessException("Erro instantiating ChessPosition. Valid values are from a1 to h8.");
		this.column = column;
		this.row = row;
	}
	
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

//----------------------------------------------------METHOD TO CONVERT MATRIX POSITIONS TO CHESS POSITIONS---------------------------------------------------------------
	
	protected Position toPosition() { //from chess to matrix
		return new Position(8 - row, column - 'a'); //matrix_row = 8 - chess_row and matrix_column = chess_column - 'a'
	}
	
	
	protected static ChessPosition fromPosition(Position position) { //from matrix to chess
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow()); //casting to char
	}
	
	@Override
	public String toString() {
		return "" + column + row; //empty string to force the compilator understand that this is a concatenation of strings
	}
	
}
