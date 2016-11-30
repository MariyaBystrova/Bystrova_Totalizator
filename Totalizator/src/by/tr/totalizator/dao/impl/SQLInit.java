package by.tr.totalizator.dao.impl;

import by.tr.totalizator.dao.SourceInitDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;

public class SQLInit implements SourceInitDAO {

	@Override
	public void init() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e) {
			throw new DAOException("Problems with initializing connection pool", e);
		}

	}

	@Override
	public void destroy() {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.dispose();
	}

}
