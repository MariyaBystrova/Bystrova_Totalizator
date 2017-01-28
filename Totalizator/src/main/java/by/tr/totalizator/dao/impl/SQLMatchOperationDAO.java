package by.tr.totalizator.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.tr.totalizator.dao.MatchOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.bean.Match;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.dao.MatchOperationDAO} interface with a proper
 * realization of all methods for the database.
 * 
 * @author Mariya Bystrova
 *
 */
public class SQLMatchOperationDAO implements MatchOperationDAO {

	/**
	 * Inserts a new record, representing a new match, into the database.
	 * 
	 * @param match
	 *            a value of {@link by.tr.totalizator.entity.bean.Match} object
	 *            to be created.
	 * @return a boolean value {@code true} in case of successful registration
	 *         and {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public boolean registerMatch(Match match) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.INSERT_INTO_MATCH)) {
				ps.setString(1, match.getName());
				ps.setInt(2, match.getCouponId());
				ps.setString(3, match.getTeamOne());
				ps.setString(4, match.getTeamTwo());
				ps.setTimestamp(5, match.getStartDate());
				ps.setTimestamp(6, match.getEndDate());
				if (ps.executeUpdate() != 0) {
					return true;
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return false;
	}

	/**
	 * Updates the record in the database, representing the particular match.
	 * 
	 * @param match
	 *            a value of {@link by.tr.totalizator.entity.bean.Match} object
	 *            to be changed.
	 * @return a boolean value {@code true} in case of successful update and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public boolean editMatch(Match match) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.UPDATE_MATCH)) {
				ps.setString(1, match.getName());
				ps.setInt(2, match.getId());
				ps.setString(3, match.getTeamOne());
				ps.setString(4, match.getTeamTwo());
				ps.setTimestamp(5, match.getStartDate());
				ps.setTimestamp(6, match.getEndDate());
				if (ps.executeUpdate() != 0) {
					return true;
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return false;
	}

	/**
	 * Updates the record in the database, representing the particular match.
	 * SQL query includes the control of input parameters.
	 * <p>
	 * Checks that match's start date is not before coupon's end date and not
	 * after two days from it's end date. Also checks an execution of one item
	 * of the following:
	 * </p>
	 * <p>
	 * 1. Match's end date has passed, status is 5(finished) and the result is
	 * '1', '2' or 'x';
	 * </p>
	 * <p>
	 * 2. Match's end date has passed, status is 4(cancelled) and the result if
	 * {@code NULL};
	 * </p>
	 * <p>
	 * 3. Match's end date is not finished yet, status is 2(in progress) or
	 * 4(cancelled) and the result is NULL
	 * </p>
	 * 
	 * @param match
	 *            a value of {@link by.tr.totalizator.entity.bean.Match} object
	 *            to be changed.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public boolean editMatchResult(Match match) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.UPDATE_MATCH_DATES_RESULT_STATUS)) {
				ps.setInt(1, match.getId());
				ps.setTimestamp(2, match.getStartDate());
				ps.setTimestamp(3, match.getEndDate());
				ps.setInt(4, match.getStatus());
				ps.setString(5, match.getResult());
				if (ps.executeUpdate() != 0) {
					return true;
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return false;
	}
	
	/**
	 * Returns all matches related to a coupon represented by it's id.
	 * 
	 * @param couponId
	 *            a value for coupon's unique identifier.
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Match} related to coupon id.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public List<Match> getCouponMatches(int couponId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		List<Match> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.SELECT_MATCHES_WHERE_CUPONID)) {
				ps.setInt(1, couponId);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Match match = new Match(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
								rs.getString(5), rs.getTimestamp(6), rs.getTimestamp(7));
						match.setResult(rs.getString(8));
						match.setStatus(rs.getInt(9));
						list.add(match);
					}
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return list;
	}

	/**
	 * Returns all matches related to the current coupon.
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Match} for current coupon.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public List<Match> getCurrentCouponMatches() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		List<Match> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(StatementTotalizator.SELECT_CURRENT_COUPON_MATCHES)) {

					while (rs.next()) {
						Match match = new Match(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
								rs.getString(5), rs.getTimestamp(6), rs.getTimestamp(7));
						match.setResult(rs.getString(8));
						match.setStatus(rs.getInt(9));

						list.add(match);
					}
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return list;
	}


}
