package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //pieces matrix
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at leat 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) { //return the matrix pieces in row/column
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) { //OVERLOAD -- return the piece with position
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
		
	public void placePiece(Piece piece, Position position) { //put the Piece piece on the Position position of the board
		if (thereIsAPiece(position)) { //before placing a piece on the board, we must know if there is already a piece on that position
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece; 
		/*in the pieces matrix of the board, in the position.getrow and in the column position.getcolumn
		 attributes to this pieces matrix, the piece that came as argument (piece)*/
		piece.position = position; //now, the position of the piece isn't null anymore
		
    }
	
	
	//------------------------------------------METHOD TO TEST IF THE POSITION EXISTS-------------------------------------------------------------------------------------
	private boolean positionExists(int row, int column) { //easier test by the row/column than by the position
		return row >= 0 && row < rows && column >= 0 && column < columns; //rows is the height and columns is the width
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	//------------------------------------------METHOD TO TEST IF THERE IS A PIECE ON THE POSITION-------------------------------------------------------------------------
	
	public boolean thereIsAPiece(Position position) { 
		if(!positionExists(position)) { //before testing if there is a piece, we must test if the position exists
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; //if the position is different from null, returns the piece that is in the matrix in this position
	}
}
