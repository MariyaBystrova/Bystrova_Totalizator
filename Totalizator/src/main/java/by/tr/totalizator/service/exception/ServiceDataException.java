package by.tr.totalizator.service.exception;

public class ServiceDataException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceDataException() {
		super();
	}

	public ServiceDataException(String message) {
		super(message);
	}

	public ServiceDataException(Exception e) {
		super(e);
	}

	public ServiceDataException(String message, Exception e) {
		super(message, e);
	}
}
