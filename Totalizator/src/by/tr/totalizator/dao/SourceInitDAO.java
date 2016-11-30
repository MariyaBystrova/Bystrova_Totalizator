package by.tr.totalizator.dao;

import by.tr.totalizator.dao.exception.DAOException;

public interface SourceInitDAO {
	public void init() throws DAOException;
	public void destroy();
}
