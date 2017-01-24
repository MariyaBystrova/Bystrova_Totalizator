package by.tr.totalizator.service.impl;

import java.sql.Timestamp;
import java.util.List;

import by.tr.totalizator.dao.TotalizatorOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.exception.NotAllFinishedMatchesDAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.Validator;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.service.TotalizatorService}.
 * 
 * @author Mariya Bystrova
 *
 */
public class EditTotalizator implements TotalizatorService {
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
	@Override
	public List<Match> getCuponMatches(int cuponId) throws ServiceException, ServiceDataException {
		if (cuponId <= 0) {
			throw new ServiceDataException("Invalid data: cupon_id must be greater then 0.");
		}

		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Match> list = null;
		try {
			list = totoDAO.getCuponMatches(cuponId);
		} catch (DAOException e) {
			throw new ServiceException("Get coupon matches failed.", e);
		}
		return list;
	}

	/**
	 * Returns all matches related to the current coupon.
	 * 
	 * @return a {@link java.util.List} of
	 *         {@link by.tr.totalizator.entity.bean.Match} for current coupon.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	@Override
	public List<Match> getCurrentCupon() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Match> list = null;
		try {
			list = totoDAO.getCurrentCouponMatches();
		} catch (DAOException e) {
			throw new ServiceException("Get coupon matches failed.", e);
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
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		try {
			return totoDAO.getMinBetAmount(couponId);
		} catch (DAOException e) {
			throw new ServiceException("Get min bet amount failed.", e);
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
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 */
	@Override
	public List<Coupon> getEmptyValidCoupons() throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Coupon> list = null;
		try {
			list = totoDAO.getEmptyValidCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get empty valid coupons failed.", e);
		}
		return list;
	}

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
	 * @return a boolean value <code>true</code>in case of successful
	 *         registration and <code>false</code> otherwise.
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
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;

		if (start.before(end)) {
			try {
				result = totoDAO.registerCoupon(start, end, minBetAmount);
			} catch (DAOException e) {
				throw new ServiceException("Register coupon failed.", e);
			}
		}

		return result;
	}

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
	 * @return a boolean value <code>true</code>in case of successful
	 *         registration and <code>false</code> otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public boolean registerBet(RegisterBetDTO bean) throws ServiceException, ServiceDataException {

		boolean result = Validator.validateBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(),
				bean.getUserId(), bean.getCouponId());
		if (!result) {
			throw new ServiceDataException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		try {
			result = totoDAO.registerBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(), bean.getUserId(),
					Integer.parseInt(bean.getCouponId()));
		} catch (DAOException e) {
			throw new ServiceException("Register bet failed.", e);
		}
		return result;
	}

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
	 * @return a boolean value <code>true</code>in case of successful
	 *         registration and <code>false</code> otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public boolean registerMatch(MatchDTO matchDTO) throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		matchDTO.setStartDate(formatDate(matchDTO.getStartDate()));
		matchDTO.setEndDate(formatDate(matchDTO.getEndDate()));

		if (Validator.validateMatch(matchDTO)) {
			start = Timestamp.valueOf(matchDTO.getStartDate());
			end = Timestamp.valueOf(matchDTO.getEndDate());
		} else {
			throw new ServiceDataException("Invalid data.");
		}

		Match matchEntity = new Match(matchDTO.getName(), matchDTO.getTeamOne(), matchDTO.getTeamTwo(), start, end);

		if (matchDTO.getCouponId() != null) {
			matchEntity.setCouponId(Integer.parseInt(matchDTO.getCouponId()));
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = totoDAO.registerMatch(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
		}

		return result;
	}

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
	 * @return a boolean value <code>true</code>in case of successful edit and
	 *         <code>false</code> otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public boolean editMatch(MatchDTO matchDTO) throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		matchDTO.setStartDate(formatDate(matchDTO.getStartDate()));
		matchDTO.setEndDate(formatDate(matchDTO.getEndDate()));

		if (Validator.validateMatch(matchDTO)) {
			start = Timestamp.valueOf(matchDTO.getStartDate());
			end = Timestamp.valueOf(matchDTO.getEndDate());
		} else {
			throw new ServiceDataException("Invalid data.");
		}

		Match matchEntity = new Match(matchDTO.getName(), matchDTO.getTeamOne(), matchDTO.getTeamTwo(), start, end);

		if (matchDTO.getId() != null) {
			matchEntity.setId(Integer.parseInt(matchDTO.getId()));
		}
		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = totoDAO.editMatch(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
		}
		return result;
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
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		List<Coupon> list;
		try {
			list = totoDAO.getCurrentCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get current coupons failed.", e);
		}
		return list;
	}

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
	 * @return a boolean value <code>true</code>in case of successful edit and
	 *         <code>false</code> otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public boolean editMatchResStatus(MatchDTO matchDTO) throws ServiceException, ServiceDataException {
		Timestamp start;
		Timestamp end;

		matchDTO.setStartDate(formatDate(matchDTO.getStartDate()));
		matchDTO.setEndDate(formatDate(matchDTO.getEndDate()));

		if (Validator.validateMatchDatesResultStatus(matchDTO)) {
			start = Timestamp.valueOf(matchDTO.getStartDate());
			end = Timestamp.valueOf(matchDTO.getEndDate());
		} else {
			throw new ServiceDataException("Invalid data.");
		}

		Match matchEntity = new Match();
		matchEntity.setStartDate(start);
		matchEntity.setEndDate(end);
		if (!matchDTO.getResult().equals("NULL")) {
			matchEntity.setResult(matchDTO.getResult());
		}
		matchEntity.setStatus(Integer.parseInt(matchDTO.getStatus()));
		if (matchDTO.getId() != null) {
			matchEntity.setId(Integer.parseInt(matchDTO.getId()));
		}

		DAOFactory factory = DAOFactory.getInstance();
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = totoDAO.editMatchResult(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
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
	 * @return a boolean value <code>true</code>in case of successful
	 *         calculation and <code>false</code> otherwise.
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
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		try {
			result = totoDAO.closeCoupon(Integer.parseInt(couponId));
		} catch (NotAllFinishedMatchesDAOException e) {
			throw new NotAllFinishedMatchesServiceException("Close coupon failed.", e);
		} catch (DAOException e) {
			throw new ServiceException("Close coupon failed.", e);
		}
		return result;
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
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		List<Coupon> list = null;
		try {
			list = totoDAO.getAllCoupons();
		} catch (DAOException e) {
			throw new ServiceException("Get all coupons failed.", e);
		}
		return list;
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
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();
		try {
			Coupon coupon = totoDAO.getCouponById(Integer.parseInt(id));
			return coupon;
		} catch (DAOException e) {
			throw new ServiceException("Get coupon failed.", e);
		}

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
	 * @return a boolean value <code>true</code>in case of successful edit and
	 *         <code>false</code> otherwise.
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
		TotalizatorOperationDAO totoDAO = factory.getTotalizatorOperationDAO();

		Timestamp start = Timestamp.valueOf(couponDTO.getStartDate());
		Timestamp end = Timestamp.valueOf(couponDTO.getEndDate());
		if (start.after(end)) {
			throw new ServiceDataException("Invalid dates: start date is after end date");
		}
		try {
			Coupon couponEntity = new Coupon();
			couponEntity.setId(Integer.parseInt(couponDTO.getId()));
			couponEntity.setPull(Integer.parseInt(couponDTO.getPull()));
			couponEntity.setJackpot(Integer.parseInt(couponDTO.getJackpot()));
			couponEntity.setMinBetAmount(Integer.parseInt(couponDTO.getMinBetAmount()));
			couponEntity.setStatus(Integer.parseInt(couponDTO.getStatus()));
			couponEntity.setStartDate(start);
			couponEntity.setEndDate(end);

			boolean result = totoDAO.editCouponInfo(couponEntity);
			return result;
		} catch (DAOException e) {
			throw new ServiceException("Edit coupon failed.", e);
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
