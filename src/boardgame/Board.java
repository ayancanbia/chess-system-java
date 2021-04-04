package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //pieces matrix
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) { //return the matrix pieces in row/column
		return pieces[row][column];
	}
	
	public Piece piece(Position position) { //OVERLOAD -- return the piece with position
		return pieces[position.getRow()][position.getColumn()];
	}
		
}
