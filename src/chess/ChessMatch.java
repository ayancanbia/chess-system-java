package chess;

import boardgame.Board;
import boardgame.Piece;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) { // method to make the program capable of printing the possible moves from a source position
		Position position = sourcePosition.toPosition(); //convert the chessposition to matrix position
		validateSourcePosition(position);
		return board.piece(position).possibleMoves(); //return the possible moves of the piece on this position
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) { //method return a captured position 
		Position source = sourcePosition.toPosition(); //converting the chessposition to matrix position. remove the piece from the original position
		Position target = targetPosition.toPosition(); //to the target position
		validateSourcePosition(source); //method to know if there is a piece on the source position
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece)capturedPiece; //downcasting to ChessPiece, cause the capturedPiece is a Piece
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); //remove the piece from the original position
		Piece capturedPiece = board.removePiece(target); //remove (if) the piece on the target position
		board.placePiece(p, target); //now, the piece p is on the target position
		return capturedPiece;		
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) { //if the target position isn't a possible movement to the source piece, it means it can't move 
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
		
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //receive the chess coordinates and the chess piece
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //placing a new piecing receiving chess coordinates
	}
	
	private void initialSetup() { //responsible for starting the chess match, putting the pieces on the board
	    placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
