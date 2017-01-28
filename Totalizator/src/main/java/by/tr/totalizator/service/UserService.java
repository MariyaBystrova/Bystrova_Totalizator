package by.tr.totalizator.service;

import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.UserDTO;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;

/**
 * Represents an interface UserService, implementation of which provides a
 * proper work with user's services.
 * 
 * @author Mariya Bystrova
 *
 */
public interface UserService {
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
	 * @return a boolean value {@code true}, if user is created and
	 *         {@code false}, if registration failed.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean registerUser(UserDTO userDTO) throws ServiceException, ServiceDataException;

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
	User editUserPersonalInfo(UserDTO userDTO) throws ServiceException, ServiceDataException;

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
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws ServiceException
	 *             if a problem with data source or connection pool has occur.
	 * @throws ServiceDataException
	 *             if a problem with invalid data has occur.
	 */
	boolean editUserAccountInfo(byte[] password, byte[] rpassword, int userId)
			throws ServiceException, ServiceDataException;

	/**
	 * Provides an authorization service for user.
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
	User authorization(String login, byte[] password) throws ServiceException, ServiceDataException;
}
