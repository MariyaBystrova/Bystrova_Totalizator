package by.tr.totalizator.service.impl;

import by.tr.totalizator.dao.BetOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.BetService;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.Validator;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.service.BetService}.
 * 
 * @author Mariya Bystrova
 *
 */
public class EditBet implements BetService {

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
	@Override
	public boolean registerBet(RegisterBetDTO bean) throws ServiceException, ServiceDataException {

		boolean result = Validator.validateBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(),
				bean.getUserId(), bean.getCouponId());
		if (!result) {
			throw new ServiceDataException("Invalid data.");
		}
		DAOFactory factory = DAOFactory.getInstance();
		BetOperationDAO betDAO = factory.getBetOperationDAO();
		try {
			result = betDAO.registerBet(bean.getMap(), bean.getAmount(), bean.getCreditCardNumber(), bean.getUserId(),
					Integer.parseInt(bean.getCouponId()));
		} catch (DAOException e) {
			throw new ServiceException("Register bet failed.", e);
		}
		return result;
	}

}
