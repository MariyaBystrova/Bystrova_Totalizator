package by.tr.totalizator.service.impl;

import by.tr.totalizator.dao.SourceInitDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.service.SourceInitService;
import by.tr.totalizator.service.exception.ServiceException;

public class SourceInit implements SourceInitService {

	@Override
	public void init() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		SourceInitDAO sourceDAO = factory.getSourceInitDAO();
		try {
			sourceDAO.init();
		} catch (DAOException e) {
			throw new ServiceException("Init source failed", e);
		}
	}

	@Override
	public void destroy() {
		DAOFactory factory = DAOFactory.getInstance();
		SourceInitDAO sourceDAO = factory.getSourceInitDAO();
		sourceDAO.destroy();
	}

}
