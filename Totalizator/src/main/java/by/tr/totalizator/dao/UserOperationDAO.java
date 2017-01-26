package by.tr.totalizator.dao;

import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.bean.User;

/**
 * Represents an interface UserOperationDAO, implementation of which provides a
 * proper work with the specific data source.
 * 
 * @author Mariya Bystrova
 *
 */
public interface UserOperationDAO {
	/**
	 * Inserts a new record, representing a new user, into the data source.
	 * 
	 * @param user
	 *            a value of user object to be created.
	 * @param password
	 *            a hash value of password.
	 * @return a boolean value {@code true}, if user is created and
	 *         {@code false}, if registration failed.
	 * @throws DAOException
	 *             if a problem with data source or connection pool has
	 *             occur.
	 */
	boolean createUser(User user, String password) throws DAOException;

	/**
	 * Updates the record in the data source, representing the particular user.
	 * 
	 * @param user
	 *            a value of user object to be changed.
	 * @return an updated {@link by.tr.totalizator.entity.bean.User} object.
	 * @throws DAOException
	 *             if a problem with data source or connection pool has
	 *             occur.
	 */
	User editUserPersonalInfo(User user) throws DAOException;

	/**
	 * Updates the record in the data source, representing the particular user,
	 * tagged by id.
	 * 
	 * @param userId
	 *            a value of user's unique identifier, representing the user to
	 *            be changed.
	 * @param password
	 *            a hash value of password to change.
	 * @return a boolean value {@code true} in case of successful edit and
	 *         {@code false} otherwise.
	 * @throws DAOException
	 *             if a problem with data source or connection pool has
	 *             occur.
	 */
	boolean editUserAccountInfo(int userId, String password) throws DAOException;

	/**
	 * Checks the presence of tandem "user-password" in the data source.
	 * 
	 * @param login
	 *            a value of login.
	 * @param password
	 *            a value of password.
	 * @return a {@link by.tr.totalizator.entity.bean.User} object representing
	 *         the entered login and password in the system.
	 * @throws DAOException
	 *             if a problem with data source or connection pool has
	 *             occur.
	 */
	User authentication(String login, String password) throws DAOException;
}
