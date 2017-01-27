package by.tr.totalizator.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.exception.NotAllFinishedMatchesDAOException;
import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;

/**
 * Represents an interface TotalizatorOperationDAO, implementation of which
 * provides a proper work with the specific data source.
 * 
 * @author Mariya Bystrova
 *
 */
public interface TotalizatorOperationDAO {
	/**
	 * Returns all matches related to a coupon represented by it's id.
	 * 
	 * @param couponId
	 *            a value for coupon's unique identifier.
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Match} related to coupon id.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	List<Match> getCouponMatches(int couponId) throws DAOException;

	/**
	 * Returns all matches related to the current coupon.
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Match} for current coupon.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	List<Match> getCurrentCouponMatches() throws DAOException;

	/**
	 * Returns minimal bet money amount related to a coupon represented by it's
	 * id.
	 * 
	 * @param couponId
	 *            a value for coupon's unique identifier.
	 * @return a value of minimal money amount.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	int getMinBetAmount(int couponId) throws DAOException;

	/**
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 * "free"(6) and start date after the current time.
	 * 
	 * @return {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 *         "free"(6) and start date after the current time.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	List<Coupon> getEmptyValidCoupons() throws DAOException;

	/**
	 * Inserts a new record, representing a new coupon, into the data source.
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
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	boolean registerCoupon(Timestamp startDate, Timestamp endDate, int minBetAmount) throws DAOException;

	/**
	 * Inserts a new record, representing a new match, into the data source.
	 * 
	 * @param match
	 *            a value of {@link by.tr.totalizator.entity.bean.Match} object
	 *            to be created.
	 * @return a boolean value {@code true} in case of successful registration
	 *         and {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	boolean registerMatch(Match match) throws DAOException;

	/**
	 * Updates the record in the data source, representing the particular match.
	 * 
	 * @param match
	 *            a value of {@link by.tr.totalizator.entity.bean.Match} object
	 *            to be changed.
	 * @return a boolean value {@code true} in case of successful update and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	boolean editMatch(Match match) throws DAOException;

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

	/**
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 * "open"(1) or "free"(6).
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 *         "open"(1) or "free"(6).
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	List<Coupon> getCurrentCoupons() throws DAOException;

	/**
	 * Updates the record in the data source, representing the particular match.
	 * 
	 * @param match
	 *            a value of {@link by.tr.totalizator.entity.bean.Match} object
	 *            to be changed.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	boolean editMatchResult(Match match) throws DAOException;

	/**
	 * Provides all necessary changes of the data source. Representing the
	 * correct calculation of winnings.
	 * 
	 * @param couponId
	 *            a value of unique identifier, representing the coupon to be
	 *            closed.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws NotAllFinishedMatchesDAOException
	 *             if not all matches matching to this coupon has their results.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	boolean closeCoupon(int couponId) throws NotAllFinishedMatchesDAOException, DAOException;

	/**
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} that are available in this
	 * data source.
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} that are available
	 *         in this data source.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	List<Coupon> getAllCoupons() throws DAOException;

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
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	Coupon getCouponById(int id) throws DAOException;

	/**
	 * Updates the record in the data source, representing the particular
	 * Coupon.
	 * 
	 * @param coupon
	 *            a value of {@link by.tr.totalizator.entity.bean.Coupon} object
	 *            to be changed.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if some problems with data source or connection pool has
	 *             occur.
	 */
	boolean editCouponInfo(Coupon coupon) throws DAOException;

	/**
	 * Deletes the coupon and it's matches from the data source.
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
	boolean deleteCoupon(int couponId) throws DAOException;
}
