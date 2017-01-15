package by.tr.totalizator.service.exception;

public class NotAllFinishedMatchesServiceException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public NotAllFinishedMatchesServiceException(){
		super();
	}
	
	public NotAllFinishedMatchesServiceException(String message){
		super(message);
	}	
	
	public NotAllFinishedMatchesServiceException(Exception e){
		super(e);
	}
	
	public NotAllFinishedMatchesServiceException(String message, Exception e){
		super(message, e);
	}
}
