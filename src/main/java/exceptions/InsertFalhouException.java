package exceptions;

public class InsertFalhouException extends RuntimeException{

	public InsertFalhouException() {
		super();
	}
	
	public InsertFalhouException(String msg) {
		super(msg);
	}
}
