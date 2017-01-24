package by.tr.totalizator.service.exception;

/**
 * Thrown when an exceptional situation with match results has occurred. For
 * example, not all matches matching to this coupon has their results.
 * 
 * @author Mariya Bystrova
 *
 */
public class NotAllFinishedMatchesServiceException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public NotAllFinishedMatchesServiceException() {
		super();
	}

	public NotAllFinishedMatchesServiceException(String message) {
		super(message);
	}

	public NotAllFinishedMatchesServiceException(Exception e) {
		super(e);
	}

	public NotAllFinishedMatchesServiceException(String message, Exception e) {
		super(message, e);
	}
}
