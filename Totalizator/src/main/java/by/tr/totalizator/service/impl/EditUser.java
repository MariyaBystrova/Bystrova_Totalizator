package by.tr.totalizator.service.impl;

import java.util.Arrays;

import by.tr.totalizator.dao.UserOperationDAO;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.dao.factory.DAOFactory;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.UserDTO;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.impl.util.HashMd5Coder;
import by.tr.totalizator.service.impl.util.Validator;

/**
 * Represents an implementation of
 * {@link by.tr.totalizator.service.UserService}.
 * 
 * @author Mariya Bystrova
 *
 */
public class EditUser implements UserService {

	/**
	 * Registers a new user in the system.
	 * <p>
	 * Validates userDTO and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * <p>
	 * Makes a hash of password.
	 * </p>
	 * <p>
	 * Forms a {@link by.tr.totalizator.entity.bean.User} object.
	 * </p>
	 * 
	 * @param userDTO
	 *            a DTO value of {@link by.tr.totalizator.entity.dto.UserDTO}
	 *            object to be created.
	 * @return a boolean value <code>true</code>, if user is created and
	 *         <code>false</code>, if registration failed.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public boolean registerUser(UserDTO userDTO) throws ServiceException, ServiceDataException {

		if (!Validator.registrationValidator(userDTO)) {
			throw new ServiceDataException("Invalid data.");
		}

		// city and address are unnecessary for fulfilling
		if (userDTO.getCity().isEmpty()) {
			userDTO.setCity(null);
		}
		if (userDTO.getAddress().isEmpty()) {
			userDTO.setAddress(null);
		}

		User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getSex(), userDTO.getEmail(),
				userDTO.getCountry(), userDTO.getCity(), userDTO.getAddress(), userDTO.getLogin(), userDTO.getRole());

		String passwordHash = HashMd5Coder.hashMd5(userDTO.getPassword());
		userDTO.setPassword((byte) 0);
		userDTO.setRepPassword((byte) 0);

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userDAO = factory.getUserOperationDAO();
		try {
			userDAO.createUser(user, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException("Create user failed.", e);
		}
		return true;
	}

	/**
	 * Updates user's personal information such as first name, last name, sex,
	 * email, country, city and address.
	 * <p>
	 * Validates userDTO and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * <p>
	 * Forms a {@link by.tr.totalizator.entity.bean.User} object.
	 * </p>
	 * 
	 * @param userDTO
	 *            a DTO value of {@link by.tr.totalizator.entity.dto.UserDTO}
	 *            object to be changed.
	 * @return an updated {@link by.tr.totalizator.entity.bean.User} object
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public User editUserPersonalInfo(UserDTO userDTO) throws ServiceException, ServiceDataException {
		if (!Validator.userPersonalInfoValidator(userDTO)) {
			throw new ServiceDataException("Invalid data.");
		}
		User user = null;

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userOpDao = factory.getUserOperationDAO();
		try {
			user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getSex(), userDTO.getEmail(),
					userDTO.getCountry(), userDTO.getCity(), userDTO.getAddress());
			user.setId(Integer.parseInt(userDTO.getId()));
			user = userOpDao.editUserPersonalInfo(user);
		} catch (DAOException e) {
			throw new ServiceException("Edit user personal information failed. ", e);
		}
		return user;
	}

	/**
	 * Updates user's account details (password), which is tagged by id.
	 * <p>
	 * Validates input parameters and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * <p>
	 * Makes a hash of password.
	 * </p>
	 * 
	 * @param password
	 *            a value of password to change.
	 * @param rpassword
	 *            repeated value of password to change.
	 *            <p>
	 *            It must be equals to the first one, otherwise method throws
	 *            {@link by.tr.totalizator.service.exception.ServiceDataException}.
	 *            </p>
	 * @param userId
	 *            a value of user's unique identifier, representing the user to
	 *            be changed.
	 * @return a boolean value <code>true</code> in case of successful edit and
	 *         <code>false</code> otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public boolean editUserAccountInfo(byte[] password, byte[] rpassword, int userId)
			throws ServiceException, ServiceDataException {
		if (!Validator.userAccountInfoValidator(password, rpassword, userId)) {
			throw new ServiceDataException("Invalid data.");
		}
		String passwordHash = HashMd5Coder.hashMd5(password);
		for (int i = 0; i < password.length; i++) {
			password[i] = 0;
			rpassword[i] = 0;
		}

		boolean result = false;
		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userOpDao = factory.getUserOperationDAO();

		try {
			result = userOpDao.editUserAccountInfo(userId, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException("Edit user account information failed. ", e);
		}

		return result;
	}

	/**
	 * Provides an authentication service for user.
	 * <p>
	 * Validates input parameters and throws
	 * {@link by.tr.totalizator.service.exception.ServiceDataException} in case
	 * of invalid data.
	 * </p>
	 * <p>
	 * Makes a hash of password.
	 * </p>
	 * 
	 * @param login
	 *            a value of login to enter the system.
	 * @param password
	 *            a value of password to enter the system.
	 * @return a {@link by.tr.totalizator.entity.bean.User} object representing
	 *         the entered login and password in the system.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	@Override
	public User authentication(String login, byte[] password) throws ServiceException, ServiceDataException {

		User user = null;
		if (!Validator.authenticationValidator(login, password)) {
			throw new ServiceDataException("Invalid login or password.");
		}
		String passwordHash = HashMd5Coder.hashMd5(password);
		Arrays.fill(password, (byte) 0);

		DAOFactory factory = DAOFactory.getInstance();
		UserOperationDAO userOpDao = factory.getUserOperationDAO();
		try {
			user = userOpDao.authentication(login, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException("Autentication failed. ", e);
		}
		return user;
	}

}
