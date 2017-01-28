package by.tr.totalizator.service;

import java.util.List;

import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;

/**
 * Represents an interface MatchService, implementation of which provides
 * a proper work with general macth's services.
 * 
 * @author Mariya Bystrova
 *
 */
public interface MatchService {
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
}
