package boardgame;

public class BoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoardException(String msg) { //receive a string msg and throws it to the constructor of the superclass
		super(msg); 
	}
	
	
}
