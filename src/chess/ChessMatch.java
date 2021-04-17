package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8); //this class is responsible for knowing the size of a board
		turn = 1;
		currentPlayer = Color.WHITE; //whites begin
		check = false; //a boolean property starts with false value always
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check!");
		}	
		check = (testCheck(opponent(currentPlayer))) ? true : false; //if testCheck is true, the opponent is in check
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
		nextTurn();
		}
		return (ChessPiece)capturedPiece; //downcasting to ChessPiece, cause the capturedPiece is a Piece
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source); //remove the piece from the original position
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target); //remove (if) the piece on the target position
		board.placePiece(p, target); //now, the piece p is on the target position
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece); //remove the piece from the list of pieces on the board
			capturedPieces.add(capturedPiece); //and add to the list of captured pieces
		}
		return capturedPiece;		
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //method to undo moves -- p.ex player put himself in check
		ChessPiece p = (ChessPiece)board.removePiece(target); //remove the piece of the target
		p.decreaseMoveCount();
		board.placePiece(p, source); //return the piece to the source position
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
		
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours!");
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
		
	private void nextTurn() {
		turn++; 
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //if the current player is white, then it will now be black, otherwise it will be white
	}
	
	private Color opponent(Color color) { //method returns the color of the opponent
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) { //method to find the king of each color
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //filtering the list. list receives the pieces in play,
	// look for every piece x, such that the color of piece x is the color of the argument. downcasting necessary because piece has no color	
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("The is no" + color + " king on the board"); 
	}
	
	private boolean testCheck(Color color) { //testing if the king of that color is in check
		Position kingPosition = king(color).getChessPosition().toPosition(); //get the chess position of the king and convert to matrix position
		List<Piece> opponentPieces =  piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p: opponentPieces) {
			boolean[][] mat = p.possibleMoves(); //matrix of possibles moves of the opponent piece p
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { //if the element of the matrix is true, means that the king is in check
				return true; //check test is true
			}
		}
		return false; 
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { //if this color isnt in check, then isnt in check mate either
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //filtring every piece of this color
		for (Piece p: list) { //if there is any piece p of the list that has the move that takes from the check, return false
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) { 
				for(int j=0; j<board.getColumns(); j++) {
					if(mat[i][j]) { //testing if this possible move could take the piece off checkmate
						Position source = ((ChessPiece)p).getChessPosition().toPosition(); //downcasting to chesspiece to be able to get the position
						Position target = new Position(i, j); //i j is the target position, cause it is a possible move
						Piece capturedPiece = makeMove(source, target); //piece did the possible move
						boolean testCheck = testCheck(color); //testing if the king's color still in check after move
						undoMove(source, target, capturedPiece); //the move was made to test, so it is necessary to undo the move
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //receive the chess coordinates and the chess piece
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); //placing a new piecing receiving chess coordinates
		piecesOnTheBoard.add(piece); 
	}
	
	private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
