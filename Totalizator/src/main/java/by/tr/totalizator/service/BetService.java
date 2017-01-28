package by.tr.totalizator.service;

import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;

/**
 * Represents an interface BetService, implementation of which provides
 * a proper work with bet's services.
 * 
 * @author Mariya Bystrova
 *
 */
public interface BetService {
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

}
