package br.ufpr.exception;

public class NullParameterException extends TccException {

	private static final String DEFAULT_MESSAGE = "O parametro passado n�o pode ser nulo!";
	private static final long serialVersionUID = 1L;
	
	public NullParameterException() {
		super(DEFAULT_MESSAGE);
	}

	public NullParameterException(String message, Throwable exception) {
		super(message, exception);
	}
	
	public NullParameterException(String message) {
		super(message);
	}
	
	public NullParameterException(Throwable exception) {
		super(DEFAULT_MESSAGE, exception);
	}
	
}
