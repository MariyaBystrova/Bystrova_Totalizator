package by.tr.totalizator.dao.exception;

/**
 * Thrown when an exceptional situation with match results has occurred. For
 * example, not all matches matching to this coupon has their results.
 * 
 * @author Mariya Bystrova
 *
 */
public class NotAllFinishedMatchesDAOException extends DAOException {
	private static final long serialVersionUID = 1L;

	public NotAllFinishedMatchesDAOException() {
		super();
	}

	public NotAllFinishedMatchesDAOException(String message) {
		super(message);
	}

	public NotAllFinishedMatchesDAOException(Exception e) {
		super(e);
	}

	public NotAllFinishedMatchesDAOException(String message, Exception e) {
		super(message, e);
	}
}
