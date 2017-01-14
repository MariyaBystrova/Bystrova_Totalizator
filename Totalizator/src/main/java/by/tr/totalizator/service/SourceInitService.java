package by.tr.totalizator.service;

import by.tr.totalizator.service.exception.ServiceException;

public interface SourceInitService {
	public void init() throws ServiceException;
	public void destroy();
}
