package exceptions;

public class CampoInvalidoException extends RuntimeException {

	public CampoInvalidoException() { super(); }
	
	public CampoInvalidoException(String msg) { super(msg); }
}
