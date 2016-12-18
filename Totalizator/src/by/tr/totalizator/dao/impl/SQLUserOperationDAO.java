package by.tr.totalizator.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.tr.totalizator.dao.UserOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.User;

public class SQLUserOperationDAO implements UserOperationDAO {

	private final static String SELECT_USER_WHERE_LOGIN_PASSWORD = "SELECT `user_id`,`first_name`, `last_name`, `sex`, `e-mail`, `country`, `city`, `address`, `login`, `role` FROM `user`  WHERE `login`=? AND `password`=?;";
	private final static String INSERT_INTO_USER = "INSERT INTO `user` (`first_name`,`last_name`,`login`,`password`,`sex`,`e-mail`,`country`,`city`,`address`,`role`)VALUES (?,?,?,?,?,?,?,?,?,?);";
	private final static String UPDATE_USER_PERSONAL_DATA = "UPDATE `user` SET `first_name` = ?,`last_name` = ?,`sex` = ?,`e-mail` = ?,`country` = ?,`city` = ?,`address` = ? WHERE `user_id` = ?;";
	private final static String UPDATE_USER_ACCOUNT_DATA = "UPDATE `user` SET `password` = ? WHERE `user_id` = ?;";
	
	@Override
	public boolean createUser(User user, String password) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(INSERT_INTO_USER);
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

	@Override
	public User editUserPersonalInfo(User user) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(UPDATE_USER_PERSONAL_DATA);
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

	@Override
	public boolean editUserAccountInfo(int id, String password) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();

			ps = con.prepareStatement(UPDATE_USER_ACCOUNT_DATA);
			ps.setString(1, password);
			ps.setInt(2, id);
			
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

	@Override
	public User authentication(String login, String password) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		User user = null;
		try {
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(SELECT_USER_WHERE_LOGIN_PASSWORD);
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