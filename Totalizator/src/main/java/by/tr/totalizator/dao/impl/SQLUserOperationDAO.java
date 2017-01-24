package by.tr.totalizator.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.tr.totalizator.dao.UserOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.bean.User;

/**
 * Represents an implementation of {@link by.tr.totalizator.dao.UserOperationDAO}
 * with a proper realization of all methods for the database.
 * 
 * @author Mariya Bystrova
 *
 */
public class SQLUserOperationDAO implements UserOperationDAO {
	/**
	 * Inserts a new record, representing a new user, into the database.
	 * 
	 * @param user
	 *            a value of user object to be created.
	 * @param password
	 *            a hash value of password.
	 * @return a boolean value <code>true</code>, if user is created and
	 *         <code>false</code>, if registration failed.
	 * @throws DAOException
	 *             if some problems with database or connection pool has
	 *             occur.
	 */
	@Override
	public boolean createUser(User user, String password) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(StatementUser.INSERT_INTO_USER);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getLogin());
			ps.setString(4, password);
			ps.setString(5, user.getSex());
			ps.setString(6, user.getEmail());
			ps.setString(7, user.getCountry());
			ps.setString(8, user.getCity());
			ps.setString(9, user.getAddress());
			ps.setString(10, user.getRole());
			if (ps.executeUpdate() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps);
		}

	}

	/**
	 * Updates the record in the database, representing the particular user.
	 * 
	 * @param user
	 *            a value of user object to be changed.
	 * @return an updated {@link by.tr.totalizator.entity.bean.User} object.
	 * @throws DAOException
	 *             if some problems with database or connection pool has
	 *             occur.
	 */
	@Override
	public User editUserPersonalInfo(User user) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(StatementUser.UPDATE_USER_PERSONAL_DATA);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getSex());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getCountry());
			ps.setString(6, user.getCity());
			ps.setString(7, user.getAddress());
			ps.setInt(8, user.getId());

			if (ps.executeUpdate() != 0) {
				return user;
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps);
		}

	}

	/**
	 * Updates the record in the database, representing the particular user,
	 * tagged by id.
	 * 
	 * @param userId
	 *            a value of user's unique identifier, representing the user to
	 *            be changed.
	 * @param password
	 *            a hash value of password to change.
	 * @return a boolean value <code>true</code> in case of successful edit and
	 *         <code>false</code> otherwise.
	 * @throws DAOException
	 *             if some problems with database or connection pool has
	 *             occur.
	 */
	@Override
	public boolean editUserAccountInfo(int userId, String password) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(StatementUser.UPDATE_USER_ACCOUNT_DATA);
			ps.setString(1, password);
			ps.setInt(2, userId);

			if (ps.executeUpdate() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps);
		}
	}

	/**
	 * Checks the presence of tandem "user-password" in the database.
	 * 
	 * @param login
	 *            a value of login.
	 * @param password
	 *            a value of password.
	 * @return a {@link by.tr.totalizator.entity.bean.User} object representing
	 *         the entered login and password in the system.
	 * @throws DAOException
	 *             if some problems with database or connection pool has
	 *             occur.
	 */
	@Override
	public User authentication(String login, String password) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		User user = null;
		try {
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(StatementUser.SELECT_USER_WHERE_LOGIN_PASSWORD);
			ps.setString(1, login);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.first()) { // if there was a result set line
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
			}

		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps, rs);
		}
		return user;
	}
}