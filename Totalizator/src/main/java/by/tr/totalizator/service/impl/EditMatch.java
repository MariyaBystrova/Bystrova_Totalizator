package by.tr.totalizator.service.impl;

import java.sql.Timestamp;
import java.util.List;

import by.tr.totalizator.dao.MatchOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.service.MatchService;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.Validator;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.service.MatchService}.
 * 
 * @author Mariya Bystrova
 *
 */
public class EditMatch implements MatchService {

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
		MatchOperationDAO matchDAO = factory.getMatchOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = matchDAO.registerMatch(matchEntity);
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
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
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
		MatchOperationDAO matchDAO = factory.getMatchOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = matchDAO.editMatch(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
		}
		return result;
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
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
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
		MatchOperationDAO matchDAO = factory.getMatchOperationDAO();
		boolean result = false;
		if (start.before(end)) {
			try {
				result = matchDAO.editMatchResult(matchEntity);
			} catch (DAOException e) {
				throw new ServiceException("Register match failed.", e);
			}
		}
		return result;
	}

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
		MatchOperationDAO matchDAO = factory.getMatchOperationDAO();

		List<Match> list = null;
		try {
			list = matchDAO.getCouponMatches(cuponId);
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
		MatchOperationDAO matchDAO = factory.getMatchOperationDAO();

		List<Match> list = null;
		try {
			list = matchDAO.getCurrentCouponMatches();
		} catch (DAOException e) {
			throw new ServiceException("Get coupon matches failed.", e);
		}
		return list;
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
