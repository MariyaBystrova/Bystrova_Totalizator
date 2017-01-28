package by.tr.totalizator.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.tr.totalizator.dao.BetOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.dao.BetOperationDAO} interface with a proper
 * realization of all methods for the database.
 * 
 * @author Mariya Bystrova
 *
 */
public class SQLBetOperationDAO implements BetOperationDAO {
	private final static String RESULT = "result";

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
}
