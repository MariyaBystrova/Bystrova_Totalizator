package by.tr.totalizator.service;

import java.util.List;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;

/**
 * Represents an interface TotalizatorService, implementation of which provides
 * a proper work with general toto's services.
 * 
 * @author Mariya Bystrova
 *
 */
public interface TotalizatorService {
	/**
	 * Returns all matches related to a coupon represented by it's id.
	 * <p>
	 * Validates input parameter and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param cuponId
	 *            a value for coupon's unique identifier.
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Match} related to the coupon
	 *         id.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	List<Match> getCuponMatches(int cuponId) throws ServiceException, ServiceDataException;

	/**
	 * Returns all matches related to the current coupon.
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Match} for current coupon.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	List<Match> getCurrentCupon() throws ServiceException;

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
	 * Registers a new bet.
	 * <p>
	 * Validates input parameter and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param bean
	 *            a value of {@link by.tr.totalizator.entity.dto.RegisterBetDTO}
	 *            with data for making a bet.
	 * @return a boolean value {@code true} in case of successful registration
	 *         and {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean registerBet(RegisterBetDTO bean) throws ServiceException, ServiceDataException;

	/**
	 * Registers a new match.
	 * <p>
	 * Validates input parameter and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param matchDTO
	 *            a value of {@link by.tr.totalizator.entity.dto.MatchDTO} to
	 *            register a new match.
	 * @return a boolean value {@code true} in case of successful registration
	 *         and {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean registerMatch(MatchDTO matchDTO) throws ServiceException, ServiceDataException;

	/**
	 * Updates match's details.
	 * <p>
	 * Validates input parameter and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param matchDTO
	 *            a value of {@link by.tr.totalizator.entity.dto.MatchDTO} to
	 *            edit.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean editMatch(MatchDTO matchDTO) throws ServiceException, ServiceDataException;

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
	 * Updates match result and might change the dates.
	 * <p>
	 * Validates input parameter and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * 
	 * @param matchDTO
	 *            a value of {@link by.tr.totalizator.entity.dto.MatchDTO} to
	 *            edit.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean editMatchResStatus(MatchDTO matchDTO) throws ServiceException, ServiceDataException;

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
}
