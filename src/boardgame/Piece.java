package boardgame;

public class Piece {
	
	protected Position position; //it's not a chess position (matrix position), is not visible in the chess layer
	private Board board; //association of the piece with the board
	
	public Piece(Board board) { //the position of a newly created piece is initially null, it means that the piece has not been placed on the board yet, so it isn't part of the constructor 
		this.board = board;
		position = null;
	}

	protected Board getBoard() { //just the get method: the board can't be changed -- protected: only classes and subclasses within the same package will be able to access the board
		return board;
	}	
}
