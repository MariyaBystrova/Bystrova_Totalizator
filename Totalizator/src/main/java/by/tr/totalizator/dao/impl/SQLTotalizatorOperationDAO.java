package by.tr.totalizator.dao.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.tr.totalizator.dao.TotalizatorOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.exception.NotAllFinishedMatchesDAOException;
import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.dao.TotalizatorOperationDAO} interface with a proper
 * realization of all methods for the database.
 * 
 * @author Mariya Bystrova
 *
 */
public class SQLTotalizatorOperationDAO implements TotalizatorOperationDAO {
	private final static String RESULT = "result";

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
	 * Inserts new records, representing a bet, into the database.
	 * 
	 * @param resultMap
	 *            a value of {@link java.util.Map} representing bet's
	 *            chooses(results).
	 * @param amount
	 *            a value of money amount that is set by this bet.
	 * @param creditCardNumber
	 *            a value of credit card number from which goes the money
	 *            transfer.
	 * @param userId
	 *            a value of user's unique identifier, representing the user,
	 *            which did this bet.
	 * @param couponId
	 *            a value of coupon's unique identifier, representing the coupon
	 *            to make bet to.
	 * @return a boolean value {@code true} in case of successful registration
	 *         and {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with database or connection pool has occur.
	 */
	@Override
	public boolean registerBet(Map<String, String> resultMap, int amount, String creditCardNumber, int userId,
			int couponId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;

		try {
			con = connectionPool.takeConnection();
			con.setAutoCommit(false);

			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.INSERT_INTO_BET)) {
				ps.setInt(1, userId);
				ps.setInt(2, couponId);
				ps.setInt(3, amount);
				ps.setString(4, creditCardNumber);
				ps.executeUpdate();
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					throw new DAOException("Database rollback error.", e1);
				}
				throw new DAOException("Database access error.", e);
			}

			int betId = -1;
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(StatementTotalizator.LAST_INSERTED_ID)) {
					rs.next();
					betId = rs.getInt(1);
				}
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					throw new DAOException("Database rollback error.", e1);
				}
				throw new DAOException("Database access error.", e);
			}

			List<Integer> matchId = new ArrayList<Integer>();
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(StatementTotalizator.SELECT_CURRENT_COUPON_MATCHES)) {
					while (rs.next()) {
						matchId.add(rs.getInt(1));
					}
				}
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					throw new DAOException("Database rollback error.", e1);
				}
				throw new DAOException("Database access error.", e);
			}

			for (int i = 0; i < matchId.size(); i++) {
				try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.INSERT_INTO_USER_BET_DETAIL)) {
					ps.setInt(1, betId);
					ps.setInt(2, matchId.get(i));
					ps.setString(3, resultMap.get(RESULT + new Integer(i + 1).toString()));
					ps.executeUpdate();
				} catch (SQLException e) {
					try {
						con.rollback();
					} catch (SQLException e1) {
						throw new DAOException("Database rollback error.", e1);
					}
					throw new DAOException("Database access error.", e);
				}
			}

			con.commit();
		} catch (SQLException | ConnectionPoolException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Database rollback error.", e1);
			}
			throw new DAOException("Database access error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
		return false;
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

			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.DELETE_COUPON_MATCHES)) {
				ps.setInt(1, couponId);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}

			try (PreparedStatement ps = con.prepareStatement(StatementTotalizator.DELETE_COUPON)) {
				ps.setInt(1, couponId);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException("Database access error.", e);
			}

			return true;
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con);
		}
	}

}
