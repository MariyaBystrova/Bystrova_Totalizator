package by.tr.totalizator.dao.impl;

import by.tr.totalizator.dao.SourceInitDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;

/**
 * Represents an implementation of {@link by.tr.totalizator.dao.SourceInitDAO}
 * interface with a proper initialization of the connection pool to the
 * database.
 * 
 * @author Mariya Bystrova
 *
 */
public class SQLInit implements SourceInitDAO {
	/**
	 * Provides a proper initialization of the connection pool to the database.
	 */
	@Override
	public void init() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e) {
			throw new DAOException("Problems with initializing connection pool", e);
		}

	}

	/**
	 * Provides the proper destruction of connection.
	 */
	@Override
	public void destroy() {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.dispose();
	}

}
