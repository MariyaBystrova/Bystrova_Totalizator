package by.tr.totalizator.service;

import java.util.List;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;

/**
 * Represents an interface CouponService, implementation of which provides
 * a proper work with general coupon's services.
 * 
 * @author Mariya Bystrova
 *
 */
public interface CouponService {	
	/**
	 * Registers a new coupon for the system.
	 * <p>
	 * Validates input parameters and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * <p>
	 * Converts a string dates and times values to the
	 * {@link java.sql.Timestamp}.
	 * </p>
	 * 
	 * @param startDate
	 *            a string value for coupon's start date.
	 * @param endDate
	 *            a string value for coupon's end date.
	 * @param minBetAmount
	 *            a value for coupon's minimal bet money amount.
	 * @return a boolean value {@code true} in case of successful registration
	 *         and {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean registerCoupon(String startDate, String endDate, int minBetAmount)
			throws ServiceException, ServiceDataException;

	/**
	 * Updates coupon details.
	 * <p>
	 * Validates input parameter and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param couponDTO
	 *            a value of {@link by.tr.totalizator.entity.dto.MatchDTO} to
	 *            edit.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean editCouponInfo(CouponDTO couponDTO) throws ServiceException, ServiceDataException;

	/**
	 * Deletes the coupon and it's matches.
	 * <p>
	 * Validates input parameter and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param couponId
	 *            a value of coupon's unique identifier pointing a coupon to be
	 *            deleted.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean deleteCoupon(String couponId) throws ServiceException, ServiceDataException;
	
	/**
	 * Provides the correct calculation of winnings.
	 * <p>
	 * Validates input parameter(coupon id must be greater then 0) and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param couponId
	 *            a String value for coupon id.
	 * @return a boolean value {@code true} in case of successful calculation
	 *         and {@code false} otherwise.
	 * @throws NotAllFinishedMatchesServiceException
	 *             if not all matches matching to this coupon has their results.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean closeCoupon(String couponId)
			throws NotAllFinishedMatchesServiceException, ServiceException, ServiceDataException;

	/**
	 * Returns a {@link by.tr.totalizator.entity.bean.Coupon} that is associated
	 * to the coupon's id.
	 * <p>
	 * Validates input parameter(coupon id must be greater then 0) and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param id
	 *            a String value for coupon id.
	 * @return a {@link by.tr.totalizator.entity.bean.Coupon} that is associated
	 *         to the coupon's id.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	Coupon getCouponById(String id) throws ServiceException, ServiceDataException;

	/**
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} that are available in this
	 * data source.
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} that are available
	 *         in this data source.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	List<Coupon> getAllCoupons() throws ServiceException;

	/**
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 * "free"(6) and start date after the current time.
	 * 
	 * @return {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 *         "free"(6) and start date after the current time.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	List<Coupon> getEmptyValidCoupons() throws ServiceException;

	/**
	 * Returns a {@link java.util.List} of
	 * {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 * "open"(1) or "free"(6).
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Coupon} with coupon's status
	 *         "open"(1) or "free"(6).
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	List<Coupon> getCurrentCoupons() throws ServiceException;
	
	/**
	 * Returns minimal bet money amount related to a coupon represented by it's
	 * id.
	 * <p>
	 * Validates input parameter(id must be greater then 0) and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param couponId
	 *            a value for coupon's unique identifier.
	 * @return a value of minimal money amount.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	int getMinBetAmount(int couponId) throws ServiceException, ServiceDataException;

}
