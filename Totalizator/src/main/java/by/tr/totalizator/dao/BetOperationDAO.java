package by.tr.totalizator.dao;

import java.util.Map;

import by.tr.totalizator.dao.exception.DAOException;

/**
 * Represents an interface CouponOperationDAO, implementation of which
 * provides a proper work with the specific data source.
 * 
 * @author Mariya Bystrova
 *
 */
public interface BetOperationDAO {
	/**
	 * Inserts new records, representing a bet, into the data source.
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
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	boolean registerBet(Map<String, String> resultMap, int amount, String creditCardNumber, int userId, int couponId)
			throws DAOException;
}
