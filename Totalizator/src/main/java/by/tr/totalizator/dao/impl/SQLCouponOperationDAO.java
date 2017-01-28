package by.tr.totalizator.dao.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.tr.totalizator.dao.CouponOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.exception.NotAllFinishedMatchesDAOException;
import by.tr.totalizator.entity.bean.Coupon;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.dao.CouponOperationDAO} interface with a proper
 * realization of all methods for the database.
 * 
 * @author Mariya Bystrova
 *
 */
public class SQLCouponOperationDAO implements CouponOperationDAO {

	/**
	 * Inserts a new record, representing a new coupon, into the database.
	 * 
	 * @param startDate
	 *            a {@link java.sql.Timestamp} value for coupon's start date and
	 *            time.
	 * @param endDate
	 *            a {@link java.sql.Timestamp} value for coupon's end date and
	 *            time.
	 * @param minBetAmount
	 *            a value for coupon's minimal bet money amount.
	 * @return a boolean value {@code true} in case of successful registration
	 *         and {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public boolean registerCoupon(Timestamp startDate, Timestamp endDate, int minBetAmount) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.INSERT_INTO_COUPON)) {
				ps.setTimestamp(1, startDate);
				ps.setTimestamp(2, endDate);
				ps.setInt(3, minBetAmount);
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
	 * Updates the record in the database, representing the particular Coupon.
	 * 
	 * @param coupon
	 *            a value of {@link by.tr.totalizator.entity.bean.Coupon} object
	 *            to be changed.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public boolean editCouponInfo(Coupon coupon) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.UPDATE_COUPON)) {
				ps.setTimestamp(1, coupon.getStartDate());
				ps.setTimestamp(2, coupon.getEndDate());
				ps.setInt(3, coupon.getMinBetAmount());
				ps.setInt(4, coupon.getPull());
				ps.setInt(5, coupon.getJackpot());
				ps.setInt(6, coupon.getStatus());
				ps.setInt(7, coupon.getId());
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
	 * Deletes the coupon and it's matches from database.
	 * 
	 * @param couponId
	 *            a value of coupon's unique identifier pointing a coupon to be
	 *            deleted.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	@Override
	public boolean deleteCoupon(int couponId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();

			con.setAutoCommit(false);

			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.DELETE_COUPON_MATCHES)) {
				ps.setInt(1, couponId);
				ps.executeUpdate();
			} catch (SQLException e) {
				con.rollback();
				throw new DAOException("Database access error.", e);
			}

			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.DELETE_COUPON)) {
				ps.setInt(1, couponId);
				int rows = ps.executeUpdate();

				if (rows <= 0) {
					con.rollback();
					return false;
				}
			} catch (SQLException e) {
				con.rollback();
				throw new DAOException("Database access error.", e);
			}

			con.commit();
			return true;

		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
	}

	/**
	 * Provides all necessary changes of the database. Representing the correct
	 * calculation of winnings.
	 * 
	 * @param couponId
	 *            a value of unique identifier, representing the coupon to be
	 *            closed.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws NotAllFinishedMatchesDAOException
	 *             if not all matches matching to this coupon has their results.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public boolean closeCoupon(int couponId) throws NotAllFinishedMatchesDAOException, DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (CallableStatement cs = con.prepareCall(StatementTotalizator.CLOSE_COUPON_PROCEDURE)) {
				cs.setInt(1, couponId);
				cs.registerOutParameter(2, java.sql.Types.INTEGER);
				cs.execute();
				int result = cs.getInt(2);
				if (result == -1) {
					return false;
				}
				if (result == -2) {
					throw new NotAllFinishedMatchesDAOException("Not all matches has finished yet.");
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return true;
	}

	/**
	 * Returns a {@link by.tr.totalizator.entity.bean.Coupon} that is associated
	 * to the coupon's id.
	 * 
	 * @param id
	 *            a value of the coupon's unique identifier.
	 * @return a {@link by.tr.totalizator.entity.bean.Coupon} that is associated
	 *         to the coupon's id.
	 * 
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public Coupon getCouponById(int id) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.SELECT_COUPON_BY_ID)) {
				ps.setInt(1, id);
				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					Coupon coupon = new Coupon(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4),
							rs.getInt(5), rs.getInt(6), rs.getInt(7));
					return coupon;
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}

	}

	/**
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} that are available in this
	 * data source.
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} that are available
	 *         in this data source.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public List<Coupon> getAllCoupons() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		List<Coupon> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(StatementTotalizator.SELECT_ALL_FROM_COUPON)) {
					while (rs.next()) {
						Coupon coupon = new Coupon(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4),
								rs.getInt(5), rs.getInt(6), rs.getInt(7));
						list.add(coupon);
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
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 * "free"(6) and start date after the current time.
	 * 
	 * @return {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 *         "free"(6) and start date after the current time.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public List<Coupon> getEmptyValidCoupons() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		List<Coupon> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(StatementTotalizator.SELECT_FREE_VALID_COUPONS)) {
					while (rs.next()) {
						Coupon coupon = new Coupon(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4),
								rs.getInt(5), rs.getInt(6), rs.getInt(7));
						list.add(coupon);
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
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 * "open"(1) or "free"(6).
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 *         "open"(1) or "free"(6).
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public List<Coupon> getCurrentCoupons() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		List<Coupon> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(StatementTotalizator.SELECT_NOT_CLOSED_COUPONS)) {
					while (rs.next()) {
						Coupon coupon = new Coupon(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4),
								rs.getInt(5), rs.getInt(6), rs.getInt(7));
						list.add(coupon);
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
	 * Returns minimal bet money amount related to a coupon represented by it's
	 * id.
	 * 
	 * @param couponId
	 *            a value for coupon's unique identifier.
	 * @return a value of minimal money amount.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public int getMinBetAmount(int couponId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.SELECT_MIN_BET_AMOUNT_BY_COUPONID)) {
				ps.setInt(1, couponId);

				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
	}
}
