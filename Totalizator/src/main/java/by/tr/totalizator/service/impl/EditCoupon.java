package by.tr.totalizator.service.impl;

import java.sql.Timestamp;
import java.util.List;

import by.tr.totalizator.dao.CouponOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.exception.NotAllFinishedMatchesDAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.service.CouponService;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.Validator;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.service.CouponService}.
 * 
 * @author Mariya Bystrova
 *
 */
public class EditCoupon implements CouponService {

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
	@Override
	public boolean registerCoupon(String startDate, String endDate, int minBetAmount)
			throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		startDate = formatDate(startDate);
		endDate = formatDate(endDate);

		if (Validator.validateCoupon(startDate, endDate, minBetAmount)) {
			start = Timestamp.valueOf(startDate);
			end = Timestamp.valueOf(endDate);
		} else {
			throw new ServiceDataException("Invalid data.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();
		boolean result = false;

		if (start.before(end)) {
			try {
				result = couponDAO.registerCoupon(start, end, minBetAmount);
			} catch (DAOException e) {
				throw new ServiceException("Register coupon failed.", e);
			}
		}

		return result;
	}
	
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
	@Override
	public boolean editCouponInfo(CouponDTO couponDTO) throws ServiceException, ServiceDataException {
		couponDTO.setStartDate(formatDate(couponDTO.getStartDate()));
		couponDTO.setEndDate(formatDate(couponDTO.getEndDate()));

		if (!Validator.validateCoupon(couponDTO)) {
			throw new ServiceDataException("Invalid data.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();

		Timestamp start = Timestamp.valueOf(couponDTO.getStartDate());
		Timestamp end = Timestamp.valueOf(couponDTO.getEndDate());
		if (start.after(end)) {
			throw new ServiceDataException("Invalid dates: start date is after end date");
		}
		try {
			Coupon couponEntity = new Coupon();
			couponEntity.setId(Integer.parseInt(couponDTO.getId()));
			couponEntity.setPool(Integer.parseInt(couponDTO.getPool()));
			couponEntity.setJackpot(Integer.parseInt(couponDTO.getJackpot()));
			couponEntity.setMinBetAmount(Integer.parseInt(couponDTO.getMinBetAmount()));
			couponEntity.setStatus(Integer.parseInt(couponDTO.getStatus()));
			couponEntity.setStartDate(start);
			couponEntity.setEndDate(end);

			boolean result = couponDAO.editCouponInfo(couponEntity);
			return result;
		} catch (NumberFormatException e) {
			throw new ServiceDataException("Invalid data.");
		} catch (DAOException e) {
			throw new ServiceException("Edit coupon failed.", e);
		}
	}

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
	@Override
	public boolean deleteCoupon(String couponId) throws ServiceException, ServiceDataException {
		if (!Validator.validateCouponId(couponId)) {
			throw new ServiceDataException("Invalid data.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();

		boolean result = false;
		try {
			result = couponDAO.deleteCoupon(Integer.valueOf(couponId));
		} catch (NumberFormatException e) {
			throw new ServiceDataException("Invalid data.");
		} catch (DAOException e) {
			throw new ServiceException("Delete coupon failed.", e);
		}
		return result;
	}

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
	@Override
	public boolean closeCoupon(String couponId)
			throws NotAllFinishedMatchesServiceException, ServiceException, ServiceDataException {
		boolean result = Validator.validateCouponId(couponId);
		if (!result) {
			throw new ServiceDataException("Invalid data.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();

		try {
			result = couponDAO.closeCoupon(Integer.parseInt(couponId));
		} catch (NumberFormatException e) {
			throw new ServiceDataException("Invalid data.");
		} catch (NotAllFinishedMatchesDAOException e) {
			throw new NotAllFinishedMatchesServiceException("Close coupon failed.", e);
		} catch (DAOException e) {
			throw new ServiceException("Close coupon failed.", e);
		}
		return result;
	}

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
	@Override
	public Coupon getCouponById(String id) throws ServiceException, ServiceDataException {
		if (!Validator.validateCouponId(id)) {
			throw new ServiceDataException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();
		try {
			Coupon coupon = couponDAO.getCouponById(Integer.parseInt(id));
			return coupon;
		} catch (NumberFormatException e) {
			throw new ServiceDataException("Invalid data.");
		} catch (DAOException e) {
			throw new ServiceException("Get coupon failed.", e);
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
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	@Override
	public List<Coupon> getAllCoupons() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();

		List<Coupon> list = null;
		try {
			list = couponDAO.getAllCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get all coupons failed.", e);
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
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	@Override
	public List<Coupon> getEmptyValidCoupons() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();

		List<Coupon> list = null;
		try {
			list = couponDAO.getEmptyValidCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get empty valid coupons failed.", e);
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
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	@Override
	public List<Coupon> getCurrentCoupons() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();
		List<Coupon> list;
		try {
			list = couponDAO.getCurrentCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get current coupons failed.", e);
		}
		return list;
	}

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
	@Override
	public int getMinBetAmount(int couponId) throws ServiceException, ServiceDataException {
		if (couponId <= 0) {
			throw new ServiceDataException("CouponId must be greater then 0.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		CouponOperationDAO couponDAO = factory.getCouponOperationDAO();

		try {
			return couponDAO.getMinBetAmount(couponId);
		} catch (DAOException e) {
			throw new ServiceException("Get min bet amount failed.", e);
		}
	}

	
	/**
	 * Changes the format of date and time string. Replaces the char ' ' to 'T'
	 * and string "%3A" to ":". Concatenates the string with ":00".
	 * 
	 * @param date
	 *            a String value of date and time to be changed.
	 * @return a formatting date and time String.
	 */
	private String formatDate(String date) {
		date = date.replace('T', ' ');
		date = date.replaceAll("%3A", ":");
		date = date.concat(":00");
		return date;
	}

}
