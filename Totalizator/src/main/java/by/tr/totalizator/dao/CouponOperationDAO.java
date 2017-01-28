package by.tr.totalizator.dao;

import java.sql.Timestamp;
import java.util.List;

import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.exception.NotAllFinishedMatchesDAOException;
import by.tr.totalizator.entity.bean.Coupon;

/**
 * Represents an interface CouponOperationDAO, implementation of which
 * provides a proper work with the specific data source.
 * 
 * @author Mariya Bystrova
 *
 */
public interface CouponOperationDAO {

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

}
